package net.todd.games.boardgame;

import javax.media.j3d.Bounds;

public interface ISceneGenerator {
	void createGameGrid(IPicker picker, IGameGridFactory gameGridFactory);

	void lightScene(Bounds bounds);

	void createBackground(Bounds bounds);
}
