package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import javax.vecmath.Vector3f;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class TileHighlighterCalculatorTest {
	private IGameGridData gameGridData;
	private IMovementRuleCollection ruleCollection;

	private ITileHighlighterCalculator tileHighlighterCalculator;
	private TileData tile1;
	private TileData tile2;
	private TileData tile3;
	private TileData tile4;
	private TileData tile5;
	private TileData tile6;
	private IRule failingRule;

	@Before
	public void setUp() throws ValidMoveException {
		ruleCollection = mock(IMovementRuleCollection.class);
		gameGridData = mock(IGameGridData.class);
		
		tileHighlighterCalculator = new TileHighlighterCalculator(
				gameGridData, ruleCollection);
		
		tile1 = new TileData();
		tile1.setPosition(new float[] { 0f, 0f, 0f });
		tile2 = new TileData();
		tile2.setPosition(new float[] { 0f, 0f, 0f });
		tile3 = new TileData();
		tile3.setPosition(new float[] { 0f, 0f, 0f });
		tile4 = new TileData();
		tile4.setPosition(new float[] { 0f, 0f, 0f });
		tile5 = new TileData();
		tile5.setPosition(new float[] { 0f, 0f, 0f });
		tile6 = new TileData();
		tile6.setPosition(new float[] { 0f, 0f, 0f });
		TileData[][] tileData = new TileData[][] { { tile1, tile2, tile3 },
				{ tile4, tile5, tile6 } };
		doReturn(tileData).when(gameGridData).getTileData();
		
		failingRule = mock(IRule.class);
		doThrow(new ValidMoveException("")).when(failingRule).validateMove(any(PieceInfo.class), any(Vector3f.class));
	}

	@Test
	public void testIfNoRulesComplainAllTilesGetHighlighted() {
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
		doReturn(Arrays.asList(failingRule)).when(ruleCollection).getRules();

		TileData[] tilesToHighlight = tileHighlighterCalculator
				.calculateTilesToHighlight(null);

		assertEquals(0, tilesToHighlight.length);
	}

	@Test
	public void testRulesGetsPassedCorrectPieceInfo() throws ValidMoveException {
		IRule rule = mock(IRule.class);
		doReturn(Arrays.asList(rule)).when(ruleCollection).getRules();

		PieceInfo pieceInfo = new PieceInfo();
		tileHighlighterCalculator.calculateTilesToHighlight(pieceInfo);

		ArgumentCaptor<PieceInfo> pieceInfoCaptor = ArgumentCaptor.forClass(PieceInfo.class);
		verify(rule, atLeastOnce()).validateMove(pieceInfoCaptor.capture(), any(Vector3f.class));
		
		assertSame(pieceInfo, pieceInfoCaptor.getValue());
	}
}
