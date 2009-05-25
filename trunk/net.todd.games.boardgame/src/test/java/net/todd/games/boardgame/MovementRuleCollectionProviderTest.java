package net.todd.games.boardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class MovementRuleCollectionProviderTest {
	@Test
	public void testMoveCollectionIsValid() {
		assertNotNull(MovementRuleCollectionProvider.getRuleCollection());
		assertTrue(MovementRuleCollectionProvider.getRuleCollection() instanceof IMovementRuleCollection);
	}

	@Test
	public void testCollectionContainsTakeTurnsRule() {
		List<IRule> rules = MovementRuleCollectionProvider.getRuleCollection()
				.getRules();

		int count = 0;
		for (IRule rule : rules) {
			if (rule instanceof TakeTurnsRule) {
				count++;
			}
		}
		assertEquals(1, count);
	}

	@Test
	public void testCollectionContainsProximityRule() {
		List<IRule> rules = MovementRuleCollectionProvider.getRuleCollection()
				.getRules();

		int count = 0;
		for (IRule rule : rules) {
			if (rule instanceof ProximityRule) {
				count++;
			}
		}
		assertEquals(1, count);
	}

	@Test
	public void testCollectionContainsAvailabilityRule() {
		List<IRule> rules = MovementRuleCollectionProvider.getRuleCollection()
				.getRules();

		int count = 0;
		for (IRule rule : rules) {
			if (rule instanceof AvailabilityRule) {
				count++;
			}
		}
		assertEquals(1, count);
	}
}
