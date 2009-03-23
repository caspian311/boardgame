package net.todd.games.boardgame;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;
import net.todd.common.uitools.ListenerManager;

public class UserPiecesModel implements IUserPiecesModel {
	private static final float PIECE_HEIGHT = 10f;
	private final ListenerManager modelListenerManager = new ListenerManager();
	private final IGameGridModel gameGridModel;
	private Vector3f currentPosition;

	public UserPiecesModel(final IGameGridModel gameGridModel) {
		this.gameGridModel = gameGridModel;
		gameGridModel.addPositionSelectedListener(new IListener() {
			public void fireEvent() {
				currentPosition = gameGridModel.getSelectedPosition();
				adjustPositionForHeight(currentPosition);
				modelListenerManager.notifyListeners();
			}
		});
	}

	private void adjustPositionForHeight(Vector3f position) {
		if (position != null) {
			position.y = PIECE_HEIGHT / 2;
		}
	}

	public void addListener(IListener userModelListener) {
		modelListenerManager.addListener(userModelListener);
	}

	public Vector3f getCurrentPosition() {
		return currentPosition;
	}

	public List<PieceInfo> getAllTeamOnePieces() {
		return convertToPieceInfo(gameGridModel.getTeamOneStartingGridPositions(),
				GameColors.TEAM_ONE_COLOR);
	}

	public List<PieceInfo> getAllTeamTwoPieces() {
		return convertToPieceInfo(gameGridModel.getTeamTwoStartingGridPositions(),
				GameColors.TEAM_TWO_COLOR);
	}

	private List<PieceInfo> convertToPieceInfo(List<Vector3f> positions, Color3f teamColor) {
		List<PieceInfo> teamTwoPieces = new ArrayList<PieceInfo>();
		for (Vector3f position : positions) {
			adjustPositionForHeight(position);

			PieceInfo piece = new PieceInfo();
			piece.setPosition(position);
			piece.setColor(teamColor);
			teamTwoPieces.add(piece);
		}
		return teamTwoPieces;
	}
}
