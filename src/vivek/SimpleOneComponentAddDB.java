package vivek;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class SimpleOneComponentAddDB extends JFrame implements ActionListener, KeyListener {

    JLabel l1;
    JTextField t1;
    JButton b1;
    JPanel jpanel;
    static final String DB_URL = "jdbc:sqlserver://VIVEK\\SQLEXPRESS:1433;databaseName=vivek;encrypt=true;trustServerCertificate=true";
    static final String USER = "sa";
    static final String PASS = "King@3010";

    public SimpleOneComponentAddDB() {
        l1 = new JLabel("Name");
        t1 = new JTextField(20);
        b1 = new JButton("Submit");
        jpanel = new JPanel();
        add(l1);
        add(t1);
        add(b1);
        setVisible(true);
        setTitle("Insert Demo");
        setSize(500, 500);
        setLayout(new FlowLayout());
        b1.addActionListener(this);
        t1.addKeyListener(this);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
            try {
                Connection con;
                con = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement ps = con.prepareStatement("Insert into sy values(?)");
                String name = t1.getText();
                ps.setString(1, name);
                int rowsAffected = ps.executeUpdate();
                System.out.println("@@ Row Value is=" + rowsAffected);
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(jpanel, "Data inserted successfully");
                } else {
                    JOptionPane.showMessageDialog(jpanel, "Failed to insert data");
                }
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        new SimpleOneComponentAddDB();
    }
}
