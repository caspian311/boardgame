package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Bounds;
import javax.media.j3d.DirectionalLight;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class LightFactoryTest {
	private ILightFactory lightFactory;
	private Bounds bounds;

	@Before
	public void setUp() {
		bounds = mock(Bounds.class);
		lightFactory = new LightFactory(bounds);
	}
	
	@Test
	public void createAmbientLightThatIsTheRightColor() {
		AmbientLight ambientLight = lightFactory.createAmbientLight();
		
		Color3f color1 = new Color3f();
		ambientLight.getColor(color1);
		assertEquals(color1, GameColors.LIGHT_COLOR);
	}
	
	
	@Test
	public void createDirectionalLightThatIsTheRightColorAndDirection() {
		DirectionalLight directionalLight = lightFactory.createDirectionalLight();
		
		Color3f color2 = new Color3f();
		directionalLight.getColor(color2);
		assertEquals(color2, GameColors.LIGHT_COLOR);
		Vector3f direction = new Vector3f();
		directionalLight.getDirection(direction);
		assertEquals(new Vector3f(0, -1, 0), direction);
	}
	
	@Test
	@Ignore
	public void createAmbientLightThatHasTheRightInfluencingBounds() {
		AmbientLight ambientLight = lightFactory.createAmbientLight();
		
		assertEquals(bounds, ambientLight.getInfluencingBounds());
	}
	
	@Test
	@Ignore
	public void createDirectionalLightThatHasTheRightInfluencingBounds() {
		DirectionalLight directionalLight = lightFactory.createDirectionalLight();
		
		assertEquals(bounds, directionalLight.getInfluencingBounds());
	}
}
