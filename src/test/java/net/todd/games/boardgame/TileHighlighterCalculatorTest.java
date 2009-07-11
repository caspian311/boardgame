package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import javax.vecmath.Vector3f;

import org.junit.Before;
import org.junit.Test;

public class TileHighlighterCalculatorTest {
	private GameGridDataStub gameGridData;
	private MovementRuleCollection movementRuleCollection;

	private PieceInfo pieceReceived;

	@Before
	public void setUp() {
		movementRuleCollection = new MovementRuleCollection();
		gameGridData = new GameGridDataStub();
	}

	@Test
	public void testIfNoRulesComplainAllTilesGetHighlighted() {
		TileData tile1 = new TileData();
		TileData tile2 = new TileData();
		TileData tile3 = new TileData();
		TileData tile4 = new TileData();
		TileData tile5 = new TileData();
		TileData tile6 = new TileData();
		gameGridData.tileData = new TileData[][] { { tile1, tile2, tile3 },
				{ tile4, tile5, tile6 } };

		TileHighlighterCalculator tileHighlighterCalculator = new TileHighlighterCalculator(
				gameGridData, movementRuleCollection);
		TileData[] tilesToHighlight = tileHighlighterCalculator
				.calculateTilesToHighlight(null);

		List<TileData> highlighted = Arrays.asList(tilesToHighlight);
		assertTrue(highlighted.contains(tile1));
		assertTrue(highlighted.contains(tile2));
		assertTrue(highlighted.contains(tile3));
		assertTrue(highlighted.contains(tile4));
		assertTrue(highlighted.contains(tile5));
		assertTrue(highlighted.contains(tile6));
	}

	@Test
	public void testIfRulesDontAllowAnyThenNoTilesAreHighlighted() {
		TileData tile1 = new TileData();
		tile1.setPosition(new float[] { 0f, 0f, 0f });
		TileData tile2 = new TileData();
		tile2.setPosition(new float[] { 0f, 0f, 0f });
		TileData tile3 = new TileData();
		tile3.setPosition(new float[] { 0f, 0f, 0f });
		TileData tile4 = new TileData();
		tile4.setPosition(new float[] { 0f, 0f, 0f });
		TileData tile5 = new TileData();
		tile5.setPosition(new float[] { 0f, 0f, 0f });
		TileData tile6 = new TileData();
		tile6.setPosition(new float[] { 0f, 0f, 0f });
		gameGridData.tileData = new TileData[][] { { tile1, tile2, tile3 },
				{ tile4, tile5, tile6 } };

		movementRuleCollection.addRule(new IRule() {
			public void validateMove(PieceInfo pieceToMove,
					Vector3f targetLocation) throws ValidMoveException {
				throw new ValidMoveException("");
			}
		});

		TileHighlighterCalculator tileHighlighterCalculator = new TileHighlighterCalculator(
				gameGridData, movementRuleCollection);
		TileData[] tilesToHighlight = tileHighlighterCalculator
				.calculateTilesToHighlight(null);

		assertEquals(0, tilesToHighlight.length);
	}

	@Test
	public void testRulesGetsPassedCorrectPieceInfo() {
		TileData tile1 = new TileData();
		tile1.setPosition(new float[] { 0f, 0f, 0f });
		TileData tile2 = new TileData();
		tile2.setPosition(new float[] { 0f, 0f, 0f });
		TileData tile3 = new TileData();
		tile3.setPosition(new float[] { 0f, 0f, 0f });
		TileData tile4 = new TileData();
		tile4.setPosition(new float[] { 0f, 0f, 0f });
		TileData tile5 = new TileData();
		tile5.setPosition(new float[] { 0f, 0f, 0f });
		TileData tile6 = new TileData();
		tile6.setPosition(new float[] { 0f, 0f, 0f });
		gameGridData.tileData = new TileData[][] { { tile1, tile2, tile3 },
				{ tile4, tile5, tile6 } };

		movementRuleCollection.addRule(new IRule() {
			public void validateMove(PieceInfo pieceToMove,
					Vector3f targetLocation) throws ValidMoveException {
				pieceReceived = pieceToMove;
			}
		});

		PieceInfo pieceInfo = new PieceInfo();
		TileHighlighterCalculator highlighter = new TileHighlighterCalculator(
				gameGridData, movementRuleCollection);
		highlighter.calculateTilesToHighlight(pieceInfo);
		assertSame(pieceInfo, pieceReceived);
	}

	private static class GameGridDataStub implements IGameGridData {
		private TileData[][] tileData;

		public TileData[][] getTileData() {
			return tileData;
		}
	}
}
