package util.AST;

import java.util.ArrayList;

import checker.SemanticException;

public class Program extends AST{
	private String token = "Program";
	private ArrayList<VariableGlobalDefinition> variableGlobalDefinition ;
	private FunctionProcedureDefinitionList functionProcedureDefinitionList;
	
	/*
	 * Construtores
	 * */
	
	public Program(FunctionProcedureDefinitionList functionProcedureDefinitionList){
		this.functionProcedureDefinitionList = functionProcedureDefinitionList;
	}
	
	public Program(ArrayList<VariableGlobalDefinition> variableGlobalDefinition, FunctionProcedureDefinitionList functionProcedureDefinitionList){
		this.functionProcedureDefinitionList = functionProcedureDefinitionList;
		this.variableGlobalDefinition = variableGlobalDefinition;
	}
	
	
	
	/*
	 * Getters
	 * */
	 
	public ArrayList<VariableGlobalDefinition> getVariableGlobalDefinition(){
		return this.variableGlobalDefinition;
	}
	
	public FunctionProcedureDefinitionList getFunctionProcedureDefinitionList(){
		return this.functionProcedureDefinitionList;
	}
	
	
	
	@Override
	public String toString(int level) {
		StringBuffer vg = new StringBuffer();
		StringBuffer fpl = new StringBuffer();
		
		if(this.variableGlobalDefinition != null){
			for(VariableGlobalDefinition vgd : this.variableGlobalDefinition){
				vg.append(vgd.toString(level));
			}
		}
		if(this.functionProcedureDefinitionList != null){
			fpl.append(this.functionProcedureDefinitionList.toString(level));
		}
		
		
		return (super.getSpaces(level) + token + "\n" + vg + "\t" + fpl);
	}
	
	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		return v.visitProgram(this, arg);
	}

}
