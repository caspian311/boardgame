package net.todd.games.boardgame;

import net.todd.common.uitools.IListener;
import net.todd.common.uitools.ListenerManager;

public class UserPieceModel implements IUserPieceModel {
	private static final float PIECE_HEIGHT = 10f;
	private final IGameGridModel gameGridModel;
	private final ListenerManager modelListenerManager = new ListenerManager();
	private float[] currentPosition;

	public UserPieceModel(final IGameGridModel gameGridModel) {
		this.gameGridModel = gameGridModel;
		gameGridModel.addPositionSelectedListener(new IListener() {
			public void fireEvent() {
				currentPosition = gameGridModel.getSelectedPosition();
				adjustCurrentPositionForHeight();
				modelListenerManager.notifyListeners();
			}
		});
	}

	private void adjustCurrentPositionForHeight() {
		if (currentPosition != null) {
			currentPosition[1] = PIECE_HEIGHT / 2;
		}
	}

	public float[] getStartingPoint() {
		float[] gridPosition = gameGridModel.getTeamOneStartingGridPosition();
		gridPosition[1] = PIECE_HEIGHT / 2;
		return gridPosition;
	}

	public void addListener(IListener userModelListener) {
		modelListenerManager.addListener(userModelListener);
	}

	public float[] getCurrentPosition() {
		return currentPosition;
	}
}
