package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.junit.Test;

public class TileHighlighterCalculatorTest {
	@Test
	public void testHighlightTilesWithinMovingDistanceOfPiece() {
		PieceInfo pieceInfo = new PieceInfo();
		pieceInfo.setPosition(new Vector3f(-15f, 0f, -35f));
		pieceInfo.setSpeed(1f);
		GameGridData gameGridData = new GameGridData();
		TileHighlighterCalculator tileHighlighterCalculator = new TileHighlighterCalculator(
				gameGridData);

		TileData[] tilesToHighlight = tileHighlighterCalculator
				.calculateTilesToHighlight(pieceInfo);

		assertEquals(4, tilesToHighlight.length);
		for (TileData tileData : tilesToHighlight) {
			float distanceFromPiece = new Point3f(tileData.getPosition()).distance(new Point3f(
					pieceInfo.getPosition()));
			assertTrue(distanceFromPiece <= pieceInfo.getSpeed() * GameGridData.TILE_SIZE);
		}
	}

	@Test
	public void testHighlightTilesWithinMovingDistanceOfPieceWithDifferentSpeed() {
		PieceInfo pieceInfo = new PieceInfo();
		pieceInfo.setPosition(new Vector3f(-15f, 0f, -35f));
		pieceInfo.setSpeed(4f);
		GameGridData gameGridData = new GameGridData();
		TileHighlighterCalculator tileHighlighterCalculator = new TileHighlighterCalculator(
				gameGridData);

		TileData[] tilesToHighlight = tileHighlighterCalculator
				.calculateTilesToHighlight(pieceInfo);

		assertEquals(25, tilesToHighlight.length);
		for (TileData tileData : tilesToHighlight) {
			float distanceFromPiece = new Point3f(tileData.getPosition()).distance(new Point3f(
					pieceInfo.getPosition()));
			assertTrue(distanceFromPiece <= pieceInfo.getSpeed() * GameGridData.TILE_SIZE);
		}
	}

	@Test
	public void testHighlightTilesWithinMovingDistanceOfPieceWithReallyBigSpeed() {
		PieceInfo pieceInfo = new PieceInfo();
		pieceInfo.setPosition(new Vector3f(-15f, 0f, -35f));
		pieceInfo.setSpeed(10f);
		GameGridData gameGridData = new GameGridData();
		TileHighlighterCalculator tileHighlighterCalculator = new TileHighlighterCalculator(
				gameGridData);

		TileData[] tilesToHighlight = tileHighlighterCalculator
				.calculateTilesToHighlight(pieceInfo);

		assertEquals(64, tilesToHighlight.length);
		for (TileData tileData : tilesToHighlight) {
			float distanceFromPiece = new Point3f(tileData.getPosition()).distance(new Point3f(
					pieceInfo.getPosition()));
			assertTrue(distanceFromPiece <= pieceInfo.getSpeed() * GameGridData.TILE_SIZE);
		}
	}
}
