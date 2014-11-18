package util.AST;

import java.util.ArrayList;

public class IntVariableDefinition extends VariableDefinition{

	private Terminal equalSign ;
	private Terminal tipo;
	private Identifier identifier;
	private Expression expression;
	
	@Override

	public String toString(int level) {
	// TODO Auto-generated method stub
		return null;
	}
	public IntVariableDefinition(Terminal tipo, Identifier identifier, Terminal equalSign, Expression expression){
		this.tipo = tipo;
		this.identifier = identifier;
		this.equalSign = equalSign;
		this.expression = expression;
	}

}
