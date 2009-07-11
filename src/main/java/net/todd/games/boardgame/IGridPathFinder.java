package net.todd.games.boardgame;

import java.util.List;

import javax.vecmath.Vector3f;

public interface IGridPathFinder {
	List<Vector3f> getPath(Vector3f start, Vector3f end);
}
