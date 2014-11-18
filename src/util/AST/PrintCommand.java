package util.AST;

import java.util.ArrayList;

import scanner.TokenKind;

public class PrintCommand extends Command{
	
	private ReservedWord reservedWord;
	
	private Expression expression;
	private ArrayList<Operator> operator;
	
	void setOperator(){
		this.reservedWord = new ReservedWord(TokenKind.WRITEF.toString());
		this.operator = new ArrayList<Operator>();
		this.operator.add(new Operator(TokenKind.LPAR.toString()));
		this.operator.add(new Operator(TokenKind.QUOTE.toString()));
		this.operator.add(new Operator(TokenKind.QUOTE.toString()));
		this.operator.add(new Operator(TokenKind.RPAR.toString()));
	}
	
	public PrintCommand(Expression expression) {
		this.setOperator();
		this.expression = expression;
		
	}



	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}

}
