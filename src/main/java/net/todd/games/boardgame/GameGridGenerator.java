package net.todd.games.boardgame;

import javax.media.j3d.Background;
import javax.media.j3d.Bounds;

public class GameGridGenerator implements ISceneGenerator {
	private final IBranchGroup branchGroup;
	private final ILightFactory lightFactory;

	public GameGridGenerator(IBranchGroup branchGroup, ILightFactory lightFactory) {
		this.branchGroup = branchGroup;
		this.lightFactory = lightFactory;
	}

	public void createGameGrid(IPicker picker, IGameGridFactory gameGridFactory) {
		branchGroup.addChild(gameGridFactory.constructGameGrid(picker));
		branchGroup.addChild(gameGridFactory.constructHighlightedGrid());
	}

	public void lightScene(Bounds bounds) {
		branchGroup.addChild(lightFactory.createAmbientLight());
		branchGroup.addChild(lightFactory.createDirectionalLight());
	}

	public void createBackground(Bounds bounds) {
		Background background = new Background();
		background.setApplicationBounds(bounds);
		background.setColor(GameColors.BACKGROUND_COLOR);
		branchGroup.addChild(background);
	}
}
