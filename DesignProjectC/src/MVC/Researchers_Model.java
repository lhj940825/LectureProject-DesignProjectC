package MVC;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;

import GUI.*;
import Algorithms.*;
import DB.DataBase;
import DataStructure.*;

public class Researchers_Model {
	private FileInputStream researcherInput;
	private FileInputStream paperInput;
	Researcher_Recommendation rr;

	// TODO modify all ArrayList<String> to proper data set except papers
	private AdjacencyList researcher_AdjList = new AdjacencyList();
	private ArrayList<String> papers;

	public Researchers_Model() {
		rr = new Researcher_Recommendation();

		papers = new ArrayList<String>();//

	}

	public void readResearchers(int minYear, int maxYear) throws SQLException {
		// TODO researcherInput initialize

		DataBase db = new DataBase();

		ArrayList<String> data = db.getCoauthorData(String.valueOf(minYear), String.valueOf(maxYear));
		System.out.println("max" + maxYear);

		// db의 researcher이름들로 AddVertex하는 과정
		for (int i = 0; i < data.size(); i++) {
			String[] arr = data.get(i).split("&&");
			if (arr.length == 1) {
				Researcher r = new Researcher();
				r.setname(arr[0]);
				researcher_AdjList.AddSoloVertex(r);
				continue;
			}
			for (int k = 0; k < arr.length; k++) {
				for (int l = k + 1; l < arr.length; l++) {
					Researcher r1 = new Researcher();
					Researcher r2 = new Researcher();
					r1.setname(arr[k]);
					r2.setname(arr[l]);
					//System.out.println(arr[k] + "   " + arr[l]);
					researcher_AdjList.AddVertex(r1, r2);
				}
			}
			// 이제 연구횟수는 여기서 늘려준다
			for (int k = 0; k < arr.length; k++) {
				researcher_AdjList.getvertex(arr[k]).getResearcher().AddresearchCnt(+1);
			}
		}
		// vertex들 소팅
		researcher_AdjList.SortGraph();
/*		System.out.println("------------------소팅 이후-------------------------");
		for (int i = 0; i < researcher_AdjList.getTotalVertex().size(); i++) {
			System.out.println(i + " " + researcher_AdjList.getTotalVertex().get(i).getResearcher().getname());
		}*/
		researcher_AdjList.SetAlphabetIndex();

	}

	public void readResearchers(int year) throws SQLException {
		DataBase db = new DataBase();

		ArrayList<String> data = db.getCoauthorData(String.valueOf(year));
		System.out.println("max" + year);

		// db의 researcher이름들로 AddVertex하는 과정
		for (int i = 0; i < data.size(); i++) {
			String[] arr = data.get(i).split("&&");
			if (arr.length == 1) {
				Researcher r = new Researcher();
				r.setname(arr[0]);
				researcher_AdjList.AddSoloVertex(r);
				continue;
			}
			for (int k = 0; k < arr.length; k++) {
				for (int l = k + 1; l < arr.length; l++) {
					Researcher r1 = new Researcher();
					Researcher r2 = new Researcher();
					r1.setname(arr[k]);
					r2.setname(arr[l]);
					//System.out.println(arr[k] + "   " + arr[l]);
					researcher_AdjList.AddVertex(r1, r2);
				}
			}
			// 이제 연구횟수는 여기서 늘려준다
			for (int k = 0; k < arr.length; k++) {
				researcher_AdjList.getvertex(arr[k]).getResearcher().AddresearchCnt(+1);
			}
		}
		// vertex들 소팅
		researcher_AdjList.SortGraph();
	/*	System.out.println("------------------소팅 이후-------------------------");
		for (int i = 0; i < researcher_AdjList.getTotalVertex().size(); i++) {
			System.out.println(i + " " + researcher_AdjList.getTotalVertex().get(i).getResearcher().getname());
		}*/
		researcher_AdjList.SetAlphabetIndex();
	}

	public void readPapers() {
		// TODO paperInput initialize

	}

	public void extract_KeywordsFromPapers() {

	}

	public void set_KewordsToResearchers() {

	}

	public ArrayList<String> recommend_Researchers() {
		ArrayList<String> recommendList = new ArrayList<String>();
		return recommendList;
	}

	public AdjacencyList get_researcher_AdjList(int minYear, int maxYear) throws SQLException {
		readResearchers(minYear, maxYear);
		System.out.println(this.researcher_AdjList.getTotalVertex().size());
		return this.researcher_AdjList;
	}

	public AdjacencyList get_researcher_AdjList(int year) throws SQLException {
		readResearchers(year);
		System.out.println(this.researcher_AdjList.getTotalVertex().size());
		return this.researcher_AdjList;
	}

	public ArrayList<String> get_Papers() {
		return this.papers;
	}

	public boolean check_LoginInfo_IsValid(String ID, String Password) {
		return true;
	}

}