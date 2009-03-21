package net.todd.games.boardgame;

import java.util.UUID;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Sphere;

public class UserPiece extends Shape3D {
	private static final Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
	private static final Color3f specular = new Color3f(0.9f, 0.9f, 0.9f);
	private static final Color3f blue = new Color3f(0.0f, 0.0f, 1.0f);
	private final String uuid = UUID.randomUUID().toString();
	private final Piece piece;

	public Piece getPiece() {
		return piece;
	}

	public UserPiece(Piece piece) {
		this.piece = piece;
		Material matterial = new Material(black, blue, black, specular, 128.0f);
		Appearance appearance = new Appearance();
		appearance.setMaterial(matterial);

		Sphere sphere = new Sphere(3f, 1, 50, appearance);
		setGeometry(sphere.getShape().getGeometry());
		setAppearance(sphere.getShape().getAppearance());
	}

	public String getUuid() {
		return uuid;
	}
}
