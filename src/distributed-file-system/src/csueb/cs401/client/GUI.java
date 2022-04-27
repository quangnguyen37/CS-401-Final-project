import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUI extends JFrame {
	 
	 private JFrame frame;
	 private JPanel panel;
	 JList dvdJList;
	 JScrollPane FileListPane;
	 String titleText = "FileName: ";
	 JLabel titleLabel, ratingLabel, runtimeLabel;
	 
	 JButton blogin;
	 JPanel loginpanel;
	 JTextField txuser;
	 JTextField pass;
	 JButton newUSer;
	 JLabel username;
	 JLabel password;
	  
	 JFrame frame2;
	 JTextField searchFile;
	 JButton searchFileButton;
	 JLabel searchFileLabel;
	 
	 
	 public GUI()
	 {
		 
		 ClientGUIComponents();
		 login();
		 requestFile();
	 }
	 
		public void ClientGUIComponents() {
			frame = new JFrame("GUI");
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			JPanel listPanel = new JPanel();
			frame.add(listPanel);

			// File Info Display
			Box infoBox = Box.createVerticalBox();
			// infoBox.add(Box.createVerticalStrut(10));
			titleLabel = new JLabel(titleText);
			infoBox.add(titleLabel);

			Box buttonBox = Box.createVerticalBox();
			JButton addButton = new JButton("Post File");
			addButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addFile();
				}
			});
			buttonBox.add(addButton);
			buttonBox.add(Box.createVerticalStrut(10));
			JButton requestFileButton = new JButton("Request File");
			requestFileButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					requestFile();
					frame2.setVisible(true);	
				}
			});
			buttonBox.add(requestFileButton);
			buttonBox.add(Box.createVerticalStrut(10));
			JButton eventLogButton = new JButton("Event Log");
			eventLogButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					eventLog();
				}
			});
			buttonBox.add(eventLogButton);
			buttonBox.add(Box.createVerticalStrut(10));
			JButton saveAndExit = new JButton("Save and Exit");
			eventLogButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					saveAndExit();
				}
			});
			buttonBox.add(saveAndExit);
			buttonBox.add(Box.createVerticalStrut(20));
			listPanel.add(buttonBox);
			// Show the Frame
			frame.setVisible(false);
			frame.pack();
		}
		
		public void processCommands() {

		}
		
		public void addFile() {

		}

		private void requestFile() {
			frame2 = new JFrame("Search");
			frame2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			JPanel searchPanel = new JPanel();
			frame2.add(searchPanel);
			
			searchFileButton = new JButton("Search");
			searchFile = new JTextField(15);
			searchFileLabel = new JLabel("File name: ");
			
			setSize(300, 200);
			setLocation(500, 280);

			searchFile.setBounds(90, 30, 150, 20);
			searchFileLabel.setBounds(20, 28, 80, 20);
			searchFileButton.setBounds(110, 100, 80, 20);
			
			searchPanel.add(searchFileLabel);
			searchPanel.add(searchFile);
			searchPanel.add(searchFileButton);
			
			searchFileButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

				}

			});
			
			frame2.pack();
			frame2.setVisible(false);
			
		}

		public void eventLog() {

		}
		public void saveAndExit() {

		}

		private void login() {

			blogin = new JButton("Login");
			loginpanel = new JPanel();
			txuser = new JTextField(15);
			pass = new JPasswordField(15);
			username = new JLabel("Username: ");
			password = new JLabel("Password: ");

			setSize(300, 200);
			setLocation(500, 280);
			loginpanel.setLayout(null);

			txuser.setBounds(90, 30, 150, 20);
			pass.setBounds(90, 65, 150, 20);
			blogin.setBounds(110, 100, 80, 20);
			username.setBounds(20, 28, 80, 20);
			password.setBounds(20, 63, 80, 20);

			loginpanel.add(blogin);
			loginpanel.add(txuser);
			loginpanel.add(pass);
			loginpanel.add(username);
			loginpanel.add(password);

			getContentPane().add(loginpanel);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);

			Writer writer = null;
			File check = new File("passWordlist.txt");
			if (check.exists()) {

			} else {
				try {
					File texting = new File("passWordlist.txt");
					writer = new BufferedWriter(new FileWriter(texting));
					writer.write("message");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			blogin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						File file = new File("passWordlist.txt");
						Scanner scan = new Scanner(file);
						;
						String line = null;
						FileWriter filewrite = new FileWriter(file, true);

						String usertxt = " ";
						String passtxt = " ";
						String puname = txuser.getText();
						String ppaswd = pass.getText();

						while (scan.hasNext()) {
							usertxt = scan.nextLine();
							passtxt = scan.nextLine();
						

						if (puname.equals(usertxt) && ppaswd.equals(passtxt)) {
							setVisible(false);
							frame.setVisible(true);
							dispose();
						}
						} 
						if (puname.equals("") && ppaswd.equals("")) {
							JOptionPane.showMessageDialog(null, "Please enter info");
						} 
						else if (puname != (usertxt) || ppaswd != (passtxt)) {
							txuser.setText("");
							pass.setText("");
							txuser.requestFocus();
						}
						else {
							
						}
					} 
					catch (IOException d) {
						d.printStackTrace();
					}

				}

			});

		}

	}
	
		

