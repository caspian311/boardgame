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
	public void testTeamOneStartingPositionIsInCorrectPosition1() {
		GameGridDataMock gameGridData = new GameGridDataMock();
		gameGridData.tileData = new TileData[4][4];
		gameGridData.tileData[0][2] = new TileData();
		gameGridData.tileData[0][2].setPosition(new float[] {});
		model.setGridData(gameGridData);

		assertEquals(gameGridData.tileData[0][2].getPosition(), model.getTeamOneStartingGridPosition());
	}

	@Test
	public void testTeamOneStartingPositionIsInCorrectPosition2() {
		GameGridDataMock gameGridData = new GameGridDataMock();
		gameGridData.tileData = new TileData[3][3];
		gameGridData.tileData[0][1] = new TileData();
		gameGridData.tileData[0][1].setPosition(new float[] {});
		model.setGridData(gameGridData);

		assertEquals(gameGridData.tileData[0][1].getPosition(), model.getTeamOneStartingGridPosition());
	}

	private static class GameGridDataMock extends GameGridData {
		private TileData[][] tileData;

		@Override
		public TileData[][] getTileData() {
			return tileData;
		}
	}
}
