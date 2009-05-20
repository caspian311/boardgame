package net.todd.games.boardgame;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.List;

import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;

import org.junit.Before;
import org.junit.Test;

public class HighlightGridPresenterTest {
	private HighlightedGridViewStub view;
	private GameGridModelStub model;

	@Before
	public void setUp() {
		view = new HighlightedGridViewStub();
		model = new GameGridModelStub();
	}

	@Test
	public void testPresenterListensForTiesToHighlightFromModelAndNotifiesView() {
		model.tilesToHighlight = new TileData[] { new TileData(), new TileData() };
		new HighlightGridPresenter(view, model);

		assertNull(view.tilesToHighlight);

		model.listener.fireEvent();

		assertSame(model.tilesToHighlight, view.tilesToHighlight);
	}

	private static class GameGridModelStub implements IGameGridModel {
		IListener listener;
		TileData[] tilesToHighlight;
		TileData selectedTile;
		TileData[][] data;

		public TileData[][] getTileData() {
			return data;
		}

		public List<Vector3f> getTeamOneStartingGridPositions() {
			throw new UnsupportedOperationException();
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

		public List<Vector3f> getTeamTwoStartingGridPositions() {
			throw new UnsupportedOperationException();
		}

		public void setSelectedUserPiece(PieceInfo pieceInfo) {
			throw new UnsupportedOperationException();
		}
	}

	private static class HighlightedGridViewStub implements IHighlightedGridView {
		private TileData[] tilesToHighlight;

		public IBranchGroup getBranchGroup() {
			return null;
		}

		public void highlightTiles(TileData[] tiles) {
			tilesToHighlight = tiles;
		}
	}
}
