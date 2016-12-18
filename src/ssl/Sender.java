package ssl;


import java.net.*;
import java.io.*;

import main.main;







public class Sender implements Runnable{
	public boolean mustClose=false;
	public int numThread;
	private Socket s;
	Socket rs=null;
	private String host;
	private int port;
	@SuppressWarnings("static-access")
	public Sender(Socket s,String host,int port,int numThread){
		this.s=s;
		this.setHost(host);
		this.port=port;
		this.numThread=numThread;
	}
	@Override
	public void run() {
		
		PrintWriter prw;
		//System.out.println("on viens ici merde");
		
		try{
			
			
			if(main.gagnant != -10 && numThread != main.gagnant)
			{
				
				return;
			}
			
			System.out.println(getHost()+":"+port);
			
			
			rs = new Socket(getHost(),port);
			
			Thread closeSock= new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(true){
						if (mustClose){
							try {
								
								s.close();
								rs.close();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
							
						} else
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
					
				}
	        	
	        });  
				closeSock.start();

				
				if(main.gagnant != -10 && numThread != main.gagnant)
				{
					
					return;
				}
				
			
			BufferedInputStream bf=new BufferedInputStream(s.getInputStream());
			BufferedOutputStream writer= new BufferedOutputStream(rs.getOutputStream());
			BufferedInputStream bfi=new BufferedInputStream(rs.getInputStream());
			BufferedOutputStream bfo= new BufferedOutputStream(s.getOutputStream());
			//rprw.write(query+"\r\n");
			//rprw.flush();
			System.out.println("Connected to "+getHost()+':'+port + " !" + " le pede est "+ numThread);
			//int i=0;
			Receiver rfs= new Receiver(rs,s,bfi,bfo,this);
			Thread trfs= new Thread(rfs);
			
			trfs.start();
			
			int second =1;
			
			//while(true){	
				try{
					
					byte[] buf = new byte[1024];
					int bytesIn = 0,byteCount=0;
					while ((bytesIn = bf.read(buf)) >= 0) 
					{	
						if(((main.SEMA==-1)&&(main.blackListed==-1))||((main.SEMA==numThread)&&(main.blackListed>-1))){
							  System.out.println("[Bytes sended "+" : " + bytesIn+"]"+" By Thread "+numThread);
							  	
							  
							 
								  writer.write(buf, 0, bytesIn);
								  writer.flush();
								  
							 
							 
							  
							
							//bf=new BufferedInputStream(s.getInputStream());
							byteCount += bytesIn;
							second++;
							}
					}
					//System.out.println("sended: "+ byteCount+" bytes");
					
						//System.out.println("i= "+i);
						
						//Thread.sleep(100);
				}catch(Exception e){System.out.println("class proxy: "+e);
					if(e.toString().contains("Connection reset")) main.blackListed=21;
				}
			
			
			//}
				
				main.EstConnect = true;
			
		}catch(Exception e){

			main.EstConnect = false;
		}
	}
	
	public static boolean isConnected(){
		boolean val=false;
		Socket s=null;
		try{
			
			s= new Socket("vfsonline.comli.com",80);
			BufferedReader bf= new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter pw= new PrintWriter(s.getOutputStream());
			pw.println("GET / HTTP/1.1\r\n"+
					"host: vfsonline.comli.com \r\n");
			pw.flush();
			String ligne=bf.readLine();
			//System.out.println(ligne);
			if (!ligne.contains("302 Found")&&!ligne.contains("200 OK")) throw new RuntimeException();
			val=true;
			s.close();
		}catch(Exception e){//e.printStackTrace();
		
		}
		return val;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}

}
