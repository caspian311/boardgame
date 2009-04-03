package net.todd.games.boardgame;

import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;

public interface IGameGridModel {
	TileData[][] getTileData();

	void addTileSelectedListener(IListener listener);

	Vector3f getSelectedTileLocation();

	void setSelectedTile(TileData tileData);

	void setSelectedUserPiece(PieceInfo pieceInfo);

	void addUserPieceSelectedListener(IListener listener);

	TileData[] getTilesToHighlight();
}
