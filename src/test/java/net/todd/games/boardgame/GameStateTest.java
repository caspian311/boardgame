package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.vecmath.Vector3f;

import org.junit.Before;
import org.junit.Test;

public class GameStateTest {
	private IGamePieceData gameData;
	private IGameState gameState;
	private List<PieceInfo> teamOnePieces;
	private List<PieceInfo> teamTwoPieces;

	@Before
	public void setUp() {
		gameData = mock(IGamePieceData.class);
		
		teamOnePieces = new ArrayList<PieceInfo>();
		teamTwoPieces = new ArrayList<PieceInfo>();
		
		PieceInfo piece1 = new PieceInfo();
		piece1.setId(UUID.randomUUID().toString());
		piece1.setTeam(Team.ONE);
		PieceInfo piece2 = new PieceInfo();
		piece2.setId(UUID.randomUUID().toString());
		piece2.setTeam(Team.ONE);
		PieceInfo piece3 = new PieceInfo();
		piece3.setId(UUID.randomUUID().toString());
		piece3.setTeam(Team.TWO);
		
		PieceInfo piece4 = new PieceInfo();
		piece4.setId(UUID.randomUUID().toString());
		piece4.setTeam(Team.TWO);
		
		teamOnePieces.add(piece1);
		teamOnePieces.add(piece2);
		teamOnePieces.add(piece3);

		teamTwoPieces.add(piece4);

		doReturn(teamOnePieces).when(gameData).getTeamOnePieces();
		doReturn(teamTwoPieces).when(gameData).getTeamTwoPieces();
		
		gameState = new GameState(gameData);
	}

	@Test
	public void testAllPiecesPullsFromGameData() {
		assertEquals(4, gameState.getAllPieces().size());
	}

	@Test
	public void testFirstTeamToMoveIsTeamOne() {
		assertSame(Team.ONE, gameState.getTeamToMove());
	}

	@Test
	public void testTeamToMoveDoesntChangeIfNoMovesAreMade() {
		for (int i = 0; i < 100; i++) {
			assertSame(Team.ONE, gameState.getTeamToMove());
		}
	}

	@Test
	public void testTeamToMoveAlternatesBetweenTeamsWhenAMoveIsMadeByTheTeamToMoveButStartsWithTeamOne() {
		for (int i = 0; i < 100; i++) {
			if (i % 2 == 0) {
				assertSame(Team.ONE, gameState.getTeamToMove());
			} else {
				assertSame(Team.TWO, gameState.getTeamToMove());
			}

			Vector3f targetLocation = new Vector3f();
			if (i % 2 == 0) {
				gameState.moveMade(teamOnePieces.get(0).getId(), targetLocation);
			} else {
				gameState.moveMade(teamTwoPieces.get(0).getId(), targetLocation);
			}
		}
	}

	@Test
	public void testGameStateKeepsTrackOfWherePiecesAre() {
		for (int i = 0; i < 100; i++) {
			Random random = new Random();
			Vector3f targetLocation1 = new Vector3f(random.nextFloat(), random
					.nextFloat(), random.nextFloat());
			gameState.moveMade(teamOnePieces.get(0).getId(), targetLocation1);
			Vector3f targetLocation2 = new Vector3f(random.nextFloat(), random
					.nextFloat(), random.nextFloat());
			gameState.moveMade(teamOnePieces.get(1).getId(), targetLocation2);
			Vector3f targetLocation3 = new Vector3f(random.nextFloat(), random
					.nextFloat(), random.nextFloat());
			gameState.moveMade(teamOnePieces.get(2).getId(), targetLocation3);
			Vector3f targetLocation4 = new Vector3f(random.nextFloat(), random
					.nextFloat(), random.nextFloat());
			gameState.moveMade(teamTwoPieces.get(0).getId(), targetLocation4);

			assertEquals(targetLocation1, getPieceById(teamOnePieces.get(0).getId(),
					gameState.getAllPieces()).getPosition());
			assertEquals(targetLocation2, getPieceById(teamOnePieces.get(1).getId(),
					gameState.getAllPieces()).getPosition());
			assertEquals(targetLocation3, getPieceById(teamOnePieces.get(2).getId(),
					gameState.getAllPieces()).getPosition());
			assertEquals(targetLocation4, getPieceById(teamTwoPieces.get(0).getId(),
					gameState.getAllPieces()).getPosition());
		}
	}

	private PieceInfo getPieceById(String id, List<PieceInfo> allPieces) {
		PieceInfo target = null;
		for (PieceInfo pieceInfo : allPieces) {
			if (pieceInfo.getId().equals(id)) {
				target = pieceInfo;
				break;
			}
		}
		return target;
	}
}
