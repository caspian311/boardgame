package net.todd.games.boardgame;

import javax.media.j3d.Node;

import net.todd.common.uitools.IListener;
import net.todd.common.uitools.ListenerManager;

public class GameGridView implements IGameGridView {
	private final ListenerManager tileSelectedListeners = new ListenerManager();

	private final IBranchGroup board;
	private final IBranchGroup highlightingBranchGroup;
	private final IBranchGroupFactory branchGroupFactory;
	
	private TileData selectedTile;

	public GameGridView(IPickerFactory pickerFactory, IBranchGroupFactory branchGroupFactory) {
		this.branchGroupFactory = branchGroupFactory;
		board = branchGroupFactory.createBranchGroup();
		highlightingBranchGroup = branchGroupFactory.createBranchGroup();
		board.addChild(highlightingBranchGroup);

		final IPicker picker = pickerFactory.createPicker(board);
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

	public void highlightTiles(TileData[] tiles) {
		highlightingBranchGroup.removeAllChildren();
		IBranchGroup highlightedStuff = branchGroupFactory.createBranchGroup();
		
		for (TileData tileData : tiles) {
			tileData.getPosition()[1] += 0.5f;
			tileData.setColor(new float[] { 0f, 0.2f, 0.8f });
			tileData.setTransparent(true);
			highlightedStuff.addChild(new Tile(tileData));
		}

		highlightingBranchGroup.addChild(highlightedStuff);
	}
}
