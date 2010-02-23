package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;

import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;

import org.junit.Before;
import org.junit.Test;

public class GameGridModelTest {
	private IGameGridModel model;
	private ITileHighlighterCalculator tileHighlighterCalculator;
	private IGameGridData gameGridData;

	@Before
	public void setUp() {
		gameGridData = mock(IGameGridData.class);
		tileHighlighterCalculator = mock(ITileHighlighterCalculator.class);
		
		model = new GameGridModel(gameGridData, tileHighlighterCalculator);
	}

	@Test
	public void testGetTileDataPullsFromGridData() {
		TileData tileData1 = mock(TileData.class);
		TileData tileData2 = mock(TileData.class);
		TileData tileData3 = mock(TileData.class);
		TileData tileData4 = mock(TileData.class);
		TileData[][] tileData = new TileData[][] {{tileData1, tileData2}, {tileData3, tileData4}};
		doReturn(tileData).when(gameGridData).getTileData();
		
		List<TileData> listOfTiles = model.getTileData();
		assertEquals(4, listOfTiles.size());
		assertSame(tileData1, listOfTiles.get(0));
		assertSame(tileData2, listOfTiles.get(1));
		assertSame(tileData3, listOfTiles.get(2));
		assertSame(tileData4, listOfTiles.get(3));
	}

	@Test
	public void testModelNotifiesListenersWhenAGridPositionIsSelected() {
		TileData tileData = mock(TileData.class);
		doReturn(new float[] { 1f, 2f, 3f }).when(tileData).getPosition();
		
		IListener listener = mock(IListener.class);
		model.addTileSelectedListener(listener);
		
		model.setSelectedTile(tileData);

		verify(listener).fireEvent();
	}

	@Test
	public void testModelMakesSelectedPositionInformationAvailableBeforeNotifyingListeners() {
		float[] position = new float[] { 1f, 2f, 3f };
		
		TileData tileData = mock(TileData.class);
		doReturn(position).when(tileData).getPosition();
		
		model.setSelectedTile(tileData);
		
		Vector3f selectedTilePosition = model.getSelectedTileLocation();
		assertEquals(position[0], selectedTilePosition.x);
		assertEquals(position[1], selectedTilePosition.y);
		assertEquals(position[2], selectedTilePosition.z);
	}

	@Test
	public void testSettingSelectedUserPieceNotifiesAllListeners() {
		IListener listener = mock(IListener.class);
		model.addUserPieceSelectedListener(listener);

		model.setSelectedUserPiece(null);

		verify(listener).fireEvent();
	}

	@Test
	public void testTileHighlighterIsGivenSelectedPiece() {
		PieceInfo pieceInfo = mock(PieceInfo.class);
		model.setSelectedUserPiece(pieceInfo);
		
		model.getTilesToHighlight();
		
		verify(tileHighlighterCalculator).calculateTilesToHighlight(pieceInfo);
	}
	
	@Test
	public void testReturnWhateverCalculatorReturns() {
		TileData[] expectedHighlightedTiles = new TileData[]{};
		doReturn(expectedHighlightedTiles).when(tileHighlighterCalculator).calculateTilesToHighlight(null);

		TileData[] actualHighlightedTiles = model.getTilesToHighlight();
		
		assertSame(actualHighlightedTiles, expectedHighlightedTiles);
	}
}