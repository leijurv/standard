package org.usfirst.frc4904.standard.commands.healthchecks;


import edu.wpi.first.wpilibj.Compressor;

/**
 * This command will wait a certain amount of time since being started, and then warn
 * you if the compressor isn't drawing enough current. This indicates a low-pressure system,
 * which might be indicative of a leak or an open pressure valve.
 * It is recommended to set the timeout around 2 seconds, and measure the current by calling
 * (new Compressor(0)).getCompressorCurrent()
 * in teleopPeriodic (which runs at 50Hz) and printing out the output 2 seconds after turning
 * on the compressor. Make sure the system is sealed during this measurement!
 */
public class PressureValveClosedTest extends AbstractHealthCheck {
	private final double timeout;
	private final double currentThreshold;
	private final Compressor compressor;
	public static final double DEFAULT_TIMEOUT = 2;
	
	/**
	 * @param compressor
	 *        The compressor to read current from
	 * @param timeout
	 *        Timeout before checking compressor current in seconds
	 * @param currentThreshold
	 *        Current the compressor should be drawing at minimum if the system is pressurizing
	 */
	public PressureValveClosedTest(Compressor compressor, double timeout, double currentThreshold) {
		super("PressureValveClosedTest");
		this.timeout = timeout;
		this.currentThreshold = currentThreshold;
		this.compressor = compressor;
	}
	
	/**
	 * Will construct a new Compressor(0).
	 * 
	 * @param timeout
	 *        Timeout before checking compressor current in seconds
	 * @param currentThreshold
	 *        Current the compressor should be drawing at minimum if the system is pressurizing
	 */
	public PressureValveClosedTest(double timeout, double currentThreshold) {
		this(new Compressor(0), timeout, currentThreshold);
	}
	
	/**
	 * Will use PressureValveClosedTest.DEFAULT_TIMEOUT as the timeout.
	 * 
	 * @param compressor
	 *        The compressor to read current from
	 * @param currentThreshold
	 *        Current the compressor should be drawing at minimum if the system is pressurizing
	 */
	public PressureValveClosedTest(Compressor compressor, double currentThreshold) {
		this(compressor, DEFAULT_TIMEOUT, currentThreshold);
	}
	
	/**
	 * Will use PressureValveClosedTest.DEFAULT_TIMEOUT as the timeout.
	 * Will construct a new Compressor(0).
	 * 
	 * @param currentThreshold
	 *        Current the compressor should be drawing at minimum if the system is pressurizing
	 */
	public PressureValveClosedTest(double currentThreshold) {
		this(DEFAULT_TIMEOUT, currentThreshold);
	}
	
	@Override
	protected boolean finished() {
		return isTimedOut();
	}
	
	@Override
	protected void initialize() {
		setTimeout(timeout);
	}
	
	@Override
	protected HealthLevel getStatus() {
		return compressor.getCompressorCurrent() < currentThreshold ? HealthLevel.DANGEROUS : HealthLevel.PERFECT;
	}
}
