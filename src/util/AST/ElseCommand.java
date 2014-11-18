package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import scanner.TokenKind;

public class ElseCommand extends Command {
	
	private ReservedWord reservedWord;
	private ArrayList<Operator> operator;
	private ArrayList<Command> command;
	//private Expression expression;
	
	
	private void setOperator(){
		this.reservedWord = new ReservedWord(TokenKind.ELSE.toString());
		this.operator = new ArrayList<Operator>();
		this.operator.add(new Operator(TokenKind.LPAR.toString()));
		this.operator.add(new Operator(TokenKind.RPAR.toString()));
		this.operator.add(new Operator(TokenKind.LCURL.toString()));
		this.operator.add(new Operator(TokenKind.RCURL.toString()));
	}
	
	public ElseCommand(ArrayList<Command> command) {
		this.command = command;
		//this.expression = expression;
		this.setOperator();
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {		
		return v.visitElseCommand(this, arg);
	}

}
