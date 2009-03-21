package net.todd.games.boardgame;

public interface IPieceGenerator {
	void createPieces(IPicker picker, IUserPiecesFactory userPiecesFactory);
}
