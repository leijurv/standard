package org.usfirst.frc4904.standard.commands.chassis;


import org.usfirst.frc4904.standard.subsystems.chassis.SolenoidShifters;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command shifts a set of solenoids.
 *
 */
public class ChassisShift extends Command {
	private SolenoidShifters solenoids;
	private SolenoidShifters.ShiftState state;
	
	/**
	 * Shifts the solenoids to the opposite state
	 * 
	 * @param solenoids
	 */
	public ChassisShift(SolenoidShifters solenoids) {
		super("ChassisShift");
		this.solenoids = solenoids;
		requires(solenoids);
		setInterruptible(false);
		state = null;
	}
	
	/**
	 * Shifts the solenoids to the state state
	 * 
	 * @param solenoids
	 * @param state
	 */
	public ChassisShift(SolenoidShifters solenoids, SolenoidShifters.ShiftState state) {
		this(solenoids);
		this.state = state;
	}
	
	protected void initialize() {
		switch (state) {
			case UP:
				solenoids.shift(SolenoidShifters.ShiftState.UP);
				break;
			case DOWN:
				solenoids.shift(SolenoidShifters.ShiftState.DOWN);
				break;
			default:
				solenoids.shift();
		}
	}
	
	protected void execute() {}
	
	protected void interrupted() {}
	
	protected void end() {}
	
	protected boolean isFinished() {
		return true;
	}
}
