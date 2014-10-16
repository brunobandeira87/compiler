
package parser;

import scanner.BCPLScanner;
import scanner.LexicalException;
import scanner.Scanner;
import scanner.Token;
import scanner.TokenKind;

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

	/**
	 * Parser constructor
	 */
	public Parser() {

		this.scanner = new BCPLScanner();

		try {
			this.currentToken = this.scanner.getNextToken();
		}
		catch (LexicalException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Verifies if the source program is syntactically correct
	 *
	 * @throws SyntacticException
	 */
	public AST parse() throws SyntacticException {
		this.parseProgram();

		return null;
	}

	/**
	 * Veririfes if the current token kind is the expected one
	 *
	 * @param kind
	 * @throws SyntacticException
	 */
	private void accept(TokenKind kind) throws SyntacticException {
		if (this.currentToken.getKind() == kind) {
			try {
				this.currentToken = this.scanner.getNextToken();
			}
			catch (LexicalException e) {
				e.printStackTrace();
			}
		}
		else {
			throw new SyntacticException(
				"Expected token " + kind, currentToken);
		}
	}

	/**
	 * Gets next token
	 */
	private void acceptIt() {
		try {
			this.currentToken = this.scanner.getNextToken();
		}
		catch (LexicalException e) {
			e.printStackTrace();
		}
	}

	/**
	 * addSubExpression ::= (Identifier | number) ('+' | '-') (Identifier |
	 * number)
	 */
	private void parseAddSubExpression() throws SyntacticException {
		switch (this.currentToken.getKind()) {
			case NUMBER:
			case IDENTIFIER:
				acceptIt();
				accept(TokenKind.OP_ARITMETIC);

				switch (this.currentToken.getKind()) {
					case NUMBER:
					case IDENTIFIER:
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

	/**
	 * andExpression ::= ( numberBoolExpression |(arithmeticExpression op_rel
	 * arithmeticExpression) | booleanValue ) '&&' ( numberBoolExpression
	 * |(arithmeticExpression op_rel arithmeticExpression) | booleanValue)
	 */
	private void parseAndExpression() throws SyntacticException {
		switch (this.currentToken.getKind()) {
			case NUMBERBOOLEXP:
				parseNumberBoolExpression();
				break;
			case ARITHEXP:
				parseArithmeticBoolExpression();

				accept(TokenKind.OP_RELATION);

				parseArithmeticBoolExpression();

				break;
			case TRUE:
			case FALSE:
				acceptIt();
				break;
			default:
				throw new SyntacticException(null, currentToken);
		}

		if (this.currentToken.getKind() == TokenKind.ANDLOGICAL) {
			acceptIt();

			switch (this.currentToken.getKind()) {
				case NUMBERBOOLEXP:
					parseNumberBoolExpression();
					break;
				case ARITHEXP:
					parseArithmeticBoolExpression();

					accept(TokenKind.OP_RELATION);

					parseArithmeticBoolExpression();
					break;
				case TRUE:
				case FALSE:
					acceptIt();
					break;
				default:
					throw new SyntacticException(null, currentToken);
			}
		}
		else {
			throw new SyntacticException(null, currentToken);
		}
	}

	/**
	 * arithBoolExpression ::= arithmeticExpression | numberBoolExpression
	 */
	private void parseArithmeticBoolExpression() throws SyntacticException {
		switch (this.currentToken.getKind()) {
			case NUMBERBOOLEXP:
				parseNumberBoolExpression();

				break;
			case ARITHEXP:
				parseArithmeticExpression();

				break;
			default:
				throw new SyntacticException(null, currentToken);
		}
	}

	/**
	 * arithmeticExpression ::= multDivExpression | addSubExpression
	 */
	private void parseArithmeticExpression() throws SyntacticException {
		switch (this.currentToken.getKind()) {
			case MULDIVEXP:
				parseMultDivExpression();

				break;
			case ADDSUBEXP:
				parseAddSubExpression();

				break;
			default:
				throw new SyntacticException(null, currentToken);
		}
	}

	/**
	 * assignmentCommand ::= Identifier ':=' (number | booleanValue |
	 * numberExpression | functionCallCommand | Identifier);
	 */
	private void parseAssignmentCommand() throws SyntacticException {
		if (this.currentToken.getKind() == TokenKind.IDENTIFIER) {
			acceptIt();
			accept(TokenKind.EQUAL);

			switch (this.currentToken.getKind()) {
				case NUMBER:
				case TRUE:
				case FALSE:
				case IDENTIFIER:
					acceptIt();

					break;
				case FUNCALLCOM:
					parseFunctionCallCommand();

					break;
				case NUMBEREXP:
					parseNumberExpression();

					break;
				default:
					throw new SyntacticException(null, currentToken);
			}
		}

		else {
			throw new SyntacticException(null, currentToken);
		}
	}

	/**
	 * booleanExpression ::= ('!')? ( andExpression | orExpression |
	 * equalExpresion | notEqualExpression )
	 */
	private void parseBooleanExpression() throws SyntacticException {
		if (this.currentToken.getKind() == TokenKind.NOT) {
			acceptIt();
		}

		switch (this.currentToken.getKind()) {
			case ANDEXP:
				parseAndExpression();

				break;
			case OREXP:
				parseOrExpression();

				break;
			case EQUALEXP:
				parseEqualExpression();

				break;
			case NOTEQUALEXP:
				parseNotEqualExpression();

				break;
			default:
				throw new SyntacticException(null, currentToken);
		}
	}

	/**
	 * boolVariableDefinition ::= ('BOOL' Identifier '=' booleanExpression)*;
	 */
	private void parseBoolVariableDefinition() throws SyntacticException {
		while (this.currentToken.getKind() == TokenKind.BOOL) {
			acceptIt();

			accept(TokenKind.IDENTIFIER);

			accept(TokenKind.EQUAL);

			parseBooleanExpression();
		}
	}

	/**
	 * breakCommand ::= 'BREAK';
	 */
	private void parseBreakCommand() throws SyntacticException {
		if (this.currentToken.getKind() == TokenKind.BREAK) {
			acceptIt();
		}
		else {
			throw new SyntacticException(null, currentToken);
		}
	}

	/**
	 * callableDefinition ::= Identifier '(' parametersPrototype ')';
	 */
	private void parseCallableDefinition() throws SyntacticException {
		accept(TokenKind.IDENTIFIER);
		accept(TokenKind.LPAR);

		parseParametersPrototype();

		accept(TokenKind.RPAR);

		if (currentToken.getKind() == TokenKind.BE) {
			parseProcedureDefinitonTail();
		}
		else {
			parseFunctionDefinitionTail();
		}
	}

	/**
	 * callCommand ::= functionCallCommand | procedureCallCommand
	 */
	private void parseCallCommand() throws SyntacticException {
		switch (this.currentToken.getKind()) {
			case FUNCALLCOM:
				parseFunctionCallCommand();

				break;
			case PROCCALLCOM:
				parseProcedureCallCommand();

				break;
			default:
				throw new SyntacticException(null, currentToken);
		}
	}

	/**
	 * command ::= assignmentCommand | whileCommand | ifCommand | breakCommand |
	 * continueCommand | printCommand | variableDecCommand | callCommand;
	 */
	private void parseCommand() throws SyntacticException {
		switch (this.currentToken.getKind()) {
			case WHILE:
				parseWhileCommand();

				break;
			case IF:
				parseIfCommand();

				break;
			case BREAK:
				parseBreakCommand();

				break;
			case CONTINUE:
				parseContinueCommand();

				break;
			case WRITEF:
				parsePrintCommand();

				break;
			case BOOL:
			case INT:
			case GLOBAL:
				parseVariableDecCommand();
			case IDENTIFIER:
		}
	}

	/**
	 * commandBlock ::= '{' commandList '}';
	 */
	private void parseCommandBlock() throws SyntacticException {
		if (this.currentToken.getKind() == TokenKind.RCURL) {
			acceptIt();

			parseCommandList();

			accept(TokenKind.LCURL);
		}
		else {
			throw new SyntacticException(null, currentToken);
		}
	}

	/**
	 * commandList ::= command (';' command)*;
	 */
	private void parseCommandList() throws SyntacticException {
		parseCommand();

		while (this.currentToken.getKind() == TokenKind.SEMICOL) {
			acceptIt();

			parseCommand();
		}
	}

	/**
	 * continueCommand ::= 'CONTINUE';
	 */
	private void parseContinueCommand() throws SyntacticException {
		if (this.currentToken.getKind() == TokenKind.CONTINUE) {
			acceptIt();
		}
		else {
			throw new SyntacticException(null, currentToken);
		}
	}

	/**
	 * declarationList ::= 'LET' definition ('AND' definition)*; |
	 * varGlobalDefinition
	 */
	private void parseDeclarationList() throws SyntacticException {
		switch (this.currentToken.getKind()) {
			case LET:
				acceptIt();

				parseDefinition();

				while (this.currentToken.getKind() == TokenKind.AND) {
					acceptIt();

					parseDefinition();
				}

				break;
			default:
				parseVarGlobalDefinition();
		}
	}

	/**
	 * definition ::= functionDefinition | procedureDefinition |
	 * variableDefinition;
	 */
	private void parseDefinition() throws SyntacticException {
		if (currentToken.getKind() == TokenKind.IDENTIFIER) {
			parseCallableDefinition();
		}
		else {
			parseVariableDefinition();
		}

		parseVariableDefinition();
	}

	/**
	 * equalExpression ::= (numberBoolExpression |(arithmeticExpression op_rel
	 * (arithmeticExpression | number)) | booleanValue ) '=='
	 * (numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression
	 * | number)) | booleanValue )
	 */
	private void parseEqualExpression() throws SyntacticException {
		switch (this.currentToken.getKind()) {
			case NUMBERBOOLEXP:
				parseNumberBoolExpression();

				break;
			case ARITHEXP:
				parseArithmeticBoolExpression();

				accept(TokenKind.OP_RELATION);

				parseArithmeticBoolExpression();

				break;
			case TRUE:
			case FALSE:
				acceptIt();

				break;
			default:
				throw new SyntacticException(null, currentToken);
		}

		if (this.currentToken.getKind() == TokenKind.EQUALLOGICAL) {
			acceptIt();

			switch (this.currentToken.getKind()) {
				case NUMBERBOOLEXP:
					parseNumberBoolExpression();

					break;
				case ARITHEXP:
					parseArithmeticBoolExpression();

					accept(TokenKind.OP_RELATION);

					parseArithmeticBoolExpression();

					break;
				case TRUE:
				case FALSE:
					acceptIt();

					break;
				default:
					throw new SyntacticException(null, currentToken);
			}
		}
		else {
			throw new SyntacticException(null, currentToken);
		}
	}

	private void parseExpression() {
		acceptIt();
	}

	/**
	 * functionBlock ::= '{' commandList resultisCommand '}';
	 */
	private void parseFunctionBlock() throws SyntacticException {
		accept(TokenKind.LCURL);

		parseCommandList();

		parseResultIsCommand();

		accept(TokenKind.RCURL);
	}

	/**
	 * functionCallCommand ::= Identifier '('(parametersCallCommand)*')'
	 */
	private void parseFunctionCallCommand() throws SyntacticException {
		if (this.currentToken.getKind() == TokenKind.IDENTIFIER) {
			acceptIt();
			accept(TokenKind.RPAR);

			while (this.currentToken.getKind() == TokenKind.PARCALLCOM) {
				parseParametersCallCommand();
			}
		}
		else {
			throw new SyntacticException(null, currentToken);
		}
	}

	/**
	 * functionDefinition ::= Identifier '(' parametersPrototype ')' '=' 'VALOF'
	 * functionBlock;
	 */
	private void parseFunctionDefinitionTail() throws SyntacticException {
		accept(TokenKind.OP_ATTR);
		accept(TokenKind.VALOF);

		parseFunctionBlock();
	}

	/**
	 * ifCommand ::= 'IF' '(' booleanExpression | booleanValue ')' commandBlock
	 * ('ELSE' commandBlock)?;
	 */
	private void parseIfCommand() throws SyntacticException {
		if (this.currentToken.getKind() == TokenKind.IF) {
			acceptIt();
			accept(TokenKind.RPAR);

			switch (this.currentToken.getKind()) {
				case BOOLEXP:
					parseBooleanExpression();

					break;
				case TRUE:
				case FALSE:
					acceptIt();

					parseCommandBlock();

					break;
				default:
					throw new SyntacticException(null, currentToken);
			}

			if (this.currentToken.getKind() == TokenKind.ELSE) {
				acceptIt();

				parseCommandBlock();
			}
			else if (this.currentToken.getKind() == TokenKind.EOL) {
				acceptIt();
			}
			else {
				throw new SyntacticException(null, currentToken);
			}
		}
		else {
			throw new SyntacticException(null, currentToken);
		}
	}

	/**
	 * intVariableDefinition ::= ('INT' Identifier '=' numberExpression)*;
	 */
	private void parseIntVariableDefinition() throws SyntacticException {
		while (this.currentToken.getKind() == TokenKind.INT) {
			acceptIt();
			accept(TokenKind.IDENTIFIER);
			accept(TokenKind.OP_ATTR);

			parseNumberExpression();
		}
	}

	/**
	 * multDivExpression ::= (Identifier | number) ('/' | '*') (Identifier |
	 * number)
	 */
	private void parseMultDivExpression() throws SyntacticException {
		switch (this.currentToken.getKind()) {
			case NUMBER:
			case IDENTIFIER:
				acceptIt();
				accept(TokenKind.OP_ARITMETIC);
				switch (this.currentToken.getKind()) {
					case NUMBER:
					case IDENTIFIER:
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

	/**
	 * notEqualExpression ::= (numberBoolExpression |(arithmeticExpression
	 * op_rel (arithmeticExpression | number)) | booleanValue ) '!='
	 * (numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression
	 * | number)) | booleanValue )
	 */
	private void parseNotEqualExpression() throws SyntacticException {
		switch (this.currentToken.getKind()) {
			case NUMBERBOOLEXP:
				parseNumberBoolExpression();

				break;
			case ARITHEXP:
				parseArithmeticBoolExpression();

				accept(TokenKind.OP_RELATION);

				parseArithmeticBoolExpression();

				break;
			case TRUE:
			case FALSE:
				acceptIt();

				break;
			default:
				throw new SyntacticException(null, currentToken);
		}

		if (this.currentToken.getKind() == TokenKind.NOTEQUALLOGICAL) {
			acceptIt();

			switch (this.currentToken.getKind()) {
				case NUMBERBOOLEXP:
					parseNumberBoolExpression();

					break;
				case ARITHEXP:
					parseArithmeticBoolExpression();

					accept(TokenKind.OP_RELATION);

					parseArithmeticBoolExpression();

					break;
				case TRUE:
				case FALSE:
					acceptIt();

					break;
				default:
					throw new SyntacticException(null, currentToken);
			}
		}
		else {
			throw new SyntacticException(null, currentToken);
		}
	}

	/**
	 * numberBoolExpression ::= (number | Identifier)Op_relation ( number |
	 * Identifier)
	 */
	private void parseNumberBoolExpression() throws SyntacticException {
		switch (this.currentToken.getKind()) {
			case NUMBER:
			case IDENTIFIER:
				acceptIt();
				accept(TokenKind.OP_RELATION);

				switch (this.currentToken.getKind()) {
					case NUMBER:
					case IDENTIFIER:
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

	/**
	 * numberExpression ::= (multDivExpression)? arithmeticExpression
	 */
	private void parseNumberExpression() throws SyntacticException {
		if (this.currentToken.getKind() == TokenKind.MULDIVEXP) {
			parseMultDivExpression();
		}

		if (this.currentToken.getKind() == TokenKind.ARITHEXP) {
			parseArithmeticExpression();
		}
		else {
			throw new SyntacticException(null, currentToken);
		}
	}

	/**
	 * orExpression ::= ( numberBoolExpression |(arithmeticExpression op_rel
	 * (arithmeticExpression | number)) | booleanValue ) '||' (
	 * numberBoolExpression |(arithmeticExpression op_rel (arithmeticExpression
	 * | number)) | booleanValue )
	 */
	private void parseOrExpression() throws SyntacticException {
		switch (this.currentToken.getKind()) {
			case NUMBERBOOLEXP:
				parseNumberBoolExpression();

				break;
			case ARITHEXP:
				parseArithmeticBoolExpression();

				accept(TokenKind.OP_RELATION);

				parseArithmeticBoolExpression();

				break;
			case TRUE:
			case FALSE:
				acceptIt();

				break;
			default:
				throw new SyntacticException(null, currentToken);
		}

		if (this.currentToken.getKind() == TokenKind.ORLOGICAL) {
			acceptIt();

			switch (this.currentToken.getKind()) {
				case NUMBERBOOLEXP:
					parseNumberBoolExpression();

					break;
				case ARITHEXP:
					parseArithmeticBoolExpression();

					accept(TokenKind.OP_RELATION);

					parseArithmeticBoolExpression();

					break;
				case TRUE:
				case FALSE:
					acceptIt();

					break;
				default:
					throw new SyntacticException(null, currentToken);
			}
		}
		else {
			throw new SyntacticException(null, currentToken);
		}
	}

	/**
	 * parametersCallCommand ::= (Identifier (, Identifier)*)
	 */
	private void parseParametersCallCommand() throws SyntacticException {
		if (this.currentToken.getKind() == TokenKind.IDENTIFIER) {
			accept(TokenKind.IDENTIFIER);

			while (this.currentToken.getKind() == TokenKind.VIRG) {
				acceptIt();

				accept(TokenKind.IDENTIFIER);
			}
		}
		else {
			throw new SyntacticException(null, currentToken);
		}
	}

	/**
	 * parametersPrototype ::= (('INT' | 'BOOL')Identifier ((, 'INT' |
	 * 'BOOL')Identifier)*)
	 */
	private void parseParametersPrototype() throws SyntacticException {
		switch (this.currentToken.getKind()) {
			case INT:
			case BOOL:
				acceptIt();
				accept(TokenKind.IDENTIFIER);

				while (this.currentToken.getKind() == TokenKind.VIRG) {
					acceptIt();

					switch (this.currentToken.getKind()) {
						case INT:
						case BOOL:
							acceptIt();

							break;
						default:
							break;
					}

					accept(TokenKind.IDENTIFIER);
				}

			default:
				break;
		}
	}

	/**
	 * printCommand ::= 'WRITEF' '(' (Identifier)* ')';
	 */
	private void parsePrintCommand() throws SyntacticException {
		accept(TokenKind.WRITEF);
		accept(TokenKind.LPAR);

		if (currentToken.getKind() == TokenKind.IDENTIFIER ||
			currentToken.getKind() == TokenKind.NUMBER) {

			acceptIt();
		}

		accept(TokenKind.RPAR);
	}

	/**
	 * procedureCallCommand ::= Identifier '('(parametersCallCommand)*')'
	 */
	private void parseProcedureCallCommand() throws SyntacticException {
		if (this.currentToken.getKind() == TokenKind.IDENTIFIER) {
			acceptIt();
			accept(TokenKind.RPAR);

			while (this.currentToken.getKind() == TokenKind.PARCALLCOM) {
				parseParametersCallCommand();
			}
		}
		else {
			throw new SyntacticException(null, currentToken);
		}
	}

	/**
	 * procedureDefinition ::= Identifier '(' parametersPrototype ')' 'BE'
	 * commandBlock;
	 */
	private void parseProcedureDefinitonTail() throws SyntacticException {
		if (this.currentToken.getKind() == TokenKind.IDENTIFIER) {
			acceptIt();
			accept(TokenKind.LPAR);

			parseParametersPrototype();

			accept(TokenKind.RPAR);
			accept(TokenKind.EQUAL);
			accept(TokenKind.BE);

			parseCommandBlock();
		}
		else {
			throw new SyntacticException(null, currentToken);
		}
	}

	/**
	 * program ::= declarationList
	 */
	private void parseProgram() throws SyntacticException {
		parseDeclarationList();
	}

	/**
	 * resultisCommand ::= 'RESULTIS' expression;
	 */
	private void parseResultIsCommand() throws SyntacticException {
		accept(TokenKind.RESULTIS);

		parseExpression();
	}

	/**
	 * varGlobalDefinition ::= ('GLOBAL') (intVariableDefinition |
	 * boolVariableDefinition);
	 */
	private void parseVarGlobalDefinition() throws SyntacticException {
		accept(TokenKind.GLOBAL);

		switch (this.currentToken.getKind()) {
			case INT:
				parseIntVariableDefinition();

				break;
			default:
				parseBoolVariableDefinition();
		}
	}

	/**
	 * variableDecCommand ::= intVariableDefinition | boolVariableDefinition
	 */
	private void parseVariableDecCommand() throws SyntacticException {
		switch (this.currentToken.getKind()) {
			case INTVARDEF:
				parseIntVariableDefinition();

				break;
			case BOOLVARDEF:
				parseBoolVariableDefinition();

				break;
			default:
				throw new SyntacticException(null, currentToken);
		}
	}

	/**
	 * variableDefinition ::= intVariableDefinition
	 */
	private void parseVariableDefinition() throws SyntacticException {
		if (currentToken.getKind() == TokenKind.GLOBAL) {
			acceptIt();
		}

		if (currentToken.getKind() == TokenKind.INT) {
			parseIntVariableDefinition();
		}
		else {
			parseBoolVariableDefinition();
		}
	}

	/**
	 * whileCommand ::= 'WHILE' '(' ( booleanExpression | booleanValue ')'
	 * commandBlock;
	 */
	private void parseWhileCommand() throws SyntacticException {
		if (this.currentToken.getKind() == TokenKind.WHILE) {
			acceptIt();
			accept(TokenKind.RPAR);

			switch (this.currentToken.getKind()) {
				case BOOLEXP:
					parseBooleanExpression();

					break;
				case TRUE:
				case BOOL:
					acceptIt();

					break;
				default:
					throw new SyntacticException(null, currentToken);
			}

			accept(TokenKind.LPAR);

			parseCommandBlock();
		}
		else {
			throw new SyntacticException(null, currentToken);
		}
	}

	// The current token

	private Token currentToken = null;

	// The scanner

	private Scanner scanner = null;

}