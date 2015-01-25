package org.usfirst.frc.team3328.components;

import org.usfirst.frc.team3328.robot.Robot;


import org.usfirst.frc.team3328.util.DoubleSpeedController;

import edu.wpi.first.wpilibj.*;

public class Driver {

	// Controls both movement + rotation
	private Joystick moveStick;
	
	SpeedController frontLeft = new DoubleSpeedController(new Talon(0), new Talon(4)), 
			rearLeft = new Talon(2),
			frontRight = new DoubleSpeedController(new Talon(1), new Talon(5)),
	        rearRight = new Talon(3);
	
	public void init() {
		moveStick = Robot.getJs1();
	}
	
	
	public void mecDrive(double forward, double turnRight, double strafeRight) {
        double FL = clamp(forward + turnRight + strafeRight);
        double RL = clamp(forward + turnRight - strafeRight);
        double FR = clamp(forward - turnRight - strafeRight);
        double RR = clamp(forward - turnRight + strafeRight);
        /*frontLeft.set(FL);
        rearLeft.set(.9*RL);
        frontRight.set(.97*-FR);
        rearRight.set(.9*-RR);*/
        frontLeft.set(-FL);
        rearLeft.set(-RL);
        frontRight.set(FR);
        rearRight.set(RR);
        
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
	
	
	public void teleopPeriodic() {
		double x = moveStick.getX();
		double y = moveStick.getY();
		
		//Old driving using builtin.  Did not work, replaced with hand code from 2014.
		//double magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		// To control the max speed via z axis:
		//double magnitude = moveStick.getZ() * Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		
		// Will compute to a value in the range of -180 to 180 degrees.
		//double direction = Math.toDegrees(Math.atan2(y, x));
		
		mecDrive(y, -moveStick.getTwist(), -x);
	}
	
	private boolean forward = true;
	private int counter = 1;
	
	// Test function, moves robot back and forth between two locations.
	// Starts out full speed, slows to a stop.
	
	public void autonomousPeriodic() {
		//TODO: Drive
		Timer.delay(0.5);
		
		counter++;
		
		if (counter == 4) {
			counter = 1;
			forward = !forward;
		}
	}
}