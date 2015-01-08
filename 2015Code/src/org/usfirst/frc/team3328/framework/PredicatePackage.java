package org.usfirst.frc.team3328.framework;

public class PredicatePackage {
	public Predicate p;
	public StateChangeEvent e;
	public boolean lastVal;

	public PredicatePackage(Predicate p, StateChangeEvent e, boolean lastVal) {
		this.p = p;
		this.e = e;
		this.lastVal = lastVal;
	}
}
