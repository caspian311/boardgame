package net.todd.games.boardgame;

import javax.media.j3d.Bounds;

public class PieceGenerator implements IPieceGenerator {
	private final IBranchGroup bg;

	public PieceGenerator(IBranchGroup bg) {
		this.bg = bg;
	}

	public void createPieces(IPicker picker, Bounds bounds) {
		IGameGridModel gameGridModel = GameGridModelProvider.getModel();
		IUserPiecesModel userPiecesModel = new UserPiecesModel(gameGridModel);
		IUserPiecesView userPiecesView = new UserPiecesView(bounds, picker);
		new UserPiecesPresenter(userPiecesView, userPiecesModel);

		bg.addChild(userPiecesView.getBranchGroup());
	}
}
