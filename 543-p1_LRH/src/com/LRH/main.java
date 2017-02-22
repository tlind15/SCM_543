import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class vrsn_ctrl_gui
{

	public void createMenuBar(JFrame frame)
	{
		 JMenuBar menu_bar = new JMenuBar();
		 JMenu view_menu = new JMenu("View");
		 JMenuItem activity_logs = new JMenuItem("Activity Logs");
		 view_menu.add(activity_logs);
		 menu_bar.add(view_menu);
		 frame.add(menu_bar);
		 frame.setJMenuBar(menu_bar);
	}

	public void createMainPanel(JPanel panel)
	{
		panel.setBackground(Color.white);
		Insets spac = new Insets(10, 10, 2, 2);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = spac;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.setLayout(layout);
		JLabel repoLabel = new JLabel("Repo: CECS543/trunk");
		gbc.gridx = 0; gbc.gridy = 0;
		gbc.gridwidth = 2;
		panel.add(repoLabel, gbc);
		JLabel dirCpyLabel = new JLabel("Directory to Copy: ");
		gbc.gridx = 0; gbc.gridy = 1;
		gbc.gridwidth = 1;
		panel.add(dirCpyLabel, gbc);
		JTextField dirCpy = new JTextField(30);
		gbc.gridx = 1; gbc.gridy = 1;
		panel.add(dirCpy, gbc);
		JLabel repoLocLabel = new JLabel("Repo Location: ");
		gbc.gridx = 0; gbc.gridy = 2;
		gbc.gridwidth = 1;
		panel.add(repoLocLabel, gbc);
		JTextField repoLoc = new JTextField(10);
		gbc.gridx = 1; gbc.gridy = 2;
		panel.add(repoLoc, gbc);
		JButton createButton = new JButton("Create");
		gbc.gridx = 1; gbc.gridy = 3;
		createButton.addActionListener(new createRepo());
		panel.add(createButton, gbc);
	}
	class createRepo implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{

		}
	}
	vrsn_ctrl_gui()
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

	public static void main(String[] args)
	{
		new vrsn_ctrl_gui();
	}
}
