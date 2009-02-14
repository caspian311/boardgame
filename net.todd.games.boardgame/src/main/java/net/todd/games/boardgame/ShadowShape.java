package net.todd.games.boardgame;

import javax.media.j3d.GeometryArray;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

public class ShadowShape extends Shape3D {
	private static final Color3f shadowColor = new Color3f(0.5f, 0.5f, 0.5f);

	public ShadowShape(GeometryArray geometry, Vector3f lightDirection, float height) {
		int vCount = geometry.getVertexCount();
		QuadArray poly = new QuadArray(vCount, GeometryArray.COORDINATES | GeometryArray.COLOR_3);

		Point3f vertex = new Point3f();
		Point3f shadow = new Point3f();
		for (int v = 0; v < vCount; v++) {
			geometry.getCoordinate(v, vertex);
			float shadowX = (vertex.x + (vertex.y - height) * lightDirection.x);
			float shadowY = height + 0.0001f;
			float shadowZ = vertex.z + (vertex.y - height) * lightDirection.y;
			shadow.set(shadowX, shadowY, shadowZ);
			poly.setCoordinate(v, shadow);
			poly.setColor(v, shadowColor);
		}

		this.setGeometry(poly);
	}
}
