package net.todd.games.boardgame;

public class GameStateProvider {
	private static GameState gameState;

	public static GameState getGameState() {
		if (gameState == null) {
			gameState = new GameState(new GamePieceData());
		}
		return gameState;
	}
}
