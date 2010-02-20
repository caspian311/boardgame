package net.todd.games.boardgame;

public class TileFactory implements ITileFactory {
	public Tile createTile(TileData datum) {
		return new Tile(datum);
	}
}
