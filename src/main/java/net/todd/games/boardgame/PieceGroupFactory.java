package net.todd.games.boardgame;

import javax.media.j3d.Bounds;

public class PieceGroupFactory implements IPieceGroupFactory {
	public PieceGroup createPieceGroup(Bounds bounds, PieceInfo pieceInfo) {
		return new PieceGroup(bounds, pieceInfo);
	}
}
