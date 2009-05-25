package net.todd.games.boardgame;

import javax.vecmath.Vector3f;

public class AvailabilityRule implements IRule {
	private final IGameState gameState;

	public AvailabilityRule(IGameState gameState) {
		this.gameState = gameState;
	}

	public void validateMove(PieceInfo pieceToMove, Vector3f targetLocation)
			throws ValidMoveException {
		for (PieceInfo pieceInfo : gameState.getAllPieces()) {
			if (pieceInfo.getPosition().x == targetLocation.x) {
				if (pieceInfo.getPosition().z == targetLocation.z) {
					throw new ValidMoveException(
							"Location selected is not available");
				}
			}
		}
	}
}
