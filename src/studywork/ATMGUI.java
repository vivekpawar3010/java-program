package studywork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ATMGUI extends JFrame {
    private double balance;
    private String accountNumber;
    private String pin;

    private JLabel balanceLabel;
    private JTextField depositField;
    private JTextField withdrawField;
    private JTextField pinField;
    private JTextField newPinField;

    public ATMGUI(String accountNumber, String pin, double initialBalance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = initialBalance;

        setTitle("ATM");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a JLabel to hold the background image
        JLabel background = new JLabel(new ImageIcon("background.jpg"));
        setContentPane(background);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.setOpaque(false); // Make the panel transparent

        JLabel accountLabel = new JLabel("Account Number:");
        JLabel pinLabel = new JLabel("PIN:");
        JLabel depositLabel = new JLabel("Deposit Amount:");
        JLabel withdrawLabel = new JLabel("Withdraw Amount:");
        JLabel newPinLabel = new JLabel("New PIN:");

        balanceLabel = new JLabel("Balance: $" + balance);
        JButton checkBalanceButton = new JButton("Check Balance");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton changePinButton = new JButton("Change PIN");
        JButton exitButton = new JButton("Exit");

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Your balance is: $" + balance);
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double amount = Double.parseDouble(depositField.getText());
                deposit(amount);
                balanceLabel.setText("Balance: $" + balance);
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double amount = Double.parseDouble(withdrawField.getText());
                withdraw(amount);
                balanceLabel.setText("Balance: $" + balance);
            }
        });

        changePinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPin = newPinField.getText();
                changePin(newPin);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Thank you for using the ATM.");
                System.exit(0);
            }
        });

        depositField = new JTextField();
        withdrawField = new JTextField();
        pinField = new JTextField();
        newPinField = new JTextField();

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

        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 3));
        buttonPanel.add(checkBalanceButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void deposit(double amount) {
        balance += amount;
        JOptionPane.showMessageDialog(null, "Deposited: $" + amount + "\nNew balance is: $" + balance);
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            JOptionPane.showMessageDialog(null, "Insufficient funds");
        } else {
            balance -= amount;
            JOptionPane.showMessageDialog(null, "Withdrawn: $" + amount + "\nNew balance is: $" + balance);
        }
    }

    public void changePin(String newPin) {
        pin = newPin;
        JOptionPane.showMessageDialog(null, "PIN changed successfully.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ATMGUI("123456", "1234", 1000.0);
            }
        });
    }
}

