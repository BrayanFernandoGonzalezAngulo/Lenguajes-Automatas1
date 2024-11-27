import java.util.ArrayList;

public class Parser {
    private Lexer lexer;
    private ArrayList<String> syntaxErrors = new ArrayList<>();
    private String currentToken;
    private int tokenIndex = 0;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        if (!lexer.getTokenList().isEmpty()) {
            currentToken = lexer.getTokenList().get(0);
        }
    }

    public void parse() {
        try {
            parseP();
        } catch (Exception e) {
            syntaxErrors.add(e.getMessage());
        }
    }

    public ArrayList<String> getSyntaxErrors() {
        return syntaxErrors;
    }

    private void parseP() {
        parseD();
        parseS();
    }

    private void parseD() {
        if (currentToken.matches("[a-zA-Z][a-zA-Z0-9]*")) {
            match(currentToken);
            if (currentToken.equals("int") || currentToken.equals("string")) {
                match(currentToken);
                match(";");
                parseD();
            }
        }
    }

    private void parseS() {
        if (currentToken.equals("while")) {
            match("while");
            parseE();
            match("do");
            parseS();
        } else if (currentToken.matches("[a-zA-Z][a-zA-Z0-9]*")) {
            match(currentToken);
            match("=");
            parseE();
        } else if (currentToken.equals("print")) {
            match("print");
            parseE();
        } else {
            syntaxErrors.add("Se esperaba 'while', 'id' o 'print', pero se encontró: " + currentToken);
        }
    }

    private void parseE() {
        if (currentToken.matches("[a-zA-Z][a-zA-Z0-9]*")) {
            match(currentToken);
            if (currentToken.equals("+")) {
                match("+");
                match(currentToken);
            }
        } else {
            syntaxErrors.add("Se esperaba un identificador, pero se encontró: " + currentToken);
        }
    }

    private void match(String expected) {
        if (currentToken.equals(expected)) {
            if (++tokenIndex < lexer.getTokenList().size()) {
                currentToken = lexer.getTokenList().get(tokenIndex);
            } else {
                currentToken = null;
            }
        } else {
            syntaxErrors.add("Se esperaba '" + expected + "', pero se encontró: " + currentToken);
        }
    }
}
