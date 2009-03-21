package net.todd.games.boardgame;

public class TileFixture {

	public static Tile getTile() {
		Tile tile = new Tile(getTileData());
		return tile;
	}

	public static TileData getTileData() {
		TileData data = new TileData();
		data.setPosition(new float[3]);
		data.setColor(new float[3]);
		return data;
	}
}
