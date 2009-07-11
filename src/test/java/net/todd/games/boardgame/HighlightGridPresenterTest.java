package net.todd.games.boardgame;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

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
		model.tilesToHighlight = new TileData[] { new TileData(),
				new TileData() };
		new HighlightGridPresenter(view, model);

		assertNull(view.tilesToHighlight);

		model.userPieceSelectedListener.fireEvent();

		assertSame(model.tilesToHighlight, view.tilesToHighlight);
	}

	@Test
	public void testWhenTileIsSelectedViewClearsOutHighlightedTiles() {
		new HighlightGridPresenter(view, model);

		assertFalse(view.highlightedTilesCleared);

		model.gameGridTileSelectedListener.fireEvent();
		assertTrue(view.highlightedTilesCleared);
	}

	private static class GameGridModelStub implements IGameGridModel {
		private IListener userPieceSelectedListener;
		private IListener gameGridTileSelectedListener;
		private TileData[] tilesToHighlight;
		private List<TileData> data;

		public List<TileData> getTileData() {
			return data;
		}

		public TileData[] getTilesToHighlight() {
			return tilesToHighlight;
		}

		public void addUserPieceSelectedListener(IListener listener) {
			this.userPieceSelectedListener = listener;
		}

		public void addTileSelectedListener(IListener listener) {
			this.gameGridTileSelectedListener = listener;
		}

		public List<Vector3f> getTeamOneStartingGridPositions() {
			throw new UnsupportedOperationException();
		}

		public Vector3f getSelectedTileLocation() {
			throw new UnsupportedOperationException();
		}

		public void setSelectedTile(TileData position) {
			throw new UnsupportedOperationException();
		}

		public List<Vector3f> getTeamTwoStartingGridPositions() {
			throw new UnsupportedOperationException();
		}

		public void setSelectedUserPiece(PieceInfo pieceInfo) {
			throw new UnsupportedOperationException();
		}
	}

	private static class HighlightedGridViewStub implements
			IHighlightedGridView {
		private boolean highlightedTilesCleared;
		private TileData[] tilesToHighlight;

		public IBranchGroup getBranchGroup() {
			return null;
		}

		public void highlightTiles(TileData[] tiles) {
			tilesToHighlight = tiles;
		}

		public void clearHighlightedTiles() {
			highlightedTilesCleared = true;
		}
	}
}
