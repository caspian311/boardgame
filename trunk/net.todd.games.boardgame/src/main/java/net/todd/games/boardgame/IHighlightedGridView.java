package net.todd.games.boardgame;

public interface IHighlightedGridView {
	IBranchGroup getBranchGroup();

	void highlightTiles(TileData[] tiles);
	
	void clearHighlightedTiles();
}
