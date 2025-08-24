package studywork;

//package ATM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ATM extends JFrame {
    private static final long serialVersionUID = 1L;
    private double balance;
    private String accountNumber;
    private String pin;
    private Connection connection; // Added

    private JLabel balanceLabel;
    private JTextField depositField;
    private JTextField withdrawField;
    private JTextField pinField;
    private JTextField newPinField;

    // Database connection details
    private static final String SERVER_NAME = "AMANDEEP\\SQLEXPRESS";
    private static final String DATABASE_NAME = "Accounts";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "Amandeep@965";

    public ATM(String accountNumber, String pin, double initialBalance) {
        setAccountNumber(accountNumber);
        setPin(pin);
        this.balance = initialBalance;

        setTitle("ATM");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Establish database connection
//        try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            String connectionUrl = "jdbc:sqlserver://" + SERVER_NAME + ":1433;databaseName=" + DATABASE_NAME + ";user=" + USERNAME + ";password=" + PASSWORD;
//            connection = DriverManager.getConnection(connectionUrl);
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Failed to connect to database.");
//            System.exit(1);
//        }

        // Create components and set layout
        createComponents();
        setLayouts();
        addActionListeners();

        setVisible(true);
    }

    private void createComponents() {
        JLabel accountLabel = new JLabel("Account Number:");
        JLabel pinLabel = new JLabel("PIN:");
        JLabel depositLabel = new JLabel("Deposit Amount:");
        JLabel withdrawLabel = new JLabel("Withdraw Amount:");
        balanceLabel = new JLabel("Balance: $" + balance);
        JButton checkBalanceButton = new JButton("Check Balance");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton changePinButton = new JButton("Change PIN");
        JButton exitButton = new JButton("Exit");

        depositField = new JTextField();
        withdrawField = new JTextField();
        pinField = new JTextField();
        newPinField = new JTextField();

        // Add components to the frame
        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(accountLabel);
        panel.add(pinLabel);
        panel.add(new JLabel(accountNumber));
        panel.add(pinField);
        panel.add(depositLabel);
        panel.add(withdrawLabel);
        panel.add(depositField);
        panel.add(withdrawField);
        panel.add(changePinButton);
        panel.add(newPinField);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 3));
        buttonPanel.add(checkBalanceButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(exitButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setLayouts() {
        // Set layouts and other properties
        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        JPanel backgroundPanel = new JPanel(new BorderLayout());
        backgroundPanel.add(new JLabel(new ImageIcon("background.jpg")));
        contentPane.add(backgroundPanel, BorderLayout.CENTER);
    }

    private void addActionListeners() {
        // Instantiate JButtons
        JButton depositButton = new JButton();
        JButton withdrawButton = new JButton();
        JButton changePinButton = new JButton();
        JButton checkBalanceButton = new JButton();
        JButton exitButton = new JButton();

        // Add action listeners to buttons
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(depositField.getText());
                    deposit(amount);
                    balanceLabel.setText("Balance: $" + balance);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid deposit amount.");
                }
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(withdrawField.getText());
                    withdraw(amount);
                    balanceLabel.setText("Balance: $" + balance);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid withdraw amount.");
                }
            }
        });

        changePinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPin = newPinField.getText();
                changePin(newPin);
            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Your balance is: $" + balance);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Thank you for using the ATM.");
                System.exit(0);
            }
        });
    }

    private void deposit(double amount) {
        balance += amount;
        JOptionPane.showMessageDialog(null, "Deposited: $" + amount + "\nNew balance is: $" + balance);
        // Update balance in the database
        try {
            connection.createStatement().executeUpdate("UPDATE Accounts SET Balance = " + balance + " WHERE AccountNumber = '" + accountNumber + "'");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to update balance.");
        }
    }

    private void withdraw(double amount) {
        if (amount > balance) {
            JOptionPane.showMessageDialog(null, "Insufficient funds");
        } else {
            balance -= amount;
            JOptionPane.showMessageDialog(null, "Withdrawn: $" + amount + "\nNew balance is: $" + balance);
            // Update balance in the database
            try {
                connection.createStatement().executeUpdate("UPDATE Accounts SET Balance = " + balance + " WHERE AccountNumber = '" + accountNumber + "'");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to update balance.");
            }
        }
    }

    private void changePin(String newPin) {
        this.pin = newPin;
        JOptionPane.showMessageDialog(null, "PIN changed successfully.");
        // Update PIN in the database
        try {
            connection.createStatement().executeUpdate("UPDATE Accounts SET PIN = '" + pin + "' WHERE AccountNumber = '" + accountNumber + "'");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to update PIN.");
        }
    }

    private void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    private void setPin(String pin) {
        this.pin = pin;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ATM("123456", "1234", 1000.0);
            }
        });
    }
}