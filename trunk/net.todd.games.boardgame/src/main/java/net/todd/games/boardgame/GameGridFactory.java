package net.todd.games.boardgame;

public class GameGridFactory implements IGameGridFactory {
	public IBranchGroup constructGameGrid(IPickerFactory pickerFactory) {
		IBranchGroupFactory branchGroupFactory = new BranchGroupFactory();
		IGameGridView boardView = new GameGridView(pickerFactory, branchGroupFactory);
		new GameGridPresenter(boardView, GameGridModelProvider.getModel());
		IBranchGroup bg = boardView.getBranchGroup();
		return bg;
	}

	public IBranchGroup constructHighlightedGrid() {
		IBranchGroupFactory branchGroupFactory = new BranchGroupFactory();
		IHighlightedGridView highlightedGridView = new HighlightedGridView(branchGroupFactory);
		IGameGridModel gameGridModel = GameGridModelProvider.getModel();
		new HighlightGridPresenter(highlightedGridView, gameGridModel);
		return highlightedGridView.getBranchGroup();
	}
}
