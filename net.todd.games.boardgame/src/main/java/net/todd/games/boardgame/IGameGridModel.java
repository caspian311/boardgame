package net.todd.games.boardgame;

import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;

public interface IGameGridModel {
	TileData[][] getTileData();

	void addPositionSelectedListener(IListener listener);

	Vector3f getSelectedPosition();

	void setSelectedTile(TileData tileData);
}
