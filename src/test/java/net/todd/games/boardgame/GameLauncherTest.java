package net.todd.games.boardgame;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class GameLauncherTest {
	private IUniverse universe;
	private IBranchGroup branchGroup;
	private IGameEngine gameEngine;
	private IPicker picker;
	private IGameLauncher gameLauncher;

	@Before
	public void setup() {
		universe = mock(IUniverse.class);
		branchGroup = mock(IBranchGroup.class);
		gameEngine = mock(IGameEngine.class);
		picker = mock(IPicker.class);

		gameLauncher = new GameLauncher(universe, branchGroup, gameEngine,
				picker);
	}

	@Test
	public void testLaunchCompilesAfterCreatingScene() {
		InOrder inorder = inOrder(universe, branchGroup, gameEngine, picker);
		gameLauncher.launchGame();
		
		inorder.verify(gameEngine).createScene(picker);
		inorder.verify(gameEngine).createCamera(universe);
		inorder.verify(branchGroup).compile();
		inorder.verify(universe).addBranchGraph(branchGroup);
	}
}
