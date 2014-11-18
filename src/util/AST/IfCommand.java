package util.AST;

import java.util.ArrayList;

import scanner.TokenKind;

public class IfCommand extends Command{

	private ReservedWord reservedWord;
	private Expression expression;
	private ArrayList<Command> command;
	private ArrayList<Operator> operator;
	private ElseCommand elseCommand;
	
	
	private void setOperator(){
		this.reservedWord = new ReservedWord(TokenKind.IF.toString());
		this.operator = new ArrayList<Operator>();
		this.operator.add(new Operator(TokenKind.LPAR.toString()));
		this.operator.add(new Operator(TokenKind.RPAR.toString()));
		this.operator.add(new Operator(TokenKind.LCURL.toString()));
		this.operator.add(new Operator(TokenKind.RCURL.toString()));
	}
	
	
	
	
	public IfCommand(Expression expression, ArrayList<Command> command,
			ElseCommand elseCommand) {
		this.expression = expression;
		this.command = command;
		this.elseCommand = elseCommand;
		this.setOperator();
	}




	public IfCommand(Expression expression, ElseCommand elseCommand) {
		this.expression = expression;
		this.elseCommand = elseCommand;
		this.setOperator();
	}




	public IfCommand(Expression expression, ArrayList<Command> command) {
		this.expression = expression;
		this.command = command;
		this.setOperator();
	}




	public IfCommand(Expression expression) {
		this.expression = expression;
	}




	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
}
