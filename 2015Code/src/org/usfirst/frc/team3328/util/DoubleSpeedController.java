package org.usfirst.frc.team3328.util;

import edu.wpi.first.wpilibj.*;

public class DoubleSpeedController implements SpeedController {
	private SpeedController sp1, sp2;
	public DoubleSpeedController(SpeedController sp1, SpeedController sp2) {
		this.sp1 = sp1;
		this.sp2 = sp2;
	}

	@Override
	public void pidWrite(double output) {
		sp1.pidWrite(output);
		sp2.pidWrite(output);
	}

	@Override
	public double get() {
		
		return sp1.get();
	}

	@Override
	public void set(double speed, byte syncGroup) {
		sp1.set(speed, syncGroup);
		sp2.set(speed, syncGroup);
		
	}

	@Override
	public void set(double speed) {
		sp1.set(speed);
		sp2.set(speed);
	}

	@Override
	public void disable() {
		sp1.disable();
		sp2.disable();
		
	}

}
