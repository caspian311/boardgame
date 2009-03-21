package net.todd.games.boardgame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Node;
import javax.media.j3d.PickInfo;

import net.todd.common.uitools.IListener;
import net.todd.common.uitools.ListenerManager;

import com.sun.j3d.utils.pickfast.PickCanvas;

public class Picker implements IPicker {
	private Node selectedNode;
	private final ListenerManager pickListenerManager = new ListenerManager();

	public Picker(Canvas3D canvas3D, BranchGroup branchGroup) {
		final PickCanvas pickCanvas = new PickCanvas(canvas3D, branchGroup);
		pickCanvas.setMode(PickInfo.PICK_GEOMETRY);
		pickCanvas.setTolerance(4.0f);

		canvas3D.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				pickCanvas.setShapeLocation(mouseEvent);
				PickInfo pickClosest = pickCanvas.pickClosest();
				if (pickClosest != null) {
					selectedNode = pickClosest.getNode();
					pickListenerManager.notifyListeners();
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.todd.games.boardgame.IPicker#addListener(net.todd.common.uitools.
	 * IListener)
	 */
	public void addListener(IListener listener) {
		pickListenerManager.addListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.todd.games.boardgame.IPicker#getSelectedNode()
	 */
	public Node getSelectedNode() {
		return selectedNode;
	}
}
