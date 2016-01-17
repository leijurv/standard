package org.usfirst.frc4904.standard.custom.sensors;


import edu.wpi.first.wpilibj.PIDSource;

/**
 * This is an extremely minimal encoder
 * that can be either a normal encoder or a
 * CAN encoder.
 *
 */
public interface CustomEncoder extends PIDSource {
	/**
	 * Gets current count
	 */
	int get();
	
	/**
	 * Gets direction of most recent movement
	 */
	boolean getDirection();
	
	/**
	 * Gets rate
	 */
	double getRate();
	
	/**
	 * Sets the distance per pulse
	 */
	void setDistancePerPulse(double distancePerPulse);
	
	/**
	 * To the surprise of everyone, enables and disables reverse direction
	 */
	void setReverseDirection(boolean reverseDirection);
}
