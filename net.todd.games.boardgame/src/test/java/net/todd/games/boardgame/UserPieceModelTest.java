package net.todd.games.boardgame;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.todd.common.uitools.IListener;
import net.todd.common.uitools.ListenerManager;

import org.junit.Before;
import org.junit.Test;

public class UserPieceModelTest {
	private GameGridModelStub gameGridModel;
	private float[] currentPieceLocation;

	@Before
	public void setUp() {
		gameGridModel = new GameGridModelStub();
		currentPieceLocation = null;
	}

	@Test
	public void testUserStartingPositionIsGottenFromGridModelButAdjustedForHeightOfPiece() {
		gameGridModel.startingPosition = new float[][] { { 1f, 0f, 1f } };
		IUserPieceModel userPieceModel = new UserPieceModelFactory(gameGridModel)
				.getUserPieceModels().get(0);

		ComparisonUtil.compareArrays(new float[] { 1f, 5f, 1f }, userPieceModel
				.getCurrentPosition());
	}

	// @Test
	// public void testUserPieceDoesNotListensToGameGridWhenItIsNotSelected() {
	// UserModelListenerStub userModelListener1 = new UserModelListenerStub();
	//
	// UserPieceModel userPieceModel = new UserPieceModel(gameGridModel,
	// new float[] { 1f, 0f, 1f });
	//
	// userPieceModel.addListener(userModelListener1);
	// assertFalse(userModelListener1.eventFired);
	// gameGridModel.positionSelectedListener.notifyListeners();
	// assertFalse(userModelListener1.eventFired);
	// }

	@Test
	public void testUserPieceListensToGameGridWhenItIsSelected() {
		UserModelListenerStub userModelListener1 = new UserModelListenerStub();

		UserPieceModel userPieceModel = new UserPieceModel(gameGridModel,
				new float[] { 1f, 0f, 1f });

		userPieceModel.addListener(userModelListener1);
		// userPieceModel.selected();
		gameGridModel.positionSelectedListener.notifyListeners();
		assertTrue(userModelListener1.eventFired);
	}

	@Test
	public void testUserPieceModelTellsItsListenersThatAPieceShouldMove() {
		UserModelListenerStub userModelListener1 = new UserModelListenerStub();
		UserModelListenerStub userModelListener2 = new UserModelListenerStub();
		
		gameGridModel.startingPosition = new float[][] { { 1f, 0f, 1f } };
		IUserPieceModel userPieceModel = new UserPieceModelFactory(gameGridModel)
				.getUserPieceModels().get(0);
		// userPieceModel.selected();
		
		userPieceModel.addListener(userModelListener1);
		userPieceModel.addListener(userModelListener2);
		assertFalse(userModelListener1.eventFired);
		assertFalse(userModelListener2.eventFired);
		gameGridModel.positionSelectedListener.notifyListeners();
		assertTrue(userModelListener1.eventFired);
		assertTrue(userModelListener2.eventFired);
	}

	@Test
	public void testUserPieceModelGetsSelectedTilePositionFromGridModelThenNotifiesListeners() {
		gameGridModel.startingPosition = new float[][] { { 1f, 0f, 1f } };
		final IUserPieceModel userPieceModel = new UserPieceModelFactory(gameGridModel)
				.getUserPieceModels().get(0);
		
		userPieceModel.addListener(new IListener() {
			public void fireEvent() {
				currentPieceLocation = userPieceModel.getCurrentPosition();
			}
		});
		// userPieceModel.selected();

		gameGridModel.selectedPosition = new float[] { 1f, 0f, 3f };
		gameGridModel.positionSelectedListener.notifyListeners();
		ComparisonUtil.compareArrays(new float[] { 1f, 5f, 3f }, currentPieceLocation);
	}

	private static class GameGridModelStub implements IGameGridModel {
		float[] selectedPosition;
		public ListenerManager positionSelectedListener = new ListenerManager();
		float[][] startingPosition;

		public TileData[][] getTileData() {
			throw new UnsupportedOperationException();
		}

		public float[][] getTeamOneStartingGridPositions() {
			return startingPosition;
		}

		public void addPositionSelectedListener(IListener listener) {
			positionSelectedListener.addListener(listener);
		}

		public float[] getSelectedPosition() {
			return selectedPosition;
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
