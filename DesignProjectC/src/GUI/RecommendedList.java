package GUI;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import DB.DataBase;

import com.mxgraph.model.mxCell;

public class RecommendedList extends JFrame implements ListSelectionListener {
	private JLabel label = new JLabel();
	private JList list;
	private Border border = BorderFactory.createTitledBorder("선택사항");
	private JScrollPane scroll = new JScrollPane();
	private DefaultListModel listModel;

	public RecommendedList() {

	}

	public RecommendedList(ArrayList<String> lists) {
	
		
		listModel = new DefaultListModel();
		// list 셋팅
		list = new JList(listModel);
		for (String temp : lists) {
			listModel.addElement(temp);
		}
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(this); // 이벤트리스너 장착

	

		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				JList theList = (JList) mouseEvent.getSource();
				if (mouseEvent.getClickCount() == 2) {
					int index = theList.locationToIndex(mouseEvent.getPoint());
					if (index >= 0) {
						Object o = theList.getModel().getElementAt(index);

						try {
							DataBase db = new DataBase();
							ArrayList<String> papers = db.getPaperData(o.toString());
							paperTable table = new paperTable(papers);
							table.showTable();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});

		// 스크롤 셋팅
		scroll.setViewportView(list);
		scroll.setBorder(border); // 경계 설정
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 가로바정책

		// 기본컴포넌트 셋팅
		label.setText("상세 정보를 열람하고 싶은 저자를 더블클릭해주세요");

		// 프레임에 컴포넌트 장착
		add(label, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);

		// 기본 프래임 셋팅
		setTitle("추천된 저자 목록");
		setLocation(120, 120);
		setSize(320, 450);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == list) {
			String str = (String) list.getSelectedValue();
			label.setText(str + "을 선택 하셨습니다");

		}
	}

}
