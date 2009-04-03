package net.todd.games.boardgame;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.vecmath.Vector3f;

import org.junit.Before;
import org.junit.Test;

public class MoveValidatorTest {
	private GamePieceDataMock gamePieceData;

	@Before
	public void setUp() {
		gamePieceData = new GamePieceDataMock();
	}

	@Test
	public void testWillNotMovePieceIfMovingToAPlaceWhereAnotherPieceIs() {
		PieceInfo pieceInfo1 = new PieceInfo();
		pieceInfo1.setId(UUID.randomUUID().toString());
		pieceInfo1.setPosition(new Vector3f(1f, 2f, 3f));
		pieceInfo1.setSpeed(5);
		pieceInfo1.setTeam(Team.ONE);
		PieceInfo pieceInfo2 = new PieceInfo();
		pieceInfo2.setId(UUID.randomUUID().toString());
		pieceInfo2.setPosition(new Vector3f(1f, 2f, 4f));
		pieceInfo2.setSpeed(5);
		pieceInfo2.setTeam(Team.TWO);
		gamePieceData.teamOne.add(pieceInfo1);
		gamePieceData.teamTwo.add(pieceInfo2);
		MoveValidator validator = new MoveValidator(gamePieceData);

		try {
			validator.confirmMove(pieceInfo1, new Vector3f(5f, 5f, 5f));
		} catch (ValidMoveException e) {
			fail("Valid move was not accepted");
		}

		try {
			validator.confirmMove(pieceInfo2, new Vector3f(5f, 5f, 5f));
			fail("Invalid move was accepted");
		} catch (ValidMoveException e) {
		}
	}

	@Test
	public void testWillBlowupIfNoPieceOrTargetIsGiven() {
		MoveValidator validator = new MoveValidator(gamePieceData);

		try {
			validator.confirmMove(null, new Vector3f());
			fail("should have failed");
		} catch (ValidMoveException e) {
		}

		try {
			validator.confirmMove(new PieceInfo(), null);
			fail("should have failed");
		} catch (ValidMoveException e) {
		}

		try {
			validator.confirmMove(null, null);
			fail("should have failed");
		} catch (ValidMoveException e) {
		}
	}

	@Test
	public void testAPieceCannotMoveToSameLocationThatItAlreadyOccupied() {
		PieceInfo pieceInfo1 = new PieceInfo();
		pieceInfo1.setId(UUID.randomUUID().toString());
		pieceInfo1.setPosition(new Vector3f(1f, 2f, 3f));
		pieceInfo1.setTeam(Team.ONE);
		gamePieceData.teamOne.add(pieceInfo1);

		MoveValidator validator = new MoveValidator(gamePieceData);

		try {
			validator.confirmMove(pieceInfo1, new Vector3f(1f, 2f, 3f));
			fail("Cannot move to location that piece already occupies");
		} catch (ValidMoveException e) {
		}
	}

	@Test
	public void testMovesAlternateBetweenTeamsAndTeamOneIsFirstToMove() {
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
		gamePieceData.teamOne.add(teamOnePieceInfo);
		gamePieceData.teamTwo.add(teamTwoPieceInfo);

		MoveValidator validator = new MoveValidator(gamePieceData);

		try {
			validator.confirmMove(teamTwoPieceInfo, new Vector3f(5f, 5f, 5f));
			fail("Team two cannot go first");
		} catch (ValidMoveException e) {
		}

		try {
			validator.confirmMove(teamOnePieceInfo, new Vector3f(5f, 5f, 5f));
		} catch (ValidMoveException e) {
			fail("Team one must go first");
		}

		try {
			validator.confirmMove(teamOnePieceInfo, new Vector3f(1f, 1f, 1f));
			fail("Team two must go second");
		} catch (ValidMoveException e) {
		}

		try {
			validator.confirmMove(teamTwoPieceInfo, new Vector3f(1f, 1f, 1f));
		} catch (ValidMoveException e) {
			fail("Team two must go second");
		}

		try {
			validator.confirmMove(teamTwoPieceInfo, new Vector3f(2f, 2f, 2f));
			fail("Team one must go third");
		} catch (ValidMoveException e) {
		}

		try {
			validator.confirmMove(teamOnePieceInfo, new Vector3f(2f, 2f, 2f));
		} catch (ValidMoveException e) {
			fail("Team one must go third");
		}
	}

	@Test
	public void testPieceCanOnlyMoveADistanceEqualsToItsSpeedTimesTheTileSize() {
		PieceInfo pieceInfo1 = new PieceInfo();
		pieceInfo1.setPosition(new Vector3f(0f, 5f, 0f));
		pieceInfo1.setSpeed(3);
		pieceInfo1.setTeam(Team.ONE);
		gamePieceData.teamOne.add(pieceInfo1);

		MoveValidator validator = new MoveValidator(gamePieceData);

		float maxDistance = pieceInfo1.getSpeed() * GameGridData.TILE_SIZE;

		try {
			validator.confirmMove(pieceInfo1, new Vector3f(maxDistance + 1f, 5f, 0f));
			fail("location too far away");
		} catch (ValidMoveException e) {
		}

		try {
			validator.confirmMove(pieceInfo1, new Vector3f(maxDistance, 5f, 0f));
		} catch (ValidMoveException e) {
			fail("should not have failed");
		}

		validator = new MoveValidator(gamePieceData);

		try {
			validator.confirmMove(pieceInfo1, new Vector3f(maxDistance, 5f, maxDistance + 1f));
			fail("location too far away");
		} catch (ValidMoveException e) {
		}

		try {
			validator.confirmMove(pieceInfo1, new Vector3f(maxDistance, 5f, maxDistance));
		} catch (ValidMoveException e) {
			fail("should not have failed");
		}
	}

	@Test
	public void testWithADifferentSpeedPieceCanOnlyMoveADistanceEqualsToItsSpeedTimesTheTileSize() {
		PieceInfo pieceInfo1 = new PieceInfo();
		pieceInfo1.setPosition(new Vector3f(0f, 5f, 0f));
		pieceInfo1.setSpeed(2);
		pieceInfo1.setTeam(Team.ONE);
		gamePieceData.teamOne.add(pieceInfo1);

		MoveValidator validator = new MoveValidator(gamePieceData);

		float maxDistance = pieceInfo1.getSpeed() * GameGridData.TILE_SIZE;

		try {
			validator.confirmMove(pieceInfo1, new Vector3f(maxDistance + 1f, 5f, 0f));
			fail("location too far away");
		} catch (ValidMoveException e) {
		}

		try {
			validator.confirmMove(pieceInfo1, new Vector3f(maxDistance, 5f, 0f));
		} catch (ValidMoveException e) {
			fail("should not have failed");
		}

		validator = new MoveValidator(gamePieceData);

		try {
			validator.confirmMove(pieceInfo1, new Vector3f(maxDistance, 5f, maxDistance + 1f));
			fail("location too far away");
		} catch (ValidMoveException e) {
		}

		try {
			validator.confirmMove(pieceInfo1, new Vector3f(maxDistance, 5f, maxDistance));
		} catch (ValidMoveException e) {
			fail("should not have failed");
		}
	}

	private static class GamePieceDataMock extends GamePieceData {
		List<PieceInfo> teamTwo = new ArrayList<PieceInfo>();
		List<PieceInfo> teamOne = new ArrayList<PieceInfo>();

		@Override
		public List<PieceInfo> getTeamOnePieces() {
			return teamOne;
		}

		@Override
		public List<PieceInfo> getTeamTwoPieces() {
			return teamTwo;
		}
	}
}
