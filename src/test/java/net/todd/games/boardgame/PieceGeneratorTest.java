package net.todd.games.boardgame;

import static org.junit.Assert.assertSame;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;

import net.todd.common.uitools.IListener;

import org.junit.Test;

public class PieceGeneratorTest {
	@Test
	public void testPieceGeneratorPassesPickerToGivenFactory() {
		BranchGroupStub branchGroup = new BranchGroupStub();
		PieceGenerator pieceGenerator = new PieceGenerator(branchGroup);
		UserPiecesFactoryStub userPiecesFactory = new UserPiecesFactoryStub();
		PickerStub picker = new PickerStub();

		pieceGenerator.createPieces(picker, userPiecesFactory);

		assertSame(picker, userPiecesFactory.picker);
	}

	@Test
	public void testPieceGeneratorReturnsFactoryCreatedBranchGroup() {
		BranchGroupStub branchGroup = new BranchGroupStub();
		PieceGenerator pieceGenerator = new PieceGenerator(branchGroup);
		UserPiecesFactoryStub userPiecesFactory = new UserPiecesFactoryStub();

		pieceGenerator.createPieces(null, userPiecesFactory);

		assertSame(branchGroup.addedChild, userPiecesFactory.branchGroup);
	}

	private static class BranchGroupStub implements IBranchGroup {
		IBranchGroup addedChild;

		public void addChild(IBranchGroup child) {
			addedChild = child;
		}

		public void addChild(Node node) {
			throw new UnsupportedOperationException();
		}

		public void compile() {
			throw new UnsupportedOperationException();
		}

		public BranchGroup underlyingImplementation() {
			throw new UnsupportedOperationException();
		}

		public void removeAllChildren() {
			throw new UnsupportedOperationException();
		}
	}

	private static class UserPiecesFactoryStub implements IUserPiecesFactory {
		IBranchGroup branchGroup;
		IPicker picker;

		public IBranchGroup constructUserPieces(IPicker picker) {
			this.picker = picker;
			return branchGroup;
		}
	}

	private static class PickerStub implements IPicker {
		public void addListener(IListener listener) {
			throw new UnsupportedOperationException();
		}

		public Node getSelectedNode() {
			throw new UnsupportedOperationException();
		}

		public void removeListener(IListener listener) {
			throw new UnsupportedOperationException();
		}
	}
}
