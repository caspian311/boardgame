package net.todd.games.boardgame;

import javax.media.j3d.Appearance;
import javax.media.j3d.Geometry;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;

public class Tile extends Shape3D implements ITile {
	private static final Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
	private static final Color3f specular = new Color3f(0.9f, 0.9f, 0.9f);

	private final float size;
	private final float centerX;
	private final float centerY;
	private final float centerZ;
	private final Color3f tileColor;
	private final TileData tileData;
	private final boolean isTransparent;

	public Tile(TileData datum) {
		this.tileData = datum;
		this.centerX = datum.getPosition()[0];
		this.centerY = datum.getPosition()[1];
		this.centerZ = datum.getPosition()[2];
		this.size = datum.getSize();
		this.tileColor = new Color3f(datum.getColor());
		this.isTransparent = datum.isTranparent();
		setGeometry(getMyGeometry());
		setAppearance(getMyAppearance());
		setPickable(true);
	}

	private Appearance getMyAppearance() {
		Appearance appearance = new Appearance();

		PolygonAttributes polygonAttributes = new PolygonAttributes();
		polygonAttributes.setCullFace(PolygonAttributes.CULL_BACK);

		appearance.setPolygonAttributes(polygonAttributes);
		appearance.setMaterial(new Material(black, tileColor, black, specular, 128.0f));
		if (isTransparent) {
			TransparencyAttributes transparencyAttributes = new TransparencyAttributes();
			transparencyAttributes.setTransparencyMode(TransparencyAttributes.BLENDED);
			transparencyAttributes.setTransparency(0.5f);
			appearance.setTransparencyAttributes(transparencyAttributes);
		}

		return appearance;
	}

	private Geometry getMyGeometry() {
		Point3f[] points = new Point3f[4];
		points[0] = new Point3f(new float[] { centerX + (size / 2), centerY, centerZ + (size / 2) });
		points[1] = new Point3f(new float[] { centerX + (size / 2), centerY, centerZ - (size / 2) });
		points[2] = new Point3f(new float[] { centerX - (size / 2), centerY, centerZ - (size / 2) });
		points[3] = new Point3f(new float[] { centerX - (size / 2), centerY, centerZ + (size / 2) });

		QuadArray quadArray = new QuadArray(points.length, GeometryArray.COORDINATES
				| GeometryArray.NORMALS);
		quadArray.setCoordinates(0, points);

		GeometryInfo info = new GeometryInfo(quadArray);
		new NormalGenerator().generateNormals(info);

		return quadArray;
	}

	@Override
	public String toString() {
		return "Center: (" + centerX + ", " + centerY + ", " + centerZ + ")";
	}

	public TileData getTileData() {
		return tileData;
	}
}
