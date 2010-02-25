package net.todd.games.boardgame;

public class GameLauncher implements IGameLauncher {
	private final IUniverse universe;
	private final IBranchGroup branchGroup;
	private final IPicker picker;
	private final IGameEngine gameEngine;

	public GameLauncher(IUniverse universe, IBranchGroup branchGroup,
			IGameEngine gameEngine, IPicker picker) {
		this.universe = universe;
		this.branchGroup = branchGroup;
		this.gameEngine = gameEngine;
		this.picker = picker;
	}

	public void launchGame() {
		gameEngine.createScene(picker);
		gameEngine.createCamera(universe);

		branchGroup.compile();

		universe.addBranchGraph(branchGroup);
	}
}
