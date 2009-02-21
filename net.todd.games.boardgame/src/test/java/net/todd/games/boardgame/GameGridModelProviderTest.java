package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class GameGridModelProviderTest {
	@Test
	public void testProviderReturnsAnInstanceOfTheModel() {
		assertNotNull(GameGridModelProvider.getModel());
	}

	@Test
	public void testProviderReturnsSameInstanceOfTheModel() {
		assertEquals(GameGridModelProvider.getModel(), GameGridModelProvider.getModel());
	}
}
