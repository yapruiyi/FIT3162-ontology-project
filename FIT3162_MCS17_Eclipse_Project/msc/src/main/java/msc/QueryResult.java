package msc;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

public class QueryResult {

	JFrame QueryResultFrame;
	private JLabel resultImageLabel;
	String queryInput;
	
	//owlapi
	List<String> resultList;
	List<String> invalidInputList;
	private JLabel severityRatingValueLabel;
	private JLabel severityRatingLabel;
	boolean matchFound;
	OWLAPIFirst owlapipage;
	
	//Severity display
	int lowSeverityCounter=0;
	List<String> lowSeverityList=new ArrayList<String>();
	int midSeverityCounter=0;
	List<String> midSeverityList=new ArrayList<String>();
	int highSeverityCounter=0;
	List<String> highSeverityList=new ArrayList<String>();
	
	//Severity Buttons
	private JButton highSeverityButton;
	int highSeverityButtonX=125;
	int highSeverityButtonY=350;
	int highSeverityButtonWidth=50;
	int highSeverityButtonHeight=20;
	private JButton midSeverityButton;
	int midSeverityButtonX=200;
	int midSeverityButtonY=350;
	int midSeverityButtonWidth=50;
	int midSeverityButtonHeight=20;
	private JButton lowSeverityButton;
	int lowSeverityButtonX=275;
	int lowSeverityButtonY=350;
	int lowSeverityButtonWidth=50;
	int lowSeverityButtonHeight=20;
	private JLabel severityRatingIntegerLabel;
	private JButton backButton;
	private JLabel errorMsg1;
	private JLabel errorMsg2;
	private JLabel errorMsg3;
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args,String queryInputString) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QueryResult window = new QueryResult(queryInputString);
					window.QueryResultFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 * @throws InterruptedException 
	 */
	public QueryResult(String queryInputString) throws InterruptedException {
		//save query input value
		queryInput=queryInputString;
		
		initialize();
		
		owlapiquery();
		
		if (!matchFound) {
			QueryResultFrame.dispose();
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		QueryResultFrame = new JFrame();
		QueryResultFrame.getContentPane().setBackground(new Color(255, 255, 255));
		QueryResultFrame.setBounds(100, 100, 450, 450);
		QueryResultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		QueryResultFrame.getContentPane().setLayout(null);
		
		resultImageLabel = new JLabel("");
		resultImageLabel.setBounds(115, 71, 200, 200);
		QueryResultFrame.getContentPane().add(resultImageLabel);
		Image img=new ImageIcon(this.getClass().getResource("/heart_stage_1.png")).getImage();
		resultImageLabel.setIcon(new ImageIcon(img));

		severityRatingLabel = new JLabel("Severity Rating:");
		severityRatingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		severityRatingLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		severityRatingLabel.setBounds(115, 10, 150, 30);
		QueryResultFrame.getContentPane().add(severityRatingLabel);
		
		severityRatingValueLabel = new JLabel("");
		severityRatingValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		severityRatingValueLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		severityRatingValueLabel.setBounds(0, 45, 450, 30);
		QueryResultFrame.getContentPane().add(severityRatingValueLabel);
		
		highSeverityButton = new JButton("");
		highSeverityButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (highSeverityCounter!=0) {
					QueryResultFrame.setVisible(false);
					QueryResultFrame.dispose();
					SeverityList page=new SeverityList(owlapipage,"high",highSeverityCounter,highSeverityList,queryInput);
					page.severityListFrame.setVisible(true);
				}
			}
		});
		highSeverityButton.setForeground(new Color(0, 0, 0));
		highSeverityButton.setBackground(new Color(255, 0, 0));
		highSeverityButton.setBounds(125, 350, highSeverityButtonWidth, highSeverityButtonHeight);
		QueryResultFrame.getContentPane().add(highSeverityButton);
		
		midSeverityButton = new JButton("");
		midSeverityButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (midSeverityCounter!=0) {
					QueryResultFrame.setVisible(false);
					QueryResultFrame.dispose();
					SeverityList page=new SeverityList(owlapipage,"mid",midSeverityCounter,midSeverityList,queryInput);
					page.severityListFrame.setVisible(true);
				}
			}
		});
		midSeverityButton.setForeground(new Color(0, 0, 0));
		midSeverityButton.setBackground(new Color(255, 128, 0));
		midSeverityButton.setBounds(200, 350, midSeverityButtonWidth, midSeverityButtonHeight);
		QueryResultFrame.getContentPane().add(midSeverityButton);
		
		lowSeverityButton = new JButton("");
		lowSeverityButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lowSeverityCounter!=0) {
					QueryResultFrame.setVisible(false);
					QueryResultFrame.dispose();
					SeverityList page=new SeverityList(owlapipage,"low",lowSeverityCounter,lowSeverityList,queryInput);
					page.severityListFrame.setVisible(true);
				}
			}
		});
		lowSeverityButton.setForeground(new Color(0, 0, 0));
		lowSeverityButton.setBackground(new Color(255, 255, 0));
		lowSeverityButton.setBounds(275, 350, lowSeverityButtonWidth, lowSeverityButtonHeight);
		QueryResultFrame.getContentPane().add(lowSeverityButton);
		
		severityRatingIntegerLabel = new JLabel("");
		severityRatingIntegerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		severityRatingIntegerLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		severityRatingIntegerLabel.setBounds(271, 10, 44, 30);
		QueryResultFrame.getContentPane().add(severityRatingIntegerLabel);
		
		backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				QueryResultFrame.setVisible(false);
				QueryResultFrame.dispose();
				QueryPage page=new QueryPage();
				page.QueryPageFrame.setVisible(true);
			}
		});
		backButton.setBounds(10, 375, 89, 23);
		QueryResultFrame.getContentPane().add(backButton);
		
		errorMsg1 = new JLabel("");
		errorMsg1.setHorizontalAlignment(SwingConstants.CENTER);
		errorMsg1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		errorMsg1.setBounds(0, 100, 450, 30);
		QueryResultFrame.getContentPane().add(errorMsg1);
		errorMsg1.setVisible(false);
		
		errorMsg2 = new JLabel("");
		errorMsg2.setHorizontalAlignment(SwingConstants.CENTER);
		errorMsg2.setFont(new Font("Times New Roman", Font.BOLD, 20));
		errorMsg2.setBounds(0, 150, 450, 30);
		QueryResultFrame.getContentPane().add(errorMsg2);
		errorMsg2.setVisible(false);
		
		errorMsg3 = new JLabel("");
		errorMsg3.setHorizontalAlignment(SwingConstants.CENTER);
		errorMsg3.setFont(new Font("Times New Roman", Font.BOLD, 20));
		errorMsg3.setBounds(0, 200, 450, 30);
		QueryResultFrame.getContentPane().add(errorMsg3);
		errorMsg3.setVisible(false);
		
	}

	public void owlapiquery() throws InterruptedException {
		owlapipage=new OWLAPIFirst(queryInput);

		matchFound=owlapipage.checkMatchFound();
		//at least 1 match found
		if (owlapipage.checkMatchFound()) {
			resultList=owlapipage.getResultList();
			
			//get severity list
			String currentSeverity;
			for (int i=0;i<resultList.size();i++) {
				currentSeverity=owlapipage.getSeverity(resultList.get(i));
				
				if (currentSeverity!="Severity not found") {

					if (currentSeverity.equals("low")) {
						lowSeverityCounter+=1;
						lowSeverityList.add(resultList.get(i));
					} else if(currentSeverity.equals("medium")) {
						midSeverityCounter+=1;
						midSeverityList.add(resultList.get(i));
					} else if(currentSeverity.equals("high")) {
						highSeverityCounter+=1;
						highSeverityList.add(resultList.get(i));
					}	
				}
			}
			
			//update table
			
			highSeverityButton.setText(Integer.toString(highSeverityCounter));
			midSeverityButton.setText(Integer.toString(midSeverityCounter));
			lowSeverityButton.setText(Integer.toString(lowSeverityCounter));
			int overallCounter=lowSeverityCounter+(midSeverityCounter*2)+(highSeverityCounter*3);
			
			if (highSeverityCounter>5) {
				highSeverityCounter=5;
			}
			if (midSeverityCounter>5) {
				midSeverityCounter=5;
			}
			if (lowSeverityCounter>5) {
				lowSeverityCounter=5;			
			}

			highSeverityButtonY-=(20*highSeverityCounter);
			highSeverityButtonHeight+=(20*highSeverityCounter);
			highSeverityButton.setBounds(highSeverityButtonX, highSeverityButtonY, highSeverityButtonWidth, highSeverityButtonHeight);
			
			midSeverityButtonY-=(20*midSeverityCounter);
			midSeverityButtonHeight+=(20*midSeverityCounter);
			midSeverityButton.setBounds(midSeverityButtonX, midSeverityButtonY, midSeverityButtonWidth, midSeverityButtonHeight);
			
			lowSeverityButtonY-=(20*lowSeverityCounter);
			lowSeverityButtonHeight+=(20*lowSeverityCounter);
			lowSeverityButton.setBounds(lowSeverityButtonX, lowSeverityButtonY, lowSeverityButtonWidth, lowSeverityButtonHeight);

			updateOverallSeverity(overallCounter);
			severityRatingIntegerLabel.setText(Integer.toString(overallCounter));
		} else {
			
			errorMsg1.setText("No match found, press back to try again");
			errorMsg1.setVisible(true);
			errorMsg2.setText("One or more values not found in the model");
			errorMsg2.setVisible(true);
			errorMsg3.setText("Ensure that inputs are spelled correctly");
			errorMsg3.setVisible(true);
			severityRatingLabel.setText("");
			severityRatingValueLabel.setText("");
			
			highSeverityButton.setVisible(false);
			midSeverityButton.setVisible(false);
			lowSeverityButton.setVisible(false);
			resultImageLabel.setVisible(false);
		}
	}
	
	public void updateOverallSeverity(int severityScore) {
		if (severityScore<2) {
			severityRatingValueLabel.setText("Probably Fine");
			Image img=new ImageIcon(this.getClass().getResource("/heart_stage_1.png")).getImage();
			resultImageLabel.setIcon(new ImageIcon(img));
		} else if (severityScore<5) {
			severityRatingValueLabel.setText("Might Have Some Problems");
			Image img=new ImageIcon(this.getClass().getResource("/heart_stage_2.png")).getImage();
			resultImageLabel.setIcon(new ImageIcon(img));
		} else if (severityScore<10) {
			severityRatingValueLabel.setText("Get That Checked");
			Image img=new ImageIcon(this.getClass().getResource("/heart_stage_3.png")).getImage();
			resultImageLabel.setIcon(new ImageIcon(img));
		} else if (severityScore<15) {
			severityRatingValueLabel.setText("There's a lot of possibilities");
			Image img=new ImageIcon(this.getClass().getResource("/heart_stage_4.png")).getImage();
			resultImageLabel.setIcon(new ImageIcon(img));
		} else if (severityScore<20) {
			severityRatingValueLabel.setText("Either really general, or you're in danger");
			Image img=new ImageIcon(this.getClass().getResource("/heart_stage_5.png")).getImage();
			resultImageLabel.setIcon(new ImageIcon(img));
		} else {
			severityRatingValueLabel.setText("How Are You Alive");
			Image img=new ImageIcon(this.getClass().getResource("/heart_stage_6.png")).getImage();
			resultImageLabel.setIcon(new ImageIcon(img));
		}
	}
}
