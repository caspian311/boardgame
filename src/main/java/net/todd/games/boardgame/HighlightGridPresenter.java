package net.todd.games.boardgame;

import net.todd.common.uitools.IListener;

public class HighlightGridPresenter {
	public HighlightGridPresenter(final IHighlightedGridView highlightedGridView,
			final IGameGridModel gameGridModel) {
		gameGridModel.addUserPieceSelectedListener(new IListener() {
			public void fireEvent() {
				highlightedGridView.highlightTiles(gameGridModel.getTilesToHighlight());
			}
		});
		gameGridModel.addTileSelectedListener(new IListener() {
			public void fireEvent() {
				highlightedGridView.clearHighlightedTiles();
			}
		});
	}
}
