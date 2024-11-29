import java.util.List;

public class Parser {
    private Lexer lexer;
    private String currentToken;
    private List<String> keywords;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.keywords = lexer.getKeywords(); // Obtener las keywords del lexer
        this.currentToken = lexer.getToken();
    }

    // Método principal para parsear el programa
    public void parse() {
        try {
            parseP();
            if (!currentToken.equals("")) {
                error("Código adicional no esperado.");
            } else {
                System.out.println("El código es válido según la gramática.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // P -> DS
    private void parseP() {
        parseD();  // Analizar las declaraciones
        parseS();  // Analizar las sentencias
    }

    // D -> id (int | string) ; D
    private void parseD() {
        if (currentToken.equals("")) return;

        if (isIdentifier(currentToken)) {
            String identifier = currentToken;
            currentToken = lexer.getToken();
            if (isType(currentToken)) {
                String type = currentToken;
                currentToken = lexer.getToken();
                if (currentToken.equals(";")) {
                    currentToken = lexer.getToken();
                    System.out.println("Declaración: " + identifier + " de tipo " + type);
                    parseD();  // Continuar con más declaraciones
                } else {
                    error("Se esperaba ';' después de la declaración.");
                }
            } else {
                error("Se esperaba un tipo de dato (int o string), pero se encontró: " + currentToken);
            }
        } else {
            error("Se esperaba un identificador, pero se encontró: " + currentToken);
        }
    }

    // S -> while E do S | id = E | print E
    private void parseS() {
        if (currentToken.equals("")) return;

        if (currentToken.equals("while")) {
            currentToken = lexer.getToken();
            parseE();  // Analizar expresión E
            if (currentToken.equals("do")) {
                currentToken = lexer.getToken();
                parseS();  // Analizar sentencia dentro de while
            } else {
                error("Se esperaba 'do' después de 'while', pero se encontró: " + currentToken);
            }
        } else if (isIdentifier(currentToken)) {
            String id = currentToken;
            currentToken = lexer.getToken();
            if (currentToken.equals("=")) {
                currentToken = lexer.getToken();
                parseE();  // Analizar expresión E
                System.out.println("Asignación: " + id + " = " + currentToken);
            } else {
                error("Se esperaba '=' para la asignación, pero se encontró: " + currentToken);
            }
        } else if (currentToken.equals("print")) {
            currentToken = lexer.getToken();
            parseE();  // Analizar expresión E
            System.out.println("Impresión de: " + currentToken);
        } else {
            error("Sentencia no válida, token encontrado: " + currentToken);
        }
    }

    // E -> id + id | id
    private void parseE() {
        if (isIdentifier(currentToken)) {
            String id1 = currentToken;
            currentToken = lexer.getToken();
            if (currentToken.equals("+")) {
                currentToken = lexer.getToken();
                if (isIdentifier(currentToken)) {
                    String id2 = currentToken;
                    currentToken = lexer.getToken();
                    System.out.println("Expresión: " + id1 + " + " + id2);
                } else {
                    error("Se esperaba un identificador después de '+', pero se encontró: " + currentToken);
                }
            } else {
                System.out.println("Expresión: " + id1);
            }
        } else {
            error("Se esperaba un identificador en la expresión, pero se encontró: " + currentToken);
        }
    }

    // Verificar si es un identificador
    private boolean isIdentifier(String token) {
        return token != null && token.matches("[a-zA-Z][a-zA-Z0-9]*") && !keywords.contains(token);
    }

    // Verificar si es un tipo de dato (int o string)
    private boolean isType(String token) {
        return token != null && (token.equals("int") || token.equals("string"));
    }

    // Método para mostrar errores
    private void error(String mensaje) {
        throw new RuntimeException("Error de sintaxis: " + mensaje);
    }
}
