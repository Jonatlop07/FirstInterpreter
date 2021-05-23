import java.util.Scanner;

public class PsiCoderVisitorImpl extends PsiCoderBaseVisitor<PsiCoderType> {
    private SymbolTable table = new SymbolTable( null );
    
    public PsiCoderType visitInstructions( PsiCoderParser.InstructionsContext ctx ) {
        
    }
    
    public PsiCoderType visitRead( PsiCoderParser.ReadContext ctx ) {
        int i = 0;
        if ( ctx.DOT( i ) != null ) {
            while ( ctx.DOT( i ) != null ) {
                /*Mirar si la estructura (clase) tiene dicha variable miembro
                y llevar control de esta*/
            }
        }
        
        Scanner in = new Scanner( System.in );
        table.updateValue( "" /*identificador*/, in.next() );
        
        return null;
    }
    
    
    public PsiCoderType visitPrint( PsiCoderParser.PrintContext ctx ) {
        String toPrint = "";
        int i = 0;
        
        while ( ctx.expression( i ) != null ) {
            toPrint += visitExpression( ctx.expression( i ) );
            i++;
        }
        
        System.out.println( toPrint );
        
        return null;
    }
    
    
    public PsiCoderType visitConditional( PsiCoderParser.ConditionalContext ctx ) {
        visitExpression( ctx.expression() );
        visitInstructions( ctx.instructions( 0 ) );
        
        if ( ctx.THEN() != null ) {
            visitInstructions( ctx.instructions( 1 ) );
        }
        
        return null;
    }
    
    public PsiCoderType visitWhileLoop( PsiCoderParser.WhileLoopContext ctx ) {
        while ( ( Boolean ) visitExpression( ctx.expression() ) ) {
            visitInstructions( ctx.instructions() );
        }
        
        return null;
    }
    
    public PsiCoderType visitDoWhile( PsiCoderParser.DoWhileContext ctx ) {
        do {
            visitInstructions( ctx.instructions() );
        } while ( ( Boolean ) visitExpression( ctx.expression() ) );
        
        return null;
    }
    
    public PsiCoderType visitForLoop( PsiCoderParser.ForLoopContext ctx ) {
        PsiCoderType loopVarValue = visitExpression( ctx.expression( 0 ) );
        
        if ( ctx.INTEGER() != null ) {
            table.addSymbol( ctx.ID( 0 ).getText(), loopVarValue, ctx.INTEGER().getText() );
        } else {
            //Modificar el valor de la variable
        }
        
        Boolean flagExpression = ;
        Integer step;
        
        if ( ctx.ID() != null ) {
            //Asignar el valor de la variable a step
        } else {
            step = Integer.valueOf( ctx.ID( 1 ).getText() );
        }
        
        while ( ( Boolean ) visitExpression( ctx.expression( 1 ) ) ) {
            visitInstructions( ctx.instructions() );
            //Aumentar la variable una cantidad igual a step
        }
    }
    
    public PsiCoderType visitMultSelection( PsiCoderParser.MultSelectionContext ctx ) {
        PsiCoderType expression = visitExpression( ctx.expression() );
        
        int i = 0;
        
        if ( ctx.CASE( i ) != null ) {
            if ( visitValue( ctx.value( i ) ).equals( expression ) ) {
                visitInstructions( ctx.instructions( ctx.instructions().size() - 1 ) );
            }
            
            while ( ctx.CASE( ++i ) != null ) {
                visitInstructions( ctx.instructions( i ) );
            }
            
            if ( ctx.defaultCase() != null ) {
                visitDefaultCase( ctx.defaultCase() );
            }
        } else {
            visitDefaultCase( ctx.defaultCase() );
        }
    }
    
    public PsiCoderType visitDefaultCase( PsiCoderParser.DefaultCaseContext ctx ) {
        if ( ctx.ROMPER() != null ) {
            visitInstructions( ctx.instructions() );
            return null;
        }
        
        return visitInstructions( ctx.instructions() );
    }
    
    public PsiCoderType visitFunctionCall( PsiCoderParser.FunctionCallContext ctx ) {
        
    }
    
    public PsiCoderType visitValue( PsiCoderParser.ValueContext ctx ) {
        if ( ctx.ID() != null ) {
            
        }
        
        if ( ctx.INT() != null ) {
            return new PsiCoderType( Integer.valueOf( ctx.INT().getText() ) );
        }
        
        if ( ctx.REAL() != null ) {
            return new PsiCoderType( Double.valueOf( ctx.REAL().getText() ) );
        }
        
        if ( ctx.STRING() != null ) {
            return new PsiCoderType( ctx.STRING().getText() );
        }
        
        if ( ctx.CHAR() != null ) {
            return new PsiCoderType( Character.valueOf( ctx.CHAR().getText().charAt( 0 ) ) );
        }
        
        if ( ctx.BOOLEAN() != null ) {
            return new PsiCoderType( Boolean.valueOf( ctx.BOOLEAN().getText() ) );
        }
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
            if ( rightExprResult.isString() ) {
                return new PsiCoderType( leftExprResult.toString() + "" + rightExprResult.toStringType() );
            }
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
    public PsiCoderType visitIntValExpression( PsiCoderParser.IntValExpressionContext ctx ) {
        return new PsiCoderType( Integer.valueOf( ctx.INT().getText() ) );
    }
    
    @Override
    public PsiCoderType visitRealValExpression( PsiCoderParser.RealValExpressionContext ctx ) {
        return new PsiCoderType( Double.valueOf( ctx.REAL().getText() ) );
    }
    
    @Override
    public PsiCoderType visitBooleanValExpression( PsiCoderParser.BooleanValExpressionContext ctx ) {
        return new PsiCoderType( Boolean.valueOf( ctx.BOOLEAN().getText() ) );
    }
    
    @Override
    public PsiCoderType visitCharValExpression( PsiCoderParser.CharValExpressionContext ctx ) {
        return new PsiCoderType( Character.valueOf( ctx.CHAR().getText().charAt( 0 ) ) );
    }
    
    @Override
    public PsiCoderType visitStringValExpression( PsiCoderParser.StringValExpressionContext ctx ) {
        return new PsiCoderType( ctx.STRING().getText() );
    }
    
    @Override
    public PsiCoderType visitIdExpression( PsiCoderParser.IdExpressionContext ctx ) {
        String identifier = ctx.ID( 0 ).getText();
        
        if ( ctx.DOT( 0 ) != null ) {
            //PENDIENTE
        }
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
    
    /*public T visitExpression( PsiCoderParser.ExpressionContext ctx ) {
        T expression1 = visitExpression( ctx.expression() );
        T expression2 = visitAndExpression( ctx.andExpression() );
        
        if ( expression1 != null ) {
            return ( T ) ( Boolean ) ( ( Boolean ) expression1 || ( Boolean ) expression2 ));
        }
        return expression2;
    }
    
    public T visitAndExpression( PsiCoderParser.AndExpressionContext ctx ) {
        T expression1 = visitAndExpression( ctx.andExpression() );
        T expression2 = visitEqualityExpression( ctx.equalityExpression() );
        
        if ( expression1 != null ) {
            return ( T ) ( ( Boolean ) ( ( Boolean ) expression1 && ( Boolean ) expression2 ) );
        }
        return expression2;
    }
    
    public T visitEqualityExpression( PsiCoderParser.EqualityExpressionContext ctx ) {
        T expression1 = visitEqualityExpression( ctx.equalityExpression() );
        T expression2 = visitComparisonExpression( ctx.comparisonExpression() );
        
        if ( expression1 != null ) {
            Boolean expression;
            
            if ( ctx.EQUAL_OP().getText().equals( "==" ) ) {
                expression = expression1.equals( expression2 );
            } else {
                expression = !expression1.equals( expression2 );
            }
            
            return ( T ) expression;
        }
        return expression2;
    }
    
    public T visitComparisonExpression( PsiCoderParser.ComparisonExpressionContext ctx ) {
        T expression1 = visitComparisonExpression( ctx.comparisonExpression() );
        T expression2 = visitAditionExpression( ctx.aditionExpression() );
        
        if ( expression1 != null ) {
            Boolean expression;
            
            if ( ctx.COMP_OP().getText().equals( "<" ) ) {
                expression = expression1 < expression2;
            } else if ( ctx.COMP_OP().getText().equals( "<=" ) ) {
                expression = expression1 <= expression2;
            } else if ( ctx.COMP_OP().getText().equals( ">" ) ) {
                expression = expression1 > expression2;
            } else {
                expression = expression1 >= expression2;
            }
            
            return ( T ) expression;
        }
        
        return expression2;
    }
    
    public T visitAditionExpression( PsiCoderParser.AditionExpressionContext ctx ) {
        T expression1 = visitAditionExpression( ctx.aditionExpression() );
        T expression2 = visitMultExpression( ctx.multExpression() );
        
        if ( expression1 != null ) {
            if ( ctx.AD_OP().getText().equals( "+" ) ) {
                return expression1 + expression2;
            } else {
                return expression1 - expression2;
            }
        }
        
        return expression2;
    }
    
    public T visitMultExpression( PsiCoderParser.MultExpressionContext ctx ) {
        T expression1 = visitMultExpression( ctx.multExpression() );
        T factor = visitFactor( ctx.factor() );
        
        if ( expression1 != null ) {
            if ( ctx.MULT_OP().getText().equals( "*" ) ) {
                return ( T ) expression1 * factor;
            } else {
                return ( T ) expression1 / factor;
            }
        }
        
        return factor;
    }
    
    @Override
    public T visitFactor( PsiCoderParser.FactorContext ctx ) {
        if ( ctx.BOOLEAN() != null ) {
            Boolean bool = Boolean.valueOf( ctx.BOOLEAN().getText() );
            return ( T ) bool;
        }
        
        if ( ctx.INT() != null ) {
            Integer num = Integer.valueOf( ctx.INT().getText() );
            return ( T ) ( ( ctx.MINUS() != null ) ? Integer.valueOf( -num ) : num );
        }
        
        if ( ctx.CHAR() != null ) {
            return ( T ) Character.valueOf( ctx.CHAR().getText().charAt( 0 ) );
        }
        
        if ( ctx.STRING() != null ) {
            return ( T ) ctx.STRING().getText();
        }
        
        if ( ctx.REAL() != null ) {
            Double num = Double.valueOf( ctx.REAL().getText() );
            return ( T ) ( ( ctx.MINUS() != null ) ? Double.valueOf( -num ) : num );
        }
        
        if ( ctx.NEG() != null ) {
            Boolean expression = ( Boolean ) visitFactor( ctx );
            return ( T ) expression;
        }
        
        if ( ctx.LEFT_PAR() != null ) {
            return visitExpression( ctx.expression() );
        }
        
        if ( ctx.ID() != null ) {
            String name = ctx.ID( 0 ).getText();
            Object value;
            
            if ( !table.hasSymbol( name ) ) {
                int line = ctx.ID( 0 ).getSymbol().getLine();
                int col = ctx.ID( 0 ).getSymbol().getCharPositionInLine() + 1;
                
                System.err.printf( "<%d:%d> Error semantico, la variable con nombre \"" + name + "\" no fue declarada.\n", line, col );
                System.exit( -1 );
            }
            
            value = table.getValue( name );
            
            if ( ctx.DOT( 0 ) != null ) {
                String memberName = ctx.ID( 1 ).getText();
                
                
            } else if ( ctx.LEFT_PAR() != null ) {
            } else return ( T ) value;
        }
    }*/
}
