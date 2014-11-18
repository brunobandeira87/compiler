package util.AST;

import scanner.TokenKind;

public class CallCommand extends Command{
	
	private ReservedWord reservedWord;
	private Identifier identifier;
	private Operator lpar;
	private ParametersCallCommand parameters;
	private Operator rpar;
	
	
	
	private void setPar(){
		this.lpar = new Operator(TokenKind.LPAR.toString());
		this.rpar = new Operator(TokenKind.RPAR.toString());
	}

	private void setReservedWord(){
		this.reservedWord = new ReservedWord(TokenKind.CALL.toString());
	}



	public CallCommand(Identifier identifier) {
		this.setPar();
		this.setReservedWord();
		
		this.identifier = identifier;
	}



	public CallCommand(Identifier identifier, ParametersCallCommand parameters) {
		
		this.identifier = identifier;
		this.parameters = parameters;
		this.setPar();
		this.setReservedWord();
	}









	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}

}
