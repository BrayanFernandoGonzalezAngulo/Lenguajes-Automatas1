import java.util.Scanner;

public class Compilador {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Bucle infinito para seguir procesando varios códigos
        while (true) {
            System.out.println("Introduce el código (o 'salir' para terminar):");
            String codigo = scanner.nextLine();

            // Si el usuario escribe 'salir', termina el ciclo
            if (codigo.equalsIgnoreCase("salir")) {
                System.out.println("Saliendo del compilador.");
                break;
            }

            // Crear un objeto Lexer y Parser
            Lexer lexer = new Lexer(codigo);
            Parser parser = new Parser(lexer);

            // Realizar el análisis sintáctico
            parser.parse();
        }

        scanner.close();
    }
}
