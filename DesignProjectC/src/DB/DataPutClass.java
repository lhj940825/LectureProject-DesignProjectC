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
		.println("============================================================위는 2004년 논문들========================================================================================================================");
		getData("2005");
		System.out
				.println("============================================================위는 2005년 논문들========================================================================================================================");
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

		// Input 스트림 생성
		InputStreamReader isr = null;

		// File Input 스트림 생성
		FileInputStream fis = null;
		// File 경로
		File file = new File("C:\\Users\\HoJun\\Documents\\Visual Studio 2015\\Projects\\test\\Sorting\\testpaper.txt");

		// 버퍼로 읽어들일 임시 변수
		String temp = "";

		String title = "";

		try {

			// 파일을 읽어들여 File Input 스트림 객체 생성
			fis = new FileInputStream(file);

			// File Input 스트림 객체를 이용해 Input 스트림 객체를 생서하는데 인코딩을 UTF-8로 지정
			isr = new InputStreamReader(fis, "UTF-8");

			// Input 스트림 객체를 이용하여 버퍼를 생성
			br = new BufferedReader(isr);

			// 버퍼를 한줄한줄 읽어들여 내용 추출
			while ((temp = br.readLine()) != null) {
				// temp = 논문이름||논문 저자들(&&)||논문발표 년도

				// arr[1]<-저자가 없는경우의 예외처리
				String[] arr = temp.split("\\|\\|");
				if (!arr[1].equals("")) {
					title = (parse.doParse(arr[0]));
					System.out.println(title + "\\" + arr[1] + "\\" + arr[2]);
					//db.putCoauthorData(title, arr[1], arr[2]);
				}

			}

			System.out.println("================== 파일 내용 출력 ==================");
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
