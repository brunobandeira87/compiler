package util.AST;

public class ContinueCommand extends Command{
	private ReservedWord reservedWord;
	
	public ContinueCommand(ReservedWord reservedWord) {
		this.reservedWord = reservedWord;
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}

}
