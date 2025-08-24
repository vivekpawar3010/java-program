import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class King extends JFrame {
    private JTextField userInputField;
    private JTextArea outputArea;
    private JLabel headerLabel1, headerLabel2;

    public King() {
        super("JARVIS"); // Set the title of the JFrame

        try {
            // Load the background image
            Image backgroundImage = ImageIO.read(new File("C:\\Users\\vivek\\OneDrive\\Desktop\\CollegeJAVA\\Screenshot (11).png"));
            setContentPane(new BackgroundPanel(backgroundImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create and position the first header label
        headerLabel1 = new JLabel("Welcome to JARVIS");
        headerLabel1.setFont(new Font("Times New Roman", Font.BOLD, 30));
        headerLabel1.setHorizontalAlignment(JLabel.CENTER);
        headerLabel1.setBounds(350, 50, 500, 30);
        add(headerLabel1);

        // Create and position the user input field
        userInputField = new JTextField(20);
        userInputField.setBounds(350, 100, 500, 30);
        userInputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processInput(userInputField.getText());
                userInputField.setText("");
            }
        });
        add(userInputField);

        // Create and position the second header label
        headerLabel2 = new JLabel("Output:");
        headerLabel2.setFont(new Font("Roboto", Font.BOLD, 30));
        headerLabel2.setHorizontalAlignment(JLabel.CENTER);
        headerLabel2.setBounds(350, 150, 500, 30);
        add(headerLabel2);

        // Create and position the output area with a scroll pane
        outputArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBounds(350, 200, 500, 300);
        outputArea.setEditable(false);
        add(scrollPane);

        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void processInput(String input) {
        switch (input.toLowerCase()) {
            case "open my webapp":
                openURL("https://bit.ly/stunystew");
                break;
            case "notepad":
                openNotepad();
                break;
            case "youtube":
                openURL("https://youtube.com");
                break;
            case "instagram":
                openURL("https://instagram.com");
                break;
            case "github":
                openURL("https://github.com");
                break;
            case "music":
                openURL("https://youtu.be/dZqmlSGmlC4?si=5sT4DWsDhxkv8vtD");
                break;
            case "hi":
            case "hello":
                displayOutput("Hello, " + getUserInput() + "! How can I help you?");
                break;
            default:
                displayOutput("Such command does not exist.");
        }
    }

    private String getUserInput() {
        return userInputField.getText();
    }

    private void displayOutput(String message) {
        outputArea.append(message + "\n");
    }

    private static void openURL(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static void openNotepad() {
        try {
            Runtime.getRuntime().exec("notepad.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(King::new);
    }
}

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
