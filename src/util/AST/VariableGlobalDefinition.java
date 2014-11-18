package util.AST;

import scanner.TokenKind;


public class VariableGlobalDefinition extends AST{

	private ReservedWord reserverdWord;
	private VariableDefinition variableDefinition;
	
	
	public VariableGlobalDefinition(VariableDefinition variableDefinition) {
		this.reserverdWord = new ReservedWord(TokenKind.GLOBAL.toString()); 
		this.variableDefinition = variableDefinition;
	}
	
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
