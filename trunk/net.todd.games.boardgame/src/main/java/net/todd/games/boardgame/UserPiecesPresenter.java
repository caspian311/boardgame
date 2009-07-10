package net.todd.games.boardgame;

import java.util.List;

import net.todd.common.uitools.IListener;

public class UserPiecesPresenter {
	public UserPiecesPresenter(final IUserPiecesView userPieceView,
			final IUserPiecesModel userPieceModel) {
		addAllPiecesToView(userPieceView, userPieceModel.getAllTeamOnePieces());
		addAllPiecesToView(userPieceView, userPieceModel.getAllTeamTwoPieces());

		userPieceView.addPieceSelectedListener(new IListener() {
			public void fireEvent() {
				userPieceModel.setSelectedPiece(userPieceView.getSelectedPiece());
			}
		});
	}

	private void addAllPiecesToView(final IUserPiecesView userPieceView,
			List<PieceInfo> allTeamTwoPieces) {
		for (PieceInfo pieceInfo : allTeamTwoPieces) {
			userPieceView.addPiece(pieceInfo);
		}
	}
}