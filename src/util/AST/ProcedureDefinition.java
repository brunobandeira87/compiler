package util.AST;

import java.util.ArrayList;

import scanner.TokenKind;

// 'VOID' Identifier '(' parametersPrototype? ')' 'BE' '{' varDefinition* command* '}'

public class ProcedureDefinition extends CallableDefinition{

	private Tipo tipo;
	private Identifier identifier;
	private ArrayList<Terminal> terminal;
	
	private ArrayList<VariableDefinition> variableDefinition;
	private ArrayList<Command> command;
	private ParametersPrototype params;
	
	void setOperator(){
		this.tipo = new Tipo(TokenKind.VOID.toString());
		this.terminal = new ArrayList<Terminal>();
		this.terminal.add( new Operator(TokenKind.LPAR.toString()));
		this.terminal.add( new Operator(TokenKind.RPAR.toString()));
		this.terminal.add( new ReservedWord(TokenKind.BE.toString()));
		this.terminal.add( new Operator(TokenKind.LCURL.toString()));
		this.terminal.add( new Operator(TokenKind.RCURL.toString()));
	}
	
	
	
	
	public ProcedureDefinition(Identifier identifier,
			ArrayList<Command> command, ParametersPrototype params) {
		this.identifier = identifier;
		this.command = command;
		this.params = params;
		this.setOperator();
	}




	public ProcedureDefinition(Identifier identifier, ParametersPrototype params) {

		this.identifier = identifier;
		this.params = params;
		this.setOperator();
	}




	public ProcedureDefinition(Identifier identifier) { 
		this.identifier = identifier;
		this.setOperator();
	}




	public ProcedureDefinition(Identifier identifier,
			ArrayList<VariableDefinition> variableDefinition,
			ArrayList<Command> command, ParametersPrototype params) {
		this.identifier = identifier;
		this.variableDefinition = variableDefinition;
		this.command = command;
		this.params = params;
		this.setOperator();
	}
	
	public ProcedureDefinition(Identifier identifier, ParametersPrototype params, ArrayList<VariableDefinition> variableDefinitions){
		this.identifier = identifier;
		this.params = params;
		this.variableDefinition = variableDefinitions;
		this.setOperator();
	}
	


	public ProcedureDefinition(Identifier identifier,
			ArrayList<VariableDefinition> variableDefinition,
			ArrayList<Command> command) {
		this.identifier = identifier;
		this.variableDefinition = variableDefinition;
		this.command = command;
		this.setOperator();
	}

	
	






	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
}
