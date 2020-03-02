package MVC;

import java.awt.EventQueue;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Dblp_System window = null;
				try {
					window = new Dblp_System();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				window.initialize();
			}
		});
	}
}
 