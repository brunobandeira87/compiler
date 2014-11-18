package util.AST;

public class BoolVariableDefinition extends VariableDefinition {

	

	private Terminal equalSign ;
	private Terminal tipo;
	private Identifier identifier;
	private Expression expression;
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}

	public BoolVariableDefinition(Terminal equalSign, Terminal tipo,
			Identifier identifier, Expression expression) {
		//super();
		this.equalSign = equalSign;
		this.tipo = tipo;
		this.identifier = identifier;
		this.expression = expression;
	}
	
	
}
