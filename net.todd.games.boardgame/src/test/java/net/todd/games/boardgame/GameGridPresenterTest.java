package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.media.j3d.BranchGroup;

import net.todd.common.uitools.IListener;

import org.junit.Before;
import org.junit.Test;

public class GameGridPresenterTest {
	private GameGridViewStub view;
	private GameGridModelStub model;

	@Before
	public void setUp() {
		view = new GameGridViewStub();
		model = new GameGridModelStub();
	}

	@Test
	public void testGridIsCreatedWithDimensionFromModel() {
		model.data = new TileData[][] {};

		assertNull(view.data);
		new GameGridPresenter(view, model);
		assertEquals(model.data, view.data);
	}

	@Test
	public void testPresenterListensForSelectedTilesFromViewAndNotifiesModel() {
		model.data = new TileData[][] {};

		view.tileData = new TileData();
		view.tileData.setPosition(new float[] { 1f, 2f, 3f });

		new GameGridPresenter(view, model);
		assertNull(model.selectedPosition);
		view.listener.fireEvent();
		ComparisonUtil.compareArrays(view.tileData.getPosition(), model.selectedPosition
				.getPosition());
	}

	public class GameGridViewStub implements IGameGridView {
		private TileData[][] data;
		private IListener listener;
		private TileData tileData;

		public void addTileSelectedListener(IListener listener) {
			this.listener = listener;
		}

		public BranchGroup getBG() {
			throw new UnsupportedOperationException();
		}

		public TileData getSelectedTile() {
			return tileData;
		}

		public void constructGrid(TileData[][] data) {
			this.data = data;
		}
	}

	private static class GameGridModelStub implements IGameGridModel {
		private TileData selectedPosition;
		private TileData[][] data;

		public TileData[][] getTileData() {
			return data;
		}

		public float[][] getTeamOneStartingGridPositions() {
			throw new UnsupportedOperationException();
		}

		public void addPositionSelectedListener(IListener listener) {
			throw new UnsupportedOperationException();
		}

		public float[] getSelectedPosition() {
			throw new UnsupportedOperationException();
		}

		public void setSelectedTile(TileData position) {
			selectedPosition = position;
		}
	}
}
