package net.todd.games.boardgame;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.todd.common.uitools.IListener;

import org.junit.Before;
import org.junit.Test;

public class UserPieceModelTest {
	private GameGridModelStub gameGridModel;
	private UserPieceModel userPieceModel;
	private float[] currentPieceLocation;

	@Before
	public void setUp() {
		gameGridModel = new GameGridModelStub();
		userPieceModel = new UserPieceModel(gameGridModel);
		currentPieceLocation = null;
	}

	@Test
	public void testUserStartingPositionIsGottenFromGridModelButAdjustedForHeightOfPiece() {
		gameGridModel.startingPosition = new float[] { 1f, 0f, 1f };

		ComparisonUtil.compareArrays(new float[] { 1f, 5f, 1f }, userPieceModel.getStartingPoint());
	}

	@Test
	public void testUserPieceModelTellsItsListenersThatAPieceShouldMove() {
		UserModelListenerStub userModelListener1 = new UserModelListenerStub();
		UserModelListenerStub userModelListener2 = new UserModelListenerStub();
		userPieceModel.addListener(userModelListener1);
		userPieceModel.addListener(userModelListener2);
		assertFalse(userModelListener1.eventFired);
		assertFalse(userModelListener2.eventFired);
		gameGridModel.positionSelectedListener.fireEvent();
		assertTrue(userModelListener1.eventFired);
		assertTrue(userModelListener2.eventFired);
	}

	@Test
	public void testUserPieceModelGetsSelectedTilePositionFromGridModelThenNotifiesListeners() {
		userPieceModel.addListener(new IListener() {
			public void fireEvent() {
				currentPieceLocation = userPieceModel.getCurrentPosition();
			}
		});

		gameGridModel.selectedPosition = new float[] { 1f, 0f, 3f };
		gameGridModel.positionSelectedListener.fireEvent();
		ComparisonUtil.compareArrays(new float[] { 1f, 5f, 3f }, currentPieceLocation);
	}

	private static class GameGridModelStub implements IGameGridModel {
		private float[] selectedPosition;
		public IListener positionSelectedListener;
		private float[] startingPosition;

		public GameGridData getGridData() {
			throw new UnsupportedOperationException();
		}

		public float[] getTeamOneStartingGridPosition() {
			return startingPosition;
		}

		public void addPositionSelectedListener(IListener listener) {
			positionSelectedListener = listener;
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
