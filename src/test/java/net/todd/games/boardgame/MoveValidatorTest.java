package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.util.Arrays;

import javax.vecmath.Vector3f;

import org.junit.Before;
import org.junit.Test;

public class MoveValidatorTest {
	private IGameState gameState;
	private IMovementRuleCollection movementRuleCollection;
	private IMoveValidator validator;
	private IRule failingRule;
	private IRule passingRule;

	@Before
	public void setUp() throws ValidMoveException {
		gameState = mock(IGameState.class);
		movementRuleCollection = mock(IMovementRuleCollection.class);

		validator = new MoveValidator(gameState, movementRuleCollection);

		failingRule = mock(IRule.class);
		doThrow(new ValidMoveException("Epic Fail!")).when(failingRule)
				.validateMove(any(PieceInfo.class), any(Vector3f.class));

		passingRule = mock(IRule.class);
	}

	@Test
	public void testWillBlowupIfNullPieceIsGiven() {
		try {
			validator.confirmMove(null, new Vector3f());
			fail("should have failed");
		} catch (ValidMoveException e) {
			assertEquals(
					"Either piece doesn't exist or location to move to doesn't exist",
					e.getMessage());
		}
	}

	@Test
	public void testWillBlowupIfNullTargetIsGiven() {
		try {
			validator.confirmMove(new PieceInfo(), null);
			fail("should have failed");
		} catch (ValidMoveException e) {
			assertEquals(
					"Either piece doesn't exist or location to move to doesn't exist",
					e.getMessage());
		}
	}

	@Test
	public void testWillBlowupIfNeitherPieceOrTargetIsGiven() {
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
		doReturn(Arrays.asList(failingRule)).when(movementRuleCollection)
				.getRules();

		try {
			validator.confirmMove(new PieceInfo(), new Vector3f(0f, 0f, 0f));
			fail("should have failed");
		} catch (ValidMoveException e) {
			assertEquals("Epic Fail!", e.getMessage());
		}
	}

	@Test
	public void testValiatorDoesntFailIfRulesFromCollectionAllPass() {
		doReturn(Arrays.asList(passingRule)).when(movementRuleCollection)
				.getRules();

		try {
			validator.confirmMove(new PieceInfo(), new Vector3f(0f, 0f, 0f));
		} catch (ValidMoveException e) {
			fail("should not have failed");
		}
	}
}
