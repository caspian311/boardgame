package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;

import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;

import org.junit.Before;
import org.junit.Test;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class GameEngineTest {
	private int lightSceneCallCount;
	private int createGameGridCallCount;
	private int createBackgroundCallCount;
	private int createPiecesCallCount;
	private int createCameraCallCount;

	private int callCount;

	private CameraGeneratorStub cameraGenerator;
	private PieceGeneratorStub pieceGenerator;
	private SceneGeneratorStub sceneGenerator;

	private BranchGroup bg;

	@Before
	public void setUp() {

		cameraGenerator = new CameraGeneratorStub();
		pieceGenerator = new PieceGeneratorStub();
		sceneGenerator = new SceneGeneratorStub();
		bg = new BranchGroup();
	}

	@Test
	public void testCreateSceneCallsAllTheRightMethodsInCorrectOrder() {
		GameEngine gameEngine = new GameEngine(bg, sceneGenerator, pieceGenerator, cameraGenerator);

		gameEngine.createScene(null);

		assertEquals(1, lightSceneCallCount);
		assertEquals(2, createGameGridCallCount);
		assertEquals(3, createBackgroundCallCount);
		assertEquals(4, createPiecesCallCount);
	}

	@Test
	public void testCreateCameraCallsRightMethod() {
		GameEngine gameEngine = new GameEngine(bg, null, null, cameraGenerator);

		gameEngine.createCamera(null, null);

		assertEquals(1, createCameraCallCount);
	}

	private class CameraGeneratorStub implements ICameraGenerator {
		public void createCamera(SimpleUniverse su, Canvas3D canvas3D, Bounds bounds) {
			createCameraCallCount = ++callCount;
		}
	}

	private class PieceGeneratorStub implements IPieceGenerator {

		public void createPieces(BranchGroup bg, IPicker picker, Bounds bounds) {
			createPiecesCallCount = ++callCount;
		}
	}

	private class SceneGeneratorStub implements ISceneGenerator {

		public void createBackground(BranchGroup bg, Bounds bounds) {
			createBackgroundCallCount = ++callCount;
		}

		public void createGameGrid(BranchGroup bg, IPicker picker) {
			createGameGridCallCount = ++callCount;
		}

		public void lightScene(BranchGroup bg, Bounds bounds) {
			lightSceneCallCount = ++callCount;
		}
	}
}
