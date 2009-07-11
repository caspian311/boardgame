package net.todd.games.boardgame;

import java.util.Enumeration;

import javax.media.j3d.Behavior;
import javax.media.j3d.Bounds;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnElapsedTime;
import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;
import net.todd.common.uitools.ListenerManager;

public class AnimationBehviour extends Behavior {
	private static final float MOVEMENT_SPEED = 0.1f;
	private static final int ANIMATION_WAITING = 10;
	private final ListenerManager finishedListenerManager = new ListenerManager();
	private final TransformGroup transformGroup;
	private final Vector3f currentLocation;
	private final Vector3f moveToLocation;
	private boolean atLocation;

	public AnimationBehviour(Bounds bounds, TransformGroup transformGroup,
			Vector3f currentLocation, Vector3f moveToLocation) {
		this.transformGroup = transformGroup;
		this.currentLocation = currentLocation;
		this.moveToLocation = moveToLocation;

		setSchedulingBounds(bounds);
	}

	@Override
	public void initialize() {
		wakeupOn(new WakeupOnElapsedTime(10));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void processStimulus(Enumeration stimuli) {
		while (stimuli.hasMoreElements()) {
			WakeupCriterion criterion = (WakeupCriterion) stimuli.nextElement();
			if (criterion instanceof WakeupOnElapsedTime) {
				moveCloser();

				if (!atLocation) {
					wakeupOn(new WakeupOnElapsedTime(ANIMATION_WAITING));
				} else {
					setEnable(false);
					finishedListenerManager.notifyListeners();
				}
			}
		}
	}

	private void moveCloser() {
		Transform3D transformation = new Transform3D();
		transformGroup.getTransform(transformation);

		if (isFinished(currentLocation)) {
			atLocation = true;
		} else {
			updatePosition(currentLocation, moveToLocation);
			transformation.set(currentLocation);
		}

		transformGroup.setTransform(transformation);
	}

	private void updatePosition(Vector3f currentPositionVector, Vector3f endingPointVector) {
		float[] currentPosition = new float[3];
		currentPositionVector.get(currentPosition);
		float[] endingPoint = new float[3];
		endingPointVector.get(endingPoint);

		for (int i = 0; i < currentPosition.length; i++) {
			if (currentPosition[i] < endingPoint[i]) {
				currentPosition[i] += MOVEMENT_SPEED;
			} else if (currentPosition[i] > endingPoint[i]) {
				currentPosition[i] -= 0.1f;
			}
		}

		currentPositionVector.set(currentPosition);
	}

	private boolean isFinished(Vector3f currentPositionVector) {
		boolean finished = false;

		if (currentPositionVector.x <= moveToLocation.x + .1
				&& currentPositionVector.x >= moveToLocation.x - .1) {
			if (currentPositionVector.y <= moveToLocation.y + .1
					&& currentPositionVector.y >= moveToLocation.y - .1) {
				if (currentPositionVector.z <= moveToLocation.z + .1
						&& currentPositionVector.z >= moveToLocation.z - .1) {
					finished = true;
				}
			}
		}

		return finished;
	}

	public void addAnimationFinishedListener(IListener animationFinishedListener) {
		finishedListenerManager.addListener(animationFinishedListener);
	}
}
