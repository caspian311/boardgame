package net.todd.games.boardgame;

import javax.media.j3d.Bounds;

public interface IPieceGroupFactory {
	PieceGroup createPieceGroup(Bounds bounds, PieceInfo pieceInfo);
}
