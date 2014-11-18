package util.AST;

public class ReservedWord extends Terminal {
	
	
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return super.spelling;
	}
	
	public ReservedWord(String spelling){
		super.spelling = spelling;
	}

}
