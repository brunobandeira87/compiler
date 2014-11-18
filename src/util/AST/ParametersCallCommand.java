package util.AST;

import java.util.ArrayList;

public class ParametersCallCommand extends AST {

	private ArrayList<Identifier> identifier;
	private ArrayList<Operator> terminal;
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}

	public ParametersCallCommand(ArrayList<Identifier> identifier,
			ArrayList<Operator> terminal) {
		this.identifier = identifier;
		this.terminal = terminal;
	}

	public ParametersCallCommand(ArrayList<Identifier> identifier) {
		this.identifier = identifier;
	}
	
	
}
