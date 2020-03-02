package Algorithms;

import DataStructure.*;
import Algorithms.Keyword;
import DB.DataBase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.apache.bcel.generic.SWAP;

import com.mysql.fabric.xmlrpc.base.Array;

public class Keyword_Extraction {
	private static final String ArrayList = null;

	private AdjacencyList adjList;
	private DataBase db;

	public Keyword_Extraction() {

	}

	public Keyword_Extraction(AdjacencyList al) throws SQLException {
		adjList = al;
		db = new DataBase();
	}

	public String extract_KeyWords_From_Papers() {
		String research_Field;

		research_Field = "";
		// extract Algorithms here
		return research_Field;

	}

	// String 내에서 필요없는 기호나 카운트 하지 않을 단어를 필터링해주는 매소드
	public String Sentencefilter(String s) {
		String S;
		S = s.replace("it", "");
		S = S.replace("there ", "");
		S = S.replace("There ", "");
		S = S.replace("It ", "");
		S = S.replace("is ", "");
		S = S.replace("are ", "");
		S = S.replace("be ", "");
		S = S.replace("to ", "");
		S = S.replace("of ", "");
		S = S.replace("at ", "");
		S = S.replace("so ", "");
		S = S.replace("for ", "");
		S = S.replace("in ", "");
		S = S.replace("In ", "");
		S = S.replace("on ", "");
		S = S.replace("the ", "");
		S = S.replace("The ", "");
		S = S.replace("and ", "");
		S = S.replace("used ", "");
		S = S.replace("all ", "");
		S = S.replace("Abstract", "");
		S = S.replace("as ", "");
		S = S.replace("by ", "");
		S = S.replace("new ", "");
		S = S.replace("AND ", "");
		S = S.replace("CAN ", "");
		S = S.replace("We ", "");
		S = S.replace("we ", "");
		S = S.replace("us ", "");
		S = S.replace("He ", "");
		S = S.replace("he ", "");
		S = S.replace("She ", "");
		S = S.replace("she ", "");
		S = S.replace("Can ", "");
		S = S.replace("can ", "");
		S = S.replace("An ", "");
		S = S.replace("an ", "");
		S = S.replace("a ", "");
		S = S.replace("A ", "");

		S = S.replace(",", "");
		S = S.replace(".", "");
		S = S.replace("?", "");
		S = S.replace(":", "");
		S = S.replace("=", "");
		S = S.replace("(", "");
		S = S.replace(")", "");
		S = S.replace("<", "");
		S = S.replace(">", "");
		S = S.replace("[", "");
		S = S.replace("]", "");
		S = S.replace("{", "");
		S = S.replace("}", "");
		S = S.replace("'", "");
		S = S.replace("\"\\", "");
		return S;
	}

	// researchers_AdjacencyList에서 각 researcher를 하나씩 불러온다
	// 불러온 researcher의 이름으로 그 researcher와 관련된 논문들의 요약문을 불러온다
	// 불러온 요약문들은 ArrayList에 담겨있는데 그냥 하나의 문장으로 연결시킨다
	// 각 researcher의 Major list로 5개를 추려 옮기기 전에 단어들을 검수할 ArrayList<Keyword> temp를
	// 만든다
	// 요약문의 단어를 하나씩 읽어가며 필터에 속하는 단어가 아니거나 temp에 여태껏 없던 단어이면 ArrayList<Keyword>
	// temp에 넣는다
	// 만약 하나씩 읽어가는데 이미 있는 단어이면 그 단어의 count를 하나 올려준다
	// 문장 끝에 .나 ,가 와도 제거하도록 예외처리한다 () 나 {}, []도 제거한다
	// 모든 요약문을 읽어냈다면 temp에서 Keyword의 count가 가장 높은 5개를 뽑아낸다.
	// 뽑아낸 Keyword들의 word만 해당 researcher의 ArrayList<String> SelectingMajors에
	// 넣어준다.
	// 이 과정을 AdjacencyList의 크기만큼 반복해서 모든 researcher의 major들을 정해준다.
	public void SelectingResearchersMajors(String minYear, String maxYear) throws SQLException {
		for (int i = 0; i < adjList.getTotalVertex().size(); i++) {
			adjList.getTotalVertex().get(i).getResearcher().ClearMajors();
			String name = adjList.getTotalVertex().get(i).getResearcher().getname();
			String Sentence = "";
			ArrayList<String> al = db.getPaperAbstractData(name, minYear, maxYear);

			for (int j = 0; j < al.size(); j++) {
				String[] ar = al.get(j).split("\\|\\|");
				for (int k = 0; k < ar.length; k++) {
					Sentence = Sentence + ' ' + ar[k];
				}
				Sentence = Sentence + ' ';
			}
			// System.out.println("###문장     : " + Sentence);
			String S = Sentencefilter(Sentence); // 문장에서 필요없는 단어나 기호를 필터링함

			if (S.substring(0, 1).equals(" ")) {
				S = S.substring(1, S.length());
			}
			S = S.replace("    ", " ");
			S = S.replace("   ", " ");
			S = S.replace("  ", " ");
			// System.out.println("###변경문장: " + S);

			String[] arr = S.split(" ");
			ArrayList<String> words = new ArrayList<String>();
			// ArrayList<String> words에 일단 가공되지 않은 단어들을 넣음
			for (int j = 0; j < arr.length; j++)
				words.add(arr[j]);

			// 빈 칸이 들어간 노드를 일단 처리하지 않고 넘어감
			// 빈 칸 노드를 제거하는 알고리즘이 떠오르면 이 곳에 추가

			// 가공된 keyword들을 넣을 ArrayList temp
			ArrayList<Keyword> temp = new ArrayList<Keyword>();
			for (int j = 0; j < words.size(); j++) {
				boolean isExisted = false;
				for (int k = 0; k < temp.size(); k++) {
					// temp에 이 keyword가 이미 있다면 count만 1 올려줌
					if (temp.get(k).getword().equals(words.get(j))) {
						temp.get(k).Addcount();
						isExisted = true;
					}
				}
				// temp에 이 keyword가 없는경우 keyword를 새로 추가해줌
				if (isExisted == false) {
					Keyword kw = new Keyword();
					kw.setword(words.get(j));
					kw.Addcount();
					temp.add(kw);
				}
			}
			// 내림차순으로 sorting
			for (int j = 0; j < temp.size(); j++) {
				for (int k = j; k < temp.size(); k++) {
					if (temp.get(j).getcount() < temp.get(k).getcount()) {
						Collections.swap(temp, j, k);
					}

				}
			}
			// System.out.println("@@@" + name + "의 Keywords@@@");
			for (int j = 0; j < temp.size(); j++) {
				System.out.println(temp.get(j).getword() + "|| count: " + temp.get(j).getcount());
			}

			for (int j = 0; j < temp.size(); j++) {
				if (temp.get(j).getcount() >= 2) {
					adjList.getTotalVertex().get(i).getResearcher().AddMajors(temp.get(j).getword());
				}
			}
		}
	}

}