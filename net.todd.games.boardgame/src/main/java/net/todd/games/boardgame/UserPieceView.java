package net.todd.games.boardgame;

import javax.media.j3d.BranchGroup;
import javax.vecmath.Vector3f;

public class UserPieceView implements IUserPieceView {
	private final UserPiece piece;

	public UserPieceView(BranchGroup bg) {
		piece = new UserPiece();

		bg.addChild(piece.getGraphNode());
		// bg.addChild(piece.getShadowNode(lightDirection));
	}

	public void setStartingPoint(float[] startingPoint) {
		piece.setPosition(new Vector3f(startingPoint));
	}

	public void movePieceTo(float[] position) {
		// TODO make this an animation
		setStartingPoint(position);
	}
}
