package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.DirectionalLight;
import javax.vecmath.Color3f;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class GameGridGeneratorTest {
	private IBranchGroup branchGroup;
	private IGameGridFactory gameGridFactory;
	private ISceneGenerator gridGenerator;
	private BoundingSphere BOUNDS;
	private ILightFactory lightFactory;
	private DirectionalLight directionalLight;
	private AmbientLight ambientLight;

	@Before
	public void setUp() {
		branchGroup = mock(IBranchGroup.class);
		gameGridFactory = mock(IGameGridFactory.class);
		lightFactory = mock(ILightFactory.class);

		ambientLight = mock(AmbientLight.class);
		doReturn(ambientLight).when(lightFactory).createAmbientLight();
		
		directionalLight = mock(DirectionalLight.class);
		doReturn(directionalLight).when(lightFactory).createDirectionalLight();
		
		gridGenerator = new GameGridGenerator(branchGroup, lightFactory);
		
		BOUNDS = new BoundingSphere();
	}

	@Test
	public void createBackgroundCreatesABackgroundWithGivenBounds() {
		gridGenerator.createBackground(BOUNDS);

		ArgumentCaptor<Background> backgroundCaptor = ArgumentCaptor.forClass(Background.class);
		verify(branchGroup).addChild(backgroundCaptor.capture());
		Background background = backgroundCaptor.getValue();
		
		assertEquals(BOUNDS, background.getApplicationBounds());
	}
	
	@Test
	public void createBackgroundCreatesABackgroundWithCorrectColor() {
		gridGenerator.createBackground(BOUNDS);

		ArgumentCaptor<Background> backgroundCaptor = ArgumentCaptor.forClass(Background.class);
		verify(branchGroup).addChild(backgroundCaptor.capture());
		Background background = backgroundCaptor.getValue();
		
		Color3f backgroundColor = new Color3f();
		background.getColor(backgroundColor);
		
		assertEquals(GameColors.BACKGROUND_COLOR, backgroundColor);
	}

	@Test
	public void lightSceneAddsAmbientAndDirectionalLight() {
		gridGenerator.lightScene(BOUNDS);

		verify(branchGroup).addChild(ambientLight);
		verify(branchGroup).addChild(directionalLight);
	}
	
//	@Test
//	public void testCreateGameGridAddsGameGridToBranchGroup() {
//		GameGridGenerator gridGenerator = new GameGridGenerator(branchGroup);
//
//		assertEquals(0, branchGroup.addedGroup.size());
//		assertFalse(gameGridFactory.gameGridconstructed);
//
//		PickerStub picker = new PickerStub();
//		gridGenerator.createGameGrid(picker, gameGridFactory);
//
//		assertTrue(gameGridFactory.gameGridconstructed);
//		assertTrue(branchGroup.addedGroup.size() > 0);
//	}
//
//	@Test
//	public void testCreateGameGridAlsoCreatesHighlightedGrid() {
//		GameGridGenerator gridGenerator = new GameGridGenerator(branchGroup);
//
//		PickerStub picker = new PickerStub();
//
//		assertFalse(gameGridFactory.highlightedGridContructed);
//		gridGenerator.createGameGrid(picker, gameGridFactory);
//		assertTrue(gameGridFactory.highlightedGridContructed);
//
//		assertEquals(2, branchGroup.addedGroup.size());
//	}
}
