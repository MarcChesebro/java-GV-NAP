import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainGui {
	JPanel panel;
	JFrame frame;
	 JLabel connection, ServerhostName, port, userName, hostName, speed;
	JTextField ServerhostNameTxt, portTxt, userNameTxt, hostNameTxt;
	JButton connect;
		
		
	public MainGui() {
		gui();
		
	}
	
	public void gui() {
		JFrame frame = new JFrame("Client GUI");
	    frame.setVisible(true);
	    frame.setSize(500,500);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    GridBagConstraints c = new GridBagConstraints();
	    
	    panel = new JPanel(new GridBagLayout());
	    
	    connection = new JLabel("Connection:");
		c.gridx=0;
		c.gridy=0;
		panel.add(connection,c);
		
		ServerhostName = new JLabel("Server Hostname:");
		c.gridx=0;
		c.gridy=1;
		panel.add(ServerhostName, c);
		
		ServerhostNameTxt = new JTextField();
		c.ipadx=500;
		c.gridx=1;
		c.gridy=1;
		panel.add(ServerhostNameTxt, c);
		
		port = new JLabel("Port:");
		c.ipadx=0;
		c.gridx=2;
		c.gridy=1;
		panel.add(port, c);
		
		portTxt = new JTextField();
		c.ipadx=500;
		c.gridx=3;
		c.gridy=1;
		panel.add(portTxt,c);

		connect = new JButton("Connect");
		c.ipadx=0;
		c.gridx=4;
		c.gridy=1;
		panel.add(connect, c);

		userName = new JLabel("UserName:");
		c.gridx=0;
		c.gridy=2;
		panel.add(userName, c);

		userNameTxt = new JTextField("username", 64);
		c.gridx=1;
		c.gridy=2;
		panel.add(userNameTxt,c);

		hostName = new JLabel("Hostname:");
		c.gridx=2;
		c.gridy=2;
		panel.add(hostName, c);

		hostNameTxt = new JTextField("hostname",64);
		c.gridx=3;
		c.gridy=2;
		panel.add(hostNameTxt, c);
		
		frame.add(panel);
		frame.pack();
	}
	 public static void main (String[] args) {

		 new MainGui();
	 }
}
