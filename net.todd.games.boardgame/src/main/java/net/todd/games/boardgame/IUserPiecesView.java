package net.todd.games.boardgame;

import javax.media.j3d.Node;
import javax.vecmath.Vector3f;

public interface IUserPiecesView {
	void addPiece(Vector3f startingPoint);

	void movePieceTo(Vector3f position);

	Node getBranchGroup();
}
