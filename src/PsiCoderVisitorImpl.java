import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;
import java.util.stream.Collectors;

public class PsiCoderVisitorImpl extends PsiCoderBaseVisitor<PsiCoderType> {
    private final Map<String, Function> functions;
    private Scope scope;
    
    public PsiCoderVisitorImpl( Scope scope, Map<String, Function> functions ) {
        this.scope = scope;
        this.functions = functions;
    }
    
    @Override
    public PsiCoderType visitProgram( PsiCoderParser.ProgramContext ctx ) {
        if ( ctx.globalDeclaration().size() > 0 ) {
            visitGlobalDeclaration( ctx.globalDeclaration( 0 ) );
            if ( ctx.globalDeclaration( 1 ) != null )
                visitGlobalDeclaration( ctx.globalDeclaration( 1 ) );
        }
        return visitInstructions( ctx.instructions() );
    }
    
    @Override
    public PsiCoderType visitGlobalDeclaration( PsiCoderParser.GlobalDeclarationContext ctx ) {
        ctx.structDeclaration().forEach( this::visitStructDeclaration );
        ctx.functionDeclaration().forEach( this::visitFunctionDeclaration );
        return null;
    }
    
    private PsiCoderType getDefaultValueByDataType( String dataType ) {
        switch ( dataType ) {
            case "booleano":
                return new PsiCoderType( false );
            case "caracter":
                return new PsiCoderType( Character.MIN_VALUE );
            case "entero":
                return new PsiCoderType( 0 );
            case "real":
                return new PsiCoderType( 0.0 );
            case "cadena":
                return new PsiCoderType( "" );
        }
        return null;
    }
    
    @Override
    public PsiCoderType visitStructDeclaration( PsiCoderParser.StructDeclarationContext ctx ) {
        String structName = ctx.ID().getText();
        if ( !Scope.structsDeclared.containsKey( structName ) ) {
            Scope.structsDeclared.put( structName, new StructDeclaration() );
            ctx.structMember()
                .forEach( structMemberContext -> visitStructMember( structMemberContext, structName ) );
        } else
            SemanticError.throwError( "Una estructura con el mismo identificador ha sido declarada." );
        return null;
    }
    
    public void visitStructMember( PsiCoderParser.StructMemberContext ctx, String structName ) {
        if ( ctx.DATA_TYPE() != null ) {
            String dataType = ctx.DATA_TYPE().getText();
            PsiCoderType defaultValue = getDefaultValueByDataType( dataType );
            ctx.ID()
                .stream()
                .map( ParseTree::getText )
                .forEach( memberId -> {
                    if ( !Scope.structsDeclared.get( structName ).getAtomic().containsKey( memberId )
                        && !Scope.structsDeclared.get( structName ).getCompound().containsKey( memberId ) ) {
                        Scope.structsDeclared.get( structName ).getAtomic().put( memberId, defaultValue );
                    } else
                        SemanticError.throwError( "Miembro de la estructura '" + structName + "', '" + memberId + "', ya definido" );
                } );
        } else {
            String structMemberName = ctx.ID( 0 ).getText();
            if ( !Scope.structsDeclared.containsKey( structMemberName ) )
                SemanticError.throwError( "La estructura '" + structMemberName + "' no se encuentra declarada" );
            for ( int i = 1; i < ctx.ID().size(); ++i ) {
                String memberId = ctx.ID( i ).getText();
                if ( !Scope.structsDeclared.get( structName ).getAtomic().containsKey( memberId )
                    && !Scope.structsDeclared.get( structName ).getCompound().containsKey( memberId ) ) {
                    Scope.structsDeclared.get( structName ).getCompound().put( memberId, structMemberName );
                } else
                    SemanticError.throwError( "Miembro de la estructura '" + structName + "', '" + memberId + "' ya definido" );
            }
        }
    }
    
    @Override
    public PsiCoderType visitFunctionDeclaration( PsiCoderParser.FunctionDeclarationContext ctx ) {
        PsiCoderType functionDataType = visitDataType( ctx.dataType( 0 ) );
        String functionName = ctx.ID( 0 ).getText();
        if ( functionDataType.isStruct() ) {
            if ( !Scope.structsDeclared.containsKey( functionDataType.toStruct().getType() ) )
                SemanticError.throwError( "En la funcion '" + functionName +
                    "', la estructura '" + functionDataType.toStruct().getType() + "' no se encuentra declarada" );
        }
        List<FunctionParameter> parameters = new ArrayList<>();
        for ( int i = 1; i < ctx.dataType().size(); ++i ) {
            PsiCoderType argumentDataType = visitDataType( ctx.dataType( i ) );
            String argumentId = ctx.ID( i ).getText();
            parameters.add(
                new FunctionParameter(
                    argumentId,
                    argumentDataType
                )
            );
            Set<String> parametersSet = parameters.stream().map( FunctionParameter::getIdentifier ).collect( Collectors.toSet() );
            if ( parameters.size() > parametersSet.size() )
                SemanticError.throwError( "En la funcion '" + functionName + "', el parametro '" + argumentId + "' ya esta definido" );
        }
        functions.put(
            functionName + ( ctx.dataType().size() - 1 ),
            new Function(
                scope,
                functionDataType,
                parameters,
                ctx.instructions(),
                ctx.returnExpression()
            )
        );
        return null;
    }
    
    public PsiCoderType visitDataType( PsiCoderParser.DataTypeContext ctx ) {
        if ( ctx.DATA_TYPE() != null )
            return getDefaultValueByDataType( ctx.DATA_TYPE().getText() );
        return new PsiCoderType(
            new Struct(
                ctx.ID().getText(),
                Scope.structsDeclared.get( ctx.ID().getText() ).getAtomic(),
                Scope.structsDeclared.get( ctx.ID().getText() ).getCompound()
            )
        );
    }
    
    @Override
    public PsiCoderType visitReturnExpression( PsiCoderParser.ReturnExpressionContext ctx ) {
        return visit( ctx.expression() );
    }
    
    public void visitVariableDeclaration( PsiCoderParser.VariableDeclarationContext ctx, String dataType ) {
        String identifier = ctx.ID().getText();
        if ( scope.searchId( identifier, false ) == null ) {
            if ( ctx.expression() != null ) {
                PsiCoderType value = visit( ctx.expression() );
                if ( dataType.equals( "booleano" ) && value.isBoolean() )
                    scope.add( identifier, new PsiCoderType( value.toBoolean() ) );
                else if ( dataType.equals( "caracter" ) && value.isCharacter() )
                    scope.add( identifier, new PsiCoderType( value.toCharacter() ) );
                else if ( dataType.equals( "entero" ) && value.isNumber() )
                    scope.add( identifier, new PsiCoderType( value.toInteger() ) );
                else if ( dataType.equals( "real" ) && value.isNumber() )
                    scope.add( identifier, new PsiCoderType( value.toReal() ) );
                else if ( dataType.equals( "cadena" ) && value.isString() )
                    scope.add( identifier, new PsiCoderType( value.toStringType() ) );
                else SemanticError.throwError( "Se esperaba un valor de tipo '" + dataType + "'" );
            } else
                scope.add( identifier, getDefaultValueByDataType( dataType ) );
        } else
            SemanticError.throwError( "Variable '" + identifier + "' ya declarada " );
    }
    
    @Override
    public PsiCoderType visitVariableAssignment( PsiCoderParser.VariableAssignmentContext ctx ) {
        PsiCoderType value = visit( ctx.expression() );
        variableAssignment( value, ctx.ID() );
        return null;
    }
    
    @Override
    public PsiCoderType visitStructInstantiation( PsiCoderParser.StructInstantiationContext ctx ) {
        String structName = ctx.ID().getText();
        if ( Scope.structsDeclared.containsKey( structName ) )
            ctx.structInstantiationAssignment().forEach(
                structInstantiationAssignmentContext ->
                    visitStructInstantiationAssignment( structInstantiationAssignmentContext, structName )
            );
        else
            SemanticError.throwError( "La estructura '" + structName + "' no se encuentra declarada" );
        return null;
    }
    
    public void visitStructInstantiationAssignment(
        PsiCoderParser.StructInstantiationAssignmentContext ctx,
        String structName
    ) {
        String structId = ctx.ID().getText();
        if ( scope.searchId( structId ) == null ) {
            if ( ctx.expression() != null ) {
                PsiCoderType expressionResult = visit( ctx.expression() );
                if ( expressionResult.isStruct() )
                    scope.add( structId, expressionResult );
                else
                    SemanticError.throwError( "Intento de asignar un valor primitivo a la variable '"
                        + structId + "' de tipo '" + structName + "'" );
            } else
                scope.add( structId,
                    new PsiCoderType(
                        new Struct(
                            structName,
                            Scope.structsDeclared.get( structName ).getAtomic(),
                            Scope.structsDeclared.get( structName ).getCompound()
                        )
                    )
                );
        } else
            SemanticError.throwError( "La variable '" + structId + "' ya se encuentra declarada" );
    }
    
    @Override
    public PsiCoderType visitInstructions( PsiCoderParser.InstructionsContext ctx ) {
        return visitChildren( ctx );
    }
    
    @Override
    public PsiCoderType visitInstruction( PsiCoderParser.InstructionContext ctx ) {
        if ( ctx.DATA_TYPE() != null )
            ctx.variableDeclaration().forEach(
                variableDeclarationContext ->
                    visitVariableDeclaration( variableDeclarationContext, ctx.DATA_TYPE().getText() ) );
        else if ( ctx.variableAssignment( 0 ) != null )
            ctx.variableAssignment().forEach( this::visitVariableAssignment );
        else return visitChildren( ctx );
        return null;
    }
    
    public PsiCoderType readInput() {
        Scanner in = new Scanner( System.in );
        if ( in.hasNextBoolean() )
            return new PsiCoderType( in.nextBoolean() );
        if ( in.hasNextInt() )
            return new PsiCoderType( in.nextInt() );
        if ( in.hasNextDouble() )
            return new PsiCoderType( in.nextDouble() );
        String line = in.nextLine();
        if ( line.charAt( 0 ) == '\'' && line.charAt( 2 ) == '\'' )
            return new PsiCoderType( line.charAt( 1 ) );
        else
            return new PsiCoderType( line );
    }
    
    @Override
    public PsiCoderType visitRead( PsiCoderParser.ReadContext ctx ) {
        PsiCoderType value = readInput();
        variableAssignment( value, ctx.ID() );
        return null;
    }
    
    private void variableAssignment( PsiCoderType value, List<TerminalNode> ids ) {
        String identifier = ids.get( 0 ).getText();
        PsiCoderType variable = scope.searchId( identifier );
        if ( variable != null ) {
            if ( ids.size() > 1 ) {
                if ( variable.isStruct() ) {
                    List<String> literals = ids
                        .stream()
                        .map( ParseTree::getText )
                        .collect( Collectors.toList() );
                    scope.assignToStructMember( literals, value );
                } else
                    SemanticError.throwError( "Intento de acceder a las variables miembro de la"
                        + " variable '" + identifier + "', la cual no es de tipo estructura" );
            } else {
                if ( variable.isInteger() && value.isReal() )
                    scope.add( identifier, new PsiCoderType( value.toInteger() ) );
                else
                    scope.add( identifier, value );
            }
        } else
            SemanticError.throwError( "La variable '" + identifier + "' no se encuentra declarada" );
    }
    
    @Override
    public PsiCoderType visitPrint( PsiCoderParser.PrintContext ctx ) {
        String toPrint = "";
        int i = 0;
        while ( ctx.expression( i ) != null ) {
            PsiCoderType value = visit( ctx.expression( i ) );
            if ( value.isBoolean() )
                toPrint += ( value.toBoolean() ? "verdadero" : "falso" );
            else
                toPrint += String.valueOf( value.getValue() );
            i++;
        }
        System.out.println( toPrint );
        return null;
    }
    
    @Override
    public PsiCoderType visitConditional( PsiCoderParser.ConditionalContext ctx ) {
        if ( visit( ctx.expression() ).isBoolean() ) {
            scope = new Scope( scope, false );
            if ( visit( ctx.expression() ).toBoolean() )
                visitInstructions( ctx.instructions( 0 ) );
            else if ( ctx.THEN() != null )
                visitInstructions( ctx.instructions( 1 ) );
            scope = scope.getParentScope();
        } else
            SemanticError.throwError( "La expresion del condicional debe evaluar a un booleano" );
        return null;
    }
    
    @Override
    public PsiCoderType visitWhileLoop( PsiCoderParser.WhileLoopContext ctx ) {
        if ( visit( ctx.expression() ).isBoolean() ) {
            scope = new Scope( scope, false );
            while ( visit( ctx.expression() ).toBoolean() )
                visitInstructions( ctx.instructions() );
            scope = scope.getParentScope();
        } else
            SemanticError.throwError( "La expresion del ciclo 'mientras' debe evaluar a un booleano" );
        return null;
    }
    
    @Override
    public PsiCoderType visitDoWhile( PsiCoderParser.DoWhileContext ctx ) {
        if ( visit( ctx.expression() ).isBoolean() ) {
            scope = new Scope( scope, false );
            do
                visitInstructions( ctx.instructions() );
            while ( visit( ctx.expression() ).toBoolean() );
            scope = scope.getParentScope();
        } else
            SemanticError.throwError( "La expresion del ciclo 'hacer mientras' debe evaluar a un booleano" );
        return null;
    }
    
    @Override
    public PsiCoderType visitForLoop( PsiCoderParser.ForLoopContext ctx ) {
        boolean loopVarOutOfScope = false;
        int expressionIndex = 0;
        String loopVarId = ctx.ID( 0 ).getText();
        if ( ctx.DATA_TYPE() != null ) {
            if ( ctx.DATA_TYPE().getText().equals( "entero" ) ) {
                scope = new Scope( scope, false );
                if ( ctx.expression().size() > 1 )
                    expressionIndex = determineForLoopVar( ctx, expressionIndex, loopVarId );
                else
                    scope.add( loopVarId, getDefaultValueByDataType( "entero" ) );
            } else
                SemanticError.throwError( "La variable '" + loopVarId + "' debe ser de tipo entero" );
        } else {
            loopVarOutOfScope = true;
            PsiCoderType searchResult = scope.searchId( loopVarId );
            if ( searchResult != null ) {
                if ( searchResult.isInteger() ) {
                    if ( ctx.expression().size() > 1 )
                        expressionIndex = determineForLoopVar( ctx, expressionIndex, loopVarId );
                    scope = new Scope( scope, false );
                } else
                    SemanticError.throwError( "La variable '" + loopVarId + "' debe ser de tipo entero" );
            } else
                SemanticError.throwError( "La variable '" + loopVarId + "' no se encuentra declarada" );
        }
        
        if ( visit( ctx.expression( expressionIndex ) ).isBoolean() ) {
            int loopStep = 0;
            if ( ctx.ID( 1 ) != null ) {
                String identifier = ctx.ID( 1 ).getText();
                PsiCoderType loopStepResult = scope.searchId( identifier );
                if ( loopStepResult != null ) {
                    if ( loopStepResult.isInteger() ) {
                        loopStep = loopStepResult.toInteger();
                    } else
                        SemanticError.throwError( "La variable '" + identifier + "' debe ser de tipo entero" );
                } else
                    SemanticError.throwError( "La variable '" + identifier + "' no ha sido declarada" );
            } else
                loopStep = Integer.parseInt( ctx.INT().getText() );
            
            while ( visit( ctx.expression( expressionIndex ) ).toBoolean() ) {
                visitInstructions( ctx.instructions() );
                PsiCoderType updatedLoopVarId;
                if ( loopVarOutOfScope ) {
                    updatedLoopVarId = new PsiCoderType( scope.getParentScope().searchId( loopVarId ).toInteger() + loopStep );
                    updatedLoopVarId.toInteger();
                    scope.getParentScope().add( loopVarId, updatedLoopVarId );
                } else {
                    updatedLoopVarId = new PsiCoderType( scope.searchId( loopVarId ).toInteger() + loopStep );
                    updatedLoopVarId.toInteger();
                    scope.add( loopVarId, updatedLoopVarId );
                }
            }
        } else
            SemanticError.throwError( "La condición del ciclo 'para' debe evaluar a un booleano" );
        scope = scope.getParentScope();
        return null;
    }
    
    private int determineForLoopVar( PsiCoderParser.ForLoopContext ctx, int expressionIndex, String loopVarId ) {
        PsiCoderType loopVar = visit( ctx.expression( expressionIndex++ ) );
        if ( loopVar.isInteger() ) {
            scope.add( loopVarId, loopVar );
        } else
            SemanticError.throwError( "El valor asignado a la variable '" + loopVarId + "' debe ser un entero" );
        return expressionIndex;
    }
    
    @Override
    public PsiCoderType visitMultSelection( PsiCoderParser.MultSelectionContext ctx ) {
        PsiCoderType expression = visit( ctx.expression() );
        if ( expression.isStruct() )
            SemanticError.throwError( "El argumento de seleccionar no puede ser de tipo estructura." );
        int i = 0;
        if ( ctx.CASE( i ) != null ) {
            do {
                PsiCoderType caseValue = visitPrimitiveValue( ctx.primitiveValue( i ) );
                if ( expression.isCompatible( caseValue ) ) {
                    if ( expression.equals( caseValue ) ) {
                        scope = new Scope( scope, false );
                        visitInstructions( ctx.instructions( i ) );
                        scope = scope.getParentScope();
                        return null;
                    }
                } else
                    SemanticError.throwError( "El valor de la variable de seleccion es diferente"
                        + " al valor evaluado por el caso: '" + caseValue.getValue() + "'" );
            } while ( ctx.CASE( ++i ) != null );
            if ( ctx.defaultCase() != null ) visitDefaultCase( ctx.defaultCase() );
        } else
            visitDefaultCase( ctx.defaultCase() );
        return null;
    }
    
    public PsiCoderType visitDefaultCase( PsiCoderParser.DefaultCaseContext ctx ) {
        scope = new Scope( scope, false );
        visitInstructions( ctx.instructions() );
        scope = scope.getParentScope();
        return null;
    }
    
    @Override
    public PsiCoderType visitFunctionCall( PsiCoderParser.FunctionCallContext ctx ) {
        String identifier = ctx.ID().getText() + ctx.expression().size();
        Function function = functions.get( identifier );
        if ( function == null )
            SemanticError.throwError( "La funcion '" + ctx.ID().getText() + "' no se "
                + "encuentra definida o el número de argumentos es inválido" );
        List<PsiCoderType> functionArguments = ctx.expression()
            .stream()
            .map( this::visit )
            .collect( Collectors.toList() );
        return function.makeCall( ctx.ID().getText(), functionArguments, functions );
    }
    
    @Override
    public PsiCoderType visitPrimitiveValue( PsiCoderParser.PrimitiveValueContext ctx ) {
        if ( ctx.INT() != null )
            return new PsiCoderType( Integer.valueOf( ctx.INT().getText() ) );
        if ( ctx.REAL() != null )
            return new PsiCoderType( Double.valueOf( ctx.REAL().getText() ) );
        if ( ctx.STRING() != null )
            return new PsiCoderType( ctx.STRING().getText().split( "\"" )[ 1 ] );
        if ( ctx.CHAR() != null )
            return new PsiCoderType( ctx.CHAR().getText().charAt( 1 ) );
        return new PsiCoderType( ctx.BOOLEAN().getText().equals( "verdadero" ) );
    }
    
    @Override
    public PsiCoderType visitMinusExpression( PsiCoderParser.MinusExpressionContext ctx ) {
        PsiCoderType exprResult = visit( ctx.expression() );
        if ( exprResult.isInteger() ) return new PsiCoderType( -exprResult.toInteger() );
        if ( exprResult.isReal() ) return new PsiCoderType( -exprResult.toReal() );
        SemanticError.throwError( "El operador '-' solo puede aplicarse a enteros y reales." );
        return null;
    }
    
    @Override
    public PsiCoderType visitNegExpression( PsiCoderParser.NegExpressionContext ctx ) {
        PsiCoderType exprResult = visit( ctx.expression() );
        if ( !exprResult.isBoolean() )
            SemanticError.throwError( "El operador '!' solo puede aplicarse a booleanos." );
        return new PsiCoderType( !exprResult.toBoolean() );
    }
    
    @Override
    public PsiCoderType visitMultExpression( PsiCoderParser.MultExpressionContext ctx ) {
        PsiCoderType leftExprResult = visit( ctx.expression( 0 ) );
        PsiCoderType rightExprResult = visit( ctx.expression( 1 ) );
        if ( !leftExprResult.isNumber() && !rightExprResult.isNumber() )
            SemanticError.throwError( "La multiplicacion, division y modulo solo pueden hacerse entre enteros y reales." );
        if ( leftExprResult.isInteger() && rightExprResult.isInteger() ) {
            Integer operationResult = leftExprResult.toInteger();
            if ( ctx.MULT_OP().getText().equals( "*" ) ) operationResult *= rightExprResult.toInteger();
            else if ( ctx.MULT_OP().getText().equals( "/" ) ) operationResult /= rightExprResult.toInteger();
            else operationResult %= rightExprResult.toInteger();
            return new PsiCoderType( operationResult );
        }
        Double operationResult = leftExprResult.toReal();
        if ( ctx.MULT_OP().getText().equals( "*" ) ) operationResult *= rightExprResult.toReal();
        else if ( ctx.MULT_OP().getText().equals( "/" ) ) operationResult /= rightExprResult.toReal();
        else operationResult %= rightExprResult.toReal();
        return new PsiCoderType( operationResult );
    }
    
    @Override
    public PsiCoderType visitAdExpression( PsiCoderParser.AdExpressionContext ctx ) {
        PsiCoderType leftExprResult = visit( ctx.expression( 0 ) );
        PsiCoderType rightExprResult = visit( ctx.expression( 1 ) );
        if ( ctx.AD_OP().getText().equals( "+" ) ) {
            if ( leftExprResult.isString() )
                return new PsiCoderType( leftExprResult.toStringType() + "" + rightExprResult.toString() );
            if ( rightExprResult.isString() )
                return new PsiCoderType( leftExprResult.toString() + "" + rightExprResult.toStringType() );
        }
        if ( !leftExprResult.isNumber() || !rightExprResult.isNumber() )
            SemanticError.throwError( "La suma y resta solo pueden hacerse entre enteros y reales,"
                + " y para la concatenacion uno de los operandos debe ser de tipo cadena" );
        if ( leftExprResult.isInteger() && rightExprResult.isInteger() ) {
            Integer operationResult = leftExprResult.toInteger();
            if ( ctx.AD_OP().getText().equals( "+" ) ) operationResult += rightExprResult.toInteger();
            else operationResult -= rightExprResult.toInteger();
            return new PsiCoderType( operationResult );
        }
        Double operationResult = leftExprResult.toReal();
        if ( ctx.AD_OP().getText().equals( "+" ) ) operationResult += rightExprResult.toReal();
        else operationResult -= rightExprResult.toReal();
        return new PsiCoderType( operationResult );
    }
    
    @Override
    public PsiCoderType visitCompExpression( PsiCoderParser.CompExpressionContext ctx ) {
        PsiCoderType leftExprResult = visit( ctx.expression( 0 ) );
        PsiCoderType rightExprResult = visit( ctx.expression( 1 ) );
        switch ( ctx.COMP_OP().getText() ) {
            case "<":
                return lessThan( leftExprResult, rightExprResult );
            case "<=":
                return lessEqThan( leftExprResult, rightExprResult );
            case ">":
                return greaterThan( leftExprResult, rightExprResult );
            case ">=":
                return greaterEqThan( leftExprResult, rightExprResult );
            default:
                return null;
        }
    }
    
    @Override
    public PsiCoderType visitEqExpression( PsiCoderParser.EqExpressionContext ctx ) {
        PsiCoderType leftExprResult = visit( ctx.expression( 0 ) );
        PsiCoderType rightExprResult = visit( ctx.expression( 1 ) );
        if ( ctx.EQUAL_OP().getText().equals( "==" ) )
            return new PsiCoderType( leftExprResult.equals( rightExprResult ) );
        else
            return new PsiCoderType( !leftExprResult.equals( rightExprResult ) );
    }
    
    @Override
    public PsiCoderType visitAndExpression( PsiCoderParser.AndExpressionContext ctx ) {
        PsiCoderType leftExprResult = visit( ctx.expression( 0 ) );
        PsiCoderType rightExprResult = visit( ctx.expression( 1 ) );
        if ( !leftExprResult.isBoolean() || !rightExprResult.isBoolean() )
            SemanticError.throwError( "Ambos terminos de la expresion logica 'and' deben ser de tipo booleano." );
        return new PsiCoderType( leftExprResult.toBoolean() && rightExprResult.toBoolean() );
    }
    
    @Override
    public PsiCoderType visitOrExpression( PsiCoderParser.OrExpressionContext ctx ) {
        PsiCoderType leftExprResult = visit( ctx.expression( 0 ) );
        PsiCoderType rightExprResult = visit( ctx.expression( 1 ) );
        if ( !( leftExprResult.isBoolean() || !rightExprResult.isBoolean() ) )
            SemanticError.throwError( "Ambos terminos de la expresion logica 'or' deben ser de tipo booleano." );
        return new PsiCoderType( leftExprResult.toBoolean() || rightExprResult.toBoolean() );
    }
    
    @Override
    public PsiCoderType visitNestedExpression( PsiCoderParser.NestedExpressionContext ctx ) {
        return visit( ctx.expression() );
    }
    
    @Override
    public PsiCoderType visitPrimitiveValExpression( PsiCoderParser.PrimitiveValExpressionContext ctx ) {
        return visitPrimitiveValue( ctx.primitiveValue() );
    }
    
    public PsiCoderType visitIdExpression( PsiCoderParser.IdExpressionContext ctx ) {
        List<String> literals = ctx.ID()
            .stream()
            .map( ParseTree::getText )
            .collect( Collectors.toList() );
        int i = 1;
        if ( literals.size() > i )
            return scope.getStructMemberValue( literals );
        String identifier = literals.get( 0 );
        PsiCoderType value = scope.searchId( identifier );
        if ( value == null )
            SemanticError.throwError( "La variable '" + identifier + "' no ha sido declarada" );
        return value;
    }
    
    @Override
    public PsiCoderType visitFunctionCallExpression( PsiCoderParser.FunctionCallExpressionContext ctx ) {
        return visitFunctionCall( ctx.functionCall() );
    }
    
    private PsiCoderType lessThan( PsiCoderType leftExpr, PsiCoderType rightExpr ) {
        if ( leftExpr.isString() && rightExpr.isString() )
            return new PsiCoderType( leftExpr.toStringType().compareTo( rightExpr.toStringType() ) < 0 );
        else if ( leftExpr.isNumber() && rightExpr.isNumber() )
            return new PsiCoderType( leftExpr.toReal() < rightExpr.toReal() );
        else
            SemanticError.throwError( "La comparacion menor que solo puede hacerse entre enteros y reales o entre solo cadenas" );
        return null;
    }
    
    private PsiCoderType lessEqThan( PsiCoderType leftExpr, PsiCoderType rightExpr ) {
        if ( leftExpr.isString() && rightExpr.isString() )
            return new PsiCoderType( leftExpr.toStringType().compareTo( rightExpr.toStringType() ) <= 0 );
        else if ( leftExpr.isNumber() && rightExpr.isNumber() )
            return new PsiCoderType( leftExpr.toReal() <= rightExpr.toReal() );
        else
            SemanticError.throwError( "La comparacion menor igual que solo puede hacerse entre "
                + "enteros y reales o entre solo cadenas" );
        return null;
    }
    
    private PsiCoderType greaterThan( PsiCoderType leftExpr, PsiCoderType rightExpr ) {
        if ( leftExpr.isString() && rightExpr.isString() )
            return new PsiCoderType( leftExpr.toStringType().compareTo( rightExpr.toStringType() ) > 0 );
        else if ( leftExpr.isNumber() && rightExpr.isNumber() )
            return new PsiCoderType( leftExpr.toReal() >= rightExpr.toReal() );
        else
            SemanticError.throwError( "La comparacion mayor que solo puede hacerse entre enteros y reales o entre solo cadenas" );
        return null;
    }
    
    private PsiCoderType greaterEqThan( PsiCoderType leftExpr, PsiCoderType rightExpr ) {
        if ( leftExpr.isString() && rightExpr.isString() )
            return new PsiCoderType( leftExpr.toStringType().compareTo( rightExpr.toStringType() ) >= 0 );
        else if ( leftExpr.isNumber() && rightExpr.isNumber() )
            return new PsiCoderType( leftExpr.toReal() >= rightExpr.toReal() );
        else
            SemanticError.throwError( "La comparacion mayor igual que solo puede hacerse entre enteros y reales o entre solo cadenas" );
        return null;
    }
}