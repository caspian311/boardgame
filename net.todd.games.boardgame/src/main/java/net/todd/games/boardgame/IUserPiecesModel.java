package net.todd.games.boardgame;

import java.util.List;

import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;

public interface IUserPiecesModel {
	void addListener(IListener userModelListener);

	Vector3f getCurrentPosition();

	List<PieceInfo> getAllTeamOnePieces();

	List<PieceInfo> getAllTeamTwoPieces();
}
