package net.todd.games.boardgame;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class GameGridModelTest {
	private GameGridModel model;

	@Before
	public void setUp() {
		model = new GameGridModel();
	}

	@Test
	public void testGridDataIsNeverNull() {
		assertNotNull(model.getGridData());
	}
}
