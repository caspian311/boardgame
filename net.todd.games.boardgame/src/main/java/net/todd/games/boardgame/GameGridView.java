package net.todd.games.boardgame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.PickInfo;

import net.todd.common.uitools.IListener;
import net.todd.common.uitools.ListenerManager;

import com.sun.j3d.utils.pickfast.PickCanvas;

public class GameGridView implements IGameGridView {
	private final BranchGroup board;

	private ITile selectedTile;

	private final ListenerManager tileSelectedListeners = new ListenerManager();

	public GameGridView(Canvas3D canvas3D) {
		board = new BranchGroup();

		final PickCanvas pickCanvas = new PickCanvas(canvas3D, board);
		pickCanvas.setMode(PickInfo.PICK_GEOMETRY);
		pickCanvas.setTolerance(4.0f);

		canvas3D.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				pickCanvas.setShapeLocation(mouseEvent);
				PickInfo pickClosest = pickCanvas.pickClosest();
				if (pickClosest != null) {
					Tile tile = (Tile) pickClosest.getNode();
					selectedTile = tile;
					tileSelectedListeners.notifyListeners();
				}
			}
		});
	}

	public void constructGrid(GameGridData data) {
		for (int x = 0; x < data.getTileData().length; x++) {
			for (int z = 0; z < data.getTileData()[0].length; z++) {
				Tile tile = new Tile(data.getTileData()[x][z]);
				board.addChild(tile);
			}
		}
	}

	public BranchGroup getBG() {
		return board;
	}

	public ITile getSelectedTile() {
		return selectedTile;
	}

	public void addTileSelectedListener(IListener listener) {
		tileSelectedListeners.addListener(listener);
	}
}
