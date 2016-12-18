package main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;


public class inondation implements Runnable {
	static PrintWriter writer;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String Host="";
		int port;
		//URL url= new URL("http://lordmancer.com");
		Socket S=new Socket() ;
		InputStream in ;
		OutputStream out ;
		BufferedReader reader ;
		String[] addrList= {"youtube.com:80","facebook.com:80","orange.cm:80","google.com:443"};
		
		String command = "GET / HTTP/1.0\nUser-Agent: Mozilla/5.0 (Windows NT 6.3; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0\nConnection: keep-alive\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\nHost: lordmancer.com\nAccept-Encoding: gzip\nAccept-Language: fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3\n\n";
		String command1="GET /?gfe_rd=cr&ei=jRl5VsfIMMrB8gfy0LLgDQ&gws_rd=cr,ssl HTTP/2.0\nUser-Agent: Opera/9.80 (Windows NT 6.2; WOW64) Presto/2.12.388 Version/12.14\nHost: www.pdfsam.org\nAccept: text/html, application/xml;q=0.9, application/xhtml+xml, image/png, image/webp, image/jpeg, image/gif, image/x-xbitmap, */*;q=0.1\nAccept-Language: fr-FR,fr;q=0.9,en;q=0.8\nAccept-Encoding: gzip, deflate\nCookie: lm=djqpg02gkvs2pappdiqqoklj30\nConnection: Keep-Alive\n\n";
		String command2 = "GET / HTTP/1.0\n\n";
		boolean connecte;
		
		int i=1;
		while(true){
			Random r = new Random();
			int pos =r.nextInt(addrList.length-1);
			String str=addrList[pos];
			Host=str.substring(0, str.indexOf(":"));
			port=Integer.parseInt(str.substring(str.indexOf(":")+1));
			System.out.println(Host+" : "+port);
			connecte=false;
			while (!connecte){
				connecte=true;
				try{
					
					S = new Socket(Host,port);
					//S.setSoTimeout(3000);
					//S.bind(new InetSocketAddress("10.179.162.136", 5000));
					//S.connect(new InetSocketAddress(Host, 80));
					System.out.println("envoi "+i+"!");
					//Thread.sleep(20000);
				}catch(IOException E){connecte=false;
					}
				}
			
			try{
				in = S.getInputStream();
				out = S.getOutputStream();
				
				reader = new BufferedReader(new InputStreamReader(in));
				writer = new PrintWriter(out);
				
				
				writer.print(command2);
				writer.flush();
				sleep(3,reader);
				i++;
				S.close();
			}catch(Exception e1){e1.printStackTrace();}
		}
		
	}
	
	
	private static void sleep(int s,BufferedReader reader) throws IOException, InterruptedException {
		s *=1000;String input="";
		long tempsI = System.currentTimeMillis();
		while ((System.currentTimeMillis()) < (tempsI+s)){
				if (input==null) break;
				try{
					
					while(reader.ready()){
						input = reader.readLine();
						System.out.println(input);
					}
				}catch(Exception e){System.out.println(e);}
				
			
			
		}
		
		
	}
}
