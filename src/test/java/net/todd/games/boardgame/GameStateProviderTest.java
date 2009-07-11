package net.todd.games.boardgame;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class GameStateProviderTest {
	@Test
	public void testGameStateProvderReturnsAValidGameState() {
		assertNotNull(GameStateProvider.getGameState());
	}

	@Test
	public void testSingletonNature() {
		assertSame(GameStateProvider.getGameState(), GameStateProvider
				.getGameState());
	}
}
