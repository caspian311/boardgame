package net.todd.games.boardgame;

public class GameLauncher implements IGameLauncher {
	public void launchGame(IUniverse universe, IBranchGroupFactory branchGroupFactory,
			IGameEngineFactory gameEngineFactory) {
		IBranchGroup branchGroup = branchGroupFactory.createBranchGroup();
		IGameEngine gameEngine = gameEngineFactory.createGameEngine(branchGroup);

		IPicker picker = getPicker(universe, branchGroup);

		gameEngine.createScene(picker);
		gameEngine.createCamera(universe);

		branchGroup.compile();

		universe.addBranchGraph(branchGroup);
	}

	IPicker getPicker(IUniverse universe, IBranchGroup branchGroup) {
		return new Picker(universe, branchGroup);
	}
}
