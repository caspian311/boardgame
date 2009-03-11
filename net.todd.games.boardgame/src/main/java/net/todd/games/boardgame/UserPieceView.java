package net.todd.games.boardgame;

import java.util.Date;

import javax.media.j3d.Alpha;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;

import com.sun.j3d.utils.behaviors.interpolators.KBKeyFrame;
import com.sun.j3d.utils.behaviors.interpolators.KBRotPosScaleSplinePathInterpolator;

public class UserPieceView implements IUserPieceView {
	private final UserPiece piece;
	private KBRotPosScaleSplinePathInterpolator linearInterpolator;
	private KBKeyFrame[] linearKeyFrames;

	public UserPieceView(BranchGroup bg, Bounds bounds, Canvas3D canvas3D) {
		// final PickCanvas pickCanvas = new PickCanvas(canvas3D, bg);
		// pickCanvas.setMode(PickInfo.PICK_GEOMETRY);
		// pickCanvas.setTolerance(4.0f);
		//
		// canvas3D.addMouseListener(new MouseAdapter() {
		// @Override
		// public void mouseClicked(MouseEvent mouseEvent) {
		// pickCanvas.setShapeLocation(mouseEvent);
		// PickInfo pickClosest = pickCanvas.pickClosest();
		// if (pickClosest.getNode() instanceof Tile) {
		// Tile tile = (Tile) pickClosest.getNode();
		// selectedTile = tile.getTileData();
		// tileSelectedListeners.notifyListeners();
		// }
		// }
		// });
		
		
		TransformGroup userPieceTransformGroup = new TransformGroup(
				new Transform3D());
		userPieceTransformGroup
				.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		piece = new UserPiece();

		userPieceTransformGroup.addChild(piece.getGraphNode());
		createMovingBehavior(userPieceTransformGroup, bounds);
		// bg.addChild(piece.getShadowNode(lightDirection));

		bg.addChild(userPieceTransformGroup);
	}

	private void createMovingBehavior(TransformGroup userPieceTransformGroup,
			Bounds bounds) {
		BranchGroup behaviorBranch = new BranchGroup();
		linearKeyFrames = new KBKeyFrame[2];
		linearKeyFrames[0] = createKeyFrameAtPosition(new float[] { 0f, 0f, 0f });
		linearKeyFrames[0].knot = 0f;
		linearKeyFrames[1] = createKeyFrameAtPosition(new float[] { 0f, 0f, 0f });
		linearKeyFrames[1].knot = 1f;
		
		Transform3D yAxis = new Transform3D();
		Alpha animAlpha = new Alpha(1, 2000);
		
		linearInterpolator = new KBRotPosScaleSplinePathInterpolator(
				animAlpha, userPieceTransformGroup, yAxis, linearKeyFrames);
		linearInterpolator.setSchedulingBounds(bounds);
		linearInterpolator.setEnable(false);
		
		behaviorBranch.addChild(linearInterpolator);
		userPieceTransformGroup.addChild(behaviorBranch);
	}

	public void setStartingPoint(float[] startingPoint) {
		 linearKeyFrames[0] = createKeyFrameAtPosition(startingPoint);
		linearKeyFrames[0].knot = 0f;
		linearKeyFrames[1] = createKeyFrameAtPosition(startingPoint);
		linearKeyFrames[1].knot = 1f;
		linearInterpolator.setKeyFrames(linearKeyFrames);
		linearInterpolator.setEnable(true);
		// piece.setPosition(new Vector3f(startingPoint));
	}

	public void movePieceTo(float[] position) {
		linearInterpolator.setEnable(false);
		linearKeyFrames[0] = linearKeyFrames[1];
		linearKeyFrames[0].knot = 0;
		linearKeyFrames[1] = createKeyFrameAtPosition(position);
		linearKeyFrames[1].knot = 1;
		linearInterpolator.setKeyFrames(linearKeyFrames);
		linearInterpolator.getAlpha().setStartTime(new Date().getTime());
		linearInterpolator.setEnable(true);
	}

	private KBKeyFrame createKeyFrameAtPosition(float[] position) {
		return new KBKeyFrame(1f, 0, new Point3f(position), 0f, 0f, 0,
				new Point3f(1, 1, 1), 0f, 0f, 0f);
	}
}
