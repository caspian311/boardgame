package net.todd.games.boardgame;

public interface IGameGridFactory {
	IBranchGroup constructGameGrid(IPicker picker);

	IBranchGroup constructHighlightedGrid();
}