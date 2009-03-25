package net.todd.games.boardgame;

import java.util.List;
import java.util.UUID;

import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;
import net.todd.common.uitools.ListenerManager;

public class UserPiecesModel implements IUserPiecesModel {
	private static final float PIECE_HEIGHT = 10f;
	private final ListenerManager moveListenerManager = new ListenerManager();
	private Vector3f targetLocation;
	private final GamePieceData gamePieceData;

	public UserPiecesModel(GamePieceData gamePieceData, final IGameGridModel gameGridModel) {
		this.gamePieceData = gamePieceData;
		gameGridModel.addPositionSelectedListener(new IListener() {
			public void fireEvent() {
				targetLocation = gameGridModel.getSelectedPosition();
				adjustPositionForHeight(targetLocation);
				moveListenerManager.notifyListeners();
			}
		});
	}

	private void adjustPositionForHeight(Vector3f position) {
		if (position != null) {
			position.y = PIECE_HEIGHT / 2;
		}
	}

	public void addMoveListener(IListener moveListener) {
		moveListenerManager.addListener(moveListener);
	}

	public Vector3f getMoveToLocation() {
		return targetLocation;
	}

	public List<PieceInfo> getAllTeamOnePieces() {
		List<PieceInfo> adjustPiecesForHeight = adjustPiecesForHeight(gamePieceData
				.getTeamOnePieces());
		for (PieceInfo pieceInfo : adjustPiecesForHeight) {
			pieceInfo.setId(UUID.randomUUID().toString());
		}
		return adjustPiecesForHeight;
	}

	private List<PieceInfo> adjustPiecesForHeight(List<PieceInfo> teamOnePieces) {
		for (PieceInfo pieceInfo : teamOnePieces) {
			adjustPositionForHeight(pieceInfo.getPosition());
		}
		return teamOnePieces;
	}

	public List<PieceInfo> getAllTeamTwoPieces() {
		List<PieceInfo> adjustPiecesForHeight = adjustPiecesForHeight(gamePieceData
				.getTeamTwoPieces());
		for (PieceInfo pieceInfo : adjustPiecesForHeight) {
			pieceInfo.setId(UUID.randomUUID().toString());
		}
		return adjustPiecesForHeight;
	}
}
