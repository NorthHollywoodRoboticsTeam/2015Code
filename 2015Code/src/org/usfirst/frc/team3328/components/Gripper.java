package org.usfirst.frc.team3328.components;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

public class Gripper {
	
	Talon gripperTalon = new Talon(5);
	
	
	Joystick controlStick;
	
	public Gripper(Joystick controlStick) {
		this.controlStick = controlStick;
	}
	
	
	public void init() {
	}
	
	public void teleopPeriodic() {
		
	}
	
	public void autonomousPeriodic() {
		
	}
}
