package utils.core;
import utils.modems.Mifi;
import utils.modems.ModemTypeInterface;

public class Application {

	protected int blackListedThread = -1;
	protected int SEMA = -1;
	protected int SEMABIN = 1;
	protected boolean succeeded = false;
	protected boolean finished = true;
	protected int winnerThread = -10;
	
	protected ModemTypeInterface modem;
	
	public Application() {}
	
	public void setModemType(int modemType) {
		if( modemType == 0 ) {
			setModem(new Mifi());
		} else if( modemType == 1 ) {
			setModem(null);
		}
	}
	
	
	// GETTERS & SETTERS
	public int getBlackListedThread() { return blackListedThread; }
	public void setBlackListedThread(int blackListedThread) { this.blackListedThread = blackListedThread; }
	
	public int getSEMA() { return SEMA; }
	public void setSEMA(int sEMA) { SEMA = sEMA; }
	
	public int getSEMABIN() { return SEMABIN; }
	public void setSEMABIN(int sEMABIN) { SEMABIN = sEMABIN; }
	
	public boolean isSucceeded() { return succeeded; }
	public void setSucceeded(boolean succeeded) { this.succeeded = succeeded; }
	
	public boolean isFinished() { return finished; }
	public void setFinished(boolean finished) { this.finished = finished; }
	
	public int getWinnerThread() { return winnerThread; }
	public void setWinnerThread(int winnerThread) { this.winnerThread = winnerThread; }
	
	public ModemTypeInterface getModem() { return modem; }
	protected void setModem(ModemTypeInterface modem) { this.modem = modem; }

}
