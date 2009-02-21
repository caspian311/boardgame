package net.todd.games.boardgame;

public class GameGridModel implements IGameGridModel {
	private static final float SIZE = 10f;
	static final float[] white = new float[] { 1f, 1f, 1f };
	static final float[] black = new float[] { 0f, 0f, 0f };

	private final GameGridData gameGridData = new GameGridData();

	private int colorIndex;
	private float xIndex = 40;
	private float zIndex = 40;

	public GameGridData getGridData() {
		TileData[][] tileData = new TileData[8][8];

		for (int x = 0; x < 8; x++) {
			float nextZ = getNextZ();
			for (int y = 0; y < 8; y++) {
				TileData tileDatum = new TileData();
				tileDatum.setSize(SIZE);
				tileDatum.setColor(getNextColor());
				tileDatum.setPosition(new float[] { getNextX(), 0f, nextZ });

				tileData[x][y] = tileDatum;
			}
			getNextColor();
		}

		gameGridData.setTileData(tileData);

		return gameGridData;
	}

	private float getNextX() {
		xIndex += SIZE;
		if (xIndex > 35)
			xIndex = -35;
		return xIndex;
	}

	private float getNextZ() {
		zIndex += SIZE;
		if (zIndex > 35)
			zIndex = -35;
		return zIndex;
	}

	private float[] getNextColor() {
		colorIndex++;
		return colorIndex % 2 == 0 ? white : black;
	}
}
