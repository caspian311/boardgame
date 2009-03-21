package net.todd.games.boardgame;

import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;

public interface ISceneGenerator {
	void createGameGrid(BranchGroup bg, IPicker picker);

	void lightScene(BranchGroup bg, Bounds bounds);

	void createBackground(BranchGroup bg, Bounds bounds);
}
