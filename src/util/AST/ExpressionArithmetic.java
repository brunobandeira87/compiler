package util.AST;

import java.util.ArrayList;

public class ExpressionArithmetic extends AST{

	private ExpressionMultiplication expressionMultiplicationLeft;
	private ArrayList<Operator> operadores ;
	private ArrayList<ExpressionMultiplication> expressionMultiplicationOthers;
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}

	public ExpressionMultiplication getExpressionMultiplicationLeft() {
		return expressionMultiplicationLeft;
	}

	public ArrayList<Operator> getOperadores() {
		return operadores;
	}

	public ArrayList<ExpressionMultiplication> getExpressionMultiplicationOthers() {
		return expressionMultiplicationOthers;
	}

	public ExpressionArithmetic(ExpressionMultiplication expressionMultiplicationLeft,
			ArrayList<Operator> operadores,
			ArrayList<ExpressionMultiplication> expressionMultiplicationOthers) {
		this.expressionMultiplicationLeft = expressionMultiplicationLeft;
		this.operadores = operadores;
		this.expressionMultiplicationOthers = expressionMultiplicationOthers;
	}

	public ExpressionArithmetic(ExpressionMultiplication expressionMultiplicationLeft) {
		this.expressionMultiplicationLeft = expressionMultiplicationLeft;
	}
	
	
}
