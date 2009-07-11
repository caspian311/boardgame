package net.todd.games.boardgame;

import java.util.List;

import net.todd.common.uitools.IListener;

public interface IGameGridView {
	TileData getSelectedTile();

	IBranchGroup getBranchGroup();

	void addTileSelectedListener(IListener listener);

	void constructGrid(List<TileData> tileData);
}
