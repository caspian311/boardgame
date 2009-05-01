package net.todd.games.boardgame;

public class GameEngineFactory implements IGameEngineFactory {
	public IGameEngine createGameEngine(IBranchGroup branchGroup) {
		ISceneGenerator gameGridGenerator = new GameGridGenerator(branchGroup);
		IPieceGenerator pieceGenerator = new PieceGenerator(branchGroup);
		ICameraGenerator cameraGenerator = new CameraGenerator();

		IGameEngine gameEngine = new GameEngine(gameGridGenerator, pieceGenerator, cameraGenerator);
		return gameEngine;
	}
}
