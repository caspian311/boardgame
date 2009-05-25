package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.vecmath.Vector3f;

import org.junit.Before;
import org.junit.Test;

public class MoveValidatorTest {
	private GameStateStub gameState;
	private MovementRuleCollectionStub movementRuleCollection;

	@Before
	public void setUp() {
		gameState = new GameStateStub();
		movementRuleCollection = new MovementRuleCollectionStub();
	}

	@Test
	public void testWillBlowupIfNoPieceOrTargetIsGiven() {
		MoveValidator validator = new MoveValidator(gameState,
				movementRuleCollection);

		try {
			validator.confirmMove(null, new Vector3f());
			fail("should have failed");
		} catch (ValidMoveException e) {
			assertEquals(
					"Either piece doesn't exist or location to move to doesn't exist",
					e.getMessage());
		}

		try {
			validator.confirmMove(new PieceInfo(), null);
			fail("should have failed");
		} catch (ValidMoveException e) {
			assertEquals(
					"Either piece doesn't exist or location to move to doesn't exist",
					e.getMessage());
		}

		try {
			validator.confirmMove(null, null);
			fail("should have failed");
		} catch (ValidMoveException e) {
			assertEquals(
					"Either piece doesn't exist or location to move to doesn't exist",
					e.getMessage());
		}
	}

	@Test
	public void testWhichTeamToMoveIsControlledByGameState() {
		PieceInfo teamOnePieceInfo = new PieceInfo();
		teamOnePieceInfo.setId(UUID.randomUUID().toString());
		teamOnePieceInfo.setPosition(new Vector3f(1f, 2f, 3f));
		teamOnePieceInfo.setTeam(Team.ONE);
		teamOnePieceInfo.setSpeed(5);
		PieceInfo teamTwoPieceInfo = new PieceInfo();
		teamTwoPieceInfo.setId(UUID.randomUUID().toString());
		teamTwoPieceInfo.setPosition(new Vector3f(1f, 2f, 4f));
		teamTwoPieceInfo.setTeam(Team.TWO);
		teamTwoPieceInfo.setSpeed(5);
		gameState.allPieces.add(teamOnePieceInfo);
		gameState.allPieces.add(teamTwoPieceInfo);

		MoveValidator validator = new MoveValidator(gameState,
				movementRuleCollection);

		gameState.teamToMove = Team.ONE;

		try {
			validator.confirmMove(teamTwoPieceInfo, new Vector3f(5f, 5f, 5f));
			fail("Team two cannot go first");
		} catch (ValidMoveException e) {
			assertEquals("Not your turn to play", e.getMessage());
		}

		gameState.teamToMove = Team.TWO;

		try {
			validator.confirmMove(teamTwoPieceInfo, new Vector3f(5f, 5f, 5f));
		} catch (ValidMoveException e) {
			fail("Should have been a valid move");
		}

		gameState.teamToMove = Team.ONE;

		try {
			validator.confirmMove(teamTwoPieceInfo, new Vector3f(5f, 5f, 5f));
			fail("Team two cannot go first");
		} catch (ValidMoveException e) {
			assertEquals("Not your turn to play", e.getMessage());
		}

		gameState.teamToMove = Team.TWO;

		try {
			validator.confirmMove(teamTwoPieceInfo, new Vector3f(5f, 5f, 5f));
		} catch (ValidMoveException e) {
			fail("Should have been a valid move");
		}
	}

	// TODO test that move validator validates against collection of rules

	private static class GameStateStub implements IGameState {
		private final List<PieceInfo> allPieces = new ArrayList<PieceInfo>();
		private Team teamToMove;

		public List<PieceInfo> getAllPieces() {
			return allPieces;
		}

		public Team getTeamToMove() {
			return teamToMove;
		}

		public void moveMade(String id, Vector3f targetLocation) {
		}
	}

	private static class MovementRuleCollectionStub implements
			IMovementRuleCollection {
		public void validateMove(PieceInfo pieceToMove, Vector3f targetLocation)
				throws ValidMoveException {
			// TODO Auto-generated method stub
		}
	}
}
