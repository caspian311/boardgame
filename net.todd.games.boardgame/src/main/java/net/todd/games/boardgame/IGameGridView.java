package net.todd.games.boardgame;

import javax.media.j3d.BranchGroup;

import net.todd.common.uitools.IListener;

public interface IGameGridView {
	ITile getSelectedTile();

	BranchGroup getBG();

	void addTileSelectedListener(IListener listener);

	void constructGrid(GameGridData data);
}
