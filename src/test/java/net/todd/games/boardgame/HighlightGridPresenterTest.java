package net.todd.games.boardgame;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import net.todd.common.uitools.IListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class HighlightGridPresenterTest {
	private IHighlightedGridView view;
	private IGameGridModel model;
	private IListener userPieceSelectedListener;
	private IListener tileSelectedListener;

	@Before
	public void setUp() {
		view = mock(IHighlightedGridView.class);
		model = mock(IGameGridModel.class);
		
		new HighlightGridPresenter(view, model);
		
		ArgumentCaptor<IListener> userPieceSelectedListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(model).addUserPieceSelectedListener(userPieceSelectedListenerCaptor.capture());
		userPieceSelectedListener = userPieceSelectedListenerCaptor.getValue();
		
		ArgumentCaptor<IListener> tileSelectedListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(model).addTileSelectedListener(tileSelectedListenerCaptor.capture());
		tileSelectedListener = tileSelectedListenerCaptor.getValue();
	}

	@Test
	public void testPresenterListensForTiesToHighlightFromModelAndNotifiesView() {
		TileData[] tileData = new TileData[]{};
		doReturn(tileData).when(model).getTilesToHighlight();
		
		userPieceSelectedListener.fireEvent();

		verify(view).highlightTiles(tileData);
	}

	@Test
	public void testWhenTileIsSelectedViewClearsOutHighlightedTiles() {
		tileSelectedListener.fireEvent();
		
		verify(view).clearHighlightedTiles();
	}
}
