package vivek;
import java.applet.Applet;
import java.awt.Button;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class applet extends Applet implements ActionListener {

	 private Button clickButton;

	    public void init() {
	        clickButton = new Button("Click Me");
	        clickButton.addActionListener(this);
	        add(clickButton);
	    }

	    public void paint(Graphics g) {
	        g.drawString("Welcome to My GUI Applet!", 20, 50);
	    }

	    public void actionPerformed(ActionEvent e) {
	        if (e.getSource() == clickButton) {
	            showStatus("Button clicked!");
	        }
	    }

}
/**<html>
*<body>
*    <applet code="MyGUIApplet.class" width="300" height="200">
*        Your browser does not support Java applets.
*    </applet>
*</body>
*</html>
**/


