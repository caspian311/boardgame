package net.todd.games.boardgame;

import javax.media.j3d.Bounds;
import javax.media.j3d.Node;
import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;

public class UserPiecesView implements IUserPiecesView {
	private PieceGroup selectedPiece;
	private final Bounds bounds;
	private final IBranchGroup allPiecesBranchGroup;
	private final IBranchGroup teamTwoBranchGroup;
	private final IBranchGroup teamOneBranchGroup;

	public UserPiecesView(Bounds bounds, IPickerFactory pickerFactory,
			IBranchGroupFactory branchGroupFactory) {
		this.bounds = bounds;
		this.allPiecesBranchGroup = branchGroupFactory.createBranchGroup();
		this.teamOneBranchGroup = branchGroupFactory.createBranchGroup();
		this.teamTwoBranchGroup = branchGroupFactory.createBranchGroup();

		allPiecesBranchGroup.addChild(teamOneBranchGroup);
		allPiecesBranchGroup.addChild(teamTwoBranchGroup);

		final IPicker teamOnePicker = pickerFactory.createPicker(teamOneBranchGroup);
		teamOnePicker.addListener(new IListener() {
			public void fireEvent() {
				Node selectedNode = teamOnePicker.getSelectedNode();
				if (selectedNode instanceof SelectablePiece) {
					SelectablePiece node = (SelectablePiece) selectedNode;
					selectedPiece = node.getPiece();
				}
			}
		});

		final IPicker teamTwoPicker = pickerFactory.createPicker(teamTwoBranchGroup);
		teamTwoPicker.addListener(new IListener() {
			public void fireEvent() {
				Node selectedNode = teamTwoPicker.getSelectedNode();
				if (selectedNode instanceof SelectablePiece) {
					SelectablePiece node = (SelectablePiece) selectedNode;
					selectedPiece = node.getPiece();
				}
			}
		});
	}

	public void addPiece(PieceInfo pieceInfo) {
		Vector3f startingPoint = pieceInfo.getPosition();
		PieceGroup piece = new PieceGroup(bounds, startingPoint, pieceInfo.getColor());
		if (pieceInfo.getTeam() == Team.ONE) {
			teamOneBranchGroup.addChild(piece);
		} else {
			teamTwoBranchGroup.addChild(piece);
		}
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
