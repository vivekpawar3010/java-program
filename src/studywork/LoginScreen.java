package studywork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginScreen extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
    private JPasswordField passwordField;
    private Connection connection;

    public LoginScreen(Connection conn) {
        connection = conn;
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        add(usernameLabel);

        usernameField = new JTextField();
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        add(passwordLabel);

        passwordField = new JPasswordField();
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the login screen
                new RegisterScreen(connection); // Open the register screen
            }
        });
        add(registerButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Check credentials in the database
            // If valid, open the main banking screen
            // Otherwise, show an error message
        }
    }

    public static void main(String[] args) {
        try {
            // Connect to your SQL Server database
            String jdbcUrl = "jdbc:sqlserver://VIVEK\\SQLEXPRESS:1433;databaseName=BankStore;encrypt=true;trustServerCertificate=true";
            String user = "sa";
            String password = "King@3010";
            Connection connection = DriverManager.getConnection(jdbcUrl, user, password);

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new LoginScreen(connection);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to connect to the database", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
