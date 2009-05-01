package net.todd.games.boardgame;

import net.todd.common.uitools.IListener;

public interface IGameGridView {
	TileData getSelectedTile();

	IBranchGroup getBranchGroup();

	void addTileSelectedListener(IListener listener);

	void constructGrid(TileData[][] tileData);

	void highlightTiles(TileData[] tiles);
}
