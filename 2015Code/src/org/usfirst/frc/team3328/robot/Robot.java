package org.usfirst.frc.team3328.robot;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team3328.components.Driver;
import org.usfirst.frc.team3328.components.Gripper;
import org.usfirst.frc.team3328.components.Lifter;
import org.usfirst.frc.team3328.framework.*;

import edu.wpi.first.wpilibj.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	private final Joystick js0, js1;
	
	private final Driver driver;
	private final Lifter lifter;
	private final Gripper gripper;
	
	public Robot() {
		js0 = new Joystick(0);
		js1 = new Joystick(1);
		
		driver = new Driver(js0);
		lifter = new Lifter(js1);
		gripper = new Gripper(js1);
	}
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	driver.init();
    	lifter.init();
    	gripper.init();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	driver.autonomousPeriodic();
    	lifter.autonomousPeriodic();
    	gripper.autonomousPeriodic();
    }
    @Override
    public void disabledInit() {
    	// TODO Auto-generated method stub
    	super.disabledInit();
    	driver.disabledInit();
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	processEvents();
    	driver.teleopPeriodic();
    	lifter.teleopPeriodic();
    	gripper.teleopPeriodic();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    }
    
    public void onButtonPress(int joystickNumber, int buttonNumber, Action action) {
    	final Joystick js = joystickNumber == 0 ? js0 : js1;
    	addPreteriteTrigger(new Predicate() {
			@Override
			public boolean test() {
				return js.getRawButton(buttonNumber);
				
			}}, new StateChangeEvent() {
				@Override
				public void stateChanged(boolean newVal) {
					if (newVal) {
						action.perform();
					}
				}
			});
    }
    
    public void onButtonRelease(int joystickNumber, int buttonNumber, Action action) {
    	final Joystick js = joystickNumber == 0 ? js0 : js1;
    	addPreteriteTrigger(new Predicate() {
			@Override
			public boolean test() {
				return js.getRawButton(buttonNumber);
				
			}}, new StateChangeEvent() {
				@Override
				public void stateChanged(boolean newVal) {
					if (!newVal) {
						action.perform();
					}
				}
			});
    }
    
    public void processEvents() {
    	for (int i = 0; i < activeTriggers.size(); i++) {
    		boolean currentVal = activeTriggers.get(i).p.test();
    		if (currentVal != activeTriggers.get(i).lastVal) {
    			activeTriggers.get(i).lastVal = currentVal;
    			activeTriggers.get(i).e.stateChanged(currentVal);
    		}
    	}
    }
    
    List<PredicatePackage> activeTriggers = new ArrayList<PredicatePackage>();
    
    public void addPreteriteTrigger(Predicate condition, StateChangeEvent event) {
    	activeTriggers.add(new PredicatePackage(condition, event, condition.test()));
    }
    
}
