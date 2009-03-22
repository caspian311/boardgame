package net.todd.games.boardgame;

import javax.vecmath.Vector3f;

public interface IUserPiecesView {
	void addPiece(PieceInfo pieceInfo);

	void movePieceTo(Vector3f position);

	IBranchGroup getBranchGroup();
}
