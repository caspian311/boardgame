package net.todd.games.boardgame;

public class GameGridModel implements IGameGridModel {
	private GameGridData gameGridData;

	public GameGridModel() {
		gameGridData = new GameGridData();
	}

	public GameGridData getGridData() {
		return gameGridData;
	}

	public float[] getTeamOneStartingGridPosition() {
		TileData[] firstRow = gameGridData.getTileData()[0];
		TileData centerSquare = firstRow[firstRow.length / 2];
		return centerSquare.getPosition();
	}

	void setGridData(GameGridData gameGridData) {
		this.gameGridData = gameGridData;
	}
}
