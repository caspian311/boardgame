package net.todd.games.boardgame;

import java.util.List;

import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

public class PieceGroup extends BranchGroup implements IPieceGroup {
	private final TransformGroup userPieceTransformGroup;
	private final PieceInfo pieceInfo;
	private final Bounds bounds;

	public PieceGroup(Bounds bounds, PieceInfo pieceInfo) {
		setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		setCapability(BranchGroup.ALLOW_DETACH);

		this.bounds = bounds;
		this.pieceInfo = pieceInfo;
		userPieceTransformGroup = new TransformGroup(new Transform3D());
		userPieceTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		userPieceTransformGroup.setCapability(Group.ALLOW_CHILDREN_EXTEND);

		SelectablePiece userPiece = new SelectablePiece(this);

		Transform3D piecePosition = new Transform3D();
		TransformGroup pieceTG = new TransformGroup(piecePosition);
		pieceTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		pieceTG.addChild(userPiece);

		userPieceTransformGroup.addChild(pieceTG);

		addChild(userPieceTransformGroup);
		setPieceAtLocation(pieceInfo.getPosition());
	}

	private void setPieceAtLocation(Vector3f position) {
		Transform3D transformation = new Transform3D();
		userPieceTransformGroup.getTransform(transformation);
		transformation.set(position);
		userPieceTransformGroup.setTransform(transformation);
	}

	public Color3f getColor() {
		return pieceInfo.getColor();
	}

	public PieceInfo getPieceInfo() {
		return pieceInfo;
	}

	public void movePieceAlongPath(List<Vector3f> path) {
		MovementAnimation movementAnimation = new MovementAnimation(this, bounds,
				userPieceTransformGroup);
		movementAnimation.animateAlongPath(path);
	}
}
