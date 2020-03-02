package MVC;

import java.awt.EventQueue;

import GUI.*;
import Streaming.RealTimeStreamThread;
import Algorithms.*;
import DB.DataBase;
import DataStructure.*;
import DataStructure.AdjacencyList.vertex;

import javax.swing.JFrame;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dialog;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.TextField;
import java.awt.Panel;
import java.awt.Label;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mxgraph.model.mxCell;
import com.wcohen.ss.JaroWinkler;
import com.wcohen.ss.api.StringDistance;

public class Dblp_System {

	private JFrame mainGUI;
	private Researchers_Model_Controller rc;
	private Researchers_Graph rg;
	private Search_Algorithms sa;
	private Keyword_Extraction ke;
	private RealTimeStreamThread rtst;
	private int minYear = 1998;
	private int maxYear = 2006;
	private ArrayList<String> subscribeList;
	//
	//

	private AdjacencyList researchers_AdjacencyList;

	// Buttons
	private JButton btn_Request_Recommendation;
	private JButton btn_Show_TotalAuthorshipGraph;
	private JButton btn_Show_YearlyPapers;
	private JButton btn_Read_Data;
	private JTextField titleField;
	private JTextField yearField;
	private JTextField contentField;
	private JTextField searchField;
	private JTextField nameField;

	/**
	 * Create the application.
	 * 
	 * @throws SQLException
	 */
	public Dblp_System() throws SQLException {
		this.subscribeList = new ArrayList<String>();
		this.rc = new Researchers_Model_Controller();
		this.rg = new Researchers_Graph();

		this.rtst = new RealTimeStreamThread(maxYear);
		this.rtst.setCallBack(new RealTimeStreamThread.callBack() {

			@Override
			public void dataReadNotify(int currentYear) throws SQLException {
				// TODO Auto-generated method stub
				Dblp_System.this.maxYear = currentYear;
				readData(maxYear);
				createSubscribeDialog(maxYear);

			}

		});
		this.rtst.setTime(10);
		this.rtst.start();
		// edges = new ArrayList<String>();
		readData();
		this.sa = new Search_Algorithms(researchers_AdjacencyList);
		this.ke = new Keyword_Extraction(researchers_AdjacencyList);
	}

	// TODO �˻��� ���ĺ� ���
	public void createSubscribeDialog(int maxYear) throws SQLException {
		DataBase db = new DataBase();
		ArrayList<String> list = db.getCoauthorData(String.valueOf(maxYear));
		ArrayList<String> updated = new ArrayList<String>();
		for (int i = 0; i < this.subscribeList.size(); i++) {
			for (String temp : list) {
				if (temp.indexOf(subscribeList.get(i)) != -1) {
					updated.add(subscribeList.get(i));
					break;
				}
			}
		}
		if (updated.size() != 0) {
			final Dialog notify = new Dialog(this.mainGUI, "Subscribe Info Notify", true);
			notify.setSize(450, 120);
			notify.setLocation(50, 50);
			notify.setLayout(new FlowLayout());

			JLabel msg;
			if (updated.size() > 1)
				msg = new JLabel(updated.get(0) + "외에 " + (updated.size() - 1) + "명이 논문을 투고하였습니다.", JLabel.CENTER);
			else
				msg = new JLabel(updated.get(0) + "님이 논문을 투고하였습니다.", JLabel.CENTER);

			JButton ok = new JButton("OK");
			ok.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					notify.dispose();
				}
			});
			notify.add(msg);
			notify.add(ok);
			notify.setVisible(true);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {
		mainGUI = new JFrame();
		mainGUI.setBounds(100, 100, 609, 466); // 100, 100 = �ʱ� ���� ��ġ 450
												// width,
												// 300 height
		mainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainGUI.getContentPane().setLayout(null);

		btn_Request_Recommendation = new JButton("Request Recommend");
		btn_Request_Recommendation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Dblp_System.this.ke.SelectingResearchersMajors(String.valueOf(minYear), String.valueOf(maxYear));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// 이 밑부분은 확인 후 주석처리해줘!
				for (int i = 0; i < researchers_AdjacencyList.getTotalVertex().size(); i++) {
					String name = researchers_AdjacencyList.getTotalVertex().get(i).getResearcher().getname();
					ArrayList<String> arrli = researchers_AdjacencyList.getTotalVertex().get(i).getResearcher().getSelectingMajors();
					System.out.println("###" + name + "의 핵심 키워드");
					for (int j = 0; j < arrli.size(); j++) {
						System.out.println("* " + (j + 1) + ": " + arrli.get(j));
					}
				}
				RecommendResearcherFrame rrf = new RecommendResearcherFrame(Dblp_System.this.researchers_AdjacencyList, new ArrayList<String>());
				rrf.setCallBack(new RecommendResearcherFrame.callBack() {

					@Override
					public void recommendRequest(String Reserchers) throws SQLException {
						// TODO Auto-generated method stub
						Researcher_Recommendation rr = new Researcher_Recommendation(Dblp_System.this.researchers_AdjacencyList);
						ArrayList<String> recommendedResearchers = rr.recommend_Researchers(Reserchers);
						if (recommendedResearchers.size() == 0)
							System.out.println("해당 저자의 키워드가 없어 추천이 불가능합니다.");
						else {
							// create something
							RecommendedList recommendedList = new RecommendedList(recommendedResearchers);

						}

					}
				});
				rrf.showFrame();

			}

		});
		btn_Request_Recommendation.setBounds(33, 90, 173, 23);
		mainGUI.getContentPane().add(btn_Request_Recommendation);

		btn_Show_TotalAuthorshipGraph = new JButton("Show Total Authorship Graph");
		btn_Show_TotalAuthorshipGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				functionForTotalGraph_BTN();
			}
		});
		btn_Show_TotalAuthorshipGraph.setBounds(329, 57, 232, 23);
		mainGUI.getContentPane().add(btn_Show_TotalAuthorshipGraph);

		btn_Show_YearlyPapers = new JButton("Show YearlyPapers");
		btn_Show_YearlyPapers.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				yearlyPaperSearch yps = new yearlyPaperSearch(minYear, maxYear);
				yps.startSearch();
			}

		});
		btn_Show_YearlyPapers.setBounds(329, 249, 232, 23);
		mainGUI.getContentPane().add(btn_Show_YearlyPapers);

		JButton btn_SpecificAuthorshipGraph = new JButton("Show Specific Authorship Graph");
		btn_SpecificAuthorshipGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				functionForSpecificGraph_BTN();
			}
		});
		btn_SpecificAuthorshipGraph.setBounds(329, 110, 232, 23);
		mainGUI.getContentPane().add(btn_SpecificAuthorshipGraph);

		JButton btn_Top_K_Graph = new JButton("Show Top-K Graph");
		btn_Top_K_Graph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				functionForTopK_BTN();
			}
		});
		btn_Top_K_Graph.setBounds(329, 158, 232, 23);
		mainGUI.getContentPane().add(btn_Top_K_Graph);

		JButton btn_Show_SpecificTop_K_Graph = new JButton("Show Specific Top-K Graph");
		btn_Show_SpecificTop_K_Graph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				functionForSpecificTopK_BTN();
			}
		});
		btn_Show_SpecificTop_K_Graph.setBounds(329, 206, 232, 23);
		mainGUI.getContentPane().add(btn_Show_SpecificTop_K_Graph);

		JButton btnShowChain = new JButton("Show Chain");
		btnShowChain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				functionForChain_BTN();

			}
		});
		btnShowChain.setBounds(329, 294, 232, 23);
		mainGUI.getContentPane().add(btnShowChain);

		btn_Read_Data = new JButton("Read Data");
		btn_Read_Data.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					readData();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btn_Read_Data.setBounds(33, 44, 173, 23);
		mainGUI.getContentPane().add(btn_Read_Data);

		JButton btnSubscribe = new JButton("Subscribe");
		btnSubscribe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SubscribeFrame sf = new SubscribeFrame(researchers_AdjacencyList, subscribeList);
				/*
				 * sf.setResearchers(researchers_AdjacencyList);
				 * sf.setSubscribeList(subscribeList);
				 */
				sf.showFrame();
			}
		});
		btnSubscribe.setBounds(33, 143, 173, 23);
		mainGUI.getContentPane().add(btnSubscribe);

		JLabel lblNewLabel = new JLabel("             Search Data");
		lblNewLabel.setBounds(33, 227, 173, 15);
		mainGUI.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Title");
		lblNewLabel_1.setBounds(33, 253, 57, 15);
		mainGUI.getContentPane().add(lblNewLabel_1);

		titleField = new JTextField();
		titleField.setBounds(90, 250, 116, 21);
		mainGUI.getContentPane().add(titleField);
		titleField.setColumns(10);
		titleField.setText(new String(""));

		yearField = new JTextField();
		yearField.setBounds(90, 279, 116, 21);
		mainGUI.getContentPane().add(yearField);
		yearField.setColumns(10);
		yearField.setText(new String(""));

		JLabel lblNewLabel_2 = new JLabel("Year");
		lblNewLabel_2.setBounds(33, 282, 57, 15);
		mainGUI.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Content");
		lblNewLabel_3.setBounds(33, 307, 57, 15);
		mainGUI.getContentPane().add(lblNewLabel_3);

		contentField = new JTextField();
		contentField.setBounds(90, 304, 116, 21);
		mainGUI.getContentPane().add(contentField);
		contentField.setColumns(10);
		contentField.setText(new String(""));

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DataBase db;
				try {
					db = new DataBase();
					if (!titleField.getText().equals("")) {
						String searchTitle = titleField.getText();
						ArrayList<String> papers = db.getPaperDataByTitle(searchTitle, String.valueOf(Dblp_System.this.minYear), String.valueOf(Dblp_System.this.maxYear));
						if (papers.size() != 0) {
							paperTable table = new paperTable(papers);
							table.showTable();
						}
						titleField.setText(new String(""));
					} else if (!yearField.getText().equals("")) {
						String yearTitle = yearField.getText();
						if (Integer.parseInt(yearTitle) >= Dblp_System.this.minYear && Integer.parseInt(yearTitle) <= Dblp_System.this.maxYear) {
							ArrayList<String> papers = db.getPaperDataByYear(yearTitle);
							if (papers.size() != 0) {
								paperTable table = new paperTable(papers);
								table.showTable();
							}
						}
						yearField.setText(new String(""));
					} else if (!contentField.getText().equals("")) {
						String contentTitle = contentField.getText();
						ArrayList<String> papers = db.getPaperDataByContent(contentTitle, String.valueOf(Dblp_System.this.minYear), String.valueOf(Dblp_System.this.maxYear));
						if (papers.size() != 0) {
							paperTable table = new paperTable(papers);
							table.showTable();
						}
						contentField.setText(new String(""));
					} else {

					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSearch.setBounds(33, 332, 173, 23);
		mainGUI.getContentPane().add(btnSearch);

		JButton btnSearchCoauthor = new JButton("Search Coauthor");
		btnSearchCoauthor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CoauthorFrame cf = new CoauthorFrame(Dblp_System.this.researchers_AdjacencyList, new ArrayList<String>());
				cf.setCallBack(new CoauthorFrame.callBack() {

					@Override
					public void CoauthorListNotify(ArrayList<String> coauthorList) throws SQLException {
						// TODO Auto-generated method stub
						String temp = "";
						for (int i = 0; i < coauthorList.size() - 1; i++)
							temp += (coauthorList.get(i) + "&&");
						temp += coauthorList.get(coauthorList.size() - 1);
						DataBase db = new DataBase();
						ArrayList<String> papers = db.getPaperData(temp);
						paperTable table = new paperTable(papers);
						table.showTable();
					}

				});
				cf.showFrame();
			}
		});
		btnSearchCoauthor.setBounds(33, 194, 173, 23);
		mainGUI.getContentPane().add(btnSearchCoauthor);

		searchField = new JTextField();
		searchField.setBounds(330, 346, 116, 21);
		mainGUI.getContentPane().add(searchField);
		searchField.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("             알  수도 있는 저자 탐색");
		lblNewLabel_4.setBounds(329, 327, 232, 15);
		mainGUI.getContentPane().add(lblNewLabel_4);

		JButton btnSearch_1 = new JButton("Search");
		btnSearch_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String researcherName = searchField.getText();
				ArrayList<vertex> researcherList = new ArrayList<vertex>();
				ArrayList<vertex> vertexs = Dblp_System.this.researchers_AdjacencyList.getTotalVertex();
				vertex temp = null;
				for (int i = 0; i < vertexs.size(); i++) {
					if (vertexs.get(i).getResearcher().getname().equals(researcherName)) {
						temp = vertexs.get(i);
						break;
					}
				}

				if (temp == null)
					System.out.println("해당 사용자가 존재하지 않습니다.");
				else {
					for (int i = 0; i < temp.getEdges().size(); i++) {
						// System.out.println(temp.getEdges().get(i).getmainvertex().getResearcher().getname());
						if (!temp.getEdges().get(i).getmainvertex().getResearcher().getname().equals(researcherName)) {
							researcherList.add(temp.getEdges().get(i).getmainvertex());
						}
					}
					int size = researcherList.size();
					for (int i = 0; i < size; i++) {
						vertex tempVertex = researcherList.remove(0);
						for (int j = 0; j < tempVertex.getEdges().size(); j++) {
							researcherList.add(tempVertex.getEdges().get(j).getmainvertex());
						}
					}
					ArrayList<String> realList = new ArrayList<String>();
					realList.add(researcherList.get(0).getResearcher().getname());
					for (int i = 1; i < researcherList.size(); i++) {
						boolean check = true;
						for (int j = 0; j < realList.size(); j++) {
							if (researcherList.get(i).getResearcher().getname().equals(realList.get(j))) {
								check = false;
								break;
							}
						}
						if (check)
							realList.add(researcherList.get(i).getResearcher().getname());
					}

					for (int i = 0; i < realList.size(); i++) {
						realList.set(i, realList.get(0) + "||None");
					}

					rg = new Researchers_Graph();
					rg.setVertexs(realList);
					// System.out.println(researchersEdges.size());
					rg.setEdges(new ArrayList<String>()); // Edge���� �Ѱ��־����
					// rg.show_CoAuthorshipGraph();
					rg.show_CoAuthorshipGraphWithChain();

					// 출력부분
				}
			}
		});
		btnSearch_1.setBounds(464, 345, 97, 23);
		mainGUI.getContentPane().add(btnSearch_1);

		JLabel label = new JLabel("            비슷한 이름의 저자 탐색");
		label.setBounds(33, 376, 232, 15);
		mainGUI.getContentPane().add(label);

		nameField = new JTextField();
		nameField.setColumns(10);
		nameField.setBounds(33, 396, 116, 21);
		mainGUI.getContentPane().add(nameField);

		JButton button = new JButton("Search");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				JaroWinkler jaro = new JaroWinkler();
				StringDistance distanceChecker = jaro.getDistance();
				ArrayList<vertex> vertexs = Dblp_System.this.researchers_AdjacencyList.getTotalVertex();
				ArrayList<String> realList = new ArrayList<String>();
				for (int i = 0; i < vertexs.size(); i++) {
					System.out.println(vertexs.get(i).getResearcher().getname());
					String temp = vertexs.get(i).getResearcher().getname();
					double similarity = distanceChecker.score(name, temp);
					if(similarity>0.5){
						realList.add(temp+"||asdf");
					}
				}
				rg = new Researchers_Graph();
				rg.setVertexs(realList);
				// System.out.println(researchersEdges.size());
				rg.setEdges(new ArrayList<String>()); // Edge���� �Ѱ��־����
				// rg.show_CoAuthorshipGraph();
				rg.show_CoAuthorshipGraphWithChain();
				
			}
		});
		button.setBounds(158, 395, 97, 23);
		mainGUI.getContentPane().add(button);

		this.mainGUI.setVisible(true);
	}

	// public boolean login(){}

	public synchronized void readData() throws SQLException {
		Dblp_System.this.researchers_AdjacencyList = rc.get_Researchers_AdjacencyList(this.minYear, this.maxYear);
	}

	public synchronized void readData(int year) throws SQLException {
		Dblp_System.this.researchers_AdjacencyList = rc.get_Researchers_AdjacencyList(year);
	}

	public void readData_InRealTime() {

	}

	public void request_Recommand() {

	}

	public void request_Papers() {

	}

	public void request_Researchers() {

	}

	public void functionForTotalGraph_BTN() {
		/*
		 * ArrayList<Researcher> temp=
		 * getTopkNode(1,this.researchers_AdjacencyList.getTotalVertex());
		 * for(Researcher temp2:temp){ System.out.println(temp2.getname()); }
		 */
		if (researchers_AdjacencyList != null) {
			ArrayList<AdjacencyList.vertex> vertexs = Dblp_System.this.researchers_AdjacencyList.getTotalVertex();
			ArrayList<String> researchersNames = new ArrayList<String>();
			ArrayList<String> researchersEdges = new ArrayList<String>();
			for (int i = 0; i < vertexs.size(); i++) {
				researchersNames.add(vertexs.get(i).getResearcher().getname() + "||" + "None");
				ArrayList<AdjacencyList.edge> temp = vertexs.get(i).getEdges();
				for (int j = 0; j < temp.size(); j++) {
					String line = temp.get(j).getmainvertex().getResearcher().getname() + "||" + temp.get(j).getsubvertex().getResearcher().getname() + "||" + temp.get(j).getWeight();
					if (researchersEdges.size() == 0) {
						researchersEdges.add(line);
					} else {
						boolean edgeExist = false;
						for (int k = 0; k < researchersEdges.size(); k++) {
							String[] lineArr = researchersEdges.get(k).split("\\|\\|");
							if ((lineArr[0].equals(temp.get(j).getmainvertex().getResearcher().getname()) && lineArr[1].equals(temp.get(j).getsubvertex().getResearcher().getname()))
									|| (lineArr[0].equals(temp.get(j).getsubvertex().getResearcher().getname()) && lineArr[1].equals(temp.get(j).getmainvertex().getResearcher().getname()))) {
								edgeExist = true;
								break;
							}
						}
						if (!edgeExist)
							researchersEdges.add(line);
					}
				}

			}//
				//
			//
			/*
			 * rg2 = new Researchers_Graph2(); rg2.setVertexs(researchersNames);
			 * rg2.setEdges(researchersEdges); // Edge���� �Ѱ��־����
			 * rg2.show_CoAuthorshipGraph();
			 */

			rg = new Researchers_Graph();
			rg.setVertexs(researchersNames);
			// System.out.println(researchersEdges.size());
			rg.setEdges(researchersEdges); // Edge���� �Ѱ��־����
			// rg.show_CoAuthorshipGraph();
			rg.show_CoAuthorshipGraphWithChain();
		}

	}

	public void functionForSpecificGraph_BTN() {
		String input = JOptionPane.showInputDialog("�������� �׷����� ������� ������ �̸��� �Է����ּ���");
		if (input == null) {
			// System.out.println("�̸��� �Է����ּ���");
			System.exit(0);
		} else {
			ArrayList<AdjacencyList.vertex> vertexs = Dblp_System.this.researchers_AdjacencyList.getTotalVertex();
			ArrayList<String> researchersNames = new ArrayList<String>();
			ArrayList<String> researchersEdges = new ArrayList<String>();

			AdjacencyList.vertex target = null;
			for (int i = 0; i < vertexs.size(); i++) {
				if (vertexs.get(i).getResearcher().getname().equals(input)) {
					target = vertexs.get(i);
					break;
				}
			}
			researchersNames.add(target.getResearcher().getname() + "||" + "Color");
			for (int i = 0; i < target.getResearcher().getCoauthorlist().size(); i++) {
				ArrayList<Researcher> temp = target.getResearcher().getCoauthorlist();
				researchersNames.add(temp.get(i).getname() + "||" + "None");
				ArrayList<AdjacencyList.edge> tempEdges = target.getEdges();
				for (int j = 0; j < tempEdges.size(); j++)
					researchersEdges.add(tempEdges.get(j).getmainvertex().getResearcher().getname() + "||" + tempEdges.get(j).getsubvertex().getResearcher().getname() + "||"
							+ tempEdges.get(j).getWeight());
			}
			// TODO �ߺ����� ���� �ڵ� �ʿ�
			rg = new Researchers_Graph();
			rg.setVertexs(researchersNames);
			rg.setEdges(researchersEdges); // Edge���� �Ѱ��־����

			// rg.show_CoAuthorshipGraph();
			rg.show_CoAuthorshipGraphWithChain();
		}
	}

	public void functionForTopK_BTN() {
		String kSize = JOptionPane.showInputDialog("K�� ����� �Է����ּ���");
		if (kSize == null) {
			System.exit(0);
		} else {
			ArrayList<AdjacencyList.vertex> vertexs = Dblp_System.this.researchers_AdjacencyList.getTotalVertex();
			ArrayList<String> researchersNames = new ArrayList<String>();
			ArrayList<String> researchersEdges = new ArrayList<String>();

			if (Integer.parseInt(kSize) > vertexs.size()) {
				System.exit(0);
			}

			ArrayList<Researcher> dummyList = getTopkNode(Integer.parseInt(kSize), vertexs);

			for (int i = 0; i < vertexs.size(); i++) {
				boolean isInTopK = false;
				for (int dum = 0; dum < dummyList.size(); dum++) {
					if (dummyList.get(dum).getname().equals(vertexs.get(i).getResearcher().getname())) {
						isInTopK = true;
						break;
					}
				}
				if (isInTopK)
					researchersNames.add(vertexs.get(i).getResearcher().getname() + "||" + "color");
				else
					researchersNames.add(vertexs.get(i).getResearcher().getname() + "||" + "None");

				ArrayList<AdjacencyList.edge> temp = vertexs.get(i).getEdges();
				for (int j = 0; j < temp.size(); j++) {
					String line = temp.get(j).getmainvertex().getResearcher().getname() + "||" + temp.get(j).getsubvertex().getResearcher().getname() + "||" + temp.get(j).getWeight();
					if (researchersEdges.size() == 0) {
						researchersEdges.add(line);
					} else {
						boolean edgeExist = false;
						for (int k = 0; k < researchersEdges.size(); k++) {
							String[] lineArr = researchersEdges.get(k).split("\\|\\|");
							if ((lineArr[0].equals(temp.get(j).getmainvertex().getResearcher().getname()) && lineArr[1].equals(temp.get(j).getsubvertex().getResearcher().getname()))
									|| (lineArr[0].equals(temp.get(j).getsubvertex().getResearcher().getname()) && lineArr[1].equals(temp.get(j).getmainvertex().getResearcher().getname()))) {
								edgeExist = true;
								break;
							}
						}
						if (!edgeExist)
							researchersEdges.add(line);
					}
				}

			}//
				//
			rg = new Researchers_Graph();
			rg.setVertexs(researchersNames);
			rg.setEdges(researchersEdges); // Edge���� �Ѱ��־����
			// rg.show_CoAuthorshipGraph();
			rg.show_CoAuthorshipGraphWithChain();
		}
	}

	public void functionForSpecificTopK_BTN() {
		String input = JOptionPane.showInputDialog("�������� �׷����� ������� ������ �̸��� K�� ����� �Է����ּ���(�̸� K������ ��, ||�� ����)");
		if (input == null) {
			System.exit(0);
		} else {
			String[] inputData = input.split("\\|\\|");

			ArrayList<AdjacencyList.vertex> vertexs = Dblp_System.this.researchers_AdjacencyList.getTotalVertex();
			ArrayList<String> researchersNames = new ArrayList<String>();
			ArrayList<String> researchersEdges = new ArrayList<String>();

			AdjacencyList.vertex target = null;
			for (int i = 0; i < vertexs.size(); i++) {
				if (vertexs.get(i).getResearcher().getname().equals(inputData[0])) {
					target = vertexs.get(i);
					break;
				}
			}
			// 1�� depthŽ���϶� ����ó��

			if (Integer.parseInt(inputData[1]) > (target.getResearcher().getCoauthorlist().size() + 1)) {
				System.exit(0);
			}
			// /////////////////////////////////////////////////////////////////////////

			// getTopKNode�� �Ķ���ͷ� vertex����Ʈ�� �޾ƾ� �ϱ� ������ ����
			// targetVertexs��� ����
			// ����� ���ڷ� ����
			ArrayList<AdjacencyList.vertex> targetsVertexs = new ArrayList<AdjacencyList.vertex>();
			targetsVertexs.add(target);
			for (int i = 0; i < vertexs.size(); i++) {
				for (int j = 0; j < target.getResearcher().getCoauthorlist().size(); j++) {
					if (vertexs.get(i).getResearcher().getname().equals(target.getResearcher().getCoauthorlist().get(j).getname())) {
						// System.out.println(vertexs.get(i).getResearcher().getname()
						// + "�� "+ );
						targetsVertexs.add(vertexs.get(i));
						break;
					}
				}
			}
			// System.out.println("������:" + targetsVertexs.size());
			// /////////////////////////////////////////////////////////////////////////

			// �÷����� �ʿ��� ��带 ����

			ArrayList<Researcher> dummyList = getTopkNode(Integer.parseInt(inputData[1]), targetsVertexs);

			boolean isInTopK = false;
			for (int dum = 0; dum < dummyList.size(); dum++) {
				if (dummyList.get(dum).getname().equals(target.getResearcher().getname())) {
					isInTopK = true;
					break;
				}
			}
			if (isInTopK)
				researchersNames.add(target.getResearcher().getname() + "||" + "Color");
			else
				researchersNames.add(target.getResearcher().getname() + "||" + "None");

			isInTopK = false;

			// /////////////////////////////////////////////////////////////////////////

			ArrayList<Researcher> temp = target.getResearcher().getCoauthorlist(); // ����
																					// ����
																					// ���̷���
																					// ����
			for (int i = 0; i < temp.size(); i++) {
				isInTopK = false;

				for (int dum = 0; dum < dummyList.size(); dum++) {
					if (dummyList.get(dum).getname().equals(temp.get(i).getname())) {
						isInTopK = true;
						break;
					}
				}
				if (isInTopK)
					researchersNames.add(temp.get(i).getname() + "||" + "Color");
				else
					researchersNames.add(temp.get(i).getname() + "||" + "None");

				ArrayList<AdjacencyList.edge> tempEdges = target.getEdges();
				for (int j = 0; j < tempEdges.size(); j++)
					researchersEdges.add(tempEdges.get(j).getmainvertex().getResearcher().getname() + "||" + tempEdges.get(j).getsubvertex().getResearcher().getname() + "||"
							+ tempEdges.get(j).getWeight());
			}

			// TODO �ߺ����� ���� �ڵ� �ʿ�
			rg = new Researchers_Graph();
			rg.setVertexs(researchersNames);
			rg.setEdges(researchersEdges); // Edge���� �Ѱ��־����
			// rg.show_CoAuthorshipGraph();
			rg.show_CoAuthorshipGraphWithChain();
		}
	}

	public void functionForChain_BTN() {
		String input = JOptionPane.showInputDialog("������ �� �θ��� ������ �̸��� �Է����ּ���");
		if (input == null) {
			System.exit(0);
		} else {
			String[] inputData = input.split("\\|\\|");
			if (researchers_AdjacencyList != null) {

				ArrayList<AdjacencyList.vertex> vertexs = Dblp_System.this.researchers_AdjacencyList.getTotalVertex();

				ArrayList<String> namesInChain = sa.find_Chain_BY_BFS(vertexs, inputData[0], inputData[1]);
				if (namesInChain != null) {
				} else {
					System.exit(0);
				}

				ArrayList<String> researchersNames = new ArrayList<String>();
				ArrayList<String> researchersEdges = new ArrayList<String>();
				for (int i = 0; i < vertexs.size(); i++) {
					boolean isInChain = false;
					//
					for (String nameInChain : namesInChain) {
						if (nameInChain.equals(vertexs.get(i).getResearcher().getname())) {
							isInChain = true;
							break;
						}
					}
					if (isInChain)
						researchersNames.add(vertexs.get(i).getResearcher().getname() + "||" + "Color");
					else
						researchersNames.add(vertexs.get(i).getResearcher().getname() + "||" + "None");
					//

					ArrayList<AdjacencyList.edge> temp = vertexs.get(i).getEdges();
					for (int j = 0; j < temp.size(); j++) {

						String line = temp.get(j).getmainvertex().getResearcher().getname() + "||" + temp.get(j).getsubvertex().getResearcher().getname() + "||" + temp.get(j).getWeight();
						//
						for (int k = 0; k < namesInChain.size() - 1; k++) {
							if ((line.indexOf(namesInChain.get(k)) != -1) && (line.indexOf(namesInChain.get(k + 1)) != -1)) {
								line = line + "||Color";
								break;
							}
						}

						//
						if (researchersEdges.size() == 0) {
							researchersEdges.add(line);
						} else {
							boolean edgeExist = false;
							for (int k = 0; k < researchersEdges.size(); k++) {
								String[] lineArr = researchersEdges.get(k).split("\\|\\|");
								if ((lineArr[0].equals(temp.get(j).getmainvertex().getResearcher().getname()) && lineArr[1].equals(temp.get(j).getsubvertex().getResearcher().getname()))
										|| (lineArr[0].equals(temp.get(j).getsubvertex().getResearcher().getname()) && lineArr[1].equals(temp.get(j).getmainvertex().getResearcher().getname()))) {
									edgeExist = true;
									break;
								}
							}
							if (!edgeExist)
								researchersEdges.add(line);
						}
					}

				}//

				rg = new Researchers_Graph(); // rg.setResearchers(researchers);
				rg.setVertexs(researchersNames);
				rg.setEdges(researchersEdges); // Edge���� �Ѱ��־����
				rg.show_CoAuthorshipGraphWithChain();
			}
		}
	}

	public ArrayList<Researcher> getTopkNode(int size, ArrayList<AdjacencyList.vertex> vertexlist) // size��
	{
		ArrayList<Researcher> dummylist = new ArrayList<Researcher>();
		int adlistsize = vertexlist.size();
		Researcher[] researcherAry = new Researcher[adlistsize];
		for (int i = 0; i < adlistsize; i++) {
			researcherAry[i] = vertexlist.get(i).getResearcher();
			// System.out.println("data" + "[" + i + "]:" +
			// vertexlist.get(i).getResearcher().getname());
		}
		PartialSort ps = new PartialSort(researcherAry);
		for (int i = 0; i < size; i++) {
			dummylist.add(researcherAry[i]);
		}
		return dummylist;
	}
}
