package DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class DataBase {
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	private PreparedStatement pstmt;

	private String url = "jdbc:mysql://163.180.116.125:3306/designC?useUnicode=true&characterEncoding=euckr";
	private String id = "designC";
	private String password = "1234";

	public DataBase() throws SQLException {

		String driverName = "org.gjt.mm.mysql.Driver";
		try {
			Class.forName(driverName);
			getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getConnection() throws SQLException {
		con = DriverManager.getConnection(url, id, password);
	}

	public void createTable(String tableName) throws SQLException {
		stmt.executeUpdate("CREATE TABLE " + tableName + " (idx int unsigned not null, name varchar(100), address varchar(100))");
	}

	public void putCoauthorData(String title, String coAuthor, String year, String paperAbstract) throws SQLException {
		pstmt = con.prepareStatement("INSERT INTO coauthor values(?,?,?,?)");
		pstmt.setString(1, title);
		pstmt.setString(2, coAuthor);
		pstmt.setString(3, year);
		pstmt.setString(4, paperAbstract);
		pstmt.executeUpdate();
		pstmt.close();
	}

	public ArrayList<String> getCoauthorData() throws SQLException {
		ArrayList<String> coauthorDataList = new ArrayList<String>();
		pstmt = con.prepareStatement(new String("select * from coauthor"));
		rs = pstmt.executeQuery();
		String title, coAuthor, year, paperAbstract;
		while (rs.next()) {
			title = rs.getString("title");
			coAuthor = rs.getString("coauthor");
			year = rs.getString("year");
			paperAbstract = rs.getString("abstract");
			coauthorDataList.add(title + "||" + coAuthor + "||" + year + "||" + paperAbstract);
		}
		rs.close();
		pstmt.close();
		return coauthorDataList;
	}

	public ArrayList<String> getCoauthorData(String year) throws SQLException {
		ArrayList<String> coauthorDataList = new ArrayList<String>();
		pstmt = con.prepareStatement(new String("select coauthor from coauthor where year = " + year));

		rs = pstmt.executeQuery();
		String coAuthor;
		while (rs.next()) {
			coAuthor = rs.getString("coauthor");
			coauthorDataList.add(coAuthor);
		}
		rs.close();
		pstmt.close();
		return coauthorDataList;
	}

	public ArrayList<String> getCoauthorData(String minYear, String maxYear) throws SQLException {
		ArrayList<String> coauthorDataList = new ArrayList<String>();
		pstmt = con.prepareStatement(new String("select coauthor from coauthor where  year >=" + minYear + " AND year<= " + maxYear));

		rs = pstmt.executeQuery();
		String coAuthor;
		while (rs.next()) {
			coAuthor = rs.getString("coauthor");
			coauthorDataList.add(coAuthor);
		}
		rs.close();
		pstmt.close();
		return coauthorDataList;
	}

	public ArrayList<String> getpaperData(String minYear, String maxYear) throws SQLException {
		ArrayList<String> papers = new ArrayList<String>();
		pstmt = con.prepareStatement(new String("select * from coauthor where year >=" + minYear + " AND year<= " + maxYear));
		rs = pstmt.executeQuery();
		String title, coAuthor, year, paperAbstract;

		while (rs.next()) {
			title = rs.getString("title");
			coAuthor = rs.getString("coauthor");
			year = rs.getString("year");
			paperAbstract = rs.getString("abstract");
			papers.add(title + "||" + coAuthor + "||" + year + "||" + paperAbstract);
		}
		rs.close();
		pstmt.close();
		return papers;
	}

	public ArrayList<String> getPaperData(String author) throws SQLException {
		ArrayList<String> papers = new ArrayList<String>();
		pstmt = con.prepareStatement(new String("select * from coauthor where coauthor like '%" + author + "%'"));
		rs = pstmt.executeQuery();
		String title, coAuthor, year, paperAbstract;

		while (rs.next()) {
			title = rs.getString("title");
			coAuthor = rs.getString("coauthor");
			year = rs.getString("year");
			paperAbstract = rs.getString("abstract");
			papers.add(title + "||" + coAuthor + "||" + year + "||" + paperAbstract);
		}
		rs.close();
		pstmt.close();
		return papers;
	}

	public ArrayList<String> getPaperAbstractData(String author, String minYear, String maxYear) throws SQLException {
		ArrayList<String> papers = new ArrayList<String>();
		pstmt = con.prepareStatement(new String("select * from coauthor where coauthor like '%" + author + "%' AND year >= " + minYear + " AND year<= " + maxYear));
		rs = pstmt.executeQuery();
		String title, coAuthor, year, paperAbstract;

		while (rs.next()) {
			title = rs.getString("title");
			paperAbstract = rs.getString("abstract");
			papers.add(title + "||" + paperAbstract);
		}
		rs.close();
		pstmt.close();
		return papers;
	}

	public ArrayList<String> getPaperDataByTitle(String Title, String minYear, String maxYear) throws SQLException {
		ArrayList<String> papers = new ArrayList<String>();
		pstmt = con.prepareStatement(new String("select * from coauthor where title like '%" + Title + "%'AND year >=" + minYear + " AND year<= " + maxYear));
		rs = pstmt.executeQuery(); 																	
		String title, coAuthor, year, paperAbstract;

		while (rs.next()) {
			title = rs.getString("title");
			coAuthor = rs.getString("coauthor");
			year = rs.getString("year");
			paperAbstract = rs.getString("abstract");
			papers.add(title + "||" + coAuthor + "||" + year + "||" + paperAbstract);
		}
		rs.close();
		pstmt.close();
		return papers;
	}

	public ArrayList<String> getPaperDataByYear(String Year) throws SQLException {
		ArrayList<String> papers = new ArrayList<String>();
		pstmt = con.prepareStatement(new String("select * from coauthor where year = " + Year));
		rs = pstmt.executeQuery();
		String title, coAuthor, year, paperAbstract;

		while (rs.next()) {
			title = rs.getString("title");
			coAuthor = rs.getString("coauthor");
			year = rs.getString("year");
			paperAbstract = rs.getString("abstract");
			papers.add(title + "||" + coAuthor + "||" + year + "||" + paperAbstract);
		}
		rs.close();
		pstmt.close();
		return papers;
	}

	public ArrayList<String> getPaperDataByContent(String content, String minYear, String maxYear) throws SQLException {
		ArrayList<String> papers = new ArrayList<String>();
		pstmt = con.prepareStatement(new String("select * from coauthor where abstract like '%" + content + "%'AND year >=" + minYear + " AND year<= " + maxYear));
		rs = pstmt.executeQuery();
		String title, coAuthor, year, paperAbstract;

		while (rs.next()) {
			title = rs.getString("title");
			coAuthor = rs.getString("coauthor");
			year = rs.getString("year");
			paperAbstract = rs.getString("abstract");
			papers.add(title + "||" + coAuthor + "||" + year + "||" + paperAbstract);
		}
		rs.close();
		pstmt.close();
		return papers;
	}

	public void closeConnection() throws SQLException {
		con.close();
	}
}
