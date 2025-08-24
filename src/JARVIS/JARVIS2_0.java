//package JARVIS;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.net.URL;
//import java.sql.*;
//
//public class JARVIS2_0 extends JFrame {
//
//    public static final long serialVersionUID = 1L;
//    public JTextField userInputField;
//    public JTextArea outputArea1, outputArea2;
//    public JLabel headerLabel1, headerLabel2, headerLabel3, headerLabel4;
//    public Image backgroundImage;
//
//    static final String DB_URL = "jdbc:sqlserver://VIVEK\\SQLEXPRESS:1433;databaseName=JARVIS2_0;encrypt=true;trustServerCertificate=true";
//    static final String USER = "sa";
//    static final String PASS = "King@3010";
//
//    public JARVIS2_0() {
//        super("JARVIS2.0");
//
//        try {
//            backgroundImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\vivek\\OneDrive\\Desktop\\CollegeJAVA\\java4.jpg");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        JPanel contentPanel = new JPanel() {
//            /**
//			 * 
//			 */
//			private static final long serialVersionUID = 1L;
//
//			@Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                if (backgroundImage != null) {
//                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
//                }
//            }
//        };
//        contentPanel.setLayout(null);
//        setContentPane(contentPanel);
//
//        headerLabel1 = new JLabel("Welcome to JARVIS2.0");
//        headerLabel1.setFont(new Font("Times New Roman", Font.BOLD, 40));
//        headerLabel1.setHorizontalAlignment(JLabel.CENTER);
//        headerLabel1.setBounds(350, 40, 500, 40);
//        headerLabel1.setForeground(Color.GREEN);
//        contentPanel.add(headerLabel1);
//
//        headerLabel3 = new JLabel("Enter the site or app you want to open");
//        headerLabel3.setFont(new Font("Times New Roman", Font.BOLD, 25));
//        headerLabel3.setHorizontalAlignment(JLabel.CENTER);
//        headerLabel3.setBounds(300, 150, 500, 30);
//        headerLabel3.setForeground(Color.BLUE);
//        contentPanel.add(headerLabel3);
//
//        headerLabel4 = new JLabel("Input URL OR Name:");
//        headerLabel4.setFont(new Font("Roboto", Font.BOLD, 30));
//        headerLabel4.setHorizontalAlignment(JLabel.CENTER);
//        headerLabel4.setBounds(105, 120, 500, 30);
//        headerLabel4.setForeground(Color.RED);
//        contentPanel.add(headerLabel4);
//
//        userInputField = new JTextField(30);
//        userInputField.setBounds(200, 150, 750, 50);
//        userInputField.setFont(new Font("Roboto", Font.BOLD, 25));
//        userInputField.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                processInput(userInputField.getText());
//                displayOutput("Enter the website or its name to open it");
//                userInputField.setText("");
//            }
//        });
//        userInputField.setBackground(Color.LIGHT_GRAY);
//        userInputField.setForeground(Color.BLACK);
//        contentPanel.add(userInputField);
//
//        headerLabel2 = new JLabel("Reference For Next Action:");
//        headerLabel2.setFont(new Font("Roboto", Font.BOLD, 30));
//        headerLabel2.setHorizontalAlignment(JLabel.CENTER);
//        headerLabel2.setBounds(135, 220, 500, 30);
//        headerLabel2.setForeground(Color.RED);
//        contentPanel.add(headerLabel2);
//
//        outputArea1 = new JTextArea();
//        JScrollPane scrollPane = new JScrollPane(outputArea1);
//        scrollPane.setBounds(200, 250, 750, 450);
//        outputArea1.setFont(new Font("Roboto", Font.ITALIC, 15));
//        outputArea1.setEditable(false);
//        outputArea1.setBackground(Color.BLACK);
//        outputArea1.setForeground(Color.GREEN);
//        contentPanel.add(scrollPane);
//
//        JButton historyButton = new JButton("History");
//        historyButton.setFont(new Font("Times New Roman", Font.BOLD, 25));
//        historyButton.setBounds(1000, 150, 200, 50);
//        historyButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                outputArea2.setText(fetchDataFromDatabase());
//            }
//        });
//        contentPanel.add(historyButton);
//
//        outputArea2 = new JTextArea();
//        JScrollPane scrollPane1 = new JScrollPane(outputArea2);
//        scrollPane1.setBounds(1000, 200, 200, 500);
//        outputArea2.setFont(new Font("Roboto", Font.ITALIC, 15));
//        outputArea2.setEditable(false);
//        outputArea2.setForeground(Color.BLACK);
//        contentPanel.add(scrollPane1);
//
//        setSize(1250, 900);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//        setVisible(true);
//
//        String concatenatedUrls = fetchDataFromDatabase();
//        outputArea2.setText(concatenatedUrls);
//
//        JButton clearButton = new JButton("Clear");
//        clearButton.setFont(new Font("Times New Roman", Font.BOLD, 25));
//        clearButton.setBounds(1000, 700, 200, 50);
//        clearButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                clearHistory();
//            }
//        });
//        contentPanel.add(clearButton);
//    }
//
//    public boolean checkWebsiteAvailability(String urlString) {
//        try {
//            URL url = new URL(urlString);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("HEAD");
//            int responseCode = connection.getResponseCode();
//            return (responseCode == HttpURLConnection.HTTP_OK);
//        } catch (IOException e) {
//            return false;
//        }
//    }
//
//    public void clearHistory() {
//        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
//            String sql = "DELETE FROM jarvis_table";
//            try (Statement stmt = conn.createStatement()) {
//                stmt.executeUpdate(sql);
//                outputArea2.setText("History cleared successfully.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void processInput(String input) {
//        saveInputToDatabase(input, getUserInput());
//        displayOutput("Opening the site ...");
//        boolean isAvailable = false;
//        switch (input.toLowerCase()) {
//            // Your existing cases here
//        }
//
//        if (!isAvailable) {
//            displayOutput("You can enter the full address of a website to open it.");
//            displayOutput("E.g., https://www." + input + ".com");
//        }
//    }
//
//    public void saveInputToDatabase(String url, String userInput) {
//        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
//            String query = "INSERT INTO jarvis_table (URL, UserInput) VALUES (?, ?)";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//                preparedStatement.setString(1, url);
//                preparedStatement.setString(2, userInput);
//                int rowsAffected = preparedStatement.executeUpdate();
//                if (rowsAffected > 0) {
//                    displayOutput("Data saved successfully.");
//                } else {
//                    displayOutput("Failed to save data.");
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            displayOutput("Error saving data.");
//        }
//    }
//
//    public String fetchDataFromDatabase() {
//        StringBuilder urlsBuilder = new StringBuilder();
//        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
//            String query = "SELECT URL, UserInput FROM jarvis_table";
//            try (Statement statement = connection.createStatement();
//                 ResultSet resultSet = statement.executeQuery(query)) {
//                while (resultSet.next()) {
//                    String url = resultSet.getString("URL");
//                    String userInput = resultSet.getString("UserInput");
//                    urlsBuilder.append(url).append(" - ").append(userInput).append("\n");
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return urlsBuilder.toString();
//    }
//
//    public static void openURL(String url) {
//        try {
//            Desktop.getDesktop().browse(new URI(url));
//        } catch (IOException | URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public String getUserInput() {
//        return userInputField.getText();
//    }
//
//    public static void openNotepad() {
//        try {
//            Runtime.getRuntime().exec("notepad.exe");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void displayOutput(String message) {
//        outputArea1.append(message + "\n");
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(JARVIS2_0::new);
//    }
//}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package JARVIS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JARVIS extends JFrame {

    public static final long serialVersionUID = 1L;
    public JTextField userInputField;
    public JTextArea outputArea1, outputArea2;
    public JLabel headerLabel1, headerLabel2, headerLabel3, headerLabel4;
    public Image backgroundImage;
    public JComboBox<String> predefinedCasesComboBox;

    static final String DB_URL = "jdbc:sqlserver://VIVEK\\SQLEXPRESS:1433;databaseName=JARVIS;encrypt=true;trustServerCertificate=true";
    static final String USER = "sa";
    static final String PASS = "King@3010";

    public JARVIS() {
        super("JARVIS");

        try {
            backgroundImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\vivek\\OneDrive\\Desktop\\CollegeJAVA\\java4.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel contentPanel = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        contentPanel.setLayout(null);
        setContentPane(contentPanel);

        headerLabel1 = new JLabel("Welcome to JARVIS");
        headerLabel1.setFont(new Font("Times New Roman", Font.BOLD, 40));
        headerLabel1.setHorizontalAlignment(JLabel.CENTER);
        headerLabel1.setBounds(350, 40, 500, 40);
        headerLabel1.setForeground(Color.GREEN);
        contentPanel.add(headerLabel1);

        headerLabel3 = new JLabel("Enter the site or app want to open");
        headerLabel3.setFont(new Font("Times New Roman", Font.BOLD, 25));
        headerLabel3.setHorizontalAlignment(JLabel.CENTER);
        headerLabel3.setBounds(300, 150, 500, 30);
        headerLabel3.setForeground(Color.BLUE);
        contentPanel.add(headerLabel3);

        headerLabel4 = new JLabel("Input URL OR Name:");
        headerLabel4.setFont(new Font("Robot", Font.BOLD, 30));
        headerLabel4.setHorizontalAlignment(JLabel.CENTER);
        headerLabel4.setBounds(105, 120, 500, 30);
        headerLabel4.setForeground(Color.RED);
        contentPanel.add(headerLabel4);

        userInputField = new JTextField(30);
        userInputField.setBounds(200, 150, 750, 50);
        userInputField.setFont(new Font("Roboto", Font.BOLD, 25));
        userInputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processInput(userInputField.getText());
                displayOutput("Enter the website or it's name to open it");
                userInputField.setText("");
            }
        });
        userInputField.setBackground(Color.LIGHT_GRAY);
        userInputField.setForeground(Color.BLACK);
        contentPanel.add(userInputField);

        headerLabel2 = new JLabel("Refrence For Next Action:");
        headerLabel2.setFont(new Font("Robot", Font.BOLD, 30));
        headerLabel2.setHorizontalAlignment(JLabel.CENTER);
        headerLabel2.setBounds(135, 220, 500, 30);
        headerLabel2.setForeground(Color.RED);
        contentPanel.add(headerLabel2);

        outputArea1 = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputArea1);
        scrollPane.setBounds(200, 250, 750, 450);
        outputArea1.setFont(new Font("Roboto", Font.ITALIC, 15));
        outputArea1.setEditable(false);
        outputArea1.setBackground(Color.BLACK);
        outputArea1.setForeground(Color.GREEN);
        contentPanel.add(scrollPane);

        String[] predefinedCases = {
            "leetcode",
            "notepad",
            "youtube",
            "javanotes",
            // Add more predefined cases as needed
        };

        predefinedCasesComboBox = new JComboBox<>(predefinedCases);
        predefinedCasesComboBox.setBounds(200, 200, 250, 30);
        contentPanel.add(predefinedCasesComboBox);

        JButton historyButton = new JButton("History");
        historyButton.setFont(new Font("Times New Roman", Font.BOLD, 25));
        historyButton.setBounds(1000, 150, 200, 50);
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputArea2.setText(fetchDataFromDatabase());
            }
        });
        contentPanel.add(historyButton);

        outputArea2 = new JTextArea();
        JScrollPane scrollPane1 = new JScrollPane(outputArea2);
        scrollPane1.setBounds(1000, 200, 200, 500);
        outputArea2.setFont(new Font("Roboto", Font.ITALIC, 15));
        outputArea2.setEditable(false);
        outputArea2.setForeground(Color.BLACK);
        contentPanel.add(scrollPane1);

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Times New Roman", Font.BOLD, 25));
        clearButton.setBounds(1000, 700, 200, 50);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearHistory();
            }
        });
        contentPanel.add(clearButton);

        setSize(1250, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        String concatenatedUrls = fetchDataFromDatabase();
        outputArea2.setText(concatenatedUrls); 
        
    }

    public void processInput(String input) {
        if (predefinedCasesComboBox.getSelectedItem() != null) {
            input = (String) predefinedCasesComboBox.getSelectedItem();
        }

        saveInputToDatabase(input);
        displayOutput("Opening the site .. . . .");
        switch (input.toLowerCase()) {
            case "leetcode":
                openURL("https://leetcode.com/");
                break;
            case "notepad":
                openNotepad();
                break;
            case "youtube":
                openURL("https://youtube.com");
                break;
            case "javanotes":
                openURL("https://java.antosh.in/");
                break;
            // Add more cases as needed
        }
    }

    public void saveInputToDatabase(String input) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "INSERT INTO JARVIS (urL) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, input);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    displayOutput("");
                } else {
                    displayOutput("");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            displayOutput("");
        }
    }

    public String fetchDataFromDatabase() {
        StringBuilder urlsBuilder = new StringBuilder();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT url FROM JARVIS";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    String url = resultSet.getString("url");
                    urlsBuilder.append(url).append("\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return urlsBuilder.toString();
    }

    public static void openURL(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void openNotepad() {
        try {
            Runtime.getRuntime().exec("notepad.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayOutput(String message) {
        outputArea1.append(message + "\n");
    }

    public void clearHistory() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "DELETE FROM JARVIS";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sql);
                outputArea2.setText("History cleared successfully.");
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JARVIS::new);
    }
}
