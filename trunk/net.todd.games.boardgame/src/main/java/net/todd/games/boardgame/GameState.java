package net.todd.games.boardgame;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3f;

public class GameState implements IGameState {
	private Team teamToMove;
	private final List<PieceInfo> allPieces;

	public GameState(IGamePieceData gameData) {
		allPieces = new ArrayList<PieceInfo>(gameData.getTeamOnePieces());
		allPieces.addAll(gameData.getTeamTwoPieces());
		teamToMove = Team.ONE;
	}

	public List<PieceInfo> getAllPieces() {
		return allPieces;
	}

	public Team getTeamToMove() {
		return teamToMove;
	}

	public void moveMade(String id, Vector3f targetLocation) {
		PieceInfo piece = getPieceById(id);
		piece.setPosition(targetLocation);
		teamToMove = teamToMove == Team.ONE ? Team.TWO : Team.ONE;
	}

	private PieceInfo getPieceById(String id) {
		PieceInfo target = null;
		for (PieceInfo pieceInfo : allPieces) {
			if (pieceInfo.getId().equals(id)) {
				target = pieceInfo;
				break;
			}
		}
		return target;
	}
}
