package net.todd.games.boardgame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.Node;
import javax.media.j3d.PickInfo;

import net.todd.common.uitools.IListener;
import net.todd.common.uitools.ListenerManager;

import com.sun.j3d.utils.pickfast.PickCanvas;

public class Picker implements IPicker {
	private Node selectedNode;
	private final ListenerManager pickListenerManager = new ListenerManager();

	public Picker(IUniverse universe, IBranchGroup branchGroup) {
		Canvas3D canvas = universe.getCanvas();
		final PickCanvas pickCanvas = new PickCanvas(canvas, branchGroup.getInternal());
		pickCanvas.setMode(PickInfo.PICK_GEOMETRY);
		pickCanvas.setTolerance(4.0f);

		canvas.addMouseListener(new MouseAdapter() {
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

	public void addListener(IListener listener) {
		pickListenerManager.addListener(listener);
	}

	public Node getSelectedNode() {
		return selectedNode;
	}
}
