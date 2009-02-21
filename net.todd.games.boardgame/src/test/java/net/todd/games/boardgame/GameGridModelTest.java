package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.todd.common.uitools.IListener;

import org.junit.Before;
import org.junit.Test;

public class GameGridModelTest {
	private GameGridModel model;
	private float[] positionSelected;

	@Before
	public void setUp() {
		model = new GameGridModel();
		positionSelected = null;
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

		assertEquals(gameGridData.tileData[0][2].getPosition(), model
				.getTeamOneStartingGridPosition());
	}

	@Test
	public void testTeamOneStartingPositionIsInCorrectPosition2() {
		GameGridDataMock gameGridData = new GameGridDataMock();
		gameGridData.tileData = new TileData[3][3];
		gameGridData.tileData[0][1] = new TileData();
		gameGridData.tileData[0][1].setPosition(new float[] {});
		model.setGridData(gameGridData);

		assertEquals(gameGridData.tileData[0][1].getPosition(), model
				.getTeamOneStartingGridPosition());
	}

	@Test
	public void testModelNotifiesListenersWhenAGridPositionIsSelected() {
		PositionSelectedListenerStub listenerStub1 = new PositionSelectedListenerStub();
		PositionSelectedListenerStub listenerStub2 = new PositionSelectedListenerStub();
		model.addPositionSelectedListener(listenerStub1);
		model.addPositionSelectedListener(listenerStub2);
		assertFalse(listenerStub1.eventFired);
		assertFalse(listenerStub2.eventFired);
		model.setSelectedTile(new TileData());
		assertTrue(listenerStub1.eventFired);
		assertTrue(listenerStub2.eventFired);
	}

	@Test
	public void testModelMakesSelectedPositionInformationAvailableBeforeNotifyingListeners() {
		model.addPositionSelectedListener(new IListener() {
			public void fireEvent() {
				positionSelected = model.getSelectedPosition();
			}
		});

		TileData tileData = new TileData();
		tileData.setPosition(new float[] { 1f, 2f, 3f });
		model.setSelectedTile(tileData);
		ComparisonUtil.compareArrays(tileData.getPosition(), positionSelected);
	}

	private static class GameGridDataMock extends GameGridData {
		private TileData[][] tileData;

		@Override
		public TileData[][] getTileData() {
			return tileData;
		}
	}

	private static class PositionSelectedListenerStub implements IListener {
		private boolean eventFired;

		public void fireEvent() {
			eventFired = true;
		}
	}
}
