package net.todd.games.boardgame;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

public class UserPieceFixture {
	public static UserPiece getUserPiece() {
		UserPiece userPiece = new UserPiece(getPiece());
		return userPiece;
	}

	public static Piece getPiece() {
		Bounds bounds = new BoundingSphere(new Point3d(0, 0, 0), 100);
		Piece piece = new Piece(bounds, new Vector3f(new float[] { 0, 0, 0 }));
		return piece;
	}
}
