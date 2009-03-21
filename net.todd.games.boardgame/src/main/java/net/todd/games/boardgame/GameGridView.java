package net.todd.games.boardgame;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;

import net.todd.common.uitools.IListener;
import net.todd.common.uitools.ListenerManager;

public class GameGridView implements IGameGridView {
	private final BranchGroup board;

	private TileData selectedTile;

	private final ListenerManager tileSelectedListeners = new ListenerManager();

	public GameGridView(final IPicker picker) {
		board = new BranchGroup();

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

	public BranchGroup getBG() {
		return board;
	}

	public TileData getSelectedTile() {
		return selectedTile;
	}

	public void addTileSelectedListener(IListener listener) {
		tileSelectedListeners.addListener(listener);
	}
}
