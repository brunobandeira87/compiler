package util.AST;

public class BreakCommand extends Command{

	private ReservedWord reservedWord;
	
	public BreakCommand(ReservedWord reservedWord){
		this.reservedWord = reservedWord;
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
}
