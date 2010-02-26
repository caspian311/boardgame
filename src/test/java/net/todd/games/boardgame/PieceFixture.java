package net.todd.games.boardgame;

import java.util.Random;

import javax.vecmath.Vector3f;

public class PieceFixture {
	private static Random random = new Random();

	public static PieceInfo createPieceInfo() {
		PieceInfo info = new PieceInfo();
		info.setPosition(new Vector3f(new float[] { random.nextFloat(),
				random.nextFloat(), random.nextFloat() }));
		return info;
	}
}
