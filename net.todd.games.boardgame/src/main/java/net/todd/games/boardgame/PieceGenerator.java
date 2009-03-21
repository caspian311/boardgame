package net.todd.games.boardgame;

import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;

public class PieceGenerator implements IPieceGenerator {
	public void createPieces(BranchGroup bg, IPicker picker, Bounds bounds) {
		IGameGridModel gameGridModel = GameGridModelProvider.getModel();
		IUserPiecesModel userPiecesModel = new UserPiecesModel(gameGridModel);
		IUserPiecesView userPiecesView = new UserPiecesView(bounds, picker);
		new UserPiecesPresenter(userPiecesView, userPiecesModel);

		bg.addChild(userPiecesView.getBranchGroup());
	}
}
