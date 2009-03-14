package net.todd.games.boardgame;

import java.util.Date;

import javax.media.j3d.Alpha;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.interpolators.KBKeyFrame;
import com.sun.j3d.utils.behaviors.interpolators.KBRotPosScaleSplinePathInterpolator;

public class Piece extends BranchGroup implements IPiece {
	private KBRotPosScaleSplinePathInterpolator linearInterpolator;
	private KBKeyFrame[] linearKeyFrames;

	public Piece(Bounds bounds, Vector3f startingPoint) {
		TransformGroup userPieceTransformGroup = new TransformGroup(new Transform3D());
		userPieceTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		UserPiece piece = new UserPiece(this);

		Transform3D piecePosition = new Transform3D();
		TransformGroup pieceTG = new TransformGroup(piecePosition);
		pieceTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		pieceTG.addChild(piece);

		userPieceTransformGroup.addChild(pieceTG);
		createMovingBehavior(userPieceTransformGroup, bounds);

		addChild(userPieceTransformGroup);
		movePieceTo(startingPoint);
	}

	private void createMovingBehavior(TransformGroup userPieceTransformGroup, Bounds bounds) {
		BranchGroup behaviorBranch = new BranchGroup();
		linearKeyFrames = new KBKeyFrame[2];
		linearKeyFrames[0] = createKeyFrameAtPosition(new Vector3f(new float[] { 0f, 0f, 0f }));
		linearKeyFrames[0].knot = 0f;
		linearKeyFrames[1] = createKeyFrameAtPosition(new Vector3f(new float[] { 0f, 0f, 0f }));
		linearKeyFrames[1].knot = 1f;

		Transform3D yAxis = new Transform3D();
		Alpha animAlpha = new Alpha(1, 2000);

		linearInterpolator = new KBRotPosScaleSplinePathInterpolator(animAlpha,
				userPieceTransformGroup, yAxis, linearKeyFrames);
		linearInterpolator.setSchedulingBounds(bounds);
		linearInterpolator.setEnable(false);

		behaviorBranch.addChild(linearInterpolator);
		userPieceTransformGroup.addChild(behaviorBranch);
	}

	public void movePieceTo(Vector3f position) {
		linearInterpolator.setEnable(false);
		linearKeyFrames[0] = linearKeyFrames[1];
		linearKeyFrames[0].knot = 0;
		linearKeyFrames[1] = createKeyFrameAtPosition(position);
		linearKeyFrames[1].knot = 1;
		linearInterpolator.setKeyFrames(linearKeyFrames);
		linearInterpolator.getAlpha().setStartTime(new Date().getTime());
		linearInterpolator.setEnable(true);
	}

	private KBKeyFrame createKeyFrameAtPosition(Vector3f position) {
		return new KBKeyFrame(1f, 0, new Point3f(position), 0f, 0f, 0, new Point3f(1, 1, 1), 0f,
				0f, 0f);
	}
}
