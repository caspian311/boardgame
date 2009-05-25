package net.todd.games.boardgame;

import javax.vecmath.Vector3f;

public interface IMovementRuleCollection {

	void validateMove(PieceInfo pieceToMove, Vector3f targetLocation)
			throws ValidMoveException;

}
