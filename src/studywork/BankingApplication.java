package studywork;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class BankingApplication extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel balanceLabel;
    private Connection connection;

    public BankingApplication() {
        setTitle("Banking Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(null); // Set layout to null

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(10, 10, 80, 25); // Set bounds for username label
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(100, 10, 180, 25); // Set bounds for username field
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 40, 80, 25); // Set bounds for password label
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 40, 180, 25); // Set bounds for password field
        add(passwordField);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(100, 70, 80, 25); // Set bounds for register button
        registerButton.addActionListener(this);
        add(registerButton);

        JButton loginButton = new JButton("Login"); // Added login button
        loginButton.setBounds(190, 70, 80, 25); // Set bounds for login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });
        add(loginButton);

        balanceLabel = new JLabel("Balance: $0.00");
        balanceLabel.setBounds(10, 100, 150, 25); // Set bounds for balance label
        add(balanceLabel);

        JLabel depositLabel = new JLabel("Enter Deposit Amount:");
        depositLabel.setBounds(10, 130, 150, 25); // Set bounds for deposit label
        add(depositLabel);

        JTextField depositField = new JTextField();
        depositField.setBounds(160, 130, 120, 25); // Set bounds for deposit field
        add(depositField);

        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(10, 160, 80, 25); // Set bounds for deposit button
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String depositAmountString = depositField.getText();
                try {
                    double depositAmount = Double.parseDouble(depositAmountString);
                    deposit(depositAmount);
                    depositField.setText(""); // Clear the deposit field after depositing
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(BankingApplication.this, "Invalid amount", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(depositButton);

        JLabel withdrawLabel = new JLabel("Enter Withdrawal Amount:");
        withdrawLabel.setBounds(10, 190, 150, 25); // Set bounds for withdraw label
        add(withdrawLabel);

        JTextField withdrawField = new JTextField();
        withdrawField.setBounds(160, 190, 120, 25); // Set bounds for withdraw field
        add(withdrawField);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(10, 220, 80, 25); // Set bounds for withdraw button
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String withdrawAmountString = withdrawField.getText();
                try {
                    double withdrawAmount = Double.parseDouble(withdrawAmountString);
                    withdraw(withdrawAmount);
                    withdrawField.setText(""); // Clear the withdraw field after withdrawing
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(BankingApplication.this, "Invalid amount", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(withdrawButton);

        try {
            // Connect to your SQL Server database
            String jdbcUrl = "jdbc:sqlserver://VIVEK\\SQLEXPRESS:1433;databaseName=BankStore;encrypt=true;trustServerCertificate=true";
            String user = "sa";
            String password = "King@3010";
            connection = DriverManager.getConnection(jdbcUrl, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to connect to the database", "Error", JOptionPane.ERROR_MESSAGE);
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Register")) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username and password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                registerUser(username, password);
            }
        }
    }

    private void registerUser(String username, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO accounts (username, password, balance) VALUES (?, ?, ?)");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setDouble(3, 0.0); // Initial balance is 0
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Registration successful", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to register user", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to register user", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deposit(double amount) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE username = ?");
            statement.setDouble(1, amount);
            statement.setString(2, usernameField.getText());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                updateBalanceLabel();
                JOptionPane.showMessageDialog(this, "Deposit successful", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to deposit amount", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to deposit amount", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void withdraw(double amount) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE username = ? AND balance >= ?");
            statement.setDouble(1, amount);
            statement.setString(2, usernameField.getText());
            statement.setDouble(3, amount);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                updateBalanceLabel();
                JOptionPane.showMessageDialog(this, "Withdrawal successful", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to withdraw amount", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to withdraw amount", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateBalanceLabel() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT balance FROM accounts WHERE username = ?");
            statement.setString(1, usernameField.getText());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                double balance = resultSet.getDouble("balance");
                balanceLabel.setText("Balance: $" + balance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to update balance label", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loginUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM accounts WHERE username = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // User exists, fetch user data and update UI
                double balance = resultSet.getDouble("balance");
                balanceLabel.setText("Balance: $" + balance);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to login", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BankingApplication();
            }
        });
    }
}

