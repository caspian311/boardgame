package net.todd.games.boardgame;

public class Main {
	public static void main(String[] args) {
		new Main().execute();
	}

	private void execute() {
		IUniverseFactory universeFactory = new UniverseFactory();
		MainApplication mainApplication = new MainApplication("Board Game", universeFactory);
		mainApplication.createGame();
		mainApplication.start();
	}
}
