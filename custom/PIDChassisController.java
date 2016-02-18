package org.usfirst.frc4904.standard.custom;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.Util;
import org.usfirst.frc4904.standard.custom.motioncontrollers.MotionController;
import org.usfirst.frc4904.standard.custom.sensors.IMU;
import edu.wpi.first.wpilibj.PIDSourceType;

public class PIDChassisController implements ChassisController {
	private ChassisController controller;
	private double maxDegreesPerSecond;
	private double targetYaw;
	private double lastUpdate;
	private IMU imu;
	public MotionController motionController;
	
	public PIDChassisController(ChassisController controller, IMU imu, MotionController motionController, double maxDegreesPerSecond) {
		this.controller = controller;
		this.maxDegreesPerSecond = maxDegreesPerSecond;
		this.imu = imu;
		this.imu.reset();
		this.imu.setPIDSourceType(PIDSourceType.kDisplacement);
		motionController.setInputRange(-180.0f, 180.0f);
		motionController.setOutputRange(-1.0f, 1.0f);
		motionController.setContinuous(true);
		motionController.reset();
		motionController.enable();
		targetYaw = imu.getYaw();
		lastUpdate = (double) System.currentTimeMillis() / 1000.0;
	}
	
	public void reset() {
		targetYaw = imu.getYaw();
		lastUpdate = (double) System.currentTimeMillis() / 1000.0;
		motionController.disable();
		motionController.reset();
		motionController.enable();
	}
	
	@Override
	public double getX() {
		return controller.getX();
	}
	
	@Override
	public double getY() {
		return controller.getY();
	}
	
	@Override
	public double getTurnSpeed() {
		if (Util.isZero(controller.getY()) && Util.isZero(controller.getX())) {
			motionController.setSetpoint(imu.getYaw());
			targetYaw = imu.getYaw();
			return controller.getTurnSpeed();
		}
		LogKitten.v(motionController.getSetpoint() + " " + imu.getYaw(), true);
		targetYaw = targetYaw + ((controller.getTurnSpeed() * maxDegreesPerSecond) * (((double) System.currentTimeMillis() / 1000.0) - lastUpdate));
		lastUpdate = (double) System.currentTimeMillis() / 1000.0;
		if (targetYaw > 180) {
			targetYaw = -180 + (Math.abs(targetYaw) - 180);
		} else if (targetYaw < -180) {
			targetYaw = 180 - (Math.abs(targetYaw) - 180);
		}
		motionController.setSetpoint(targetYaw);
		// LogKitten.d("Total error: " + pid.totalError + ", Raw Yaw: " + ahrs.getRawYaw() + ", Error: " + pid.getError(), true);
		return motionController.get();
	}
}
