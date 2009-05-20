package net.todd.games.boardgame;

import static org.junit.Assert.assertSame;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;

import org.junit.Test;

public class HighlightedGridViewTest {
	@Test
	public void testBranchGroupGottenFromGridViewWasGeneratedByFactoryGivenToTheGridView() {
		BranchGroupFactoryStub branchGroupFactory = new BranchGroupFactoryStub();
		branchGroupFactory.branchGroup = new BranchGroupStub();
		IHighlightedGridView highlightedGridView = new HighlightedGridView(branchGroupFactory);
		assertSame(branchGroupFactory.branchGroup, highlightedGridView.getBranchGroup());
	}

	private static class BranchGroupFactoryStub implements IBranchGroupFactory {
		private IBranchGroup branchGroup;

		public IBranchGroup createBranchGroup() {
			return branchGroup;
		}
	}

	private static class BranchGroupStub implements IBranchGroup {
		public void addChild(Node node) {
			throw new UnsupportedOperationException();
		}

		public void addChild(IBranchGroup child) {
			throw new UnsupportedOperationException();
		}

		public void compile() {
			throw new UnsupportedOperationException();
		}

		public BranchGroup getInternal() {
			throw new UnsupportedOperationException();
		}

		public void removeAllChildren() {
			throw new UnsupportedOperationException();
		}
	}
}
