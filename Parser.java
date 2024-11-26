// Parser.java
import javax.swing.JTextArea;

public class Parser {
    private Lexer lexer;
    private String currentToken;
    private JTextArea output;

    public Parser(Lexer lexer, JTextArea output) {
        this.lexer = lexer;
        this.output = output;
        currentToken = lexer.getToken();
    }

    public void parse() throws Exception {
        try {
            P();
            if (currentToken != null) {
                throw new Exception("Tokens sobrantes después del fin del programa: '" + currentToken + "' en posición " + lexer.getCurrentPosition());
            }
        } catch (Exception e) {
            throw new Exception("Error en el parser: " + e.getMessage());
        }
    }

    private void P() throws Exception {
        D();
        S();
    }

    private void D() throws Exception {
        if (lexer.isIdentifier(currentToken)) {
            currentToken = lexer.getToken();
            if ("int".equals(currentToken) || "String".equals(currentToken)) {
                currentToken = lexer.getToken();
                if (lexer.isDelimiter(currentToken)) {
                    currentToken = lexer.getToken();
                    D();
                } else {
                    throw new Exception("Se esperaba un ';' en posición " + lexer.getCurrentPosition());
                }
            } else {
                throw new Exception("Tipo de dato inválido en posición " + lexer.getCurrentPosition());
            }
        }
    }

    private void S() throws Exception {
        if ("while".equals(currentToken)) {
            currentToken = lexer.getToken();
            E();
            if ("do".equals(currentToken)) {
                currentToken = lexer.getToken();
                S();
            } else {
                throw new Exception("Se esperaba 'do' en posición " + lexer.getCurrentPosition());
            }
        } else if (lexer.isIdentifier(currentToken)) {
            currentToken = lexer.getToken();
            if ("=".equals(currentToken)) {
                currentToken = lexer.getToken();
                E();
            } else {
                throw new Exception("Se esperaba '=' en posición " + lexer.getCurrentPosition());
            }
        } else if ("print".equals(currentToken)) {
            currentToken = lexer.getToken();
            E();
        } else {
            throw new Exception("Instrucción inválida en posición " + lexer.getCurrentPosition());
        }
    }

    private void E() throws Exception {
        if (lexer.isIdentifier(currentToken)) {
            currentToken = lexer.getToken();
            if ("+".equals(currentToken)) {
                currentToken = lexer.getToken();
                if (lexer.isIdentifier(currentToken)) {
                    currentToken = lexer.getToken();
                } else {
                    throw new Exception("Se esperaba un identificador después de '+' en posición " + lexer.getCurrentPosition());
                }
            }
        } else {
            throw new Exception("Expresión inválida en posición " + lexer.getCurrentPosition());
        }
    }
}
