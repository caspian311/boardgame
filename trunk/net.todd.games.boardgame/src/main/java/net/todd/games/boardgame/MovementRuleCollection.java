package net.todd.games.boardgame;

import java.util.Arrays;
import java.util.List;

import javax.vecmath.Vector3f;

public class MovementRuleCollection implements IMovementRuleCollection {
	private final List<IRule> movementRules;

	public MovementRuleCollection(IRule... rules) {
		movementRules = Arrays.asList(rules);
	}

	public void validateMove(PieceInfo pieceToMove, Vector3f targetLocation)
			throws ValidMoveException {
		for (IRule rule : movementRules) {
			rule.validateMove(pieceToMove, targetLocation);
		}
	}
}
