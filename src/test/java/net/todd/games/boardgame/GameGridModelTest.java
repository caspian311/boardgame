package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;

import org.junit.Before;
import org.junit.Test;

public class GameGridModelTest {
	private Vector3f positionSelected;
	private TileData[] selectedTilesToHighlight;

	@Before
	public void setUp() {
		positionSelected = null;
	}

	@Test
	public void testGetTileDataPullsFromGridData() {
		TileData[][] tileData = new TileData[][] {};
		GameGridDataStub gameGridData = new GameGridDataStub();
		gameGridData.tileData = tileData;
		GameGridModel model = new GameGridModel(gameGridData, null);
		List<TileData> listOfTiles = model.getTileData();
		for (TileData[] columns : tileData) {
			for (TileData tileDatum : columns) {
				listOfTiles.contains(tileDatum);
			}
		}
	}

	@Test
	public void testModelNotifiesListenersWhenAGridPositionIsSelected() {
		GameGridModel model = new GameGridModel(null, null);
		ListenerStub listenerStub1 = new ListenerStub();
		ListenerStub listenerStub2 = new ListenerStub();
		model.addTileSelectedListener(listenerStub1);
		model.addTileSelectedListener(listenerStub2);

		assertFalse(listenerStub1.eventFired);
		assertFalse(listenerStub2.eventFired);

		TileData tileData = new TileData();
		tileData.setPosition(new float[] { 1f, 2f, 3f });
		model.setSelectedTile(tileData);

		assertTrue(listenerStub1.eventFired);
		assertTrue(listenerStub2.eventFired);
	}

	@Test
	public void testModelMakesSelectedPositionInformationAvailableBeforeNotifyingListeners() {
		final GameGridModel model = new GameGridModel(null, null);
		model.addTileSelectedListener(new IListener() {
			public void fireEvent() {
				positionSelected = model.getSelectedTileLocation();
			}
		});

		TileData tileData = new TileData();
		tileData.setPosition(new float[] { 1f, 2f, 3f });
		model.setSelectedTile(tileData);
		assertEquals(new Vector3f(tileData.getPosition()), positionSelected);
	}

	@Test
	public void testSettingSelectedUserPieceNotifiesAllListeners() {
		GameGridModel model = new GameGridModel(null, null);
		ListenerStub listener1 = new ListenerStub();
		ListenerStub listener2 = new ListenerStub();
		model.addUserPieceSelectedListener(listener1);
		model.addUserPieceSelectedListener(listener2);

		assertFalse(listener1.eventFired);
		assertFalse(listener2.eventFired);

		model.setSelectedUserPiece(null);

		assertTrue(listener1.eventFired);
		assertTrue(listener2.eventFired);
	}

	@Test
	public void testModelFindsAllAvailableMovesForPieceBasedOnGivenSelectedUserPiece() {
		TileHighlighterCalculatorStub tileHighlighterCalculator = new TileHighlighterCalculatorStub();

		GameGridDataStub gameGridData = new GameGridDataStub();
		TileData tileData1 = new TileData();
		TileData tileData2 = new TileData();
		TileData tileData3 = new TileData();
		TileData tileData4 = new TileData();
		TileData tileData5 = new TileData();
		TileData tileData6 = new TileData();
		gameGridData.tileData = new TileData[][] {
				{ tileData1, tileData2, tileData3 },
				{ tileData4, tileData5, tileData6, } };

		tileHighlighterCalculator.tilesToHighlight = new TileData[] {
				tileData3, tileData4, tileData5 };

		PieceInfo pieceInfo = new PieceInfo();
		final GameGridModel model = new GameGridModel(gameGridData,
				tileHighlighterCalculator);
		model.addUserPieceSelectedListener(new IListener() {
			public void fireEvent() {
				selectedTilesToHighlight = model.getTilesToHighlight();
			}
		});

		assertNull(tileHighlighterCalculator.givenPieceInfo);
		assertNull(selectedTilesToHighlight);

		model.setSelectedUserPiece(pieceInfo);

		assertSame(pieceInfo, tileHighlighterCalculator.givenPieceInfo);
		for (int i = 0; i < tileHighlighterCalculator.tilesToHighlight.length; i++) {
			assertSame(tileHighlighterCalculator.tilesToHighlight[i],
					selectedTilesToHighlight[i]);
		}
	}

	private static class GameGridDataStub implements IGameGridData {
		TileData[][] tileData;

		public TileData[][] getTileData() {
			return tileData;
		}
	}

	private static class ListenerStub implements IListener {
		boolean eventFired;

		public void fireEvent() {
			eventFired = true;
		}
	}

	private static class TileHighlighterCalculatorStub implements
			ITileHighlighterCalculator {
		TileData[] tilesToHighlight;
		PieceInfo givenPieceInfo;

		public TileData[] calculateTilesToHighlight(PieceInfo pieceInfo) {
			this.givenPieceInfo = pieceInfo;
			return tilesToHighlight;
		}
	}
}