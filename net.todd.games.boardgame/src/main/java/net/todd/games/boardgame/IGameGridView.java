package net.todd.games.boardgame;

import javax.media.j3d.BranchGroup;

import net.todd.common.uitools.IListener;

public interface IGameGridView {
	TileData getSelectedTile();

	BranchGroup getBranchGroup();

	void addTileSelectedListener(IListener listener);

	void constructGrid(TileData[][] tileData);
}
