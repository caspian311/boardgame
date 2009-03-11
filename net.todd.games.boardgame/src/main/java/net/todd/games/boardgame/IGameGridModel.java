package net.todd.games.boardgame;

import net.todd.common.uitools.IListener;

public interface IGameGridModel {
	TileData[][] getTileData();

	float[][] getTeamOneStartingGridPositions();

	void addPositionSelectedListener(IListener listener);

	float[] getSelectedPosition();

	void setSelectedTile(TileData tileData);
}
