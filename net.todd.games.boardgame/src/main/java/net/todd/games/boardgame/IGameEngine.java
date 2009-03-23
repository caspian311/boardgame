package net.todd.games.boardgame;

public interface IGameEngine {
	void createScene(IPickerFactory picker);

	void createCamera(IUniverse su);
}