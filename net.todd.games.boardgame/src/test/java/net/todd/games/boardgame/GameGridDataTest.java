package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class GameGridDataTest {
	private GameGridData gridData;

	@Before
	public void setUp() {
		gridData = new GameGridData();
	}

	@Test
	public void testGridDataContainsTileInformationForAn8X8Grid() {
		TileData[][] tileData = gridData.getTileData();
		assertNotNull(tileData);
		assertEquals(8, tileData.length);
		for (int i = 0; i < tileData.length; i++) {
			assertEquals(8, tileData[i].length);
		}
	}

	@Test
	public void testGridDataContainsTilesThatAre10X10InSize() {
		TileData[][] tileData = gridData.getTileData();
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				assertEquals(10f, tileData[x][y].getSize());
			}
		}
	}

	@Test
	public void testGridDataTilesContainChessPatternInWhitesAndBlacks() {
		TileData[][] tileData = gridData.getTileData();
		assertEquals(GameGridData.black, tileData[0][0].getColor());
		assertEquals(GameGridData.white, tileData[0][1].getColor());
		assertEquals(GameGridData.black, tileData[0][2].getColor());
		assertEquals(GameGridData.white, tileData[0][3].getColor());
		assertEquals(GameGridData.black, tileData[0][4].getColor());
		assertEquals(GameGridData.white, tileData[0][5].getColor());
		assertEquals(GameGridData.black, tileData[0][6].getColor());
		assertEquals(GameGridData.white, tileData[0][7].getColor());
		assertEquals(GameGridData.white, tileData[1][0].getColor());
		assertEquals(GameGridData.black, tileData[1][1].getColor());
		assertEquals(GameGridData.white, tileData[1][2].getColor());
		assertEquals(GameGridData.black, tileData[1][3].getColor());
		assertEquals(GameGridData.white, tileData[1][4].getColor());
		assertEquals(GameGridData.black, tileData[1][5].getColor());
		assertEquals(GameGridData.white, tileData[1][6].getColor());
		assertEquals(GameGridData.black, tileData[1][7].getColor());
		assertEquals(GameGridData.black, tileData[2][0].getColor());
		assertEquals(GameGridData.white, tileData[2][1].getColor());
	}

	@Test
	public void testGridDataTilesContainTilePositions() {
		TileData[][] tileData = gridData.getTileData();
		ComparisonUtil.compareArrays(new float[] { -35f, 0f, -35f }, tileData[0][0].getPosition());
		ComparisonUtil.compareArrays(new float[] { -25f, 0f, -35f }, tileData[0][1].getPosition());
		ComparisonUtil.compareArrays(new float[] { -15f, 0f, -35f }, tileData[0][2].getPosition());
		ComparisonUtil.compareArrays(new float[] { -5f, 0f, -35f }, tileData[0][3].getPosition());
		ComparisonUtil.compareArrays(new float[] { 5f, 0f, -35f }, tileData[0][4].getPosition());
		ComparisonUtil.compareArrays(new float[] { 15f, 0f, -35f }, tileData[0][5].getPosition());
		ComparisonUtil.compareArrays(new float[] { 25f, 0f, -35f }, tileData[0][6].getPosition());
		ComparisonUtil.compareArrays(new float[] { 35f, 0f, -35f }, tileData[0][7].getPosition());
		ComparisonUtil.compareArrays(new float[] { -35f, 0f, -25f }, tileData[1][0].getPosition());
		ComparisonUtil.compareArrays(new float[] { -25f, 0f, -25f }, tileData[1][1].getPosition());
		ComparisonUtil.compareArrays(new float[] { -15f, 0f, -25f }, tileData[1][2].getPosition());
		ComparisonUtil.compareArrays(new float[] { -5f, 0f, -25f }, tileData[1][3].getPosition());
		ComparisonUtil.compareArrays(new float[] { 5f, 0f, -25f }, tileData[1][4].getPosition());
		ComparisonUtil.compareArrays(new float[] { 15f, 0f, -25f }, tileData[1][5].getPosition());
		ComparisonUtil.compareArrays(new float[] { 25f, 0f, -25f }, tileData[1][6].getPosition());
		ComparisonUtil.compareArrays(new float[] { 35f, 0f, -25f }, tileData[1][7].getPosition());
		ComparisonUtil.compareArrays(new float[] { -35f, 0f, -15f }, tileData[2][0].getPosition());
		ComparisonUtil.compareArrays(new float[] { -25f, 0f, -15f }, tileData[2][1].getPosition());
	}

	@Test
	public void testCoordinatesForTeamOneStartingPositions() {
		float[][] teamOneStartingPositions = gridData.getTeamOneStartingPositions();
		assertEquals(4, teamOneStartingPositions.length);
		ComparisonUtil.compareArrays(new float[] { -15f, 0f, -35f }, teamOneStartingPositions[0]);
		ComparisonUtil.compareArrays(new float[] { -5f, 0f, -35f }, teamOneStartingPositions[1]);
		ComparisonUtil.compareArrays(new float[] { 5f, 0f, -35f }, teamOneStartingPositions[2]);
		ComparisonUtil.compareArrays(new float[] { 15f, 0f, -35f }, teamOneStartingPositions[3]);
	}
}
