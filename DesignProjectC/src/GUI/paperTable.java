package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class paperTable extends JFrame {
	JTable table; // View -(Visual Component)
	JTextArea tArea;
	DefaultTableModel tablemodel;

	//

	// controller : �𵨰� view�κ��� �����Ͽ� ���۹���̳� �̺�Ʈ ����� ��� �ϴ� �κ�.
	// XXXRenderer... XXXEditor...

	public paperTable() {
		/*
		 * super(":: JTableDemo ::"); tablemodel = new DefaultTableModel();
		 * 
		 * // tablemodel.setColumnIdentifiers(new String[] { "�̸�", "����", "����"
		 * }); table = new JTable(tablemodel); JScrollPane scroll = new
		 * JScrollPane(table); tablemodel.addRow(new String[] { "123", "2", "3"
		 * }); // Container cp = getContentPane(); cp.add(scroll); // �⺻
		 * ��������.....
		 * 
		 * addWindowListener(new WindowAdapter() {
		 * 
		 * public void windowClosing(WindowEvent e) { dispose(); } });
		 */
	} // JTableDemo() -----

	public paperTable(final ArrayList<String> data) {
		tablemodel = new DefaultTableModel();
		tablemodel.setColumnIdentifiers(new String[] { "Title", "CoauThor", "Year" });
		table = new JTable(tablemodel);
		JScrollPane scroll = new JScrollPane(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//
		tArea = new JTextArea(5, 15);
		JScrollPane sp = new JScrollPane(tArea);
		sp.setSize(500, 500);
		//
		for (int i = 0; i < data.size(); i++) {
			String[] dataArr = data.get(i).split("\\|\\|");
			tablemodel.addRow(new String[] { dataArr[0], dataArr[1], dataArr[2] });
		}

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				// do some actions here, for example
				// print first column value from selected row
				// System.out.println(table.getValueAt(table.getSelectedRow(),
				// 1).toString());
				String[] dataArr = data.get(table.getSelectedRow()).split("\\|\\|");
				tArea.setText("");
				for (int i = 0; i < dataArr[3].length(); i += 75) {
					if ((i + 75) < dataArr[3].length())
						tArea.append(dataArr[3].substring(i, i + 75) + '\n');
					else
						tArea.append(dataArr[3].substring(i, dataArr[3].length()) + '\n');
				}

			}
		});
		JButton btn = new JButton("Search Using Web");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String[] dataArr = data.get(table.getSelectedRow()).split("\\|\\|");
				System.setProperty("webdriver.gecko.driver", "C:\\Users\\HoJun\\Desktop\\경희대\\연구실\\내 공부\\Selenium\\geckodriver-v0.16.0-win64\\geckodriver.exe");
				WebDriver driver = new FirefoxDriver();
				String address = "https://scholar.google.co.kr/scholar?q=";
				String title = dataArr[0].replaceAll(" ", "%20");
				driver.get(address + title);
			}

		});
		Container cp = getContentPane();
		cp.add(scroll, BorderLayout.NORTH); //
		cp.add(sp, BorderLayout.CENTER);
		//
		cp.add(btn, BorderLayout.SOUTH);
		//

		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
	}

	public void showTable() {
		this.setSize(500, 650);
		this.setVisible(true);
	}

}
