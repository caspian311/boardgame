package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.Node;
import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;

import org.junit.Before;
import org.junit.Test;

public class UserPiecePresenterTest {
	private UserPieceViewStub view;
	private UserPieceModelStub model;

	@Before
	public void setUp() {
		view = new UserPieceViewStub();
		model = new UserPieceModelStub();
	}

	@Test
	public void testPresenterGetsPiecesFromModelAndAddsPiecesToView() {
		PieceInfo pieceInfo1 = PieceFixture.createPieceInfo();
		PieceInfo pieceInfo2 = PieceFixture.createPieceInfo();
		model.pieces.add(pieceInfo1);
		model.pieces.add(pieceInfo2);
		assertEquals(0, view.startingPositions.size());
		new UserPiecesPresenter(view, model);

		assertEquals(pieceInfo1.getPosition(), view.startingPositions.get(0));
		assertEquals(pieceInfo2.getPosition(), view.startingPositions.get(1));
	}

	@Test
	public void testWhenModelFiresEventPresenterTellsViewWhereToMovePiece() {
		PieceInfo pieceInfo1 = PieceFixture.createPieceInfo();
		model.pieces.add(pieceInfo1);
		model.position = new Vector3f(new float[] { 1f, 2f, 3f });
		new UserPiecesPresenter(view, model);
		assertNull(view.moveToPosition);
		model.userModelListener.fireEvent();
		assertEquals(view.moveToPosition, model.position);
	}

	private static class UserPieceViewStub implements IUserPiecesView {
		Vector3f moveToPosition;
		List<Vector3f> startingPositions = new ArrayList<Vector3f>();

		public void addPiece(Vector3f startingPoint) {
			startingPositions.add(startingPoint);
		}

		public void movePieceTo(Vector3f position) {
			moveToPosition = position;
		}

		public Node getBranchGroup() {
			throw new UnsupportedOperationException();
		}
	}

	private static class UserPieceModelStub implements IUserPiecesModel {
		IListener userModelListener;
		Vector3f position;
		final List<PieceInfo> pieces = new ArrayList<PieceInfo>();

		public void addListener(IListener listener) {
			userModelListener = listener;
		}

		public List<PieceInfo> getAllPieces() {
			return pieces;
		}

		public Vector3f getCurrentPosition() {
			return position;
		}
	}
}
