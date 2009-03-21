package net.todd.games.boardgame;

import javax.media.j3d.BranchGroup;

public class BranchGroupFactory implements IBranchGroupFactory {
	public BranchGroupAdapter createBranchGroup() {
		return new BranchGroupAdapter(new BranchGroup());
	}
}
