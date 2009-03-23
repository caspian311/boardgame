package net.todd.games.boardgame;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

public class UserPieceFixture {
	private static final Color3f blue = new Color3f(0.0f, 0.0f, 1.0f);

	public static UserPiece getUserPiece() {
		UserPiece userPiece = new UserPiece(getPiece());
		return userPiece;
	}

	public static Piece getPiece() {
		Bounds bounds = new BoundingSphere(new Point3d(0, 0, 0), 100);
		Piece piece = new Piece(bounds, new Vector3f(new float[] { 0, 0, 0 }), blue);
		return piece;
	}
}
