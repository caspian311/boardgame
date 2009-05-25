package net.todd.games.boardgame;

import javax.vecmath.Vector3f;

public class MoveValidator implements IMoveValidator {
	private final IGameState gameState;
	private final IMovementRuleCollection movementRuleCollection;

	public MoveValidator(IGameState gameState,
			IMovementRuleCollection movementRuleCollection) {
		this.gameState = gameState;
		this.movementRuleCollection = movementRuleCollection;
	}

	public void confirmMove(PieceInfo pieceInfo, Vector3f targetLocation)
			throws ValidMoveException {
		if (pieceInfo == null || targetLocation == null) {
			throw new ValidMoveException(
					"Either piece doesn't exist or location to move to doesn't exist");
		}

		validateMove(pieceInfo, targetLocation);

		updatePieceWithMove(pieceInfo, targetLocation);
	}

	private void validateMove(PieceInfo pieceToMove, Vector3f targetLocation)
			throws ValidMoveException {
		if (!isPiecesTurnToPlay(pieceToMove)) {
			throw new ValidMoveException("Not your turn to play");
		}

		movementRuleCollection.validateMove(pieceToMove, targetLocation);
	}

	private void updatePieceWithMove(PieceInfo pieceInfo,
			Vector3f targetLocation) {
		gameState.moveMade(pieceInfo.getId(), targetLocation);
	}

	private boolean isPiecesTurnToPlay(PieceInfo pieceInfo) {
		return gameState.getTeamToMove().equals(pieceInfo.getTeam());
	}
}
