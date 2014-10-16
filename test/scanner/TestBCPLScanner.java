package scanner;

import org.junit.Test;

public class TestBCPLScanner {

	private BCPLScanner scanner = new BCPLScanner();

	@Test
	public void testGetNextToken() throws LexicalException {
		Token token;

		do {
			token = scanner.getNextToken();

			System.out.println(token.getKind());
		}
		while (token.getKind() != Token.EOF);
	}

}
