package net.todd.games.boardgame;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import net.todd.common.uitools.IListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class GameGridPresenterTest {
	private IGameGridView view;
	private IGameGridModel model;
	private List<TileData> tileDataList;
	private IListener tileSelectedListener;

	@Before
	public void setUp() {
		view = mock(IGameGridView.class);
		model = mock(IGameGridModel.class);
		
		tileDataList = Arrays.asList();
		doReturn(tileDataList).when(model).getTileData();
		
		new GameGridPresenter(view, model);
		
		ArgumentCaptor<IListener> tileSelectedListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(view).addTileSelectedListener(tileSelectedListenerCaptor.capture());
		tileSelectedListener = tileSelectedListenerCaptor.getValue();
	}

	@Test
	public void testGridIsCreatedWithDimensionFromModel() {
		verify(view).constructGrid(tileDataList);
	}

	@Test
	public void testPresenterListensForSelectedTilesFromViewAndNotifiesModel() {
		TileData selectedTileData = mock(TileData.class);
		doReturn(selectedTileData).when(view).getSelectedTile();
		
		tileSelectedListener.fireEvent();

		verify(model).setSelectedTile(selectedTileData);
	}
}
