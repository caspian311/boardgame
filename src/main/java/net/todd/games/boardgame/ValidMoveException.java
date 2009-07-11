package net.todd.games.boardgame;

public class ValidMoveException extends Exception {
	private static final long serialVersionUID = 4255125653519000978L;

	public ValidMoveException(String message) {
		super(message);
	}
}
