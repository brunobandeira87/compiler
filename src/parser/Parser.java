package parser;

import org.omg.CORBA.SystemException;
import scanner.Scanner;
import scanner.Token;
import util.AST.AST;

/**
 * Parser class
 *
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
     *
     * @param kind
     * @throws SyntacticException
     */ //TODO
    private void accept(int kind) throws SyntacticException {
		// If the current token kind is equal to the expected
        if(this.currentToken.getKind() == kind){
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
     *
     * @throws SyntacticException
     */ //TODO
    public AST parse() throws SyntacticException {
        this.parseProgram();
        return null;
    }

    //program                  ::=    declarationList;
    private void parseProgram() throws SyntacticException {
        parseDeclarationList();
    }

    /*
     declarationList          ::=    'LET' definition ('AND' definition)*;
                                |      varGlobalDefinition
        
     */
    private void parseDeclarationList() throws SyntacticException {
        
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

    private void parseDefinition() throws SyntacticException {
        
        switch(this.currentToken.getKind()){
        
            case Token.FUNCDEF:
                parseFunctionDefinition();
            break;
                
            case Token.PROCDEF:
                parseProcedureDefiniton();
            break;
                
            case Token.VARDEF:
                parseVariableDefinition();
            break;
                
            default:
                throw new SyntacticException(null, currentToken);
                
            
        }
        
    }

    /*
     varGlobalDefinition      ::=    ('GLOBAL') (intVariableDefinition | boolVariableDefinition);
     */
    private void parseVarGlobalDefinition() throws SyntacticException {
        if(this.currentToken.getKind() == Token.GLOBAL){
            acceptIt();
            switch(this.currentToken.getKind()){
                
                case Token.INTVARDEF:
                    parseIntVariableDefinition();
                break;
                    
                case Token.BOOLVARDEF:
                    parseBoolVariableDefinition();
                break;
                    
                default:
                    throw new SyntacticException(null, currentToken);
                        
            }
            
            
        }
        else{
            throw new SyntacticException(null, currentToken);
        }
    }
    /*
     functionDefinition       ::=    Identifier '(' parametersPrototype ')' '=' 'VALOF' functionBlock;
        
     */

    private void parseFunctionDefinition() {
    }

    /*
     procedureDefinition      ::=    Identifier '(' parametersPrototype ')' 'BE' commandBlock;
     */
    private void parseProcedureDefiniton() {
    }
    /*
     variableDefinition      ::=    intVariableDefinition
     |       
     */

    private void parseVariableDefinition() {
    }

    /*
     functionBlock            ::=    '{' commandList resultisCommand '}';
     */
    private void parseFunctionBlock() {
    }

    /*
     parametersPrototype      ::=   (('INT' | 'BOOL') Identifier ((, 'INT' | 'BOOL') Identifier)*)*
     */
    private void parseParametersPrototype() {
    }

    /*
     intVariableDefinition    ::=    ('INT' Identifier '=' numberExpression)*;
     */
    private void parseIntVariableDefinition() {
    }

    /*
     boolVariableDefinition   ::=    ('BOOL' Identifier '=' booleanExpression)*;
     */
    private void parseBoolVariableDefinition() {
    }

    /*
     commandBlock             ::=    '{' commandList '}';
     */
    private void parseCommandBlock() {
    }

    /*
     parametersCallCommand    ::=   (Identifier (, Identifier)*)* 
     */
    private void parseParametersCallCommand() {
    }

    /*
     commandList              ::=    command (';' command)*;
     */
    private void parseCommandList() {
    }

    /*
     command                  ::=    assignmentCommand
     |    whileCommand
     |    ifCommand
     |    breakCommand
     |    continueCommand
     |    printCommand
     |    variableDecCommand
     |    callCommand;
     */
    private void parseCommand() {
    }

    /*
     callCommand              ::=     functionCallCommand
     |      procedureCallCommand
        
     */
    private void parseCallCommand() {
    }

    /*
     variableDecCommand       ::=     intVariableDefinition
     |      boolVariableDefinition
     */
    private void parseVariableDecCommand() {
    }

    /*
     functionCallCommand      ::=    Identifier '('(parametersCallCommand)*')'
     */
    private void parseFunctionCallCommand() {
    }
    /*
     procedureCallCommand     ::=    Identifier '('(parametersCallCommand)*')'
        
     */

    private void parseProcedureCallCommand() {
    }

    /*
     ifCommand                ::=    'IF' '(' booleanExpression | booleanValue ')' commandBlock ('ELSE' commandBlock)?;
        
     */
    private void parseIfCommand() {
    }

    /*
     whileCommand             ::=    'WHILE' '(' ( booleanExpression | booleanValue ')' commandBlock;
     */
    private void parseWhileCommand() {
    }

    /*
     resultisCommand          ::=    'RESULTIS' expression;
     */
    private void parseResultIsCommand() {
    }

    /*
     assignmentCommand        ::=    Identifier ':=' (number | booleanValue | numberExpression | functionCallCommand | Identifier);
     */
    private void parseAssignmentCommand() {
    }
    /*
     breakCommand             ::=    'BREAK';
        
     */

    private void parseBreakCommand() {
    }

    /*
     continueCommand          ::=    'CONTINUE';
     */
    private void parseContinueCommand() {
    }

    /*
     printCommand             ::=    'WRITEF' '(' (Identifier)* ')';
     */
    private void parsePrintCommand() {
    }

    /*
     numberBoolExpression     ::=     (number | Identifier) Op_relation ( number | Identifier) 
        
     */
    private void parseNumberBoolExpression() {
    }

    /*
     arithBoolExpression      ::= arithmeticExpression | numberBoolExpression
     */
    private void parseArithmeticBoolExpression() {
    }

    /*
     booleanExpression        ::= ('!')? (  andExpression | orExpression  | equalExpresion | notEqualExpression )
        
     */
    private void parseBooleanExpression() {
    }

    /*
     numberExpression         ::=  (multDivExpression)? arithmeticExpression
        
     */
    private void parse() {
    }

    /*
     arithmeticExpression     ::=  multDivExpression
     |   addSubExpression
     */
    private void parseArithmeticExpression() {
    }

    /*
     multDivExpression        ::=  (Identifier | number) ('/' | '*') (Identifier | number)
     */
    private void parseMultDivExpression() {
    }

    /*
     addSubExpression         ::=  (Identifier | number) ('+' | '-') (Identifier | number)                         
     */
    private void parseAddSubExpression() {
    }

    /*
     andExpression            ::=  ( numberBoolExpression |(arithmeticExpression op_rel arithmeticExpression) | booleanValue ) '&&' 
     ( numberBoolExpression |(arithmeticExpression op_rel arithmeticExpression) | booleanValue)
     */
    private void parseAndExpression() {
    }

    /*
     orExpression             ::=  ( numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression | number)) | booleanValue ) 
     '||' ( numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression | number)) | booleanValue )
        
     */
    private void parseOrExpression() {
    }

    /*
     equalExpression          ::=  (numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression | number)) | booleanValue  )
     '==' (numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression | number)) | booleanValue )
     */
    private void parseEqualExpression() {
    }

    /*
     notEqualExpression       ::=  (numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression | number)) | booleanValue ) 
    '!=' (numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression | number)) | booleanValue )
     */
    private void parseNotEqualExpression() {
    }

    /*
        
                         


     identifierList           ::=    Identifier (',' Identifier)*;
                         





                         

     booleanValue            ::= 'TRUE'
     |  'FALSE'


        
        
     */
}
