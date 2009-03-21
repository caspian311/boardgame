package net.todd.games.boardgame;

public class GameGridFactory implements IGameGridFactory {
	public IBranchGroup constructGameGrid(IPicker picker) {
		IBranchGroupFactory branchGroupFactory = new BranchGroupFactory();
		IGameGridView boardView = new GameGridView(picker, branchGroupFactory);
		new GameGridPresenter(boardView, GameGridModelProvider.getModel());
		IBranchGroup bg = boardView.getBranchGroup();
		return bg;
	}
}
