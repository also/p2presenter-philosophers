package edu.uoregon.cs.p2presenter.philosopher.participant;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JButton;

import edu.uoregon.cs.p2presenter.interactivity.InteractivityClientComponent;
import edu.uoregon.cs.p2presenter.philosopher.Philosopher;
import edu.uoregon.cs.p2presenter.philosopher.PhilosopherStateListener;

import java.awt.GridBagConstraints;

public class SimplePhilosopherInterface extends JPanel implements InteractivityClientComponent<Philosopher>, PhilosopherStateListener {

	private static final long serialVersionUID = 1L;
	private JButton takeLeftChopstickButton = null;
	private JButton takeRightChopstickButton = null;
	private JButton releaseRightChopstickButton = null;
	private JButton releaseLeftChopstickButton = null;

	private Philosopher.Hand leftHand;
	private Philosopher.Hand rightHand;

	/**
	 * This is the default constructor
	 */
	public SimplePhilosopherInterface() {
		super();
		initialize();
	}

	public void setModel(Philosopher philosopher) {
		this.leftHand = philosopher.getLeftHand();
		this.rightHand = philosopher.getRightHand();
		philosopher.addPhilosopherStateListener(this);
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 2;
		gridBagConstraints3.gridy = 0;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 2;
		gridBagConstraints2.gridy = 1;
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
		this.add(getTakeLeftChopstickButton(), gridBagConstraints);
		this.add(getReleaseLeftChopstickButton(), gridBagConstraints1);
		this.add(getReleaseRightChopstickButton(), gridBagConstraints2);
		this.add(getTakeRightChopstickButton(), gridBagConstraints3);
	}

	/**
	 * This method initializes takeLeftChopstickButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getTakeLeftChopstickButton() {
		if (takeLeftChopstickButton == null) {
			takeLeftChopstickButton = new JButton();
			takeLeftChopstickButton.setText("Take Left");
			takeLeftChopstickButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					leftHand.takeChopstick();
				}
			});
		}
		return takeLeftChopstickButton;
	}

	/**
	 * This method initializes takeRightChopstickButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getTakeRightChopstickButton() {
		if (takeRightChopstickButton == null) {
			takeRightChopstickButton = new JButton();
			takeRightChopstickButton.setText("Take Right");
			takeRightChopstickButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					rightHand.takeChopstick();
				}
			});
		}
		return takeRightChopstickButton;
	}

	/**
	 * This method initializes releaseRightChopstickButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getReleaseRightChopstickButton() {
		if (releaseRightChopstickButton == null) {
			releaseRightChopstickButton = new JButton();
			releaseRightChopstickButton.setText("Release Right");
			releaseRightChopstickButton
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							rightHand.releaseChopstick();
						}
					});
		}
		return releaseRightChopstickButton;
	}

	/**
	 * This method initializes releaseLeftChopstickButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getReleaseLeftChopstickButton() {
		if (releaseLeftChopstickButton == null) {
			releaseLeftChopstickButton = new JButton();
			releaseLeftChopstickButton.setText("Release Left");
			releaseLeftChopstickButton
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							leftHand.releaseChopstick();
						}
					});
		}
		return releaseLeftChopstickButton;
	}

	public void philosopherStateChanged(Philosopher philosopher, Philosopher.State philosopherState, Philosopher.Hand.State leftHandState, Philosopher.Hand.State rightHandState) {
		updateHandButtons(leftHandState, takeLeftChopstickButton, releaseLeftChopstickButton);
		updateHandButtons(rightHandState, takeRightChopstickButton, releaseRightChopstickButton);
		System.out.println("Philosopher state changed to " + philosopherState);
	}

	private void updateHandButtons(Philosopher.Hand.State state, JButton takeButton, JButton releaseButton) {
		if (state == Philosopher.Hand.State.EMPTY) {
			takeButton.setEnabled(true);
			releaseButton.setEnabled(false);
		}
		else {
			takeButton.setEnabled(false);
			if (state == Philosopher.Hand.State.HOLDING) {
				releaseButton.setEnabled(true);
			}
			else {
				releaseButton.setEnabled(false);
			}
		}
	}

}
