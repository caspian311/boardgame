package net.todd.games.boardgame;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3f;

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
	}

	public TileData[][] getTileData() {
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

	public List<Vector3f> getTeamOneStartingPositions() {
		List<Vector3f> startingPositions = new ArrayList<Vector3f>();
		startingPositions.add(new Vector3f(-15f, 0f, -35f));
		startingPositions.add(new Vector3f(-5f, 0f, -35f));
		startingPositions.add(new Vector3f(5f, 0f, -35f));
		startingPositions.add(new Vector3f(15f, 0f, -35f));

		return startingPositions;
	}

	public List<Vector3f> getTeamTwoStartingPositions() {
		List<Vector3f> teamTwoStartingPositions = new ArrayList<Vector3f>();
		teamTwoStartingPositions.add(new Vector3f(-15f, 0f, 35f));
		teamTwoStartingPositions.add(new Vector3f(-5f, 0f, 35f));
		teamTwoStartingPositions.add(new Vector3f(5f, 0f, 35f));
		teamTwoStartingPositions.add(new Vector3f(15f, 0f, 35f));

		return teamTwoStartingPositions;
	}
}
