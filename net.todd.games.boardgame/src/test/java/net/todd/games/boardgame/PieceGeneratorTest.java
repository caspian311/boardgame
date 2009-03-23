package net.todd.games.boardgame;

import static org.junit.Assert.assertSame;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;

import org.junit.Test;

public class PieceGeneratorTest {
	@Test
	public void testPieceGeneratorPassesPickerToGivenFactory() {
		BranchGroupStub branchGroup = new BranchGroupStub();
		PieceGenerator pieceGenerator = new PieceGenerator(branchGroup);
		UserPiecesFactoryStub userPiecesFactory = new UserPiecesFactoryStub();
		IPickerFactory pickerFactory = new IPickerFactory() {
			public IPicker createPicker(IBranchGroup branchGroup) {
				throw new UnsupportedOperationException();
			}
		};

		pieceGenerator.createPieces(pickerFactory, userPiecesFactory);

		assertSame(pickerFactory, userPiecesFactory.pickerFactory);
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

		public BranchGroup getInternal() {
			throw new UnsupportedOperationException();
		}
	}

	private static class UserPiecesFactoryStub implements IUserPiecesFactory {
		IBranchGroup branchGroup;
		IPickerFactory pickerFactory;

		public IBranchGroup constructUserPieces(IPickerFactory pickerFactory) {
			this.pickerFactory = pickerFactory;
			return branchGroup;
		}
	}
}
