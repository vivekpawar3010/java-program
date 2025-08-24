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
import java.sql.*;

public class JARVIS extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField userInputField;
    private JTextArea outputArea1 , outputArea2;
    private JLabel headerLabel1, headerLabel2, headerLabel3, headerLabel4;
    private Image backgroundImage;
    
    static final String DB_URL = "jdbc:sqlserver://VIVEK\\SQLEXPRESS:1433;databaseName=JARVIS;encrypt=true;trustServerCertificate=true";
	 static final String USER = "sa";
	 static final String PASS = "King@3010";

    public JARVIS() {
        super("JARVIS"); // Set the title of the JFrame

        try {
            // Load the background image
            backgroundImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\vivek\\OneDrive\\Desktop\\CollegeJAVA\\java4.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and position the custom panel
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        contentPanel.setLayout(null); // Use null layout for precise positioning
        setContentPane(contentPanel);

        // Create and position the first header label
        headerLabel1 = new JLabel("Welcome to JARVIS");
        headerLabel1.setFont(new Font("Times New Roman", Font.BOLD, 40));
        headerLabel1.setHorizontalAlignment(JLabel.CENTER);
        headerLabel1.setBounds(350, 40, 500, 40);
        headerLabel1.setForeground(Color.GREEN); // Set the color to white
        contentPanel.add(headerLabel1);
        headerLabel3 = new JLabel("Enter the site or app want to open");
        headerLabel3.setFont(new Font("Times New Roman", Font.BOLD, 25));
        headerLabel3.setHorizontalAlignment(JLabel.CENTER);
        headerLabel3.setBounds(300, 150, 500, 30);
        headerLabel3.setForeground(Color.BLUE); // Set the color to white
        contentPanel.add(headerLabel3);
        headerLabel4 = new JLabel("Input URL OR Name:");
        headerLabel4.setFont(new Font("Robot", Font.BOLD, 30));
        headerLabel4.setHorizontalAlignment(JLabel.CENTER);
        headerLabel4.setBounds(105, 120, 500, 30);
        headerLabel4.setForeground(Color.RED)
        ; // Set the color to white
        contentPanel.add(headerLabel4);

        // Create and position the user input field
        userInputField = new JTextField(30);
        userInputField.setBounds(200, 150, 750, 50);
        userInputField.setFont(new Font("Roboto", Font.BOLD, 25));
        userInputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processInput(userInputField.getText());
                userInputField.setText("");
            }
        });
        userInputField.setBackground(Color.LIGHT_GRAY); // Set the background color to light gray
        userInputField.setForeground(Color.BLACK); // Set the text color to black
        contentPanel.add(userInputField);

        // Create and position the second header label
        headerLabel2 = new JLabel("Refrence For Next Action:");
        headerLabel2.setFont(new Font("Robot", Font.BOLD, 30));
        headerLabel2.setHorizontalAlignment(JLabel.CENTER);
        headerLabel2.setBounds(135, 220, 500, 30);
        headerLabel2.setForeground(Color.RED); // Set the color to white
        contentPanel.add(headerLabel2);

        // Create and position the output area with a scroll pane
        outputArea1 = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputArea1);
        scrollPane.setBounds(200, 250, 750, 450);
        outputArea1.setFont(new Font("Roboto", Font.ITALIC, 15));
        outputArea1.setEditable(false);
        // outputArea.setBackground(Color.GRAY); // Set the background color to black
        outputArea1.setForeground(Color.BLUE); // Set the text color to white
        contentPanel.add(scrollPane);
        
     // Create the history button
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

        // Create the textarea to show the history
        outputArea2 = new JTextArea();
        JScrollPane scrollPane1 = new JScrollPane(outputArea2);
        scrollPane1.setBounds(1000,200,200, 500);
        outputArea2.setFont(new Font("Roboto", Font.ITALIC, 15));
        outputArea2.setEditable(false);
        // outputArea.setBackground(Color.GRAY); // Set the background color to black
        outputArea2.setForeground(Color.BLACK); // Set the text color to white
        contentPanel.add(scrollPane1);

        setSize(1250, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        
        


        String concatenatedUrls = fetchDataFromDatabase();
        outputArea2.setText(concatenatedUrls); // Display the concatenated URLs in the output area
        

    }

    private boolean checkWebsiteAvailability(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (responseCode == HttpURLConnection.HTTP_OK);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * @param input
     */
    private void processInput(String input) {
    	saveInputToDatabase(input);
    	 displayOutput("Opening the site .. . . .");
        switch (input.toLowerCase()) {
            case "open my webapp":
                openURL("https://bit.ly/stunystew");
                break;
            case "write a note":
            case "notepad":
                openNotepad();
                break;
            case "youtube":
                openURL("https://youtube.com");
                break;
            case "javanotes":
                openURL("https://java.antosh.in/"
                		+ "");
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
            case "sveri":
            case "college website":
            	openURL("https://coe.sveri.ac.in/");
            	break;
            case "google":
                openURL("https://google.com");
                break;
            case "facebook":
                openURL("https://facebook.com");
                break;
            case "twitter":
                openURL("https://twitter.com");
                break;
            case "wikipedia":
                openURL("https://wikipedia.org");
                break;
            case "linkedin":
                openURL("https://linkedin.com");
                break;
            case "reddit":
                openURL("https://reddit.com");
                break;
            case "amazon":
                openURL("https://amazon.com");
                break;
            case "whatsapp":
                openURL("https://whatsapp.com");
                break;
            case "netflix":
                openURL("https://netflix.com");
                break;
            case "pinterest":
                openURL("https://pinterest.com");
                break;
            case "quora":
                openURL("https://quora.com");
                break;
            case "duckduckgo":
                openURL("https://duckduckgo.com");
                break;
            case "suggetion for code":
            case "stackoverflow":
                openURL("https://stackoverflow.com");
                break;
            case "medium":
                openURL("https://medium.com");
                break;
            case "twitch":
                openURL("https://twitch.tv");
                break;
            case "play music":
            case "spotify":
                openURL("https://spotify.com");
                break;
            case "imdb":
                openURL("https://imdb.com");
                break;
            case "cnn":
                openURL("https://cnn.com");
                break;
            case "bbc":
                openURL("https://bbc.com");
                break;
            case "etsy":
                openURL("https://etsy.com");
                break;
            case "hacker news":
                openURL("https://news.ycombinator.com");
                break;
            case "ted":
                openURL("https://ted.com");
                break;
            case "gmail":
            case "write the email":
                openURL("https://mail.google.com");
                break;
            case "yahoo":
                openURL("https://yahoo.com");
                break;
            case "stack exchange":
                openURL("https://stackexchange.com");
                break;
            case "adobe":
                openURL("https://creativecloud.adobe.com");
                break;
            case "coursera":
                openURL("https://coursera.org");
                break;
            case "quizlet":
                openURL("https://quizlet.com");
                break;
            case "khan academy":
                openURL("https://www.khanacademy.org");
                break;
            case "chegg":
                openURL("https://www.chegg.com");
                break;
            case "duolingo":
                openURL("https://www.duolingo.com");
                break;
            case "ted-ed":
                openURL("https://ed.ted.com");
                break;
            case "mathway":
                openURL("https://www.mathway.com");
                break;
            case "sparknotes":
                openURL("https://www.sparknotes.com");
                break;
            case "grammarly":
                openURL("https://www.grammarly.com");
                break;
            case "edx":
                openURL("https://www.edx.org");
                break;
            case "memrise":
                openURL("https://www.memrise.com");
                break;
            case "codecademy":
                openURL("https://www.codecademy.com");
                break;
            case "wolfram alpha":
                openURL("https://www.wolframalpha.com");
                break;
            case "quizizz":
                openURL("https://www.quizizz.com");
                break;
            case "scratch":
                openURL("https://scratch.mit.edu");
                break;
            case "notion":
                openURL("https://www.notion.so");
                break;
            case "draw.io":
                openURL("https://www.draw.io");
                break;
            case "researchgate":
                openURL("https://www.researchgate.net");
                break;
            case "onenote":
                openURL("https://www.onenote.com");
                break;
            case "prezi":
                openURL("https://www.prezi.com");
                break;
            default:
            if (checkWebsiteAvailability(input)) {
                openURL(input);
            } else {
            	displayOutput("Site Opening Failed * * * * * *");
                displayOutput("The website is not available.\n You anc enter the full address of website to open it. \n eg.https://www."+input+".com");
            }
                
        }
    }
    
    private void saveInputToDatabase(String input) {//TO SAVE THE INPUT
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
    

    private String getUserInput() {
        return userInputField.getText();
    }

    private void displayOutput(String message) {
        outputArea1.append(message + "\n");
    }
    
    private String fetchDataFromDatabase() {//featch the data from the database
        StringBuilder urlsBuilder = new StringBuilder();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT url FROM JARVIS";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    String url = resultSet.getString("url");
                    urlsBuilder.append(url).append("           \n"); // Append each URL to the StringBuilder
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return urlsBuilder.toString(); // Return the concatenated URLs as a single String
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
        SwingUtilities.invokeLater(JARVIS::new);
    }
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
//import javax.swing.SwingUtilities;
//
//
//public class ok extends JFrame {
//
//    private static final long serialVersionUID = 1L;
//    private JTextField userInputField;
//    private JTextArea outputArea1, outputArea2;
//    private JLabel headerLabel1, headerLabel2, headerLabel3, headerLabel4;
//    private Image backgroundImage;
//
//    static final String DB_URL = "jdbc:sqlserver://VIVEK\\SQLEXPRESS:1433;databaseName=JARVIS;encrypt=true;trustServerCertificate=true";
//    static final String USER = "sa";
//    static final String PASS = "King@3010";
//
//    public ok() {
//        super("JARVIS"); // Set the title of the JFrame
//
//        try {
//            // set the background image
//            backgroundImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\vivek\\OneDrive\\Desktop\\CollegeJAVA\\java4.jpg");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Create and position the custom panel
//        JPanel contentPanel = new JPanel() {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                if (backgroundImage != null) {
//                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
//                }
//            }
//        };
//        contentPanel.setLayout(null); // Use null layout for set any position
//        setContentPane(contentPanel);
//
//        // Create and position the first header label
//        headerLabel1 = new JLabel("Welcome to JARVIS");
//        headerLabel1.setFont(new Font("Times New Roman", Font.BOLD, 40));
//        headerLabel1.setHorizontalAlignment(JLabel.CENTER);
//        headerLabel1.setBounds(350, 40, 500, 40);
//        headerLabel1.setForeground(Color.GREEN);
//        contentPanel.add(headerLabel1);
//        headerLabel3 = new JLabel("Enter the site or app want to open");
//        headerLabel3.setFont(new Font("Times New Roman", Font.BOLD, 25));
//        headerLabel3.setHorizontalAlignment(JLabel.CENTER);
//        headerLabel3.setBounds(300, 150, 500, 30);
//        headerLabel3.setForeground(Color.BLUE);
//        contentPanel.add(headerLabel3);
//        headerLabel4 = new JLabel("Input URL OR Name:");
//        headerLabel4.setFont(new Font("Robot", Font.BOLD, 30));
//        headerLabel4.setHorizontalAlignment(JLabel.CENTER);
//        headerLabel4.setBounds(105, 120, 500, 30);
//        headerLabel4.setForeground(Color.RED);
//        contentPanel.add(headerLabel4);
//
//        // Create and position the user input field
//        userInputField = new JTextField(30);
//        userInputField.setBounds(200, 150, 750, 50);
//        userInputField.setFont(new Font("Roboto", Font.BOLD, 25));
//        userInputField.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                processInput(userInputField.getText());
//                displayOutput("Enter the website or it's name to open it");
//                userInputField.setText("");
//            }
//        });
//        userInputField.setBackground(Color.LIGHT_GRAY); // Set the background color to light gray
//        userInputField.setForeground(Color.BLACK); // Set the text color to black
//        contentPanel.add(userInputField);
//
//        // Create and position the second header label
//        headerLabel2 = new JLabel("Refrence For Next Action:");
//        headerLabel2.setFont(new Font("Robot", Font.BOLD, 30));
//        headerLabel2.setHorizontalAlignment(JLabel.CENTER);
//        headerLabel2.setBounds(135, 220, 500, 30);
//        headerLabel2.setForeground(Color.RED); // Set the color to white
//        contentPanel.add(headerLabel2);
//
//        // Create and position the output area with a scroll pane
//        outputArea1 = new JTextArea();
//        JScrollPane scrollPane = new JScrollPane(outputArea1);
//        scrollPane.setBounds(200, 250, 750, 450);
//        outputArea1.setFont(new Font("Roboto", Font.ITALIC, 15));
//        outputArea1.setEditable(false);
//        outputArea1.setBackground(Color.BLACK);
//        outputArea1.setForeground(Color.GREEN);
//        contentPanel.add(scrollPane);
//
//        // Create the history button
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
//        // Create the textarea to show the history
//        outputArea2 = new JTextArea();
//        JScrollPane scrollPane1 = new JScrollPane(outputArea2);
//        scrollPane1.setBounds(1000, 200, 200, 500);
//        outputArea2.setFont(new Font("Roboto", Font.ITALIC, 15));
//        outputArea2.setEditable(false);
//        outputArea2.setForeground(Color.BLACK); // Set the text color to white
//        contentPanel.add(scrollPane1);
//
//        setSize(1250, 900);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//        setVisible(true);
//
//        String concatenatedUrls = fetchDataFromDatabase();
//        outputArea2.setText(concatenatedUrls); // Display the concatenated URLs in the output area
//        
//     // Create the clear button
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
//        
//    }
//
//    private boolean checkWebsiteAvailability(String urlString) {
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
////    private void clearOutput() {
////        outputArea2.setText(""); // Clear the output area
////    }
//    private void clearHistory() {
//        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
//            String sql = "DELETE FROM JARVIS";
//            try (Statement stmt = conn.createStatement()) {
//                stmt.executeUpdate(sql);
//                outputArea2.setText("History cleared successfully."); // Update the output area
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//    /**
//     * @param input
//     */
//    private void processInput(String input) {
//        saveInputToDatabase(input);
//        displayOutput("Opening the site .. . . .");
//        boolean isAvailable = false;
//        switch (input.toLowerCase()) {
//            case "leetcode":
//                openURL("https://leetcode.com/");
//                break;
//            case "write a note":
//            case "notepad":
//                openNotepad();
//                break;
//            case "youtube":
//                openURL("https://youtube.com");
//                break;
//            case "javanotes":
//                openURL("https://java.antosh.in/"
//                		+ "");
//                break;
//            case "instagram":
//                openURL("https://instagram.com");
//                break;
//            case "github":
//                openURL("https://github.com");
//                break;
//            case "music":
//                openURL("https://youtu.be/dZqmlSGmlC4?si=5sT4DWsDhxkv8vtD");
//                break;
//            case "hi":
//            case "hello":
//                displayOutput("Hello, " + getUserInput() + "! How can I help you?");
//                break;
//            case "sveri":
//            case "college website":
//            	openURL("https://coe.sveri.ac.in/");
//            	break;
//            case "google":
//                openURL("https://google.com");
//                break;
//            case "facebook":
//                openURL("https://facebook.com");
//                break;
//            case "twitter":
//                openURL("https://twitter.com");
//                break;
//            case "wikipedia":
//                openURL("https://wikipedia.org");
//                break;
//            case "linkedin":
//                openURL("https://linkedin.com");
//                break;
//            case "reddit":
//                openURL("https://reddit.com");
//                break;
//            case "amazon":
//                openURL("https://amazon.com");
//                break;
//            case "whatsapp":
//                openURL("https://whatsapp.com");
//                break;
//            case "netflix":
//                openURL("https://netflix.com");
//                break;
//            case "pinterest":
//                openURL("https://pinterest.com");
//                break;
//            case "quora":
//                openURL("https://quora.com");
//                break;
//            case "duckduckgo":
//                openURL("https://duckduckgo.com");
//                break;
//            case "suggetion for code":
//            case "stackoverflow":
//                openURL("https://stackoverflow.com");
//                break;
//            case "medium":
//                openURL("https://medium.com");
//                break;
//            case "twitch":
//                openURL("https://twitch.tv");
//                break;
//            case "play music":
//            case "spotify":
//                openURL("https://spotify.com");
//                break;
//            case "imdb":
//                openURL("https://imdb.com");
//                break;
//            case "cnn":
//                openURL("https://cnn.com");
//                break;
//            case "bbc":
//                openURL("https://bbc.com");
//                break;
//            case "etsy":
//                openURL("https://etsy.com");
//                break;
//            case "hacker news":
//                openURL("https://news.ycombinator.com");
//                break;
//            case "ted":
//                openURL("https://ted.com");
//                break;
//            case "gmail":
//            case "write the email":
//                openURL("https://mail.google.com");
//                break;
//            case "yahoo":
//                openURL("https://yahoo.com");
//                break;
//            case "stack exchange":
//                openURL("https://stackexchange.com");
//                break;
//            case "adobe":
//                openURL("https://creativecloud.adobe.com");
//                break;
//            case "coursera":
//                openURL("https://coursera.org");
//                break;
//            case "quizlet":
//                openURL("https://quizlet.com");
//                break;
//            case "khan academy":
//                openURL("https://www.khanacademy.org");
//                break;
//            case "chegg":
//                openURL("https://www.chegg.com");
//                break;
//            case "duolingo":
//                openURL("https://www.duolingo.com");
//                break;
//            case "ted-ed":
//                openURL("https://ed.ted.com");
//                break;
//            case "mathway":
//                openURL("https://www.mathway.com");
//                break;
//            case "sparknotes":
//                openURL("https://www.sparknotes.com");
//                break;
//            case "grammarly":
//                openURL("https://www.grammarly.com");
//                break;
//            case "edx":
//                openURL("https://www.edx.org");
//                break;
//            case "memrise":
//                openURL("https://www.memrise.com");
//                break;
//            case "codecademy":
//                openURL("https://www.codecademy.com");
//                break;
//            case "wolfram alpha":
//                openURL("https://www.wolframalpha.com");
//                break;
//            case "quizizz":
//                openURL("https://www.quizizz.com");
//                break;
//            case "scratch":
//                openURL("https://scratch.mit.edu");
//                break;
//            case "notion":
//                openURL("https://www.notion.so");
//                break;
//            case "draw.io":
//                openURL("https://www.draw.io");
//                break;
//            case "researchgate":
//                openURL("https://www.researchgate.net");
//                break;
//            case "onenote":
//                openURL("https://www.onenote.com");
//                break;
//            case "prezi":
//                openURL("https://www.prezi.com");
//                break;
//
//            // Add your other cases here
//            default:
//                if (checkWebsiteAvailability(input)) {
//                    openURL(input);
//                    isAvailable = true;
//                } else {
//                    displayOutput("Site Opening Failed * * * * * *");
//                    displayOutput("The website is not available. Trying to search in the default browser...");
//
//                    // Attempt to open the default browser with the input as a search query
//                    try {
//                        Desktop.getDesktop().browse(new URI("https://www.google.com/search?q=" + input.replace(" ", "+")));
//                    } catch (IOException | URISyntaxException e) {
//                        e.printStackTrace();
//                        displayOutput("Failed to open the default browser.");
//                    }
//                }
//        }
//
//        // If the input was available in the predefined cases, don't display the default browser message
////        if (!isAvailable) {
////            displayOutput("You can enter the full address of a website to open it.");
////            displayOutput("E.g., https://www." + input + ".com");
////        }
//    }
//
//    private void saveInputToDatabase(String input) {//save the data to the database
//        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
//            String query = "INSERT INTO JARVIS (urL) VALUES (?)";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//                preparedStatement.setString(1, input);
//                int rowsAffected = preparedStatement.executeUpdate();
//                if (rowsAffected > 0) {
//                    displayOutput("");
//                } else {
//                    displayOutput("");
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            displayOutput("");
//        }
//    }
//
//    private String fetchDataFromDatabase() {//fetch the data from the database
//        StringBuilder urlsBuilder = new StringBuilder();
//        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
//            String query = "SELECT url FROM JARVIS";
//            try (Statement statement = connection.createStatement();
//                 ResultSet resultSet = statement.executeQuery(query)) {
//                while (resultSet.next()) {
//                    String url = resultSet.getString("url");
//                    urlsBuilder.append(url).append("\n"); // Append each URL to the StringBuilder
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return urlsBuilder.toString(); // Return the concatenated URLs as a single String
//    }
//
//    private static void openURL(String url) {
//        try {
//            Desktop.getDesktop().browse(new URI(url));
//        } catch (IOException | URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void openNotepad() {
//        try {
//            Runtime.getRuntime().exec("notepad.exe");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void displayOutput(String message) {
//        outputArea1.append(message + "\n");
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(ok::new);
//    }
//}
//
