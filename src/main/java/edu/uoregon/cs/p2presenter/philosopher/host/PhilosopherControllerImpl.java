package edu.uoregon.cs.p2presenter.philosopher.host;

import java.io.Serializable;

import com.ryanberdeen.djava.Asynchronous;

import edu.uoregon.cs.p2presenter.interactivity.monitor.Hidden;
import edu.uoregon.cs.p2presenter.philosopher.Chopstick;
import edu.uoregon.cs.p2presenter.philosopher.Philosopher;
import edu.uoregon.cs.p2presenter.philosopher.PhilosopherStateListener;
import edu.uoregon.cs.p2presenter.philosopher.Table;

public class PhilosopherControllerImpl implements Philosopher, Serializable {
	private Table table;

	// XXX supposed to support more than one
	private transient PhilosopherStateListener philosopherStateListener;

	public PhilosopherControllerImpl(Table table) {
		this.table = table;
	}

	public synchronized void reset(Chopstick leftChopstick, Chopstick rightChopstick) {
		leftHand.reset(leftChopstick);
		rightHand.reset(rightChopstick);
		stateChanged();
	}

	private HandImpl leftHand = new HandImpl();
	private HandImpl rightHand = new HandImpl();

	private class HandImpl implements Hand, Serializable {
		private State state = State.EMPTY;
		private Chopstick chopstick;

		public State getState() {
			return state;
		}

		public Chopstick getChopstick() {
			return chopstick;
		}

		public void takeChopstick() {
			synchronized (chopstick) {
				if (state == State.EMPTY) {
					if (!chopstick.isHeld()) {
						doTakeChopstick();
					}
					else {
						state = State.WAITING;
						stateChanged();
						new WaitingHandThread().start();
					}
				}
			}
		}

		private void doTakeChopstick() {
			chopstick.hold(PhilosopherControllerImpl.this);
			state = State.HOLDING;
			stateChanged();
		}

		public synchronized void releaseChopstick() {
			if (state == State.HOLDING) {
				chopstick.release(PhilosopherControllerImpl.this);
				state = State.EMPTY;
				stateChanged();
			}
		}

		private void reset(Chopstick chopstick) {
			this.chopstick = chopstick;
			this.state = State.EMPTY;
		}

		private class WaitingHandThread extends Thread {

			@Override
			public void run() {
				synchronized (chopstick) {
					while (chopstick.isHeld()) {
						try {
							chopstick.wait();
							// let the shopstick sit on the table
							sleep(400);
						}
						catch (InterruptedException ex) {
							// TODO maybe do something
						}
					}

					doTakeChopstick();
				}
			}
		}

	}

	public synchronized State getState() {
		if (leftHand.state == Hand.State.WAITING || rightHand.state == Hand.State.WAITING) {
			return State.WAITING;
		}
		else if (leftHand.state == rightHand.state) {
			switch (leftHand.state) {
			case EMPTY:
				return State.MEDITATING;
			case HOLDING:
				return State.EATING;
			}
		}

		return State.INTERMEDIATE;
	}

	public Hand getLeftHand() {
		return leftHand;
	}

	public Hand getRightHand() {
		return rightHand;
	}

	private void stateChanged() {
		table.philosopherStateChanged(this, getState(), leftHand.getState(), rightHand.getState());

		if (philosopherStateListener != null) {
			this.philosopherStateListener.philosopherStateChanged(this, getState(), leftHand.getState(), rightHand.getState());
		}
	}

	@Hidden
	@Asynchronous
	public void addPhilosopherStateListener(PhilosopherStateListener philosopherStateListener) {
		this.philosopherStateListener = philosopherStateListener;
	}
}
