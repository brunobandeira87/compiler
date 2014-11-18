package util.AST;

public class Identifier extends Terminal{

	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		//super.getSpaces(level); 
		return super.spelling;
	}
	
	public Identifier(String spelling){
		super.spelling = spelling;
	}
}
