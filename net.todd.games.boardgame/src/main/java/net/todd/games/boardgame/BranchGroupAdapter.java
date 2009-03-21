package net.todd.games.boardgame;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;

public class BranchGroupAdapter implements IBranchGroup {
	private final BranchGroup branchGroup;

	public BranchGroupAdapter(BranchGroup branchGroup) {
		this.branchGroup = branchGroup;
	}

	public void compile() {
		branchGroup.compile();
	}

	public void addChild(Node child) {
		branchGroup.addChild(child);
	}

	public BranchGroup getInternal() {
		return branchGroup;
	}
}
