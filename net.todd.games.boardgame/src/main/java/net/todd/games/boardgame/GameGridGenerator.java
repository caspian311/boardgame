package net.todd.games.boardgame;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

public class GameGridGenerator implements ISceneGenerator {
	private static final Color3f white = new Color3f(1.0f, 1.0f, 1.0f);

	public void createGameGrid(BranchGroup bg, IPicker picker) {
		IGameGridView boardView = new GameGridView(picker);
		new GameGridPresenter(boardView, GameGridModelProvider.getModel());

		bg.addChild(boardView.getBG());
	}

	public void lightScene(BranchGroup bg, Bounds bounds) {
		AmbientLight ambient = new AmbientLight(white);
		ambient.setInfluencingBounds(bounds);
		bg.addChild(ambient);

		Vector3f lightDirection = new Vector3f(0.0f, -1.0f, 0.0f);
		DirectionalLight direct1 = new DirectionalLight(white, lightDirection);
		direct1.setInfluencingBounds(bounds);
		bg.addChild(direct1);
	}

	public void createBackground(BranchGroup bg, Bounds bounds) {
		Background background = new Background();
		background.setApplicationBounds(bounds);
		background.setColor(0.50f, 0.50f, 0.50f);
		bg.addChild(background);
	}
}
