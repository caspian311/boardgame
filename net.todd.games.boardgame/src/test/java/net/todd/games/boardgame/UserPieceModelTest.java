package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;
import net.todd.common.uitools.ListenerManager;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class UserPieceModelTest {
	private GameGridModelStub gameGridModel;
	private Vector3f currentPieceLocation;
	private GamePieceDataMock gamePieceData;

	@Before
	public void setUp() {
		gameGridModel = new GameGridModelStub();
		currentPieceLocation = null;
		gamePieceData = new GamePieceDataMock();
	}

	@Test
	public void testStartingPositionsAreGottenFromGamePieceDataAndAdjustedForHeightOfPiece() {
		PieceInfo pieceInfo1 = new PieceInfo();
		pieceInfo1.setPosition(new Vector3f(1f, 2f, 3f));
		PieceInfo pieceInfo2 = new PieceInfo();
		pieceInfo2.setPosition(new Vector3f(1f, 2f, 4f));
		gamePieceData.teamOneStartingPositions.add(pieceInfo1);
		gamePieceData.teamTwoStartingPositions.add(pieceInfo2);
		UserPiecesModel userPieceModel = new UserPiecesModel(gamePieceData, gameGridModel);

		List<PieceInfo> allPieces = userPieceModel.getAllTeamOnePieces();
		allPieces.addAll(userPieceModel.getAllTeamTwoPieces());

		assertEquals(2, allPieces.size());
		assertEquals(new Vector3f(1f, 5f, 3f), allPieces.get(0).getPosition());
		assertEquals(new Vector3f(1f, 5f, 4f), allPieces.get(1).getPosition());
	}

	@Test
	public void testUserPieceModelTellsItsListenersThatAPieceShouldMove() {
		UserModelListenerStub userModelListener1 = new UserModelListenerStub();
		UserModelListenerStub userModelListener2 = new UserModelListenerStub();

		IUserPiecesModel userPieceModel = new UserPiecesModel(gamePieceData, gameGridModel);

		userPieceModel.addMoveListener(userModelListener1);
		userPieceModel.addMoveListener(userModelListener2);
		assertFalse(userModelListener1.eventFired);
		assertFalse(userModelListener2.eventFired);
		gameGridModel.positionSelectedListener.notifyListeners();
		assertTrue(userModelListener1.eventFired);
		assertTrue(userModelListener2.eventFired);
	}

	@Test
	public void testUserPieceModelGetsNewPositionFromSelectedTilePositionAdjustedForHeightFromGridThenNotifiesListeners() {
		final IUserPiecesModel userPieceModel = new UserPiecesModel(gamePieceData, gameGridModel);

		userPieceModel.addMoveListener(new IListener() {
			public void fireEvent() {
				currentPieceLocation = userPieceModel.getMoveToLocation();
			}
		});

		gameGridModel.selectedPosition = new Vector3f(new float[] { 1f, 0f, 3f });
		gameGridModel.positionSelectedListener.notifyListeners();
		assertEquals(new Vector3f(new float[] { 1f, 5f, 3f }), currentPieceLocation);
	}

	@Test
	public void testAllAddedPiecesHaveUniqueIdentifiers() {
		for (int i = 0; i < 100; i++) {
			gamePieceData.teamOneStartingPositions.add(new PieceInfo());
		}
		for (int i = 0; i < 100; i++) {
			gamePieceData.teamTwoStartingPositions.add(new PieceInfo());
		}
		UserPiecesModel userPieceModel = new UserPiecesModel(gamePieceData, gameGridModel);
		List<String> uniqueIds = new ArrayList<String>();

		List<PieceInfo> allPieces = userPieceModel.getAllTeamOnePieces();
		allPieces.addAll(userPieceModel.getAllTeamTwoPieces());
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
	public void testWillFireMoveEventIfMovingToANewPlaceWhereNoOtherPieceIs() {
		PieceInfo pieceInfo1 = new PieceInfo();
		pieceInfo1.setPosition(new Vector3f(1f, 2f, 3f));
		PieceInfo pieceInfo2 = new PieceInfo();
		pieceInfo2.setPosition(new Vector3f(1f, 2f, 4f));
		gamePieceData.teamOneStartingPositions.add(pieceInfo1);
		gamePieceData.teamTwoStartingPositions.add(pieceInfo2);
		UserPiecesModel userPieceModel = new UserPiecesModel(gamePieceData, gameGridModel);

		ListenerStub moveListener = new ListenerStub();
		userPieceModel.addMoveListener(moveListener);

		gameGridModel.selectedPosition = new Vector3f(1f, 2f, 5f);
		gameGridModel.positionSelectedListener.notifyListeners();

		assertEquals(1, moveListener.callCount);
	}

	@Test
	@Ignore
	public void testWillNotfireMoveEventIfMovingToAPlaceWhereAnotherPieceIs() {
		PieceInfo pieceInfo1 = new PieceInfo();
		pieceInfo1.setPosition(new Vector3f(1f, 2f, 3f));
		PieceInfo pieceInfo2 = new PieceInfo();
		pieceInfo2.setPosition(new Vector3f(1f, 2f, 4f));
		gamePieceData.teamOneStartingPositions.add(pieceInfo1);
		gamePieceData.teamTwoStartingPositions.add(pieceInfo2);
		UserPiecesModel userPieceModel = new UserPiecesModel(gamePieceData, gameGridModel);

		ListenerStub moveListener = new ListenerStub();
		userPieceModel.addMoveListener(moveListener);

		gameGridModel.selectedPosition = new Vector3f(1f, 2f, 4f);
		gameGridModel.positionSelectedListener.notifyListeners();

		assertEquals(0, moveListener.callCount);
	}

	@Test
	public void testTeamOneStartingPositionIsInCorrectPosition() {
		UserPiecesModel model = new UserPiecesModel(gamePieceData, gameGridModel);
		PieceInfo pieceInfo1 = new PieceInfo();
		pieceInfo1.setPosition(new Vector3f(1, 2, 3));
		PieceInfo pieceInfo2 = new PieceInfo();
		pieceInfo2.setPosition(new Vector3f(4, 6, 6));
		gamePieceData.teamOneStartingPositions.add(pieceInfo1);
		gamePieceData.teamOneStartingPositions.add(pieceInfo2);

		List<PieceInfo> teamOneStartingPositions = model.getAllTeamOnePieces();

		assertEquals(2, teamOneStartingPositions.size());
		assertEquals(new Vector3f(1f, 5f, 3f), teamOneStartingPositions.get(0).getPosition());
		assertEquals(new Vector3f(4f, 5f, 6f), teamOneStartingPositions.get(1).getPosition());
	}

	@Test
	public void testTeamTwoStartingPositionIsInCorrectPosition() {
		UserPiecesModel model = new UserPiecesModel(gamePieceData, gameGridModel);
		PieceInfo pieceInfo1 = new PieceInfo();
		pieceInfo1.setPosition(new Vector3f(4f, 2f, 6f));
		PieceInfo pieceInfo2 = new PieceInfo();
		pieceInfo2.setPosition(new Vector3f(2f, 4f, 1f));
		gamePieceData.teamTwoStartingPositions.add(pieceInfo1);
		gamePieceData.teamTwoStartingPositions.add(pieceInfo2);

		List<PieceInfo> teamTwoStartingPositions = model.getAllTeamTwoPieces();

		assertEquals(2, teamTwoStartingPositions.size());
		assertEquals(new Vector3f(4f, 5f, 6f), teamTwoStartingPositions.get(0).getPosition());
		assertEquals(new Vector3f(2f, 5f, 1f), teamTwoStartingPositions.get(1).getPosition());
	}

	private static class GameGridModelStub implements IGameGridModel {
		Vector3f selectedPosition;
		public ListenerManager positionSelectedListener = new ListenerManager();
		Color3f teamOneColor;
		Color3f teamTwoColor;

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

	private static class ListenerStub implements IListener {
		int callCount;

		public void fireEvent() {
			++callCount;
		}
	}

	private static class GamePieceDataMock extends GamePieceData {
		List<PieceInfo> teamTwoStartingPositions = new ArrayList<PieceInfo>();
		List<PieceInfo> teamOneStartingPositions = new ArrayList<PieceInfo>();

		@Override
		public List<PieceInfo> getTeamOnePieces() {
			return teamOneStartingPositions;
		}

		@Override
		public List<PieceInfo> getTeamTwoPieces() {
			return teamTwoStartingPositions;
		}
	}
}
