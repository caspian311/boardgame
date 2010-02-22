package net.todd.games.boardgame;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.DirectionalLight;

public interface ILightFactory {
	AmbientLight createAmbientLight();

	DirectionalLight createDirectionalLight();
}
