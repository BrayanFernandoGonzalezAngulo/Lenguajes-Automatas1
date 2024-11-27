import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Compilador Descendente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        
        JTextArea codeArea = new JTextArea();
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setForeground(Color.BLACK);
        
        JScrollPane codeScroll = new JScrollPane(codeArea);
        JScrollPane outputScroll = new JScrollPane(outputArea);
        
        JButton compileButton = new JButton("Compilar");
        compileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code = codeArea.getText().trim();
                if (code.isEmpty()) {
                    outputArea.setText("Error: No se ha ingresado código para compilar.");
                    return;
                }
                
                Lexer lexer = new Lexer(code);
                Parser parser = new Parser(lexer);
                
                outputArea.setText(""); // Limpiar área de salida
                lexer.tokenize(); // Procesar tokens
                
                outputArea.append("Tokens:\n");
                for (String token : lexer.getTokenList()) {
                    outputArea.append("- " + token + "\n");
                }
                
                if (!lexer.getErrors().isEmpty()) {
                    outputArea.append("\nErrores léxicos:\n");
                    for (String error : lexer.getErrors()) {
                        outputArea.append("✗ " + error + "\n");
                    }
                } else {
                    parser.parse();
                    if (!parser.getSyntaxErrors().isEmpty()) {
                        outputArea.append("\nErrores sintácticos:\n");
                        for (String error : parser.getSyntaxErrors()) {
                            outputArea.append("✗ " + error + "\n");
                        }
                    } else {
                        outputArea.append("\nCompilación exitosa.");
                    }
                }
            }
        });
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(codeScroll, BorderLayout.CENTER);
        panel.add(compileButton, BorderLayout.SOUTH);
        panel.add(outputScroll, BorderLayout.EAST);
        
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}
