package net.todd.games.boardgame;

public class HighlightedGridView implements IHighlightedGridView {
	private final IBranchGroup branchGroup;
	private final IBranchGroupFactory branchGroupFactory;

	public HighlightedGridView(IBranchGroupFactory branchGroupFactory) {
		this.branchGroupFactory = branchGroupFactory;
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
			highlightedStuff.addChild(new Tile(tileData));
		}

		branchGroup.addChild(highlightedStuff);
	}
}
