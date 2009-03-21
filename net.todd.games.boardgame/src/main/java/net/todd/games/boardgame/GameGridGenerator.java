package net.todd.games.boardgame;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.Bounds;
import javax.media.j3d.DirectionalLight;
import javax.vecmath.Vector3f;

public class GameGridGenerator implements ISceneGenerator {
	private final IBranchGroup branchGroup;

	public GameGridGenerator(IBranchGroup branchGroup) {
		this.branchGroup = branchGroup;
	}

	public void createGameGrid(IPicker picker, IGameGridFactory gameGridFactory) {
		IBranchGroup bg = gameGridFactory.constructGameGrid(picker);

		branchGroup.addChild(bg);
	}

	public void lightScene(Bounds bounds) {
		AmbientLight ambient = new AmbientLight(GameColors.LIGHT_COLOR);
		ambient.setInfluencingBounds(bounds);
		branchGroup.addChild(ambient);

		Vector3f lightDirection = new Vector3f(0.0f, -1.0f, 0.0f);
		DirectionalLight direct1 = new DirectionalLight(GameColors.LIGHT_COLOR, lightDirection);
		direct1.setInfluencingBounds(bounds);
		branchGroup.addChild(direct1);
	}

	public void createBackground(Bounds bounds) {
		Background background = new Background();
		background.setApplicationBounds(bounds);
		background.setColor(GameColors.BACKGROUND_COLOR);
		branchGroup.addChild(background);
	}
}
