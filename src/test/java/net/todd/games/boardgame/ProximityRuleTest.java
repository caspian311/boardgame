package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Random;

import javax.vecmath.Vector3f;

import org.junit.Before;
import org.junit.Test;

public class ProximityRuleTest {
	private PieceInfo pieceToMove;
	private ProximityRule rule;
	private float maxDistance;

	@Before
	public void setUp() {
		int speed = new Random().nextInt(100);
		if (speed == 0) {
			speed = 1;
		}

		pieceToMove = new PieceInfo();
		pieceToMove.setPosition(new Vector3f(0f, 5f, 0f));
		pieceToMove.setSpeed(speed);
		pieceToMove.setTeam(Team.ONE);

		rule = new ProximityRule();

		maxDistance = pieceToMove.getSpeed() * GameGridData.TILE_SIZE;
	}

	@Test
	public void testWithFurthestValidLocation() {
		try {
			rule.validateMove(pieceToMove, new Vector3f(maxDistance, 5f, 0f));
		} catch (ValidMoveException e) {
			fail("should not have failed");
		}
	}

	@Test
	public void testWithFurthestValidLocationInTheOtherDirection() {
		try {
			rule.validateMove(pieceToMove, new Vector3f(0f, 5f, maxDistance));
		} catch (ValidMoveException e) {
			fail("should not have failed");
		}
	}

	@Test
	public void testCantMoveWhenLocationIsTooFarAway() {
		try {
			rule.validateMove(pieceToMove, new Vector3f(maxDistance + 1f, 5f,
					0f));
			fail("location too far away");
		} catch (ValidMoveException e) {
			assertEquals("Location selected is too far away", e.getMessage());
		}
	}

	@Test
	public void testCantMoveWhenLocationTooFarAwayInDifferentDirection() {
		try {
			rule.validateMove(pieceToMove, new Vector3f(0f, 5f,
					maxDistance + 1f));
			fail("location too far away");
		} catch (ValidMoveException e) {
			assertEquals("Location selected is too far away", e.getMessage());
		}
	}

	@Test
	public void testRandomValidMove() {
		try {
			rule.validateMove(pieceToMove, new Vector3f(maxDistance / 2, 5f,
					maxDistance / 2));
		} catch (ValidMoveException e) {
			fail("should not have failed");
		}
	}

	@Test
	public void testIgnoreHeight() {
		try {
			rule.validateMove(pieceToMove, new Vector3f(maxDistance / 2, 1000f,
					maxDistance / 2));
		} catch (ValidMoveException e) {
			fail("should not have failed");
		}
	}
}
