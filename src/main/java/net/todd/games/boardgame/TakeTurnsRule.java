package net.todd.games.boardgame;

import javax.vecmath.Vector3f;

public class TakeTurnsRule implements IRule {
	private final IGameState gameState;

	public TakeTurnsRule(IGameState gameState) {
		this.gameState = gameState;
	}

	public void validateMove(PieceInfo pieceToMove, Vector3f targetLocation)
			throws ValidMoveException {
		if (!gameState.getTeamToMove().equals(pieceToMove.getTeam())) {
			throw new ValidMoveException("Not your turn to play");
		}
	}
}
