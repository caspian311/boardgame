package net.todd.games.boardgame;

public class GameGridModelProvider {

	private static GameGridModel model;

	public static IGameGridModel getModel() {
		if (model == null) {
			model = new GameGridModel();
		}
		return model;
	}

}
