package net.todd.games.boardgame;

import java.util.List;

public interface IGamePieceData {
	List<PieceInfo> getTeamOnePieces();

	List<PieceInfo> getTeamTwoPieces();
}
