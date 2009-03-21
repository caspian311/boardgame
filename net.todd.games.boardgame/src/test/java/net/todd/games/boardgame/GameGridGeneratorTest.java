package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;

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

	@Before
	public void setUp() {
		branchGroup = new BranchGroupStub();
	}

	@Test
	public void testCreateBackground() {
		GameGridGenerator gridGenerator = new GameGridGenerator(branchGroup);
		Bounds bounds = new BoundingSphere();

		assertEquals(0, branchGroup.addedNode.size());

		gridGenerator.createBackground(bounds);

		assertEquals(1, branchGroup.addedNode.size());
		Background background = (Background) branchGroup.addedNode.get(0);
		assertEquals(bounds, background.getApplicationBounds());
		Color3f color = new Color3f();
		background.getColor(color);
		assertEquals(GameColors.BACKGROUND_COLOR, color);
	}

	@Test
	public void testLightScene() {
		GameGridGenerator gridGenerator = new GameGridGenerator(branchGroup);
		Bounds bounds = new BoundingSphere();

		assertEquals(0, branchGroup.addedNode.size());

		gridGenerator.lightScene(bounds);

		assertEquals(2, branchGroup.addedNode.size());

		AmbientLight light1 = (AmbientLight) branchGroup.addedNode.get(0);
		assertEquals(bounds, light1.getInfluencingBounds());
		Color3f color1 = new Color3f();
		light1.getColor(color1);
		assertEquals(color1, GameColors.LIGHT_COLOR);

		DirectionalLight light2 = (DirectionalLight) branchGroup.addedNode.get(1);
		assertEquals(bounds, light2.getInfluencingBounds());
		Color3f color2 = new Color3f();
		light2.getColor(color2);
		assertEquals(color2, GameColors.LIGHT_COLOR);
		Vector3f direction = new Vector3f();
		light2.getDirection(direction);
		assertEquals(new Vector3f(0, -1, 0), direction);
	}

	@Test
	public void testCreateGameGridAddsOnlyOneChild() {
		GameGridGenerator gridGenerator = new GameGridGenerator(branchGroup);

		assertEquals(0, branchGroup.addedNode.size());

		gridGenerator.createGameGrid(new IPicker() {
			public void addListener(IListener listener) {
			}

			public Node getSelectedNode() {
				throw new UnsupportedOperationException();
			}
		});

		assertEquals(1, branchGroup.addedNode.size());
	}

	private static class BranchGroupStub implements IBranchGroup {
		List<Node> addedNode = new ArrayList<Node>();

		public void addChild(Node node) {
			this.addedNode.add(node);
		}

		public void compile() {
			throw new UnsupportedOperationException();
		}

		public BranchGroup getInternal() {
			throw new UnsupportedOperationException();
		}
	}
}
