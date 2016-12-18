package ssl;

import java.io.BufferedInputStream;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class http implements Runnable {
	String host,query;
	int port;
	public http(String host,int port,String query){
		this.host=host;
		this.port=port;
		this.query=query;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			Socket s=new Socket(host,port);
			BufferedReader bf=new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter pw=new PrintWriter(s.getOutputStream());
			pw.println(query);
			pw.flush();
			while(!(bf.ready())) Thread.sleep(50);
			System.out.println(bf.readLine());
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
