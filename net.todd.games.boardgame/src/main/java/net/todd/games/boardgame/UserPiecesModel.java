package net.todd.games.boardgame;

import java.util.ArrayList;
import java.util.List;

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
		return currentPosition;// adjustCurrentPositionForHeight();
	}

	public List<PieceInfo> getAllPieces() {
		List<PieceInfo> allPieces = new ArrayList<PieceInfo>();
		for (Vector3f position : gameGridModel.getTeamOneStartingGridPositions()) {
			adjustPositionForHeight(position);
			
			PieceInfo piece = new PieceInfo();
			piece.setPosition(position);
			allPieces.add(piece);
		}
		return allPieces;
	}
}
