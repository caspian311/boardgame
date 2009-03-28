package net.todd.games.boardgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;

public class UserPiecesModel implements IUserPiecesModel {
	private static final float PIECE_HEIGHT = 10f;
	private Vector3f targetLocation;
	private List<PieceInfo> teamOnePieces;
	private List<PieceInfo> teamTwoPieces;
	private IPieceGroup selectedPieceToMove;
	private final Map<String, PieceInfo> piecesMap = new HashMap<String, PieceInfo>();
	private Team teamToMove;

	public UserPiecesModel(GamePieceData gamePieceData, final IGameGridModel gameGridModel) {
		gameGridModel.addPositionSelectedListener(new IListener() {
			public void fireEvent() {
				if (selectedPieceToMove != null) {
					targetLocation = gameGridModel.getSelectedPosition();
					moveAction();
				}
			}
		});

		populatePiecesMap(gamePieceData);
		teamToMove = Team.ONE;
	}

	private void moveAction() {
		if (teamToMove.equals(selectedPieceToMove.getPieceInfo().getTeam())) {
			if (!isLocationOccupied(targetLocation)) {
				adjustPositionForHeight(targetLocation);
				selectedPieceToMove.movePieceTo(targetLocation);
				PieceInfo pieceInfo = piecesMap.get(selectedPieceToMove.getPieceInfo().getId());
				pieceInfo.setPosition(targetLocation);
				updateTeamToMove();
			}
		}
	}

	private void updateTeamToMove() {
		if (teamToMove.equals(Team.ONE)) {
			teamToMove = Team.TWO;
		} else {
			teamToMove = Team.ONE;
		}
	}

	private void populatePiecesMap(GamePieceData gamePieceData) {
		teamOnePieces = adjustPiecesForHeightAndGiveId(gamePieceData.getTeamOnePieces());
		teamTwoPieces = adjustPiecesForHeightAndGiveId(gamePieceData.getTeamTwoPieces());

		for (PieceInfo pieceInfo : teamOnePieces) {
			piecesMap.put(pieceInfo.getId(), pieceInfo);
		}
		for (PieceInfo pieceInfo : teamTwoPieces) {
			piecesMap.put(pieceInfo.getId(), pieceInfo);
		}
	}

	private boolean isLocationOccupied(Vector3f targetLocation) {
		boolean occupied = false;
		List<PieceInfo> allPieces = getAllPieces();
		for (PieceInfo pieceInfo : allPieces) {
			if (pieceInfo.getPosition().x == targetLocation.x) {
				if (pieceInfo.getPosition().z == targetLocation.z) {
					occupied = true;
					break;
				}
			}
		}
		return occupied;
	}

	private List<PieceInfo> getAllPieces() {
		List<PieceInfo> allPieces = new ArrayList<PieceInfo>();

		allPieces.addAll(piecesMap.values());

		return allPieces;
	}

	private void adjustPositionForHeight(Vector3f position) {
		if (position != null) {
			position.y = PIECE_HEIGHT / 2;
		}
	}

	private List<PieceInfo> adjustPiecesForHeightAndGiveId(List<PieceInfo> teamOnePieces) {
		for (PieceInfo pieceInfo : teamOnePieces) {
			pieceInfo.setId(UUID.randomUUID().toString());
			adjustPositionForHeight(pieceInfo.getPosition());
		}
		return teamOnePieces;
	}

	public List<PieceInfo> getAllTeamOnePieces() {
		return teamOnePieces;
	}

	public List<PieceInfo> getAllTeamTwoPieces() {
		return teamTwoPieces;
	}

	public void setSelectedPiece(IPieceGroup selectedPiece) {
		this.selectedPieceToMove = selectedPiece;
	}
}
