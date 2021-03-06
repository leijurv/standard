package org.usfirst.frc4904.standard.commands.chassis;


import org.usfirst.frc4904.standard.subsystems.chassis.SolenoidShifters;
import edu.wpi.first.wpilibj.command.Command;

/**
 * GNDN, just a default command for shifters.
 *
 */
public class ShiftersIdle extends Command {
	/**
	 * GNDN, just a default command for shifters
	 * 
	 * @param shifter
	 *        : the shifter to idle
	 */
	public ShiftersIdle(SolenoidShifters shifter) {
		requires(shifter);
		setInterruptible(true);
	}
	
	protected void initialize() {}
	
	protected void execute() {}
	
	protected void interrupted() {}
	
	protected void end() {}
	
	protected boolean isFinished() {
		return false;
	}
}
