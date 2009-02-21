package net.todd.games.boardgame;

import net.todd.common.uitools.IListener;

public class GameGridPresenter {
	public GameGridPresenter(final IGameGridView boardView, final IGameGridModel boardModel) {
		boardView.constructGrid(boardModel.getGridData());
		boardView.addTileSelectedListener(new IListener() {
			public void fireEvent() {
				boardModel.setSelectedTile(boardView.getSelectedTile());
			}
		});
	}
}
