package org.usfirst.frc.team3328.components;

import org.usfirst.frc.team3328.robot.Robot;
import org.usfirst.frc.team3328.util.SplitSpeedController;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;


public class Driver {
	
	private RobotDrive drive;
	
	// Controls both movement + rotation
	private Joystick moveStick;
	
	public Driver(Joystick js) {
		moveStick = js;
		
		//frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor
		drive = new RobotDrive(
				new SplitSpeedController(new Talon(0), new Talon(4)),
				new Talon(2),
				new SplitSpeedController(new Talon(1), new Talon(5)),
				new Talon(3)
			);
	}
	
	public void init() {
		
	}
	
	public void teleopPeriodic() {
		double x = moveStick.getX();
		double y = moveStick.getY();
		
		double magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		// To control the max speed via z axis:
		//double magnitude = moveStick.getZ() * Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		
		// Will compute to a value in the range of -180 to 180 degrees.
		// Y values are reversed on a joystick.
		double direction = Math.toDegrees(Math.atan2(x, -y));
		
		drive.mecanumDrive_Polar(magnitude, direction, moveStick.getTwist());
	}
	
	private boolean forward = true;
	private int counter = 1;
	
	// Test function, moves robot back and forth between two locations.
	// Starts out full speed, slows to a stop.
	
	public void autonomousPeriodic() {
		System.out.println(counter);
		drive.mecanumDrive_Polar((1/counter), forward ? 90 : 270, 0); //magnitude, direction, twist
		Timer.delay(1);
		
		counter++;
		
		if (counter == 5) {
			counter = 1;
			forward = !forward;
		}		
	}
}