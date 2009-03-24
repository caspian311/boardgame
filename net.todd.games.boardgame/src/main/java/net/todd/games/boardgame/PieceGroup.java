package net.todd.games.boardgame;

import java.util.Date;

import javax.media.j3d.Alpha;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.interpolators.KBKeyFrame;
import com.sun.j3d.utils.behaviors.interpolators.KBRotPosScaleSplinePathInterpolator;

public class PieceGroup extends BranchGroup implements IPieceGroup {
	private KBKeyFrame[] linearKeyFrames;
	private final Color3f color;
	private final TransformGroup userPieceTransformGroup;
	private final Bounds bounds;
	private Vector3f previousPosition;

	public PieceGroup(Bounds bounds, Vector3f startingPoint, Color3f color) {
		this.bounds = bounds;
		this.color = color;
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
		movePieceTo(startingPoint);
	}

	public Color3f getColor() {
		return color;
	}

	public void movePieceTo(final Vector3f position) {
		BranchGroup behaviorBranch = new BranchGroup();
		linearKeyFrames = new KBKeyFrame[2];
		linearKeyFrames[0] = createKeyFrameAtPosition(new Vector3f(new float[] { 0f, 0f, 0f }));
		linearKeyFrames[0].knot = 0f;
		linearKeyFrames[1] = createKeyFrameAtPosition(new Vector3f(new float[] { 0f, 0f, 0f }));
		linearKeyFrames[1].knot = 1f;

		Transform3D yAxis = new Transform3D();
		Alpha animAlpha = new Alpha(1, 2000);

		KBRotPosScaleSplinePathInterpolator linearInterpolator = new KBRotPosScaleSplinePathInterpolator(
				animAlpha, userPieceTransformGroup, yAxis, linearKeyFrames);
		linearInterpolator.setSchedulingBounds(bounds);
		linearInterpolator.setEnable(false);

		behaviorBranch.addChild(linearInterpolator);
		userPieceTransformGroup.addChild(behaviorBranch);

		linearInterpolator.setEnable(false);
		if (previousPosition == null) {
			previousPosition = position;
		}
		linearKeyFrames[0] = createKeyFrameAtPosition(previousPosition);
		linearKeyFrames[0].knot = 0;
		linearKeyFrames[1] = createKeyFrameAtPosition(position);
		linearKeyFrames[1].knot = 1;
		linearInterpolator.setKeyFrames(linearKeyFrames);
		linearInterpolator.getAlpha().setStartTime(new Date().getTime());
		linearInterpolator.setEnable(true);

		previousPosition = position;
	}

	private KBKeyFrame createKeyFrameAtPosition(Vector3f position) {
		if (position == null) {
			position = new Vector3f(0, 0, 0);
		}
		return new KBKeyFrame(1f, 0, new Point3f(position), 0f, 0f, 0, new Point3f(1, 1, 1), 0f,
				0f, 0f);
	}
}
