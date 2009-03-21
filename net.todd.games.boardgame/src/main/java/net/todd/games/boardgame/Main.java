package net.todd.games.boardgame;

public class Main {
	public static void main(String[] args) {
		new Main().execute();
	}

	private void execute() {
		MainApplication mainApplication = new MainApplication("Board Game");
		mainApplication.createGame();
		mainApplication.start();
	}
}
