package parser;

import scanner.Scanner;
import scanner.Token;
import util.AST.AST;

/**
 * Parser class
 * @version 2010-august-29
 * @discipline Projeto de Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
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
			// Gets next token
		// If not
			// Raises an exception
	}
	
	/**
	 * Gets next token
	 */ //TODO
	private void acceptIt() {
	}

	/**
	 * Verifies if the source program is syntactically correct
	 * @throws SyntacticException
	 */ //TODO
	public AST parse() throws SyntacticException {
            this.parseProgram();
		return null;
	}
        //program                  ::=    declarationList;
	private void parseProgram() throws SyntacticException {
                       
        }
        
        
        /*
        declarationList          ::=    'LET' definition ('AND' definition)*;
                         |      varGlobalDefinition
        
        */
        private void parseDeclarationList() throws SyntacticException{
        }
        /*
        definition               ::=    functionDefinition
                         |    procedureDefinition
                         |    variableDefinition;
        */
        private void parseDefinition(){}
        
        private void parseVarGlobalDefinition(){}
        /*
        functionDefinition       ::=    Identifier '(' parametersPrototype ')' '=' 'VALOF' functionBlock;
        
        */
        private void parseFunctionDefinition(){}
        
        /*
        procedureDefinition      ::=    Identifier '(' parametersPrototype ')' 'BE' commandBlock;
        */
        
        private void parseProcedureDefiniton(){}
        /*
        variableDefinition      ::=    intVariableDefinition
                        |       
        */
        private void parseVariableDefinition(){}
        
        /*
        functionBlock            ::=    '{' commandList resultisCommand '}';
        */
        private void parseFunctionBlock(){}
        
        /*
        parametersPrototype      ::=   (('INT' | 'BOOL') Identifier ((, 'INT' | 'BOOL') Identifier)*)*
        */
        
        private void parseParametersPrototype(){}
        
        /*
        
        */
        private void parse(){}
        private void parse(){}
        private void parse(){}
        private void parse(){}
        private void parse(){}
        private void parse(){}
        private void parse(){}
        private void parse(){}
        private void parse(){}
        private void parse(){}
        private void parse(){}
        private void parse(){}
        private void parse(){}
        private void parse(){}
        
        
        
                
        
        
        
        /*
        
                         


identifierList           ::=    Identifier (',' Identifier)*;







varGlobalDefinition      ::=    ('GLOBAL') (intVariableDefinition | boolVariableDefinition);


intVariableDefinition    ::=    ('INT' Identifier '=' numberExpression)*;

boolVariableDefinition   ::=    ('BOOL' Identifier '=' booleanExpression)*;

commandBlock             ::=    '{' commandList '}';




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
 
}
