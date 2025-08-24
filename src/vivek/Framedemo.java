package vivek;

import javax.swing.*; 
import java.awt.*;

public class Framedemo extends JFrame {

    static JButton b1;
    static JTextField tf1, tf2;
    static JLabel l1, l2, l3, l4;
    static JTextArea a1, a2;

    public Framedemo() {
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new FlowLayout());

        l1 = new JLabel("Enter a number: ");
        tf1 = new JTextField(10);
        b1 = new JButton("Submit");

        l2 = new JLabel("Result: ");
        tf2 = new JTextField(10);

        l3 = new JLabel("Label 3");
        l4 = new JLabel("Label 4");
        
        a1 = new JTextArea(1,23);
        a2 = new JTextArea("Enter the address");
        

        add(l1);
        add(tf1);
        add(b1);
        add(l2);
        add(tf2);
        add(l3);
        add(l4);
        add(a1);
        add(a2);

        b1.addActionListener(e -> {
            int num = Integer.parseInt(tf1.getText());
            tf2.setText(Integer.toString(num * 2));
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new Framedemo();
    }
}



