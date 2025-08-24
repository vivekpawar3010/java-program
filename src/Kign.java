import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Kign extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    JLabel l1, l2, l3, l4, l8;
    JTextField tf1, tf2, tf3, tf8;
    JButton btn1, btn2;

    // Database connection properties
    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String DB_URL = "jdbc:sqlserver://VIVEK\\SQLEXPRESS:1433;databaseName=Studentinfo;encrypt=true;trustServerCertificate=true";
    static final String USER = "sa";
    static final String PASS = "King@3010";

    Connection conn = null;
    Statement stmt = null;

    public Kign() {
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setLayout(null);

        setVisible(true);
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Student marks");

        l1 = new JLabel("Student marks:");
        l1.setForeground(Color.blue);
        l1.setFont(new Font("Serif", Font.BOLD, 20));
        l2 = new JLabel("Name:");
        l3 = new JLabel("Branch:");
        l4 = new JLabel("Class and Div:"); // Changed from "class and div" to "Class and Div"

        l8 = new JLabel("Marks");
        tf1 = new JTextField();
        tf2 = new JTextField();
        tf3 = new JTextField();

        tf8 = new JTextField();
        btn1 = new JButton("Submit");
        btn2 = new JButton("Clear");
        btn1.addActionListener(this);
        btn2.addActionListener(this);

        l1.setBounds(100, 250, 400, 30);
        l2.setBounds(80, 290, 200, 30);
        l3.setBounds(80, 330, 200, 30);
        l4.setBounds(80, 370, 200, 30);

        l8.setBounds(80, 450, 200, 30);
        tf1.setBounds(300, 290, 200, 30);
        tf2.setBounds(300, 330, 200, 30);
        tf3.setBounds(300, 370, 200, 30);

        tf8.setBounds(300, 450, 200, 30);
        btn1.setBounds(100, 490, 100, 30);
        btn2.setBounds(250, 490, 100, 30);

        contentPane.add(l1);
        contentPane.add(l2);
        contentPane.add(tf1);
        contentPane.add(l3);
        contentPane.add(tf2);
        contentPane.add(l4); // Added l4 to contentPane
        contentPane.add(tf3);
        contentPane.add(l8);
        contentPane.add(tf8);
        contentPane.add(btn1);
        contentPane.add(btn2);

        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn1) {
            String s1 = tf1.getText();
            String s2 = tf2.getText();
            String s3 = tf3.getText();
            String s8 = tf8.getText();

            if (!s1.isEmpty() && !s2.isEmpty() && !s3.isEmpty() && !s8.isEmpty()) { // Added condition for s8
                try {
                    // Execute a query
                    stmt = conn.createStatement();
                    String sql = "INSERT INTO Studentinfo (name, branch, class_div, marks) VALUES ('" + s1 + "', '" + s2 + "', '" + s3 + "', '" + s8 + "')"; // Corrected SQL query
                    stmt.executeUpdate(sql);
                    JOptionPane.showMessageDialog(btn1, "Data Saved Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(btn1, "Error saving data", "Error", JOptionPane.ERROR_MESSAGE);
                }

                // Open certificate frame
                JFrame certificateFrame = new JFrame("Certificate");
                certificateFrame.setSize(900, 700);
                certificateFrame.setLayout(null);
                certificateFrame.setLocationRelativeTo(null);

                
                dispose();
            }
        } else {
            tf1.setText("");
            tf2.setText("");
            tf3.setText("");
            tf8.setText("");
        }
    }

    public static void main(String[] args) {
        new Kign();
    }
}
