package org.usfirst.frc.team3328.util;

import edu.wpi.first.wpilibj.SpeedController;

/*
 * Duplicates an incoming signal on a talon and pushes the identical value out of two nodes.
 */
public class SplitSpeedController implements SpeedController {
	
	private SpeedController[] controllers;
	
	public SplitSpeedController(SpeedController... controllers) {
		this.controllers = controllers;
	}

	@Override
	public void pidWrite(double output) {
		for (SpeedController sc : controllers)
			sc.pidWrite(output);
	}

	@Override
	public double get() {
		return controllers[0].get();
	}

	@Override
	public void set(double speed, byte syncGroup) {
		for (SpeedController sc : controllers)
			sc.set(speed, syncGroup);
	}

	@Override
	public void set(double speed) {
		for (SpeedController sc : controllers)
			sc.set(speed);
	}

	@Override
	public void disable() {
		for (SpeedController sc : controllers)
			sc.disable();
	}
}
