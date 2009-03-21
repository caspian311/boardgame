package net.todd.games.boardgame;

public interface IGameEngineFactory {
	IGameEngine createGameEngine(IBranchGroup branchGroup);
}