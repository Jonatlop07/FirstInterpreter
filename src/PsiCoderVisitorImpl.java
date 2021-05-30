import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;
import java.util.stream.Collectors;

public class PsiCoderVisitorImpl extends PsiCoderBaseVisitor<PsiCoderType> {
    private Map<String, Function> functions;
    private Scope scope = new Scope( null, false );
    
    public PsiCoderVisitorImpl( Scope scope, Map<String, Function> functions ) {
        this.scope = scope;
        this.functions = functions;
    }
    
    @Override
    public PsiCoderType visitProgram( PsiCoderParser.ProgramContext ctx ) {
        if ( ctx.globalDeclaration().size() > 0 ) {
            visitGlobalDeclaration( ctx.globalDeclaration( 0 ) );
            
            if ( ctx.globalDeclaration( 1 ) != null ) {
                visitGlobalDeclaration( ctx.globalDeclaration( 1 ) );
            }
        }
        return visitInstructions( ctx.instructions() );
    }
    
    @Override
    public PsiCoderType visitGlobalDeclaration( PsiCoderParser.GlobalDeclarationContext ctx ) {
        ctx.structDeclaration().forEach( ( structDeclarationCtx ) -> visitStructDeclaration( structDeclarationCtx ) );
        ctx.functionDeclaration().forEach( ( functionDeclarationCtx ) -> visitFunctionDeclaration( functionDeclarationCtx ) );
        return null;
    }
    
    private Optional<PsiCoderType> getDefaultValueByDataType( String dataType ) {
        Optional empty = Optional.empty();
        switch ( dataType ) {
            case "booleano":
                return Optional.of( new PsiCoderType( false ) );
            case "caracter":
                return Optional.of( new PsiCoderType( Character.MIN_VALUE ) );
            case "entero":
                return Optional.of( new PsiCoderType( 0 ) );
            case "real":
                return Optional.of( new PsiCoderType( 0.0 ) );
            case "cadena":
                return Optional.of( new PsiCoderType( "" ) );
        }
        return empty;
    }
    
    @Override
    public PsiCoderType visitStructDeclaration( PsiCoderParser.StructDeclarationContext ctx ) {
        String structId = ctx.ID().getText();
        
        if ( !Scope.structsDeclared.containsKey( structId ) ) {
            Scope.structsDeclared.put( structId, new StructDeclaration() );
            ctx.structMember().forEach( structMemberContext -> visitStructMember( structMemberContext, structId ) );
        } else {
            System.err.printf( "Error semantico: Una estructura con el mismo identificador ha sido declarada." );
            System.exit( -1 );
        }
        return null;
    }
    
    public PsiCoderType visitStructMember( PsiCoderParser.StructMemberContext ctx, String structId ) {
        if ( ctx.DATA_TYPE() != null ) {
            String dataType = ctx.DATA_TYPE().getText();
            Optional<PsiCoderType> defaultValue = getDefaultValueByDataType( dataType );
            ctx.ID()
                .stream()
                .map( memberIdNode -> memberIdNode.getText() )
                .forEach( memberId -> {
                    if ( !Scope.structsDeclared.get( structId ).getAtomic().containsKey( memberId )
                        && !Scope.structsDeclared.get( structId ).getCompound().containsKey( memberId ) ) {
                        Scope.structsDeclared.get( structId ).getAtomic().put( memberId, defaultValue );
                    } else {
                        System.err.printf( "Error semantico: Miembro de la estructura "
                            + structId + ", " + memberId + ", ya definido" );
                        System.exit( -1 );
                    }
                } );
        } else {
            String structMemberId = ctx.ID().remove( 0 ).getText();
            if ( !Scope.structsDeclared.containsKey( structMemberId ) ) {
                System.err.printf( "Error semantico: La estructura " + structMemberId + " no se encuentra declarada" );
                System.exit( -1 );
                //
            }
            ctx.ID()
                .stream()
                .map( memberIdNode -> memberIdNode.getText() )
                .forEach( memberId -> {
                    if ( !Scope.structsDeclared.get( structId ).getAtomic().containsKey( memberId )
                        && !Scope.structsDeclared.get( structId ).getCompound().containsKey( memberId ) ) {
                        Scope.structsDeclared.get( structId ).getCompound().put( memberId, structMemberId );
                    } else {
                        System.err.printf( "Error semantico: Miembro de la estructura "
                            + structId + ", " + memberId + " ya definido" );
                        System.exit( -1 );
                    }
                } );
        }
        return null;
    }
    
    @Override
    public PsiCoderType visitFunctionDeclaration( PsiCoderParser.FunctionDeclarationContext ctx ) {
        String functionDataType = ctx.DATA_TYPE( 0 ).getText();
        String functionId = ctx.ID( 0 ).getText();
        
        List<FunctionParameter> parameters = new ArrayList<>();
        
        for ( int i = 1; i < ctx.DATA_TYPE().size(); ++i ) {
            String argumentDataType = ctx.DATA_TYPE( i ).getText();
            String argumentId = ctx.ID( i ).getText();
            parameters.add( new FunctionParameter(
                argumentId,
                getDefaultValueByDataType( argumentDataType ).get() )
            );
        }
        
        functions.put(
            functionId + ( ctx.DATA_TYPE().size() - 1 ),
            new Function(
                scope,
                getDefaultValueByDataType( functionDataType ).get(),
                parameters,
                ctx.instructions(),
                ctx.returnExpression() )
        );
        
        return null;
    }
    
    @Override
    public PsiCoderType visitReturnExpression( PsiCoderParser.ReturnExpressionContext ctx ) {
        return visit( ctx.expression() );
    }
    
    public PsiCoderType visitVariableDeclaration( PsiCoderParser.VariableDeclarationContext ctx, String dataType ) {
        String identifier = ctx.ID().getText();
        
        if ( scope.searchId( identifier, false ) == null ) {
            if ( ctx.expression() != null ) {
                PsiCoderType value = visit( ctx.expression() );
                
                if ( dataType.equals( "booleano" ) && value.isBoolean() ) {
                    scope.add( identifier, Optional.of( new PsiCoderType( value.toBoolean() ) ) );
                } else if ( dataType.equals( "caracter" ) && value.isCharacter() ) {
                    scope.add( identifier, Optional.of( new PsiCoderType( value.toCharacter() ) ) );
                } else if ( dataType.equals( "entero" ) && value.isNumber() ) {
                    scope.add( identifier, Optional.of( new PsiCoderType( value.toInteger() ) ) );
                } else if ( dataType.equals( "real" ) && value.isNumber() ) {
                    scope.add( identifier, Optional.of( new PsiCoderType( value.toReal() ) ) );
                } else if ( dataType.equals( "cadena" ) && value.isString() ) {
                    scope.add( identifier, Optional.of( new PsiCoderType( value.toStringType() ) ) );
                } else {
                    System.err.printf( "Error semantico, se esperaba un valor de tipo " + dataType );
                    System.exit( -1 );
                }
            } else
                scope.add( identifier, getDefaultValueByDataType( dataType ) );
        } else {
            System.err.printf( "Error semantico, variable " + identifier + "ya declarada " );
            System.exit( -1 );
        }
        
        return null;
    }
    
    @Override
    public PsiCoderType visitVariableAssignment( PsiCoderParser.VariableAssignmentContext ctx ) {
        String identifier = ctx.ID( 0 ).getText();
        PsiCoderType value = visit( ctx.expression() );
        Optional searchResult = scope.searchId( identifier );
        
        if ( searchResult != null ) {
            variableAssignment( identifier, searchResult, value, ctx.ID() );
        } else {
            System.err.printf( "Error semantico, variable " + identifier + " no declarada" );
            System.exit( -1 );
        }
        return null;
    }
    
    @Override
    public PsiCoderType visitStructInstantiation( PsiCoderParser.StructInstantiationContext ctx ) {
        String structName = ctx.ID( 0 ).getText();
        
        if ( Scope.structsDeclared.containsKey( structName ) ) {
            for ( int i = 1; i < ctx.ID().size(); ++i ) {
                String structId = ctx.ID( i ).getText();
                if ( scope.searchId( structId ) == null )
                    scope.add( ctx.ID( i ).getText(), structName );
                else {
                    System.err.printf( "Error semantico: La estructura " + structId + " se encuentra declarada" );
                    System.exit( -1 );
                }
            }
        } else {
            System.err.printf( "Error semantico: La estructura " + structName + " no se encuentra declarada" );
            System.exit( -1 );
        }
        
        return null;
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
            ctx.variableAssignment().forEach(
                variableAssignmentContext ->
                    visitVariableAssignment( variableAssignmentContext )
            );
        else return visitChildren( ctx );
        
        return null;
    }
    
    public PsiCoderType readInput() {
        Scanner in = new Scanner( System.in );
        if ( in.hasNextBoolean() )
            return new PsiCoderType( Boolean.valueOf( in.nextBoolean() ) );
        if ( in.hasNextInt() )
            return new PsiCoderType( Integer.valueOf( in.nextInt() ) );
        if ( in.hasNextDouble() )
            return new PsiCoderType( Double.valueOf( in.nextDouble() ) );
        String line = in.nextLine();
        if ( line.charAt( 0 ) == '\'' && line.charAt( 2 ) == '\'' ) {
            return new PsiCoderType( Character.valueOf( line.charAt( 1 ) ) );
        } else
            return new PsiCoderType( line );
        
    }
    
    @Override
    public PsiCoderType visitRead( PsiCoderParser.ReadContext ctx ) {
        String identifier = ctx.ID( 0 ).getText();
        Optional searchResult = scope.searchId( identifier );
        
        if ( searchResult != null ) {
            PsiCoderType value = readInput();
            variableAssignment( identifier, searchResult, value, ctx.ID() );
        } else {
            System.err.printf( "Error semantico, variable " + identifier + " no declarada" );
            System.exit( -1 );
        }
        return null;
    }
    
    private void variableAssignment( String identifier, Optional searchResult,
                                     PsiCoderType value, List<TerminalNode> ids ) {
        if ( ids.size() > 1 ) {
            if ( !( searchResult.get() instanceof PsiCoderType ) ) {
                List<String> literals = ids.stream().map( id -> id.getText() ).collect( Collectors.toList() );
                scope.assignStructMember( literals, value );
            } else {
                System.err.printf( "Error semantico: Intento de acceder a las variables miembro de la"
                    + " variable " + identifier + ", la cual no es de tipo estructura" );
                System.exit( -1 );
            }
        } else {
            if ( searchResult.get() instanceof PsiCoderType ) {
                if ( value.isReal() && ( ( PsiCoderType ) searchResult.get() ).isInteger() )
                    scope.add( identifier, Optional.of( new PsiCoderType( value.toInteger() ) ) );
                else scope.add( identifier, Optional.of( value ) );
            } else {
                System.err.printf( "Error semantico: Intento de asignar una variable de tipo estructura"
                    + " a una variable de tipo primitivo" );
                System.exit( -1 );
            }
        }
    }
    
    @Override
    public PsiCoderType visitPrint( PsiCoderParser.PrintContext ctx ) {
        int i = 0;
        String toPrint = "";
        
        while ( ctx.expression( i ) != null ) {
            toPrint += String.valueOf( visit( ctx.expression( i ) ).getValue() );
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
        } else {
            System.err.printf( "Error semantico: La expresion del condicional debe evaluar a un booleano" );
            System.exit( -1 );
        }
        return null;
    }
    
    @Override
    public PsiCoderType visitWhileLoop( PsiCoderParser.WhileLoopContext ctx ) {
        if ( visit( ctx.expression() ).isBoolean() ) {
            scope = new Scope( scope, false );
            
            while ( visit( ctx.expression() ).toBoolean() )
                visitInstructions( ctx.instructions() );
            
            scope = scope.getParentScope();
        } else {
            System.err.printf( "Error semantico: La expresion del ciclo mientras debe evaluar a un booleano" );
            System.exit( -1 );
        }
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
        } else {
            System.err.printf( "Error semantico: La expresion del ciclo hacer mientras debe evaluar a un booleano" );
            System.exit( -1 );
        }
        return null;
    }
    
    @Override
    public PsiCoderType visitForLoop( PsiCoderParser.ForLoopContext ctx ) {
        boolean loopVarOutOfScope = false;
        int expressionIndex = 0, loopIndex = 0;
        String loopVarId = ctx.ID( 0 ).getText();
        
        if ( ctx.DATA_TYPE() != null ) {
            if ( ctx.DATA_TYPE().getText().equals( "entero" ) ) {
                scope = new Scope( scope, false );
                if ( ctx.expression().size() > 1 ) {
                    PsiCoderType loopVar = visit( ctx.expression( expressionIndex++ ) );
                    if ( loopVar.isInteger() ) {
                        scope.add( loopVarId, Optional.of( loopVar ) );
                    } else {
                        System.err.printf( "Error semantico: El valor asignado a la variable " + loopVarId + " debe ser un entero" );
                        System.exit( -1 );
                    }
                } else {
                    scope.add( loopVarId, getDefaultValueByDataType( "entero" ) );
                }
                loopIndex = ( ( PsiCoderType ) scope.searchId( loopVarId ).get() ).toInteger();
            } else {
                System.err.printf( "Error semantico: La variable " + loopVarId + " debe ser de tipo entero" );
                System.exit( -1 );
            }
        } else {
            loopVarOutOfScope = true;
            Optional searchResult = scope.searchId( loopVarId );
            if ( searchResult != null ) {
                if ( searchResult.get() instanceof PsiCoderType
                    && ( ( PsiCoderType ) searchResult.get() ).isInteger() ) {
                    if ( ctx.expression().size() > 1 ) {
                        PsiCoderType loopVar = visit( ctx.expression( expressionIndex++ ) );
                        if ( loopVar.isInteger() ) {
                            loopIndex = loopVar.toInteger();
                            scope.add( loopVarId, Optional.of( loopVar ) );
                        } else {
                            System.err.printf( "Error semantico: El valor asignado a la variable " + loopVarId + " debe ser un entero" );
                            System.exit( -1 );
                        }
                    }
                    loopIndex = ( ( PsiCoderType ) scope.searchId( loopVarId ).get() ).toInteger();
                    scope = new Scope( scope, false );
                } else {
                    System.err.printf( "Error semantico: La variable " + loopVarId + " debe ser de tipo entero" );
                    System.exit( -1 );
                }
            } else {
                System.err.printf( "Error semantico: La variable " + loopVarId + " no se encuentra declarada" );
                System.exit( -1 );
            }
        }
        
        if ( visit( ctx.expression( expressionIndex ) ).isBoolean() ) {
            int loopStep = 0;
            if ( ctx.ID( 1 ) != null ) {
                Optional loopStepSearchResult = scope.searchId( ctx.ID( 1 ).getText() );
                if ( loopStepSearchResult != null ) {
                    PsiCoderType loopStepResult = ( PsiCoderType ) loopStepSearchResult.get();
                    if ( loopStepResult.isInteger() ) {
                        loopStep = loopStepResult.toInteger();
                    } else {
                        System.err.printf( "Error semantico: La variable ingresada en el argumento de paso del ciclo debe ser de tipo entero" );
                        System.exit( -1 );
                    }
                } else {
                    System.err.printf( "Error semantico: La variable ingresada en el argumento de paso del ciclo para no ha sido declarada" );
                    System.exit( -1 );
                }
            } else {
                loopStep = Integer.parseInt( ctx.INT().getText() );
            }
            
            while ( visit( ctx.expression( expressionIndex ) ).toBoolean() ) {
                visitInstructions( ctx.instructions() );
                PsiCoderType updatedLoopVarId = null;
                if ( loopVarOutOfScope ) {
                    updatedLoopVarId = new PsiCoderType(
                        ( Integer ) ( ( PsiCoderType ) scope.getParentScope().searchId( loopVarId ).get() ).getValue() + loopStep
                    );
                    updatedLoopVarId.toInteger();
                    scope.getParentScope().add( loopVarId, Optional.of( updatedLoopVarId ) );
                } else {
                    updatedLoopVarId = new PsiCoderType(
                        ( Integer ) ( ( PsiCoderType ) scope.searchId( loopVarId ).get() ).getValue() + loopStep
                    );
                    updatedLoopVarId.toInteger();
                    scope.add( loopVarId, Optional.of( updatedLoopVarId ) );
                }
            }
        } else {
            System.err.printf( "Error semantico: La condición del ciclo 'para' debe evaluar a un booleano" );
            System.exit( -1 );
        }
        
        scope = scope.getParentScope();
        return null;
    }
    
    @Override
    public PsiCoderType visitMultSelection( PsiCoderParser.MultSelectionContext ctx ) {
        boolean ignoreMatch = false;
        PsiCoderType expression = visit( ctx.expression() );
        
        int i = 0;
        
        if ( ctx.CASE( i ) != null ) {
            do {
                if ( ignoreMatch || visitPrimitiveValue( ctx.primitiveValue( i ) ).equals( expression ) ) {
                    scope = new Scope( scope, false );
                    visitInstructions( ctx.instructions( i ) );
                    scope = scope.getParentScope();
                    if ( ctx.ROMPER() == null ) ignoreMatch = true;
                    else return null;
                }
            } while ( ctx.CASE( ++i ) != null );
            return ( ctx.defaultCase() != null ? visitDefaultCase( ctx.defaultCase() ) : null );
        } else
            return visitDefaultCase( ctx.defaultCase() );
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
        if ( function == null ) {
            System.err.printf( "Error semantico: la funcion " + ctx.ID().getText() + " no se "
                + "encuentra definida o el número de argumentos es inválido" );
            System.exit( -1 );
        }
        List<PsiCoderType> functionArguments = ctx.expression()
            .stream()
            .map( expressionContext -> visit( expressionContext ) )
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
            return new PsiCoderType( Character.valueOf( ctx.CHAR().getText().charAt( 1 ) ) );
        return new PsiCoderType( Boolean.valueOf( ctx.BOOLEAN().getText() ) );
    }
    
    @Override
    public PsiCoderType visitMinusExpression( PsiCoderParser.MinusExpressionContext ctx ) {
        PsiCoderType exprResult = visit( ctx.expression() );
        
        if ( exprResult.isInteger() ) return new PsiCoderType( -exprResult.toInteger() );
        if ( exprResult.isReal() ) return new PsiCoderType( -exprResult.toReal() );
        
        System.err.printf( "Error semantico, el operador '-' solo puede aplicarse a enteros y reales." );
        System.exit( -1 );
        return null;
    }
    
    @Override
    public PsiCoderType visitNegExpression( PsiCoderParser.NegExpressionContext ctx ) {
        PsiCoderType exprResult = visit( ctx.expression() );
        
        if ( !exprResult.isBoolean() ) {
            System.err.printf( "Error semantico, el operador '!' solo puede aplicarse a booleanos." );
            System.exit( -1 );
        }
        return new PsiCoderType( !exprResult.toBoolean() );
    }
    
    @Override
    public PsiCoderType visitMultExpression( PsiCoderParser.MultExpressionContext ctx ) {
        PsiCoderType leftExprResult = visit( ctx.expression( 0 ) );
        PsiCoderType rightExprResult = visit( ctx.expression( 1 ) );
        
        if ( ( !leftExprResult.isInteger() && !leftExprResult.isReal() )
            || ( !rightExprResult.isInteger() && !rightExprResult.isReal() ) ) {
            System.err.printf( "Error semantico, la multiplicacion, division y modulo solo pueden hacerse entre enteros y reales." );
            System.exit( -1 );
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
        
        if ( !leftExprResult.isNumber() || !rightExprResult.isNumber() ) {
            System.err.printf( "Error semantico, la suma y resta solo pueden hacerse entre enteros y reales,"
                + " y para la concatenacion uno de los operandos debe ser de tipo cadena" );
            System.exit( -1 );
        }
        
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
        
        if ( !leftExprResult.isBoolean() || !rightExprResult.isBoolean() ) {
            System.err.printf( "Error semantico, ambos terminos de la expresion logica 'or' deben ser de tipo booleano." );
            System.exit( -1 );
        }
        
        return new PsiCoderType( leftExprResult.toBoolean() && rightExprResult.toBoolean() );
    }
    
    @Override
    public PsiCoderType visitOrExpression( PsiCoderParser.OrExpressionContext ctx ) {
        PsiCoderType leftExprResult = visit( ctx.expression( 0 ) );
        PsiCoderType rightExprResult = visit( ctx.expression( 1 ) );
        
        if ( !leftExprResult.isBoolean() || !rightExprResult.isBoolean() ) {
            System.err.printf( "Error semantico, ambos terminos de la expresion logica 'and' deben ser de tipo booleano." );
            System.exit( -1 );
        }
        
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
    
    @Override
    public PsiCoderType visitIdExpression( PsiCoderParser.IdExpressionContext ctx ) {
        List<String> memberChain = ctx.ID().stream().map( id -> id.getText() ).collect( Collectors.toList() );
        String identifier = memberChain.get( 0 );
        
        int i = 1;
        if ( memberChain.size() > i )
            return scope.getStructMemberValue( memberChain ).get();
        
        Optional value = scope.searchId( identifier );
        if ( value == null ) {
            System.err.printf( "Error semantico, la variable " + identifier + " no ha sido declarada" );
            System.exit( -1 );
        } else if ( !( value.get() instanceof PsiCoderType ) ) {
            System.err.printf( "Error semantico: No se puede emplear la variable " + identifier + " de tipo estructura en una expresion" );
            System.exit( -1 );
        }
        return ( PsiCoderType ) value.get();
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
        else {
            System.err.printf( "Error semantico, la comparacion menor que solo puede hacerse entre enteros y reales o entre solo cadenas" );
            System.exit( -1 );
        }
        return null;
    }
    
    private PsiCoderType lessEqThan( PsiCoderType leftExpr, PsiCoderType rightExpr ) {
        if ( leftExpr.isString() && rightExpr.isString() )
            return new PsiCoderType( leftExpr.toStringType().compareTo( rightExpr.toStringType() ) <= 0 );
        else if ( leftExpr.isNumber() && rightExpr.isNumber() )
            return new PsiCoderType( leftExpr.toReal() <= rightExpr.toReal() );
        else {
            System.err.printf( "Error semantico, la comparacion menor igual que solo puede hacerse entre enteros y reales o entre solo cadenas" );
            System.exit( -1 );
        }
        return null;
    }
    
    private PsiCoderType greaterThan( PsiCoderType leftExpr, PsiCoderType rightExpr ) {
        if ( leftExpr.isString() && rightExpr.isString() )
            return new PsiCoderType( leftExpr.toStringType().compareTo( rightExpr.toStringType() ) > 0 );
        else if ( leftExpr.isNumber() && rightExpr.isNumber() )
            return new PsiCoderType( leftExpr.toReal() >= rightExpr.toReal() );
        else {
            System.err.printf( "Error semantico, la comparacion mayor que solo puede hacerse entre enteros y reales o entre solo cadenas" );
            System.exit( -1 );
        }
        return null;
    }
    
    private PsiCoderType greaterEqThan( PsiCoderType leftExpr, PsiCoderType rightExpr ) {
        if ( leftExpr.isString() && rightExpr.isString() )
            return new PsiCoderType( leftExpr.toStringType().compareTo( rightExpr.toStringType() ) >= 0 );
        else if ( leftExpr.isNumber() && rightExpr.isNumber() )
            return new PsiCoderType( leftExpr.toReal() >= rightExpr.toReal() );
        else {
            System.err.printf( "Error semantico, la comparacion mayor igual que solo puede hacerse entre enteros y reales o entre solo cadenas" );
            System.exit( -1 );
        }
        return null;
    }
}