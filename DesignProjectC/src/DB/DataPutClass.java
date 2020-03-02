package DB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;

import DataStructure.Researcher;

public class DataPutClass {

	public static void main(String[] args) throws SQLException {
		getData("2004");
		System.out
		.println("============================================================���� 2004�� ����========================================================================================================================");
		getData("2005");
		System.out
				.println("============================================================���� 2005�� ����========================================================================================================================");
		getData("2006");
		
	}

	static void getData(String year) throws SQLException {
		DataBase db = new DataBase();
		ArrayList<String> data = db.getCoauthorData(year);
		for (String temp : data)
			System.out.println(temp);
	}
	
	
	static void putData() throws SQLException {
		// TODO Auto-generated method stub
		Parsing parse = new Parsing();
		DataBase db = new DataBase();

		BufferedReader br = null;

		// Input ��Ʈ�� ����
		InputStreamReader isr = null;

		// File Input ��Ʈ�� ����
		FileInputStream fis = null;
		// File ���
		File file = new File("C:\\Users\\HoJun\\Documents\\Visual Studio 2015\\Projects\\test\\Sorting\\testpaper.txt");

		// ���۷� �о���� �ӽ� ����
		String temp = "";

		String title = "";

		try {

			// ������ �о�鿩 File Input ��Ʈ�� ��ü ����
			fis = new FileInputStream(file);

			// File Input ��Ʈ�� ��ü�� �̿��� Input ��Ʈ�� ��ü�� �����ϴµ� ���ڵ��� UTF-8�� ����
			isr = new InputStreamReader(fis, "UTF-8");

			// Input ��Ʈ�� ��ü�� �̿��Ͽ� ���۸� ����
			br = new BufferedReader(isr);

			// ���۸� �������� �о�鿩 ���� ����
			while ((temp = br.readLine()) != null) {
				// temp = ���̸�||�� ���ڵ�(&&)||����ǥ �⵵

				// arr[1]<-���ڰ� ���°���� ����ó��
				String[] arr = temp.split("\\|\\|");
				if (!arr[1].equals("")) {
					title = (parse.doParse(arr[0]));
					System.out.println(title + "\\" + arr[1] + "\\" + arr[2]);
					//db.putCoauthorData(title, arr[1], arr[2]);
				}

			}

			System.out.println("================== ���� ���� ��� ==================");
			// System.out.println(content);

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {

			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				isr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
