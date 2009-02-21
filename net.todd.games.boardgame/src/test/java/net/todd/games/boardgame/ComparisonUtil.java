package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;

public class ComparisonUtil {
	public static void compareArrays(float[] array1, float[] array2) {
		for (int i = 0; i < array1.length; i++) {
			assertEquals(array1[i], array2[i]);
		}
	}
}
