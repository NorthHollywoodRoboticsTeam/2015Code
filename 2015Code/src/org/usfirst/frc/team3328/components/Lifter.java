package org.usfirst.frc.team3328.components;

import edu.wpi.first.wpilibj.*;

public class Lifter {
	
	private static final int DOWN_ENCODER_VALUE = -50;//All values here subject to change.
	private static final int ONE_TOTE_DISTANCE = 1000;
	private static final int MARGIN_OF_ERROR = 20;
	
	private static final double DEFAULT_LIFTER_SPEED = .9;
	
	private Talon lifterTalon;
	private DigitalInput limitSwitch;
	private Encoder encoder;
	
	Joystick controlStick;
	
	public Lifter(Joystick controlStick) {
		this.controlStick = controlStick;
		
		lifterTalon = new Talon(4);
		limitSwitch = new DigitalInput(0);  //Pressed must be a 1 value
		encoder = new Encoder(1, 2); //Encoder + is clockwise when looking down.
	}
	
	
	public void init() {
		
	}
	
	//Positive encoder vals mean up
	double lifterDirection = 0;
	int targetPostition = 0;
	
	public void teleopPeriodic() {
		
		System.out.println("encoder val from lifter: " + encoder.get());
		System.out.println("Target position from lifter: " + targetPostition);
		if (controlStick.getRawAxis(6) < -.5) {
			targetPostition -= ONE_TOTE_DISTANCE;
		}
		if (controlStick.getRawAxis(6) > .5) {
			targetPostition += ONE_TOTE_DISTANCE;
		}
		if (controlStick.getRawButton(3) || controlStick.getRawButton(4)) {
			targetPostition = DOWN_ENCODER_VALUE;
		}
		
		if (encoder.get() < targetPostition - MARGIN_OF_ERROR) {
			lifterDirection = DEFAULT_LIFTER_SPEED;
		} else if (encoder.get() > targetPostition + MARGIN_OF_ERROR) {
			lifterDirection = -DEFAULT_LIFTER_SPEED;
		} else {
			lifterDirection = 0;
		}
		
		//Manual override
		if (controlStick.getY() != 0) {
			lifterDirection = controlStick.getY();
		}
		
		if (limitSwitch.get()) {
			encoder.reset();
			if (lifterDirection > 0) {
				lifterDirection = 0;
			}
		}
		
		lifterTalon.set(lifterDirection);
	}

	public void autonomousPeriodic() {

	}

}
