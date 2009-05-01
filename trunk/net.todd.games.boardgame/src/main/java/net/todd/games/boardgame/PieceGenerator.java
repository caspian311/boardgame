package net.todd.games.boardgame;

public class PieceGenerator implements IPieceGenerator {
	private final IBranchGroup bg;

	public PieceGenerator(IBranchGroup bg) {
		this.bg = bg;
	}

	public void createPieces(IPickerFactory pickerFactory, IUserPiecesFactory userPiecesFactory) {
		IBranchGroup branchGroup = userPiecesFactory.constructUserPieces(pickerFactory);
		bg.addChild(branchGroup);
	}
}
