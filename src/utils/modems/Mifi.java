package utils.modems;

import java.io.IOException;

/**
 * Class to manipulate a HUAWEI Wifi Modem
 * @author ken
 *
 */
public class Mifi implements ModemTypeInterface {
	
	public Mifi() {}
	
	@Override
	public void turnOnData() {
		try {
			Process p = Runtime.getRuntime().exec("php -f turn_on_connection.php > connection_logs.txt");
			System.out.println("Activation des données Internet sur le Mifi...");
			p.waitFor();
			System.out.println("Données Internet du Mifi activées!");
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void turnOffData() {
		try {
			Process p = Runtime.getRuntime().exec("php -f turn_off_connection.php > connection_logs.txt");
			System.out.println("Désactivation des données Internet sur le Mifi...");
			p.waitFor();
			System.out.println("Données Internet du Mifi désactivées!");
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
