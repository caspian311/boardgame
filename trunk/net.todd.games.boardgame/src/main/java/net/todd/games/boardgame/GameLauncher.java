package net.todd.games.boardgame;

public class GameLauncher implements IGameLauncher {
	public void launchGame(IUniverse universe,
			IBranchGroupFactory branchGroupFactory,
			IGameEngineFactory gameEngineFactory) {
		IBranchGroup branchGroup = branchGroupFactory.createBranchGroup();
		IGameEngine gameEngine = gameEngineFactory
				.createGameEngine(branchGroup);

		IPickerFactory pickerFactory = getPickerFactory(universe);
		IPicker picker = pickerFactory.createPicker(branchGroup);

		gameEngine.createScene(picker);
		gameEngine.createCamera(universe);

		branchGroup.compile();

		universe.addBranchGraph(branchGroup);
	}

	IPickerFactory getPickerFactory(IUniverse universe) {
		return new PickerFactory(universe);
	}
}
