import java.util.ArrayList;

public class Lexer {
    private String[] tokens;
    private int index;
    private ArrayList<String> tokenList = new ArrayList<>();
    private ArrayList<String> errors = new ArrayList<>();

    public Lexer(String code) {
        tokens = code.split("\\s+");
        index = 0;
    }

    public void tokenize() {
        while (index < tokens.length) {
            String token = tokens[index++];
            if (isKeyword(token) || isIdentifier(token) || isOperator(token) || isDelimiter(token)) {
                tokenList.add(token);
            } else {
                reportError(token);
            }
        }
    }

    private boolean isKeyword(String token) {
        return token.equals("int") || token.equals("string") || token.equals("while") || token.equals("do") || token.equals("print");
    }

    private boolean isIdentifier(String token) {
        return token.matches("[a-zA-Z][a-zA-Z0-9]*");
    }

    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("=");
    }

    private boolean isDelimiter(String token) {
        return token.equals(";");
    }

    private void reportError(String token) {
        errors.add("Token inválido: '" + token + "'");
    }

    public ArrayList<String> getTokenList() {
        return tokenList;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }
}
