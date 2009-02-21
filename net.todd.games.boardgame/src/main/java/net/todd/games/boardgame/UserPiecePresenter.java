package net.todd.games.boardgame;

public class UserPiecePresenter {
	public UserPiecePresenter(IUserPieceView userPieceView, IUserPieceModel userPieceModel) {
		userPieceView.setStartingPoint(userPieceModel.getStartingPoint());
	}
}
