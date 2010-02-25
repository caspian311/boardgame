package net.todd.games.boardgame;

import javax.media.j3d.Canvas3D;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class UniverseProvider {
	private static IUniverse universeAdapter;

	static {
		Canvas3D canvas3D = new Canvas3D(SimpleUniverse
				.getPreferredConfiguration());
		canvas3D.setFocusable(true);
		canvas3D.requestFocus();

		SimpleUniverse universe = new SimpleUniverse(canvas3D);
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.getViewer().getView().setMinimumFrameCycleTime(5);

		universeAdapter = new UniverseAdapter(universe);
	}

	public IUniverse createUniverse() {
		return universeAdapter;
	}
}
