package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.vecmath.Vector3f;

import org.junit.Before;
import org.junit.Test;

public class AvailabilityRuleTest {
	private IGameState gameState;
	private IRule rule;
	private PieceInfo pieceInfo1;
	private PieceInfo pieceInfo2;

	@Before
	public void setUp() {
		gameState = mock(IGameState.class);
		
		pieceInfo1 = new PieceInfo();
		pieceInfo1.setId(UUID.randomUUID().toString());
		pieceInfo1.setPosition(new Vector3f(1f, 2f, 3f));
		pieceInfo1.setSpeed(5);
		pieceInfo1.setTeam(Team.ONE);
		pieceInfo2 = new PieceInfo();
		pieceInfo2.setId(UUID.randomUUID().toString());
		pieceInfo2.setPosition(new Vector3f(1f, 2f, 4f));
		pieceInfo2.setSpeed(5);
		pieceInfo2.setTeam(Team.TWO);

		List<PieceInfo> allPieces = Arrays.asList(pieceInfo1, pieceInfo2);
		doReturn(allPieces).when(gameState).getAllPieces();
		
		rule = new AvailabilityRule(gameState);
	}

	@Test
	public void occupiedSpaceIsNotAvailable() {
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
		try {
			rule.validateMove(pieceInfo1, new Vector3f(1f, 2f, 3f));
			fail("Cannot move to location that piece already occupies");
		} catch (ValidMoveException e) {
			assertEquals("Location selected is not available", e.getMessage());
		}
	}

	@Test
	public void testValidMovePassesRule() throws ValidMoveException {
		rule.validateMove(pieceInfo1, new Vector3f(1f, 2f, 5f));
	}
}
