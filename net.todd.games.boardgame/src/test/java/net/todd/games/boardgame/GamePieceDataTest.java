package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.vecmath.Vector3f;

import org.junit.Test;

public class GamePieceDataTest {
	@Test
	public void testCoordinatesForTeamOneStartingPositions() {
		GamePieceData gridData = new GamePieceData();

		List<PieceInfo> teamOne = gridData.getTeamOnePieces();

		assertEquals(4, teamOne.size());
		assertEquals(new Vector3f(-15f, 0f, -35f), teamOne.get(0).getPosition());
		assertEquals(new Vector3f(-5f, 0f, -35f), teamOne.get(1).getPosition());
		assertEquals(new Vector3f(5f, 0f, -35f), teamOne.get(2).getPosition());
		assertEquals(new Vector3f(15f, 0f, -35f), teamOne.get(3).getPosition());
	}

	@Test
	public void testCoordinatesForTeamTwoStartingPositions() {
		GamePieceData pieceData = new GamePieceData();

		List<PieceInfo> teamTwo = pieceData.getTeamTwoPieces();

		assertEquals(4, teamTwo.size());
		assertEquals(new Vector3f(-15f, 0f, 35f), teamTwo.get(0).getPosition());
		assertEquals(new Vector3f(-5f, 0f, 35f), teamTwo.get(1).getPosition());
		assertEquals(new Vector3f(5f, 0f, 35f), teamTwo.get(2).getPosition());
		assertEquals(new Vector3f(15f, 0f, 35f), teamTwo.get(3).getPosition());
	}

	@Test
	public void testTeamOneColorIsBlue() {
		GamePieceData gamePieceData = new GamePieceData();

		List<PieceInfo> allPieces = gamePieceData.getTeamOnePieces();
		for (PieceInfo pieceInfo : allPieces) {
			assertEquals(GameColors.TEAM_ONE_COLOR, pieceInfo.getColor());
		}
	}

	@Test
	public void testTeamTwoColorIsRed() {
		GamePieceData gamePieceData = new GamePieceData();

		List<PieceInfo> allPieces = gamePieceData.getTeamTwoPieces();
		for (PieceInfo pieceInfo : allPieces) {
			assertEquals(GameColors.TEAM_TWO_COLOR, pieceInfo.getColor());
		}
	}

	@Test
	public void testTeamOneIsTeamOne() {
		GamePieceData gamePieceData = new GamePieceData();

		List<PieceInfo> allPieces = gamePieceData.getTeamOnePieces();
		for (PieceInfo pieceInfo : allPieces) {
			assertEquals(Team.ONE, pieceInfo.getTeam());
		}
	}

	@Test
	public void testTeamTwoIsTeamTwo() {
		GamePieceData gamePieceData = new GamePieceData();

		List<PieceInfo> allPieces = gamePieceData.getTeamTwoPieces();
		for (PieceInfo pieceInfo : allPieces) {
			assertEquals(Team.TWO, pieceInfo.getTeam());
		}
	}
}
