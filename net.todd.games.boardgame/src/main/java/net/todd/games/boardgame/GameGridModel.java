package net.todd.games.boardgame;

public class GameGridModel implements IGameGridModel {
	private final GameGridData gameGridData = new GameGridData();

	public GameGridData getGridData() {
		return gameGridData;
	}
}
