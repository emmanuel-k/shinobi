package utils.ssl;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;
import java.net.SocketException;

import utils.core.Application;





public class Receiver implements Runnable {
	BufferedInputStream rbfr;
	BufferedOutputStream prw;
	static int i;
	Socket rs,s;
	Sender S;
	Application application;
	public Receiver(Socket rs,Socket s,BufferedInputStream rbfr,BufferedOutputStream prw,Sender S, Application application){
		this.rbfr=rbfr;
		this.prw=prw;
		this.rs=rs;
		this.s=s;
		this.S=S;
		this.application = application;
	}
	@SuppressWarnings("unused")
	@Override
	public void run() {
		int first=0;
		
		try{
		//while(true){
			try{
			try{
				
					byte[] buf =  new byte[1024];

					int bytesIn = 0,byteCount=0;
					 i=0;
					while (((bytesIn = rbfr.read(buf)) >= 0) )
					{
						System.out.println(++i+" fois");
						System.out.println("[Bytes received "+" : " + bytesIn+"] from "+S.getHost()+" By Thread "+S.numThread);
						application.setFinished(false);
						System.out.println("Le grand gagnant est " + S.numThread);
						application.setWinnerThread(S.numThread);
						if(first==0){
							
							first=1;
							if ((bytesIn!=56)&&(bytesIn!=28)){
								/*S.mustClose=true;
								break;*/
							}
							else 
								if (application.getSEMABIN()==1){
									application.setSEMABIN(0);
									application.setBlackListedThread(S.numThread);
									application.setSEMA(S.numThread);
								}
						}
						
						if(application.getSEMA()==S.numThread){
							prw.write(buf, 0, bytesIn);
							prw.flush();
							byteCount += bytesIn;
						}
					}
					System.out.println("on sort");
					Thread.sleep(100);
			}catch(SocketException e1){System.out.println("class rfs: "+e1);}
			}catch(Exception e){System.out.println("class rfs: "+e);}
				
			//}
						//System.out.println("j= "+j);
						
					
				
			
		
		}catch(Exception e){System.out.println(e);}
	}

}

