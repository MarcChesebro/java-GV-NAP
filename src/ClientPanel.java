import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;

public class ClientPanel extends JPanel {

  public ClientPanel() {
    JLabel connection, hostName, port;
    JTextField hostNameTxt;
    JButton connect;

    JPanel pane = new Jpanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    connection = new JLabel("Connection:");
    c.gridx=0;
    c.gridy=0;
    pane.add(connection, c);

    hostName = new JLabel("Server Hostname:");
    c.gridx=0;
    c.gridy=1;
    pane.add(hostName, c);

    hostNameTxt = new JTextField("IP address", 64);
    c.gridx=1;
    c.gridy=1;
    pane.add(hostNameTxt, c);
  
    port = new JLabel("Port:");
    c.gridx=2;
    c.gridy=1;
    pane.add(port, c);

    connect = new JButton("Connect");
    c.gridx=3;
    c.gridy=1;
    pane.add(connect, c);



  }
}
