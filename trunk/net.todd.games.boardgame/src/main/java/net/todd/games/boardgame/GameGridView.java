package net.todd.games.boardgame;

import javax.media.j3d.Node;

import net.todd.common.uitools.IListener;
import net.todd.common.uitools.ListenerManager;

public class GameGridView implements IGameGridView {
	private final ListenerManager tileSelectedListeners = new ListenerManager();

	private final IBranchGroup board;

	private TileData selectedTile;

	public GameGridView(final IPicker picker,
			IBranchGroupFactory branchGroupFactory) {
		board = branchGroupFactory.createBranchGroup();

		picker.addListener(new IListener() {
			public void fireEvent() {
				Node selectedNode = picker.getSelectedNode();
				if (selectedNode instanceof Tile) {
					Tile tile = (Tile) selectedNode;
					selectedTile = tile.getTileData();
					tileSelectedListeners.notifyListeners();
				}
			}
		});
	}

	public void constructGrid(TileData[][] tileData) {
		for (int x = 0; x < tileData.length; x++) {
			for (int z = 0; z < tileData[0].length; z++) {
				Tile tile = new Tile(tileData[x][z]);
				board.addChild(tile);
			}
		}
	}

	public IBranchGroup getBranchGroup() {
		return board;
	}

	public TileData getSelectedTile() {
		return selectedTile;
	}

	public void addTileSelectedListener(IListener listener) {
		tileSelectedListeners.addListener(listener);
	}
}
