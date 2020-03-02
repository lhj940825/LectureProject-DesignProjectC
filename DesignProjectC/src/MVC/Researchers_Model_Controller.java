package MVC;

import java.sql.SQLException;
import java.util.ArrayList;

import DataStructure.AdjacencyList;
import GUI.*;
import Algorithms.*;

public class Researchers_Model_Controller {

	private Researchers_Model researchers;

	public Researchers_Model_Controller() {
		researchers = new Researchers_Model();
	}

	public int request_readData() {

		return 1;// true
	}

	// TODO modify ArrayList<String> to proper dataset
	public AdjacencyList get_Researchers_AdjacencyList(int minYear, int maxYear) throws SQLException {

		return researchers.get_researcher_AdjList(minYear, maxYear);
	}

	public AdjacencyList get_Researchers_AdjacencyList(int year) throws SQLException {

		return researchers.get_researcher_AdjList(year);
	}

	public ArrayList<String> get_Papers() {
		ArrayList<String> papers = new ArrayList<String>();
		return papers;
	}

	public ArrayList<String> get_Recommended_List(String researchesrsName) {
		ArrayList<String> recommendedList = new ArrayList<String>();
		return recommendedList;
	}

	public boolean get_LoginPermission(String id, String password) {
		return true;
	}
}
