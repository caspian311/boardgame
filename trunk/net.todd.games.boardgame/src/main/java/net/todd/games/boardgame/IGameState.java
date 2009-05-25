package net.todd.games.boardgame;

import java.util.List;

import javax.vecmath.Vector3f;

public interface IGameState {
	List<PieceInfo> getAllPieces();

	Team getTeamToMove();

	void moveMade(String id, Vector3f targetLocation);
}
