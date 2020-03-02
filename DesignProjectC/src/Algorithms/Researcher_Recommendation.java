package Algorithms;

import java.util.ArrayList;

import DataStructure.AdjacencyList;
import MVC.Dblp_System;

public class Researcher_Recommendation {
	AdjacencyList reserchesrsList;

	public Researcher_Recommendation() {

	}

	public Researcher_Recommendation(AdjacencyList researcherList) {
		this.reserchesrsList = researcherList;
	}

	public ArrayList<String> recommend_Researchers(String targetName) {
		ArrayList<String> recommendedList = new ArrayList<String>();
		// recommendation Algorithms here
		ArrayList<AdjacencyList.vertex> vertexs = this.reserchesrsList.getTotalVertex();
		AdjacencyList.vertex target = null;
		// target Vertex를 찾아서 저장
		for (int i = 0; i < vertexs.size(); i++) {
			if (vertexs.get(i).getResearcher().getname().equals(targetName)) {
				target = vertexs.get(i);
				break;
			}
		}

		if (target.getResearcher().getSelectingMajors().size() != 0) {
			System.out.println("=====");
			for (int i = 0; i < vertexs.size(); i++) {
				if (vertexs.get(i).getResearcher().getSelectingMajors().size() != 0 && !vertexs.get(i).getResearcher().getname().equals(targetName)) {
					if (compareKeyWords(vertexs.get(i), target)) {// if keywords
																	// Match
																	// then
																	// recommend
																	// that
																	// Researcher
						recommendedList.add(vertexs.get(i).getResearcher().getname());
					}
				}
			}
		}
		return recommendedList;
	}

	public boolean compareKeyWords(AdjacencyList.vertex temp, AdjacencyList.vertex target) {
		ArrayList<String> tempKeyWords = temp.getResearcher().getSelectingMajors();
		ArrayList<String> targetKeyWords = target.getResearcher().getSelectingMajors();
		for (int i = 0; i < tempKeyWords.size(); i++) {
			for (int j = 0; j < targetKeyWords.size(); j++) {
				if (tempKeyWords.get(i).equals(targetKeyWords.get(j))) {
					return true;
				}
			}
		}
		return false;
	}

}
