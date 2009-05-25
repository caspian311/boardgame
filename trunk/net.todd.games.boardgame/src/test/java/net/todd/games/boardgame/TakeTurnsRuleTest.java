package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import javax.vecmath.Vector3f;

import org.junit.Before;
import org.junit.Test;

public class TakeTurnsRuleTest {
	private GameStateStub gameState;
	private PieceInfo pieceFromTeam1;

	@Before
	public void setUp() {
		gameState = new GameStateStub();
		pieceFromTeam1 = new PieceInfo();
		pieceFromTeam1.setTeam(Team.ONE);
	}

	@Test
	public void testRulePassesWhenTeamOnesTurnAndPieceFromTeamOneMoves() {
		TakeTurnsRule rule = new TakeTurnsRule(gameState);

		gameState.teamToMove = Team.ONE;

		try {
			rule.validateMove(pieceFromTeam1, null);
		} catch (ValidMoveException e) {
			fail("Was a valid move");
		}
	}

	@Test
	public void testRuleFailsWhenTeamTwoTurnAndPieceFromTeamOneMoves() {
		TakeTurnsRule rule = new TakeTurnsRule(gameState);

		gameState.teamToMove = Team.TWO;

		try {
			rule.validateMove(pieceFromTeam1, null);
			fail("invalid move");
		} catch (ValidMoveException e) {
			assertEquals("Not your turn to play", e.getMessage());
		}
	}

	private static class GameStateStub implements IGameState {
		private Team teamToMove;

		public List<PieceInfo> getAllPieces() {
			throw new UnsupportedOperationException();
		}

		public Team getTeamToMove() {
			return teamToMove;
		}

		public void moveMade(String id, Vector3f targetLocation) {
			throw new UnsupportedOperationException();
		}
	}
}
