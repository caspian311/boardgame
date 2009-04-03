package net.todd.games.boardgame;

import javax.media.j3d.BranchGroup;

public class BranchGroupFactory implements IBranchGroupFactory {
	public BranchGroupAdapter createBranchGroup() {
		BranchGroup branchGroup = new BranchGroup();
		branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		return new BranchGroupAdapter(branchGroup);
	}
}
