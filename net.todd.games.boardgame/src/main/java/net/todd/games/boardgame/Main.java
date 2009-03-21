package net.todd.games.boardgame;

public class Main {
	public static void main(String[] args) {
		new Main().execute();
	}

	private void execute() {
		GameLauncher gameLauncher = new GameLauncher();
		MainApplication mainApplication = new MainApplication("Board Game", gameLauncher);
		mainApplication.start();
	}
}
