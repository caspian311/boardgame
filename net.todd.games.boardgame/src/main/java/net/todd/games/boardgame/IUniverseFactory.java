package net.todd.games.boardgame;

import javax.media.j3d.Canvas3D;

import com.sun.j3d.utils.universe.SimpleUniverse;

public interface IUniverseFactory {
	SimpleUniverse generateUniverse(Canvas3D canvas3D);
}
