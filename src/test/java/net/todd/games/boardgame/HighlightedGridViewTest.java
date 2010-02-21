package net.todd.games.boardgame;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class HighlightedGridViewTest {
	private IHighlightedGridView highlightedGridView;
	private IBranchGroup branchGroup;
	private IBranchGroupFactory branchGroupFactory;
	private IBranchGroup highlitedStuffBranchGroup;
	private ITileFactory tileFactory;

	@Before
	public void setUp() {
		branchGroupFactory = mock(IBranchGroupFactory.class);
		branchGroup = mock(IBranchGroup.class);
		highlitedStuffBranchGroup = mock(IBranchGroup.class);

		tileFactory = mock(ITileFactory.class);
		
		when(branchGroupFactory.createBranchGroup()).thenReturn(branchGroup, highlitedStuffBranchGroup);

		highlightedGridView = new HighlightedGridView(branchGroupFactory, tileFactory);
	}

	@Test
	public void testBranchGroupGottenFromGridViewWasGeneratedByFactoryGivenToTheGridView() {
		assertSame(branchGroup, highlightedGridView.getBranchGroup());
	}

	@Test
	public void testBranchGroupChildrenAreClearedFirstBeforeAddingHighlitedStuff() {
		highlightedGridView.highlightTiles(new TileData[] {});
		
		InOrder inOrder = inOrder(branchGroup);
		inOrder.verify(branchGroup).removeAllChildren();
		inOrder.verify(branchGroup).addChild(highlitedStuffBranchGroup);
	}

	@Test
	public void testHighlightGivenTiles() {
		TileData tileDatum1 = mock(TileData.class);
		TileData tileDatum2 = mock(TileData.class);
		TileData tileDatum3 = mock(TileData.class);
		
		doReturn(new float[]{1f, 1f, 1f}).when(tileDatum1).getPosition();
		doReturn(new float[]{1f, 1f, 1f}).when(tileDatum2).getPosition();
		doReturn(new float[]{1f, 1f, 1f}).when(tileDatum3).getPosition();
		
		Tile tile1 = mock(Tile.class);
		Tile tile2 = mock(Tile.class);
		Tile tile3 = mock(Tile.class);
		
		doReturn(tile1).when(tileFactory).createTile(tileDatum1);
		doReturn(tile2).when(tileFactory).createTile(tileDatum2);
		doReturn(tile3).when(tileFactory).createTile(tileDatum3);
		
		highlightedGridView.highlightTiles(new TileData[] {tileDatum1, tileDatum2, tileDatum3});
		
		verify(highlitedStuffBranchGroup).addChild(tile1);
		verify(highlitedStuffBranchGroup).addChild(tile2);
		verify(highlitedStuffBranchGroup).addChild(tile3);
	}

	@Test
	public void testBranchGroupChildrenAreRemovedWhenClearHighlightedTilesIsSelected() {
		highlightedGridView.clearHighlightedTiles();
		
		verify(branchGroup).removeAllChildren();
	}
}
