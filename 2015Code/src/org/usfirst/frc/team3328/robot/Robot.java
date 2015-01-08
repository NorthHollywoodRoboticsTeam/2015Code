
package org.usfirst.frc.team3328.robot;


import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team3328.framework.*;

//import edu.wpi.first.wpilibj.*;
import robotemulator.*;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	Joystick js1 = new Joystick(1);
	Joystick js2 = new Joystick(2);
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	processEvents();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
    public void onButtonPress(int joystickNumber, int buttonNumber, Action action) {
    	final Joystick js = joystickNumber == 1 ? js1 : js2;
    	addPreterateTrigger(new Predicate() {
			@Override
			public boolean test() {
				return js.getRawButton(buttonNumber);
				
			}}, new StateChangeEvent() {
				@Override
				public void sateChanged(boolean newVal) {
					if (newVal) {
						action.perform();
					}
				}
			});
    }
    
    public void onButtonRelease(int joystickNumber, int buttonNumber, Action action) {
    	final Joystick js = joystickNumber == 1 ? js1 : js2;
    	addPreterateTrigger(new Predicate() {
			@Override
			public boolean test() {
				return js.getRawButton(buttonNumber);
				
			}}, new StateChangeEvent() {
				@Override
				public void sateChanged(boolean newVal) {
					if (!newVal) {
						action.perform();
					}
				}
			});
    }
    
    public void processEvents() {
    	for (int i = 0; i < activeTrigers.size(); i++) {
    		boolean cuurentVal = activeTrigers.get(i).p.test();
    		if (cuurentVal != activeTrigers.get(i).lastVal) {
    			activeTrigers.get(i).lastVal = cuurentVal;
    			activeTrigers.get(i).e.sateChanged(cuurentVal);
    		}
    	}
    }
    
    List<PredicatePackage> activeTrigers = new ArrayList<PredicatePackage>();
    public void addPreterateTrigger(Predicate condition, StateChangeEvent event) {
    	activeTrigers.add(new PredicatePackage(condition, event, condition.test()));
    }
    
}
