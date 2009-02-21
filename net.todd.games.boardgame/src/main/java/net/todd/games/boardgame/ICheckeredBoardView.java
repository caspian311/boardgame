package net.todd.games.boardgame;

import javax.media.j3d.BranchGroup;

import net.todd.common.uitools.IListener;

public interface ICheckeredBoardView {
	Tile getSelectedTile();

	BranchGroup getBG();

	void addTileSelectedListener(IListener listener);
}
