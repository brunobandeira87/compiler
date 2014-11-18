package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import scanner.TokenKind;

public class WhileCommand extends Command {
	
	private ReservedWord reservedWord;
	private ArrayList<Operator> terminal;
	private Expression expression;
	private ArrayList<Command> command;
	

	private void setTerminal(){
		this.reservedWord = new ReservedWord(TokenKind.WHILE.toString());
		this.terminal = new ArrayList<Operator>();
		this.terminal.add(new Operator(TokenKind.LPAR.toString()));
		this.terminal.add(new Operator(TokenKind.RPAR.toString()));
		this.terminal.add(new Operator(TokenKind.LCURL.toString()));
		this.terminal.add(new Operator(TokenKind.RCURL.toString()));
	}
	
	public WhileCommand(Expression expression){
		this.setTerminal();
		this.expression = expression;
	}
	
	public WhileCommand(Expression expression, ArrayList<Command> command){
		this.setTerminal();
		this.expression = expression;
		this.command = command;
	}
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return v.visitWhileCommand(this, arg);
	}
	

}
