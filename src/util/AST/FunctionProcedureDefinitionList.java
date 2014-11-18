package util.AST;

import java.util.ArrayList;

public class FunctionProcedureDefinitionList extends AST{
	private ArrayList<ReservedWord> reservedWord;
	private ArrayList<CallableDefinition> callabledefinition;
	
	public FunctionProcedureDefinitionList(ArrayList<ReservedWord> reservedWord,
			ArrayList<CallableDefinition> callabledefinition) {
		
		this.reservedWord = reservedWord;
		this.callabledefinition = callabledefinition;
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		
		StringBuffer cd = new StringBuffer();
		StringBuffer rw = new StringBuffer();
		for(ReservedWord x : this.reservedWord ){
			cd.append(x.toString(level+1));
		}
		
		for(CallableDefinition y : this.callabledefinition){
			rw.append(y.toString(level+1));
		}
		
		return rw.toString() + "\n" + cd.toString();
	}
	
	
	
}
