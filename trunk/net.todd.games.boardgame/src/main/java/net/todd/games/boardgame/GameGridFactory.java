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
		IHighlightedGridView highlightedGridView = new HighlightedGridView(new BranchGroupFactory());
		IGameGridModel gameGridModel = GameGridModelProvider.getModel();
		new HighlightGridPresenter(highlightedGridView, gameGridModel);
		return highlightedGridView.getBranchGroup();
	}
}
