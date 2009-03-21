package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

	private SimpleUniverse su;
	private Canvas3D canvas3D;

	@Before
	public void setUp() {
		su = new SimpleUniverse();
		canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());

		cameraGenerator = new CameraGeneratorStub();
		pieceGenerator = new PieceGeneratorStub();
		sceneGenerator = new SceneGeneratorStub();
	}

	@Test
	public void testCreateSceneCallsAllTheRightMethodsInCorrectOrder() {
		IGameEngine gameEngine = new GameEngine(sceneGenerator, pieceGenerator, cameraGenerator);

		gameEngine.createScene(canvas3D);

		assertEquals(1, lightSceneCallCount);
		assertEquals(2, createGameGridCallCount);
		assertEquals(3, createBackgroundCallCount);
		assertEquals(4, createPiecesCallCount);
	}

	@Test
	public void testCreateCameraCallsRightMethod() {
		IGameEngine gameEngine = new GameEngine(null, null, cameraGenerator);

		gameEngine.createCamera(su, canvas3D);

		assertEquals(1, createCameraCallCount);
	}

	@Test
	public void testGetBranchGroupNeverReturnsNull() {
		assertNotNull(new GameEngine(null, null, null).getBranchGroup());
	}

	private class CameraGeneratorStub implements ICameraGenerator {
		public void createCamera(SimpleUniverse su, Canvas3D canvas3D, Bounds bounds) {
			createCameraCallCount = ++callCount;
		}
	}

	private class PieceGeneratorStub implements IPieceGenerator {

		public void createPieces(BranchGroup bg, Canvas3D canvas3D, Bounds bounds) {
			createPiecesCallCount = ++callCount;
		}
	}

	private class SceneGeneratorStub implements ISceneGenerator {

		public void createBackground(BranchGroup bg, Bounds bounds) {
			createBackgroundCallCount = ++callCount;
		}

		public void createGameGrid(BranchGroup bg, Canvas3D canvas3D) {
			createGameGridCallCount = ++callCount;
		}

		public void lightScene(BranchGroup bg, Bounds bounds) {
			lightSceneCallCount = ++callCount;
		}
	}
}
