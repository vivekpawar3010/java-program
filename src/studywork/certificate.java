package studywork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class certificate extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel l1, l2, l3, l4, l5, l8, imageLabel, certificateImageLabel, nameLabel;
    JTextField tf1, tf2, tf3, tf8;
    JButton btn1, btn2, downloadBtn;
    JComboBox<String> cb;

    // Database connection properties
    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String DB_URL = "jdbc:sqlserver://VIVEK\\\\SQLEXPRESS:1433;databaseName=MYCERTIFC;encrypt=true;trustServerCertificate=true";
    static final String USER = "sa";
    static final String PASS = "King@3010";

    Connection conn = null;
    Statement stmt = null;

    public certificate() {
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\college.jpg");
        imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(20, 50, 500, 230);

        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setLayout(null);
        contentPane.add(imageLabel);

        setVisible(true);
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Registration For Certificate in Java");

        l1 = new JLabel("Registration For Certificate Generation:");
        l1.setForeground(Color.blue);
        l1.setFont(new Font("Serif", Font.BOLD, 20));
        l2 = new JLabel("Name:");
        l3 = new JLabel("Email-ID:");
        l4 = new JLabel("College Name:");
        l5 = new JLabel("Event Name:");
        l8 = new JLabel("Phone No:");
        tf1 = new JTextField();
        tf2 = new JTextField();
        tf3 = new JTextField();
        cb = new JComboBox<String>();
        cb.addItem("Mirror Code ");
        cb.addItem("BGMI ");
        cb.addItem("Poster presentation");
        cb.addItem("Tambola");
        cb.addItem("Logo Design");
        cb.addItem("NFS");
        cb.addItem("Hack Spirit");
        tf8 = new JTextField();
        btn1 = new JButton("Submit");
        btn2 = new JButton("Clear");
        btn1.addActionListener(this);
        btn2.addActionListener(this);

        l1.setBounds(100, 250, 400, 30);
        l2.setBounds(80, 290, 200, 30);
        l3.setBounds(80, 330, 200, 30);
        l4.setBounds(80, 370, 200, 30);
        l5.setBounds(80, 410, 200, 30);
        l8.setBounds(80, 450, 200, 30);
        tf1.setBounds(300, 290, 200, 30);
        tf2.setBounds(300, 330, 200, 30);
        tf3.setBounds(300, 370, 200, 30);
        cb.setBounds(300,410,200,30);
        tf8.setBounds(300, 450, 200, 30);
        btn1.setBounds(100, 490, 100, 30);
        btn2.setBounds(250, 490, 100, 30);

        contentPane.add(l1);
        contentPane.add(l2);
        contentPane.add(tf1);
        contentPane.add(l3);
        contentPane.add(tf2);
        contentPane.add(l4);
        contentPane.add(tf3);
        contentPane.add(l5);
        contentPane.add(cb);
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
            String s4 = (String) cb.getSelectedItem();
            String s8 = tf8.getText();

            if (!s1.isEmpty() && !s2.isEmpty() && !s3.isEmpty() && !s4.isEmpty()) {
                try {
                    // Execute a query
                    stmt = conn.createStatement();
                    String sql = "INSERT INTO MYCERTIFC (name, email, college, event, phone) VALUES ('" + s1 + "', '" + s2 + "', '" + s3 + "', '" + s4 + "', '" + s8 + "')";
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

                nameLabel = new JLabel(s1);
                nameLabel.setFont(new Font("Serif", Font.BOLD, 16));
                nameLabel.setBounds(360, 331, 200, 30); // Adjusted position to be above the certificate image
                certificateFrame.add(nameLabel);

                nameLabel = new JLabel(s3);
                nameLabel.setFont(new Font("Serif", Font.BOLD, 16));
                nameLabel.setBounds(220, 366, 200, 30); // Adjusted position to be above the certificate image
                certificateFrame.add(nameLabel);

                nameLabel = new JLabel(s4);
                nameLabel.setFont(new Font("Serif", Font.BOLD, 16));
                nameLabel.setBounds(620, 366, 200, 30); // Adjusted position to be above the certificate image
                certificateFrame.add(nameLabel);

                certificateImageLabel = new JLabel();
                certificateImageLabel.setIcon(new ImageIcon("C:\\Users\\certificate.png")); // Change the path to your certificate image
                certificateImageLabel.setBounds(100, 50, 900, 600);
                certificateFrame.add(certificateImageLabel);

                JLabel certificateLabel = new JLabel("This is to certify that " + s1 + " has successfully Generated.");
                certificateLabel.setFont(new Font("Serif", Font.BOLD, 20));
                certificateLabel.setBounds(180, 30, 550, 30);
                certificateLabel.setHorizontalAlignment(SwingConstants.CENTER);
                certificateFrame.add(certificateLabel);

                // Add a download button
                downloadBtn = new JButton("Download Certificate");
                downloadBtn.setBounds(350, 610, 200, 30);
                downloadBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Implement download functionality here
                        certificateFrame.setBounds(400,500,800,600);
                        downloadCertificate(certificateFrame);
                    }
                });
                certificateFrame.add(downloadBtn);

                certificateFrame.setVisible(true);

                // Dispose of the current frame
                dispose();
            } else {
                JOptionPane.showMessageDialog(btn1, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            tf1.setText("");
            tf2.setText("");
            tf3.setText("");
            tf8.setText("");
        }
    }

    private void downloadCertificate(JFrame certificateFrame) {
        // Capture the content of the certificateFrame as an image
        BufferedImage image = new BufferedImage(certificateFrame.getWidth(), certificateFrame.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        certificateFrame.getContentPane().paint(g2); // Only capture the content pane
        g2.dispose();

        // Save the captured image as a file
        try {
            File outputFile = new File(System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "certificate.png"); // Save to the user's Downloads folder
            ImageIO.write(image, "png", outputFile);
            JOptionPane.showMessageDialog(downloadBtn, "Certificate downloaded successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(downloadBtn, "Error downloading certificate", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new certificate();
    }
}