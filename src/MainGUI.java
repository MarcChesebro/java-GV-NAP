import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainGui {
	JPanel panel;
	JFrame frame;
	 JLabel connection, ServerhostName, port, userName, hostName, speed;
	JTextField ServerhostNameTxt, portTxt, userNameTxt, hostNameTxt, speedTxt;
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
		c.ipadx=100;
		c.gridwidth=2;
		c.gridx=4;
		c.gridy=1;
		panel.add(connect, c);
		connect.addActionListener(new ButtonListener());

		userName = new JLabel("UserName:");
		c.ipadx=0;
		c.gridwidth=1;
		c.gridx=0;
		c.gridy=2;
		panel.add(userName, c);

		userNameTxt = new JTextField();
		c.ipadx=500;
		c.gridx=1;
		c.gridy=2;
		panel.add(userNameTxt,c);

		hostName = new JLabel("Hostname:");
		c.ipadx=0;
		c.gridx=2;
		c.gridy=2;
		panel.add(hostName, c);

		hostNameTxt = new JTextField();
		c.ipadx=500;
		c.gridx=3;
		c.gridy=2;
		panel.add(hostNameTxt, c);
		
		speed = new JLabel("Speed:");
		c.ipadx=0;
		c.gridx=4;
		c.gridy=2;
		panel.add(speed, c);
		
		speedTxt = new JTextField();
		c.ipadx=100;
		c.gridx=5;
		c.gridy=2;
		panel.add(speedTxt, c);
		
		frame.add(panel);
		frame.pack();
	}
	
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {

			if (connect == event.getSource()) {
				
				//Does Connect stuff?
				
			}
		}
	}
	
	 public static void main (String[] args) {

		 new MainGui();
	 }
	 
}
