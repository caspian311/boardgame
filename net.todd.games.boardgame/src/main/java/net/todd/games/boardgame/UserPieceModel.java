package net.todd.games.boardgame;

public class UserPieceModel implements IUserPieceModel {
	private static final float PIECE_HEIGHT = 10f;
	private final IGameGridModel gameGridModel;

	public UserPieceModel(IGameGridModel gameGridModel) {
		this.gameGridModel = gameGridModel;
	}

	public float[] getStartingPoint() {
		float[] gridPosition = gameGridModel.getTeamOneStartingGridPosition();
		gridPosition[1] = PIECE_HEIGHT / 2;
		return gridPosition;
	}
}
