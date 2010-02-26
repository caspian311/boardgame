package net.todd.games.boardgame;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import net.todd.common.uitools.IListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class UserPiecePresenterTest {
	private IUserPiecesView view;
	private IUserPiecesModel model;
	private PieceInfo pieceInfo1;
	private PieceInfo pieceInfo2;
	private PieceInfo pieceInfo3;
	private IListener pieceSelectedListener;

	@Before
	public void setUp() {
		view = mock(IUserPiecesView.class);
		model = mock(IUserPiecesModel.class);
		
		pieceInfo1 = PieceFixture.createPieceInfo();
		pieceInfo2 = PieceFixture.createPieceInfo();
		pieceInfo3 = PieceFixture.createPieceInfo();
		
		doReturn(Arrays.asList(pieceInfo1, pieceInfo2, pieceInfo3)).when(model).getAllPieces();
		
		new UserPiecesPresenter(view, model);
		
		ArgumentCaptor<IListener> pieceSelectedListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(view).addPieceSelectedListener(pieceSelectedListenerCaptor.capture());
		pieceSelectedListener = pieceSelectedListenerCaptor.getValue();
	}

	@Test
	public void testPresenterGetsPiecesFromModelAndAddsAllPiecesToView() {
		verify(view).addPiece(pieceInfo1);
		verify(view).addPiece(pieceInfo2);
		verify(view).addPiece(pieceInfo3);
	}

	@Test
	public void testWhenPieceSelectedOnViewModelGetsNotified() {
		IPieceGroup selectedPiece = mock(IPieceGroup.class);
		doReturn(selectedPiece).when(view).getSelectedPiece();
		
		pieceSelectedListener.fireEvent();

		verify(model).setSelectedPiece(selectedPiece);
	}
}
