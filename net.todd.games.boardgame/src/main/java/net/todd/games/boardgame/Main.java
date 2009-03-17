package net.todd.games.boardgame;

import javax.media.j3d.Canvas3D;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class Main {
	public static void main(String[] args) {
		new Main().execute();
	}

	private void execute() {
		ISceneGenerator gameGridGenerator = new GameGridGenerator();
		IPieceGenerator pieceGenerator = new PieceGenerator();
		ICameraGenerator cameraGenerator = new CameraGenerator();

		GameEngine gameEngine = new GameEngine(gameGridGenerator, pieceGenerator, cameraGenerator);

		Canvas3DGenerator canvas3DGenerator = new Canvas3DGenerator();
		Canvas3D canvas3D = canvas3DGenerator.generateCanvas();
		SimpleUniverse su = canvas3DGenerator.generateUniverse(canvas3D);

		MainApplication mainApplication = new MainApplication("Board Game");
		mainApplication.createGame(canvas3D, su, gameEngine);
	}
}
