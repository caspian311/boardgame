package net.todd.games.boardgame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.PickInfo;
import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;
import net.todd.common.uitools.ListenerManager;

import com.sun.j3d.utils.pickfast.PickCanvas;

public class UserPiecesView implements IUserPiecesView {
	private Piece selectedPiece;
	private final ListenerManager pieceSelectedListeners = new ListenerManager();
	private final Bounds bounds;
	private final BranchGroup allPiecesBranchGroup;

	public UserPiecesView(Bounds bounds, Canvas3D canvas3D) {
		this.bounds = bounds;
		this.allPiecesBranchGroup = new BranchGroup();
		final PickCanvas pickCanvas = new PickCanvas(canvas3D, allPiecesBranchGroup);
		pickCanvas.setMode(PickInfo.PICK_GEOMETRY);
		pickCanvas.setTolerance(4.0f);

		canvas3D.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				pickCanvas.setShapeLocation(mouseEvent);
				PickInfo pickClosest = pickCanvas.pickClosest();
				if (pickClosest != null) {
					if (pickClosest.getNode() instanceof UserPiece) {
						UserPiece node = (UserPiece) pickClosest.getNode();
						selectedPiece = node.getPiece();
						pieceSelectedListeners.notifyListeners();
					}
				}
			}
		});
	}

	public void addPiece(Vector3f startingPoint) {
		Piece piece = new Piece(bounds, startingPoint);
		allPiecesBranchGroup.addChild(piece);
	}

	public void addPieceSelectedListener(IListener listener) {
		pieceSelectedListeners.addListener(listener);
	}

	public Piece getSelectedPiece() {
		return selectedPiece;
	}

	public BranchGroup getBranchGroup() {
		return allPiecesBranchGroup;
	}

	public void movePieceTo(Vector3f position) {
		if (selectedPiece != null) {
			selectedPiece.movePieceTo(position);
			selectedPiece = null;
		}
	}
}
