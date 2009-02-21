package net.todd.games.boardgame;

/**
 * GameGridData is a bean to hold all the information about the game grid from
 * an informational standpoint.
 */
public class GameGridData {
	private TileData[][] tileData;

	public void setTileData(TileData[][] tileData) {
		this.tileData = tileData;
	}

	public TileData[][] getTileData() {
		return tileData;
	}
}
