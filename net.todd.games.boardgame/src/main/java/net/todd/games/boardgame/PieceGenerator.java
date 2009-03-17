package net.todd.games.boardgame;

import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;

public class PieceGenerator implements IPieceGenerator {
	public void createPieces(BranchGroup bg, Canvas3D canvas3D, Bounds bounds) {
		IGameGridModel gameGridModel = GameGridModelProvider.getModel();
		IUserPiecesModel userPiecesModel = new UserPiecesModel(gameGridModel);
		IUserPiecesView userPiecesView = new UserPiecesView(bounds, canvas3D);
		new UserPiecesPresenter(userPiecesView, userPiecesModel);

		bg.addChild(userPiecesView.getBranchGroup());
	}
}
