package org.usfirst.frc.team3328.components;

import edu.wpi.first.wpilibj.*;

//import robotemulator.*;

public class Lifter {

	
	private static final int DOWN_ENCODER_VALUE = -500;
	private static final int MID_ENCODER_VALUE = -250;
	private static final int UP_ENCODER_VALUE = -0;
	private static final int MARGEN_OF_ERROR = 20;
	
	Talon lifterTalon = new Talon(4);
	
	DigitalInput limitSwitch = new DigitalInput(0);  //Pressed must be a 1 vale
	
	Encoder encoder = new Encoder(1, 2); //Encoder + is clockwise when looking down.
	
	Joystick controlStick;
	
	public Lifter(Joystick controlStick) {
		this.controlStick = controlStick;
	}
	
	
	public void init() {
	}
	
	
	//Positive encoder vals mean up
	int lifterDirection = 0;
	int targetPostition = 0;
	public void teleopPeriodic() {
		if (controlStick.getRawButton(1)) {
			targetPostition = DOWN_ENCODER_VALUE;
		}
		if (controlStick.getRawButton(2)) {
			targetPostition = MID_ENCODER_VALUE;
		}
		if (controlStick.getRawButton(3)) {
			targetPostition = UP_ENCODER_VALUE;
		} 
		if (encoder.get() < targetPostition - MARGEN_OF_ERROR) {
			lifterDirection = 1;
		} else if (encoder.get() > targetPostition + MARGEN_OF_ERROR) {
			lifterDirection = -1;
		} else {
			lifterDirection = 0;
		}
		
		//Manual override
		if (controlStick.getRawButton(4)) {
			lifterDirection = -1;
			targetPostition = encoder.get();
		} else if (controlStick.getRawButton(5)) {
			lifterDirection = 1;
			targetPostition = encoder.get();
		}
		
		if (limitSwitch.get()) {
			encoder.reset();
			if (lifterDirection > 0) {
				lifterDirection = 0;
			}
		}
		
		switch (lifterDirection) {
		case -1:
			lifterTalon.set(-1);
			break;
		case 0:
			lifterTalon.set(0);
			break;
		case 1:
			lifterTalon.set(1);
			break;
		default:
			System.out.println("Error: invalid lifter direction: " + lifterDirection);
			lifterTalon.set(0);
		}
		
		
		
	}

	public void autonomousPeriodic() {

	}

}
