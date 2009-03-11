package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
	public void testPresenterGetsUserStartingPositionFromModelAndGivesItToView() {
		assertNull(view.startingPosition);
		model.position = new float[] { 1f, 2f, 3f };
		new UserPiecePresenter(view, model);
		assertEquals(model.position, view.startingPosition);
	}

	@Test
	public void testPresenterNewPositionToMoveToFromModelWhenModelFiresEvent() {
		model.position = new float[] { 1f, 2f, 3f };
		new UserPiecePresenter(view, model);
		assertNull(view.moveToPosition);
		model.userModelListener.fireEvent();
		assertEquals(view.moveToPosition, model.position);
	}

	private static class UserPieceViewStub implements IUserPieceView {
		private float[] moveToPosition;
		private float[] startingPosition;

		public void setStartingPoint(float[] startingPoint) {
			this.startingPosition = startingPoint;
		}

		public void movePieceTo(float[] position) {
			moveToPosition = position;
		}
	}

	private static class UserPieceModelStub implements IUserPieceModel {
		private IListener userModelListener;
		private float[] position;

		public void addListener(IListener listener) {
			userModelListener = listener;
		}

		public float[] getCurrentPosition() {
			return position;
		}

		public void selected() {
			throw new UnsupportedOperationException();
		}
	}
}
