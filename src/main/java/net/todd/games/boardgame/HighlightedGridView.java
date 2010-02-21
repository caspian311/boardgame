package net.todd.games.boardgame;

public class HighlightedGridView implements IHighlightedGridView {
	private final IBranchGroup branchGroup;
	private final IBranchGroupFactory branchGroupFactory;
	private final ITileFactory tileFactory;

	public HighlightedGridView(IBranchGroupFactory branchGroupFactory, ITileFactory tileFactory) {
		this.branchGroupFactory = branchGroupFactory;
		this.tileFactory = tileFactory;
		branchGroup = branchGroupFactory.createBranchGroup();
	}

	public IBranchGroup getBranchGroup() {
		return branchGroup;
	}

	public void highlightTiles(TileData[] tiles) {
		branchGroup.removeAllChildren();
		IBranchGroup highlightedStuff = branchGroupFactory.createBranchGroup();

		for (TileData tileData : tiles) {
			tileData.getPosition()[1] += 0.5f;
			tileData.setColor(new float[] { 0f, 0.2f, 0.8f });
			tileData.setTransparent(true);
			highlightedStuff.addChild(tileFactory.createTile(tileData));
		}

		branchGroup.addChild(highlightedStuff);
	}

	public void clearHighlightedTiles() {
		branchGroup.removeAllChildren();
	}
}
