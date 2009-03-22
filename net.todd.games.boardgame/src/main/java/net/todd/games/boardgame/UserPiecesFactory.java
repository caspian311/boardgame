package net.todd.games.boardgame;

import javax.media.j3d.Bounds;

public class UserPiecesFactory implements IUserPiecesFactory {

	private final Bounds bounds;

	public UserPiecesFactory(Bounds bounds) {
		this.bounds = bounds;
	}

	public IBranchGroup constructUserPieces(IPicker picker) {
		IGameGridModel gameGridModel = GameGridModelProvider.getModel();
		IUserPiecesModel userPiecesModel = new UserPiecesModel(gameGridModel);
		IBranchGroupFactory branchGroupFactory = new BranchGroupFactory();
		IUserPiecesView userPiecesView = new UserPiecesView(bounds, picker, branchGroupFactory);
		new UserPiecesPresenter(userPiecesView, userPiecesModel);
		IBranchGroup branchGroup = userPiecesView.getBranchGroup();
		return branchGroup;
	}

}
