package org.usfirst.frc.team3328.components;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;


public class Driver {
	
    private Talon FL, RL, FR, RR;
    private RobotDrive robotDrive;
    private Joystick driveStick;
    private Encoder denc0;
    
    private final double PULSE_TO_INCH_D = 8.19; //The number of pulses per inch for drive. 
    private final double PULSE_TO_INCH_L = 114.60; //The number of pulses per inch for lift.
    private enum RoboState { Forward, Backward, Left, Right, Lift }
	
	public Driver(Joystick driveStick) {
		
		this.driveStick = driveStick;
		
    	//				  FRONT OF ROBOT
    	// Front Left <---- 0        2 ----> Front Right
    	// Rear Left  <---- 1        3 ----> Rear Right
    	//				   REAR OF ROBOT
        FL = new Talon(0);
       	RL = new Talon(1);
       	FR = new Talon(2);
       	RR = new Talon(3);
        
        robotDrive = new RobotDrive(FL,RL,FR,FL); //Drive System - 4 Motors
        denc0 = new Encoder(0,1); //Encoder for motor(s) 0
	}
	
	public void init() {
        robotDrive.setSafetyEnabled(false);
        robotDrive.setExpiration(0.1); //Watchdog timer expiration
        denc0.reset();
	}
	
	public void autonomousPeriodic() {	
        moveAutonomous(RoboState.Forward, 12.0);        
        moveAutonomous(RoboState.Right, 12.0);
        moveAutonomous(RoboState.Backward, 12.0);
        moveAutonomous(RoboState.Left, 12.0);
	}
	
    /** Used to make the motors run and move the robot in mecanum mode
     * and set to run for the provided distance. Currently, this only 
     * utilizes one out of four encoders, due to the limitations of the
     * mecanumDrive class. If individual wheel control is utilized, then 
     * this MUST be modified. 
     * @param state The type of motion desired for the robot. Uses enum values
     * @param distance The distance that the robot is to travel, in inches. 
     */
    private void moveAutonomous(RoboState state, Double distance){
    	switch (state){
    	case Forward: 	denc0.setReverseDirection(false);
    					while(denc0.get() < (int)(distance * PULSE_TO_INCH_D)){
    						robotDrive.mecanumDrive_Polar(.5, 90, 0);
    					}
    					denc0.reset();
    					break;
    	case Backward: 	denc0.setReverseDirection(true);
    					while(denc0.get() < (int)(distance * PULSE_TO_INCH_D)){
    						robotDrive.mecanumDrive_Polar(.5, 270, 0);
    					}
    					denc0.reset();
    					break;
    	case Left: 		denc0.setReverseDirection(true);
						while(denc0.get() < (int)(distance * PULSE_TO_INCH_D)){
							robotDrive.mecanumDrive_Polar(.5, 180, 0);
						}
						denc0.reset();
						break;
    	case Right: 	denc0.setReverseDirection(false);
						while(denc0.get() < (int)(distance * PULSE_TO_INCH_D)){
							robotDrive.mecanumDrive_Polar(.5, 0, 0);
						}
						denc0.reset();
						break;
    	default: 		System.out.println("Received invalid RoboState value.");
    	}
    }
	
	public void teleopPeriodic() {
    	/*****************************************************
    	 * TODO: Add Encoders: 5, PWM INPUT    
    	 * Add Lifter Motors: 4, Talons OUTPUT --Done
    	 * Add Gripper Motor: 1, Spike OUTPUT  --Done
    	 *****************************************************/
        double Yval = driveStick.getY();
        double Xval = -driveStick.getX();
        double Zval = -driveStick.getZ();
        
    	FL.set(-1 * limit(Xval + Yval + Zval));
       	RL.set(-1 * limit(-Xval + Yval +Zval));
       	FR.set(limit(-Xval + Yval - Zval));
       	RR.set(limit(Xval + Yval - Zval));
        Timer.delay(0.005);		// wait for a motor update time
        
        /*
        liftR.set(auxiliaryStick.getY());
        liftL.set(-(auxiliaryStick.getY()));
        
        if(auxiliaryStick.getRawButton(6) && !(auxiliaryStick.getRawButton(4))){
        	grip.set(Relay.Value.kForward);
        }
        
        else if(auxiliaryStick.getRawButton(4) && !(auxiliaryStick.getRawButton(6))){
        	grip.set(Relay.Value.kReverse);
        }
        else{
        	grip.set(Relay.Value.kOff);
        }
        */
	}
	
    private static double limit(double num) {
        if (num > 1.0) {
            return 1.0;
        }
        if (num < -1.0) {
            return -1.0;
        }
        return num;
    }
}