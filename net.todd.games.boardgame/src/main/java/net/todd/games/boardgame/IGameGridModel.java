package net.todd.games.boardgame;

import net.todd.common.uitools.IListener;

public interface IGameGridModel {
	GameGridData getGridData();

	float[] getTeamOneStartingGridPosition();

	void addPositionSelectedListener(IListener listener);

	float[] getSelectedPosition();

	void setSelectedTile(TileData tileData);
}
