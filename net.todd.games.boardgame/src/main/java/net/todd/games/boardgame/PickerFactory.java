package net.todd.games.boardgame;

public class PickerFactory implements IPickerFactory {
	private final IUniverse universe;

	public PickerFactory(IUniverse universe) {
		this.universe = universe;
	}

	public IPicker createPicker(IBranchGroup branchGroup) {
		return new Picker(universe, branchGroup);
	}
}
