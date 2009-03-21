package net.todd.games.boardgame;


public class GameLauncher implements IGameLauncher {
	public void launchGame(IUniverse universe, IBranchGroupFactory branchGroupFactory) {
		IBranchGroup branchGroup = branchGroupFactory.createBranchGroup();
		IGameEngine gameEngine = new GameEngineFactory().createGameEngine(branchGroup);

		IPicker picker = new Picker(universe, branchGroup);

		gameEngine.createScene(picker);
		gameEngine.createCamera(universe);

		branchGroup.compile();

		universe.addBranchGraph(branchGroup);
	}
}
