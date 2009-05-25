package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.vecmath.Vector3f;

import org.junit.Before;
import org.junit.Test;

public class GameStateTest {
	private GamePieceDataStub gameData;

	@Before
	public void setUp() {
		gameData = new GamePieceDataStub();
	}

	@Test
	public void testAllPiecesPullsFromGameData() {
		int teamOneSize = new Random().nextInt(100);
		int teamTwoSize = new Random().nextInt(100);

		for (int i = 0; i < teamOneSize; i++) {
			gameData.teamOne.add(new PieceInfo());
		}
		for (int i = 0; i < teamTwoSize; i++) {
			gameData.teamTwo.add(new PieceInfo());
		}

		GameState gameState = new GameState(gameData);
		List<PieceInfo> allPieces = gameState.getAllPieces();
		assertEquals(teamOneSize + teamTwoSize, allPieces.size());
	}

	@Test
	public void testFirstTeamToMoveIsTeamOne() {
		GameState gameState = new GameState(gameData);

		assertSame(Team.ONE, gameState.getTeamToMove());
	}

	@Test
	public void testTeamToMoveDoesntChangeIfNoMovesAreMade() {
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

		gameData.teamOne.add(piece1);
		gameData.teamOne.add(piece2);
		gameData.teamTwo.add(piece3);
		gameData.teamTwo.add(piece4);
		GameState gameState = new GameState(gameData);

		for (int i = 0; i < 100; i++) {
			assertSame(Team.ONE, gameState.getTeamToMove());
		}
	}

	@Test
	public void testTeamToMoveAlternatesBetweenTeamsWhenAMoveIsMadeByTheTeamToMoveButStartsWithTeamOne() {
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

		gameData.teamOne.add(piece1);
		gameData.teamOne.add(piece2);
		gameData.teamTwo.add(piece3);
		gameData.teamTwo.add(piece4);
		GameState gameState = new GameState(gameData);

		for (int i = 0; i < 100; i++) {
			if (i % 2 == 0) {
				assertSame(Team.ONE, gameState.getTeamToMove());
			} else {
				assertSame(Team.TWO, gameState.getTeamToMove());
			}

			Vector3f targetLocation = new Vector3f();
			if (i % 2 == 0) {
				gameState.moveMade(piece1.getId(), targetLocation);
			} else {
				gameState.moveMade(piece2.getId(), targetLocation);
			}
		}
	}

	@Test
	public void testGameStateKeepsTrackOfWherePiecesAre() {
		PieceInfo piece1 = new PieceInfo();
		piece1.setId(UUID.randomUUID().toString());
		piece1.setTeam(Team.ONE);
		piece1.setPosition(new Vector3f(0f, 0f, 0f));
		PieceInfo piece2 = new PieceInfo();
		piece2.setId(UUID.randomUUID().toString());
		piece2.setTeam(Team.ONE);
		piece2.setPosition(new Vector3f(1f, 1f, 1f));
		PieceInfo piece3 = new PieceInfo();
		piece3.setId(UUID.randomUUID().toString());
		piece3.setTeam(Team.TWO);
		piece3.setPosition(new Vector3f(2f, 2f, 2f));
		PieceInfo piece4 = new PieceInfo();
		piece4.setId(UUID.randomUUID().toString());
		piece4.setTeam(Team.TWO);
		piece4.setPosition(new Vector3f(3f, 3f, 3f));

		gameData.teamOne.add(piece1);
		gameData.teamOne.add(piece2);
		gameData.teamTwo.add(piece3);
		gameData.teamTwo.add(piece4);
		GameState gameState = new GameState(gameData);

		for (int i = 0; i < 100; i++) {
			Random random = new Random();
			Vector3f targetLocation1 = new Vector3f(random.nextFloat(), random
					.nextFloat(), random.nextFloat());
			gameState.moveMade(piece1.getId(), targetLocation1);
			Vector3f targetLocation2 = new Vector3f(random.nextFloat(), random
					.nextFloat(), random.nextFloat());
			gameState.moveMade(piece2.getId(), targetLocation2);
			Vector3f targetLocation3 = new Vector3f(random.nextFloat(), random
					.nextFloat(), random.nextFloat());
			gameState.moveMade(piece3.getId(), targetLocation3);
			Vector3f targetLocation4 = new Vector3f(random.nextFloat(), random
					.nextFloat(), random.nextFloat());
			gameState.moveMade(piece4.getId(), targetLocation4);

			assertEquals(targetLocation1, getPieceById(piece1.getId(),
					gameState.getAllPieces()).getPosition());
			assertEquals(targetLocation2, getPieceById(piece2.getId(),
					gameState.getAllPieces()).getPosition());
			assertEquals(targetLocation3, getPieceById(piece3.getId(),
					gameState.getAllPieces()).getPosition());
			assertEquals(targetLocation4, getPieceById(piece4.getId(),
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

	private static class GamePieceDataStub implements IGamePieceData {
		private final List<PieceInfo> teamOne = new ArrayList<PieceInfo>();
		private final List<PieceInfo> teamTwo = new ArrayList<PieceInfo>();

		public List<PieceInfo> getTeamOnePieces() {
			return teamOne;
		}

		public List<PieceInfo> getTeamTwoPieces() {
			return teamTwo;
		}
	}
}
