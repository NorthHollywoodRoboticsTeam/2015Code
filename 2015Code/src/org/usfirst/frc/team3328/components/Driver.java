package org.usfirst.frc.team3328.components;

import org.usfirst.frc.team3328.robot.Robot;

import robotemulator.Joystick;
import robotemulator.RobotDrive;

//import edu.wpi.first.wpilibj.*;
//import edu.wpi.first.wpilibj.Timer;

public class Driver {
	
	private RobotDrive drive;
	
	// Controls both movement + rotation
	private Joystick moveStick;
	
	public void init() {
		//frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor
		drive = new RobotDrive(1,2,3,4);
		moveStick = Robot.getJs1();
	}
	
	public void teleopPeriodic() {
		double x = moveStick.getX();
		double y = moveStick.getY();
		
		double magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		// To control the max speed via z axis:
		//double magnitude = moveStick.getZ() * Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		
		// Will compute to a value in the range of -180 to 180 degrees.
		double direction = Math.toDegrees(Math.atan2(y, x));
		
		drive.mecanumDrive_Polar(magnitude, direction, moveStick.getTwist());
		//Timer.delay(0.1);
	}
	
	private boolean forward = true;
	private int counter = 1;
	
	// Test function, moves robot back and forth between two locations.
	// Starts out full speed, slows to a stop.
	
	public void autonomousPeriodic() {
		drive.mecanumDrive_Polar((1/counter), forward ? 90 : 270, 0);
		//Timer.delay(0.5);
		
		counter++;
		
		if (counter == 4) {
			counter = 1;
			forward = !forward;
		}
	}
}