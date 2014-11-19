package checker;

import util.AST.*;
import util.AST.Number;
import util.symbolsTable.IdentificationTable;


public final class Checker implements Visitor {
	
	private IdentificationTable identificationTable;
	

	public void check(AST ast) throws SemanticException{
		this.identificationTable = new IdentificationTable();
		ast.visit(this,null);
	}
	
	
	public Object visitProgram(Program program, Object arg) throws SemanticException {
		
		if(program.getVariableGlobalDefinition() != null){
			for(VariableGlobalDefinition var : program.getVariableGlobalDefinition()){
				var.visit(this, null);
			}
		}
		
		FunctionProcedureDefinitionList funcprocDef = program.getFunctionProcedureDefinitionList();
		if(funcprocDef != null){
			funcprocDef.visit(this, null);
		}
		
		return null;
	}

	public Object visitVariableGlobalDefinition(VariableGlobalDefinition var, Object arg) throws SemanticException {
		return null;
	}

	public Object visitFunctionProcedureDefinitionList(	FunctionProcedureDefinitionList funcProcDef, Object arg) throws SemanticException {

		return null;
	}

	public Object visitIntVariableDefinition(IntVariableDefinition intVarDef,
			Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitBoolVariableDefinition(
			BoolVariableDefinition boolVarDef, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitFunctionDefintion(FunctionDefinition funcDef, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitProcedureDefinition(ProcedureDefinition procDef,
			Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitParametersPrototype(ParametersPrototype params,
			Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitAssignmentCommand(AssignmentCommand assign, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitCallCommand(CallCommand callCmd, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitContinueCommand(ContinueCommand continueCmd, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitBreakCommand(BreakCommand breakCmd, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitPrintCommand(PrintCommand printCmd, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitIfCommand(IfCommand ifCmd, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitElseCommand(ElseCommand elseCmd, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitParametersCallCommand(ParametersCallCommand params,
			Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitResultIsCommand(ResultIsCommand resultCmd, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitWhileCommand(WhileCommand whileCmd, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitExpression(Expression expression, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitExpressionArithmetic(ExpressionArithmetic expAri,
			Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitExpressionMultiplication(
			ExpressionMultiplication expMul, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitFactor(Factor factor, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitIdentifier(Identifier identifier, Object obj)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitNumber(Number number, Object obj)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitBoolean(Bool bool, Object obj) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitOperator(Operator operator, Object obj)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitReservedWord(ReservedWord reservedWord, Object obj)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitTipo(Tipo tipo, Object obj) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}


}
