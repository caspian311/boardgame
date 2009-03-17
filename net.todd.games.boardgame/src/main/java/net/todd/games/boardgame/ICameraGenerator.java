package net.todd.games.boardgame;

import javax.media.j3d.Bounds;
import javax.media.j3d.Canvas3D;

import com.sun.j3d.utils.universe.SimpleUniverse;

public interface ICameraGenerator {
	void createCamera(SimpleUniverse su, Canvas3D canvas3D, Bounds bounds);
}
