grammar PsiCoder;
import PsiCoderLexerRules;

program: (structDeclaration | functionDeclaration)* 'funcion_principal' instruction* 'fin_principal'
         (structDeclaration | functionDeclaration)* EOF;

structDeclaration: 'estructura' ID (variableDeclaration | ID ID (COMMA ID)*)* 'fin_estructura';

functionDeclaration: 'funcion' dataType ID LEFT_PAR (dataType ID (COMMA dataType ID)*)? RIGHT_PAR
                      'hacer' instruction* returnExpression 'fin_funcion';

dataType: DATA_TYPE | ID;

returnExpression: 'retornar' expression ';';

variableDeclaration: DATA_TYPE ID variableAssignment? (COMMA ID variableAssignment)* ';';

variableAssignment: (DOT ID)* ASSIGN expression;

instructions: instruction instructions | ;

instruction: variableDeclaration #instructionVarDec
            | ID ID (COMMA ID)* ';' #instructionStructDeclaration
            | functionCall ';' #instructionFunctionCall
            | ID variableAssignment (COMMA ID variableAssignment)* ';' #instructionAsig
            | whileLoop #instructionWhileLoop
            | doWhile #instructionDoWhile
            | forLoop #instructionForLoop
            | multSelection #instructionMultSelect
            | conditional #instructionConditional
            | read #instructionRead
            | print #instructionPrint
            | returnExpression #instructionReturn ;

read: 'leer' '(' ID (DOT ID)* ')' ';';

print: 'imprimir' '(' expression (COMMA expression)* ')' ';';

conditional: 'si' '(' expression ')' 'entonces' instructions (THEN instructions)? 'fin_si';

whileLoop: 'mientras' '(' expression ')' 'hacer' instructions 'fin_mientras';

doWhile: 'hacer' instructions 'mientras' '(' expression ')' ';';

forLoop: 'para' '(' INTEGER? ID ASSIGN expression ';' expression ';' (ID | INT) ')' 
         'hacer' instructions 'fin_para';

multSelection: 'seleccionar' '(' expression ')' 'entre'
                (
                  (CASE value ':' instructions)+ defaultCase?
                  | defaultCase
                ) 'fin_seleccionar';

defaultCase: 'default' ':' instructions (ROMPER ';');

functionCall: ID '(' (expression (COMMA expression)*)? ')';

value: ID | INT | REAL | STRING | CHAR | BOOLEAN;

expression: MINUS expression #minusExpression
          | NEG expression #negExpression
          | expression MULT_OP expression #multExpression
          | expression AD_OP expression #adExpression
          | expression COMP_OP expression #compExpression
          | expression EQUAL_OP expression #eqExpression
          | expression AND expression #andExpression
          | expression OR expression #orExpression
          | '(' expression ')' #nestedExpression
          | INT #intValExpression
          | REAL #realValExpression
          | BOOLEAN #booleanValExpression
          | CHAR #charValExpression
          | STRING #stringValExpression
          | ID (DOT ID)* #idExpression
          | functionCall #functionCallExpression;

/*factor: BOOLEAN | MINUS? (INT | REAL) | CHAR | STRING | NEG factor
      | ID ((DOT ID)* | (LEFT_PAR (expression (COMMA expression)*)? RIGHT_PAR)?) | LEFT_PAR expression RIGHT_PAR;*/