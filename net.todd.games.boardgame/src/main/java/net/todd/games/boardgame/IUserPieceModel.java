package net.todd.games.boardgame;

import net.todd.common.uitools.IListener;

public interface IUserPieceModel {
	float[] getStartingPoint();

	void addListener(IListener userModelListener);

	float[] getCurrentPosition();
}
