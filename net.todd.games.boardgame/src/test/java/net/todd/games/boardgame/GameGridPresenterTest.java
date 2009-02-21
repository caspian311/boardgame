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
		model.data = new GameGridData();

		assertNull(view.data);
		new GameGridPresenter(view, model);
		assertEquals(model.data, view.data);
	}

	public class GameGridViewStub implements IGameGridView {
		private GameGridData data;

		public void addTileSelectedListener(IListener listener) {
			throw new UnsupportedOperationException();
		}

		public BranchGroup getBG() {
			throw new UnsupportedOperationException();
		}

		public ITile getSelectedTile() {
			throw new UnsupportedOperationException();
		}

		public void constructGrid(GameGridData data) {
			this.data = data;
		}
	}

	private static class GameGridModelStub implements IGameGridModel {
		private GameGridData data;

		public GameGridData getGridData() {
			return data;
		}
	}
}
