package net.todd.games.boardgame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.PickInfo;
import javax.vecmath.Color3f;

import net.todd.common.uitools.IListener;
import net.todd.common.uitools.ListenerManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.j3d.utils.pickfast.PickCanvas;

public class CheckeredBoardView implements ICheckeredBoardView {
	private final Log log = LogFactory.getLog(getClass());

	private static final Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
	private static final Color3f black = new Color3f(0.0f, 0.0f, 0.0f);

	private int colorIndex;

	private final BranchGroup board;

	private Tile selectedTile;

	private final ListenerManager tileSelectedListeners = new ListenerManager();

	public CheckeredBoardView(Canvas3D canvas3D) {
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

		creatGrid();
	}

	private void creatGrid() {
		for (int x = -35; x <= 35; x = x + 10) {
			for (int z = -35; z <= 35; z = z + 10) {
				log.debug("Drawing tile at: (" + x + ", 0, " + z + ")");

				Tile tile = new Tile(x, 0, z, 10, getNextColor());
				board.addChild(tile);
			}
			getNextColor();
		}
	}

	public BranchGroup getBG() {
		return board;
	}

	private Color3f getNextColor() {
		colorIndex++;
		return colorIndex % 2 == 0 ? black : white;
	}

	public Tile getSelectedTile() {
		return selectedTile;
	}

	public void addTileSelectedListener(IListener listener) {
		tileSelectedListeners.addListener(listener);
	}
}
