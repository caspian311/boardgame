package net.todd.games.boardgame;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

public interface IPieceGroup {
	void movePieceTo(Vector3f position);

	Color3f getColor();
}