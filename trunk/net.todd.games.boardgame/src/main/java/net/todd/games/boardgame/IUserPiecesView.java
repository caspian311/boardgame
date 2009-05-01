package net.todd.games.boardgame;

import net.todd.common.uitools.IListener;

public interface IUserPiecesView {
	void addPiece(PieceInfo pieceInfo);

	IBranchGroup getBranchGroup();

	void addPieceSelectedListener(IListener listener);

	IPieceGroup getSelectedPiece();
}
