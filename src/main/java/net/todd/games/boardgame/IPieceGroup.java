package net.todd.games.boardgame;

import java.util.List;

import javax.vecmath.Vector3f;

public interface IPieceGroup {
	void movePieceAlongPath(List<Vector3f> path);

	PieceInfo getPieceInfo();
}