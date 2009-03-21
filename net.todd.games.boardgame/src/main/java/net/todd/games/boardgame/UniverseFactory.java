package net.todd.games.boardgame;

import javax.media.j3d.Canvas3D;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class UniverseFactory implements IUniverseFactory {
	public SimpleUniverse generateUniverse(Canvas3D canvas3D) {
		SimpleUniverse su = new SimpleUniverse(canvas3D);
		su.getViewingPlatform().setNominalViewingTransform();
		su.getViewer().getView().setMinimumFrameCycleTime(5);

		return su;
	}
}
