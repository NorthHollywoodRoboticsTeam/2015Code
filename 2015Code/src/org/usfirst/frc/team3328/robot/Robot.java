package org.usfirst.frc.team3328.robot;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team3328.components.Driver;
import org.usfirst.frc.team3328.framework.*;

import edu.wpi.first.wpilibj.*;
//import robotemulator.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	private static Joystick js1;
	private static Joystick js2;
	private static Joystick js3;
	
	private Driver driver;
	
	public Robot() {
		js1 = new Joystick(1);
		js2 = new Joystick(2);
		js3 = new Joystick(3);
		
		driver = new Driver(js1);
	}
	
	public static Joystick getJs1() {
		return js1;
	}
	
	public static Joystick getJs2() {
		return js2;
	}
	
	public static Joystick getJs3() {
		return js3;
	}
	
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	//driver.init();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	driver.autonomousPeriodic();

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	processEvents();
    	driver.teleopPeriodic();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    }
    
    public void onButtonPress(int joystickNumber, int buttonNumber, Action action) {
    	final Joystick js = joystickNumber == 1 ? js1 : js2;
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
    	final Joystick js = joystickNumber == 1 ? js1 : js2;
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
