package studywork;

import javax.swing.*;
import java.awt.*;

public class TextFieldExample extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TextFieldExample() {
        super("Text Field Example");

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null); // Set null layout

        // Create a text field and set its bounds (position and size)
        JTextField textField = new JTextField();
        textField.setBounds(50, 50, 200, 30); // x, y, width, height
        contentPanel.add(textField); // Add text field to the panel

        // Create a label for demonstration purposes (not necessary for the text field)
        JLabel label = new JLabel("Enter text:");
        label.setBounds(50, 20, 100, 20); // x, y, width, height
        contentPanel.add(label); // Add label to the panel

        setContentPane(contentPanel);
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TextFieldExample::new);
    }
}
