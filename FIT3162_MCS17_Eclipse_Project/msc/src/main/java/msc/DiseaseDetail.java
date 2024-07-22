package msc;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.Color;

public class DiseaseDetail {

	JFrame DiseaseDetailFrame;
	private JLabel severityHeaderLabel;
	private JLabel saverityValueLabel;
	private JLabel diseaseDescription;
	private JLabel diseaseNameLabel;
	private JButton backBtn;
	private JLabel severityDescLabel;
	private JLabel nameLabel_1;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args, String diseaseName) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					DiseaseDetail window = new DiseaseDetail(diseaseName);
//					window.DiseaseDetailFrame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public DiseaseDetail(OWLAPIFirst owlapipage, String diseaseName, String severity, int severityScore,List<String> diseaseList,String queryInputString) {
		initialize(owlapipage,diseaseName, severity, severityScore,diseaseList,queryInputString);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(OWLAPIFirst owlapipage,String diseaseName, String severity, int severityScore,List<String> diseaseList,String queryInputString) {
		DiseaseDetailFrame = new JFrame();
		DiseaseDetailFrame.getContentPane().setBackground(new Color(255, 255, 255));
		DiseaseDetailFrame.setBounds(100, 100, 450, 450);
		DiseaseDetailFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DiseaseDetailFrame.getContentPane().setLayout(null);
		
		severityHeaderLabel = new JLabel("Severity :");
		severityHeaderLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		severityHeaderLabel.setBounds(50, 30, 140, 30);
		DiseaseDetailFrame.getContentPane().add(severityHeaderLabel);
		
		saverityValueLabel = new JLabel("New label");
		saverityValueLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		saverityValueLabel.setBounds(200, 30, 200, 30);
		saverityValueLabel.setText(severity);
		DiseaseDetailFrame.getContentPane().add(saverityValueLabel);
		
		diseaseNameLabel = new JLabel("New label");
		diseaseNameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		diseaseNameLabel.setBounds(200, 70, 200, 30);
		diseaseNameLabel.setText(diseaseName);
		DiseaseDetailFrame.getContentPane().add(diseaseNameLabel);
		
		diseaseDescription = new JLabel("");
		diseaseDescription.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		diseaseDescription.setVerticalAlignment(SwingConstants.TOP);
		diseaseDescription.setBounds(50, 151, 304, 192);
		DiseaseDetailFrame.getContentPane().add(diseaseDescription);
		diseaseDescription.setText("<html>"+owlapipage.getDescription(diseaseName)+"</html>");
		
		backBtn = new JButton("Back");
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DiseaseDetailFrame.setVisible(false);
				DiseaseDetailFrame.dispose();
				SeverityList page=new SeverityList(owlapipage,severity,severityScore,diseaseList,queryInputString);
				page.severityListFrame.setVisible(true);
			}
		});
		backBtn.setBounds(10, 379, 89, 23);
		DiseaseDetailFrame.getContentPane().add(backBtn);
		
		severityDescLabel = new JLabel("Description :");
		severityDescLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		severityDescLabel.setBounds(50, 110, 150, 30);
		DiseaseDetailFrame.getContentPane().add(severityDescLabel);
		
		nameLabel_1 = new JLabel("Name :");
		nameLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		nameLabel_1.setBounds(50, 70, 140, 30);
		DiseaseDetailFrame.getContentPane().add(nameLabel_1);
		
		
	}
}
