package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3f;

import org.junit.Before;
import org.junit.Test;

public class MoveValidatorTest {
	private GameStateStub gameState;
	private IMovementRuleCollection movementRuleCollection;

	@Before
	public void setUp() {
		gameState = new GameStateStub();
		movementRuleCollection = new MovementRuleCollection();
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
	public void testValiatorFailsIfRuleFromCollectionFails() {
		MoveValidator validator = new MoveValidator(gameState,
				movementRuleCollection);

		movementRuleCollection.addRule(new IRule() {
			public void validateMove(PieceInfo pieceToMove,
					Vector3f targetLocation) throws ValidMoveException {
				throw new ValidMoveException("Epic Fail!");
			}
		});

		try {
			validator.confirmMove(new PieceInfo(), new Vector3f(0f, 0f, 0f));
			fail("should have failed");
		} catch (ValidMoveException e) {
			assertEquals("Epic Fail!", e.getMessage());
		}
	}

	@Test
	public void testValiatorDoesntFailIfRulesFromCollectionAllPass() {
		MoveValidator validator = new MoveValidator(gameState,
				movementRuleCollection);

		movementRuleCollection.addRule(new IRule() {
			public void validateMove(PieceInfo pieceToMove,
					Vector3f targetLocation) throws ValidMoveException {
			}
		});
		movementRuleCollection.addRule(new IRule() {
			public void validateMove(PieceInfo pieceToMove,
					Vector3f targetLocation) throws ValidMoveException {
			}
		});

		try {
			validator.confirmMove(new PieceInfo(), new Vector3f(0f, 0f, 0f));
		} catch (ValidMoveException e) {
			fail("should not have failed");
		}
	}

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
}
