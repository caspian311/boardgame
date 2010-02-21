package net.todd.games.boardgame;

public class GameGridFactory implements IGameGridFactory {
	public IBranchGroup constructGameGrid(IPicker picker) {
		IBranchGroupFactory branchGroupFactory = new BranchGroupFactory();
		IGameGridView boardView = new GameGridView(picker, branchGroupFactory, new TileFactory());
		new GameGridPresenter(boardView, GameGridModelProvider.getModel());
		IBranchGroup bg = boardView.getBranchGroup();
		return bg;
	}

	public IBranchGroup constructHighlightedGrid() {
		IHighlightedGridView highlightedGridView = new HighlightedGridView(
				new BranchGroupFactory(), new TileFactory());
		IGameGridModel gameGridModel = GameGridModelProvider.getModel();
		new HighlightGridPresenter(highlightedGridView, gameGridModel);
		return highlightedGridView.getBranchGroup();
	}
}
