
package parser;

import scanner.BCPLScanner;
import scanner.LexicalException;
import scanner.Scanner;
import scanner.Token;
import scanner.TokenKind;

import util.AST.AST;
import util.AST.BoolVariableDefinition;
import util.AST.CallableDefinition;
import util.AST.CommandBlock;
import util.AST.CommandList;
import util.AST.DeclarationList;
import util.AST.Definition;
import util.AST.Expression;
import util.AST.FunctionBlock;
import util.AST.FunctionDefinitionTail;
import util.AST.FunctionOrDefinitionTail;
import util.AST.IntVariableDefinition;
import util.AST.ParametersPrototype;
import util.AST.ProcedureDefinitionTail;
import util.AST.Program;
import util.AST.ResultisCommand;
import util.AST.VarGlobalDefinition;
import util.AST.VariableDefinition;

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
		return parseProgram();
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
	private BoolVariableDefinition parseBoolVariableDefinition()
		throws SyntacticException {

		BoolVariableDefinition boolVariableDefinition =
			new BoolVariableDefinition();

		while (this.currentToken.getKind() == TokenKind.BOOL) {
			acceptIt();

			accept(TokenKind.IDENTIFIER);

			accept(TokenKind.EQUAL);

			parseBooleanExpression();
		}

		return boolVariableDefinition;
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
	private CallableDefinition parseCallableDefinition() throws SyntacticException {
		CallableDefinition callableDefinition;

		accept(TokenKind.IDENTIFIER);
		accept(TokenKind.LPAR);

		ParametersPrototype parametersPrototype = parseParametersPrototype();

		accept(TokenKind.RPAR);

		FunctionOrDefinitionTail functionOrDefinitionTail;

		if (currentToken.getKind() == TokenKind.BE) {
			functionOrDefinitionTail = parseProcedureDefinitonTail();
		}
		else {
			functionOrDefinitionTail = parseFunctionDefinitionTail();
		}

		return new CallableDefinition(
			parametersPrototype, functionOrDefinitionTail);
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
	private CommandBlock parseCommandBlock() throws SyntacticException {
		acceptIt();

		CommandBlock commandBlock = parseCommandList();

		accept(TokenKind.LCURL);

		return commandBlock;
	}

	/**
	 * commandList ::= command (';' command)*;
	 */
	private CommandList parseCommandList() throws SyntacticException {
		CommandList commandList = new CommandList();

		parseCommand();

		while (this.currentToken.getKind() == TokenKind.SEMICOL) {
			acceptIt();

			parseCommand();
		}

		return commandList;
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
	private DeclarationList parseDeclarationList() throws SyntacticException {
		DeclarationList declarationList;

		switch (this.currentToken.getKind()) {
			case LET:
				acceptIt();

				declarationList = parseDefinition();

				while (this.currentToken.getKind() == TokenKind.AND) {
					acceptIt();

					parseDefinition();
				}

				break;
			default:
				declarationList = parseVarGlobalDefinition();
		}

		return declarationList;
	}

	/**
	 * definition ::= functionDefinition | procedureDefinition |
	 * variableDefinition;
	 */
	private Definition parseDefinition() throws SyntacticException {
		Definition definition;

		if (currentToken.getKind() == TokenKind.IDENTIFIER) {
			definition = parseCallableDefinition();
		}
		else {
			definition = parseVariableDefinition();
		}

		return definition;
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

	private Expression parseExpression() {
		acceptIt();

		return new Expression();
	}

	/**
	 * functionBlock ::= '{' commandList resultisCommand '}';
	 */
	private FunctionBlock parseFunctionBlock() throws SyntacticException {
		accept(TokenKind.LCURL);

		CommandList commandList = parseCommandList();

		ResultisCommand resultisCommand = parseResultisCommand();

		accept(TokenKind.RCURL);

		return new FunctionBlock(commandList, resultisCommand);
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
	private FunctionDefinitionTail parseFunctionDefinitionTail()
		throws SyntacticException {

		accept(TokenKind.OP_ATTR);
		accept(TokenKind.VALOF);

		return parseFunctionBlock();
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
	private IntVariableDefinition parseIntVariableDefinition()
		throws SyntacticException {

		IntVariableDefinition intVariableDefinition =
			new IntVariableDefinition();

		while (this.currentToken.getKind() == TokenKind.INT) {
			acceptIt();
			accept(TokenKind.IDENTIFIER);
			accept(TokenKind.OP_ATTR);

			parseNumberExpression();
		}

		return intVariableDefinition;
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
	private ParametersPrototype parseParametersPrototype()
		throws SyntacticException {

		ParametersPrototype parametersPrototype = new ParametersPrototype();

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

		return parametersPrototype;
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
	private ProcedureDefinitionTail parseProcedureDefinitonTail()
		throws SyntacticException {

		accept(TokenKind.IDENTIFIER);
		accept(TokenKind.LPAR);

		ParametersPrototype parametersPrototype = parseParametersPrototype();

		accept(TokenKind.RPAR);
		accept(TokenKind.BE);

		CommandBlock commandBlock = parseCommandBlock();

		return new ProcedureDefinitionTail(parametersPrototype, commandBlock);
	}

	/**
	 * program ::= declarationList
	 */
	private Program parseProgram() throws SyntacticException {
		return parseDeclarationList();
	}

	/**
	 * resultisCommand ::= 'RESULTIS' expression;
	 */
	private ResultisCommand parseResultisCommand() throws SyntacticException {
		accept(TokenKind.RESULTIS);

		return parseExpression();
	}

	/**
	 * varGlobalDefinition ::= ('GLOBAL') (intVariableDefinition |
	 * boolVariableDefinition);
	 */
	private VarGlobalDefinition parseVarGlobalDefinition()
		throws SyntacticException {

		VarGlobalDefinition varGlobalDefinition;

		accept(TokenKind.GLOBAL);

		switch (this.currentToken.getKind()) {
			case INT:
				varGlobalDefinition = parseIntVariableDefinition();

				break;
			default:
				varGlobalDefinition = parseBoolVariableDefinition();
		}

		return varGlobalDefinition;
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
	private VariableDefinition parseVariableDefinition()
		throws SyntacticException {

		if (currentToken.getKind() == TokenKind.GLOBAL) {
			acceptIt();
		}

		VariableDefinition variableDefinition;

		if (currentToken.getKind() == TokenKind.INT) {
			variableDefinition = parseIntVariableDefinition();
		}
		else {
			variableDefinition = parseBoolVariableDefinition();
		}

		return variableDefinition;
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