package net.todd.games.boardgame;

import net.todd.common.uitools.IListener;
import net.todd.common.uitools.ListenerManager;

public class GameGridModel implements IGameGridModel {
	private final GameGridData gameGridData;
	private final ListenerManager positionSelectedListenerManager = new ListenerManager();
	private float[] selectedPosition;

	public GameGridModel(GameGridData gameGridData) {
		this.gameGridData = gameGridData;
	}

	public TileData[][] getTileData() {
		return gameGridData.getTileData();
	}

	public float[][] getTeamOneStartingGridPositions() {
		return gameGridData.getTeamOneStartingPositions();
	}

	public void addPositionSelectedListener(IListener listener) {
		positionSelectedListenerManager.addListener(listener);
	}

	public void setSelectedTile(TileData tileData) {
		selectedPosition = tileData.getPosition();
		positionSelectedListenerManager.notifyListeners();
	}

	public float[] getSelectedPosition() {
		return selectedPosition;
	}
}
