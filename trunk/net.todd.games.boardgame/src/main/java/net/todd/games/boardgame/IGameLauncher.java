package net.todd.games.boardgame;

public interface IGameLauncher {
	void launchGame(IUniverse universe, IBranchGroupFactory branchGroupFactory,
			IGameEngineFactory gameEngineFactory);
}