package net.todd.games.boardgame;

import javax.media.j3d.Bounds;

public class UserPiecesFactory implements IUserPiecesFactory {
	private final Bounds bounds;

	public UserPiecesFactory(Bounds bounds) {
		this.bounds = bounds;
	}

	public IBranchGroup constructUserPieces(IPickerFactory pickerFactory) {
		IGameGridModel gameGridModel = GameGridModelProvider.getModel();
		IGamePieceData gamePieceData = new GamePieceData();
		IGameState gameState = new GameState(gamePieceData);
		IMovementRuleCollection moveRuleCollection = new MovementRuleCollection(
				new ProximityRule(), new AvailabilityRule(gameState));
		IMoveValidator moveValidator = new MoveValidator(gameState,
				moveRuleCollection);
		IUserPiecesModel userPiecesModel = new UserPiecesModel(gamePieceData,
				gameGridModel, moveValidator);
		IUserPiecesView userPiecesView = new UserPiecesView(bounds,
				pickerFactory, new BranchGroupFactory());
		new UserPiecesPresenter(userPiecesView, userPiecesModel);
		IBranchGroup branchGroup = userPiecesView.getBranchGroup();
		return branchGroup;
	}
}
