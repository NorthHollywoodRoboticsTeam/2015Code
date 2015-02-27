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
	
	private double talonSpeed = 0;
	public void teleopPeriodic() {
		talonSpeed = 0;
		if (controlStick.getRawButton(11)) {
			talonSpeed = -.9;
		}
		if (controlStick.getRawButton(12)) {
			talonSpeed = .9;
		}
		
		if (controlStick.getRawAxis(3) > .5) {
			talonSpeed += .1;
		}
		gripperTalon.set(talonSpeed);
	}
	
	public void autonomousPeriodic() {
		
	}
}
