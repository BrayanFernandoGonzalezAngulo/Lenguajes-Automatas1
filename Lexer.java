// Lexer.java
import java.util.Arrays;

public class Lexer {
    private String[] tokens;
    private int index;
    private String currentToken;
    private int currentPosition;

    private static final String[] RESERVED_WORDS = {"while", "do", "print", "int", "String"};
    private static final String[] OPERATORS = {"=", "+"};
    private static final String DELIMITER = ";";

    public Lexer(String code) {
        tokens = code.split("\\s+");
        index = 0;
        currentPosition = 0;
        currentToken = tokens.length > 0 ? tokens[0] : null;
    }

    public String getToken() {
        if (index < tokens.length) {
            currentToken = tokens[index];
            currentPosition = index;
            index++;
            return currentToken;
        }
        return null;
    }

    public String peekToken() {
        return index < tokens.length ? tokens[index] : null;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public boolean isReservedWord(String token) {
        return Arrays.asList(RESERVED_WORDS).contains(token);
    }

    public boolean isOperator(String token) {
        return Arrays.asList(OPERATORS).contains(token);
    }

    public boolean isDelimiter(String token) {
        return DELIMITER.equals(token);
    }

    public boolean isIdentifier(String token) {
        return token != null && token.matches("[a-zA-Z][a-zA-Z0-9]*");
    }

    public boolean isNumber(String token) {
        return token != null && token.matches("\\d+");
    }
}
