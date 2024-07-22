package msc;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;
import java.awt.Font;

public class SeverityList {

	JFrame severityListFrame;
	private JLabel severityHeaderLabel;
	private JLabel severityValueLabel;
	private JList<String> list;
	String severityType;
	private JButton backBtn;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					SeverityList window = new SeverityList();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public SeverityList(OWLAPIFirst owlapipage,String severity, int severityScore,List<String> diseaseList,String queryInputString) {
		initialize(owlapipage,severity,severityScore,diseaseList,queryInputString);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(OWLAPIFirst owlapipage,String severity, int severityScore,List<String> diseaseList,String queryInputString) {
		severityListFrame = new JFrame();
		severityListFrame.setBounds(100, 100, 450, 450);
		severityListFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		severityListFrame.getContentPane().setLayout(null);
		
		severityHeaderLabel = new JLabel("Severity :");
		severityHeaderLabel.setBounds(40, 10, 100, 20);
		severityListFrame.getContentPane().add(severityHeaderLabel);
		
		list = new JList<String>();
		list.setFont(new Font("Times New Roman", Font.BOLD, 20));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedListItem=list.getSelectedIndex();
				if (selectedListItem>=0) {
					String diseaseName=diseaseList.get(selectedListItem);
					severityListFrame.setVisible(false);
					severityListFrame.dispose();					
					DiseaseDetail page=new DiseaseDetail(owlapipage,diseaseName,severity,severityScore,diseaseList,queryInputString);
					page.DiseaseDetailFrame.setVisible(true);
				}
			}
		});
		list.setBounds(40, 41, 340, 311);
		severityListFrame.getContentPane().add(list);
		
		severityValueLabel = new JLabel("");
		severityValueLabel.setBounds(136, 10, 100, 20);
		severityListFrame.getContentPane().add(severityValueLabel);
		
		severityValueLabel.setText(severity);
		
		severityType=severity;
		
		//jlist
		DefaultListModel<String> DLM=new DefaultListModel<String>();
		for (int i=0;i<diseaseList.size();i++) {
			DLM.addElement(diseaseList.get(i));
		}
		list.setModel(DLM);
		
		backBtn = new JButton("Back");
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				severityListFrame.setVisible(false);
				severityListFrame.dispose();
				try {
					QueryResult page=new QueryResult(queryInputString);
					page.QueryResultFrame.setVisible(true);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		backBtn.setBounds(10, 379, 89, 23);
		severityListFrame.getContentPane().add(backBtn);
	}

}
