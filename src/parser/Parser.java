package parser;


import scanner.BCPLScanner;
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
        this.scanner = new BCPLScanner();
        
        this.currentToken = this.scanner.getNextToken();
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

    private void parseFunctionDefinition() throws SyntacticException{
        if(this.currentToken.getKind() == Token.IDENTIFIER){
            acceptIt();
            accept(Token.LPAR);
            parseParametersPrototype();
            accept(Token.RPAR);
            accept(Token.EQUAL);
            accept(Token.VALOF);
            parseFunctionBlock();
                
            
        }
        else{
            throw new SyntacticException(null, currentToken);
        }
    }

    /*
     procedureDefinition      ::=    Identifier '(' parametersPrototype ')' 'BE' commandBlock;
     */
    private void parseProcedureDefiniton() throws SyntacticException {
        
        if(this.currentToken.getKind() == Token.IDENTIFIER){
            acceptIt();
            accept(Token.LPAR);
            parseParametersPrototype();
            accept(Token.RPAR);
            accept(Token.EQUAL);
            accept(Token.BE);
            parseCommandBlock();
         
        }
        else{
            throw new SyntacticException(null, currentToken);
        }
        
    }
    /*
     variableDefinition      ::=    intVariableDefinition
     |       
     */

    private void parseVariableDefinition() throws SyntacticException {
        
        switch(this.currentToken.getKind()){
            
            case Token.BOOLVARDEF:
                parseBoolVariableDefinition();
            break;
                
            case Token.INTVARDEF:
                parseIntVariableDefinition();
            break;
                
            default:
                throw new SyntacticException(null, currentToken);
                
        }
        
        
        
    }

    /*
     functionBlock            ::=    '{' commandList resultisCommand '}';
     */
    private void parseFunctionBlock() throws SyntacticException{
        if(this.currentToken.getKind() == Token.RCURL){
            acceptIt();
            parseCommandList();
            parseResultIsCommand();
            accept(Token.LCURL);
        }
        else{
            throw new SyntacticException(null, currentToken);
        }
    }

    /*
     parametersPrototype      ::=   (('INT' | 'BOOL') Identifier ((, 'INT' | 'BOOL') Identifier)*)
     */
    private void parseParametersPrototype() throws SyntacticException {
        
        switch(this.currentToken.getKind()){
            case Token.INT:
            case Token.BOOL:
                acceptIt();
                parseIdentifier();
                while(this.currentToken.getKind() == Token.VIRG){
                    acceptIt();
                    switch(this.currentToken.getKind()){
                        case Token.INT:
                        case Token.BOOL:
                            acceptIt();
                        break;
                    }
                    parseIdentifier();
                }
                
        }
        
    }
    
    /*
    Precisa da classe Scanner implementada
    */
    
    private void parseIdentifier() throws SyntacticException{
        if(this.currentToken.getKind() == Token.IDENTIFIER){
            
        }
            
    }

    /*
     intVariableDefinition    ::=    ('INT' Identifier '=' numberExpression)*;
     */
    private void parseIntVariableDefinition() throws SyntacticException {
        while(this.currentToken.getKind() == Token.INT){
            acceptIt();
            parseIdentifier();
            accept(Token.EQUAL);
            parseNumberExpression();
            
        }
        
    }

    /*
     boolVariableDefinition   ::=    ('BOOL' Identifier '=' booleanExpression)*;
     */
    private void parseBoolVariableDefinition() throws SyntacticException {
        while(this.currentToken.getKind() == Token.BOOL){
            acceptIt();
            parseIdentifier();
            accept(Token.EQUAL);
            parseBooleanExpression();
        }
    }

    /*
     commandBlock             ::=    '{' commandList '}';
     */
    private void parseCommandBlock() throws SyntacticException {
        if(this.currentToken.getKind() == Token.RCURL){
            acceptIt();
            parseCommandList();
            accept(Token.LCURL);
        }
        else{
            throw new SyntacticException(null, currentToken);
        }
    }

    /*
     parametersCallCommand    ::=   (Identifier (, Identifier)*)
     */
    private void parseParametersCallCommand() throws SyntacticException {
        if(this.currentToken.getKind() == Token.IDENTIFIER){
            parseIdentifier();
            while(this.currentToken.getKind() == Token.VIRG){
                acceptIt();
                parseIdentifier();
            }
        }
        else{
            throw new SyntacticException(null, currentToken);
        }
    }

    /*
     commandList              ::=    command (';' command)*;
     */
    private void parseCommandList() throws SyntacticException {
        parseCommand();
        while(this.currentToken.getKind() == Token.SEMICOL ){
            acceptIt();
            parseCommand();
        }
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
    private void parseCommand() throws SyntacticException {
        
        switch(this.currentToken.getKind()){
            
            case Token.ASGNCOM:
                parseAssignmentCommand();
            break;
                
            case Token.WHILECOM:
                parseWhileCommand();
            break;
                
            case Token.IFCOM:
                parseIfCommand();
            break;
            
            case Token.CONTINUECOM:
                parseContinueCommand();
            break;
                
            case Token.BREAKCOM:
                parseBreakCommand();
            break;
                
            case Token.PRINTCOM:
                parsePrintCommand();
            break;
                
            case Token.CALLCOM:
                parseCallCommand();
            break;
                
            case Token.VARDCCOM:
                parseVariableDecCommand();
            break;
                
            default:
                throw new SyntacticException(null, currentToken);
                
            
                
        }
    }

    /*
     callCommand              ::=     functionCallCommand
     |      procedureCallCommand
        
     */
    private void parseCallCommand() throws SyntacticException{
        switch(this.currentToken.getKind()){
            case Token.FUNCALLCOM:
                parseFunctionCallCommand();
            break;
                
            case Token.PROCCALLCOM:
                parseProcedureCallCommand();
            break;
                
            default:
                throw new SyntacticException(null, currentToken);
        }
    }

    /*
     variableDecCommand       ::=     intVariableDefinition
     |      boolVariableDefinition
     */
    private void parseVariableDecCommand() throws SyntacticException {
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

    /*
     functionCallCommand      ::=    Identifier '('(parametersCallCommand)*')'
     */
    private void parseFunctionCallCommand() throws SyntacticException {
        if(this.currentToken.getKind() == Token.IDENTIFIER){
            acceptIt();
            accept(Token.RPAR);
            while(this.currentToken.getKind() == Token.PARCALLCOM){
                parseParametersCallCommand();
            }
        }
        else{
            throw new SyntacticException(null, currentToken);
        }
    }
    /*
     procedureCallCommand     ::=    Identifier '('(parametersCallCommand)*')'
        
     */

    private void parseProcedureCallCommand() throws SyntacticException {
        if(this.currentToken.getKind() == Token.IDENTIFIER){
            acceptIt();
            accept(Token.RPAR);
            while(this.currentToken.getKind() == Token.PARCALLCOM){
                parseParametersCallCommand();
            }
        }
        else{
            throw new SyntacticException(null, currentToken);
        }
    }

    /*
     ifCommand                ::=    'IF' '(' booleanExpression | booleanValue ')' commandBlock ('ELSE' commandBlock)?;
        
     */
    private void parseIfCommand()  throws SyntacticException{
        if(this.currentToken.getKind() == Token.IF){
            acceptIt();
            accept(Token.RPAR);
            switch(this.currentToken.getKind()){
                case Token.BOOLEXP:
                    parseBooleanExpression();
                break;
                    
                case Token.TRUE:
                case Token.FALSE:
                    acceptIt();
                    parseCommandBlock();
                break;
                
                default:
                    throw new SyntacticException(null, currentToken);
            }
            if(this.currentToken.getKind() == Token.ELSE){
                acceptIt();
                parseCommandBlock();
            }else if(this.currentToken.getKind() == Token.EOL){
                acceptIt();
            }
            else{
                throw new SyntacticException(null, currentToken);
            }
        }
        else{
            throw new SyntacticException(null, currentToken);
        }
    }

    /*
     whileCommand             ::=    'WHILE' '(' ( booleanExpression | booleanValue ')' commandBlock;
     */
    private void parseWhileCommand() throws SyntacticException {
        if(this.currentToken.getKind() == Token.WHILE){
            acceptIt();
            accept(Token.RPAR);
            switch(this.currentToken.getKind()){
                case Token.BOOLEXP:
                    parseBooleanExpression();
                break;
                    
                case Token.TRUE:
                case Token.BOOL:
                    acceptIt();
                break;
                    
                    
                default:
                    throw new SyntacticException(null, currentToken);
            }
            
            accept(Token.LPAR);
            parseCommandBlock();
        }
        else{
            throw new SyntacticException(null, currentToken);
        }
    }

    /*
     resultisCommand          ::=    'RESULTIS' expression;
     */
    private void parseResultIsCommand() throws SyntacticException {
        if(this.currentToken.getKind() == Token.RESULTIS){
            acceptIt();
            switch(this.currentToken.getKind()){
                case Token.NUMBER:
                case Token.TRUE:
                case Token.FALSE:
                    acceptIt();
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
     assignmentCommand        ::=    Identifier ':=' (number | booleanValue | numberExpression | functionCallCommand | Identifier);
     */
    private void parseAssignmentCommand() throws SyntacticException{
        if(this.currentToken.getKind() == Token.IDENTIFIER){
            acceptIt();
            accept(Token.EQUAL);
            switch(this.currentToken.getKind()){
                case Token.NUMBER:
                case Token.TRUE:
                case Token.FALSE:
                case Token.IDENTIFIER:
                    acceptIt();
                break;
                    
                case Token.FUNCALLCOM:
                    parseFunctionCallCommand();
                break;
                    
                case Token.NUMBEREXP:
                    parseNumberExpression();
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
     breakCommand             ::=    'BREAK';
        
     */

    private void parseBreakCommand() throws SyntacticException {
        if(this.currentToken.getKind() == Token.BREAK){
            acceptIt();
        }
        else{
            throw new SyntacticException(null, currentToken);
        }
        
        
    }
    

    /*
     continueCommand          ::=    'CONTINUE';
     */
    private void parseContinueCommand() throws SyntacticException {
        if(this.currentToken.getKind() == Token.CONTINUE){
            acceptIt();
        }
        else{
            throw new SyntacticException(null, currentToken);
        }
        
                
    }

    /*
     printCommand             ::=    'WRITEF' '(' (Identifier)* ')';
     */
    private void parsePrintCommand() throws SyntacticException {
        if(this.currentToken.getKind() == Token.WRITEF){
            acceptIt();
            accept(Token.RPAR);
            while(this.currentToken.getKind() == Token.IDENTIFIER){
                parseIdentifier();
            }
        }
        else{
            throw new SyntacticException(null, currentToken);
        }
    }

    /*
     numberBoolExpression     ::=     (number | Identifier) Op_relation ( number | Identifier) 
        
     */
    private void parseNumberBoolExpression() throws SyntacticException {
        switch(this.currentToken.getKind()){
            case Token.NUMBER:
            case Token.IDENTIFIER:
                acceptIt();
                accept(Token.OP_RELATION);
                switch(this.currentToken.getKind()){
                    case Token.NUMBER:
                    case Token.IDENTIFIER:
                        acceptIt();
                    break;
                    
                    default:
                        throw new SyntacticException(null, currentToken);
                }
            break;
            
            default:
                throw new SyntacticException(null, currentToken);
        }
    }

    /*
     arithBoolExpression      ::= arithmeticExpression | numberBoolExpression
     */
    private void parseArithmeticBoolExpression() throws SyntacticException {
        switch(this.currentToken.getKind()){
            
            case Token.NUMBERBOOLEXP:
                parseNumberBoolExpression();
            break;
                
            case Token.ARITHEXP:
                parseArithmeticExpression();
            break;
                
            default:
                throw new SyntacticException(null, currentToken);
        }
    }

    /*
     booleanExpression        ::= ('!')? (  andExpression | orExpression  | equalExpresion | notEqualExpression )
        
     */
    private void parseBooleanExpression() throws SyntacticException {
        if(this.currentToken.getKind() == Token.NOT){
            acceptIt();
        }
        switch(this.currentToken.getKind()){
            case Token.ANDEXP:
                parseAndExpression();
            break;
                
            case Token.OREXP:
                parseOrExpression();                        
            break;
                
            case Token.EQUALEXP:
                parseEqualExpression();
            break;
                
            case Token.NOTEQUALEXP:
                parseNotEqualExpression();
            break;
                
            default:
                throw new SyntacticException(null, currentToken);
        }
    }

    /*
     numberExpression         ::=  (multDivExpression)? arithmeticExpression
        
     */
    private void parseNumberExpression() throws SyntacticException {
        if(this.currentToken.getKind() == Token.MULDIVEXP){
            parseMultDivExpression();
        }
        if(this.currentToken.getKind() == Token.ARITHEXP){
            parseArithmeticExpression();
        }
        else{
            throw new SyntacticException(null, currentToken);
        }
        
        
        
    }

    /*
     arithmeticExpression     ::=  multDivExpression
     |   addSubExpression
     */
    private void parseArithmeticExpression() throws SyntacticException {
        switch(this.currentToken.getKind()){
        
            case Token.MULDIVEXP:
                parseMultDivExpression();
            break;
                
            case Token.ADDSUBEXP:
                parseAddSubExpression();
            break;
                
            default:
                throw new SyntacticException(null, currentToken);
                
        }
    }

    /*
     multDivExpression        ::=  (Identifier | number) ('/' | '*') (Identifier | number)
     */
    private void parseMultDivExpression() throws SyntacticException {
        switch(this.currentToken.getKind()){
            case Token.NUMBER:
            case Token.IDENTIFIER:
                acceptIt();
                accept(Token.OP_ARITMETIC);
                switch(this.currentToken.getKind()){
                    case Token.NUMBER:
                    case Token.IDENTIFIER:
                        acceptIt();
                    
                    break;
                    
                    default:
                        throw new SyntacticException(null, currentToken);
                }
            break;
                
            
            default:
                throw new SyntacticException(null, currentToken);
        }
    }

    /*
     addSubExpression         ::=  (Identifier | number) ('+' | '-') (Identifier | number)                         
     */
    private void parseAddSubExpression() throws SyntacticException {
              switch(this.currentToken.getKind()){
            case Token.NUMBER:
            case Token.IDENTIFIER:
                acceptIt();
                accept(Token.OP_ARITMETIC);
                switch(this.currentToken.getKind()){
                    case Token.NUMBER:
                    case Token.IDENTIFIER:
                        acceptIt();
                    
                    break;
                    
                    default:
                        throw new SyntacticException(null, currentToken);
                }
            break;
                
            
            default:
                throw new SyntacticException(null, currentToken);
        }
    }

    /*
     andExpression            ::=  ( numberBoolExpression |(arithmeticExpression op_rel arithmeticExpression) | booleanValue ) '&&' 
     ( numberBoolExpression |(arithmeticExpression op_rel arithmeticExpression) | booleanValue)
     */
    private void parseAndExpression() throws SyntacticException {
        switch(this.currentToken.getKind()){
            
            case Token.NUMBERBOOLEXP:
                parseNumberBoolExpression();
            break;
                
            case Token.ARITHEXP:
                parseArithmeticBoolExpression();
                accept(Token.OP_RELATION);
                parseArithmeticBoolExpression();
            break;
                
            case Token.TRUE:
            case Token.FALSE:
                acceptIt();
            break;
                
            default:
                throw new SyntacticException(null, currentToken);
                
                
        }
        if(this.currentToken.getKind() == Token.ANDLOGICAL){
            acceptIt();
            
            switch(this.currentToken.getKind()){
                case Token.NUMBERBOOLEXP:
                    parseNumberBoolExpression();
                break;
                
                case Token.ARITHEXP:
                    parseArithmeticBoolExpression();
                    accept(Token.OP_RELATION);
                    parseArithmeticBoolExpression();
                break;
                
                case Token.TRUE:
                case Token.FALSE:
                    acceptIt();
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
     orExpression             ::=  ( numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression | number)) | booleanValue ) 
     '||' ( numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression | number)) | booleanValue )
        
     */
    private void parseOrExpression() throws SyntacticException {
        switch(this.currentToken.getKind()){
            
            case Token.NUMBERBOOLEXP:
                parseNumberBoolExpression();
            break;
                
            case Token.ARITHEXP:
                parseArithmeticBoolExpression();
                accept(Token.OP_RELATION);
                parseArithmeticBoolExpression();
            break;
                
            case Token.TRUE:
            case Token.FALSE:
                acceptIt();
            break;
                
            default:
                throw new SyntacticException(null, currentToken);
                
                
        }
        if(this.currentToken.getKind() == Token.ORLOGICAL){
            acceptIt();
            
            switch(this.currentToken.getKind()){
                case Token.NUMBERBOOLEXP:
                    parseNumberBoolExpression();
                break;
                
                case Token.ARITHEXP:
                    parseArithmeticBoolExpression();
                    accept(Token.OP_RELATION);
                    parseArithmeticBoolExpression();
                break;
                
                case Token.TRUE:
                case Token.FALSE:
                    acceptIt();
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
     equalExpression          ::=  (numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression | number)) | booleanValue  )
     '==' (numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression | number)) | booleanValue )
     */
    private void parseEqualExpression() throws SyntacticException {
        switch(this.currentToken.getKind()){
            
            
            case Token.NUMBERBOOLEXP:
                parseNumberBoolExpression();
            break;
                
            case Token.ARITHEXP:
                parseArithmeticBoolExpression();
                accept(Token.OP_RELATION);
                parseArithmeticBoolExpression();
            break;
                
            case Token.TRUE:
            case Token.FALSE:
                acceptIt();
            break;
                
            default:
                throw new SyntacticException(null, currentToken);
                
                
        }
        if(this.currentToken.getKind() == Token.EQUALLOGICAL){
            acceptIt();
            
            switch(this.currentToken.getKind()){
                case Token.NUMBERBOOLEXP:
                    parseNumberBoolExpression();
                break;
                
                case Token.ARITHEXP:
                    parseArithmeticBoolExpression();
                    accept(Token.OP_RELATION);
                    parseArithmeticBoolExpression();
                break;
                
                case Token.TRUE:
                case Token.FALSE:
                    acceptIt();
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
     notEqualExpression       ::=  (numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression | number)) | booleanValue ) 
    '!=' (numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression | number)) | booleanValue )
     */
    private void parseNotEqualExpression() throws SyntacticException {
        switch(this.currentToken.getKind()){
            
            case Token.NUMBERBOOLEXP:
                parseNumberBoolExpression();
            break;
                
            case Token.ARITHEXP:
                parseArithmeticBoolExpression();
                accept(Token.OP_RELATION);
                parseArithmeticBoolExpression();
            break;
                
            case Token.TRUE:
            case Token.FALSE:
                acceptIt();
            break;
                
            default:
                throw new SyntacticException(null, currentToken);
                
                
        }
        if(this.currentToken.getKind() == Token.NOTEQUALLOGICAL){
            acceptIt();
            
            switch(this.currentToken.getKind()){
                case Token.NUMBERBOOLEXP:
                    parseNumberBoolExpression();
                break;
                
                case Token.ARITHEXP:
                    parseArithmeticBoolExpression();
                    accept(Token.OP_RELATION);
                    parseArithmeticBoolExpression();
                break;
                
                case Token.TRUE:
                case Token.FALSE:
                    acceptIt();
                break;

                default:
                    throw new SyntacticException(null, currentToken);
            }
        }
        else{
            throw new SyntacticException(null, currentToken);
        }
    }

}
