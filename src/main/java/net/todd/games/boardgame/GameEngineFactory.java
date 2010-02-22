package net.todd.games.boardgame;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.vecmath.Point3d;

public class GameEngineFactory implements IGameEngineFactory {
	// XXX this is a duplicate variable
	private final static Bounds BOUNDS = new BoundingSphere(new Point3d(0, 0, 0), 100);
	
	public IGameEngine createGameEngine(IBranchGroup branchGroup) {
		ISceneGenerator gameGridGenerator = new GameGridGenerator(branchGroup, new LightFactory(BOUNDS));
		IPieceGenerator pieceGenerator = new PieceGenerator(branchGroup);
		ICameraGenerator cameraGenerator = new CameraGenerator();

		IGameEngine gameEngine = new GameEngine(gameGridGenerator,
				pieceGenerator, cameraGenerator);
		return gameEngine;
	}
}
