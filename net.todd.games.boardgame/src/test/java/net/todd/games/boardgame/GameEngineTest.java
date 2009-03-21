package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;

import javax.media.j3d.Bounds;

import org.junit.Before;
import org.junit.Test;

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

	@Before
	public void setUp() {
		cameraGenerator = new CameraGeneratorStub();
		pieceGenerator = new PieceGeneratorStub();
		sceneGenerator = new SceneGeneratorStub();
	}

	@Test
	public void testCreateSceneCallsAllTheRightMethodsInCorrectOrder() {
		IGameEngine gameEngine = new GameEngine(sceneGenerator, pieceGenerator, cameraGenerator);

		gameEngine.createScene(null);

		assertEquals(1, lightSceneCallCount);
		assertEquals(2, createGameGridCallCount);
		assertEquals(3, createBackgroundCallCount);
		assertEquals(4, createPiecesCallCount);
	}

	@Test
	public void testCreateCameraCallsRightMethod() {
		IGameEngine gameEngine = new GameEngine(null, null, cameraGenerator);

		gameEngine.createCamera(null);

		assertEquals(1, createCameraCallCount);
	}

	private class CameraGeneratorStub implements ICameraGenerator {
		public void createCamera(IUniverse su, Bounds bounds) {
			createCameraCallCount = ++callCount;
		}
	}

	private class PieceGeneratorStub implements IPieceGenerator {
		public void createPieces(IPicker picker, Bounds bounds) {
			createPiecesCallCount = ++callCount;
		}
	}

	private class SceneGeneratorStub implements ISceneGenerator {
		public void createBackground(Bounds bounds) {
			createBackgroundCallCount = ++callCount;
		}

		public void createGameGrid(IPicker picker) {
			createGameGridCallCount = ++callCount;
		}

		public void lightScene(Bounds bounds) {
			lightSceneCallCount = ++callCount;
		}
	}
}
