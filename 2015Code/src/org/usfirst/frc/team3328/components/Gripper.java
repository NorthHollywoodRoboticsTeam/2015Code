package org.usfirst.frc.team3328.components;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

public class Gripper {
	
	private Talon gripperTalon = new Talon(5);
	private GripState currentState;
	
	private enum GripState{ OPEN, CLOSE, HOLD }
	
	Joystick controlStick;
	
	public Gripper(Joystick controlStick) {
		this.controlStick = controlStick;
	}
	
	public void init() {
		
	}
	
	private final double MARGIN_OF_ERROR = 0.1;
	
	public void teleopPeriodic() {
		if (controlStick.getRawButton(1))
			currentState = GripState.OPEN;
		
		if (controlStick.getRawButton(2))
			currentState = GripState.CLOSE;
		
		if (Math.abs(controlStick.getZ()) > -1 + MARGIN_OF_ERROR) {
			switch (currentState) {
			case OPEN:
				gripperTalon.set(1); break;
			case CLOSE:
				gripperTalon.set(-1); break;
			default:
				break;
			}
		} else {
			gripperTalon.set(controlStick.getZ());
		}
	}
	
	public void autonomousPeriodic() {
		
	}
}
