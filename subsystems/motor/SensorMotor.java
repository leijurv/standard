package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.IdentityModifier;
import org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers.SpeedModifier;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;

public class SensorMotor extends Motor implements PIDOutput {
	protected final PIDController pid;
	private boolean enablePID;
	protected double position;
	protected long lastUpdate;
	
	public SensorMotor(String name, boolean inverted, SpeedModifier slopeController, PIDSource sensor, SpeedController... motors) {
		super(name, inverted, slopeController, motors);
		pid = new PIDController(0.0, 0.0, 0.0, sensor, this);
		pid.setOutputRange(-1.0, 1.0);
	}
	
	public SensorMotor(String name, boolean isInverted, PIDSource sensor, SpeedController... motors) {
		this(name, isInverted, new IdentityModifier(), sensor, motors);
	}
	
	public SensorMotor(String name, SpeedModifier slopeController, PIDSource sensor, SpeedController... motors) {
		this(name, false, slopeController, sensor, motors);
	}
	
	public SensorMotor(String name, PIDSource sensor, SpeedController... motors) {
		this(name, false, new IdentityModifier(), sensor, motors);
	}
	
	public SensorMotor(boolean isInverted, SpeedModifier speedModifier, PIDSource sensor, SpeedController... motors) {
		this("SensorMotor", isInverted, speedModifier, sensor, motors);
	}
	
	public SensorMotor(boolean isInverted, PIDSource sensor, SpeedController... motors) {
		this("SensorMotor", isInverted, sensor, motors);
	}
	
	public SensorMotor(SpeedModifier speedModifier, PIDSource sensor, SpeedController... motors) {
		this("SensorMotor", speedModifier, sensor, motors);
	}
	
	public SensorMotor(PIDSource sensor, SpeedController... motors) {
		this("SensorMotor", sensor, motors);
	}
	
	// TODO this method allows external things to enable() and disable() without our knowledge. We need an alternate solution.
	public PIDController getPIDController() {
		return pid;
	}
	
	public void setPID(double P, double I, double D) {
		pid.setPID(P, I, D);
	}
	
	public void setInputRange(double minimum, double maximum) {
		pid.setInputRange(minimum, maximum);
	}
	
	public void enablePID() {
		enablePID = true;
		pid.enable();
	}
	
	public void disablePID() {
		enablePID = false;
		pid.disable();
	}
	
	public void setPosition(double position) {
		pid.setSetpoint(position);
		pid.enable();
		super.set(pid.get());
	}
	
	public void set(double speed) {
		position += speed * (System.currentTimeMillis() - lastUpdate);
		lastUpdate = System.currentTimeMillis();
		pid.setSetpoint(position);
		if (enablePID) {
			super.set(pid.get());
		} else {
			super.set(speed);
		}
	}
}
