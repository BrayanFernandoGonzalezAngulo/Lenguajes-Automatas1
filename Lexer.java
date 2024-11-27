import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private String input;
    private int index = 0;
    
    // Definir las palabras clave, tipos de datos, operadores y delimitadores
    private final String[] keywords = {"while", "do", "print", "int", "string"};
    private final String[] types = {"int", "string"};
    private final String delimitador = ";";
    private final String operador = "=";
    private List<String> tokens = new ArrayList<>();
    
    public Lexer(String input) {
        this.input = input;
    }

    // Obtener todos los tokens
    public List<String> getTokens() {
        while (index < input.length()) {
            String token = getToken();
            if (!token.isEmpty()) {
                tokens.add(token);
            }
        }
        return tokens;
    }

    // Obtener el siguiente token
    public String getToken() {
        skipWhitespace();
        
        if (index >= input.length()) {
            return ""; // Fin de la entrada
        }

        char currentChar = input.charAt(index);
        
        // Verificar si es un carácter no esperado
        if (!Character.isLetterOrDigit(currentChar) && currentChar != '=' && currentChar != ';') {
            System.out.println("Error: Caracter no reconocido '" + currentChar + "'");
            index++; // Avanzar para no quedar en un loop infinito
            return getToken(); // Intentar obtener el siguiente token
        }

        // Identificar palabras clave, identificadores o números
        if (Character.isLetter(currentChar)) {
            String token = readIdentifier();
            if (isKeyword(token)) {
                return token; // Palabra clave
            }
            return token; // Identificador
        }
        
        // Verificar si es un número (aunque no se está usando en este ejemplo)
        if (Character.isDigit(currentChar)) {
            return readNumber(); // Leer un número (puedes ignorar o modificar según tus necesidades)
        }
        
        // Verificar operadores
        if (currentChar == '=') {
            index++;
            return operador; // Operador '='
        }
        
        // Verificar delimitadores
        if (currentChar == ';') {
            index++;
            return delimitador; // Delimitador ';'
        }
        
        // Si no es ninguno de estos, es un error
        System.out.println("Error: Token desconocido.");
        return ""; // Devolver vacío o manejarlo según sea necesario
    }

    private void skipWhitespace() {
        while (index < input.length() && Character.isWhitespace(input.charAt(index))) {
            index++;
        }
    }

    private String readIdentifier() {
        StringBuilder sb = new StringBuilder();
        while (index < input.length() && (Character.isLetter(input.charAt(index)) || Character.isDigit(input.charAt(index)))) {
            sb.append(input.charAt(index));
            index++;
        }
        return sb.toString();
    }

    private String readNumber() {
        StringBuilder sb = new StringBuilder();
        while (index < input.length() && Character.isDigit(input.charAt(index))) {
            sb.append(input.charAt(index));
            index++;
        }
        return sb.toString();
    }

    private boolean isKeyword(String token) {
        for (String keyword : keywords) {
            if (token.equals(keyword)) {
                return true;
            }
        }
        return false;
    }

    // Verificar si es un tipo de dato (int o string)
    public boolean isType(String token) {
        for (String type : types) {
            if (token.equals(type)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Lexer lexer = new Lexer("int x = 10; print x;");
        List<String> tokens = lexer.getTokens();
        for (String token : tokens) {
            System.out.println(token);
        }
    }
}
