package net.todd.games.boardgame;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Sphere;

public class UserPiece {
	private static final Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
	private static final Color3f specular = new Color3f(0.9f, 0.9f, 0.9f);
	private static final Color3f blue = new Color3f(0.0f, 0.0f, 1.0f);

	private final TransformGroup pieceTG;
	private final Sphere sphere;

	public UserPiece() {
		Transform3D piecePosition = new Transform3D();
		pieceTG = new TransformGroup(piecePosition);

		Material matterial = new Material(black, blue, black, specular, 128.0f);
		Appearance appearance = new Appearance();
		appearance.setMaterial(matterial);

		sphere = new Sphere(3f, 1, 50, appearance);
		pieceTG.addChild(sphere);
	}

	public void setPosition(Vector3f position) {
		Transform3D transformation = new Transform3D();
		pieceTG.getTransform(transformation);
		transformation.set(position);
		pieceTG.setTransform(transformation);
	}

	public Node getGraphNode() {
		return pieceTG;
	}

	// public Node getShadowNode(Vector3f lightDirection) {
	// Shape3D shadow = new ShadowShape((GeometryArray)
	// sphere.getShape().getGeometry(),
	// lightDirection, 0.1f);
	//
	// Transform3D shadowPosition = new Transform3D();
	// shadowPosition.set(getShadowPosition(lightDirection));
	// TransformGroup shadowTG = new TransformGroup(shadowPosition);
	//
	// shadowTG.addChild(shadow);
	//
	// return shadowTG;
	// }
	//
	// private Vector3f getShadowPosition(Vector3f lightDirection) {
	// float height = 0.001f;
	// Vector3f userStartingPosition = getUserStartingPosition();
	// float shadowX = (userStartingPosition.x + (userStartingPosition.y -
	// height)
	// * lightDirection.x);
	// float shadowZ = userStartingPosition.z + (userStartingPosition.y -
	// height)
	// * lightDirection.y;
	//
	// return new Vector3f(shadowX, height, shadowZ);
	// }
}
