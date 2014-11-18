package util.AST;

import java.util.ArrayList;

public class Program extends AST{
	private String token = "Program";
	private ArrayList<VariableGlobalDefinition> variableGlobalDefinition ;
	private FunctionProcedureDefinitionList functionProcedureDefinitionList;
	
	public Program(FunctionProcedureDefinitionList functionProcedureDefinitionList){
		this.functionProcedureDefinitionList = functionProcedureDefinitionList;
	}
	
	public Program(ArrayList<VariableGlobalDefinition> variableGlobalDefinition, FunctionProcedureDefinitionList functionProcedureDefinitionList){
		this.functionProcedureDefinitionList = functionProcedureDefinitionList;
		this.variableGlobalDefinition = variableGlobalDefinition;
	}
	
	
	public ArrayList<VariableGlobalDefinition> getVariableGlobalDefinition() {
		return variableGlobalDefinition;
	}



	public void setVariableGlobalDefinition(
			ArrayList<VariableGlobalDefinition> variableGlobalDefinition) {
		this.variableGlobalDefinition = variableGlobalDefinition;
	}


	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		
		return (super.getSpaces(level) + token);
		
	}

}
