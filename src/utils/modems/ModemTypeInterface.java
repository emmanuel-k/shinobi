package utils.modems;

/**
 * 
 * @author ken
 *
 */
public abstract interface ModemTypeInterface {
	
	/**
	 * Turn on Internet data on the modem
	 * @return void
	 */
	public abstract void turnOnData();
	/**
	 * Turn off Internet data on the modem
	 */
	public abstract void turnOffData();
	
}
