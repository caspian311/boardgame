package net.todd.games.boardgame;

import net.todd.common.uitools.IListener;

public class GameGridPresenter {
	public GameGridPresenter(final IGameGridView boardView, final IGameGridModel boardModel) {
		boardView.constructGrid(boardModel.getTileData());
		boardView.addTileSelectedListener(new IListener() {
			public void fireEvent() {
				boardModel.setSelectedTile(boardView.getSelectedTile());
			}
		});

		boardModel.addUserPieceSelectedListener(new IListener() {
			public void fireEvent() {
				boardView.highlightTiles(boardModel.getTilesToHighlight());
			}
		});
	}
}
