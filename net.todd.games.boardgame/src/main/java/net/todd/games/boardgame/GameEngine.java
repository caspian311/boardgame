package net.todd.games.boardgame;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.vecmath.Point3d;

public class GameEngine {
	private final ISceneGenerator sceneGenerator;
	private final IPieceGenerator pieceGenerator;
	private final ICameraGenerator cameraGenerator;
	private final Bounds bounds;
	private final IBranchGroup bg;

	public GameEngine(IBranchGroup bg, ISceneGenerator sceneGenerator,
			IPieceGenerator pieceGenerator, ICameraGenerator cameraGenerator) {
		this.bg = bg;
		this.sceneGenerator = sceneGenerator;
		this.pieceGenerator = pieceGenerator;
		this.cameraGenerator = cameraGenerator;

		bounds = new BoundingSphere(new Point3d(0, 0, 0), 100);
	}

	public void createScene(IPicker picker) {
		sceneGenerator.lightScene(bg, bounds);
		sceneGenerator.createGameGrid(bg, picker);
		sceneGenerator.createBackground(bg, bounds);
		pieceGenerator.createPieces(bg, picker, bounds);

		bg.compile();
	}

	public void createCamera(IUniverse su) {
		cameraGenerator.createCamera(su, bounds);
	}
}
