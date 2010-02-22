package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

public class GameEngineTest {
	private ICameraGenerator cameraGenerator;
	private IPieceGenerator pieceGenerator;
	private ISceneGenerator sceneGenerator;
	private GameEngine gameEngine;

	@Before
	public void setUp() {
		cameraGenerator = mock(ICameraGenerator.class);
		pieceGenerator = mock(IPieceGenerator.class);
		sceneGenerator = mock(ISceneGenerator.class);
		
		gameEngine = new GameEngine(sceneGenerator, pieceGenerator,
				cameraGenerator);
	}

	@Test
	public void testCreateSceneCallsAllTheRightMethodsInCorrectOrder() {
		gameEngine.createScene(null);

		InOrder inOrder = inOrder(cameraGenerator, pieceGenerator, sceneGenerator);
		
		inOrder.verify(sceneGenerator).lightScene(any(Bounds.class));
		inOrder.verify(sceneGenerator).createGameGrid(any(IPicker.class), any(IGameGridFactory.class));
		inOrder.verify(sceneGenerator).createBackground(any(Bounds.class));
		inOrder.verify(pieceGenerator).createPieces(any(IPicker.class), any(IUserPiecesFactory.class));
	}

	@Test
	public void testCreateCameraCallsRightMethodWithGivenArguments() {
		IUniverse universe = mock(IUniverse.class);

		gameEngine.createCamera(universe);

		verify(cameraGenerator).createCamera(eq(universe), any(Bounds.class));
	}

	@Test
	public void testPickerIsPassedOnToSceneAndPieceGenerators() {
		IPicker picker = mock(IPicker.class);

		gameEngine.createScene(picker);

		verify(sceneGenerator).createGameGrid(eq(picker), any(IGameGridFactory.class));
		verify(pieceGenerator).createPieces(eq(picker), any(IUserPiecesFactory.class));
	}

	@Test
	public void testBoundsGivenToAllGenerators() {
		gameEngine.createScene(null);
	}
	
	@Test
	public void boundsGivenCameraGenerator() {
		gameEngine.createCamera(null);
		
		ArgumentCaptor<Bounds> boundsCaptor = ArgumentCaptor.forClass(Bounds.class);
		verify(cameraGenerator).createCamera(any(IUniverse.class), boundsCaptor.capture());
		
		verifyBounds(boundsCaptor.getValue());
	}
	
	@Test
	public void boundsGivenToSceneGeneratorToLightScene() {
		gameEngine.createScene(null);
		
		ArgumentCaptor<Bounds> boundsCaptor = ArgumentCaptor.forClass(Bounds.class);
		verify(sceneGenerator).lightScene(boundsCaptor.capture());
		
		verifyBounds(boundsCaptor.getValue());
	}
	
	@Test
	public void boundsGivenToSceneGeneratorToCreateBackground() {
		gameEngine.createScene(null);
		
		ArgumentCaptor<Bounds> boundsCaptor = ArgumentCaptor.forClass(Bounds.class);
		verify(sceneGenerator).createBackground(boundsCaptor.capture());
		
		verifyBounds(boundsCaptor.getValue());
	}

	private void verifyBounds(Bounds bounds) {
		assertTrue(bounds instanceof BoundingSphere);
		BoundingSphere boundingSphere = (BoundingSphere)bounds;
		Point3d point3d = new Point3d();
		boundingSphere.getCenter(point3d);
		assertEquals(0d, point3d.x, 0);
		assertEquals(0d, point3d.y, 0);
		assertEquals(0d, point3d.z, 0);
		
		assertEquals(100d, boundingSphere.getRadius(), 0);		
	}
}
