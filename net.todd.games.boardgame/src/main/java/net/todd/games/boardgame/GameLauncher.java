package net.todd.games.boardgame;

import javax.media.j3d.BranchGroup;

public class GameLauncher implements IGameLauncher {
	public void launchGame(IUniverse universe) {
		IBranchGroup branchGroup = new BranchGroupAdapter(new BranchGroup());
		IGameEngine gameEngine = new GameEngineFactory().createGameEngine(branchGroup);

		IPicker picker = new Picker(universe, branchGroup);

		gameEngine.createScene(picker);
		gameEngine.createCamera(universe);

		branchGroup.compile();

		universe.addBranchGraph(branchGroup);
	}
}
