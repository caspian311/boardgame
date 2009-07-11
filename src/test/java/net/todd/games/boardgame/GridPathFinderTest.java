package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;

import org.junit.Before;
import org.junit.Test;

public class GridPathFinderTest {
	private GameStateStub gameState;
	private GameGridModelStub gameGridModel;

	@Before
	public void setUp() {
		gameState = new GameStateStub();
		gameGridModel = new GameGridModelStub();
	}

	@Test
	public void testPathIsNeverNull() {
		GridPathFinder pathFinder = new GridPathFinder(gameState, gameGridModel);

		assertNotNull(pathFinder.getPath(new Vector3f(), new Vector3f()));
	}

	@Test
	public void testIfNoObstaclesPathIsStraightestPathFromStartToEnd() {
		TileData tile1 = new TileData();
		tile1.setSize(10);
		tile1.setPosition(new float[] { -5, 0, -5 });
		TileData tile2 = new TileData();
		tile2.setSize(10);
		tile2.setPosition(new float[] { 5, 0, -5 });
		TileData tile3 = new TileData();
		tile3.setSize(10);
		tile3.setPosition(new float[] { -5, 0, 5 });
		TileData tile4 = new TileData();
		tile4.setSize(10);
		tile4.setPosition(new float[] { 5, 0, 5 });

		gameGridModel.tileData.add(tile1);
		gameGridModel.tileData.add(tile2);
		gameGridModel.tileData.add(tile3);
		gameGridModel.tileData.add(tile4);

		GridPathFinder pathFinder = new GridPathFinder(gameState, gameGridModel);

		Vector3f start = new Vector3f(-5, 0, -5);
		Vector3f end = new Vector3f(5, 0, 5);

		List<Vector3f> path = pathFinder.getPath(start, end);
		assertEquals(3, path.size());
		assertEquals(new Vector3f(-5, 5, -5), path.get(0));
		assertEquals(new Vector3f(-5, 5, 5), path.get(1));
		assertEquals(new Vector3f(5, 5, 5), path.get(2));
	}

	@Test
	public void testIfSlopeOfPathIsNotOne() {
		TileData tile1 = new TileData();
		tile1.setPosition(new float[] { -10, 0, -5 });
		TileData tile2 = new TileData();
		tile2.setPosition(new float[] { 0, 0, -5 });
		TileData tile3 = new TileData();
		tile3.setPosition(new float[] { 10, 0, -5 });
		TileData tile4 = new TileData();
		tile4.setPosition(new float[] { -10, 0, 5 });
		TileData tile5 = new TileData();
		tile5.setPosition(new float[] { 0, 0, 5 });
		TileData tile6 = new TileData();
		tile6.setPosition(new float[] { 10, 0, 5 });

		gameGridModel.tileData.add(tile1);
		gameGridModel.tileData.add(tile2);
		gameGridModel.tileData.add(tile3);
		gameGridModel.tileData.add(tile4);
		gameGridModel.tileData.add(tile5);
		gameGridModel.tileData.add(tile6);

		GridPathFinder pathFinder = new GridPathFinder(gameState, gameGridModel);

		Vector3f start = new Vector3f(-10, 0, -5);
		Vector3f end = new Vector3f(10, 0, 5);

		List<Vector3f> path = pathFinder.getPath(start, end);
		assertEquals(4, path.size());
		assertEquals(new Vector3f(-10, 5, -5), path.get(0));
		assertEquals(new Vector3f(0, 5, -5), path.get(1));
		assertEquals(new Vector3f(0, 5, 5), path.get(2));
		assertEquals(new Vector3f(10, 5, 5), path.get(3));
	}

	@Test
	public void testIfObstaclesInDirectPathReturnPathThatGoesAround() {
		TileData tile1 = new TileData();
		tile1.setPosition(new float[] { -10, 0, -10 });
		TileData tile2 = new TileData();
		tile2.setPosition(new float[] { 0, 0, -10 });
		TileData tile3 = new TileData();
		tile3.setPosition(new float[] { 10, 0, -10 });
		TileData tile4 = new TileData();
		tile4.setPosition(new float[] { -10, 0, 0 });
		TileData tile5 = new TileData();
		tile5.setPosition(new float[] { 0, 0, 0 });
		TileData tile6 = new TileData();
		tile6.setPosition(new float[] { 10, 0, 0 });
		TileData tile7 = new TileData();
		tile7.setPosition(new float[] { -10, 0, 10 });
		TileData tile8 = new TileData();
		tile8.setPosition(new float[] { 0, 0, 10 });
		TileData tile9 = new TileData();
		tile9.setPosition(new float[] { 10, 0, 10 });

		gameGridModel.tileData.add(tile1);
		gameGridModel.tileData.add(tile2);
		gameGridModel.tileData.add(tile3);
		gameGridModel.tileData.add(tile4);
		gameGridModel.tileData.add(tile5);
		gameGridModel.tileData.add(tile6);
		gameGridModel.tileData.add(tile7);
		gameGridModel.tileData.add(tile8);
		gameGridModel.tileData.add(tile9);

		PieceInfo piece1 = new PieceInfo();
		piece1.setPosition(new Vector3f(0f, 0f, 0f));
		gameState.allPieces.add(piece1);

		GridPathFinder pathFinder = new GridPathFinder(gameState, gameGridModel);

		Vector3f start = new Vector3f(-10, 0, -10);
		Vector3f end = new Vector3f(10, 0, 10);

		List<Vector3f> path = pathFinder.getPath(start, end);
		assertEquals(5, path.size());
		assertEquals(new Vector3f(-10, 5, -10), path.get(0));
		assertEquals(new Vector3f(-10, 5, 0), path.get(1));
		assertEquals(new Vector3f(-10, 5, 10), path.get(2));
		assertEquals(new Vector3f(0, 5, 10), path.get(3));
		assertEquals(new Vector3f(10, 5, 10), path.get(4));
	}

	@Test
	public void testIfMultipleObstaclesInDirectPathReturnPathThatGoesAround() {
		TileData tile1 = new TileData();
		tile1.setPosition(new float[] { -10, 0, -10 });
		TileData tile2 = new TileData();
		tile2.setPosition(new float[] { 0, 0, -10 });
		TileData tile3 = new TileData();
		tile3.setPosition(new float[] { 10, 0, -10 });
		TileData tile4 = new TileData();
		tile4.setPosition(new float[] { -10, 0, 0 });
		TileData tile5 = new TileData();
		tile5.setPosition(new float[] { 0, 0, 0 });
		TileData tile6 = new TileData();
		tile6.setPosition(new float[] { 10, 0, 0 });
		TileData tile7 = new TileData();
		tile7.setPosition(new float[] { -10, 0, 10 });
		TileData tile8 = new TileData();
		tile8.setPosition(new float[] { 0, 0, 10 });
		TileData tile9 = new TileData();
		tile9.setPosition(new float[] { 10, 0, 10 });

		gameGridModel.tileData.add(tile1);
		gameGridModel.tileData.add(tile2);
		gameGridModel.tileData.add(tile3);
		gameGridModel.tileData.add(tile4);
		gameGridModel.tileData.add(tile5);
		gameGridModel.tileData.add(tile6);
		gameGridModel.tileData.add(tile7);
		gameGridModel.tileData.add(tile8);
		gameGridModel.tileData.add(tile9);

		PieceInfo piece1 = new PieceInfo();
		piece1.setPosition(new Vector3f(0f, 0f, 0f));
		PieceInfo piece2 = new PieceInfo();
		piece2.setPosition(new Vector3f(-10f, 0f, 10f));
		gameState.allPieces.add(piece1);
		gameState.allPieces.add(piece2);

		GridPathFinder pathFinder = new GridPathFinder(gameState, gameGridModel);

		Vector3f start = new Vector3f(-10, 0, -10);
		Vector3f end = new Vector3f(10, 0, 10);

		List<Vector3f> path = pathFinder.getPath(start, end);
		assertEquals(5, path.size());
		assertEquals(new Vector3f(-10, 5, -10), path.get(0));
		assertEquals(new Vector3f(0, 5, -10), path.get(1));
		assertEquals(new Vector3f(10, 5, -10), path.get(2));
		assertEquals(new Vector3f(10, 5, 0), path.get(3));
		assertEquals(new Vector3f(10, 5, 10), path.get(4));
	}

	@Test
	public void testComplexCase() {
		PieceInfo piece1 = new PieceInfo();
		PieceInfo piece2 = new PieceInfo();
		PieceInfo piece3 = new PieceInfo();
		PieceInfo piece4 = new PieceInfo();
		PieceInfo piece5 = new PieceInfo();
		PieceInfo piece6 = new PieceInfo();
		PieceInfo piece7 = new PieceInfo();
		PieceInfo piece8 = new PieceInfo();

		piece1.setPosition(new Vector3f(-15.0f, 5.0f, -25.00002f));
		piece2.setPosition(new Vector3f(-5.0f, 5.0f, -25.00002f));
		piece3.setPosition(new Vector3f(5.0f, 5.0f, -25.00002f));
		piece4.setPosition(new Vector3f(24.900038f, 5.0f, -35.0f));
		piece5.setPosition(new Vector3f(-15.0f, 5.0f, 35.0f));
		piece6.setPosition(new Vector3f(-5.0f, 5.0f, 25.00002f));
		piece7.setPosition(new Vector3f(5.0f, 5.0f, 35.0f));
		piece8.setPosition(new Vector3f(15.0f, 5.0f, 35.0f));

		gameState.allPieces.add(piece1);
		gameState.allPieces.add(piece2);
		gameState.allPieces.add(piece3);
		gameState.allPieces.add(piece4);
		gameState.allPieces.add(piece5);
		gameState.allPieces.add(piece6);
		gameState.allPieces.add(piece7);
		gameState.allPieces.add(piece8);

		GridPathFinder pathFinder = new GridPathFinder(gameState, new GameGridModel(
				new GameGridData(), null));

		Vector3f start = new Vector3f(-15.0f, 5.0f, -5.0f);
		Vector3f end = new Vector3f(-15.0f, 5.0f, -25.0f);

		List<Vector3f> path = pathFinder.getPath(start, end);
		assertEquals(4, path.size());
		assertEquals(new Vector3f(-15.0f, 0.0f, -5.0f), path.get(0));
		assertEquals(new Vector3f(-25.0f, 0.0f, -5.0f), path.get(0));
		assertEquals(new Vector3f(-25.0f, 0.0f, -15.0f), path.get(0));
		assertEquals(new Vector3f(-15.0f, 0.0f, -25.0f), path.get(0));
	}

	private static class GameStateStub implements IGameState {
		private final List<PieceInfo> allPieces = new ArrayList<PieceInfo>();

		public List<PieceInfo> getAllPieces() {
			return allPieces;
		}

		public Team getTeamToMove() {
			throw new UnsupportedOperationException();
		}

		public void moveMade(String id, Vector3f targetLocation) {
			throw new UnsupportedOperationException();
		}
	}

	private static class GameGridModelStub implements IGameGridModel {
		private final List<TileData> tileData = new ArrayList<TileData>();

		public List<TileData> getTileData() {
			return tileData;
		}

		public void addTileSelectedListener(IListener listener) {
			throw new UnsupportedOperationException();
		}

		public void addUserPieceSelectedListener(IListener listener) {
			throw new UnsupportedOperationException();
		}

		public Vector3f getSelectedTileLocation() {
			throw new UnsupportedOperationException();
		}

		public TileData[] getTilesToHighlight() {
			throw new UnsupportedOperationException();
		}

		public void setSelectedTile(TileData tileData) {
			throw new UnsupportedOperationException();
		}

		public void setSelectedUserPiece(PieceInfo pieceInfo) {
			throw new UnsupportedOperationException();
		}
	}
}
