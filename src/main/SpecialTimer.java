package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class SpecialTimer extends Timer {

	private static final long serialVersionUID = 1L;
	private boolean fireAtStart = true;
	private boolean initialFire = false;
	
	public SpecialTimer(int delay, ActionListener listener) {
		super(0, listener);
		setInitialDelay(delay);
		
	}
	
	public void fireTimerWhenStart(boolean fireStart) {
		fireAtStart = fireStart;
	}
	
	public boolean getFireTimerWhenStart() {
		return fireAtStart;
	}
	
	@Override
	public void start() {
		initialFire = false;
		if (fireAtStart) fireActionPerformed(new ActionEvent(this, 0, getActionCommand(),
                System.currentTimeMillis(),
                0));
		initialFire = true;
		// TODO Auto-generated method stub
		super.start();
	}
	
	@Override
	public void stop() {
		initialFire = false;
		// TODO Auto-generated method stub
		super.stop();
	}
	
	@Override
	protected void fireActionPerformed(ActionEvent e) {
//		System.out.println("Fire Action");
//		System.out.println("Fire Start: " + fireAtStart);
//		System.out.println("Initial FIre: " + initialFire);
//		System.out.println();
		if (isRepeats() || !initialFire) super.fireActionPerformed(e);
	}
	
}
