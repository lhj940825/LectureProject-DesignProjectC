package Streaming;

import java.sql.SQLException;
import java.util.ArrayList;

public class RealTimeStreamThread extends Thread {
	private int currentYear;
	private int time;
	public callBack m_callBack;
	
	public interface callBack {
		void dataReadNotify(int currentYear) throws SQLException;
	}
	public void setCallBack(callBack callBack) {
		m_callBack = callBack;
	}

	
	public void RealTimeStreamThread() {

	}

	public RealTimeStreamThread(int maxYear) {
		// TODO Auto-generated constructor stub
		this.currentYear = maxYear;

	}


	public void run() {
		int t = this.time;
		while (true) {
			t--;
			if(t==0){
				try {
					m_callBack.dataReadNotify(++currentYear);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				t = this.time;
			}
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void setTime(int time){
		this.time = time;
	}

}
