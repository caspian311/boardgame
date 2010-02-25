package net.todd.games.boardgame;

public class Main {
	public static void main(String[] args) {
		new Main().execute();
	}

	private void execute() {
		IBranchGroupFactory branchGroupFactory = new BranchGroupFactory();
		IGameEngineFactory gameEngineFactory = new GameEngineFactory();
		IUniverse universe = new UniverseProvider().createUniverse();
		IBranchGroup branchGroup = branchGroupFactory.createBranchGroup();
		IPicker picker = new PickerFactory(universe).createPicker(branchGroup);
		MainApplication mainApplication = new MainApplication("Board Game",
				new GameLauncher(universe, branchGroup, gameEngineFactory
						.createGameEngine(branchGroup), picker));
		mainApplication.start();
	}
}
