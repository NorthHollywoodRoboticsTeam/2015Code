package org.usfirst.frc.team3328.components;

import edu.wpi.first.wpilibj.*;

public class Lifter {
	
	private static final int DOWN_ENCODER_VALUE = -Integer.MIN_VALUE;//All values here subject to change.
	private static final int ONE_TOTE_DISTANCE = 80;
	private static final int MARGIN_OF_ERROR = 20;
	
	private static final double DEFAULT_LIFTER_SPEED_UP = .5;
	private static final double DEFAULT_LIFTER_SPEED_DOWN = .4;
	
	private Talon lifterTalon;
	private DigitalInput limitSwitch;
	private Encoder encoder;
	
	Joystick controlStick;
	
	public Lifter(Joystick controlStick) {
		this.controlStick = controlStick;
		
		lifterTalon = new Talon(4);
		limitSwitch = new DigitalInput(0);  //Pressed must be a 1 value
		encoder = new Encoder(1, 2); //Encoder + is clockwise when looking down.  Encoder is backwards
	}
	
	
	public void init() {
		
	}
	
	//Positive encoder vals mean up
	double lifterDirection = 0;
	int targetPostition = 0;
	
	public void teleopPeriodic() {
		
		//System.out.println("encoder val from lifter: " + -encoder.get());
		//System.out.println("Target position from lifter: " + targetPostition);
		//System.out.println(controlStick.getPOV());
		if (160 < controlStick.getPOV() && controlStick.getPOV() < 200) {
			targetPostition -= ONE_TOTE_DISTANCE;
			System.out.println("Moving one tote down");
		}
		if (controlStick.getPOV() == 0) {
			System.out.println("Moving one tote up");
			targetPostition += ONE_TOTE_DISTANCE;
		}
		if (controlStick.getRawButton(3) || controlStick.getRawButton(4)) {
			targetPostition = DOWN_ENCODER_VALUE;
		}
		
		
		lifterDirection = 0;
		
		if (-encoder.get() < targetPostition - MARGIN_OF_ERROR) {
			lifterDirection = DEFAULT_LIFTER_SPEED_UP;
		} else if (-encoder.get() > targetPostition + MARGIN_OF_ERROR) {
			lifterDirection = -DEFAULT_LIFTER_SPEED_DOWN;
		} else {
			lifterDirection = 0;
		}
		
		//Manual override
		if (controlStick.getY() > .1 || controlStick.getY() < -.1) {
			double rawVal = -controlStick.getY();
			lifterDirection = rawVal < 0 ? rawVal / 3: rawVal;
			targetPostition = -encoder.get();
			
		}
		
		if (limitSwitch.get()) {
			encoder.reset();
			if (lifterDirection < 0) {
				lifterDirection = 0;
			}
		}
		
		lifterTalon.set(-lifterDirection);
	}

	public void autonomousPeriodic() {

	}

}
