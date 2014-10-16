package scanner;

/**
 * Token class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Token {

	// The token kind
	private int kind;
	// The token spelling
	private String spelling;
	// The line and column that the token was found
	private int line, column;
	
	/**
	 * Default constructor
	 * @param kind
	 * @param spelling
	 * @param line
	 * @param column
	 */
	public Token(int kind, String spelling, int line, int column) {
		this.kind = kind;
		this.spelling = spelling;
		this.line = line;
		this.column = column;
	}

	/**
	 * Returns token kind
	 * @return
	 */
	public int getKind() {
		return kind;
	}

	/**
	 * Returns token spelling
	 * @return
	 */
	public String getSpelling() {
		return spelling;
	}

	/**
	 * Returns the line where the token was found
	 * @return
	 */
	public int getLine() {
		return line;
	}

	/**
	 * Returns the column where the token was found
	 * @return
	 */	
	public int getColumn() {
		return column;
	}
	public static final int 
        IDENTIFIER = 0,
        NUMBER = 1,
        OP_ATTR = 2,
        OP_RELATION = 3,
        OP_ARITMETIC = 4,
        IF = 5,
        ELSE = 6,
        WHILE = 7,
        LET = 8,
        RESULTIS = 9,
        BREAK = 10, 
        CONTINUE = 11, 
        GLOBAL = 12, 
        WRITEF = 13,
        LPAR = 14,
        RPAR = 15,
        EQUAL = 16,
        EQUALB = 17,
        GT = 18,
        GTE = 19,
        LT = 20,
        LTE = 21,
        NOT = 22,
        AND = 23,
        FUNCDEF = 24,
        PROCDEF = 25,
        VARDEF = 26,
        VARGLODEF = 27,
        INTVARDEF = 28,
        BOOLVARDEF = 29,
        VALOF = 30,
        BE = 31,
        RCURL = 32,
        LCURL = 33,
        BOOL = 34,
        INT = 35,
        ASGNCOM = 36,
        IFCOM = 37,
        WHILECOM = 38,
        BREAKCOM = 39,
        CONTINUECOM = 40,
        VARDCCOM = 41,
        CALLCOM = 42,
        PRINTCOM = 43,
        FUNCALLCOM = 44,
        PROCCALLCOM = 45,
        ADDSUBEXP = 46,
        MULDIVEXP = 47,
        VIRG = 48,
        SEMICOL = 49,
        PARCALLCOM = 50,
        EOL = 51,
        TRUE = 52,
        FALSE = 53,
        BOOLEXP = 54;
               
        
            
}
