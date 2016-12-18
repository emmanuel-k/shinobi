package main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import ssl.Sender;


public class main {
	public static int blackListed=-1;
	public static int SEMA=-1;
	public static int SEMABIN=1;
	public static boolean EstConnect = false;
	public static boolean fin = true;
	public static int gagnant = -10;
	public static void main(String[] args) {

		//PassStringsGen psg= new PassStringsGen();
		//flow();
	//	Operation_Connectionn();
		SSlServer();
		
	}
	
	public static void flow(){
		int num=3;
		inondation i=new inondation();
		Thread[] T= new Thread[num];
		for(int j=0;j<T.length;j++){
			T[j]=new Thread(i);
		}
		for(int j=0;j<T.length;j++){
			T[j].start();
		}
	}
	
	public static void SSlServer(){
		 int port=1995,a=1;
		 int num=200;
   		ServerSocket ss=null;
   		Socket s;
   		Thread T1;
   		String query="",ch,host="";
		 int pt=0;
   		System.out.println("Lancement du serveur...");
   		boolean trouve=false;
   		
   		while(!(trouve)){
   			try{
   				try{
   					ss = new ServerSocket(port);
   					
   					
					}catch(BindException be){
						JOptionPane.showMessageDialog(null,"Le port "+port+" est occup� !\nl'application ne peut pas d�marrer");
						System.exit(1);
					}
   				
   				
   				System.out.println("	Serveur lance sur le port "+ port+"!");
   				System.out.println("	Attente de connections...");
   				
   				Operation_DeConnectionn();
   				
   				
   				
   				Thread oneThread = new Thread(){
   					
   					public void run()
   					{
   						try {
							
							Operation_Connectionn();
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
   					}
   					
   					
   					
   				};
   					Thread onThread = new Thread(){
   					
   					public void run()
   					{
   						try {
   							Thread.sleep(5000);
							
   			   				//Process p = Runtime.getRuntime().exec("openvpn --config hk1.tcpvpn.com-443.ovpn");
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
   					}
   					
   					
   					
   				};
   				
   				
   				oneThread.start();
   				onThread.start();
   				while (a==1){
   					
   					s = ss.accept();
   					blackListed=-1;
   					SEMA=-1;
   					SEMABIN=1;
   					BufferedReader BR=new BufferedReader(new InputStreamReader(s.getInputStream()));
   					while(!(BR.ready()))Thread.sleep(100);
   					query="";
   					 while(BR.ready()){
   						 ch=BR.readLine();
   						 query +=ch+"\n";
   					 }
						System.out.println(query);
   					 if (query.startsWith("CONNECT")){
   							String sub = query.substring(8);
   							pt = Integer.parseInt(sub.substring(sub.indexOf(':')+1,sub.indexOf("HTTP")-1));
   							host = query.substring(query.indexOf(" ")+1, query.indexOf(":")); //host = sub.substring(0,sub.indexOf(':'));
   							query= query.replaceFirst("CONNECT", "GET");
   							query= query.replaceFirst("HTTP/1.1", "HTTP/1.1");
   							query= query.substring(0,3)+" / "+ query.substring(query.indexOf("HTTP/1"));
   							//query= query.substring(0,query.indexOf("\n")+1);
   							query= query+"\r\n";
   						}
   					PrintWriter prw= new PrintWriter(s.getOutputStream());
   					
   					
   					prw.print("HTTP/1.0 200 connection established\r\n\r\n");
   					prw.flush();
   					Sender[] S=new Sender[num];
   					Thread[] T=new Thread[num];
   					
   					int i=0,j=0;
   					
   					while(true){
   						System.out.println("");
   						if(i>=num){ i=0;j=0;Thread.sleep(500);}
   						if (blackListed>-1 && fin)break;
   						S[i]= new Sender(s,host,pt,i);
   						T[i] = new Thread(S[i]);
   						T[i].start();
   						Thread.sleep(500);
   						s.getLocalPort();
   						i++;j++;
   						//if (Sender.isConnected()) break;
   						
   					}
   					
   					
   				}
   				ss.close();
   				
   				
   			}catch(Exception e1){
   					System.out.println("SockServer: "+e1);
   					
   				}
   				
   		}
           
         

/**/

	}
	
	public static void Operation_Connectionn()
	{
	//	ProcessBuilder builder = new ProcessBuilder();
		try {
			Process p = Runtime.getRuntime().exec("rasdial Orange");
			System.out.println("Connection de la cle internet ");
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void Operation_DeConnectionn()
	{
	//	ProcessBuilder builder = new ProcessBuilder();
		try {
			Process p = Runtime.getRuntime().exec("rasdial /disconnect");
			System.out.println("Deconnection de la cle internet ");
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}


