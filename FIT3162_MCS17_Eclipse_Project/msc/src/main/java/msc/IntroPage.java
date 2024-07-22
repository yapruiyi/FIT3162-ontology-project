package msc;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IntroPage {

	private JFrame frame;
	private JLabel groupNameLabel;
	private JLabel groupMembersLabel;
	private JButton openMainPageBtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IntroPage window = new IntroPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public IntroPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		groupNameLabel = new JLabel("FIT3162 Eclipse App");
		groupNameLabel.setBounds(40, 53, 341, 14);
		frame.getContentPane().add(groupNameLabel);
		
		groupMembersLabel = new JLabel("Tan Jin En, Yap Rui Yi, Seow Zheng Hao");
		groupMembersLabel.setBounds(40, 91, 271, 20);
		frame.getContentPane().add(groupMembersLabel);
		
		openMainPageBtn = new JButton("Open Main Page");
		openMainPageBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				QueryPage page=new QueryPage();
				page.QueryPageFrame.setVisible(true);
			}
		});
		openMainPageBtn.setBounds(28, 130, 157, 23);
		frame.getContentPane().add(openMainPageBtn);
	}

}
