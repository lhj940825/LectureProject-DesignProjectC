package DataStructure;

import java.util.ArrayList;

public class Researcher {
	private String name;
	private String major; //
	private ArrayList<String> SelectingMajors = new ArrayList<String>();
	private int researchCnt;
	private ArrayList<Researcher> Coauthorlist = new ArrayList<Researcher>();

	public Researcher() {
		name = "none";
		major = "none";
		researchCnt = 0;
	}

	public String getname() {
		return this.name;
	}

	public String getmajor() {
		return this.major;
	}

	public ArrayList<String> getSelectingMajors() {
		return this.SelectingMajors;
	}

	public int getresearchCnt() {
		return this.researchCnt;
	}

	public ArrayList<Researcher> getCoauthorlist() {
		return this.Coauthorlist;
	}

	public void setname(String _name) {
		this.name = _name;
	}

	public void setmajor(String _major) {
		this.major = _major;
	}
	public void setresearchCnt(int cnt){
		researchCnt = cnt;
	}
	public void AddresearchCnt(int cnt) {
		researchCnt += cnt;
	}

	public void AddMajors(String major) {
		SelectingMajors.add(major);
	}

	public void ClearMajors() {
		SelectingMajors.clear();
	}

	public void AddCoauthor(Researcher author) {
		for (int i = 0; i < Coauthorlist.size(); i++) {
			if (Coauthorlist.get(i).getname().equals(author.getname())) {
				return; // 추가해주려는 author가 이미 coauthorlist에 있다면 그대로 함수 종료
			}
		}
		Coauthorlist.add(author);
	}
}