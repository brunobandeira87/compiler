package scanner;

public class BCPLScanner extends Scanner {

	public BCPLScanner() {
		super();
	}

	public String getCurrentSpelling() {
		return this.currentSpelling.toString();
	}

	@Override
	public Token getNextToken() {
		while (isSeparator(currentChar)) {
			scanSeparator();
		}

		currentSpelling = new StringBuffer("");

		currentKind = scanToken();

		return new Token(currentKind, getCurrentSpelling(), line, column);
	}

	public int lookupReservedWord(String word) {
		if (word.equals(ReservedWords.IF)) {
		}

		return -1;
	}

	@Override
	protected void scanSeparator() {
		switch (currentChar) {
			case '/':
				getNextChar();

				if (currentChar == '/') {
					while (isGraphic(currentChar) && currentChar != '\n') {
						getNextChar();
					}
				}

				break;
			case ' ':
			case '\r':
			case '\t':
			case '\n':
				getNextChar();

				break;
		}
	}

	@Override
	protected int scanToken() {
		switch (currentChar) {
			case 'a':
			case 'A':
			case 'b':
			case 'B':
			case 'c':
			case 'C':
			case 'd':
			case 'D':
			case 'e':
			case 'E':
			case 'f':
			case 'F':
			case 'g':
			case 'G':
			case 'h':
			case 'H':
			case 'i':
			case 'I':
			case 'j':
			case 'J':
			case 'k':
			case 'K':
			case 'l':
			case 'L':
			case 'm':
			case 'M':
			case 'n':
			case 'N':
			case 'o':
			case 'O':
			case 'p':
			case 'P':
			case 'q':
			case 'Q':
			case 'r':
			case 'R':
			case 's':
			case 'S':
			case 't':
			case 'T':
			case 'u':
			case 'U':
			case 'v':
			case 'V':
			case 'w':
			case 'W':
			case 'x':
			case 'X':
			case 'y':
			case 'Y':
			case 'z':
			case 'Z':
				getNextChar();

				while (isLetter(currentChar) || isDigit(currentChar)) {
					getNextChar();
				}

				int reservedWord = lookupReservedWord(getCurrentSpelling());

				if (reservedWord > -1) {
					return reservedWord;
				}

				return Token.IDENTIFIER;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				getNextChar();

				while (isDigit(currentChar)) {
					getNextChar();
				}

				return Token.NUMBER;
			case '+':
			case '-':
			case '*':
			case '/':
				getNextChar();

				return Token.OP_ARITMETIC;
			case ':':
				getNextChar();

				return Token.OP_ATTR;
			case '<':
			case '>':
			case '!':
				getNextChar();

				if (currentChar == '=') {
					getNextChar();

					return Token.OP_RELATION;
				}
				else {
					return Token.OP_RELATION;
				}

			case '=':
				getNextChar();

				if (currentChar == '=') {
					getNextChar();

					return Token.OP_RELATION;
				}
				else {
					return Token.OP_ATTR;
				}

			case '(':
				getNextChar();

				return Token.LPAR;
			case ')':
				getNextChar();

				return Token.RPAR;
			case '{':
				getNextChar();

				return Token.LCURL;
			case '}':
				getNextChar();

				return Token.RCURL;
			case ';':
				getNextChar();

				return Token.SEMICOL;
			case 0:
				return Token.EOF;
			default:
				return Token.UNKNOWN;
		}
	}

}