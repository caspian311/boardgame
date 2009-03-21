package net.todd.games.boardgame;

public class GameGridFactory implements IGameGridFactory {
	public IBranchGroup constructGameGrid(IPicker picker) {
		IGameGridView boardView = new GameGridView(picker);
		new GameGridPresenter(boardView, GameGridModelProvider.getModel());
		IBranchGroup bg = boardView.getBranchGroup();
		return bg;
	}
}
