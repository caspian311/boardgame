package net.todd.games.boardgame;

import java.util.ArrayList;
import java.util.List;

public class UserPieceModelFactory {
	private final IGameGridModel gameGridModel;

	public UserPieceModelFactory(IGameGridModel gameGridModel) {
		this.gameGridModel = gameGridModel;
	}

	public List<IUserPieceModel> getUserPieceModels() {
		List<IUserPieceModel> models = new ArrayList<IUserPieceModel>();
		float[][] allPositions = gameGridModel.getTeamOneStartingGridPositions();
		for (int i = 0; i < allPositions.length; i++) {
			models.add(new UserPieceModel(gameGridModel, allPositions[i]));
		}
		return models;
	}
}
