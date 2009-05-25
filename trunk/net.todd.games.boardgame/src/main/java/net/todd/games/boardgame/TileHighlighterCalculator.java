package net.todd.games.boardgame;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3f;

public class TileHighlighterCalculator implements ITileHighlighterCalculator {
	private final IMovementRuleCollection ruleCollection;
	private final IGameGridData gameGridData;

	public TileHighlighterCalculator(IGameGridData gameGridData,
			IMovementRuleCollection ruleCollection) {
		this.gameGridData = gameGridData;
		this.ruleCollection = ruleCollection;
	}

	public TileData[] calculateTilesToHighlight(PieceInfo pieceInfo) {
		List<TileData> tilesToHighlight = new ArrayList<TileData>();

		TileData[][] allTileData = gameGridData.getTileData();
		for (TileData[] tileData : allTileData) {
			for (TileData tileDatum : tileData) {
				try {
					for (IRule rule : ruleCollection.getRules()) {
						rule.validateMove(pieceInfo, new Vector3f(tileDatum
								.getPosition()));
					}
					tilesToHighlight.add(tileDatum);
				} catch (ValidMoveException e) {
				}
			}
		}
		TileData[] tilesToHighlightArray = new TileData[tilesToHighlight.size()];
		tilesToHighlight.toArray(tilesToHighlightArray);
		return tilesToHighlightArray;
	}
}
