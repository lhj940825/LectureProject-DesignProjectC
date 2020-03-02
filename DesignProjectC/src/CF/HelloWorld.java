package CF;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;

import javax.swing.JFrame;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

public class HelloWorld extends JFrame {
	public static final String MY_CUSTOM_VERTEX_STYLE = "MY_CUSTOM_VERTEX_STYLE";
	public static final String MY_CUSTOM_EDGE_STYLE = "MY_CUSTOM_EDGE_STYLE";
	private mxGraph graph;
	private Object parent;

	private static void setStyleSheet(mxGraph graph) {

		Hashtable<String, Object> style;
		mxStylesheet stylesheet = graph.getStylesheet();

		// base style
		Hashtable<String, Object> baseStyle = new Hashtable<String, Object>();
		baseStyle.put(mxConstants.STYLE_STROKECOLOR, "#FF0000");

		// custom vertex style
		style = new Hashtable<String, Object>(baseStyle);
		style.put(mxConstants.STYLE_FILLCOLOR, "#FFF000");
		stylesheet.putCellStyle(MY_CUSTOM_VERTEX_STYLE, style);

		style.put(mxConstants.STYLE_GRADIENTCOLOR, "#FFFFFF");
		stylesheet.putCellStyle(MY_CUSTOM_VERTEX_STYLE, style);

		style.put(mxConstants.STYLE_SHADOW, "1");
		stylesheet.putCellStyle(MY_CUSTOM_VERTEX_STYLE, style);

		// custom edge style
		style = new Hashtable<String, Object>(baseStyle);
		style.put(mxConstants.STYLE_STROKEWIDTH, 3);

		stylesheet.putCellStyle(MY_CUSTOM_EDGE_STYLE, style);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2707712944901661771L;

	public HelloWorld() {
		super("Hello, World!");

		graph = new mxGraph();
		parent = graph.getDefaultParent();
		
		graph.getModel().beginUpdate();
		setStyleSheet(graph);
		try {
			Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80,
					30);
			Object v2 = graph.insertVertex(parent, null, "World!", 240, 150,
					80, 30);
			Object v3 = graph.insertVertex(graph.getDefaultParent(), null,
					"hi", 20, 20, 80, 30, MY_CUSTOM_VERTEX_STYLE);
			Object v4 = graph.insertVertex(graph.getDefaultParent(), null,
					"bye", 20, 20, 80, 30, MY_CUSTOM_VERTEX_STYLE);
			
			graph.insertEdge(parent, null, "Edge", v1, v2,MY_CUSTOM_EDGE_STYLE);
			// graph rectangle
			graph.insertEdge(parent, null, 3.0, v1, v3, "endArrow=close");
		} finally {
			graph.getModel().endUpdate();
		}

		mxIGraphLayout layout = new mxCircleLayout(graph);
		layout.execute(graph.getDefaultParent());
		
		final mxGraphComponent graphComponent = new mxGraphComponent(graph);
		graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Object cell = graphComponent.getCellAt(e.getX(), e.getY());
				if (cell instanceof mxCell) {
					System.out.println(((mxCell) cell).getValue());
					Object[] arr = graph.getChildVertices(parent);
					mxRectangle t =graph.getCellBounds(cell);
					
					System.out.println(t.getX()+" "+t.getY()+" "+t.getWidth());
					System.out.println(((mxCell)cell).getGeometry());
					for (int i = 0; i < arr.length; i++) {
						if (((mxCell) arr[i]).getValue().equals(
								((mxCell) cell).getValue())) {
							System.out.println("ÀÌ°Å´ç");
						}
					}

				}
			}
		});
		getContentPane().add(graphComponent);

	}

	public static void main(String[] args) {
		HelloWorld frame = new HelloWorld();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 320);
		frame.setVisible(true);
	}

}