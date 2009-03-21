package net.todd.games.boardgame;

import javax.vecmath.Vector3f;

public interface IUserPiecesView {
	void addPiece(Vector3f startingPoint);

	void movePieceTo(Vector3f position);

	IBranchGroup getBranchGroup();
}
