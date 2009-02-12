package net.todd.games.boardgame;

import javax.media.j3d.BranchGroup;
import javax.vecmath.Color3f;

public class CheckeredBoard {
	private static final Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
	private static final Color3f black = new Color3f(0.0f, 0.0f, 0.0f);

	private int colorIndex;

	private final BranchGroup boardBG;

	public CheckeredBoard() {
		boardBG = new BranchGroup();

		for (int x = -35; x < 35; x = x + 10) {
			for (int z = -35; z < 35; z = z + 10) {
				Tile tile = new Tile(x, 0, z, 10, getNextColor());
				boardBG.addChild(tile.getShape());
			}
		}
	}

	public BranchGroup getBG() {
		return boardBG;
	}

	private Color3f getNextColor() {
		colorIndex++;
		return colorIndex % 2 == 0 ? white : black;
	}
}
