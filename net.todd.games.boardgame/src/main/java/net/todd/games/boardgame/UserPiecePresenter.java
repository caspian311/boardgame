package net.todd.games.boardgame;

import net.todd.common.uitools.IListener;

public class UserPiecePresenter {
	public UserPiecePresenter(final IUserPieceView userPieceView,
			final IUserPieceModel userPieceModel) {
		userPieceView.setStartingPoint(userPieceModel.getStartingPoint());
		userPieceModel.addListener(new IListener() {
			public void fireEvent() {
				userPieceView.movePieceTo(userPieceModel.getCurrentPosition());
			}
		});
	}
}
