package net.todd.games.boardgame;

public class GameGridModelProvider {
	private static GameGridModel model;

	public static IGameGridModel getModel() {
		if (model == null) {
			IGameGridData gameGridData = new GameGridData();
			model = new GameGridModel(gameGridData, new TileHighlighterCalculator(gameGridData));
		}
		return model;
	}
}
