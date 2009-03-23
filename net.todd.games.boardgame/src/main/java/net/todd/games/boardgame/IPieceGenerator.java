package net.todd.games.boardgame;

public interface IPieceGenerator {
	void createPieces(IPickerFactory picker, IUserPiecesFactory userPiecesFactory);
}
