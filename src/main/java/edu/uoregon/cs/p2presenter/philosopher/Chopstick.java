package edu.uoregon.cs.p2presenter.philosopher;

import java.io.Serializable;

public class Chopstick implements Serializable {
	private Philosopher heldBy;

	public boolean isHeld() {
		return heldBy != null;
	}

	public synchronized void hold(Philosopher philosopher) {
		if (heldBy != null) {
			throw new IllegalStateException("Chopstick is already being held");
		}
		heldBy = philosopher;
	}

	public synchronized void release(Philosopher philosopher) {
		if (philosopher != heldBy) {
			throw new IllegalStateException("Philosopher is not holding chopstick");
		}

		heldBy = null;
		notify();
	}
}
