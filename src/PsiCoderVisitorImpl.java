import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class PsiCoderVisitorImpl extends PsiCoderBaseVisitor<PsiCoderType> {
    private Map<String, Function> functions = new HashMap<>();
    private Map<String, Struct> structs = new HashMap<>();
    private Scope scope = new Scope( null, false );
    
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
    
    private PsiCoderType getDefaultValueByDataType( String dataType ) {
        PsiCoderType result = new PsiCoderType( null );
        
        switch ( dataType ) {
            case "booleano":
                result = new PsiCoderType( ( Boolean ) null );
                break;
            case "caracter":
                result = new PsiCoderType( ( Character ) null );
                break;
            case "entero":
                result = new PsiCoderType( ( Integer ) null );
                break;
            case "real":
                result = new PsiCoderType( ( Double ) null );
                break;
            case "cadena":
                result = new PsiCoderType( ( String ) null );
                break;
        }
        return result;
    }
    
    @Override
    public PsiCoderType visitStructDeclaration( PsiCoderParser.StructDeclarationContext ctx ) {
        String structId = ctx.ID().getText();
        
        if ( !structs.containsKey( structId ) ) {
            structs.put( structId, new Struct() );
            ctx.structMember().forEach( structMemberContext -> visitStructMember( structMemberContext, structId ) );
        } else {
            //Error semantico: Una estructura con el mismo identificador ha sido declarada
        }
        return null;
    }
    
    public PsiCoderType visitStructMember( PsiCoderParser.StructMemberContext ctx, String structId ) {
        if ( ctx.DATA_TYPE() != null ) {
            String dataType = ctx.DATA_TYPE().getText();
            PsiCoderType defaultValue = getDefaultValueByDataType( dataType );
            ctx.ID()
                .stream()
                .map( memberIdNode -> memberIdNode.getText() )
                .forEach( memberId -> {
                    if ( structs.get( structId ).getAtomic().containsKey( memberId ) ) {
                        structs.get( structId ).getAtomic().put( memberId, defaultValue );
                    } else {
                        //Error semantico: Miembro de la estructura 'structId', 'memberId' ya definido
                    }
                } );
        } else {
            String structMemberId = ctx.ID().remove( 0 ).getText();
            if ( !structs.containsKey( structMemberId ) ) {
                //Error semantico: La estructura 'structMemberId' no se encuentra declarada
            }
            ctx.ID()
                .stream()
                .map( memberIdNode -> memberIdNode.getText() )
                .forEach( memberId -> {
                    if ( structs.get( structId ).getCompound().containsKey( memberId )) {
                        structs.get( structId ).getCompound().put( memberId, structMemberId );
                    } else {
                        //Error semantico: Miembro de la estructura 'structId', 'memberId' ya definido
                    }
                } );
        }
        return null;
    }
    
    @Override
    public PsiCoderType visitFunctionDeclaration( PsiCoderParser.FunctionDeclarationContext ctx ) {
        
    }
    
    /*@Override
    public PsiCoderType visitDataType( PsiCoderParser.DataTypeContext ctx ) {
        
    }*/
    
    @Override
    public PsiCoderType visitReturnExpression( PsiCoderParser.ReturnExpressionContext ctx ) {
        
    }
    
    public PsiCoderType visitVariableDeclaration( PsiCoderParser.VariableDeclarationContext ctx, String dataType ) {
        String identifier = ctx.ID().getText();
        
        if ( scope.searchId( identifier, false ) == null ) {
            if ( ctx.expression() != null ) {
                PsiCoderType value = visit( ctx.expression() );
                
                if ( dataType.equals( "booleano" ) && value.isBoolean() ) {
                    scope.add( identifier, new PsiCoderType( value.toBoolean() ) );
                } else if ( dataType.equals( "caracter" ) && value.isCharacter() ) {
                    scope.add( identifier, new PsiCoderType( value.toCharacter() ) );
                } else if ( dataType.equals( "entero" ) && value.isInteger() ) {
                    scope.add( identifier, new PsiCoderType( value.toInteger() ) );
                } else if ( dataType.equals( "real" ) && value.isReal() ) {
                    scope.add( identifier, new PsiCoderType( value.toReal() ) );
                } else if ( dataType.equals( "cadena" ) && value.isString() ) {
                    scope.add( identifier, new PsiCoderType( value.toStringType() ) );
                } else {
                    // Error semantico, se esperaba un valor de tipo 'dataType'
                }
            } else
                scope.add( identifier, getDefaultValueByDataType( dataType ) );
        } else {
            // Error semantico, variable ya declarada
        }
        
        return null;
    }
    
    @Override
    public PsiCoderType visitVariableAssignment( PsiCoderParser.VariableAssignmentContext ctx ) {
        String identifier = ctx.ID( 0 ).getText();
        PsiCoderType value = visit( ctx.expression() );
        
        if ( ctx.ID().size() > 1 ) {
            for ( int i = 1; i < ctx.ID().size(); ++i ) {
                /*Mirar si la estructura (clase) tiene dicha variable miembro
                y llevar control de esta*/
            }
        } else {
            if ( scope.searchId( identifier ) != null ) scope.add( identifier, value );
            else {
                //Error semantico, variable no declarada
            }
        }
        return null;
    }
    
    @Override
    public PsiCoderType visitStructInstantiation( PsiCoderParser.StructInstantiationContext ctx ) {
        String structName = ctx.ID( 0 ).getText();
        
        for ( int i = 1; i < ctx.ID().size(); ++i ) {
            scope.searchStruct( ctx.ID( i ).getText() );
            scope.addStruct( ctx.ID( i ).getText() );
        }
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
    
    @Override
    public PsiCoderType visitRead( PsiCoderParser.ReadContext ctx ) {
        Scanner in = new Scanner( System.in );
        String identifier = ctx.ID( 0 ).getText();
        
        if ( ctx.ID().size() > 1 ) {
            for ( int i = 1; i < ctx.ID().size(); ++i ) {
                /*Mirar si la estructura (clase) tiene dicha variable miembro
                y llevar control de esta*/
            }
        } else
            scope.add( identifier, new PsiCoderType( in.next() ) );
        
        return null;
    }
    
    @Override
    public PsiCoderType visitPrint( PsiCoderParser.PrintContext ctx ) {
        int i = 0;
        String toPrint = "";
        
        do {
            toPrint += String.valueOf( visit( ctx.expression( i ) ).getValue() );
        } while ( ctx.expression( ++i ) != null );
        
        System.out.println( toPrint );
        return null;
    }
    
    @Override
    public PsiCoderType visitConditional( PsiCoderParser.ConditionalContext ctx ) {
        if ( visit( ctx.expression() ).isBoolean() ) {
            scope = new Scope( scope, false );
            
            if ( ( Boolean ) visit( ctx.expression() ).getValue() )
                visitInstructions( ctx.instructions( 0 ) );
            else if ( ctx.THEN() != null )
                visitInstructions( ctx.instructions( 0 ) );
            
            scope = scope.getParentScope();
        } else {
            //Error semantico
        }
        return null;
    }
    
    @Override
    public PsiCoderType visitWhileLoop( PsiCoderParser.WhileLoopContext ctx ) {
        if ( visit( ctx.expression() ).isBoolean() ) {
            scope = new Scope( scope, false );
            
            while ( ( Boolean ) visit( ctx.expression() ).getValue() )
                visitInstructions( ctx.instructions() );
            
            scope = scope.getParentScope();
        } else {
            // Error semantico
        }
        return null;
    }
    
    @Override
    public PsiCoderType visitDoWhile( PsiCoderParser.DoWhileContext ctx ) {
        if ( visit( ctx.expression() ).isBoolean() ) {
            scope = new Scope( scope, false );
            
            do
                visitInstructions( ctx.instructions() );
            while ( ( Boolean ) visit( ctx.expression() ).getValue() );
            
            scope = scope.getParentScope();
        } else {
            // Error semantico
        }
        return null;
    }
    
    @Override
    public PsiCoderType visitForLoop( PsiCoderParser.ForLoopContext ctx ) {
        scope = new Scope( scope, false );
        
        int expressionIndex = 0;
        String loopVarId = ctx.ID( 0 ).getText();
        
        if ( ctx.INTEGER() != null || ( ctx.expression().size() > 1 ) ) {
            PsiCoderType loopVarValue = visit( ctx.expression( expressionIndex++ ) );
            if ( loopVarValue.isInteger() ) {
                if ( ctx.INTEGER() != null ) {
                    scope.add( loopVarId, loopVarValue );
                } else {
                    scope.getParentScope().add( loopVarId, loopVarValue );
                }
            } else {
                //Error semantico
            }
        }
        
        int index = ( Integer ) scope.searchId( loopVarId ).getValue();
        
        if ( visit( ctx.expression( expressionIndex ) ).isBoolean() ) {
            int loopStep = ( ctx.ID( 1 ) != null )
                ? ( Integer ) scope.searchId( ctx.ID( 1 ).getText() ).getValue()
                : Integer.parseInt( ctx.INTEGER().getText() );
            
            while ( ( Boolean ) visit( ctx.expression( expressionIndex ) ).getValue() ) {
                visitInstructions( ctx.instructions() );
                PsiCoderType updatedLoopVarId = new PsiCoderType(
                    ( Integer ) scope.searchId( loopVarId ).getValue() + loopStep
                );
                updatedLoopVarId.toInteger();
                scope.add( loopVarId, updatedLoopVarId );
            }
        } else {
            //Error semantico
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
        ctx.ID();
        int i = 0;
        if ( ctx.expression( i ) != null ) {
            do {
                //Evaluar argumentos y tipo de retorno
                visit( ctx.expression( i ) );
            } while ( ctx.expression( ++i ) != null );
        }
        return null;
    }
    
    @Override
    public PsiCoderType visitPrimitiveValue( PsiCoderParser.PrimitiveValueContext ctx ) {
        if ( ctx.INT() != null )
            return new PsiCoderType( Integer.valueOf( ctx.INT().getText() ) );
        if ( ctx.REAL() != null )
            return new PsiCoderType( Double.valueOf( ctx.REAL().getText() ) );
        if ( ctx.STRING() != null )
            return new PsiCoderType( ctx.STRING().getText() );
        if ( ctx.CHAR() != null )
            return new PsiCoderType( Character.valueOf( ctx.CHAR().getText().charAt( 0 ) ) );
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
        
        if ( ( !leftExprResult.isInteger() && !leftExprResult.isReal() )
            || ( !rightExprResult.isInteger() && !rightExprResult.isReal() ) ) {
            System.err.printf( "Error semantico, la suma y resta solo pueden hacerse entre enteros y reales,"
                + " y para la concatenacion uno de los operandos debe ser de tipo cadena" );
            System.exit( -1 );
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
    public PsiCoderType visitPrimitiveValExpression( PsiCoderParser.PrimitiveValExpressionContext ctx ) {
        return visitPrimitiveValue( ctx.primitiveValue() );
    }
    
    @Override
    public PsiCoderType visitIdExpression( PsiCoderParser.IdExpressionContext ctx ) {
        String identifier = ctx.ID( 0 ).getText();
        
        if ( ctx.DOT( 0 ) != null ) {
            //PENDIENTE
        }
        
        PsiCoderType value = scope.searchId( identifier );
        
        return value;
    }
    
    @Override
    public PsiCoderType visitFunctionCallExpression( PsiCoderParser.FunctionCallExpressionContext ctx ) {
        return visitFunctionCall( ctx.functionCall() );
    }
    
    private PsiCoderType lessThan( PsiCoderType leftExpr, PsiCoderType rightExpr ) {
        if ( leftExpr.isString() && rightExpr.isString() )
            return new PsiCoderType( leftExpr.toStringType().compareTo( rightExpr.toStringType() ) < 0 );
        else if ( ( leftExpr.isInteger() || leftExpr.isReal() ) && ( rightExpr.isInteger() || rightExpr.isReal() ) )
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
        else if ( ( leftExpr.isInteger() || leftExpr.isReal() ) && ( rightExpr.isInteger() || rightExpr.isReal() ) )
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
        else if ( ( leftExpr.isInteger() || leftExpr.isReal() ) && ( rightExpr.isInteger() || rightExpr.isReal() ) )
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
        else if ( ( leftExpr.isInteger() || leftExpr.isReal() ) && ( rightExpr.isInteger() || rightExpr.isReal() ) )
            return new PsiCoderType( leftExpr.toReal() >= rightExpr.toReal() );
        else {
            System.err.printf( "Error semantico, la comparacion mayor igual que solo puede hacerse entre enteros y reales o entre solo cadenas" );
            System.exit( -1 );
        }
        return null;
    }
}