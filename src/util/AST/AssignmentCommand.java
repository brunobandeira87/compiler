package util.AST;

import scanner.TokenKind;

public class AssignmentCommand extends Command{
	
	private Identifier identifier;
	private Operator equalSign;
	private Expression expression;
	private CallCommand callCommand;
	
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}

	private void setEqualSign(){
		this.equalSign = new Operator(TokenKind.OP_ATTR.toString());
	}

	public AssignmentCommand(Identifier identifier, Expression expression) {
		this.identifier = identifier;
		this.expression = expression;
		this.setEqualSign();
	}
	
	public AssignmentCommand(Identifier identifier, CallCommand callCommand){
		this.identifier = identifier;
		this.callCommand = callCommand;
		this.setEqualSign();
	}
	
	

}
