package net.todd.games.boardgame;

import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;
import net.todd.common.uitools.ListenerManager;

public class GameGridModel implements IGameGridModel {
	private final IGameGridData gameGridData;
	private final ListenerManager tileSelectedListenerManager = new ListenerManager();
	private Vector3f selectedPosition;
	private final ListenerManager userPieceSelectedListenerManager = new ListenerManager();
	private final ITileHighlighterCalculator tileHighlighterCalculator;
	private PieceInfo selectedUserPiece;

	public GameGridModel(IGameGridData gameGridData,
			ITileHighlighterCalculator tileHighlighterCalculator) {
		this.gameGridData = gameGridData;
		this.tileHighlighterCalculator = tileHighlighterCalculator;
	}

	public TileData[][] getTileData() {
		return gameGridData.getTileData();
	}

	public void addTileSelectedListener(IListener listener) {
		tileSelectedListenerManager.addListener(listener);
	}

	public void setSelectedTile(TileData tileData) {
		selectedPosition = new Vector3f(tileData.getPosition());
		tileSelectedListenerManager.notifyListeners();
	}

	public Vector3f getSelectedTileLocation() {
		return selectedPosition;
	}

	public void setSelectedUserPiece(PieceInfo pieceInfo) {
		this.selectedUserPiece = pieceInfo;
		userPieceSelectedListenerManager.notifyListeners();
	}

	public void addUserPieceSelectedListener(IListener listener) {
		userPieceSelectedListenerManager.addListener(listener);
	}

	public TileData[] getTilesToHighlight() {
		return tileHighlighterCalculator.calculateTilesToHighlight(selectedUserPiece);
	}
}
