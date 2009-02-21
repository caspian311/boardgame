package net.todd.games.boardgame;

public class GameGridPresenter {
	public GameGridPresenter(IGameGridView boardView, IGameGridModel boardModel) {
		boardView.constructGrid(boardModel.getGridData());
	}
}
