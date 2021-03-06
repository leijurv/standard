package org.usfirst.frc4904.standard.subsystems.motor;


import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * An encoded motor is a motor with a set
 * of variables relevant to controlling
 * a motor with an encoder. It contains
 * an Encoder, PID constants, and range
 * information.
 *
 */
public class EncodedMotor extends Motor {
	protected Encoder encoder;
	protected double P;
	protected double I;
	protected double D;
	protected double maximum;
	protected double minimum;
	
	public EncodedMotor(String name, SpeedController motor, Encoder encoder, double P, double I, double D, double maximum, double minimum, double distancePerPulse, boolean inverted) {
		super(name, motor, inverted);
		this.encoder = encoder;
		this.P = P;
		this.I = I;
		this.D = D;
		this.maximum = maximum;
		this.minimum = minimum;
		encoder.setDistancePerPulse(distancePerPulse);
	}
	
	public EncodedMotor(String name, SpeedController motor, Encoder encoder, double P, double I, double D, double maximum, double minimum, double distancePerPulse) {
		this(name, motor, encoder, P, I, D, maximum, minimum, distancePerPulse, false);
	}
	
	public Encoder getEncoder() {
		return encoder;
	}
	
	public double getP() {
		return P;
	}
	
	public double getI() {
		return I;
	}
	
	public double getD() {
		return D;
	}
	
	public double getMinimum() {
		return minimum;
	}
	
	public double getMaximum() {
		return maximum;
	}
	
	public void setDistancePerPulse(double distancePerPulse) {
		encoder.setDistancePerPulse(distancePerPulse);
	}
}
