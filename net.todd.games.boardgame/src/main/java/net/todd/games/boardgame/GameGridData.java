package net.todd.games.boardgame;

/**
 * GameGridData contains all the information about the actual grid. Later to be
 * made abstract and have subclasses that create chess grid and others
 */
public class GameGridData {
	private final TileData[][] tileData;

	private static final float TILE_SIZE = 10f;
	static final float[] white = new float[] { 1f, 1f, 1f };
	static final float[] black = new float[] { 0f, 0f, 0f };

	private int colorIndex;
	private float xIndex = 40;
	private float zIndex = 40;

	public GameGridData() {
		tileData = new TileData[8][8];

		for (int x = 0; x < 8; x++) {
			float nextZ = getNextZ();
			for (int y = 0; y < 8; y++) {
				TileData tileDatum = new TileData();
				tileDatum.setSize(TILE_SIZE);
				tileDatum.setColor(getNextColor());
				tileDatum.setPosition(new float[] { getNextX(), 0f, nextZ });

				tileData[x][y] = tileDatum;
			}
			getNextColor();
		}
	}

	public TileData[][] getTileData() {
		return tileData;
	}

	private float getNextX() {
		xIndex += TILE_SIZE;
		if (xIndex > 35)
			xIndex = -35;
		return xIndex;
	}

	private float getNextZ() {
		zIndex += TILE_SIZE;
		if (zIndex > 35)
			zIndex = -35;
		return zIndex;
	}

	private float[] getNextColor() {
		colorIndex++;
		return colorIndex % 2 == 0 ? white : black;
	}
}
