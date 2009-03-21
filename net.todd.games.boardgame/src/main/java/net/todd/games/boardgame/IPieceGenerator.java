package net.todd.games.boardgame;

import javax.media.j3d.Bounds;

public interface IPieceGenerator {
	void createPieces(IPicker picker, Bounds bounds);
}
