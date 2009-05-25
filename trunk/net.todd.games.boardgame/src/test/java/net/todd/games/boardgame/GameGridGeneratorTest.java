package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Node;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;

import org.junit.Before;
import org.junit.Test;

public class GameGridGeneratorTest {
	private BranchGroupStub branchGroup;
	private GameGridFactoryStub gameGridFactory;

	@Before
	public void setUp() {
		branchGroup = new BranchGroupStub();
		gameGridFactory = new GameGridFactoryStub();
	}

	@Test
	public void testCreateBackground() {
		GameGridGenerator gridGenerator = new GameGridGenerator(branchGroup);
		Bounds bounds = new BoundingSphere();

		assertEquals(0, branchGroup.addedNodes.size());

		gridGenerator.createBackground(bounds);

		assertEquals(1, branchGroup.addedNodes.size());
		Background background = (Background) branchGroup.addedNodes.get(0);
		assertEquals(bounds, background.getApplicationBounds());
		Color3f color = new Color3f();
		background.getColor(color);
		assertEquals(GameColors.BACKGROUND_COLOR, color);
	}

	@Test
	public void testLightScene() {
		GameGridGenerator gridGenerator = new GameGridGenerator(branchGroup);
		Bounds bounds = new BoundingSphere();

		assertEquals(0, branchGroup.addedNodes.size());

		gridGenerator.lightScene(bounds);

		assertEquals(2, branchGroup.addedNodes.size());

		AmbientLight light1 = (AmbientLight) branchGroup.addedNodes.get(0);
		assertEquals(bounds, light1.getInfluencingBounds());
		Color3f color1 = new Color3f();
		light1.getColor(color1);
		assertEquals(color1, GameColors.LIGHT_COLOR);

		DirectionalLight light2 = (DirectionalLight) branchGroup.addedNodes
				.get(1);
		assertEquals(bounds, light2.getInfluencingBounds());
		Color3f color2 = new Color3f();
		light2.getColor(color2);
		assertEquals(color2, GameColors.LIGHT_COLOR);
		Vector3f direction = new Vector3f();
		light2.getDirection(direction);
		assertEquals(new Vector3f(0, -1, 0), direction);
	}

	@Test
	public void testCreateGameGridAddsGameGridToBranchGroup() {
		GameGridGenerator gridGenerator = new GameGridGenerator(branchGroup);

		assertEquals(0, branchGroup.addedGroup.size());
		assertFalse(gameGridFactory.gameGridconstructed);

		PickerStub picker = new PickerStub();
		gridGenerator.createGameGrid(picker, gameGridFactory);

		assertTrue(gameGridFactory.gameGridconstructed);
		assertTrue(branchGroup.addedGroup.size() > 0);
	}

	@Test
	public void testCreateGameGridAlsoCreatesHighlightedGrid() {
		GameGridGenerator gridGenerator = new GameGridGenerator(branchGroup);

		PickerStub picker = new PickerStub();

		assertFalse(gameGridFactory.highlightedGridContructed);
		gridGenerator.createGameGrid(picker, gameGridFactory);
		assertTrue(gameGridFactory.highlightedGridContructed);

		assertEquals(2, branchGroup.addedGroup.size());
	}

	private static class BranchGroupStub implements IBranchGroup {
		List<Node> addedNodes = new ArrayList<Node>();
		List<IBranchGroup> addedGroup = new ArrayList<IBranchGroup>();

		public void addChild(Node node) {
			this.addedNodes.add(node);
		}

		public void compile() {
			throw new UnsupportedOperationException();
		}

		public BranchGroup getInternal() {
			throw new UnsupportedOperationException();
		}

		public void addChild(IBranchGroup child) {
			this.addedGroup.add(child);
		}

		public void removeAllChildren() {
			throw new UnsupportedOperationException();
		}
	}

	private static class PickerStub implements IPicker {
		public void addListener(IListener listener) {
			throw new UnsupportedOperationException();
		}

		public Node getSelectedNode() {
			throw new UnsupportedOperationException();
		}

		public void removeListener(IListener listener) {
			throw new UnsupportedOperationException();
		}
	}

	private static class GameGridFactoryStub implements IGameGridFactory {
		boolean gameGridconstructed;
		private boolean highlightedGridContructed;

		public IBranchGroup constructGameGrid(IPicker picker) {
			gameGridconstructed = true;
			return new IBranchGroup() {
				public void addChild(Node node) {
					throw new UnsupportedOperationException();
				}

				public void compile() {
					throw new UnsupportedOperationException();
				}

				public BranchGroup getInternal() {
					throw new UnsupportedOperationException();
				}

				public void addChild(IBranchGroup child) {
					throw new UnsupportedOperationException();
				}

				public void removeAllChildren() {
					throw new UnsupportedOperationException();
				}
			};
		}

		public IBranchGroup constructHighlightedGrid() {
			highlightedGridContructed = true;
			return new IBranchGroup() {
				public void addChild(Node node) {
					throw new UnsupportedOperationException();
				}

				public void compile() {
					throw new UnsupportedOperationException();
				}

				public BranchGroup getInternal() {
					throw new UnsupportedOperationException();
				}

				public void addChild(IBranchGroup child) {
					throw new UnsupportedOperationException();
				}

				public void removeAllChildren() {
					throw new UnsupportedOperationException();
				}
			};
		}
	}
}
