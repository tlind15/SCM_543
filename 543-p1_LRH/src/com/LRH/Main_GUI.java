//Jocelyn Ramirez - jsyramirez@gmail.com
//
//This file implements the GUI for the Version Control Application.
//It also starts up the GUI and calls in the classes/methods
//to actually create the repo, write acitvity logs, etc.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

class Main_GUI extends TemporaryClass //Will use the implemenatation class
{
	//Variables from user input
	private JTextField username = new JTextField(30);
	private JTextField dirCpy = new JTextField(30);
	private JTextField repoLoc = new JTextField(10);
	private String manifestoFile = "../activity_logs.txt";
	private TemporaryClass m_tc = new TemporaryClass();

	//create menu bar at the top of the GUI
	public void createMenuBar(JFrame frame)
	{
		 JMenuBar menu_bar = new JMenuBar();
		 JMenu view_menu = new JMenu("View");
		 JMenuItem activity_logs = new JMenuItem("Activity Logs");
		 activity_logs.addActionListener(new openLogs());
		 view_menu.add(activity_logs);
		 menu_bar.add(view_menu);
		 frame.add(menu_bar);
		 frame.setJMenuBar(menu_bar);
	}
	//create the main page of the gui
	public void createMainPanel(JPanel panel)
	{
		panel.setBackground(Color.white);
		Insets spac = new Insets(10, 10, 2, 2);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = spac;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.setLayout(layout);
		JLabel usernameLabel = new JLabel("Username: ");
		gbc.gridx = 0; gbc.gridy = 0;
		gbc.gridwidth = 2;
		panel.add(usernameLabel, gbc);
		gbc.gridx = 1; gbc.gridy = 0;
		panel.add(username, gbc);
		JLabel dirCpyLabel = new JLabel("Directory to Copy: ");
		gbc.gridx = 0; gbc.gridy = 1;
		gbc.gridwidth = 1;
		panel.add(dirCpyLabel, gbc);
		gbc.gridx = 1; gbc.gridy = 1;
		panel.add(dirCpy, gbc);
		JLabel repoLocLabel = new JLabel("Repo Location: ");
		gbc.gridx = 0; gbc.gridy = 2;
		gbc.gridwidth = 1;
		panel.add(repoLocLabel, gbc);
		gbc.gridx = 1; gbc.gridy = 2;
		panel.add(repoLoc, gbc);
		JButton createButton = new JButton("Create");
		gbc.gridx = 1; gbc.gridy = 3;
		createButton.addActionListener(new createRepo());
		panel.add(createButton, gbc);
	}
	//implement event when user hits create repo
	class createRepo implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{
			String ui_username = username.getText();
			String ui_dirCpy = dirCpy.getText();
			String ui_repoLoc = repoLoc.getText();
			//call create repo
			
			//call manifesto edit
			m_tc.writeToManifesto_RepoCreate(ui_repoLoc, ui_dirCpy, ui_username, manifestoFile);
		}
	}
	//impl. event when user has selected to view activity logs
	class openLogs implements ActionListener {
  		public void actionPerformed(ActionEvent e) {
			File file = new File(manifestoFile);
			try(FileReader fileReader = new FileReader(file))
			{
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				StringBuffer stringBuffer = new StringBuffer();
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					stringBuffer.append(line);
					stringBuffer.append("\n");
				}
			fileReader.close();
			JOptionPane.showMessageDialog(null, stringBuffer.toString(), "Activity Logs", JOptionPane.PLAIN_MESSAGE);
			}catch (IOException f) {System.out.println("Could not read Activity Logs");}
		}
	}
	//set the main function for the GUI
	Main_GUI()
	{
		final JFrame frame = new JFrame("Version Control");
		JPanel panel = new JPanel();
		createMenuBar(frame);
		createMainPanel(panel);
		frame.add(panel);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	//start running the gui
	public static void main(String[] args)
	{
		new Main_GUI();
	}
}
