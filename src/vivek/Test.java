package vivek;
//package vivek;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Test extends JFrame implements ActionListener {
    // GUI Components
    JLabel questionLabel;
    JRadioButton[] options;
    JButton nextButton, bookmarkButton, backButton, loginButton; // Added loginButton
    ButtonGroup optionGroup;

    // Variables
    int currentQuestionIndex = 0;
    int score = 0;
    int[] bookmarkedQuestions = new int[10]; // Array to store bookmarked question indices

    // Database Connection Details
    String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    String url = "jdbc:sqlserver://VIVEK\\SQLEXPRESS:1433;databaseName=test;encrypt=true;trustServerCertificate=true";
    String user = "sa";
    String password = "King@3010";
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;

    // Constructor
    Test(String title) {
        super(title);

        // Initialize GUI components
        questionLabel = new JLabel();
        options = new JRadioButton[4];
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
        }
        nextButton = new JButton("Next");
        bookmarkButton = new JButton("Bookmark");
        backButton = new JButton("Back");
        loginButton = new JButton("Login"); // Added loginButton
        optionGroup = new ButtonGroup();

        // Add components to the frame
        add(questionLabel);
        for (int i = 0; i < 4; i++) {
            add(options[i]);
            optionGroup.add(options[i]);
        }
        add(nextButton);
        add(bookmarkButton);
        add(backButton);
        add(loginButton); // Added loginButton

        // Set bounds for components
        questionLabel.setBounds(30, 40, 1000, 25);
        options[0].setBounds(50, 130, 200, 20);
        options[1].setBounds(50, 192, 200, 20);
        options[2].setBounds(50, 250, 200, 20);
        options[3].setBounds(50, 300, 200, 20);
        nextButton.setBounds(100, 400, 100, 50);
        bookmarkButton.setBounds(500, 400, 200, 50);
        backButton.setBounds(300, 400, 100, 50);
        loginButton.setBounds(700, 400, 100, 50); // Added loginButton

        // Register action listeners
        nextButton.addActionListener(this);
        bookmarkButton.addActionListener(this);
        backButton.addActionListener(this);
        loginButton.addActionListener(this); // Added loginButton action listener

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocation(150, 50);
        setVisible(true);
        setSize(1200, 700);

        // Load the first question
        loadQuestion(currentQuestionIndex);
    }

    // Load question and options
    void loadQuestion(int index) {
        // Sample questions and options
        String[] questions = {
            "Which one among these is not a primitive datatype?",
            "Which class is available to all classes automatically?",
            // Add more questions as needed
        };
        String[][] options = {
            {"int", "Float", "boolean", "char"},
            {"Swing", "Applet", "Object", "ActionEvent"},
            // Add corresponding options for each question
        };

        // Display question and options
        questionLabel.setText("Question " + (index + 1) + ": " + questions[index]);
        for (int i = 0; i < 4; i++) {
            this.options[i].setText(options[index][i]);
        }
    }

    // ActionListener implementation
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton) {
            // Check the selected option and update score
            if (checkAnswer()) {
                score++;
            }
            currentQuestionIndex++;
            loadQuestion(currentQuestionIndex);
            backButton.setEnabled(true); // Enable back button after moving to next question
        } else if (e.getSource() == bookmarkButton) {
            // Bookmark the current question
            bookmarkedQuestions[currentQuestionIndex] = 1;
            currentQuestionIndex++;
            loadQuestion(currentQuestionIndex);
            backButton.setEnabled(true);
        } else if (e.getSource() == backButton) {
            currentQuestionIndex--;
            loadQuestion(currentQuestionIndex);
            if (currentQuestionIndex == 0) {
                backButton.setEnabled(false); // Disable back button on the first question
            }
        } else if (e.getSource() == loginButton) { // Added loginButton action
            // Start the test
            showTest();
        }
        if (currentQuestionIndex == 9) {
            nextButton.setEnabled(false);
            bookmarkButton.setText("Result");
        }
        if (e.getSource() == bookmarkButton && bookmarkButton.getText().equals("Result")) {
            // Display final score
            JOptionPane.showMessageDialog(this, "Your score: " + score);
            // Insert user data into the database (name, division, roll number)
            insertUserData("John Doe", "A", 12345);
            System.exit(0);
        }
    }

    // Check if the selected option is correct
    boolean checkAnswer() {
        // Sample answers (modify as per your questions)
        boolean[] answers = {false, true, false, false};
        int selectedOption = -1;
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) {
                selectedOption = i;
                break;
            }
        }
        return selectedOption != -1 && answers[currentQuestionIndex] == options[selectedOption].isSelected();
    }

    // Insert user data into the database
    private void insertUserData(String name, String division, int rollno) {
        try {
            String insertQuery = "INSERT INTO students (name, division, rollno) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, name);
            pstmt.setString(2, division);
            pstmt.setInt(3, rollno);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Display login dialog and process user input
    private void showLoginDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
            "Username:", usernameField,
            "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Validate login credentials (you can add your own validation logic here)
            if (isValidLogin(username, password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");

                // Proceed with the test
                showTest();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
                showLoginDialog(); // Show the login dialog again if credentials are invalid
            }
        } else {
            System.exit(0); // Close the application if login is cancelled
        }
    }

    // Sample login validation method (replace with your own logic)
    private boolean isValidLogin(String username, String password) {
        return username.equals("admin") && password.equals("admin123");
    }

    // Show the test
    private void showTest() {
        JOptionPane.showMessageDialog(this, "Starting the test...");
        // Proceed with your test logic here
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Test("Online Test Of Java by SK"));
    }
}

//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.sql.*;
//
//public class Test extends JFrame implements ActionListener {
//    // GUI Components
//    JLabel questionLabel;
//    JRadioButton[] options;
//    JButton nextButton, bookmarkButton, backButton;
//    ButtonGroup optionGroup;
//
//    // Variables
//    int currentQuestionIndex = 0;
//    int score = 0;
//    int[] bookmarkedQuestions = new int[10]; // Array to store bookmarked question indices
//
//    // Database Connection Details
//    String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//    String url = "jdbc:sqlserver://VIVEK\\SQLEXPRESS:1433;databaseName=test;encrypt=true;trustServerCertificate=true";
//    String user = "sa";
//    String password = "King@3010";
//    Connection conn = null;
//    Statement stmt = null;
//    PreparedStatement pstmt = null;
//
//    // Constructor
//    Test(String title) {
//        super(title);
//
//        // Initialize GUI components
//        questionLabel = new JLabel();
//        options = new JRadioButton[4];
//        for (int i = 0; i < 4; i++) {
//            options[i] = new JRadioButton();
//        }
//        nextButton = new JButton("Next");
//        bookmarkButton = new JButton("Bookmark");
//        backButton = new JButton("Back");
//        optionGroup = new ButtonGroup();
//
//        // Add components to the frame
//        add(questionLabel);
//        for (int i = 0; i < 4; i++) {
//            add(options[i]);
//            optionGroup.add(options[i]);
//        }
//        add(nextButton);
//        add(bookmarkButton);
//        add(backButton);
//
//        // Set bounds for components
//        questionLabel.setBounds(30, 40, 1000, 25);
//        options[0].setBounds(50, 130, 200, 20);
//        options[1].setBounds(50, 192, 200, 20);
//        options[2].setBounds(50, 250, 200, 20);
//        options[3].setBounds(50, 300, 200, 20);
//        nextButton.setBounds(100, 400, 100, 50);
//        bookmarkButton.setBounds(500, 400, 200, 50);
//        backButton.setBounds(300, 400, 100, 50);
//
//        // Register action listeners
//        nextButton.addActionListener(this);
//        bookmarkButton.addActionListener(this);
//        backButton.addActionListener(this);
//
//        // Set frame properties
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(null);
//        setLocation(150, 50);
//        setVisible(true);
//        setSize(1200, 700);
//
//        // Load the first question
//        loadQuestion(currentQuestionIndex);
//
//        // Display login dialog
//        showLoginDialog();
//    }
//
//    // Load question and options
//    void loadQuestion(int index) {
//        // Sample questions and options
//        String[] questions = {
//            "Which one among these is not a primitive datatype?",
//            "Which class is available to all classes automatically?",
//            // Add more questions as needed
//        };
//        String[][] options = {
//            {"int", "Float", "boolean", "char"},
//            {"Swing", "Applet", "Object", "ActionEvent"},
//            // Add corresponding options for each question
//        };
//
//        // Display question and options
//        questionLabel.setText("Question " + (index + 1) + ": " + questions[index]);
//        for (int i = 0; i < 4; i++) {
//            this.options[i].setText(options[index][i]);
//        }
//    }
//
//    // ActionListener implementation
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == nextButton) {
//            // Check the selected option and update score
//            if (checkAnswer()) {
//                score++;
//            }
//            currentQuestionIndex++;
//            loadQuestion(currentQuestionIndex);
//            backButton.setEnabled(true); // Enable back button after moving to next question
//        } else if (e.getSource() == bookmarkButton) {
//            // Bookmark the current question
//            bookmarkedQuestions[currentQuestionIndex] = 1;
//            currentQuestionIndex++;
//            loadQuestion(currentQuestionIndex);
//            backButton.setEnabled(true);
//        } else if (e.getSource() == backButton) {
//            currentQuestionIndex--;
//            loadQuestion(currentQuestionIndex);
//            if (currentQuestionIndex == 0) {
//                backButton.setEnabled(false); // Disable back button on the first question
//            }
//        }
//        if (currentQuestionIndex == 9) {
//            nextButton.setEnabled(false);
//            bookmarkButton.setText("Result");
//        }
//        if (e.getSource() == bookmarkButton && bookmarkButton.getText().equals("Result")) {
//            // Display final score
//            JOptionPane.showMessageDialog(this, "Your score: " + score);
//            // Insert user data into the database (name, division, roll number)
//            insertUserData("John Doe", "A", 12345);
//            System.exit(0);
//        }
//    }
//
//    // Check if the selected option is correct
//    boolean checkAnswer() {
//        // Sample answers (modify as per your questions)
//        boolean[] answers = {false, true, false, false};
//        int selectedOption = -1;
//        for (int i = 0; i < 4; i++) {
//            if (options[i].isSelected()) {
//                selectedOption = i;
//                break;
//            }
//        }
//        return selectedOption != -1 && answers[currentQuestionIndex] == options[selectedOption].isSelected();
//    }
//
//    // Insert user data into the database
//    private void insertUserData(String name, String division, int rollno) {
//        try {
//            String insertQuery = "INSERT INTO students (name, division, rollno) VALUES (?, ?, ?)";
//            pstmt = conn.prepareStatement(insertQuery);
//            pstmt.setString(1, name);
//            pstmt.setString(2, division);
//            pstmt.setInt(3, rollno);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (pstmt != null) pstmt.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    // Display login dialog and process user input
//    private void showLoginDialog() {
//        JTextField usernameField = new JTextField();
//        JPasswordField passwordField = new JPasswordField();
//        Object[] message = {
//            "Username:", usernameField,
//            "Password:", passwordField
//        };
//
//        int option = JOptionPane.showConfirmDialog(this, message, "Login", JOptionPane.OK_CANCEL_OPTION);
//        if (option == JOptionPane.OK_OPTION) {
//            String username = usernameField.getText();
//            String password = new String(passwordField.getPassword());
//
//            // Validate login credentials (you can add your own validation logic here)
//            if (isValidLogin(username, password)) {
//                JOptionPane.showMessageDialog(this, "Login successful!");
//
//                // Proceed with the test
//            } else {
//                JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
//                showLoginDialog(); // Show the login dialog again if credentials are invalid
//            }
//        } else {
//            System.exit(0); // Close the application if login is cancelled
//        }
//    }
//
//    // Sample login validation method (replace with your own logic)
//    private boolean isValidLogin(String username, String password) {
//        return username.equals("admin") && password.equals("admin123");
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new Test("Online Test Of Java by SK"));
//    }
//}
//

//-------------------------------------------------------------
//import java.awt.*;
//import java.awt.event.*;
//import javax.swing.*;
//import java.sql.*;
//
//public class Test extends JFrame implements ActionListener {
//    /**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	// GUI Components
//    JLabel questionLabel;
//    JRadioButton[] options;
//    JButton nextButton, bookmarkButton, backButton;
//    ButtonGroup optionGroup;
//
//    // Variables
//    int currentQuestionIndex = 0;
//    int score = 0;
//    int[] bookmarkedQuestions = new int[10]; // Array to store bookmarked question indices
//
//    // Database Connection Details
//    String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//    String url = "jdbc:sqlserver://VIVEK\\SQLEXPRESS:1433;databaseName=test;encrypt=true;trustServerCertificate=true";
//    String user = "sa";
//    String password = "King@3010";
//    Connection conn = null;
//    Statement stmt = null;
//    PreparedStatement pstmt = null;
//
//    // Constructor
//    Test(String title) {
//        super(title);
//
//        // Initialize GUI components
//        questionLabel = new JLabel();
//        options = new JRadioButton[4];
//        for (int i = 0; i < 4; i++) {
//            options[i] = new JRadioButton();
//        }
//        nextButton = new JButton("Next");
//        bookmarkButton = new JButton("Bookmark");
//        backButton = new JButton("Back");
//        optionGroup = new ButtonGroup();
//
//        // Add components to the frame
//        add(questionLabel);
//        for (int i = 0; i < 4; i++) {
//            add(options[i]);
//            optionGroup.add(options[i]);
//        }
//        add(nextButton);
//        add(bookmarkButton);
//        add(backButton);
//
//        // Set bounds for components
//        questionLabel.setBounds(30, 40, 1000, 25);
//        options[0].setBounds(50, 130, 200, 20);
//        options[1].setBounds(50, 192, 200, 20);
//        options[2].setBounds(50, 250, 200, 20);
//        options[3].setBounds(50, 300, 200, 20);
//        nextButton.setBounds(100, 400, 100, 50);
//        bookmarkButton.setBounds(500, 400, 200, 50);
//        backButton.setBounds(300, 400, 100, 50);
//
//        // Register action listeners
//        nextButton.addActionListener(this);
//        bookmarkButton.addActionListener(this);
//        backButton.addActionListener(this);
//
//        // JDBC connection setup
//        try {
//            Class.forName(JDBC_DRIVER);
//            conn = DriverManager.getConnection(url, user, password);
//            stmt = conn.createStatement();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Set frame properties
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(null);
//        setLocation(150, 50);
//        setVisible(true);
//        setSize(1200, 700);
//
//        // Load the first question
//        loadQuestion(currentQuestionIndex);
//    }
//
//    // Load question and options
//    void loadQuestion(int index) {
//        // Sample questions and options
//        String[] questions = {
//            "Which one among these is not a primitive datatype?",
//            "Which class is available to all classes automatically?",
//            // Add more questions as needed
//        };
//        String[][] options = {
//            {"int", "Float", "boolean", "char"},
//            {"Swing", "Applet", "Object", "ActionEvent"},
//            // Add corresponding options for each question
//        };
//
//        // Display question and options
//        questionLabel.setText("Question " + (index + 1) + ": " + questions[index]);
//        for (int i = 0; i < 4; i++) {
//            this.options[i].setText(options[index][i]);
//        }
//    }
//
//    // ActionListener implementation
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == nextButton) {
//            // Check the selected option and update score
//            if (checkAnswer()) {
//                score++;
//            }
//            currentQuestionIndex++;
//            loadQuestion(currentQuestionIndex);
//            backButton.setEnabled(true); // Enable back button after moving to next question
//        } else if (e.getSource() == bookmarkButton) {
//            // Bookmark the current question
//            bookmarkedQuestions[currentQuestionIndex] = 1;
//            currentQuestionIndex++;
//            loadQuestion(currentQuestionIndex);
//            backButton.setEnabled(true);
//        } else if (e.getSource() == backButton) {
//            currentQuestionIndex--;
//            loadQuestion(currentQuestionIndex);
//            if (currentQuestionIndex == 0) {
//                backButton.setEnabled(false); // Disable back button on the first question
//            }
//        }
//        if (currentQuestionIndex == 9) {
//            nextButton.setEnabled(false);
//            bookmarkButton.setText("Result");
//        }
//        if (e.getSource() == bookmarkButton && bookmarkButton.getText().equals("Result")) {
//            // Display final score
//            JOptionPane.showMessageDialog(this, "Your score: " + score);
//            // Insert user data into the database (name, division, roll number)
//            insertUserData("John Doe", "A", 12345);
//            System.exit(0);
//        }
//    }
//
//    // Check if the selected option is correct
//    boolean checkAnswer() {
//        // Sample answers (modify as per your questions)
//        boolean[] answers = {false, true, false, false};
//        int selectedOption = -1;
//        for (int i = 0; i < 4; i++) {
//            if (options[i].isSelected()) {
//                selectedOption = i;
//                break;
//            }
//        }
//        return selectedOption != -1 && answers[currentQuestionIndex] == options[selectedOption].isSelected();
//    }
//
//    // Insert user data into the database
//    private void insertUserData(String name, String division, int rollno) {
//        try {
//            String insertQuery = "INSERT INTO students (name, division, rollno) VALUES (?, ?, ?)";
//            pstmt = conn.prepareStatement(insertQuery);
//            pstmt.setString(1, name);
//            pstmt.setString(2, division);
//            pstmt.setInt(3, rollno);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (pstmt != null) pstmt.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new Test("Online Test Of Java by SK"));
//    }
//}

//----------------------------------------------------------------------------------------------------------------------------
//	import java.awt.*;
//	import java.awt.event.*;
//	import javax.swing.*;
//	import java.sql.*;
//
//	public class Test extends JFrame implements ActionListener {
//	    JLabel l, l1;
//	    Font fo;
//	    JRadioButton jb[] = new JRadioButton[5];
//	    JButton b1, b2, b3;
//
//	    ButtonGroup bg;
//	    int count = 0, current = 0, x = 1, y = 1, now = 0;
//	    int m[] = new int[10];
//	    String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//        String url = "jdbc:sqlserver://VIVEK\\SQLEXPRESS:1433;databaseName=test;encrypt=true;trustServerCertificate=true";
//        String user = "sa";
//        String password = "King@3010";
//	    Connection conn = null;
//	    Statement stmt = null;
//	    PreparedStatement pstmt = null;
//	    
//	    
//	    
//	    Test(String s) {
//	        super(s);
//	        l = new JLabel();
//	        l1 = new JLabel();
//	        add(l);
//	        add(l1);
//	        bg = new ButtonGroup();
//	        for (int i = 0; i < 5; i++) {
//	            jb[i] = new JRadioButton();
//	            add(jb[i]);
//	            bg.add(jb[i]);
//	        }
//	        b1 = new JButton("Next");
//	        b2 = new JButton("Bookmark");
//	        b3 = new JButton("Back");
//
//	        b1.addActionListener(this);
//	        b2.addActionListener(this);
//	        b3.addActionListener(this);
//
//	        add(b1);
//	        add(b2);
//	        add(b3);
//
//	        set();
//	        l.setBounds(30, 40, 1000, 25);
//	        jb[0].setBounds(50, 130, 200, 20);
//	        jb[1].setBounds(50, 192, 200, 20);
//	        jb[2].setBounds(50, 250, 200, 20);
//	        jb[3].setBounds(50, 300, 200, 20);
//	        b1.setBounds(100, 400, 100, 50);
//	        b2.setBounds(500, 400, 200, 50);
//	        b3.setBounds(300, 400, 100, 50);
//
//	        // JDBC connection setup
//	        try {
//	            Class.forName(JDBC_DRIVER);
//	            conn = DriverManager.getConnection(url, user, password);
//	            stmt = conn.createStatement();
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//
//	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	        setLayout(null);
//	        setLocation(150, 50);
//	        setVisible(true);
//	        setSize(1200, 700);
//	    }
//
//	    class LoginPage extends JFrame {
//	        protected final String JDBC_DRIVER = null;
//			private final JTextField nameField;
//	        private final JTextField divrollnoField;
//	        JTextField usernameField;
//	        JPasswordField passwordField;
//
//	        LoginPage() {
//	            super();
//
//	            JLabel usernameLabel = new JLabel("Username:");
//	            JLabel passwordLabel = new JLabel("Password:");
//	            JLabel nameLabel = new JLabel("Name:");
//	            JLabel DivRollNoLabel = new JLabel("Div & Roll No:");
//	            usernameField = new JTextField(40);
//	            passwordField = new JPasswordField(40);
//	            nameField = new JTextField(40);
//	            divrollnoField = new JTextField(40);
//	            JButton loginButton = new JButton("Login");
//	            JButton bs = new JButton("Start Test");
//
//	            nameLabel.setBounds(300, 60, 180, 100);
//	            nameField.setBounds(600,65,500,50);
//	            DivRollNoLabel.setBounds(300, 105, 300, 100);
//	            divrollnoField.setBounds(600, 135, 500, 50);
//	            usernameLabel.setBounds(300, 180, 500, 50);
//	            usernameField.setBounds(600, 195, 500, 50);
//	            passwordLabel.setBounds(300, 240, 180, 100);
//	            passwordField.setBounds(600, 265, 500, 50);
//
//	            loginButton.setBounds(500, 400, 180, 55);
//	            bs.setBounds(900, 400, 180, 55);
//
//	            fo = new Font("Times new Roman", Font.BOLD, 30);
//	            nameLabel.setFont(fo);
//	            nameField.setFont(fo);
//	            DivRollNoLabel.setFont(fo);
//	            divrollnoField.setFont(fo);
//	            usernameLabel.setFont(fo);
//	            usernameField.setFont(fo);
//	            passwordLabel.setFont(fo);
//	            passwordField.setFont(fo);
//	            loginButton.setFont(fo);
//	            bs.setFont(fo);
//
//	            add(nameLabel);
//	            add(nameField);
//	            add(DivRollNoLabel);
//	            add(divrollnoField);
//	            add(usernameLabel);
//	            add(usernameField);
//	            add(passwordLabel);
//	            add(passwordField);
//	            add(new JLabel());
//	            add(loginButton);
//	            add(bs);
//
//	            loginButton.addActionListener(new ActionListener() {
//	                public void actionPerformed(ActionEvent e) {
//	                    String username = usernameField.getText();
//	                    String password = new String(passwordField.getPassword());
//
//	                    if (username.equals("admin") && password.equals("password")) {
//	                        JOptionPane.showMessageDialog(LoginPage.this, "Login successful!");
//	                    } else {
//	                        JOptionPane.showMessageDialog(LoginPage.this, "Invalid username or password. Try again.");
//	                    }
//	                }
//	            });
//
//	            bs.addActionListener(new ActionListener() {
//	                public void actionPerformed(ActionEvent e) {
//	                    // Code to initiate the test when the Start Test button is clicked
//	                    dispose(); // Close the login window
//	                    String username = usernameField.getText();
//	                    String password = new String(passwordField.getPassword());
//
//	                    // Check if login credentials are correct
//	                    if (username.equals("admin") && password.equals("password")) {
//	                        // Login successful
//	                        JOptionPane.showMessageDialog(LoginPage.this, "Login successful!");
//
//	                        // JDBC connection setup
//	                        try {
//	                            Class.forName(JDBC_DRIVER);
//	                            conn = DriverManager.getConnection(url, user, password);
//	                            stmt = conn.createStatement();
//	                        } catch (Exception a) {
//	                            a.printStackTrace();
//	                        }
//
//	                        // Start the test
//	                        new Test("Online Test Of Java by SK");
//	                    } else {
//	                        // Invalid username or password
//	                        JOptionPane.showMessageDialog(LoginPage.this, "Invalid username or password. Try again.");
//	                        System.exit(0);
//	                    }
//	                }
//	            });
//
//
//	            setSize(1300, 800);
//	            setLocation(400, 400);
//	            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	            setLocationRelativeTo(null);
//	            setLayout(null);
//	            setVisible(true);
//	        }
//	    }
//
//	    public void actionPerformed(ActionEvent e) {
//	        if (e.getSource() == b1) {
//	            if (check())
//	                count = count + 1;
//	            current++;
//	            set();
//	            if (current == 9) {
//	                b1.setEnabled(false);
//	                b2.setText("Result");
//	            }
//	            b3.setEnabled(true); // Enable back button after moving to next question
//	        } else if (e.getActionCommand().equals("Bookmark")) {
//	            JButton bk = new JButton("Bookmark" + x);
//	            bk.setBounds(850, 40 + 50 * x, 200, 50);
//	            add(bk);
//	            bk.setFont(fo);
//	            bk.addActionListener(this);
//	            m[x] = current;
//	            x++;
//	            current++;
//	            set();
//	            if (current == 9)
//	                b2.setText("Result");
//	            setVisible(false);
//	            setVisible(true);
//	        } else if (e.getActionCommand().equals("Back")) {
//	            if (check())
//	                count = count - 1;
//	            current--;
//	            set();
//	            if (current == 0)
//	                b3.setEnabled(false); // Disable back button when on the first question
//	        } else if (e.getActionCommand().equals("Result")) {
//	            if (check())
//	                count = count + 1;
//	            current++;
//	            JOptionPane.showMessageDialog(this, "correct ans=" + count);
//	            System.exit(0);
//	        } else {
//	            for (int i = 0, y = 1; i < x; i++, y++) {
//	                if (e.getActionCommand().equals("Bookmark" + y)) {
//	                    if (check())
//	                        count = count + 1;
//	                    now = current;
//	                    current = m[y];
//	                    set();
//	                    ((JButton) e.getSource()).setEnabled(false);
//	                    current = now;
//	                }
//	            }
//	        }
//	    }
//
//	    void set() {
//	        jb[4].setSelected(true);
//	        String[] questions = {
//	                "Which one among these is not a primitive datatype?",
//	                "Which class is available to all the class automatically?",
//	                "Which package is directly available to our class without importing it?",
//	                "String class is defined in which package?",
//	                "Which institute is best for java coaching?",
//	                "Which one among these is not a keyword?",
//	                "Which one among these is not a class?",
//	                "which one among these is not a function of Object class?",
//	                "which function is not present in Applet class?",
//	                "Which one among these is not a valid component?"
//	        };
//	        String[][] options = {
//	                {"int", "Float", "boolean", "char"},
//	                {"Swing", "Applet", "Object", "ActionEvent"},
//	                {"swing", "applet", "net", "lang"},
//	                {"lang", "Swing", "Applet", "awt"},
//	                {"Utek", "Aptech", "SSS IT", "jtek"},
//	                {"class", "int", "get", "if"},
//	                {"Swing", "Actionperformed", "ActionEvent", "Button"},
//	                {"toString", "finalize", "equals", "getDocumentBase"},
//	                {"init", "main", "start", "destroy"},
//	                {"JButton", "JList", "JButtonGroup", "JTextArea"}
//	        };
//	        if (current < questions.length) {
//	            l.setText("Que" + (current + 1) + ": " + questions[current]);
//	            for (int i = 0; i < 4; i++) {
//	                jb[i].setText(options[current][i]);
//	            }
//	            l.setBounds(50, 40, 800, 50);
//	            for (int i = 0, j = 0; i <= 50; i += 50, j++) {
//	                jb[j].setBounds(50, 130 + i, 800, 50);
//	            }
//	            fo = new Font("Times new Roman", Font.BOLD, 20);
//	            l.setFont(fo);
//	            for (int i = 0; i < 4; i++) {
//	                jb[i].setFont(fo);
//	            }
//	        }
//	    }
//
//	    boolean check() {
//	        boolean result = false;
//	        if (current == 0)
//	            result = jb[1].isSelected();
//	        else if (current == 1)
//	            result = jb[2].isSelected();
//	        else if (current == 2)
//	            result = jb[3].isSelected();
//	        else if (current == 3)
//	            result = jb[0].isSelected();
//	        else if (current == 4)
//	            result = jb[2].isSelected();
//	        else if (current == 5)
//	            result = jb[2].isSelected();
//	        else if (current == 6)
//	            result = jb[1].isSelected();
//	        else if (current == 7)
//	            result = jb[3].isSelected();
//	        else if (current == 8)
//	            result = jb[1].isSelected();
//	        else if (current == 9)
//	            result = jb[2].isSelected();
//	        return result;
//	    }
//
//	    private void insertUserData(String name, String division, int rollno) {
//	        try {
//	            String insertQuery = "INSERT INTO students (name, division, rollno) VALUES (?, ?, ?)";
//	            pstmt = conn.prepareStatement(insertQuery);
//	            pstmt.setString(1, name);
//	            pstmt.setString(2, division);
//	            pstmt.setInt(3, rollno);
//	            pstmt.executeUpdate();
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	        } finally {
//	            try {
//	                if (pstmt != null) pstmt.close();
//	            } catch (SQLException e) {
//	                e.printStackTrace();
//	            }
//	        }
//	    }
//
//
//	    public static void main(String args[]) {
//	        SwingUtilities.invokeLater(new Runnable() {
//	            public void run() {
//	                Test test = new Test("Online Test Of Java by SK");
//	                LoginPage loginPage = test.new LoginPage();
//	                test.add(loginPage);
//	            }
//	        });
//	    }
//	}
