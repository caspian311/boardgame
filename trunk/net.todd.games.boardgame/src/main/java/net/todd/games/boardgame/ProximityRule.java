package net.todd.games.boardgame;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

public class ProximityRule implements IRule {
	public void validateMove(PieceInfo pieceToMove, Vector3f targetLocation)
			throws ValidMoveException {
		Vector3f currentLocation = pieceToMove.getPosition();
		float distance = new Point3f(currentLocation.x, currentLocation.y,
				currentLocation.z).distance(new Point3f(targetLocation.x,
				targetLocation.y, targetLocation.z));
		if (distance > GameGridData.TILE_SIZE * pieceToMove.getSpeed()) {
			throw new ValidMoveException("Location selected is too far away");
		}
	}
}
