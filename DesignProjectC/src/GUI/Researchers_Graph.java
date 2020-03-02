package GUI;

import DB.*;
import MVC.*;
import Algorithms.*;
import CF.AutoGraph.MyEdge;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableDirectedWeightedGraph;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.handler.mxCellTracker;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;

import java.awt.GridLayout;

public class Researchers_Graph {

	private JFrame ResearchersFrame;
	private JPanel ResearchersPanel;
	private JPanel GraphPanel;
	private JButton btnDispose;

	private mxGraphComponent graphComponent;
	private mxGraph graph;
	private Object parent;
	// ///////////////////////////////ó�� ������ ���õǾ� ��� �����ϰ� ���� ����

	// TODO should modify
	private ArrayList<String> Vertexs;
	private ArrayList<String> Edges;
	// ///////////////////////////////�Ʒ��� �ӽú���
	private Object[] vertexsObject;
	private Object[] edgesObject;

	public static final String MY_CUSTOM_VERTEX_STYLE = "MY_CUSTOM_VERTEX_STYLE";
	public static final String MY_CUSTOM_VERTEX_STYLE2 = "MY_CUSTOM_VERTEX_STYLE2";
	public static final String MY_CUSTOM_EDGE_STYLE = "MY_CUSTOM_EDGE_STYLE";
	public static final String MY_CUSTOM_EDGE_STYLE2 = "MY_CUSTOM_EDGE_STYLE2";
	public static final String MY_CUSTOM_EDGE_STYLE3 = "MY_CUSTOM_EDGE_STYLE3";
	public static final String MY_CUSTOM_EDGE_COROLING = "MY_CUSTOM_EDGE_COROLING";

	/**
	 * Launch the application.
	 */
	public void show_CoAuthorshipGraph() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					setGraphPanel();
					ResearchersFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void show_CoAuthorshipGraphWithChain() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					setChainGraphPanel();
					ResearchersFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Researchers_Graph() {
		initialize();
		this.ResearchersFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		ResearchersPanel = new JPanel();
		ResearchersPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.ResearchersFrame.setContentPane(ResearchersPanel);
		ResearchersPanel.setLayout(null);

		GraphPanel = new JPanel();
		GraphPanel.setBounds(12, 10, 1425, 825);
		ResearchersPanel.add(GraphPanel);
		GraphPanel.setLayout(new GridLayout(1, 0, 0, 0));

		btnDispose.setBounds(1449, 10, 123, 53);
		ResearchersPanel.add(btnDispose);

	}

	public void setGraphPanel() {
		buildGraph();
		// mxIGraphLayout layout = new mxFastOrganicLayout(graphAdapter);
		mxIGraphLayout layout = new mxCircleLayout(graph);

		this.vertexsObject = graph.getChildVertices(parent);
		layout.execute(graph.getDefaultParent());
		graphComponent = new mxGraphComponent(graph);
		mxCellTracker trackColor = new mxCellTracker(graphComponent, Color.CYAN);
		this.GraphPanel.add(graphComponent);

		graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {

			@Override
			// TODO
			public void mousePressed(MouseEvent e) {
				Object cell = graphComponent.getCellAt(e.getX(), e.getY());
				if (cell instanceof mxCell) {
					for (int i = 0; i < vertexsObject.length; i++) {
						if (((mxCell) vertexsObject[i]).getId().equals(((mxCell) cell).getId())) {
							try {
								DataBase db = new DataBase();
								ArrayList<String> papers = db.getPaperData(((mxCell) vertexsObject[i]).getId());
								paperTable table = new paperTable(papers);
								table.showTable();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							break;
						}
					}
				}
				/*
				 * Object cell = graphComponent.getCellAt(e.getX(), e.getY());
				 * Object[] temp = graph.getEdgesBetween(vertexsObject[0],
				 * vertexsObject[1]); if (cell instanceof mxCell) { for (int i =
				 * 0; i < vertexsObject.length; i++) { if (((mxCell)
				 * vertexsObject[i]).getId().equals(((mxCell) cell).getId())) {
				 * System.out.println("����");
				 * 
				 * graph.removeCells(new Object[] { ((mxCell) cell) });
				 * graph.insertVertex(parent, "x1", "v1", ((mxCell)
				 * cell).getGeometry().getX(), ((mxCell)
				 * cell).getGeometry().getY(), 80, 30); vertexsObject =
				 * graph.getChildVertices(parent); break; } } }
				 * System.out.println(vertexsObject[0] + " " +
				 * vertexsObject[2]); graph.insertEdge(parent, "x3", "e1",
				 * vertexsObject[0], vertexsObject[2], "endArrow=close");
				 */

			}
		});

		graphComponent.getGraphControl().addMouseWheelListener(new MouseAdapter() {
			public void mouseWheelMoved(MouseWheelEvent event) {
				if (event.getWheelRotation() < 0)
					graphComponent.zoomIn();
				else
					graphComponent.zoomOut();
			}

		});

	}

	public void setChainGraphPanel() {
		buildChainGraph();
		// mxIGraphLayout layout = new mxFastOrganicLayout(graphAdapter);
		mxIGraphLayout layout = new mxCircleLayout(graph);

		this.vertexsObject = graph.getChildVertices(parent);
		layout.execute(graph.getDefaultParent());
		graphComponent = new mxGraphComponent(graph);
		mxCellTracker trackColor = new mxCellTracker(graphComponent, Color.CYAN);
		this.GraphPanel.add(graphComponent);

		graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {

			@Override
			// TODO
			public void mousePressed(MouseEvent e) {
				Object cell = graphComponent.getCellAt(e.getX(), e.getY());
				if (cell instanceof mxCell) {
					for (int i = 0; i < vertexsObject.length; i++) {
						if (((mxCell) vertexsObject[i]).getId().equals(((mxCell) cell).getId())) {
							try {
								DataBase db = new DataBase();
								ArrayList<String> papers = db.getPaperData(((mxCell) vertexsObject[i]).getId());
								paperTable table = new paperTable(papers);
								table.showTable();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							break;
						}
					}
				}

			}
		});

		/*
		 * graphComponent.getGraphControl().addMouseListener(new MouseAdapter()
		 * {
		 * 
		 * @Override // TODO mxCell�� vertex, edge�� Ž�� ��, �ٽ� �׷����� �׸��� ����ϴ� �ڵ�
		 * public void mousePressed(MouseEvent e) {
		 * 
		 * Object cell = graphComponent.getCellAt(e.getX(), e.getY()); Object[]
		 * temp = graph.getEdgesBetween(vertexsObject[0], vertexsObject[1]); if
		 * (cell instanceof mxCell) { for (int i = 0; i < vertexsObject.length;
		 * i++) { if (((mxCell) vertexsObject[i]).getId().equals(((mxCell)
		 * cell).getId())) { System.out.println("����");
		 * 
		 * graph.removeCells(new Object[] { ((mxCell) cell) });
		 * graph.insertVertex(parent, "x1", "v1", ((mxCell)
		 * cell).getGeometry().getX(), ((mxCell) cell).getGeometry().getY(), 80,
		 * 30); vertexsObject = graph.getChildVertices(parent); break; } } }
		 * System.out.println(vertexsObject[0] + " " + vertexsObject[2]);
		 * graph.insertEdge(parent, "x3", "e1", vertexsObject[0],
		 * vertexsObject[2], "endArrow=close"); } });
		 */

		graphComponent.getGraphControl().addMouseWheelListener(new MouseAdapter() {
			public void mouseWheelMoved(MouseWheelEvent event) {
				if (event.getWheelRotation() < 0)
					graphComponent.zoomIn();
				else
					graphComponent.zoomOut();
			}

		});

	}

	private static void setStyleSheet(mxGraph graph) {

		Hashtable<String, Object> style;
		mxStylesheet stylesheet = graph.getStylesheet();

		// base style
		Hashtable<String, Object> baseStyle = new Hashtable<String, Object>();
		baseStyle.put(mxConstants.STYLE_STROKECOLOR, "#FF0000");

		// custom vertex style
		style = new Hashtable<String, Object>(baseStyle);
		style.put(mxConstants.STYLE_FILLCOLOR, "#FFF8DC");
		stylesheet.putCellStyle(MY_CUSTOM_VERTEX_STYLE, style);

		style.put(mxConstants.STYLE_GRADIENTCOLOR, "#FFFFFF");
		stylesheet.putCellStyle(MY_CUSTOM_VERTEX_STYLE, style);

		style.put(mxConstants.STYLE_SHADOW, "1");
		stylesheet.putCellStyle(MY_CUSTOM_VERTEX_STYLE, style);

		// custom vertex style2
		style = new Hashtable<String, Object>(baseStyle);
		style.put(mxConstants.STYLE_FILLCOLOR, "#FFFF00");
		stylesheet.putCellStyle(MY_CUSTOM_VERTEX_STYLE2, style);

		style.put(mxConstants.STYLE_GRADIENTCOLOR, "#FFFFFF");
		stylesheet.putCellStyle(MY_CUSTOM_VERTEX_STYLE2, style);

		style.put(mxConstants.STYLE_SHADOW, "1");
		stylesheet.putCellStyle(MY_CUSTOM_VERTEX_STYLE2, style);

		// custom edge style
		style = new Hashtable<String, Object>(baseStyle);

		style.put(mxConstants.STYLE_STROKEWIDTH, 1);
		stylesheet.putCellStyle(MY_CUSTOM_EDGE_STYLE, style);

		style.put(mxConstants.STYLE_ENDARROW, "close");
		stylesheet.putCellStyle(MY_CUSTOM_EDGE_STYLE, style);

		// custom edge style2
		style = new Hashtable<String, Object>(baseStyle);

		style.put(mxConstants.STYLE_STROKEWIDTH, 3);
		stylesheet.putCellStyle(MY_CUSTOM_EDGE_STYLE2, style);

		style.put(mxConstants.STYLE_ENDARROW, "close");
		stylesheet.putCellStyle(MY_CUSTOM_EDGE_STYLE2, style);

		// custom edge style3
		style = new Hashtable<String, Object>(baseStyle);

		style.put(mxConstants.STYLE_STROKEWIDTH, 5);
		stylesheet.putCellStyle(MY_CUSTOM_EDGE_STYLE3, style);

		style.put(mxConstants.STYLE_ENDARROW, "close");
		stylesheet.putCellStyle(MY_CUSTOM_EDGE_STYLE3, style);

		// custom edge style2
		style = new Hashtable<String, Object>(baseStyle);

		style.put(mxConstants.STYLE_STROKEWIDTH, 5);
		stylesheet.putCellStyle(MY_CUSTOM_EDGE_COROLING, style);

		style.put(mxConstants.STYLE_STROKECOLOR, "#006400");
		stylesheet.putCellStyle(MY_CUSTOM_EDGE_COROLING, style);

		style.put(mxConstants.STYLE_ENDARROW, "close");
		stylesheet.putCellStyle(MY_CUSTOM_EDGE_COROLING, style);

	}

	public void buildGraph() {
		this.graph = new mxGraph();
		this.parent = graph.getDefaultParent();
		graph.getModel().beginUpdate();
		setStyleSheet(graph);
		try {
			/*
			 * this.graph.insertVertex(parent, "x1", "v1", 20, 20, 80, 30,
			 * MY_CUSTOM_VERTEX_STYLE); this.graph.insertVertex(parent, "x2",
			 * "v2", 20, 20, 80, 30, MY_CUSTOM_VERTEX_STYLE);
			 * this.graph.insertVertex(parent, "x3", "v3", 20, 20, 80, 30,
			 * MY_CUSTOM_VERTEX_STYLE); this.vertexs =
			 * graph.getChildCells(parent); for (int i = 0; i < vertexs.length;
			 * i++) { if (((mxCell) vertexs[i]).getId().equals("x1")) {
			 * this.graph.insertEdge(parent, "x3", "e1", vertexs[0], vertexs[1],
			 * "endArrow=close"); }
			 * 
			 * }
			 */
			for (int i = 0; i < this.Vertexs.size(); i++) {
				String[] dataArr = this.Vertexs.get(i).split("\\|\\|");
				if (dataArr[1].equals("None"))
					this.graph.insertVertex(parent, dataArr[0], dataArr[0], 20, 20, 80, 30, MY_CUSTOM_VERTEX_STYLE);
				else {
					this.graph.insertVertex(parent, dataArr[0], dataArr[0], 20, 20, 80, 30, MY_CUSTOM_VERTEX_STYLE2);
				}
			}
			this.vertexsObject = graph.getChildVertices(parent);
			/*
			 * ////////////////////////// String t1
			 * =((mxCell)vertexsObject[1]).getId(); String t2 =
			 * ((mxCell)vertexsObject[1]).getStyle(); String t3 = (String)
			 * ((mxCell)vertexsObject[1]).getValue(); graph.moveCells(new
			 * Object[]{vertexsObject[1]}, 120, 120);
			 * System.out.println(t1+" "+t2+" "+t3); ////////////////////////
			 */
			this.vertexsObject = graph.getChildVertices(parent);
			for (int i = 0; i < this.Edges.size(); i++) {
				String[] dataArr = this.Edges.get(i).split("\\|\\|");
				for (int j = 0; j < vertexsObject.length; j++) {
					Object source = null, target = null;

					for (int k = 0; k < vertexsObject.length; k++) {
						if (((mxCell) vertexsObject[k]).getId().equals(dataArr[0])) {
							source = vertexsObject[k];
							break;
						}

					}

					for (int k = 0; k < vertexsObject.length; k++) {
						if (((mxCell) vertexsObject[k]).getId().equals(dataArr[1])) {
							target = vertexsObject[k];
							break;
						}
					}
					//
					// this.graph.insertEdge(parent, dataArr[0] + "||" +
					// dataArr[1], dataArr[2], source, target,
					// "endArrow=close");
					//
					this.graph.insertEdge(parent, dataArr[0] + "||" + dataArr[1], dataArr[2], source, target, this.MY_CUSTOM_EDGE_STYLE);

				}

			}

			this.edgesObject = graph.getChildEdges(parent);

		} finally {
			//

			//
			graph.getModel().endUpdate();
		}

	}

	public void buildChainGraph() {
		this.graph = new mxGraph();
		this.parent = graph.getDefaultParent();
		graph.getModel().beginUpdate();
		setStyleSheet(graph);
		try {
			/*
			 * this.graph.insertVertex(parent, "x1", "v1", 20, 20, 80, 30,
			 * MY_CUSTOM_VERTEX_STYLE); this.graph.insertVertex(parent, "x2",
			 * "v2", 20, 20, 80, 30, MY_CUSTOM_VERTEX_STYLE);
			 * this.graph.insertVertex(parent, "x3", "v3", 20, 20, 80, 30,
			 * MY_CUSTOM_VERTEX_STYLE); this.vertexs =
			 * graph.getChildCells(parent); for (int i = 0; i < vertexs.length;
			 * i++) { if (((mxCell) vertexs[i]).getId().equals("x1")) {
			 * this.graph.insertEdge(parent, "x3", "e1", vertexs[0], vertexs[1],
			 * "endArrow=close"); }
			 * 
			 * }
			 */
			for (int i = 0; i < this.Vertexs.size(); i++) {
				String[] dataArr = this.Vertexs.get(i).split("\\|\\|");
				if (dataArr[1].equals("None"))
					this.graph.insertVertex(parent, dataArr[0], dataArr[0], 20, 20, 80, 30, MY_CUSTOM_VERTEX_STYLE);
				else {
					this.graph.insertVertex(parent, dataArr[0], dataArr[0], 20, 20, 80, 30, MY_CUSTOM_VERTEX_STYLE2);
				}
			}
			this.vertexsObject = graph.getChildVertices(parent);
			/*
			 * ////////////////////////// String t1
			 * =((mxCell)vertexsObject[1]).getId(); String t2 =
			 * ((mxCell)vertexsObject[1]).getStyle(); String t3 = (String)
			 * ((mxCell)vertexsObject[1]).getValue(); graph.moveCells(new
			 * Object[]{vertexsObject[1]}, 120, 120);
			 * System.out.println(t1+" "+t2+" "+t3); ////////////////////////
			 */
			this.vertexsObject = graph.getChildVertices(parent);
			for (int i = 0; i < this.Edges.size(); i++) {
				String[] dataArr = this.Edges.get(i).split("\\|\\|");
				Object source = null, target = null;
				for (int j = 0; j < vertexsObject.length; j++) {
					source = null;
					target = null;

					for (int k = 0; k < vertexsObject.length; k++) {
						if (((mxCell) vertexsObject[k]).getId().equals(dataArr[0])) {
							source = vertexsObject[k];
							break;
						}

					}

					for (int k = 0; k < vertexsObject.length; k++) {
						if (((mxCell) vertexsObject[k]).getId().equals(dataArr[1])) {
							target = vertexsObject[k];
							break;
						}
					}
					//
					// this.graph.insertEdge(parent, dataArr[0] + "||" +
					// dataArr[1], dataArr[2], source, target,
					// "endArrow=close");
					//
				}
				if (dataArr.length == 4) {
					this.graph.insertEdge(parent, dataArr[0] + "||" + dataArr[1], dataArr[2], source, target, this.MY_CUSTOM_EDGE_COROLING);
				} else {
					if (Integer.parseInt(dataArr[2]) <= 2)
						this.graph.insertEdge(parent, dataArr[0] + "||" + dataArr[1], dataArr[2], source, target, this.MY_CUSTOM_EDGE_STYLE);
					else if (Integer.parseInt(dataArr[2]) > 2 && Integer.parseInt(dataArr[2]) <= 4) {
						this.graph.insertEdge(parent, dataArr[0] + "||" + dataArr[1], dataArr[2], source, target, this.MY_CUSTOM_EDGE_STYLE2);
					} else {
						this.graph.insertEdge(parent, dataArr[0] + "||" + dataArr[1], dataArr[2], source, target, this.MY_CUSTOM_EDGE_STYLE3);
					}

				}
			}
			this.edgesObject = graph.getChildEdges(parent);

		} finally {
			graph.getModel().endUpdate();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		ResearchersFrame = new JFrame();
		ResearchersFrame.setBounds(100, 100, 1600, 900);
		ResearchersFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		btnDispose = new JButton("Dispose");
		btnDispose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ResearchersFrame.dispose();
			}
		});
	}

	public void setVertexs(ArrayList<String> vertexs) {
		this.Vertexs = vertexs;
		System.out.println("vertex set��");
	}

	public void setEdges(ArrayList<String> Edges) {
		this.Edges = Edges;
		System.out.println("insert set��");
	}
}
