package com.test.paperAbstract;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;


public class DataBase {
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	private PreparedStatement pstmt;

	private String url = "jdbc:mysql://localhost:3306/designC?useUnicode=true&characterEncoding=euckr";
	private String id = "designC";
	private String password = "1234";

	public DataBase()throws SQLException {

		String driverName = "org.gjt.mm.mysql.Driver"; // ����̹� �̸� ���� String
		try {
			Class.forName(driverName);
			getConnection(); 
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // ����̹� �ε�
	}

	public void getConnection() throws SQLException {
		con = DriverManager.getConnection(url, id, password);
	}

	// TODO �Ķ���ͷ� ���̺� �̸� �� �ڷ�� �޾ƿͼ� �ֱ�
	public void createTable(String tableName) throws SQLException { // ���⼭�� data�� �߰��ϴ� �ڵ嵵 ����
		stmt.executeUpdate(
				"CREATE TABLE "+tableName+" (idx int unsigned not null, name varchar(100), address varchar(100))");
	}

	public void putCoauthorData(String title, String coAuthor, String year, String paperAbstract) throws SQLException {
		pstmt = con.prepareStatement("INSERT INTO coauthor2 values(?,?,?,?)");
		pstmt.setString(1, title);
		pstmt.setString(2, coAuthor);
		pstmt.setString(3, year);
		pstmt.setString(4, paperAbstract);
		pstmt.executeUpdate();
		pstmt.close();
	}
	
	public ArrayList<String> getCoauthorData() throws SQLException{
		ArrayList<String> coauthorDataList = new ArrayList<String>();
		pstmt = con.prepareStatement(new String("select * from coauthor"));
		rs = pstmt.executeQuery();
		String title, coAuthor, year, paperAbstract;
		while (rs.next()) {			
			title = rs.getString("title");
			coAuthor = rs.getString("coauthor");
			year = rs.getString("year");
			paperAbstract = rs.getString("abstract");
			coauthorDataList.add(title+"||"+coAuthor+"||"+year+"||"+paperAbstract);
		}
		rs.close();
		pstmt.close();
		return coauthorDataList;
	}
	public ArrayList<String> getCoauthorData(String _year) throws SQLException{
		ArrayList<String> coauthorDataList = new ArrayList<String>();
		pstmt = con.prepareStatement(new String("select coauthor, year from coauthor where year = "+_year));
		
		rs = pstmt.executeQuery();
		String title, coAuthor, year;
		while (rs.next()) {			
			
			coAuthor = rs.getString("coauthor");
			year = rs.getString("year");
			coauthorDataList.add(coAuthor+"||"+year);
		}
		rs.close();
		pstmt.close();
		return coauthorDataList;
	}
	
	public ArrayList<String> getPaperData(String author) throws SQLException{
		ArrayList<String> papers = new ArrayList<String>();
		pstmt = con.prepareStatement(new String("select * from coauthor where coauthor like '%"+author+"%'"));
		rs = pstmt.executeQuery();
		String title, coAuthor, year,paperAbstract;
		
		while(rs.next()){
			title = rs.getString("title");
			coAuthor = rs.getString("coauthor");
			year = rs.getString("year");
			paperAbstract = rs.getString("abstract");
			papers.add(title+"||"+coAuthor+"||"+year+"||"+paperAbstract);
		}
		rs.close();
		pstmt.close();
		return papers;
	}
	
	public void closeConnection() throws SQLException {
		con.close();
	}
}
