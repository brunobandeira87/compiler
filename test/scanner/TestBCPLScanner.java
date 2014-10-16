
package scanner;

import org.junit.Assert;
import org.junit.Test;
public class TestBCPLScanner {

	@Test
	public void testExample01() throws LexicalException {
		System.out.println("-- Example 01 --");

		scanFile("examples/program01.bcpl");

		System.out.println("----------------");
	}

	@Test
	public void testExample02() throws LexicalException {
		System.out.println("-- Example 02 --");

		scanFile("examples/program02.bcpl");

		System.out.println("----------------");
	}

	@Test
	public void testExample03() {
		System.out.println("-- Example 03 --");

		try {
			scanFile("examples/program03.bcpl");

			Assert.fail("Program 03 should throw a LexicalException");
		}
		catch (LexicalException le) {
			le.printStackTrace();
		}

		System.out.println("----------------");
	}

	@Test
	public void testExample04() throws LexicalException {
		System.out.println("-- Example 04 --");

		scanFile("examples/program04.bcpl");

		System.out.println("----------------");
	}

	@Test
	public void testExample05() throws LexicalException {
		System.out.println("-- Example 05 --");

		scanFile("examples/program05.bcpl");

		System.out.println("----------------");
	}

	private void scanFile(String path) throws LexicalException {
		BCPLScanner scanner = new BCPLScanner(path);

		Token token;

		do {
			token = scanner.getNextToken();

			System.out.println(token.getKind());
		}
		while (token.getKind() != TokenKind.EOF);
	}

}