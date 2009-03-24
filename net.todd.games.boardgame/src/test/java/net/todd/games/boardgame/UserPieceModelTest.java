package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;
import net.todd.common.uitools.ListenerManager;

import org.junit.Before;
import org.junit.Test;

public class UserPieceModelTest {
	private GameGridModelStub gameGridModel;
	private Vector3f currentPieceLocation;

	@Before
	public void setUp() {
		gameGridModel = new GameGridModelStub();
		currentPieceLocation = null;
	}

	@Test
	public void testTeamOneStartingPositionsAreGottenFromGridModelAndAdjustedForHeightOfPiece() {
		gameGridModel.teamOneStartingPositions.add(new Vector3f(new float[] { 1f, 0f, 1f }));
		gameGridModel.teamOneStartingPositions.add(new Vector3f(new float[] { 4f, 10f, 2f }));
		UserPiecesModel userPieceModel = new UserPiecesModel(gameGridModel);

		List<PieceInfo> allPieces = userPieceModel.getAllTeamOnePieces();

		assertEquals(2, allPieces.size());
		assertEquals(new Vector3f(new float[] { 1f, 5f, 1f }), allPieces.get(0).getPosition());
		assertEquals(new Vector3f(new float[] { 4f, 5f, 2f }), allPieces.get(1).getPosition());
	}

	@Test
	public void testTeamOneColorIsBlue() {
		gameGridModel.teamOneStartingPositions.add(new Vector3f());
		gameGridModel.teamOneStartingPositions.add(new Vector3f());
		UserPiecesModel userPieceModel = new UserPiecesModel(gameGridModel);

		List<PieceInfo> allPieces = userPieceModel.getAllTeamOnePieces();
		for (PieceInfo pieceInfo : allPieces) {
			assertEquals(GameColors.TEAM_ONE_COLOR, pieceInfo.getColor());
		}
	}

	@Test
	public void testTeamTwoColorIsRed() {
		gameGridModel.teamOneStartingPositions.add(new Vector3f());
		gameGridModel.teamOneStartingPositions.add(new Vector3f());
		UserPiecesModel userPieceModel = new UserPiecesModel(gameGridModel);

		List<PieceInfo> allPieces = userPieceModel.getAllTeamTwoPieces();
		for (PieceInfo pieceInfo : allPieces) {
			assertEquals(GameColors.TEAM_TWO_COLOR, pieceInfo.getColor());
		}
	}

	@Test
	public void testTeamTwoStartingPositionsAreGottenFromGridModelAndAdjustedForHeightOfPiece() {
		gameGridModel.teamTwoStartingPositions.add(new Vector3f(new float[] { 1f, 0f, 1f }));
		gameGridModel.teamTwoStartingPositions.add(new Vector3f(new float[] { 4f, 0f, 3f }));
		UserPiecesModel userPieceModel = new UserPiecesModel(gameGridModel);

		List<PieceInfo> allPieces = userPieceModel.getAllTeamTwoPieces();

		assertEquals(2, allPieces.size());
		assertEquals(new Vector3f(new float[] { 1f, 5f, 1f }), allPieces.get(0).getPosition());
		assertEquals(new Vector3f(new float[] { 4f, 5f, 3f }), allPieces.get(1).getPosition());
	}

	@Test
	public void testUserPieceModelTellsItsListenersThatAPieceShouldMove() {
		UserModelListenerStub userModelListener1 = new UserModelListenerStub();
		UserModelListenerStub userModelListener2 = new UserModelListenerStub();

		IUserPiecesModel userPieceModel = new UserPiecesModel(gameGridModel);

		userPieceModel.addListener(userModelListener1);
		userPieceModel.addListener(userModelListener2);
		assertFalse(userModelListener1.eventFired);
		assertFalse(userModelListener2.eventFired);
		gameGridModel.positionSelectedListener.notifyListeners();
		assertTrue(userModelListener1.eventFired);
		assertTrue(userModelListener2.eventFired);
	}

	@Test
	public void testUserPieceModelGetsNewPositionFromSelectedTilePositionAdjustedForHeightFromGridThenNotifiesListeners() {
		final IUserPiecesModel userPieceModel = new UserPiecesModel(gameGridModel);

		userPieceModel.addListener(new IListener() {
			public void fireEvent() {
				currentPieceLocation = userPieceModel.getCurrentPosition();
			}
		});

		gameGridModel.selectedPosition = new Vector3f(new float[] { 1f, 0f, 3f });
		gameGridModel.positionSelectedListener.notifyListeners();
		assertEquals(new Vector3f(new float[] { 1f, 5f, 3f }), currentPieceLocation);
	}

	@Test
	public void testTeamOneIsTeamOne() {
		gameGridModel.teamOneStartingPositions.add(new Vector3f());
		gameGridModel.teamOneStartingPositions.add(new Vector3f());
		UserPiecesModel userPieceModel = new UserPiecesModel(gameGridModel);

		List<PieceInfo> allPieces = userPieceModel.getAllTeamOnePieces();
		for (PieceInfo pieceInfo : allPieces) {
			assertEquals(Team.ONE, pieceInfo.getTeam());
		}
	}

	@Test
	public void testTeamTwoIsTeamTwo() {
		gameGridModel.teamTwoStartingPositions.add(new Vector3f());
		gameGridModel.teamTwoStartingPositions.add(new Vector3f());
		UserPiecesModel userPieceModel = new UserPiecesModel(gameGridModel);

		List<PieceInfo> allPieces = userPieceModel.getAllTeamTwoPieces();
		for (PieceInfo pieceInfo : allPieces) {
			assertEquals(Team.TWO, pieceInfo.getTeam());
		}
	}

	private static class GameGridModelStub implements IGameGridModel {
		Vector3f selectedPosition;
		public ListenerManager positionSelectedListener = new ListenerManager();
		List<Vector3f> teamOneStartingPositions = new ArrayList<Vector3f>();
		List<Vector3f> teamTwoStartingPositions = new ArrayList<Vector3f>();
		Color3f teamOneColor;
		Color3f teamTwoColor;

		public List<Vector3f> getTeamTwoStartingGridPositions() {
			return teamTwoStartingPositions;
		}

		public List<Vector3f> getTeamOneStartingGridPositions() {
			return teamOneStartingPositions;
		}

		public Color3f getTeamOneColor() {
			return teamOneColor;
		}

		public Color3f getTeamTwoColor() {
			return teamTwoColor;
		}

		public void addPositionSelectedListener(IListener listener) {
			positionSelectedListener.addListener(listener);
		}

		public Vector3f getSelectedPosition() {
			return selectedPosition;
		}

		public TileData[][] getTileData() {
			throw new UnsupportedOperationException();
		}

		public void setSelectedTile(TileData position) {
			throw new UnsupportedOperationException();
		}
	}

	private static class UserModelListenerStub implements IListener {
		private boolean eventFired;

		public void fireEvent() {
			eventFired = true;
		}
	}
}
