package util.AST;

public class Expression extends AST{
	private ExpressionArithmetic expressionArithmeticLeft;
	private Operator Operator;
	private ExpressionArithmetic expressionArithmeticRight;
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}

	public ExpressionArithmetic getExpressionArithmeticLeft() {
		return expressionArithmeticLeft;
	}

	public Operator getOperator() {
		return Operator;
	}

	public ExpressionArithmetic getExpressionArithmeticRight() {
		return expressionArithmeticRight;
	}

	public Expression(ExpressionArithmetic expressionArithmeticLeft,
			Operator Operator, ExpressionArithmetic expressionArithmeticRight) {
		this.expressionArithmeticLeft = expressionArithmeticLeft;
		this.Operator = Operator;
		this.expressionArithmeticRight = expressionArithmeticRight;
	}

	public Expression(ExpressionArithmetic expressionArithmeticLeft) {
		this.expressionArithmeticLeft = expressionArithmeticLeft;
	}
	
}
