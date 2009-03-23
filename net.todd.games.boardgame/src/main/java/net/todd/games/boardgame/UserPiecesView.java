package net.todd.games.boardgame;

import javax.media.j3d.Bounds;
import javax.media.j3d.Node;
import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;
import net.todd.common.uitools.ListenerManager;

public class UserPiecesView implements IUserPiecesView {
	private PieceGroup selectedPiece;
	private final ListenerManager pieceSelectedListeners = new ListenerManager();
	private final Bounds bounds;
	private final IBranchGroup allPiecesBranchGroup;

	public UserPiecesView(Bounds bounds, IPickerFactory pickerFactory,
			IBranchGroupFactory branchGroupFactory) {
		this.bounds = bounds;
		this.allPiecesBranchGroup = branchGroupFactory.createBranchGroup();

		final IPicker picker = pickerFactory.createPicker(allPiecesBranchGroup);
		picker.addListener(new IListener() {
			public void fireEvent() {
				Node selectedNode = picker.getSelectedNode();
				if (selectedNode instanceof SelectablePiece) {
					SelectablePiece node = (SelectablePiece) selectedNode;
					selectedPiece = node.getPiece();
					pieceSelectedListeners.notifyListeners();
				}
			}
		});
	}

	public void addPiece(PieceInfo pieceInfo) {
		Vector3f startingPoint = pieceInfo.getPosition();
		PieceGroup piece = new PieceGroup(bounds, startingPoint, pieceInfo.getColor());
		allPiecesBranchGroup.addChild(piece);
	}

	public void addPieceSelectedListener(IListener listener) {
		pieceSelectedListeners.addListener(listener);
	}

	public PieceGroup getSelectedPiece() {
		return selectedPiece;
	}

	public IBranchGroup getBranchGroup() {
		return allPiecesBranchGroup;
	}

	public void movePieceTo(Vector3f position) {
		if (selectedPiece != null) {
			selectedPiece.movePieceTo(position);
			selectedPiece = null;
		}
	}
}
