grammar PsiCoder;
import PsiCoderLexerRules;

program: globalDeclaration? 'funcion_principal' instructions 'fin_principal'
         globalDeclaration? EOF;

globalDeclaration: (structDeclaration | functionDeclaration)+;

structDeclaration: 'estructura' ID structMember+ 'fin_estructura';
                    
structMember: DATA_TYPE ID (',' ID)* ';'
            | ID ID (',' ID)* ';' ;

functionDeclaration: 'funcion' DATA_TYPE ID '(' (DATA_TYPE ID (',' DATA_TYPE ID)*)? ')'
                      'hacer' instructions returnExpression 'fin_funcion';

returnExpression: 'retornar' expression ';';

variableDeclaration: ID ('=' expression)?;

variableAssignment: ID (DOT ID)* '=' expression;

structInstantiation: ID ID (',' ID)* ';';

instructions: instruction instructions | ;

instruction:  DATA_TYPE variableDeclaration (',' variableDeclaration)* ';'
            | structInstantiation
            | functionCall ';'
            | variableAssignment (',' variableAssignment)* ';'
            | whileLoop
            | doWhile
            | forLoop
            | multSelection
            | conditional
            | read
            | print
            | returnExpression;

read: 'leer' '(' ID ('.' ID)* ')' ';';

print: 'imprimir' '(' expression (',' expression)* ')' ';';

conditional: 'si' '(' expression ')' 'entonces' instructions (THEN instructions)? 'fin_si';

whileLoop: 'mientras' '(' expression ')' 'hacer' instructions 'fin_mientras';

doWhile: 'hacer' instructions 'mientras' '(' expression ')' ';';

forLoop: 'para' '(' (DATA_TYPE? ID ('=' expression)?) ';' expression ';' (ID | INT) ')' 
         'hacer' instructions 'fin_para';
         
multSelection: 'seleccionar' '(' expression ')' 'entre'
                (
                  (CASE primitiveValue ':' instructions ROMPER?)+ defaultCase?
                  | defaultCase
                ) 'fin_seleccionar';

defaultCase: 'default' ':' instructions ';';

functionCall: ID '(' (expression (',' expression)*)? ')';

primitiveValue: INT
              | REAL
              | STRING
              | CHAR
              | BOOLEAN;

expression: MINUS expression #minusExpression
          | NEG expression #negExpression
          | expression MULT_OP expression #multExpression
          | expression AD_OP expression #adExpression
          | expression COMP_OP expression #compExpression
          | expression EQUAL_OP expression #eqExpression
          | expression AND expression #andExpression
          | expression OR expression #orExpression
          | '(' expression ')' #nestedExpression
          | primitiveValue #primitiveValExpression
          | ID ('.' ID)* #idExpression
          | functionCall #functionCallExpression;