package parser;

import scanner.Scanner;
import scanner.Token;
import util.AST.AST;

/**
 * Parser class
 * @version 2014-october-15
 * @discipline Projeto de Compiladores
 * @authors Bruno Bandeira e Bruno Bastos
 * @email (bcabm,babg)@ecomp.poli.br
 */



/*


program                  ::=    declarationList;

declarationList          ::=    'LET' definition ('AND' definition)*;

definition               ::=    functionDefinition
                         |    procedureDefinition
                         |    variableDefinition;

identifierList           ::=    Identifier (',' Identifier)*;

functionDefinition       ::=    Identifier '(' parametersPrototype ')' '=' 'VALOF' functionBlock;

functionBlock            ::=    '{' commandList resultisCommand '}';

procedureDefinition      ::=    Identifier '(' parametersPrototype ')' 'BE' commandBlock;

varGlobalDefinition      ::=    ('GLOBAL') (intVariableDefinition | boolVariableDefinition);


intVariableDefinition    ::=    ('INT' Identifier '=' numberExpression)*;

boolVariableDefinition   ::=    ('BOOL' Identifier '=' booleanExpression)*;

commandBlock             ::=    '{' commandList '}';

parametersPrototype      ::=   (('INT' | 'BOOL') Identifier ((, 'INT' | 'BOOL') Identifier)*)*


parametersCallCommand    ::=   (Identifier (, Identifier)*)* 


commandList              ::=    command (';' command)*;

command                  ::=    assignmentCommand
                         |    whileCommand
                         |    ifCommand
                         |    breakCommand
                         |    continueCommand
                         |    printCommand
                         |    variableDecCommand
                         |    callCommand;

callCommand              ::=     functionCallCommand
                         |      procedureCallCommand


variableDecCommand       ::=     intVariableDefinition
                         |      boolVariableDefinition

functionCallCommand      ::=    Identifier '('(parametersCallCommand)*')'

procedureCallCommand     ::=    Identifier '('(parametersCallCommand)*')'

ifCommand                ::=    'IF' '(' booleanExpression | booleanValue ')' commandBlock ('ELSE' commandBlock)?;

whileCommand             ::=    'WHILE' '(' ( booleanExpression | booleanValue ')' commandBlock;

resultisCommand          ::=    'RESULTIS' expression;

assignmentCommand        ::=    Identifier ':=' (number | booleanValue | numberExpression | functionCallCommand | Identifier);

breakCommand             ::=    'BREAK';

continueCommand          ::=    'CONTINUE';

printCommand             ::=    'WRITEF' '(' (Identifier)* ')';


numberBoolExpression     ::=     (number | Identifier) Op_relation ( number | Identifier) 


arithBoolExpression      ::= arithmeticExpression | numberBoolExpression


booleanExpression        ::= ('!')? (  andExpression | orExpression  | equalExpresion | notEqualExpression )


numberExpression         ::=  (multDivExpression)? arithmeticExpression
arithmeticExpression     ::=  multDivExpression
                         |   addSubExpression

                         
multDivExpression        ::=  (Identifier | number) ('/' | '*') (Identifier | number)

addSubExpression         ::=  (Identifier | number) ('+' | '-') (Identifier | number)                         

andExpression            ::=  ( numberBoolExpression |(arithmeticExpression op_rel arithmeticExpression) | booleanValue ) '&&' ( numberBoolExpression |(arithmeticExpression op_rel arithmeticExpression) | booleanValue)
                         
orExpression             ::=  ( numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression | number)) | booleanValue ) '||' ( numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression | number)) | booleanValue )

equalExpression          ::=  (numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression | number)) | booleanValue  ) '==' (numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression | number)) | booleanValue )

notEqualExpression       ::=  (numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression | number)) | booleanValue ) '!=' (numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression | number)) | booleanValue )
                         

booleanValue            ::= 'TRUE'
                        |  'FALSE'




*/


public class Parser {

	// The current token
	private Token currentToken = null;
	// The scanner
	private Scanner scanner = null;
	
	/**
	 * Parser constructor
	 */
	public Parser() {
		// Initializes the scanner object
		this.scanner = new Scanner();
	}
	
	/**
	 * Veririfes if the current token kind is the expected one
	 * @param kind
	 * @throws SyntacticException
	 */ //TODO

        private void accept(int kind) throws SyntacticException {
		// If the current token kind is equal to the expected
            if(this.currentToken.equals(kind)){
			// Gets next token
                this.currentToken = this.scanner.getNextToken();
                
            }
		// If not
            else{
			// Raises an exception
                throw new SyntacticException(null, currentToken);
            }
	}
	
	/**
	 * Gets next token
	 */ //TODO
	private void acceptIt() {
            this.currentToken = this.scanner.getNextToken();
	}

	/**
	 * Verifies if the source program is syntactically correct
	 * @throws SyntacticException
	 */ //TODO
	public AST parse() throws SyntacticException {
            this.parseProgram();
		return null;
	}
        
        
        private void parseProgram(){
            
        }
        /*
        * declarationList          ::=    'LET' definition ('AND' definition)*;
                                    |      varGlobalDefinition
        */
       
        
        private void parseDeclarationList() throws SyntacticException{
            switch(this.currentToken.getKind()){
                case Token.LET:
                    acceptIt();
                    parseDefinition();
                    while(this.currentToken.getKind() == Token.AND){
                        acceptIt();
                        parseDefinition();
                    }
                
                break;
                
                case Token.VARGLODEF:
                    acceptIt();
                    parseVarGlobalDefinition();
                break;
                    
                default:
                    throw new SyntacticException(null, currentToken);
                    
                    
            }
            
        }
             
        /*
        definition               ::=    functionDefinition
                         |    procedureDefinition
                         |    variableDefinition;

        
        */
        
        private void parseDefinition(){
            switch(this.currentToken.getKind()){
                
                case Token.FUNCDEF:
                    parseFunctionDefiniton();
                    break;
                
                case Token.PROCDEF:
                    parseProcedureDefinition();
                    break;
                    
                case Token.VARDEF:
                    parseVarGlobalDefinition();
                           
            }
        }
        
        private void parseFunctionDefiniton(){
        }
        
        private void parseProcedureDefinition(){
        }
        
        private void parseVariableDefinitnion(){
        }
        
        private void parseVarGlobalDefinition(){
        }
        
        private void parseIntVariableDefinition(){
        
        }
        
        private void parseBoolVariableDefinition(){
        
        }
        
        private void parseFunctionBlock(){
            
        }
        
        private void parseIdentifierList(){
        }
        
        private void parseParametersPrototype(){
        }
        
        private void parseCommandBlock(){
        }
        
        private void parseCommandList(){
        }
        
        private void parseParametersCallCommand(){
        }
        
        private void parseCommand(){
        }
        
        private void parseAssignmentCommand(){
        }
        
        private void parseWhileCommand(){
        }
        
        private void parseIfCommand(){
        }
        
        private void parseBreakCommand(){
        }
        
        private void parseContinueCommand(){
        }
        
        private void parsePrintCommand(){
        }
        
        private void parseVariableDecCommand(){
        }
        
        private void parseCallCommand(){
        }
        
        private void parseFunctionCallCommand(){
        }
        
        private void parseProcedureCallCommand(){
        }
        
        private void parseResultIsCommand(){
        }
        
        private void parseNumberBoolExpression(){
            
        }
        
        private void parseArithmeticBoolExpression(){
        }
        
        private void parseBoolExpression(){
        }
        
        private void parseNumberExpression(){
        }
        
        private void parseArithmeticExpression(){
        }
        
        private void parseMultiDivExpression(){
        }
        
        private void parseAddSubExpression(){
        }
        
        private void parseAndExpression(){
        }
        
        private void parseOrExpression(){
        }
        
        private void parseEqualExpression(){
        }
        
        private void parseNotEqualExpression(){
        }
        
        
        
 
}
