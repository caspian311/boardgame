package net.todd.games.boardgame;

import net.todd.common.uitools.IListener;

public class UserPiecesPresenter {
	public UserPiecesPresenter(final IUserPiecesView userPieceView,
			final IUserPiecesModel userPieceModel) {
		for (PieceInfo pieceInfo : userPieceModel.getAllPieces()) {
			userPieceView.addPiece(pieceInfo.getPosition());
		}
		userPieceModel.addListener(new IListener() {
			public void fireEvent() {
				userPieceView.movePieceTo(userPieceModel.getCurrentPosition());
			}
		});
	}
}
