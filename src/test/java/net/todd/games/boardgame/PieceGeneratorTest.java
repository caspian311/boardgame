package net.todd.games.boardgame;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

public class PieceGeneratorTest {
	private IBranchGroup branchGroup;
	private PieceGenerator pieceGenerator;
	private IUserPiecesFactory userPiecesFactory;
	private IPicker picker;

	@Before
	public void setUp() {
		branchGroup = mock(IBranchGroup.class);
		
		pieceGenerator = new PieceGenerator(branchGroup);
		
		userPiecesFactory = mock(IUserPiecesFactory.class);
		picker = mock(IPicker.class);
	}
	
	@Test
	public void testPieceGeneratorPassesPickerToGivenFactory() {
		pieceGenerator.createPieces(picker, userPiecesFactory);

		verify(userPiecesFactory).constructUserPieces(picker);
	}

	@Test
	public void testPieceGeneratorReturnsFactoryCreatedBranchGroup() {
		IBranchGroup userPiecesBranchGroup = mock(IBranchGroup.class);
		doReturn(userPiecesBranchGroup).when(userPiecesFactory).constructUserPieces(any(IPicker.class));
		
		pieceGenerator.createPieces(picker, userPiecesFactory);

		verify(branchGroup).addChild(userPiecesBranchGroup);
	}
}
