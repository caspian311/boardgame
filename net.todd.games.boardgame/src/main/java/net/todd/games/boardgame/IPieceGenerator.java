package net.todd.games.boardgame;

import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;

public interface IPieceGenerator {
	void createPieces(BranchGroup bg, IPicker picker, Bounds bounds);
}
