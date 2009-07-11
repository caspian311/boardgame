package net.todd.games.boardgame;

import java.util.List;

import net.todd.common.uitools.IListener;

public class UserPiecesPresenter {
	public UserPiecesPresenter(final IUserPiecesView userPieceView,
			final IUserPiecesModel userPieceModel) {
		addAllPiecesToView(userPieceView, userPieceModel.getAllPieces());

		userPieceView.addPieceSelectedListener(new IListener() {
			public void fireEvent() {
				userPieceModel.setSelectedPiece(userPieceView
						.getSelectedPiece());
			}
		});
	}

	private void addAllPiecesToView(final IUserPiecesView userPieceView,
			List<PieceInfo> pieces) {
		for (PieceInfo pieceInfo : pieces) {
			userPieceView.addPiece(pieceInfo);
		}
	}
}
