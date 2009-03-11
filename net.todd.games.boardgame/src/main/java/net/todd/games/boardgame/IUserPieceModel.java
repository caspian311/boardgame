package net.todd.games.boardgame;

import net.todd.common.uitools.IListener;

public interface IUserPieceModel {
	void addListener(IListener userModelListener);

	float[] getCurrentPosition();

	// void selected();
}
