package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

public class TakeTurnsRuleTest {
	private IGameState gameState;
	private PieceInfo pieceFromTeam1;
	private IRule rule;

	@Before
	public void setUp() {
		gameState = mock(IGameState.class);
		
		pieceFromTeam1 = new PieceInfo();
		pieceFromTeam1.setTeam(Team.ONE);
		
		rule = new TakeTurnsRule(gameState);
	}

	@Test
	public void testRulePassesWhenTeamOnesTurnAndPieceFromTeamOneMoves() {
		doReturn(Team.ONE).when(gameState).getTeamToMove();

		try {
			rule.validateMove(pieceFromTeam1, null);
		} catch (ValidMoveException e) {
			fail("Was a valid move");
		}
	}

	@Test
	public void testRuleFailsWhenTeamTwoTurnAndPieceFromTeamOneMoves() {
		doReturn(Team.TWO).when(gameState).getTeamToMove();

		try {
			rule.validateMove(pieceFromTeam1, null);
			fail("invalid move");
		} catch (ValidMoveException e) {
			assertEquals("Not your turn to play", e.getMessage());
		}
	}
}
