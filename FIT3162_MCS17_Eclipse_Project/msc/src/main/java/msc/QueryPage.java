package msc;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class QueryPage {

	JFrame QueryPageFrame;
	private JTextField queryInputTextfield;
	private JButton querySearchBtn;
	private JLabel queryPageHeader;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QueryPage window = new QueryPage();
					window.QueryPageFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public QueryPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		QueryPageFrame = new JFrame();
		QueryPageFrame.setTitle("FIT3162 Query");
		QueryPageFrame.setBounds(100, 100, 450, 450);
		QueryPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		QueryPageFrame.getContentPane().setLayout(null);
		
		queryPageHeader = new JLabel("Input Text For Query");
		queryPageHeader.setBounds(30, 52, 241, 14);
		QueryPageFrame.getContentPane().add(queryPageHeader);
		
		queryInputTextfield = new JTextField();
		queryInputTextfield.setBounds(30, 88, 212, 20);
		QueryPageFrame.getContentPane().add(queryInputTextfield);
		queryInputTextfield.setColumns(10);
		
		querySearchBtn = new JButton("Search");
		querySearchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				QueryPageFrame.dispose();
				QueryResult page;
				try {
					page = new QueryResult(queryInputTextfield.getText());
					page.QueryResultFrame.setVisible(true);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		querySearchBtn.setBounds(30, 133, 89, 23);
		QueryPageFrame.getContentPane().add(querySearchBtn);
	}
}
