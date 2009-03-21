package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;

import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;

import org.junit.Before;
import org.junit.Test;

public class GameEngineTest {
	private int lightSceneCallCount;
	private int createGameGridCallCount;
	private int createBackgroundCallCount;
	private int createPiecesCallCount;
	private int createCameraCallCount;
	private int compileCount;

	private int callCount;

	private CameraGeneratorStub cameraGenerator;
	private PieceGeneratorStub pieceGenerator;
	private SceneGeneratorStub sceneGenerator;

	private IBranchGroup bg;

	@Before
	public void setUp() {

		cameraGenerator = new CameraGeneratorStub();
		pieceGenerator = new PieceGeneratorStub();
		sceneGenerator = new SceneGeneratorStub();
		bg = new BranchGroupStub();
	}

	@Test
	public void testCreateSceneCallsAllTheRightMethodsInCorrectOrder() {
		GameEngine gameEngine = new GameEngine(bg, sceneGenerator, pieceGenerator, cameraGenerator);

		gameEngine.createScene(null);

		assertEquals(1, lightSceneCallCount);
		assertEquals(2, createGameGridCallCount);
		assertEquals(3, createBackgroundCallCount);
		assertEquals(4, createPiecesCallCount);
		assertEquals(5, compileCount);
	}

	@Test
	public void testCreateCameraCallsRightMethod() {
		GameEngine gameEngine = new GameEngine(bg, null, null, cameraGenerator);

		gameEngine.createCamera(null);

		assertEquals(1, createCameraCallCount);
	}

	private class CameraGeneratorStub implements ICameraGenerator {
		public void createCamera(IUniverse su, Bounds bounds) {
			createCameraCallCount = ++callCount;
		}
	}

	private class PieceGeneratorStub implements IPieceGenerator {
		public void createPieces(IBranchGroup bg, IPicker picker, Bounds bounds) {
			createPiecesCallCount = ++callCount;
		}
	}

	private class SceneGeneratorStub implements ISceneGenerator {
		public void createBackground(IBranchGroup bg, Bounds bounds) {
			createBackgroundCallCount = ++callCount;
		}

		public void createGameGrid(IBranchGroup bg, IPicker picker) {
			createGameGridCallCount = ++callCount;
		}

		public void lightScene(IBranchGroup bg, Bounds bounds) {
			lightSceneCallCount = ++callCount;
		}
	}

	private class BranchGroupStub implements IBranchGroup {
		public void compile() {
			compileCount = ++callCount;
		}

		public void addChild(Node node) {
			throw new UnsupportedOperationException();
		}

		public BranchGroup getInternal() {
			throw new UnsupportedOperationException();
		}
	}
}
