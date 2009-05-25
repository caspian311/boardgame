package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.vecmath.Vector3f;

import org.junit.Before;
import org.junit.Test;

public class AvailabilityRuleTest {
	private GameStateStub gameState;

	@Before
	public void setUp() {
		gameState = new GameStateStub();
	}

	@Test
	public void testWillNotMovePieceIfMovingToAPlaceWhereAnotherPieceIs() {
		PieceInfo pieceInfo1 = new PieceInfo();
		pieceInfo1.setId(UUID.randomUUID().toString());
		pieceInfo1.setPosition(new Vector3f(1f, 2f, 3f));
		pieceInfo1.setSpeed(5);
		pieceInfo1.setTeam(Team.ONE);
		PieceInfo pieceInfo2 = new PieceInfo();
		pieceInfo2.setId(UUID.randomUUID().toString());
		pieceInfo2.setPosition(new Vector3f(1f, 2f, 4f));
		pieceInfo2.setSpeed(5);
		pieceInfo2.setTeam(Team.TWO);

		gameState.allPieces.add(pieceInfo1);
		gameState.allPieces.add(pieceInfo2);

		AvailabilityRule rule = new AvailabilityRule(gameState);

		try {
			rule.validateMove(pieceInfo1, new Vector3f(1f, 2f, 4f));
			fail("Invalid move was accepted");
		} catch (ValidMoveException e) {
			assertEquals("Location selected is not available", e.getMessage());
		}

		try {
			rule.validateMove(pieceInfo2, new Vector3f(1f, 2f, 3f));
			fail("Invalid move was accepted");
		} catch (ValidMoveException e) {
			assertEquals("Location selected is not available", e.getMessage());
		}
	}

	@Test
	public void testAPieceCannotMoveToSameLocationThatItAlreadyOccupied() {
		PieceInfo pieceInfo1 = new PieceInfo();
		pieceInfo1.setId(UUID.randomUUID().toString());
		pieceInfo1.setPosition(new Vector3f(1f, 2f, 3f));
		pieceInfo1.setTeam(Team.ONE);

		gameState.allPieces.add(pieceInfo1);

		AvailabilityRule rule = new AvailabilityRule(gameState);

		try {
			rule.validateMove(pieceInfo1, new Vector3f(1f, 2f, 3f));
			fail("Cannot move to location that piece already occupies");
		} catch (ValidMoveException e) {
			assertEquals("Location selected is not available", e.getMessage());
		}
	}

	@Test
	public void testValidMovePassesRule() {
		PieceInfo pieceInfo1 = new PieceInfo();
		pieceInfo1.setId(UUID.randomUUID().toString());
		pieceInfo1.setPosition(new Vector3f(1f, 2f, 3f));
		pieceInfo1.setSpeed(5);
		pieceInfo1.setTeam(Team.ONE);
		PieceInfo pieceInfo2 = new PieceInfo();
		pieceInfo2.setId(UUID.randomUUID().toString());
		pieceInfo2.setPosition(new Vector3f(1f, 2f, 4f));
		pieceInfo2.setSpeed(5);
		pieceInfo2.setTeam(Team.TWO);

		gameState.allPieces.add(pieceInfo1);
		gameState.allPieces.add(pieceInfo2);

		AvailabilityRule rule = new AvailabilityRule(gameState);

		try {
			rule.validateMove(pieceInfo1, new Vector3f(1f, 2f, 5f));
		} catch (ValidMoveException e) {
			fail("Should not have failed: move was valid");
		}
	}

	private static class GameStateStub implements IGameState {
		private final List<PieceInfo> allPieces = new ArrayList<PieceInfo>();

		public List<PieceInfo> getAllPieces() {
			return allPieces;
		}

		public Team getTeamToMove() {
			throw new UnsupportedOperationException();
		}

		public void moveMade(String id, Vector3f targetLocation) {
			throw new UnsupportedOperationException();
		}
	}
}
