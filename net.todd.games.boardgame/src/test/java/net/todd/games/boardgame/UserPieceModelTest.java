package net.todd.games.boardgame;

import org.junit.Before;
import org.junit.Test;

public class UserPieceModelTest {
	private GameGridModelStub gameGridModel;

	@Before
	public void setUp() {
		gameGridModel = new GameGridModelStub();
	}

	@Test
	public void testUserStartingPositionIsGottenFromGridModelButAdjustedForHeightOfPiece() {
		gameGridModel.startingPosition = new float[] { 1f, 0f, 1f };
		UserPieceModel model = new UserPieceModel(gameGridModel);
		TestUtil.compareArrays(new float[] { 1f, 5f, 1f }, model.getStartingPoint());
	}

	private static class GameGridModelStub implements IGameGridModel {
		private float[] startingPosition;

		public GameGridData getGridData() {
			throw new UnsupportedOperationException();
		}

		public float[] getTeamOneStartingGridPosition() {
			return startingPosition;
		}
	}
}
