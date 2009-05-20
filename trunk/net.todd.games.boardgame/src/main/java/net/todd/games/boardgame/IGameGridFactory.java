package net.todd.games.boardgame;

public interface IGameGridFactory {
	IBranchGroup constructGameGrid(IPickerFactory pickerFactory);

	IBranchGroup constructHighlightedGrid();
}