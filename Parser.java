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

    // D -> id (int | string) ; D | ε
    private void parseD() {
        while (isIdentifier(currentToken)) {
            String id = currentToken;
            currentToken = lexer.getToken(); // Avanzar al siguiente token

            if (currentToken.equals("int") || currentToken.equals("string")) {
                String type = currentToken;
                currentToken = lexer.getToken(); // Avanzar al siguiente token

                if (currentToken.equals(";")) {
                    System.out.println("Declaración: " + id + " de tipo " + type);
                    currentToken = lexer.getToken(); // Avanzar al siguiente token
                } else {
                    error("Se esperaba ';' después de la declaración.");
                }
            } else {
                error("Se esperaba un tipo de dato (int o string) después del identificador.");
            }
        }
    }

    // Método para verificar si un token es un identificador
    private boolean isIdentifier(String token) {
        // Implementa la lógica para verificar si un token es un identificador válido
        // Por ejemplo, podrías verificar si el token no es una palabra clave y cumple con las reglas de un identificador
        return !keywords.contains(token) && token.matches("[a-zA-Z_][a-zA-Z0-9_]*");
    }

    // S -> id = E ; S | while E do S | print E ; S | ε
    private void parseS() {
        while (!currentToken.equals("") && !currentToken.equals("}")) {
            if (isIdentifier(currentToken)) {
                String id = currentToken;
                currentToken = lexer.getToken(); // Avanzar al siguiente token

                if (currentToken.equals("=")) {
                    currentToken = lexer.getToken(); // Avanzar al siguiente token
                    parseE(); // Analizar la expresión
                    if (currentToken.equals(";")) {
                        System.out.println("Asignación: " + id + " = expresión");
                        currentToken = lexer.getToken(); // Avanzar al siguiente token
                    } else {
                        error("Se esperaba ';' después de la asignación.");
                    }
                } else {
                    error("Se esperaba '=' después del identificador.");
                }
            } else if (currentToken.equals("while")) {
                currentToken = lexer.getToken(); // Avanzar al siguiente token
                parseE(); // Analizar la expresión
                if (currentToken.equals("do")) {
                    System.out.println("Inicio del bucle while con expresión");
                    currentToken = lexer.getToken(); // Avanzar al siguiente token
                    parseS(); // Analizar la sentencia
                    System.out.println("Fin del bucle while");
                } else {
                    error("Se esperaba 'do' después de la expresión en el while.");
                }
            } else if (currentToken.equals("print")) {
                currentToken = lexer.getToken(); // Avanzar al siguiente token
                parseE(); // Analizar la expresión
                if (currentToken.equals(";")) {
                    System.out.println("Instrucción print con expresión");
                    currentToken = lexer.getToken(); // Avanzar al siguiente token
                } else {
                    error("Se esperaba ';' después de la instrucción print.");
                }
            } else {
                error("Se esperaba una sentencia válida.");
            }
        }
    }

    // E -> id | num | ( E )
    private void parseE() {
        if (isIdentifier(currentToken) || isNumber(currentToken)) {
            System.out.println("Expresión: " + currentToken);
            currentToken = lexer.getToken(); // Avanzar al siguiente token
        } else if (currentToken.equals("(")) {
            currentToken = lexer.getToken(); // Avanzar al siguiente token
            parseE(); // Analizar la expresión
            if (currentToken.equals(")")) {
                currentToken = lexer.getToken(); // Avanzar al siguiente token
            } else {
                error("Se esperaba ')' después de la expresión.");
            }
        } else {
            error("Se esperaba una expresión válida.");
        }
    }

    // Método para verificar si un token es un número
    private boolean isNumber(String token) {
        return token.matches("\\d+");
    }

    // Método para manejar errores
    private void error(String message) {
        throw new RuntimeException("Error de sintaxis: " + message + " Se encontró: " + currentToken);
    }
}