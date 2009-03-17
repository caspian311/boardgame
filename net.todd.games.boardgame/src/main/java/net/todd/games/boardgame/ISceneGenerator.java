package net.todd.games.boardgame;

import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;

public interface ISceneGenerator {
	void createGameGrid(BranchGroup bg, Canvas3D canvas3D);

	void lightScene(BranchGroup bg, Bounds bounds);

	void createBackground(BranchGroup bg, Bounds bounds);
}
