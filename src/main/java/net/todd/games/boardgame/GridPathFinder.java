package net.todd.games.boardgame;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

public class GridPathFinder implements IGridPathFinder {
	private final List<Vector3f> allLocationsOnGrid = new ArrayList<Vector3f>();
	private final List<Vector3f> path = new ArrayList<Vector3f>();
	private final List<Vector3f> occupiedPositionsOnGrid = new ArrayList<Vector3f>();
	private final List<Vector3f> wrongMoves = new ArrayList<Vector3f>();

	public GridPathFinder(IGameState gameState, IGameGridModel gameGridModel) {
		for (PieceInfo pieceInfo : gameState.getAllPieces()) {
			occupiedPositionsOnGrid.add(pieceInfo.getPosition());
		}
		for (TileData tile : gameGridModel.getTileData()) {
			allLocationsOnGrid.add(new Vector3f(tile.getPosition()[0], tile.getPosition()[1], tile
					.getPosition()[2]));
		}
	}

	public List<Vector3f> getPath(Vector3f startingPoint, Vector3f endPoint) {
		path.clear();
		startingPoint.y = UserPieceModel.PIECE_HEIGHT / 2;
		path.add(startingPoint);
		Vector3f currentStep = startingPoint;

		while (!arePointsSame(currentStep, endPoint)) {
			currentStep = findNextStep(currentStep, endPoint);
			if (currentStep == null) {
				printPathFinderState(startingPoint, endPoint);
				throw new IllegalStateException("no valid move available...");
			}
			if (!path.contains(currentStep)) {
				path.add(currentStep);
			}
		}

		if (path.size() == 1) {
			path.add(endPoint);
		}

		return path;
	}

	private void printPathFinderState(Vector3f startingPoint, Vector3f endPoint) {
		System.err.println("Trying to find path: " + printLocation(startingPoint) + " to "
				+ printLocation(endPoint));
		System.err.println("The grid:");
		for (Vector3f location : allLocationsOnGrid) {
			System.err.println(printLocation(location));
		}
		System.err.println("Occupied positions on the grid:");
		for (Vector3f location : occupiedPositionsOnGrid) {
			System.err.println(printLocation(location));
		}
	}

	private String printLocation(Vector3f point) {
		return "(" + point.x + ", " + point.y + ", " + point.z + ")";
	}

	private boolean arePointsSame(Vector3f currentStep, Vector3f endPoint) {
		boolean same = false;
		if (currentStep.x == endPoint.x) {
			if (currentStep.z == endPoint.z) {
				same = true;
			}
		}
		return same;
	}

	private Vector3f findNextStep(Vector3f current, Vector3f end) {
		List<Vector3f> potentialSteps = getPotentialNextSteps(current);
		Vector3f backStep = null;
		Vector3f nextStep = null;
		float shortestDistance = 9999f;
		for (Vector3f potentialStep : potentialSteps) {
			if (contains(allLocationsOnGrid, potentialStep)) {
				if (!contains(occupiedPositionsOnGrid, potentialStep)) {
					if (!contains(wrongMoves, potentialStep)) {
						float distance = getDistanceFromEndPoint(potentialStep, end);
						if (distance < shortestDistance) {
							if (!contains(path, potentialStep)) {
								shortestDistance = distance;
								nextStep = potentialStep;
							} else {
								backStep = potentialStep;
							}
						}
					}
				}
			}
		}
		if (nextStep == null) {
			if (backStep != null) {
				nextStep = backStep;
				nextStep.y = UserPieceModel.PIECE_HEIGHT / 2;
				path.remove(current);
				wrongMoves.add(current);
			}
		} else {
			nextStep.y = UserPieceModel.PIECE_HEIGHT / 2;
		}

		return nextStep;
	}

	private boolean contains(List<Vector3f> listOfLocations, Vector3f position) {
		for (Vector3f location : listOfLocations) {
			if ((int) position.x == (int) location.x) {
				if ((int) location.z == (int) position.z) {
					return true;
				}
			}
		}
		return false;
	}

	private List<Vector3f> getPotentialNextSteps(Vector3f current) {
		List<Vector3f> nextSteps = new ArrayList<Vector3f>();
		nextSteps.add(new Vector3f(current.x, 0, current.z + GameGridData.TILE_SIZE));
		nextSteps.add(new Vector3f(current.x - GameGridData.TILE_SIZE, 0, current.z));
		nextSteps.add(new Vector3f(current.x + GameGridData.TILE_SIZE, current.y, current.z));
		nextSteps.add(new Vector3f(current.x, 0, current.z - GameGridData.TILE_SIZE));
		return nextSteps;
	}

	private float getDistanceFromEndPoint(Vector3f current, Vector3f endPoint) {
		return new Point3f(current).distance(new Point3f(endPoint));
	}
}
