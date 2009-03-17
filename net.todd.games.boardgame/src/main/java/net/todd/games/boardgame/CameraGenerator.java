package net.todd.games.boardgame;

import javax.media.j3d.Bounds;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class CameraGenerator implements ICameraGenerator {
	public void createCamera(SimpleUniverse su, Canvas3D canvas3D, Bounds bounds) {
		ViewingPlatform vp = su.getViewingPlatform();
		View view = su.getViewer().getView();
		view.setBackClipDistance(100);

		TransformGroup tg = vp.getViewPlatformTransform();
		Transform3D t3d = new Transform3D();
		tg.getTransform(t3d);
		t3d.lookAt(new Point3d(0, 50.0, -100.0), new Point3d(0.0, 0.0, 0.0), new Vector3d(0, 1, 0));
		t3d.invert();
		tg.setTransform(t3d);

		OrbitBehavior orbit = new OrbitBehavior(canvas3D, OrbitBehavior.REVERSE_ALL);
		orbit.setSchedulingBounds(bounds);
		vp.setViewPlatformBehavior(orbit);
	}
}
