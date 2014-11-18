package util.AST;

import scanner.TokenKind;

public class ResultIsCommand extends Command{

	private ReservedWord reservedWord;
	private Expression expression;	
	
	
	public ResultIsCommand(Expression expression) {
		this.reservedWord = new ReservedWord(TokenKind.RESULTIS.toString());
		this.expression = expression;
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
}
