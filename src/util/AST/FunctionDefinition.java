package util.AST;

import java.util.ArrayList;

import scanner.TokenKind;

public class FunctionDefinition extends CallableDefinition{

	private Tipo tipo;
	private ArrayList<Terminal> terminal = new ArrayList<Terminal>();
	private Identifier identifier;
	private ParametersPrototype parameters; 
	
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		
		return null;
	}
	
	public FunctionDefinition(Tipo tipo, Identifier identifier){

		this.setTerminal();
		this.identifier = identifier;
		this.tipo = tipo;
	}
	
	public FunctionDefinition(Tipo tipo, Identifier identifier, ParametersPrototype parameters){
		this.setTerminal();
		this.tipo = tipo;
		
		this.identifier = identifier;
		this.parameters = parameters;
	}
	
	private void setTerminal(){
		
		this.terminal.add(new Operator(TokenKind.LPAR.toString()));
		this.terminal.add(new Operator(TokenKind.RPAR.toString()));
		this.terminal.add(new Operator(TokenKind.OP_ATTR.toString()));
		this.terminal.add(new ReservedWord(TokenKind.VALOF.toString()));
		this.terminal.add(new Operator(TokenKind.LCURL.toString()));
		this.terminal.add(new Operator(TokenKind.RCURL.toString()));
		
	}
	
	
	
	
	
}
