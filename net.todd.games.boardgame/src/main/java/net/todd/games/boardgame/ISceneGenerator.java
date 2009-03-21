package net.todd.games.boardgame;

import javax.media.j3d.Bounds;

public interface ISceneGenerator {
	void createGameGrid(IBranchGroup bg, IPicker picker);

	void lightScene(IBranchGroup bg, Bounds bounds);

	void createBackground(IBranchGroup bg, Bounds bounds);
}
