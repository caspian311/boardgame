package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;

import org.junit.Before;
import org.junit.Test;

public class UserPiecesViewTest {
	private IPieceGroup selectedPiece;

	private final Bounds bounds = new BoundingSphere(new Point3d(0, 0, 0), 100);
	private PickerFactoryStub pickerFactory;
	private BranchGroupStub mainBranchGroup;
	private BranchGroupFactoryStub branchGroupFactory;

	@Before
	public void setUp() {
		pickerFactory = new PickerFactoryStub();

		mainBranchGroup = new BranchGroupStub();
		branchGroupFactory = new BranchGroupFactoryStub();
		branchGroupFactory.mainBranchGroup = mainBranchGroup;
	}

	@Test
	public void testAddingPiecesToViewAddsItToCorrectBranchGroup() {
		PickerStub picker = new PickerStub();
		pickerFactory.picker = picker;
		UserPiecesView userPiecesView = new UserPiecesView(bounds, pickerFactory,
				branchGroupFactory);

		PieceInfo pieceInfo1 = new PieceInfo();
		pieceInfo1.setPosition(new Vector3f(1f, 2f, 3f));
		pieceInfo1.setColor(new Color3f(1f, 2f, 3f));
		pieceInfo1.setTeam(Team.ONE);

		PieceInfo pieceInfo2 = new PieceInfo();
		pieceInfo2.setPosition(new Vector3f(1f, 2f, 3f));
		pieceInfo2.setColor(new Color3f(1f, 2f, 3f));
		pieceInfo2.setTeam(Team.TWO);

		PieceInfo pieceInfo3 = new PieceInfo();
		pieceInfo3.setPosition(new Vector3f(1f, 2f, 3f));
		pieceInfo3.setColor(new Color3f(1f, 2f, 3f));
		pieceInfo3.setTeam(Team.TWO);

		userPiecesView.addPiece(pieceInfo1);
		userPiecesView.addPiece(pieceInfo2);
		userPiecesView.addPiece(pieceInfo3);

		assertEquals(3, mainBranchGroup.nodeChildren.size());
	}

	@Test
	public void testListenersToViewAreNotifiedWhenSomethingIsPicked() {
		ListenerStub listener = new ListenerStub();
		PickerStub picker = new PickerStub();
		pickerFactory.picker = picker;
		UserPiecesView userPiecesView = new UserPiecesView(bounds, pickerFactory,
				branchGroupFactory);

		picker.listener.fireEvent();

		assertFalse(listener.eventFired);

		userPiecesView.addPieceSelectedListener(listener);

		picker.listener.fireEvent();

		assertTrue(listener.eventFired);
	}

	@Test
	public void testViewGetsSelectedPieceBeforeNotifyingListeners() {
		PickerStub picker = new PickerStub();
		PieceInfo pieceInfo = new PieceInfo();
		pieceInfo.setPosition(new Vector3f());
		pieceInfo.setColor(new Color3f());
		picker.selectedNode = new SelectablePiece(new PieceGroupMock(pieceInfo));
		pickerFactory.picker = picker;
		final UserPiecesView userPiecesView = new UserPiecesView(bounds, pickerFactory,
				branchGroupFactory);
		userPiecesView.addPieceSelectedListener(new IListener() {
			public void fireEvent() {
				selectedPiece = userPiecesView.getSelectedPiece();
			}
		});

		assertNull(selectedPiece);

		picker.listener.fireEvent();

		assertSame(selectedPiece, picker.selectedNode.getPiece());
	}

	private static class PickerFactoryStub implements IPickerFactory {
		PickerStub picker;

		public IPicker createPicker(IBranchGroup branchGroup) {
			return picker;
		}
	}

	private static class PickerStub implements IPicker {
		IListener listener;
		SelectablePiece selectedNode;

		public void addListener(IListener listener) {
			this.listener = listener;
		}

		public Node getSelectedNode() {
			return selectedNode;
		}

		public void removeListener(IListener listener) {
			throw new UnsupportedOperationException();
		}
	}

	private static class BranchGroupFactoryStub implements IBranchGroupFactory {
		IBranchGroup mainBranchGroup;

		public IBranchGroup createBranchGroup() {
			return mainBranchGroup;
		}
	}

	private static class BranchGroupStub implements IBranchGroup {
		List<Node> nodeChildren = new ArrayList<Node>();
		List<IBranchGroup> bgChildren = new ArrayList<IBranchGroup>();

		public void addChild(Node child) {
			nodeChildren.add(child);
		}

		public void addChild(IBranchGroup child) {
			bgChildren.add(child);
		}

		public void compile() {
			throw new UnsupportedOperationException();
		}

		public BranchGroup getInternal() {
			throw new UnsupportedOperationException();
		}
	}

	private static class ListenerStub implements IListener {
		boolean eventFired;

		public void fireEvent() {
			eventFired = true;
		}
	}

	private static class PieceGroupMock extends PieceGroup {
		public PieceGroupMock(PieceInfo pieceInfo) {
			super(null, pieceInfo);
		}

		@Override
		public void moveTo(BranchGroup branchGroup) {
			// do nothing
		}
	}
}
