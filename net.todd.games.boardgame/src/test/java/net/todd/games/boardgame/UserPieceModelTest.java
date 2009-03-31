package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;
import net.todd.common.uitools.ListenerManager;

import org.junit.Before;
import org.junit.Test;

public class UserPieceModelTest {
	private GameGridModelStub gameGridModel;
	private GamePieceDataMock gamePieceData;
	private MoveValidatorStub validator;

	@Before
	public void setUp() {
		gameGridModel = new GameGridModelStub();
		gamePieceData = new GamePieceDataMock();
		validator = new MoveValidatorStub();
	}

	@Test
	public void testTeamDataIsGottenFromGamePieceDataAndAdjustedForHeightOfPiece() {
		PieceInfo pieceInfo1 = new PieceInfo();
		pieceInfo1.setPosition(new Vector3f(1f, 2f, 3f));
		pieceInfo1.setTeam(Team.ONE);
		PieceInfo pieceInfo2 = new PieceInfo();
		pieceInfo2.setPosition(new Vector3f(1f, 2f, 4f));
		pieceInfo2.setTeam(Team.TWO);
		gamePieceData.teamOne.add(pieceInfo1);
		gamePieceData.teamTwo.add(pieceInfo2);
		UserPiecesModel userPieceModel = new UserPiecesModel(gamePieceData, gameGridModel,
				validator);

		List<PieceInfo> allPieces = userPieceModel.getAllTeamOnePieces();
		allPieces.addAll(userPieceModel.getAllTeamTwoPieces());

		assertEquals(2, allPieces.size());
		assertEquals(new Vector3f(1f, 5f, 3f), allPieces.get(0).getPosition());
		assertEquals(new Vector3f(1f, 5f, 4f), allPieces.get(1).getPosition());
	}

	@Test
	public void testUserPieceModelGetsNewPositionFromSelectedTilePositionAdjustedForHeightFromGridThenMovesIt() {
		PieceInfo pieceInfo = new PieceInfo();
		pieceInfo.setId("something");
		pieceInfo.setPosition(new Vector3f());
		pieceInfo.setTeam(Team.ONE);
		gamePieceData.teamOne.add(pieceInfo);
		final IUserPiecesModel userPieceModel = new UserPiecesModel(gamePieceData, gameGridModel,
				validator);

		PieceGroupStub selectedPiece = new PieceGroupStub();
		selectedPiece.pieceInfo = pieceInfo;
		userPieceModel.setSelectedPiece(selectedPiece);

		assertNull(selectedPiece.movedToPosition);

		float x = new Random().nextFloat();
		float y = new Random().nextFloat();
		float z = new Random().nextFloat();

		gameGridModel.selectedPosition = new Vector3f(x, y, z);
		gameGridModel.positionSelectedListener.notifyListeners();

		assertEquals(selectedPiece.movedToPosition, new Vector3f(x, 5f, z));
	}

	@Test
	public void testModelPullsTeamOneInfoFromGamePieceData() {
		PieceInfo pieceInfo1 = new PieceInfo();
		pieceInfo1.setPosition(new Vector3f(1, 2, 3));
		PieceInfo pieceInfo2 = new PieceInfo();
		pieceInfo2.setPosition(new Vector3f(4, 6, 6));
		gamePieceData.teamOne.add(pieceInfo1);
		gamePieceData.teamOne.add(pieceInfo2);
		UserPiecesModel model = new UserPiecesModel(gamePieceData, gameGridModel, validator);

		List<PieceInfo> teamOne = model.getAllTeamOnePieces();

		assertEquals(2, teamOne.size());
		assertEquals(new Vector3f(1f, 5f, 3f), teamOne.get(0).getPosition());
		assertEquals(new Vector3f(4f, 5f, 6f), teamOne.get(1).getPosition());
	}

	@Test
	public void testModelPullsTeamTwoInfoFromGamePieceData() {
		PieceInfo pieceInfo1 = new PieceInfo();
		pieceInfo1.setPosition(new Vector3f(4f, 2f, 6f));
		PieceInfo pieceInfo2 = new PieceInfo();
		pieceInfo2.setPosition(new Vector3f(2f, 4f, 1f));
		gamePieceData.teamTwo.add(pieceInfo1);
		gamePieceData.teamTwo.add(pieceInfo2);
		UserPiecesModel model = new UserPiecesModel(gamePieceData, gameGridModel, validator);

		List<PieceInfo> teamTwo = model.getAllTeamTwoPieces();

		assertEquals(2, teamTwo.size());
		assertEquals(new Vector3f(4f, 5f, 6f), teamTwo.get(0).getPosition());
		assertEquals(new Vector3f(2f, 5f, 1f), teamTwo.get(1).getPosition());
	}

	@Test
	public void testModelDoesNotMoveIfValidatorFailsButWillMoveOnceValidatorPasses() {
		PieceInfo pieceInfo = new PieceInfo();
		pieceInfo.setId("something");
		pieceInfo.setPosition(new Vector3f(1f, 2f, 4f));
		pieceInfo.setTeam(Team.ONE);
		gamePieceData.teamOne.add(pieceInfo);

		validator.shouldFail = true;

		UserPiecesModel userPieceModel = new UserPiecesModel(gamePieceData, gameGridModel,
				validator);

		PieceGroupStub selectedPiece = new PieceGroupStub();
		selectedPiece.pieceInfo = pieceInfo;
		userPieceModel.setSelectedPiece(selectedPiece);

		gameGridModel.selectedPosition = new Vector3f(1f, 2f, 4f);
		gameGridModel.positionSelectedListener.notifyListeners();

		assertEquals(0, selectedPiece.movePieceToCallCount);

		validator.shouldFail = false;

		gameGridModel.selectedPosition = new Vector3f(1f, 2f, 4f);
		gameGridModel.positionSelectedListener.notifyListeners();

		assertEquals(1, selectedPiece.movePieceToCallCount);
	}

	@Test
	public void testModelCallsValidatorWithPieceInfoFromSelectedPieceAndTargetLocationFromGridModelWithAdjustedHeight() {
		PieceInfo pieceInfo = new PieceInfo();
		pieceInfo.setId("something");
		pieceInfo.setPosition(new Vector3f(1f, 2f, 4f));
		pieceInfo.setTeam(Team.ONE);
		gamePieceData.teamOne.add(pieceInfo);

		UserPiecesModel userPieceModel = new UserPiecesModel(gamePieceData, gameGridModel,
				validator);

		PieceGroupStub selectedPiece = new PieceGroupStub();
		selectedPiece.pieceInfo = pieceInfo;
		userPieceModel.setSelectedPiece(selectedPiece);

		gameGridModel.selectedPosition = new Vector3f(1f, 2f, 4f);
		gameGridModel.positionSelectedListener.notifyListeners();

		assertSame(pieceInfo, validator.pieceInfo);
		assertEquals(new Vector3f(1f, 5f, 4f), validator.targetLocation);
	}

	@Test
	public void testNothingBlowsUpIfThereIsNothingSelectedAndTheGridFiresEvent() {
		new UserPiecesModel(gamePieceData, gameGridModel, validator);

		gameGridModel.selectedPosition = new Vector3f(1f, 2f, 4f);
		gameGridModel.positionSelectedListener.notifyListeners();
	}

	private static class GameGridModelStub implements IGameGridModel {
		Vector3f selectedPosition;
		ListenerManager positionSelectedListener = new ListenerManager();
		Color3f teamOneColor;
		Color3f teamTwoColor;

		public Color3f getTeamOneColor() {
			return teamOneColor;
		}

		public Color3f getTeamTwoColor() {
			return teamTwoColor;
		}

		public void addPositionSelectedListener(IListener listener) {
			positionSelectedListener.addListener(listener);
		}

		public Vector3f getSelectedPosition() {
			return selectedPosition;
		}

		public TileData[][] getTileData() {
			throw new UnsupportedOperationException();
		}

		public void setSelectedTile(TileData position) {
			throw new UnsupportedOperationException();
		}
	}

	private static class GamePieceDataMock extends GamePieceData {
		List<PieceInfo> teamTwo = new ArrayList<PieceInfo>();
		List<PieceInfo> teamOne = new ArrayList<PieceInfo>();

		@Override
		public List<PieceInfo> getTeamOnePieces() {
			return teamOne;
		}

		@Override
		public List<PieceInfo> getTeamTwoPieces() {
			return teamTwo;
		}
	}

	private static class PieceGroupStub implements IPieceGroup {
		Vector3f movedToPosition;
		int movePieceToCallCount;
		PieceInfo pieceInfo;

		public PieceInfo getPieceInfo() {
			return pieceInfo;
		}

		public void movePieceTo(Vector3f position) {
			++movePieceToCallCount;
			movedToPosition = position;
		}
	}

	private static class MoveValidatorStub implements IMoveValidator {
		boolean shouldFail;
		PieceInfo pieceInfo;
		Vector3f targetLocation;

		public void confirmMove(PieceInfo pieceInfo, Vector3f targetLocation)
				throws ValidMoveException {
			this.pieceInfo = pieceInfo;
			this.targetLocation = new Vector3f(targetLocation);
			if (shouldFail) {
				throw new ValidMoveException();
			}
		}
	}
}
