package net.todd.games.boardgame;

import java.util.Arrays;

import net.todd.common.uitools.IListener;
import net.todd.common.uitools.ListenerManager;

public class UserPieceModel implements IUserPieceModel {
	private static final float PIECE_HEIGHT = 10f;
	private final ListenerManager modelListenerManager = new ListenerManager();
	private float[] currentPosition;

	// private boolean isSelected;

	public UserPieceModel(final IGameGridModel gameGridModel, float[] startingPosition) {
		this.currentPosition = startingPosition;
		 gameGridModel.addPositionSelectedListener(new IListener() {

			public void fireEvent() {
				// if (isSelected) {
					currentPosition = gameGridModel.getSelectedPosition();
					adjustCurrentPositionForHeight();
					modelListenerManager.notifyListeners();
				// }
			}
		});
	}

	private float[] adjustCurrentPositionForHeight() {
		float[] adjustedPosition = null;
		if (currentPosition != null) {
			adjustedPosition = Arrays.copyOf(currentPosition, currentPosition.length);
			if (adjustedPosition != null) {
				adjustedPosition[1] = PIECE_HEIGHT / 2;
			}
		}
		
		return adjustedPosition;
	}

	public void addListener(IListener userModelListener) {
		modelListenerManager.addListener(userModelListener);
	}

	public float[] getCurrentPosition() {
		return adjustCurrentPositionForHeight();
	}

	// public void selected() {
	// isSelected = true;
	// }
}
