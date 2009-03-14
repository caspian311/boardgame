package net.todd.games.boardgame;

import java.util.Random;

import javax.vecmath.Vector3f;

public class PieceFixture {
	private static Random random = new Random();

	public static PieceInfo createPieceInfo() {
		PieceInfo info = new PieceInfo();
		float[] position = new float[3];
		position[0] = random.nextFloat();
		position[1] = random.nextFloat();
		position[2] = random.nextFloat();

		info.setPosition(new Vector3f(position));
		return info;
	}
}
