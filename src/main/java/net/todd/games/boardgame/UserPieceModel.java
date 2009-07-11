package net.todd.games.boardgame;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UserPieceModel implements IUserPiecesModel {
	private static final Log log = LogFactory.getLog(UserPieceModel.class);

	public static final float PIECE_HEIGHT = 10f;
	private final List<PieceInfo> allPieces = new ArrayList<PieceInfo>();
	private IPieceGroup selectedPieceToMove;
	private final IGameGridModel gameGridModel;

	private final IGridPathFinder gridPathFinder;

	public UserPieceModel(IGameState gameState, final IGameGridModel gameGridModel,
			final IMoveValidator moveValidator, IGridPathFinder gridPathFinder) {
		this.gameGridModel = gameGridModel;
		this.gridPathFinder = gridPathFinder;

		for (PieceInfo pieceInfo : gameState.getAllPieces()) {
			allPieces.add(adjustPieceForHeight(pieceInfo));
		}

		gameGridModel.addTileSelectedListener(new IListener() {
			public void fireEvent() {
				try {
					if (selectedPieceToMove != null) {
						Vector3f targetLocation = gameGridModel.getSelectedTileLocation();
						adjustPositionForHeight(targetLocation);
						Vector3f currentLocation = selectedPieceToMove.getPieceInfo().getPosition();
						moveValidator.confirmMove(selectedPieceToMove.getPieceInfo(),
								targetLocation);
						moveAction(selectedPieceToMove, currentLocation, targetLocation);
					}
				} catch (ValidMoveException e) {
					log.info(e.getMessage());
				}
			}
		});
	}

	private void moveAction(IPieceGroup selectedPieceToMove, Vector3f currentLocation,
			Vector3f targetLocation) {
		List<Vector3f> path = gridPathFinder.getPath(currentLocation, targetLocation);

		selectedPieceToMove.movePieceAlongPath(path);
	}

	private void adjustPositionForHeight(Vector3f position) {
		if (position != null) {
			position.y = PIECE_HEIGHT / 2;
		}
	}

	private PieceInfo adjustPieceForHeight(PieceInfo pieceInfo) {
		adjustPositionForHeight(pieceInfo.getPosition());
		return pieceInfo;
	}

	public List<PieceInfo> getAllPieces() {
		return allPieces;
	}

	public void setSelectedPiece(IPieceGroup selectedPiece) {
		this.selectedPieceToMove = selectedPiece;
		gameGridModel.setSelectedUserPiece(selectedPiece.getPieceInfo());
	}
}
