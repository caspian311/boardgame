package net.todd.games.boardgame;

import java.util.List;

import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;

public interface IGameGridModel {
	TileData[][] getTileData();

	List<Vector3f> getTeamOneStartingGridPositions();

	List<Vector3f> getTeamTwoStartingGridPositions();

	void addPositionSelectedListener(IListener listener);

	Vector3f getSelectedPosition();

	void setSelectedTile(TileData tileData);
}
