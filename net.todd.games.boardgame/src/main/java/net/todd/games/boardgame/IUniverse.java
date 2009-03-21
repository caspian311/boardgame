package net.todd.games.boardgame;

import javax.media.j3d.Canvas3D;

import com.sun.j3d.utils.universe.Viewer;
import com.sun.j3d.utils.universe.ViewingPlatform;

public interface IUniverse {
	void addBranchGraph(IBranchGroup branchGroup);

	ViewingPlatform getViewingPlatform();

	Viewer getViewer();

	Canvas3D getCanvas();
}
