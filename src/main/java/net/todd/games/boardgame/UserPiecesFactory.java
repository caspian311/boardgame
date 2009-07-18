package net.todd.games.boardgame;

import javax.media.j3d.Bounds;

public class UserPiecesFactory implements IUserPiecesFactory {
	private final Bounds bounds;

	public UserPiecesFactory(Bounds bounds) {
		this.bounds = bounds;
	}

	public IBranchGroup constructUserPieces(IPicker picker) {
		IGameGridModel gameGridModel = GameGridModelProvider.getModel();
		IGameState gameState = GameStateProvider.getGameState();
		IMovementRuleCollection moveRuleCollection = MovementRuleCollectionProvider
				.getRuleCollection();
		IMoveValidator moveValidator = new MoveValidator(gameState, moveRuleCollection);
		IGridPathFinder gridPathFinder = new GridPathFinder(gameState, gameGridModel);
		IUserPiecesModel userPiecesModel = new UserPieceModel(gameState, gameGridModel,
				moveValidator, gridPathFinder);
		IUserPiecesView userPiecesView = new UserPiecesView(bounds, picker,
				new BranchGroupFactory());
		new UserPiecesPresenter(userPiecesView, userPiecesModel);
		IBranchGroup branchGroup = userPiecesView.getBranchGroup();
		return branchGroup;
	}
}
