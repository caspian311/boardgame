package net.todd.games.boardgame;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3f;

public class TileHighlighterCalculator implements ITileHighlighterCalculator {
	private final IGameGridData gameGridData;

	public TileHighlighterCalculator(IGameGridData gameGridData) {
		this.gameGridData = gameGridData;
	}

	public TileData[] calculateTilesToHighlight(PieceInfo pieceInfo) {
		// TODO use the MovementRuleCollection
		List<TileData> tilesToHighlight = new ArrayList<TileData>();

		TileData[][] allTileData = gameGridData.getTileData();
		for (TileData[] tileData : allTileData) {
			for (TileData tileDatum : tileData) {
				float maxDistance = pieceInfo.getSpeed()
						* GameGridData.TILE_SIZE;
				float distanceToPiece = new Point3f(tileDatum.getPosition())
						.distance(new Point3f(pieceInfo.getPosition()));
				if (distanceToPiece <= maxDistance) {
					tilesToHighlight.add(tileDatum);
				}
			}
		}
		TileData[] tilesToHighlightArray = new TileData[tilesToHighlight.size()];
		tilesToHighlight.toArray(tilesToHighlightArray);
		return tilesToHighlightArray;
	}
}
