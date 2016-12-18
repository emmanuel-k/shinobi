package utils.ssl;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import utils.core.Application;


public class Server {
	
	protected Application application;
	
	public Server(Application app) {
		this.application = app;
	}
	
	public void start() {
		int port=25674,a=1;
		int num=200;
		ServerSocket ss=null;
		Socket s;
		String query="",ch,host="";
		 int pt=0;
		System.out.println("Lancement du serveur...");
		boolean trouve=false;
		
		while(!(trouve)){
			try{
				try{
					ss = new ServerSocket(port);
					
					
					}catch(BindException be){
						JOptionPane.showMessageDialog(null,"Le port "+port+" est occupé !\nl'application ne peut pas démarrer");
						System.exit(1);
					}
				
				
				System.out.println("	Serveur lance sur le port "+ port+"!");
				System.out.println("	Attente de connections...");
				
				application.getModem().turnOffData();
				
				
				
				Thread oneThread = new Thread(){
					
					public void run()
					{
						try {
							
							application.getModem().turnOnData();
							
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
							
			   				Process p = Runtime.getRuntime().exec("/Applications/Tunnelblick.app/Contents/Resources/openvpn/openvpn-2.3.12-openssl-1.0.2j/openvpn --config hk1.tcpvpn.com-443.ovpn");
			   				p.waitFor();
							
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

					application.setBlackListedThread(-1);
					application.setSEMA(-1);
					application.setSEMABIN(1);

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
					
					int i=0;
					
					while(true){
						
						if(i>=num) { 
							i=0;
							Thread.sleep(500);
						}
						
						if (application.getBlackListedThread() > -1 && application.isFinished()) break;
						S[i]= new Sender(s,host,pt,i,application);
						T[i] = new Thread(S[i]);
						T[i].start();
						
						Thread.sleep(500);
						s.getLocalPort();
						i++;
						
						if (Sender.isConnected()) break;
						
					}
					
					
				}
				ss.close();
				
				
			}catch(Exception e1){
					System.out.println("SockServer: "+e1);
					
				}
				
		}
	}
}
