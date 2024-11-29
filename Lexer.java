import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private String input;
    private int index = 0;

    // Definir las palabras clave, tipos de datos, operadores y delimitadores
    private final String[] keywords = {"while", "do", "print", "int", "string"};
    private final String[] types = {"int", "string"};
    private final String delimitador = ";";
    private final String[] operadores = {"=", "+"};
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

        String remainingInput = input.substring(index);
        
        // Verificar operadores y delimitadores
        for (String operador : operadores) {
            if (remainingInput.startsWith(operador)) {
                index += operador.length();
                return operador;
            }
        }
        if (remainingInput.startsWith(delimitador)) {
            index += delimitador.length();
            return delimitador;
        }

        // Identificar palabras clave, identificadores o números
        Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]*");
        Matcher matcher = pattern.matcher(remainingInput);
        if (matcher.find()) {
            String token = matcher.group();
            index += token.length();
            if (isKeyword(token)) {
                return token; // Palabra clave
            }
            return token; // Identificador
        }

        // Verificar si es un número
        pattern = Pattern.compile("^\\d+");
        matcher = pattern.matcher(remainingInput);
        if (matcher.find()) {
            String token = matcher.group();
            index += token.length();
            return token; // Número
        }

        // Si no es ninguno de estos, es un error
        System.out.println("Error: Token desconocido '" + remainingInput.charAt(0) + "'");
        index++; // Avanzar para no quedar en un loop infinito
        return getToken(); // Intentar obtener el siguiente token
    }

    private void skipWhitespace() {
        while (index < input.length() && Character.isWhitespace(input.charAt(index))) {
            index++;
        }
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

    public List<String> getKeywords() {
        List<String> keywordList = new ArrayList<>();
        for (String keyword : keywords) {
            keywordList.add(keyword);
        }
        return keywordList;
    }
}
