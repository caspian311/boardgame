package net.todd.games.boardgame;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import net.todd.common.uitools.IListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class GameGridViewTest {
	private IBranchGroupFactory branchGroupFactory;
	private IBranchGroup branchGroup;
	private IPicker picker;
	private IGameGridView gameGridView;
	private IListener pickerListener;
	private TileData mockSelectedTileData;
	private ITileFactory tileFactory;

	@Before
	public void setup() {
		branchGroup = mock(IBranchGroup.class);
		branchGroupFactory = mock(IBranchGroupFactory.class);
		picker = mock(IPicker.class);
		tileFactory = mock(ITileFactory.class);
		
		doReturn(branchGroup).when(branchGroupFactory).createBranchGroup();
		
		Tile tile = mock(Tile.class);
		doReturn(tile).when(picker).getSelectedNode();
		mockSelectedTileData = mock(TileData.class);
		doReturn(mockSelectedTileData).when(tile).getTileData();
		
		gameGridView = new GameGridView(picker, branchGroupFactory, tileFactory);
		
		ArgumentCaptor<IListener> pickerListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(picker).addListener(pickerListenerCaptor.capture());
		pickerListener = pickerListenerCaptor.getValue();
	}

	@Test
	public void testViewNotifiesItsListenersWhenPickerListenerFiresWithATileSelectedNode() {
		IListener listener1 = mock(IListener.class);
		IListener listener2 = mock(IListener.class);
		gameGridView.addTileSelectedListener(listener1);
		gameGridView.addTileSelectedListener(listener2);

		pickerListener.fireEvent();

		verify(listener1).fireEvent();
		verify(listener2).fireEvent();
	}
	
	@Test
	public void whenPickerListenerFiresWithATileSelectedNodeMaketheSelectedTileNodeAvailable() {
		pickerListener.fireEvent();

		assertSame(mockSelectedTileData, gameGridView.getSelectedTile());
	}

	@Test
	public void testViewReturnsBranchCreatedByTheFactory() {
		assertSame(branchGroup, gameGridView.getBranchGroup());
	}

	@Test
	public void testViewConstructsGridGivenTileData() {
		TileData tileDatum1 = mock(TileData.class);
		TileData tileDatum2 = mock(TileData.class);
		TileData tileDatum3 = mock(TileData.class);
		TileData tileDatum4 = mock(TileData.class);
		List<TileData> tileData = new ArrayList<TileData>();
		tileData.add(tileDatum1);
		tileData.add(tileDatum2);
		tileData.add(tileDatum3);
		tileData.add(tileDatum4);
		
		Tile tile1 = mock(Tile.class);
		Tile tile2 = mock(Tile.class);
		Tile tile3 = mock(Tile.class);
		Tile tile4 = mock(Tile.class);
		doReturn(tile1).when(tileFactory).createTile(tileDatum1);
		doReturn(tile2).when(tileFactory).createTile(tileDatum2);
		doReturn(tile3).when(tileFactory).createTile(tileDatum3);
		doReturn(tile4).when(tileFactory).createTile(tileDatum4);
		
		gameGridView.constructGrid(tileData);
		
		verify(branchGroup).addChild(tile1);
		verify(branchGroup).addChild(tile2);
		verify(branchGroup).addChild(tile3);
		verify(branchGroup).addChild(tile4);
	}
}
