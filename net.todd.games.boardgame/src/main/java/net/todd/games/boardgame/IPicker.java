package net.todd.games.boardgame;

import javax.media.j3d.Node;

import net.todd.common.uitools.IListener;

public interface IPicker {
	void addListener(IListener listener);

	Node getSelectedNode();
}