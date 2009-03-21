package net.todd.games.boardgame;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;

import com.sun.j3d.utils.universe.SimpleUniverse;

public interface IGameEngine {

	public abstract void createScene(Canvas3D canvas3D);

	public abstract void createCamera(SimpleUniverse su, Canvas3D canvas3D);

	public abstract BranchGroup getBranchGroup();

}