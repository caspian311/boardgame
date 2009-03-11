package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;

import java.util.List;

import net.todd.common.uitools.IListener;

import org.junit.Before;
import org.junit.Test;

public class UserPieceModelFactoryTest {
	private GameGridModelStub gameGridModel;

	@Before
	public void setUp() {
		gameGridModel = new GameGridModelStub();
	}

	@Test
	public void testCorrectNumberOfUserPieceModelsGeneratedWithCorrectStartingPoints() {
		gameGridModel.startingPositions = new float[][] { { 1f, 2f, 3f }, { 4f, 5f, 6f } };
		List<IUserPieceModel> userPieceModels = new UserPieceModelFactory(gameGridModel)
				.getUserPieceModels();

		assertEquals(2, userPieceModels.size());
		ComparisonUtil.compareArrays(new float[] { 1f, 5f, 3f }, userPieceModels.get(0)
				.getCurrentPosition());
		ComparisonUtil.compareArrays(new float[] { 4f, 5f, 6f }, userPieceModels.get(1)
				.getCurrentPosition());
	}

	private static class GameGridModelStub implements IGameGridModel {
		float[][] startingPositions;

		public void addPositionSelectedListener(IListener listener) {
		}

		public float[] getSelectedPosition() {
			throw new UnsupportedOperationException();
		}

		public float[][] getTeamOneStartingGridPositions() {
			return startingPositions;
		}

		public TileData[][] getTileData() {
			throw new UnsupportedOperationException();
		}

		public void setSelectedTile(TileData tileData) {
			throw new UnsupportedOperationException();
		}
	}
}
