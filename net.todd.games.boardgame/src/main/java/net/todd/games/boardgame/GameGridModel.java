package net.todd.games.boardgame;

import net.todd.common.uitools.IListener;
import net.todd.common.uitools.ListenerManager;

public class GameGridModel implements IGameGridModel {
	private GameGridData gameGridData;
	private final ListenerManager positionSelectedListenerManager = new ListenerManager();
	private float[] selectedPosition;

	public GameGridModel() {
		gameGridData = new GameGridData();
	}

	public GameGridData getGridData() {
		return gameGridData;
	}

	public float[] getTeamOneStartingGridPosition() {
		TileData[] firstRow = gameGridData.getTileData()[0];
		TileData centerSquare = firstRow[firstRow.length / 2];
		return centerSquare.getPosition();
	}

	void setGridData(GameGridData gameGridData) {
		this.gameGridData = gameGridData;
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
