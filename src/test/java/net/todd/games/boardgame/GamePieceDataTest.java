package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3f;

import org.junit.Test;

public class GamePieceDataTest {
	@Test
	public void testAlwaysReturnSameList() {
		GamePieceData gridData = new GamePieceData();

		assertSame(gridData.getTeamOnePieces(), gridData.getTeamOnePieces());
		assertSame(gridData.getTeamTwoPieces(), gridData.getTeamTwoPieces());
	}

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

	@Test
	public void testAllAddedPiecesHaveUniqueIdentifiers() {
		GamePieceData gamePieceData = new GamePieceData();
		List<String> uniqueIds = new ArrayList<String>();

		List<PieceInfo> allPieces = gamePieceData.getTeamOnePieces();
		allPieces.addAll(gamePieceData.getTeamTwoPieces());
		for (PieceInfo pieceInfo : allPieces) {
			String id = pieceInfo.getId();
			if (!uniqueIds.contains(id)) {
				uniqueIds.add(id);
			} else {
				fail("ids are not unique");
			}
		}
	}

	@Test
	public void testAllPiecesHaveASpeedOf2() {
		GamePieceData gamePieceData = new GamePieceData();

		List<PieceInfo> allPieces = gamePieceData.getTeamOnePieces();
		allPieces.addAll(gamePieceData.getTeamTwoPieces());
		for (PieceInfo pieceInfo : allPieces) {
			assertEquals(2f, pieceInfo.getSpeed());
		}
	}
}
