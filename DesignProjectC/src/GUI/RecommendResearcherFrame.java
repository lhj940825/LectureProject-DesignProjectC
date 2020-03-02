package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import DataStructure.AdjacencyList;
import Streaming.RealTimeStreamThread.callBack;

import javax.swing.JComboBox;

public class RecommendResearcherFrame extends JFrame {

	private JPanel contentPane;
	private AdjacencyList researchers;
	private static ArrayList<String> selectedResearcher;
	private DefaultListModel subscribeModel;
	public callBack m_callBack;

	public interface callBack {
		void recommendRequest(String Reserchers) throws SQLException;
	}

	public void setCallBack(callBack callBack) {
		m_callBack = callBack;
	}

	/**
	 * Launch the application.
	 */
	public void showFrame() {

		RecommendResearcherFrame.this.setVisible(true);

	}

	public RecommendResearcherFrame() {
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][][204.00,grow][27.00][118.00,grow][grow,left]", "[][205.00,grow,bottom][grow][]"));

		JLabel lblNewLabel = new JLabel("Researchers List");
		contentPane.add(lblNewLabel, "cell 0 0 3 1,alignx center,aligny center");

		JLabel lblNewLabel_1 = new JLabel("Resercher");
		contentPane.add(lblNewLabel_1, "flowx,cell 4 0 2 1,alignx center");

		JList researcherList = new JList();
		JScrollPane scroll1 = new JScrollPane(researcherList);
		contentPane.add(scroll1, "cell 1 1 2 1,grow");

		JList subscribeList = new JList();
		JScrollPane scroll2 = new JScrollPane(subscribeList);
		contentPane.add(scroll2, "cell 4 1 2 1,grow");

	}

	/**
	 * Create the frame.
	 */
	public RecommendResearcherFrame(AdjacencyList researchers, ArrayList<String> subscribe) {
		this.researchers = researchers;
		this.selectedResearcher = subscribe;
		setTitle("Select Researcher who want to take a recommendation from System");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					m_callBack.recommendRequest(RecommendResearcherFrame.this.selectedResearcher.get(0));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
			}
		});
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][][204.00,grow][27.00][118.00,grow][grow,left]", "[][205.00,grow,bottom][grow][]"));

		JLabel lblNewLabel = new JLabel("Researchers List");
		contentPane.add(lblNewLabel, "cell 0 0 3 1,alignx center,aligny center");

		JLabel lblNewLabel_1 = new JLabel("Selected Researcher");
		contentPane.add(lblNewLabel_1, "flowx,cell 4 0 2 1,alignx center");

		DefaultListModel researcherModel = new DefaultListModel();
		JList researcherList = new JList(researcherModel);
		for (AdjacencyList.vertex temp : this.researchers.getTotalVertex()) {

			researcherModel.addElement(temp.getResearcher().getname());

		}
		JScrollPane scroll1 = new JScrollPane(researcherList);
		contentPane.add(scroll1, "cell 1 1 2 1,grow");

		subscribeModel = new DefaultListModel();
		JList subscribeList = new JList(subscribeModel);
		for (String temp : this.selectedResearcher) {
			subscribeModel.addElement(temp);
		}
		JScrollPane scroll2 = new JScrollPane(subscribeList);
		contentPane.add(scroll2, "cell 4 1 2 1,grow");

		researcherList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				JList theList = (JList) mouseEvent.getSource();
				if (mouseEvent.getClickCount() == 2) {
					int index = theList.locationToIndex(mouseEvent.getPoint());
					if (index >= 0) {
						Object o = theList.getModel().getElementAt(index);
						subscribeModel.addElement(o);
						RecommendResearcherFrame.selectedResearcher.add(o.toString());
					}
				}
			}
		});

		subscribeList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				JList theList = (JList) mouseEvent.getSource();
				if (mouseEvent.getClickCount() == 2) {
					int index = theList.locationToIndex(mouseEvent.getPoint());
					if (index >= 0) {
						Object o = theList.getModel().getElementAt(index);
						subscribeModel.removeElement(o);
						RecommendResearcherFrame.selectedResearcher.remove(o);
					}
				}
			}
		});

	}

	public void setResearchers(AdjacencyList researchers) {
		this.researchers = researchers;
	}

	public void setSubscribeList(ArrayList<String> subscribeList) {
		this.selectedResearcher = subscribeList;
	}
}
