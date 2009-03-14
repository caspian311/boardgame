package net.todd.games.boardgame;

import java.util.List;

import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;
import net.todd.common.uitools.ListenerManager;

public class GameGridModel implements IGameGridModel {
	private final GameGridData gameGridData;
	private final ListenerManager positionSelectedListenerManager = new ListenerManager();
	private Vector3f selectedPosition;

	public GameGridModel(GameGridData gameGridData) {
		this.gameGridData = gameGridData;
	}

	public TileData[][] getTileData() {
		return gameGridData.getTileData();
	}

	public List<Vector3f> getTeamOneStartingGridPositions() {
		return gameGridData.getTeamOneStartingPositions();
	}

	public void addPositionSelectedListener(IListener listener) {
		positionSelectedListenerManager.addListener(listener);
	}

	public void setSelectedTile(TileData tileData) {
		selectedPosition = new Vector3f(tileData.getPosition());
		positionSelectedListenerManager.notifyListeners();
	}

	public Vector3f getSelectedPosition() {
		return selectedPosition;
	}
}
