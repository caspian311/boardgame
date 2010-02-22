package net.todd.games.boardgame;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Bounds;
import javax.media.j3d.DirectionalLight;
import javax.vecmath.Vector3f;

public class LightFactory implements ILightFactory {
	private final Bounds bounds;

	public LightFactory(Bounds bounds) {
		this.bounds = bounds;
	}

	public AmbientLight createAmbientLight() {
		AmbientLight ambientLight = new AmbientLight(GameColors.LIGHT_COLOR);
		ambientLight.setInfluencingBounds(bounds);
		return ambientLight;
	}

	public DirectionalLight createDirectionalLight() {
		Vector3f lightDirection = new Vector3f(0.0f, -1.0f, 0.0f);
		DirectionalLight directionalLight = new DirectionalLight(GameColors.LIGHT_COLOR,
				lightDirection);
		directionalLight.setInfluencingBounds(bounds);
		return directionalLight;
	}
}
