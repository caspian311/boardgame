package net.todd.games.boardgame;

public class PieceGenerator implements IPieceGenerator {
	private final IBranchGroup rootBranchGroup;

	public PieceGenerator(IBranchGroup bg) {
		this.rootBranchGroup = bg;
	}

	public void createPieces(IPicker picker,
			IUserPiecesFactory userPiecesFactory) {
		IBranchGroup userPiecesBranchGroup = userPiecesFactory
				.constructUserPieces(picker);
		rootBranchGroup.addChild(userPiecesBranchGroup);
	}
}
