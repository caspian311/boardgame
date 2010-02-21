package net.todd.games.boardgame;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.vecmath.Point3d;

import net.todd.common.uitools.IListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class UserPiecesViewTest {
	private final Bounds bounds = new BoundingSphere(new Point3d(0, 0, 0), 100);

	private IBranchGroup allPiecesBranchGroup;
	private IBranchGroupFactory branchGroupFactory;
	private IUserPiecesView userPiecesView;
	private IPicker picker;
	private IListener pickerListener;

	private IPieceGroupFactory pieceGroupFactory;

	@Before
	public void setUp() {
		picker = mock(IPicker.class);
		pieceGroupFactory = mock(IPieceGroupFactory.class);
		branchGroupFactory = mock(IBranchGroupFactory.class);

		allPiecesBranchGroup = mock(IBranchGroup.class);

		doReturn(allPiecesBranchGroup).when(branchGroupFactory)
				.createBranchGroup();

		userPiecesView = new UserPiecesView(bounds, picker, branchGroupFactory,
				pieceGroupFactory);

		ArgumentCaptor<IListener> pickerListenerCaptor = ArgumentCaptor
				.forClass(IListener.class);
		verify(picker).addListener(pickerListenerCaptor.capture());
		pickerListener = pickerListenerCaptor.getValue();
	}

	@Test
	public void testAddingPiecesToViewAddsItToCorrectBranchGroup() {
		PieceInfo pieceInfo1 = mock(PieceInfo.class);
		PieceInfo pieceInfo2 = mock(PieceInfo.class);
		PieceInfo pieceInfo3 = mock(PieceInfo.class);

		PieceGroup pieceGroup1 = mock(PieceGroup.class);
		PieceGroup pieceGroup2 = mock(PieceGroup.class);
		PieceGroup pieceGroup3 = mock(PieceGroup.class);

		doReturn(pieceGroup1).when(pieceGroupFactory).createPieceGroup(bounds,
				pieceInfo1);
		doReturn(pieceGroup2).when(pieceGroupFactory).createPieceGroup(bounds,
				pieceInfo2);
		doReturn(pieceGroup3).when(pieceGroupFactory).createPieceGroup(bounds,
				pieceInfo3);

		userPiecesView.addPiece(pieceInfo1);
		userPiecesView.addPiece(pieceInfo2);
		userPiecesView.addPiece(pieceInfo3);

		verify(allPiecesBranchGroup).addChild(pieceGroup1);
		verify(allPiecesBranchGroup).addChild(pieceGroup2);
		verify(allPiecesBranchGroup).addChild(pieceGroup3);
	}

	@Test
	public void testListenersToViewAreNotifiedWhenSomethingIsPicked() {
		IListener listener = mock(IListener.class);
		userPiecesView.addPieceSelectedListener(listener);

		pickerListener.fireEvent();
		verify(listener).fireEvent();
	}

	@Test
	public void testPieceSelectedListenerNotifiedWhenPickerListenerFiresEvent() {
		IListener listener = mock(IListener.class);
		userPiecesView.addPieceSelectedListener(listener);
		
		pickerListener.fireEvent();
		
		verify(listener).fireEvent();
	}
	
	@Test
	public void testViewGetsSelectedPieceBeforeNotifyingListeners() {
		SelectablePiece selectedNode = mock(SelectablePiece.class);
		IPieceGroup selectedPiece = mock(IPieceGroup.class);
		
		doReturn(selectedNode).when(picker).getSelectedNode();
		doReturn(selectedPiece).when(selectedNode).getPiece();
		
		pickerListener.fireEvent();
		
		assertSame(selectedPiece, userPiecesView.getSelectedPiece());
	}
}
