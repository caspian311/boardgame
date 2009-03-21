package net.todd.games.boardgame;

public class Main {
	public static void main(String[] args) {
		new Main().execute();
	}

	private void execute() {
		ISceneGenerator gameGridGenerator = new GameGridGenerator();
		IPieceGenerator pieceGenerator = new PieceGenerator();
		ICameraGenerator cameraGenerator = new CameraGenerator();

		IGameEngine gameEngine = new GameEngine(gameGridGenerator, pieceGenerator, cameraGenerator);

		UniverseGenerator canvas3DGenerator = new UniverseGenerator();
		MainApplication mainApplication = new MainApplication("Board Game", canvas3DGenerator);
		mainApplication.createGame(gameEngine);
		mainApplication.start();
	}
}
