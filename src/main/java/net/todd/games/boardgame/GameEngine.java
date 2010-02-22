package net.todd.games.boardgame;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.vecmath.Point3d;

public class GameEngine implements IGameEngine {
	private final ISceneGenerator sceneGenerator;
	private final IPieceGenerator pieceGenerator;
	private final ICameraGenerator cameraGenerator;
	// XXX this is a duplicate variable
	private final static Bounds BOUNDS = new BoundingSphere(new Point3d(0, 0, 0), 100);

	public GameEngine(ISceneGenerator sceneGenerator,
			IPieceGenerator pieceGenerator, ICameraGenerator cameraGenerator) {
		this.sceneGenerator = sceneGenerator;
		this.pieceGenerator = pieceGenerator;
		this.cameraGenerator = cameraGenerator;
	}

	public void createScene(IPicker picker) {
		sceneGenerator.lightScene(BOUNDS);
		sceneGenerator.createGameGrid(picker, new GameGridFactory());
		sceneGenerator.createBackground(BOUNDS);
		pieceGenerator.createPieces(picker, new UserPiecesFactory(BOUNDS));
	}

	public void createCamera(IUniverse su) {
		cameraGenerator.createCamera(su, BOUNDS);
	}
}
