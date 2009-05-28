package net.todd.games.boardgame;

import javax.media.j3d.BranchGroup;

import net.todd.common.util.DynamicInterface;

public class BranchGroupFactory implements IBranchGroupFactory {
	public IBranchGroup createBranchGroup() {
		BranchGroup branchGroup = new BranchGroup();
		branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		branchGroup.setCapability(BranchGroup.ALLOW_DETACH);
		return DynamicInterface.create(IBranchGroup.class, branchGroup);
	}
}
