package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import net.todd.common.uitools.IListener;

import org.junit.Before;
import org.junit.Test;

public class UserPiecesViewTest {
	private final Bounds bounds = new BoundingSphere(new Point3d(0, 0, 0), 100);
	private PickerFactoryStub pickerFactory;
	private PickerStub teamOnePicker;
	private PickerStub teamTwoPicker;
	private BranchGroupStub mainBranchGroup;
	private BranchGroupFactoryStub branchGroupFactory;
	private BranchGroupStub teamOneBranchGroup;
	private BranchGroupStub teamTwoBranchGroup;

	@Before
	public void setUp() {
		teamOnePicker = new PickerStub();
		teamTwoPicker = new PickerStub();
		pickerFactory = new PickerFactoryStub();
		pickerFactory.teamOnePicker = teamOnePicker;
		pickerFactory.teamTwoPicker = teamTwoPicker;

		mainBranchGroup = new BranchGroupStub();
		teamOneBranchGroup = new BranchGroupStub();
		teamTwoBranchGroup = new BranchGroupStub();
		branchGroupFactory = new BranchGroupFactoryStub();
		branchGroupFactory.mainBranchGroup = mainBranchGroup;
		branchGroupFactory.teamOneBranchGroup = teamOneBranchGroup;
		branchGroupFactory.teamTwoBranchGroup = teamTwoBranchGroup;
	}

	@Test
	public void testEvenIfYouDontAddPiecesThereAreTwoBranchGroupsForEeachTeam() {
		UserPiecesView userPiecesView = new UserPiecesView(bounds, pickerFactory,
				branchGroupFactory);

		assertSame(mainBranchGroup, userPiecesView.getBranchGroup());
		assertEquals(0, mainBranchGroup.nodeChildren.size());
		assertEquals(2, mainBranchGroup.bgChildren.size());
		assertSame(teamOneBranchGroup, mainBranchGroup.bgChildren.get(0));
		assertSame(teamTwoBranchGroup, mainBranchGroup.bgChildren.get(1));
	}

	@Test
	public void testAddingPiecesOfOneTeamAddsItToOnePartOfTheBranchGroup() {
		UserPiecesView userPiecesView = new UserPiecesView(bounds, pickerFactory,
				branchGroupFactory);

		PieceInfo pieceInfo1 = new PieceInfo();
		pieceInfo1.setPosition(new Vector3f(1f, 2f, 3f));
		pieceInfo1.setColor(new Color3f(1f, 2f, 3f));
		pieceInfo1.setTeam(Team.ONE);

		PieceInfo pieceInfo2 = new PieceInfo();
		pieceInfo2.setPosition(new Vector3f(1f, 2f, 3f));
		pieceInfo2.setColor(new Color3f(1f, 2f, 3f));
		pieceInfo2.setTeam(Team.ONE);

		userPiecesView.addPiece(pieceInfo1);
		userPiecesView.addPiece(pieceInfo2);

		assertEquals(2, teamOneBranchGroup.nodeChildren.size());
	}

	@Test
	public void testAddingPiecesToBothTeamsAddsItToCorrectTeamBranchGroup() {
		UserPiecesView userPiecesView = new UserPiecesView(bounds, pickerFactory,
				branchGroupFactory);

		PieceInfo pieceInfo1 = new PieceInfo();
		pieceInfo1.setPosition(new Vector3f(1f, 2f, 3f));
		pieceInfo1.setColor(new Color3f(1f, 2f, 3f));
		pieceInfo1.setTeam(Team.ONE);

		PieceInfo pieceInfo2 = new PieceInfo();
		pieceInfo2.setPosition(new Vector3f(1f, 2f, 3f));
		pieceInfo2.setColor(new Color3f(1f, 2f, 3f));
		pieceInfo2.setTeam(Team.TWO);

		PieceInfo pieceInfo3 = new PieceInfo();
		pieceInfo3.setPosition(new Vector3f(1f, 2f, 3f));
		pieceInfo3.setColor(new Color3f(1f, 2f, 3f));
		pieceInfo3.setTeam(Team.TWO);

		userPiecesView.addPiece(pieceInfo1);
		userPiecesView.addPiece(pieceInfo2);
		userPiecesView.addPiece(pieceInfo3);

		assertEquals(1, teamOneBranchGroup.nodeChildren.size());
		assertEquals(2, teamTwoBranchGroup.nodeChildren.size());
	}

	@Test
	public void testAPickerIsGivenToEachTeam() {
		new UserPiecesView(bounds, pickerFactory, branchGroupFactory);
		assertSame(teamOneBranchGroup, teamOnePicker.associatedBranchGroup);
		assertSame(teamTwoBranchGroup, teamTwoPicker.associatedBranchGroup);
	}

	@Test
	public void testTeamTwoCannotMoveUntilTeamOneMovesFirst() {
		UserPiecesView view = new UserPiecesView(bounds, pickerFactory, branchGroupFactory);

		PieceGroupStub pieceGroup1 = new PieceGroupStub();
		teamOnePicker.selectedNode = new SelectablePiece(pieceGroup1);

		PieceGroupStub pieceGroup2 = new PieceGroupStub();
		teamTwoPicker.selectedNode = new SelectablePiece(pieceGroup2);

		teamOnePicker.listener.fireEvent();
		teamTwoPicker.listener.fireEvent();

		assertEquals(0, pieceGroup1.movePieceToCallCount);
		assertEquals(0, pieceGroup2.movePieceToCallCount);

		view.movePieceTo(new Vector3f());

		assertEquals(1, pieceGroup1.movePieceToCallCount);
		assertEquals(0, pieceGroup2.movePieceToCallCount);
	}

	@Test
	public void testTeamTwoCanMoveAfterTeamOnesMoves() {
		UserPiecesView view = new UserPiecesView(bounds, pickerFactory, branchGroupFactory);

		PieceGroupStub pieceGroup1 = new PieceGroupStub();
		teamOnePicker.selectedNode = new SelectablePiece(pieceGroup1);

		PieceGroupStub pieceGroup2 = new PieceGroupStub();
		teamTwoPicker.selectedNode = new SelectablePiece(pieceGroup2);

		teamOnePicker.listener.fireEvent();

		assertEquals(0, pieceGroup1.movePieceToCallCount);
		assertEquals(0, pieceGroup2.movePieceToCallCount);

		view.movePieceTo(new Vector3f());

		assertEquals(1, pieceGroup1.movePieceToCallCount);
		assertEquals(0, pieceGroup2.movePieceToCallCount);

		teamTwoPicker.listener.fireEvent();
		view.movePieceTo(new Vector3f());

		assertEquals(1, pieceGroup1.movePieceToCallCount);
		assertEquals(1, pieceGroup2.movePieceToCallCount);
	}

	@Test
	public void testTeamOneAndTwoAlternateMoves() {
		UserPiecesView view = new UserPiecesView(bounds, pickerFactory, branchGroupFactory);

		PieceGroupStub pieceGroup1 = new PieceGroupStub();
		teamOnePicker.selectedNode = new SelectablePiece(pieceGroup1);

		PieceGroupStub pieceGroup2 = new PieceGroupStub();
		teamTwoPicker.selectedNode = new SelectablePiece(pieceGroup2);

		teamOnePicker.listener.fireEvent();
		view.movePieceTo(new Vector3f());

		assertEquals(1, pieceGroup1.movePieceToCallCount);
		assertEquals(0, pieceGroup2.movePieceToCallCount);

		teamTwoPicker.listener.fireEvent();
		view.movePieceTo(new Vector3f());

		assertEquals(1, pieceGroup1.movePieceToCallCount);
		assertEquals(1, pieceGroup2.movePieceToCallCount);

		teamOnePicker.listener.fireEvent();
		view.movePieceTo(new Vector3f());

		assertEquals(2, pieceGroup1.movePieceToCallCount);
		assertEquals(1, pieceGroup2.movePieceToCallCount);

		teamTwoPicker.listener.fireEvent();
		view.movePieceTo(new Vector3f());

		assertEquals(2, pieceGroup1.movePieceToCallCount);
		assertEquals(2, pieceGroup2.movePieceToCallCount);
	}

	private static class PickerFactoryStub implements IPickerFactory {
		PickerStub teamTwoPicker;
		PickerStub teamOnePicker;
		private int callCount;

		public IPicker createPicker(IBranchGroup branchGroup) {
			callCount++;
			PickerStub picker = null;
			if (callCount == 1) {
				picker = teamOnePicker;
			} else if (callCount == 2) {
				picker = teamTwoPicker;
			}
			picker.associatedBranchGroup = branchGroup;
			return picker;
		}
	}

	private static class PickerStub implements IPicker {
		IListener listener;
		Node selectedNode;
		IBranchGroup associatedBranchGroup;

		public void addListener(IListener listener) {
			this.listener = listener;
		}

		public Node getSelectedNode() {
			return selectedNode;
		}

		public void removeListener(IListener listener) {
			throw new UnsupportedOperationException();
		}
	}

	private static class BranchGroupFactoryStub implements IBranchGroupFactory {
		IBranchGroup mainBranchGroup;
		IBranchGroup teamOneBranchGroup;
		IBranchGroup teamTwoBranchGroup;
		private int callCount;

		public IBranchGroup createBranchGroup() {
			IBranchGroup branchGroup = null;
			callCount++;
			if (callCount == 1) {
				branchGroup = mainBranchGroup;
			} else if (callCount == 2) {
				branchGroup = teamOneBranchGroup;
			} else if (callCount == 3) {
				branchGroup = teamTwoBranchGroup;
			}
			return branchGroup;
		}
	}

	private static class BranchGroupStub implements IBranchGroup {
		List<Node> nodeChildren = new ArrayList<Node>();
		List<IBranchGroup> bgChildren = new ArrayList<IBranchGroup>();

		public void addChild(Node child) {
			nodeChildren.add(child);
		}

		public void addChild(IBranchGroup child) {
			bgChildren.add(child);
		}

		public void compile() {
			throw new UnsupportedOperationException();
		}

		public BranchGroup getInternal() {
			throw new UnsupportedOperationException();
		}
	}

	private static class PieceGroupStub implements IPieceGroup {
		int movePieceToCallCount;

		public void movePieceTo(Vector3f position) {
			++movePieceToCallCount;
		}

		public Color3f getColor() {
			return new Color3f();
		}
	}
}
