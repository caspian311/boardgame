package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class GameGridModelTest {
	private GameGridModel model;

	@Before
	public void setUp() {
		model = new GameGridModel();
	}

	@Test
	public void testGridDataIsNeverNull() {
		assertNotNull(model.getGridData());
	}

	@Test
	public void testGridDataContainsTileInformationForAn8X8Grid() {
		GameGridData gridData = model.getGridData();
		TileData[][] tileData = gridData.getTileData();
		assertNotNull(tileData);
		assertEquals(8, tileData.length);
		for (int i = 0; i < tileData.length; i++) {
			assertEquals(8, tileData[i].length);
		}
	}

	@Test
	public void testGridDataContainsTilesThatAre10X10InSize() {
		GameGridData gridData = model.getGridData();
		TileData[][] tileData = gridData.getTileData();
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				assertEquals(10f, tileData[x][y].getSize());
			}
		}
	}

	@Test
	public void testGridDataTilesContainChessPatternInWhitesAndBlacks() {
		GameGridData gridData = model.getGridData();
		TileData[][] tileData = gridData.getTileData();
		assertEquals(GameGridModel.black, tileData[0][0].getColor());
		assertEquals(GameGridModel.white, tileData[0][1].getColor());
		assertEquals(GameGridModel.black, tileData[0][2].getColor());
		assertEquals(GameGridModel.white, tileData[0][3].getColor());
		assertEquals(GameGridModel.black, tileData[0][4].getColor());
		assertEquals(GameGridModel.white, tileData[0][5].getColor());
		assertEquals(GameGridModel.black, tileData[0][6].getColor());
		assertEquals(GameGridModel.white, tileData[0][7].getColor());
		assertEquals(GameGridModel.white, tileData[1][0].getColor());
		assertEquals(GameGridModel.black, tileData[1][1].getColor());
		assertEquals(GameGridModel.white, tileData[1][2].getColor());
		assertEquals(GameGridModel.black, tileData[1][3].getColor());
		assertEquals(GameGridModel.white, tileData[1][4].getColor());
		assertEquals(GameGridModel.black, tileData[1][5].getColor());
		assertEquals(GameGridModel.white, tileData[1][6].getColor());
		assertEquals(GameGridModel.black, tileData[1][7].getColor());
		assertEquals(GameGridModel.black, tileData[2][0].getColor());
		assertEquals(GameGridModel.white, tileData[2][1].getColor());
	}

	@Test
	public void testGridDataTilesContainTilePositions() {
		GameGridData gridData = model.getGridData();
		TileData[][] tileData = gridData.getTileData();
		compareArrays(new float[] { -35f, 0f, -35f }, tileData[0][0].getPosition());
		compareArrays(new float[] { -25f, 0f, -35f }, tileData[0][1].getPosition());
		compareArrays(new float[] { -15f, 0f, -35f }, tileData[0][2].getPosition());
		compareArrays(new float[] { -5f, 0f, -35f }, tileData[0][3].getPosition());
		compareArrays(new float[] { 5f, 0f, -35f }, tileData[0][4].getPosition());
		compareArrays(new float[] { 15f, 0f, -35f }, tileData[0][5].getPosition());
		compareArrays(new float[] { 25f, 0f, -35f }, tileData[0][6].getPosition());
		compareArrays(new float[] { 35f, 0f, -35f }, tileData[0][7].getPosition());
		compareArrays(new float[] { -35f, 0f, -25f }, tileData[1][0].getPosition());
		compareArrays(new float[] { -25f, 0f, -25f }, tileData[1][1].getPosition());
		compareArrays(new float[] { -15f, 0f, -25f }, tileData[1][2].getPosition());
		compareArrays(new float[] { -5f, 0f, -25f }, tileData[1][3].getPosition());
		compareArrays(new float[] { 5f, 0f, -25f }, tileData[1][4].getPosition());
		compareArrays(new float[] { 15f, 0f, -25f }, tileData[1][5].getPosition());
		compareArrays(new float[] { 25f, 0f, -25f }, tileData[1][6].getPosition());
		compareArrays(new float[] { 35f, 0f, -25f }, tileData[1][7].getPosition());
		compareArrays(new float[] { -35f, 0f, -15f }, tileData[2][0].getPosition());
		compareArrays(new float[] { -25f, 0f, -15f }, tileData[2][1].getPosition());
	}

	private void compareArrays(float[] array1, float[] array2) {
		for (int i = 0; i < array1.length; i++) {
			assertEquals(array1[i], array2[i]);
		}
	}
}
