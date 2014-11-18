package util.AST;

import java.util.ArrayList;

public class ExpressionMultiplication extends AST{
	protected Factor factorLeft;
	protected ArrayList<Operator> operadores;
	protected ArrayList<Factor> factorOthers;
	
	public Factor getFactorLeft() {
		return factorLeft;
	}
	public ArrayList<Operator> getOperadores() {
		return operadores;
	}
	public ArrayList<Factor> getFactorOthers() {
		return factorOthers;
	}
	public ExpressionMultiplication(Factor factorLeft,
			ArrayList<Operator> operadores, ArrayList<Factor> factorOthers) {
		this.factorLeft = factorLeft;
		this.operadores = operadores;
		this.factorOthers = factorOthers;
	}
	public ExpressionMultiplication(Factor factorLeft) {
		this.factorLeft = factorLeft;
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
