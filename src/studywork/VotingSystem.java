package studywork;
import javax.swing.*;
import java.sql.*;

public class VotingSystem extends JFrame {
    private static final long serialVersionUID = 1L;
    private static int p1 = 0;
    private static int p2 = 0;
    private static int p3 = 0;
    private JTextField nameField;
    private JTextField ageField;
    private JTextField adharField;
    private JRadioButton partyARadio;
    private JRadioButton partyBRadio;
    private JRadioButton partyCRadio;

    public VotingSystem() {
        super("Online Voting System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        
        setLayout(null);

        JLabel nameLabel = new JLabel("Enter Name:");
        nameLabel.setBounds(40, 40, 100, 30);
        add(nameLabel);

        JLabel ageLabel = new JLabel("Enter Age:");
        ageLabel.setBounds(40, 80, 100, 30);
        add(ageLabel);

        JLabel adharLable = new JLabel("Enter Adhar Number:");
        adharLable.setBounds(40, 120, 100, 30);
        add(adharLable);
        
        JLabel partyLabel = new JLabel("Select Party:");
        partyLabel.setBounds(40, 160, 100, 30);
        add(partyLabel);

        nameField = new JTextField(20);
        nameField.setBounds(160, 40, 180, 30);
        add(nameField);

        ageField = new JTextField(20);
        ageField.setBounds(160, 80, 180, 30);
        add(ageField);
        
        adharField = new JTextField(20);
        adharField.setBounds(160, 120, 180, 30);
        add(adharField);

        partyARadio = new JRadioButton("Party A");
        partyARadio.setBounds(160, 160, 100, 30);
        add(partyARadio);

        partyBRadio = new JRadioButton("Party B");
        partyBRadio.setBounds(160, 200, 100, 30);
        add(partyBRadio);

        partyCRadio = new JRadioButton("Party C");
        partyCRadio.setBounds(160, 240, 100, 30);
        add(partyCRadio);

        ButtonGroup partyGroup = new ButtonGroup();
        partyGroup.add(partyARadio);
        partyGroup.add(partyBRadio);
        partyGroup.add(partyCRadio);

        JButton submitButton = new JButton("Submit Vote");
        submitButton.setBounds(160, 280, 180, 30);
        add(submitButton);
        submitButton.addActionListener(e -> submitVote());

        JButton checkResultsButton = new JButton("Check Results");
        checkResultsButton.setBounds(160, 320, 180, 30);
        add(checkResultsButton);
        checkResultsButton.addActionListener(e -> checkResults());

        setVisible(true);
    }

    private void submitVote() {
        String name = nameField.getText();
        String ageStr = ageField.getText();
        String AdherStr = adharField.getText();
        int age;

        // Validate input
        if (name.isEmpty() || ageStr.isEmpty() || AdherStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter name, age & Adhar.");
            return;
        }
        
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid age. Please enter a valid number.");
            return;
        }

        // Condition 1: Check age limit
        if (age < 18) {
            JOptionPane.showMessageDialog(this, "You must be at least 18 years old to vote.");
            return;
        }
        
        //Condition 2: Cheak the Adhar number
        

        // Condition 3: Check if the voter has already voted
        if (voterHasVoted(name)) {
            JOptionPane.showMessageDialog(this, "You have already voted. One vote per person allowed.");
            return;
        }

        String party = "";
        if (partyARadio.isSelected()) {
            party = "Party A";
            p1++;
        } else if (partyBRadio.isSelected()) {
            party = "Party B";
            p2++;
        } else if (partyCRadio.isSelected()) {
            party = "Party C";
            p3++;
        } else {
            JOptionPane.showMessageDialog(this, "Please select a party.");
            return;
        }

        String url = "jdbc:sqlserver://VIVEK\\SQLEXPRESS:1433;databaseName=VOTING;encrypt=true;trustServerCertificate=true";
        String user = "sa";
        String password = "King@3010";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String insertQuery = "INSERT INTO VOTING(name, age, party) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, age);
                preparedStatement.setString(3, party);
                preparedStatement.executeUpdate();
            }
            JOptionPane.showMessageDialog(this, "Vote submitted successfully.");

            // Condition 2: Refresh the application for the next voter
            SwingUtilities.invokeLater(() -> {
                VotingSystem newVotingSystem = new VotingSystem();
                newVotingSystem.setVisible(true);
            });
            dispose(); // Close the current frame
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error submitting vote: " + e.getMessage());
        }

        // Reset fields after submission (will not execute in the modified code due to frame disposal)
        nameField.setText("");
        ageField.setText("");
        partyARadio.setSelected(false);
        partyBRadio.setSelected(false);
        partyCRadio.setSelected(false);
    }

    private void checkResults() {
        if (p1 > p2 && p1 > p3) {
            JOptionPane.showMessageDialog(this, "Party A " + p1 + "\nParty B " + p2 + "\nParty C " + p3 + "\n\nPARTY A has a lead");
        } else if (p2 > p1 && p2 > p3) {
            JOptionPane.showMessageDialog(this, "Party A " + p1 + "\nParty B " + p2 + "\nParty C " + p3 + "\n\nPARTY B has a lead");
        } else if (p3 > p1 && p3 > p2) {
            JOptionPane.showMessageDialog(this, "Party A " + p1 + "\nParty B " + p2 + "\nParty C " + p3 + "\n\nPARTY C has a lead");
        } else {
            JOptionPane.showMessageDialog(this, "The vote is tied!");
        }
    }

    private boolean voterHasVoted(String name) {
        // You need to implement this method to check if the voter has already voted in the database
        // For demonstration purposes, I'm assuming it always returns false
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VotingSystem::new);
    }
}


