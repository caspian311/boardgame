package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;

public class ComparisonUtil {
	public static void compareArrays(float[] array1, float[] array2) {
		for (int i = 0; i < array1.length; i++) {
			assertEquals(array1[i], array2[i]);
		}
	}

	public static void compareDoubleArrays(Object[][] data1, Object[][] data2) {
		for (int i = 0; i < data1.length; i++) {
			for (int t = 0; t < data1[i].length; i++) {
				assertEquals(data1[i][t], data2[i][t]);
			}
		}
	}
}
