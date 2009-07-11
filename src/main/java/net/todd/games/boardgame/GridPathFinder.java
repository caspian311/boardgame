package net.todd.games.boardgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.vecmath.Vector3f;

public class GridPathFinder implements IGridPathFinder {
	private final List<Node> closed = new ArrayList<Node>();
	private final SortedList open = new SortedList();

	private int maxSearchDistance;

	private final Node[][] nodes;
	private boolean allowDiagMovement;

	private final List<Vector3f> occupiedPositionsOnGrid = new ArrayList<Vector3f>();

	public GridPathFinder(IGameState gameState, IGameGridData gameGridData) {
		for (PieceInfo pieceInfo : gameState.getAllPieces()) {
			occupiedPositionsOnGrid.add(pieceInfo.getPosition());
		}

		if (gameGridData.getTileData().length > 0) {
			nodes = new Node[gameGridData.getTileData().length][gameGridData.getTileData()[0].length];
			for (int x = 0; x < gameGridData.getTileData().length; x++) {
				for (int y = 0; y < gameGridData.getTileData()[x].length; y++) {
					TileData tileData = gameGridData.getTileData()[x][y];
					nodes[x][y] = new Node((int) tileData.getPosition()[0], (int) tileData
							.getPosition()[2]);
				}
			}
		} else {
			nodes = new Node[0][0];
		}
	}

	public List<Vector3f> getPath(Vector3f startingPoint, Vector3f endPoint) {
		if (isLocationBlocked(endPoint)) {
			return null;
		}

		int sx = (int) startingPoint.x;
		int sy = (int) startingPoint.y;
		int tx = (int) endPoint.x;
		int ty = (int) endPoint.y;

		// initial state for A*. The closed group is empty. Only the starting
		// tile is in the open list and it's cost is zero, i.e. we're already
		// there
		nodes[sx][sy].cost = 0;
		nodes[sx][sy].depth = 0;
		closed.clear();
		open.clear();
		open.add(nodes[sx][sy]);

		nodes[tx][ty].parent = null;

		// while we haven't found the goal and haven't exceeded our max search
		// depth
		int maxDepth = 0;
		while ((maxDepth < maxSearchDistance) && (open.size() != 0)) {
			// pull out the first node in our open list, this is determined to
			// be the most likely to be the next step based on our heuristic
			Node current = getFirstInOpen();
			if (current == nodes[tx][ty]) {
				break;
			}

			removeFromOpen(current);
			addToClosed(current);

			// search through all the neighbours of the current node evaluating
			// them as next steps
			for (int x = -1; x < 2; x++) {
				for (int y = -1; y < 2; y++) {
					// not a neighbour, its the current tile
					if ((x == 0) && (y == 0)) {
						continue;
					}

					// if we're not allowing diaganol movement then only
					// one of x or y can be set
					if (!allowDiagMovement) {
						if ((x != 0) && (y != 0)) {
							continue;
						}
					}

					// determine the location of the neighbour and evaluate it
					int xp = x + current.x;
					int yp = y + current.y;

					if (isValidLocation(new Vector3f(sx, 0f, sy), new Vector3f(xp, 0f, yp))) {
						// the cost to get to this node is cost the current plus
						// the movement
						// cost to reach this node. Note that the heursitic
						// value is only used
						// in the sorted open list
						float nextStepCost = current.cost + getMovementCost();
						Node neighbour = nodes[xp][yp];

						// if the new cost we've determined for this node is
						// lower than
						// it has been previously makes sure the node hasn't
						// been discarded. We've
						// determined that there might have been a better path
						// to get to
						// this node so it needs to be re-evaluated
						if (nextStepCost < neighbour.cost) {
							if (inOpenList(neighbour)) {
								removeFromOpen(neighbour);
							}
							if (inClosedList(neighbour)) {
								removeFromClosed(neighbour);
							}
						}

						// if the node hasn't already been processed and
						// discarded then
						// reset it's cost to our current cost and add it as a
						// next possible
						// step (i.e. to the open list)
						if (!inOpenList(neighbour) && !(inClosedList(neighbour))) {
							neighbour.cost = nextStepCost;
							neighbour.heuristic = getHeuristicCost(xp, yp, tx, ty);
							maxDepth = Math.max(maxDepth, neighbour.setParent(current));
							addToOpen(neighbour);
						}
					}
				}
			}
		}

		// since we've got an empty open list or we've run out of search
		// there was no path. Just return null
		if (nodes[tx][ty].parent == null) {
			return null;
		}

		// At this point we've definitely found a path so we can uses the parent
		// references of the nodes to find out way from the target location back
		// to the start recording the nodes on the way.
		List<Node> path = new ArrayList<Node>();
		Node target = nodes[tx][ty];
		while (target != nodes[sx][sy]) {
			path.add(0, new Node(target.x, target.y));
			target = target.parent;
		}
		path.add(0, new Node(sx, sy));

		// thats it, we have our path
		return convertToVectors(path);
	}

	private List<Vector3f> convertToVectors(List<Node> nodePath) {
		List<Vector3f> path = new ArrayList<Vector3f>();
		for (Node step : nodePath) {
			path.add(new Vector3f(step.x, 0, step.y));
		}

		return path;
	}

	private boolean isLocationBlocked(Vector3f location) {
		return contains(occupiedPositionsOnGrid, location);
	}

	private boolean isValidLocation(Vector3f startingLocation, Vector3f nextLocation) {
		return !isLocationBlocked(nextLocation)
				&& !contains(Arrays.asList(startingLocation), nextLocation);
	}

	/**
	 * Get the first element from the open list. This is the next one to be
	 * searched.
	 * 
	 * @return The first element in the open list
	 */
	protected Node getFirstInOpen() {
		return open.first();
	}

	/**
	 * Add a node to the open list
	 * 
	 * @param node
	 *            The node to be added to the open list
	 */
	protected void addToOpen(Node node) {
		open.add(node);
	}

	/**
	 * Check if a node is in the open list
	 * 
	 * @param node
	 *            The node to check for
	 * @return True if the node given is in the open list
	 */
	protected boolean inOpenList(Node node) {
		return open.contains(node);
	}

	/**
	 * Remove a node from the open list
	 * 
	 * @param node
	 *            The node to remove from the open list
	 */
	protected void removeFromOpen(Node node) {
		open.remove(node);
	}

	/**
	 * Add a node to the closed list
	 * 
	 * @param node
	 *            The node to add to the closed list
	 */
	protected void addToClosed(Node node) {
		closed.add(node);
	}

	/**
	 * Check if the node supplied is in the closed list
	 * 
	 * @param node
	 *            The node to search for
	 * @return True if the node specified is in the closed list
	 */
	protected boolean inClosedList(Node node) {
		return closed.contains(node);
	}

	/**
	 * Remove a node from the closed list
	 * 
	 * @param node
	 *            The node to remove from the closed list
	 */
	protected void removeFromClosed(Node node) {
		closed.remove(node);
	}

	/**
	 * Get the cost to move through a given location
	 * 
	 * @param mover
	 *            The entity that is being moved
	 * @param sx
	 *            The x coordinate of the tile whose cost is being determined
	 * @param sy
	 *            The y coordiante of the tile whose cost is being determined
	 * @param tx
	 *            The x coordinate of the target location
	 * @param ty
	 *            The y coordinate of the target location
	 * @return The cost of movement through the given tile
	 */
	public float getMovementCost() {
		return 1;
	}

	public float getHeuristicCost(int x, int y, int tx, int ty) {
		float dx = tx - x;
		float dy = ty - y;

		float result = (float) (Math.sqrt((dx * dx) + (dy * dy)));

		return result;
	}

	/**
	 * A simple sorted list
	 * 
	 * @author kevin
	 */
	private class SortedList {
		private final ArrayList<Node> list = new ArrayList<Node>();

		public Node first() {
			return list.get(0);
		}

		public void clear() {
			list.clear();
		}

		public void add(Node o) {
			list.add(o);
			Collections.sort(list);
		}

		public void remove(Object o) {
			list.remove(o);
		}

		public int size() {
			return list.size();
		}

		public boolean contains(Object o) {
			return list.contains(o);
		}
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

	private class Node implements Comparable<Node> {
		private final int x;
		private final int y;
		/** The path cost for this node */
		private float cost;
		/** The parent of this node, how we reached it in the search */
		private Node parent;
		/** The heuristic cost of this node */
		private float heuristic;
		/** The search depth of this node */
		private int depth;

		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int setParent(Node parent) {
			depth = parent.depth + 1;
			this.parent = parent;

			return depth;
		}

		public int compareTo(Node other) {
			float f = heuristic + cost;
			float of = other.heuristic + other.cost;

			if (f < of) {
				return -1;
			} else if (f > of) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}
