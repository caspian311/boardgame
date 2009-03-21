package net.todd.games.boardgame;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import javax.media.j3d.BranchGroup;

import org.junit.Test;

public class BranchGroupFactoryTest {
	@Test
	public void testCreateBranchGroupHasAnInternalRealBranchGroup() {
		BranchGroupFactory branchGroupFactory = new BranchGroupFactory();
		IBranchGroup branchGroup = branchGroupFactory.createBranchGroup();

		assertNotNull(branchGroup.getInternal());
		assertTrue(branchGroup.getInternal() instanceof BranchGroup);
	}

	@Test
	public void testCreateBranchGroupDoesNotReturnSingleton() {
		BranchGroupFactory branchGroupFactory = new BranchGroupFactory();
		IBranchGroup branchGroup = branchGroupFactory.createBranchGroup();

		assertNotSame(branchGroup, branchGroupFactory.createBranchGroup());
	}
}
