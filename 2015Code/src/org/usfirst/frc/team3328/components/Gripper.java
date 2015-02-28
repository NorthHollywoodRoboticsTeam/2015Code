package org.usfirst.frc.team3328.components;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

public class Gripper {
	
	private Talon gripperTalon = new Talon(5);
	
	Joystick controlStick;
	
	public Gripper(Joystick controlStick) {
		this.controlStick = controlStick;
	}
	
	public void init() {
		
	}
	
	public double clamp(double val) {
		if (val > 1) {
			return 1;
		} else if (val < -1) {
			return -1;
		} else {
			return val;
		}
	}
	//Positive talon speeds close gripper.
	private double talonSpeed = 0;
	public void teleopPeriodic() {
		
		talonSpeed = 0;
		if (controlStick.getRawButton(9) || controlStick.getRawButton(1)) {
			talonSpeed = -.9;
		}
		if (controlStick.getRawButton(10) || controlStick.getTrigger()) {
			talonSpeed = .9;
		}
		if (-controlStick.getThrottle() > 0) {
			talonSpeed += .1;
		}
		
		gripperTalon.set(clamp(-talonSpeed));
	}
	
	public void autonomousPeriodic() {
		
	}
}
