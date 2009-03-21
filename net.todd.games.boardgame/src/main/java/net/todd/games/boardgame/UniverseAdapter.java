package net.todd.games.boardgame;

import javax.media.j3d.Canvas3D;

import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.Viewer;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class UniverseAdapter implements IUniverse {
	private final SimpleUniverse universe;

	public UniverseAdapter(SimpleUniverse universe) {
		this.universe = universe;
	}

	public void addBranchGraph(IBranchGroup branchGroup) {
		universe.addBranchGraph(branchGroup.getInternal());
	}

	public Viewer getViewer() {
		return universe.getViewer();
	}

	public ViewingPlatform getViewingPlatform() {
		return universe.getViewingPlatform();
	}

	public Canvas3D getCanvas() {
		return universe.getCanvas();
	}
}
