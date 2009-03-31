package net.todd.games.boardgame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

public class MoveValidator implements IMoveValidator {
	private Team teamToMove;
	private final Map<String, PieceInfo> allPieces = new HashMap<String, PieceInfo>();

	public MoveValidator(GamePieceData gamePieceData) {
		teamToMove = Team.ONE;
		List<PieceInfo> teamOnePieces = gamePieceData.getTeamOnePieces();
		for (PieceInfo pieceInfo : teamOnePieces) {
			allPieces.put(pieceInfo.getId(), pieceInfo);
		}
		List<PieceInfo> teamTwoPieces = gamePieceData.getTeamTwoPieces();
		for (PieceInfo pieceInfo : teamTwoPieces) {
			allPieces.put(pieceInfo.getId(), pieceInfo);
		}
	}

	public void confirmMove(PieceInfo pieceInfo, Vector3f targetLocation) throws ValidMoveException {
		if (pieceInfo == null || targetLocation == null) {
			throw new ValidMoveException();
		}

		if (!isPiecesTurnToPlay(pieceInfo)) {
			throw new ValidMoveException();
		}
		if (!isLocationOpen(targetLocation)) {
			throw new ValidMoveException();
		}
		if (!isLocationCloseEnough(pieceInfo, targetLocation)) {
			throw new ValidMoveException();
		}
		updatePieceWithMove(pieceInfo, targetLocation);
	}

	private boolean isLocationCloseEnough(PieceInfo pieceInfo, Vector3f targetLocation) {
		Vector3f currentLocation = pieceInfo.getPosition();
		float distance = new Point3f(currentLocation.x, currentLocation.y, currentLocation.z)
				.distance(new Point3f(targetLocation.x, targetLocation.y, targetLocation.z));
		return distance <= GameGridData.TILE_SIZE * 3;
	}

	private void updatePieceWithMove(PieceInfo pieceInfo, Vector3f targetLocation) {
		allPieces.get(pieceInfo.getId()).setPosition(targetLocation);
		updateTeamToMove();
	}

	private void updateTeamToMove() {
		if (teamToMove.equals(Team.ONE)) {
			teamToMove = Team.TWO;
		} else {
			teamToMove = Team.ONE;
		}
	}

	private boolean isLocationOpen(Vector3f targetLocation) {
		boolean open = true;
		for (PieceInfo pieceInfo : allPieces.values()) {
			if (pieceInfo.getPosition().x == targetLocation.x) {
				if (pieceInfo.getPosition().z == targetLocation.z) {
					open = false;
					break;
				}
			}
		}
		return open;
	}

	private boolean isPiecesTurnToPlay(PieceInfo pieceInfo) {
		return teamToMove.equals(pieceInfo.getTeam());
	}
}
