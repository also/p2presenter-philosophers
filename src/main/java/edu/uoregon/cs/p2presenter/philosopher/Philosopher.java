package edu.uoregon.cs.p2presenter.philosopher;

import com.ryanberdeen.djava.Asynchronous;

public interface Philosopher {
	public interface Hand {
		public enum State { HOLDING, WAITING, EMPTY }

		public State getState();

		public Chopstick getChopstick();

		@Asynchronous
		public void takeChopstick();

		@Asynchronous
		public void releaseChopstick();
	}

	public enum State { EATING, WAITING, MEDITATING, INTERMEDIATE, INACTIVE }

	public Hand getLeftHand();
	public Hand getRightHand();

	public State getState();

	public void addPhilosopherStateListener(PhilosopherStateListener philosopherStateListener);
}
