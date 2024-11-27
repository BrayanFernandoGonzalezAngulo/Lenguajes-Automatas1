import java.util.ArrayList;

public class Parser {
    private ArrayList<String> tokens;
    private ArrayList<String> syntaxErrors = new ArrayList<>();
    private int currentIndex;
    private String currentToken;

    public Parser(ArrayList<String> tokens) {
        this.tokens = tokens;
        this.currentIndex = 0;
        if (!tokens.isEmpty()) {
            currentToken = tokens.get(0);
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
        while (currentToken != null && currentToken.matches("[a-zA-Z][a-zA-Z0-9]*")) {
            match(currentToken);
            if (currentToken.equals("int") || currentToken.equals("string")) {
                match(currentToken);
                match(";");
            } else {
                break;
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
            match(";");
        } else {
            syntaxErrors.add("Se esperaba 'while', 'id' o 'print', pero se encontró: " + currentToken);
        }
    }

    private void parseE() {
        if (currentToken.matches("[a-zA-Z][a-zA-Z0-9]*")) {
            match(currentToken);
            if (currentToken != null && currentToken.equals("+")) {
                match("+");
                match(currentToken);
            }
        } else {
            syntaxErrors.add("Se esperaba un identificador, pero se encontró: " + currentToken);
        }
    }

    private void match(String expected) {
        if (currentToken != null && currentToken.equals(expected)) {
            currentIndex++;
            if (currentIndex < tokens.size()) {
                currentToken = tokens.get(currentIndex);
            } else {
                currentToken = null;
            }
        } else {
            syntaxErrors.add("Se esperaba '" + expected + "', pero se encontró: " + currentToken);
        }
    }
}
