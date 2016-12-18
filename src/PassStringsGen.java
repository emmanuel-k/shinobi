import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;


public class PassStringsGen {
	
	
	PrintWriter pw;
	int i,j,k,l,m,n;
	char[] Tab= new char[62];
	
	
	public PassStringsGen(){
		j=0;
		
		for(i=97;i<=122;i++){
			Tab[j]= (char)i;
			j++;
		}
		for(i=65;i<=90;i++){
			Tab[j]= (char)i;
			j++;
		}
		for(i=48;i<=57;i++){
			Tab[j]= (char)i;
			j++;
		}
		System.out.print(make(6,0));
	}
	
	public void generate(){
		System.out.println("     Generating passwords file.......");
		try {
			
			
			File f= new File("passlist.txt");
			pw= new PrintWriter(new FileOutputStream(f));
			for(i=0;i<6;i++){
				for(j=0;j<=i;j++){
					
				}
				
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("     Generation successful!");
	}
	
	public String make(int length,int pos){
		if (length<=1)
			return Tab[pos]+"";
		length-=1;
		
		return Tab[pos]+make(length,pos) ;
		
	}
	
}
