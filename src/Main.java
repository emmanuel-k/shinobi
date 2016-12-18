import utils.core.Application;
import utils.ssl.Server;

public class Main {
	// Modem types
	final static int MIFI = 0;
	final static int INTERNETKEY = 1;

	
	/**
	 * Starting of the program
	 * @param args
	 */
	public static void main(String[] args) {
		
		// First we initialize the application
		Application app = new Application();
		app.setModemType(MIFI);
		
		// Now we can start the server
		Server server = new Server(app);
		server.start();
	}
	
	
}


