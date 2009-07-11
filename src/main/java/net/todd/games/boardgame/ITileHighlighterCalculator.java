package net.todd.games.boardgame;

public interface ITileHighlighterCalculator {
	TileData[] calculateTilesToHighlight(PieceInfo pieceInfo);
}
