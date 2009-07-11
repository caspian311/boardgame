package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3f;

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
		model.data = new ArrayList<TileData>();

		assertNull(view.data);
		new GameGridPresenter(view, model);
		assertEquals(model.data, view.data);
	}

	@Test
	public void testPresenterListensForSelectedTilesFromViewAndNotifiesModel() {
		model.data = new ArrayList<TileData>();

		view.tileData = new TileData();
		view.tileData.setPosition(new float[] { 1f, 2f, 3f });

		new GameGridPresenter(view, model);
		assertNull(model.selectedTile);
		view.listener.fireEvent();

		ComparisonUtil.compareArrays(view.tileData.getPosition(),
				model.selectedTile.getPosition());
	}

	public class GameGridViewStub implements IGameGridView {
		TileData[] tilesToHighlight;
		private List<TileData> data;
		private IListener listener;
		private TileData tileData;

		public void addTileSelectedListener(IListener listener) {
			this.listener = listener;
		}

		public IBranchGroup getBranchGroup() {
			throw new UnsupportedOperationException();
		}

		public TileData getSelectedTile() {
			return tileData;
		}

		public void constructGrid(List<TileData> data) {
			this.data = data;
		}
	}

	private static class GameGridModelStub implements IGameGridModel {
		IListener listener;
		TileData[] tilesToHighlight;
		TileData selectedTile;
		List<TileData> data;

		public List<TileData> getTileData() {
			return data;
		}

		public void addTileSelectedListener(IListener listener) {
			throw new UnsupportedOperationException();
		}

		public Vector3f getSelectedTileLocation() {
			throw new UnsupportedOperationException();
		}

		public void setSelectedTile(TileData position) {
			selectedTile = position;
		}

		public TileData[] getTilesToHighlight() {
			return tilesToHighlight;
		}

		public void addUserPieceSelectedListener(IListener listener) {
			this.listener = listener;
		}

		public void setSelectedUserPiece(PieceInfo pieceInfo) {
			throw new UnsupportedOperationException();
		}
	}
}
