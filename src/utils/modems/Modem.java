package utils.modems;

/**
 * Abstraction class to manipulate the modem
 * @author ken
 *
 */
public class Modem {
	protected ModemTypeInterface modem;
	
	public Modem(int modemType) {
		if( modemType == 0 ) {
			this.modem = new Mifi();
		}
	}
	
	public void turnOnData() {
		this.modem.turnOnData();
	}
	
	public void turnOffData() {
		this.modem.turnOffData();
	}
	
}
