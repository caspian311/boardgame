package net.todd.games.boardgame;

import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;

public interface IPieceGenerator {

	void createPieces(BranchGroup bg, Canvas3D canvas3D, Bounds bounds);

}
