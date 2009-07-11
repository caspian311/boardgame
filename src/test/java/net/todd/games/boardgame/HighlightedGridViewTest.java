package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;

import org.junit.Test;

public class HighlightedGridViewTest {
	@Test
	public void testBranchGroupGottenFromGridViewWasGeneratedByFactoryGivenToTheGridView() {
		BranchGroupFactoryStub branchGroupFactory = new BranchGroupFactoryStub();
		branchGroupFactory.firstBranchGroup = new BranchGroupStub();
		IHighlightedGridView highlightedGridView = new HighlightedGridView(
				branchGroupFactory);
		assertSame(branchGroupFactory.firstBranchGroup, highlightedGridView
				.getBranchGroup());
	}

	@Test
	public void testBranchGroupChildrenAreClearedFirstBeforeHighlitingTiles() {
		BranchGroupFactoryStub branchGroupFactory = new BranchGroupFactoryStub();
		BranchGroupStub branchGroupStub = new BranchGroupStub();
		branchGroupFactory.firstBranchGroup = branchGroupStub;

		IHighlightedGridView highlightedGridView = new HighlightedGridView(
				branchGroupFactory);

		assertEquals(0, branchGroupStub.removeAllChildrenCallCount);
		highlightedGridView.highlightTiles(new TileData[] {});
		assertEquals(1, branchGroupStub.removeAllChildrenCallCount);
	}

	@Test
	public void testHighlightGivenTiles() {
		BranchGroupFactoryStub branchGroupFactory = new BranchGroupFactoryStub();
		BranchGroupStub firstBranchGroupStub = new BranchGroupStub();
		BranchGroupStub secondBranchGroupStub = new BranchGroupStub();
		branchGroupFactory.firstBranchGroup = firstBranchGroupStub;
		branchGroupFactory.secondBranchGroup = secondBranchGroupStub;
		IHighlightedGridView highlightedGridView = new HighlightedGridView(
				branchGroupFactory);

		int tileSize = new Random().nextInt(100);
		if (tileSize == 0) {
			tileSize = 1;
		}
		TileData[] tiles = new TileData[tileSize];
		tiles[0] = new TileData();
		tiles[0].setPosition(new float[] { 0f, 0f, 0f });
		tiles[0].setColor(new float[] { 0f, 0f, 0f });
		tiles[0].setSize(1f);

		for (int i = 1; i < tileSize; i++) {
			tiles[i] = new TileData();
			tiles[i].setPosition(new float[] { 0f, 0f, 0f });
			tiles[i].setColor(new float[] { 0f, 0f, 0f });
			tiles[i].setSize(1f);
		}

		assertNull(firstBranchGroupStub.group);
		highlightedGridView.highlightTiles(tiles);
		assertSame(firstBranchGroupStub.group, secondBranchGroupStub);
		assertEquals(tiles.length, secondBranchGroupStub.allChildren.size());

		assertTrue(secondBranchGroupStub.allChildren.get(0) instanceof Tile);
	}

	@Test
	public void testBranchGroupChildrenAreRemovedWhenClearHighlightedTilesIsSelected() {
		BranchGroupFactoryStub branchGroupFactory = new BranchGroupFactoryStub();
		BranchGroupStub branchGroupStub = new BranchGroupStub();
		branchGroupFactory.firstBranchGroup = branchGroupStub;
		IHighlightedGridView highlightedGridView = new HighlightedGridView(
				branchGroupFactory);

		assertEquals(0, branchGroupStub.removeAllChildrenCallCount);
		highlightedGridView.clearHighlightedTiles();
		assertEquals(1, branchGroupStub.removeAllChildrenCallCount);
	}

	private static class BranchGroupFactoryStub implements IBranchGroupFactory {
		private int call;
		private BranchGroupStub secondBranchGroup;
		private IBranchGroup firstBranchGroup;

		public IBranchGroup createBranchGroup() {
			call++;
			return call == 1 ? firstBranchGroup : secondBranchGroup;
		}
	}

	private static class BranchGroupStub implements IBranchGroup {
		private int removeAllChildrenCallCount;
		private IBranchGroup group;
		private final List<Node> allChildren = new ArrayList<Node>();

		public void addChild(IBranchGroup group) {
			this.group = group;
		}

		public void addChild(Node child) {
			allChildren.add(child);
		}

		public void compile() {
			throw new UnsupportedOperationException();
		}

		public BranchGroup underlyingImplementation() {
			throw new UnsupportedOperationException();
		}

		public void removeAllChildren() {
			removeAllChildrenCallCount++;
		}
	}
}
