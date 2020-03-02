package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSlider;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JButton;

import com.mxgraph.model.mxCell;

import DB.DataBase;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class yearlyPaperSearch extends JFrame {

	private JPanel contentPane;
	private JSlider minSlider;
	private JSlider maxSlider;
	private DataBase db;

	/**
	 * Launch the application.
	 */
	public void startSearch() {
		yearlyPaperSearch.this.setVisible(true);
	}

	public void YearlySearch() {

	}

	/**
	 * Create the frame.
	 */
	public yearlyPaperSearch(int minYear, int maxYear) {
		setTitle("Paple Search By Years");
		setBounds(100, 100, 450, 261);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[432px]", "[26px][][][][][][][]"));

		minSlider = new JSlider(JSlider.HORIZONTAL, minYear, maxYear, (minYear + maxYear) / 2);
		contentPane.add(minSlider, "cell 0 1,growx,aligny top");
		minSlider.setPaintLabels(true);
		minSlider.setPaintTicks(true);
		minSlider.setMajorTickSpacing((maxYear - minYear) / 4);
		minSlider.setMinorTickSpacing(1);
		minSlider.setValue(minYear);

		maxSlider = new JSlider(JSlider.HORIZONTAL, minYear, maxYear, (minYear + maxYear) / 2);
		contentPane.add(maxSlider, "cell 0 3,growx");
		maxSlider.setPaintLabels(true);
		maxSlider.setPaintTicks(true);
		maxSlider.setMajorTickSpacing((maxYear - minYear) / 4);
		maxSlider.setMinorTickSpacing(1);
		maxSlider.setValue(maxYear);
		JLabel lblMinYear = new JLabel("Min Year");
		contentPane.add(lblMinYear, "cell 0 0");

		JLabel lblMaxYear = new JLabel("Max Year");
		contentPane.add(lblMaxYear, "cell 0 2,alignx right");

		JButton btnSearch = new JButton("Paper Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					showData(minSlider.getValue(), maxSlider.getValue());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		contentPane.add(btnSearch, "flowx,cell 0 6 1 2,alignx center,growy");

		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
	}

	public void showData(int minYear, int maxYear) throws SQLException {
		db = new DataBase();
		ArrayList<String> papers = db.getpaperData(String.valueOf(minYear), String.valueOf(maxYear));
		paperTable table = new paperTable(papers);
		table.showTable();
	}
}
