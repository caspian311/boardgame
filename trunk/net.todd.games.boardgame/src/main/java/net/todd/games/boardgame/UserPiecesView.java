package net.todd.games.boardgame;

import javax.media.j3d.Bounds;
import javax.media.j3d.Node;

import net.todd.common.uitools.IListener;
import net.todd.common.uitools.ListenerManager;

public class UserPiecesView implements IUserPiecesView {
	private IPieceGroup selectedPiece;
	private final Bounds bounds;
	private final IBranchGroup allPiecesBranchGroup;

	private final ListenerManager pieceSelectedListenerManager = new ListenerManager();

	public UserPiecesView(Bounds bounds, final IPicker piecesPicker,
			IBranchGroupFactory branchGroupFactory) {
		this.bounds = bounds;
		this.allPiecesBranchGroup = branchGroupFactory.createBranchGroup();

		piecesPicker.addListener(new IListener() {
			public void fireEvent() {
				Node selectedNode = piecesPicker.getSelectedNode();
				if (selectedNode instanceof SelectablePiece) {
					SelectablePiece node = (SelectablePiece) selectedNode;
					selectedPiece = node.getPiece();
				}
				pieceSelectedListenerManager.notifyListeners();
			}
		});
	}

	public void addPiece(PieceInfo pieceInfo) {
		PieceGroup piece = new PieceGroup(bounds, pieceInfo);
		allPiecesBranchGroup.addChild(piece);
	}

	public IBranchGroup getBranchGroup() {
		return allPiecesBranchGroup;
	}

	public void addPieceSelectedListener(IListener listener) {
		pieceSelectedListenerManager.addListener(listener);
	}

	public IPieceGroup getSelectedPiece() {
		return selectedPiece;
	}
}
