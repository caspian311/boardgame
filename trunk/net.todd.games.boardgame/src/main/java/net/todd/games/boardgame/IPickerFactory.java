package net.todd.games.boardgame;

public interface IPickerFactory {

	public abstract IPicker createPicker(IBranchGroup branchGroup);

}