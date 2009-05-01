package net.todd.games.boardgame;

public interface IGameGridFactory {

	public abstract IBranchGroup constructGameGrid(IPickerFactory pickerFactory);

}