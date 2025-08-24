package vivek;
import javax.swing.*;
import java.awt.*;
public class JCheckBox23 extends JFrame {
	JCheckBox c1,c2,c3,c4;
    JLabel l1;

	public JCheckBox23() {
		l1 = new JLabel("CheakBox");
		c1 = new JCheckBox("Option 1");
		c2 = new JCheckBox("Option 2");
		c3 = new JCheckBox("Option 3");
		c4 = new JCheckBox("Option 4");
		add(l1);
		add(c1);
		add(c2);
		add(c3);
		add(c4);
		setVisible(true);
		setLayout(new FlowLayout());
		setTitle("CheckBox");
		setSize(400,600);
		}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 new JCheckBox23();
	}

}

