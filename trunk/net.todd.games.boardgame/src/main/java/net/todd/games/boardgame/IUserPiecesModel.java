package net.todd.games.boardgame;

import java.util.List;

public interface IUserPiecesModel {
	List<PieceInfo> getAllTeamOnePieces();

	List<PieceInfo> getAllTeamTwoPieces();

	void setSelectedPiece(IPieceGroup selectedPiece);
}
