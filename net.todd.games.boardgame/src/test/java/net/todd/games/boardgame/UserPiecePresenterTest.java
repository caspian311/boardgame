package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
		model.startingPosition = new float[] { 1f, 2f, 3f };
		new UserPiecePresenter(view, model);
		assertEquals(model.startingPosition, view.startingPosition);
	}

	private static class UserPieceViewStub implements IUserPieceView {
		private float[] startingPosition;

		public void setStartingPoint(float[] startingPoint) {
			this.startingPosition = startingPoint;
		}
	}

	private static class UserPieceModelStub implements IUserPieceModel {
		private float[] startingPosition;

		public float[] getStartingPoint() {
			return startingPosition;
		}
	}
}
