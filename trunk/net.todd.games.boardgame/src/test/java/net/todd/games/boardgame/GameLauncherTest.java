package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Node;

import net.todd.common.uitools.IListener;

import org.junit.Before;
import org.junit.Test;

import com.sun.j3d.utils.universe.Viewer;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class GameLauncherTest {
	private UniverseStub universe;
	private BranchGroupFactoryStub branchGroupFactory;
	private BranchGroupStub branchGroup;
	private GameEngineFactoryStub gameEngineFactory;
	private PickerStub picker;
	private int callCount;

	@Before
	public void setup() {
		universe = new UniverseStub();
		branchGroupFactory = new BranchGroupFactoryStub();
		branchGroup = new BranchGroupStub();
		branchGroupFactory.branchGroup = branchGroup;
		gameEngineFactory = new GameEngineFactoryStub();
		picker = new PickerStub();
	}

	@Test
	public void testLaunchCompilesAfterCreatingScene() {
		GameLauncher gameLauncher = new GameLauncher() {
			@Override
			IPickerFactory getPickerFactory(IUniverse universe) {
				return new IPickerFactory() {
					public IPicker createPicker(IBranchGroup branchGroup) {
						return picker;
					}
				};
			}
		};

		gameLauncher
				.launchGame(universe, branchGroupFactory, gameEngineFactory);

		assertEquals(1, gameEngineFactory.sceneCallCount);
		assertEquals(2, gameEngineFactory.cameraCallCount);
		assertEquals(3, branchGroup.compileCallCount);
		assertSame(branchGroup, universe.addedBranchGroup);
		assertSame(universe, gameEngineFactory.universe);
		assertSame(picker, gameEngineFactory.gamePicker);
	}

	private static class UniverseStub implements IUniverse {
		IBranchGroup addedBranchGroup;

		public void addBranchGraph(IBranchGroup branchGroup) {
			addedBranchGroup = branchGroup;
		}

		public Canvas3D getCanvas() {
			throw new UnsupportedOperationException();
		}

		public Viewer getViewer() {
			throw new UnsupportedOperationException();
		}

		public ViewingPlatform getViewingPlatform() {
			throw new UnsupportedOperationException();
		}
	}

	private class BranchGroupFactoryStub implements IBranchGroupFactory {
		IBranchGroup branchGroup;

		public IBranchGroup createBranchGroup() {
			return branchGroup;
		}
	}

	private class BranchGroupStub implements IBranchGroup {
		int compileCallCount;

		public void compile() {
			compileCallCount = ++callCount;
		}

		public void addChild(Node node) {
		}

		public void addChild(IBranchGroup child) {
		}

		public BranchGroup underlyingImplementation() {
			return null;
		}

		public void removeAllChildren() {
			throw new UnsupportedOperationException();
		}
	}

	private class GameEngineFactoryStub implements IGameEngineFactory {
		IPicker gamePicker;
		IUniverse universe;
		int cameraCallCount;
		int sceneCallCount;

		public IGameEngine createGameEngine(IBranchGroup branchGroup) {
			return new IGameEngine() {
				public void createCamera(IUniverse su) {
					cameraCallCount = ++callCount;
					universe = su;
				}

				public void createScene(IPicker picker) {
					sceneCallCount = ++callCount;
					gamePicker = picker;
				}
			};
		}
	}

	private static class PickerStub implements IPicker {
		public void addListener(IListener listener) {
			throw new UnsupportedOperationException();
		}

		public Node getSelectedNode() {
			throw new UnsupportedOperationException();
		}

		public void removeListener(IListener listener) {
			throw new UnsupportedOperationException();
		}
	}
}
