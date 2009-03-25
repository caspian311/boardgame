package net.todd.games.boardgame;

import java.util.List;

import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;

public interface IUserPiecesModel {
	void addMoveListener(IListener userModelListener);

	Vector3f getMoveToLocation();

	List<PieceInfo> getAllTeamOnePieces();

	List<PieceInfo> getAllTeamTwoPieces();
}
