package org.usfirst.frc4904.standard.commands.safety;


import java.util.LinkedList;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public abstract class HealthCheck extends Command {
	private volatile HealthLevel currentStatus = HealthLevel.UNKNOWN;
	private final Object alarmsLock = new Object();// a synchronizer lock for the alarms list. needed because the alarms list will be accessed from multiple threads
	private final LinkedList<HealthCheckAlarm> alarms = new LinkedList<>();
	private final String alarmName;
	
	protected HealthCheck(String alarmName) {
		this.alarmName = alarmName;
	}
	
	/**
	 * Some checks may be asynchronous, some checks may be synchronous. This function should begin the check. If the check can happen instantly, this should instantly call setHealthStatus
	 */
	protected abstract void beginCheck();
	
	/**
	 * The subclass should call this when the health check is finished
	 * Protected so that only the subclass can call it
	 * 
	 * @param updatedLevel
	 */
	protected void setHealthStatus(final HealthLevel updatedLevel) {
		if (updatedLevel == null) {
			throw new IllegalArgumentException("can't be null");
		}
		if (currentStatus == updatedLevel) {// nothing new, don't update the alarms
			return;
		}
		this.currentStatus = updatedLevel;
		new Thread() {// start a new thread here because the loop that calls this function might need to be fast, and some of the alarms might take actions on the updated state that could be slow (like synchronously notifying the driver or asking the driver for a decision)
			public void run() {
				synchronized (alarmsLock) {
					for (HealthCheckAlarm alarm : alarms) {
						alarm.updateStatus(updatedLevel);
					}
				}
			}
		}.start();
	}
	
	/**
	 * Add a health check alarm to the list of alarms that are notified whenever this check's state changes
	 * 
	 * @param alarm
	 */
	void addAlarm(HealthCheckAlarm alarm) {
		synchronized (alarmsLock) {
			alarms.push(alarm);
		}
	}
	
	/**
	 * If anything needs the current health level, call this
	 * 
	 * @return
	 */
	public HealthLevel getHealthStatus() {
		return currentStatus;
	}
}
