// Main.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Compilador");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea codeArea = new JTextArea(10, 40);
        codeArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(codeArea);

        JTextArea outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);

        JButton compileButton = new JButton("Compilar");

        compileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code = codeArea.getText();
                Lexer lexer = new Lexer(code);
                Parser parser = new Parser(lexer, outputArea);

                outputArea.setText(""); // Limpiar salida
                try {
                    parser.parse();
                    outputArea.append("Compilación exitosa.\n");
                } catch (Exception ex) {
                    outputArea.append("Error: " + ex.getMessage() + "\n");
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.NORTH);
        panel.add(compileButton, BorderLayout.CENTER);
        panel.add(outputScrollPane, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }
}
