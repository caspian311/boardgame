package net.todd.games.boardgame;

import java.awt.GraphicsConfiguration;

import javax.media.j3d.Canvas3D;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class Canvas3DGenerator {
	public Canvas3D generateCanvas() {
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

		Canvas3D canvas3D = new Canvas3D(config);
		canvas3D.setFocusable(true);
		canvas3D.requestFocus();

		return canvas3D;
	}

	public SimpleUniverse generateUniverse(Canvas3D canvas3D) {
		SimpleUniverse su = new SimpleUniverse(canvas3D);
		su.getViewingPlatform().setNominalViewingTransform();
		su.getViewer().getView().setMinimumFrameCycleTime(5);

		return su;
	}
}
