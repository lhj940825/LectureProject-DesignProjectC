package DB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Parsing {

	public Parsing(){
		
	}
	
	public String doParse(String key){
		URL url = null;
		BufferedReader input = null;
		String address = "http://dblp.uni-trier.de/rec/bib/" + key;
		String line = "";
		String form = null;
		
		boolean found = false;

		try {
			url = new URL(address);
			url.openConnection();
			int temp;
			input = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			while ((line = input.readLine()) != null) {

				if ((temp = line.indexOf("title")) != -1 && !found) {
					//System.out.println(line);
					form = line;
					line = input.readLine();
					//System.out.println(line);
					while (line.indexOf("booktitle") == -1) {
						form += line;
						line = input.readLine();
					//	System.out.println(line);
					}
				
					found = true;
/*					
					form = line;
					while(line.indexOf("booktitle")== -1){
						
						line = input.readLine();
						System.out.println("\\\\"+line);
						form+=line;
					}
					found = true;
*/				}
				//System.out.println(line);

			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] arr = form.split(" ");
		ArrayList<String> data = new ArrayList<String>();
		for (int i = 0; i < arr.length; i++) {
			if (!arr[i].equals(""))
				data.add(arr[i]);
		}

/*		System.out.println("============================================");
		System.out.println(form);
		for (int i = 0; i < data.size(); i++)
			System.out.println(data.get(i));
		System.out.println("============================================");*/
		
		String temp = data.get(2).substring(1,data.get(2).length());
		//System.out.println(temp);
		data.set(2, temp);
		int size = data.get(data.size()-1).length();
		temp =data.get(data.size()-1).substring(0, size-2);
		//System.out.println(temp);
		data.set(data.size()-1, temp);
		
		String total = "";
		for(int i = 2; i < data.size(); i ++){
			total += data.get(i)+ " ";
		}
		//System.out.println(total);
		return total;
	}
		
}
