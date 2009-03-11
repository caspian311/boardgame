package net.todd.games.boardgame;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.todd.common.uitools.IListener;

import org.junit.Before;
import org.junit.Test;

public class GameGridModelTest {
	private float[] positionSelected;

	@Before
	public void setUp() {
		positionSelected = null;
	}
	
	@Test
	public void testGetTileDataPullsFromGridData() {
		TileData[][] tileData = new TileData[][] {};
		GameGridDataMock gameGridData = new GameGridDataMock();
		gameGridData.tileData = tileData;
		GameGridModel model = new GameGridModel(gameGridData);
		ComparisonUtil.compareDoubleArrays(tileData, model.getTileData());
	}

	@Test
	public void testTeamOneStartingPositionIsInCorrectPosition3() {
		GameGridDataMock gameGridData = new GameGridDataMock();
		GameGridModel model = new GameGridModel(gameGridData);
		gameGridData.teamOneStartingPosition = new float[][] { { 1f, 2f, 3f }, { 4f, 5f, 6f } };

		ComparisonUtil.compareArrays(new float[] { 1f, 2f, 3f }, model
				.getTeamOneStartingGridPositions()[0]);
		ComparisonUtil.compareArrays(new float[] { 4f, 5f, 6f }, model
				.getTeamOneStartingGridPositions()[1]);
	}

	@Test
	public void testModelNotifiesListenersWhenAGridPositionIsSelected() {
		GameGridModel model = new GameGridModel(null);
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
		final GameGridModel model = new GameGridModel(null);
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
		float[][] teamOneStartingPosition;
		private TileData[][] tileData;

		@Override
		public TileData[][] getTileData() {
			return tileData;
		}
		
		@Override
		public float[][] getTeamOneStartingPositions() {
			return teamOneStartingPosition;
		}
	}

	private static class PositionSelectedListenerStub implements IListener {
		private boolean eventFired;

		public void fireEvent() {
			eventFired = true;
		}
	}
}