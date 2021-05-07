package org.psnbtech;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClockTest {
	private final float CYCLESPERSECONDS = 50.0f;
	private Clock clock;

	@BeforeEach
	void setUp() throws Exception {
		 Clock.USE_FIXED_TIME_FOR_TESTING = true;
		 clock = new Clock(CYCLESPERSECONDS);
		 
	}

	@AfterEach
	void tearDown() throws Exception {
		clock = null;
	}

	@Test
	void testClock() {
		final String assert1Label = "Tests the inital value of millsPerCycle";
		final String assert2Label = "Tests the initial value of elapsedCycles";
		final String assert3Label = "Tests the initial value of excessCycles";
		final String assert4Label = "Tests the initial value of lastUpdate";
		final String assert5Label = "Tests the initial value of isPaused";
		
		final float EXPECTED_VALUE_OF_MILLSPERCYCLE = 20;
		final float EXPECTED_VALUE_OF_ELAPSEDCYCLES = 0.0F;
		final float EXPECTED_VALUE_OF_EXCESSCYCLES = 0.0F;
		final long EXPECTED_VALUE_OF_LASTUPDATE = Clock.FIXED_TIME_FOR_TESTING;  // Using a fixed time to simulate current time
		final boolean EXPECTED_VALUE_OF_ISPAUSED = false;
		
		assertEquals(clock.getMillsPerCycle(), EXPECTED_VALUE_OF_MILLSPERCYCLE, assert1Label);
		assertEquals(clock.getElapsedCycles(), EXPECTED_VALUE_OF_ELAPSEDCYCLES, assert2Label);
		assertEquals(clock.getExcessCycles(), EXPECTED_VALUE_OF_EXCESSCYCLES, assert3Label);
		assertEquals(clock.getLastUpdate(), EXPECTED_VALUE_OF_LASTUPDATE, assert4Label);
		assertEquals(clock.isPaused(), EXPECTED_VALUE_OF_ISPAUSED, assert5Label);
	}
	
	@Test
	void testSetCyclesPersecond() {
		final String assert1Label = "Tests the setCyclePerSecond(float cyclesPerSecond)";
		
		final float INPUTED_CYCLES_PER_SECOND = 50.5F;
		final float EXPECTED_VALUE_OF_MILLSPERCYCLE = 19.80198F;
		
		clock.setCyclesPerSecond(INPUTED_CYCLES_PER_SECOND);			// Method being tested
		
		assertEquals(clock.getMillsPerCycle(), EXPECTED_VALUE_OF_MILLSPERCYCLE, assert1Label);
	}

	@Test
	void testReset() {
		final String assert1Label = "Tests the value of elapsedCycles upon reset()";
		final String assert2Label = "Tests the value of excessCycles upon reset()";
		final String assert3Label = "Tests the value of lastUpdate upon reset()";
		final String assert4Label = "Tests the value of isPaused upon reset()";
		
		final float EXPECTED_VALUE_OF_ELAPSEDCYCLES = 0.0F;
		final float EXPECTED_VALUE_OF_EXCESSCYCLES = 0.0F;
		final long EXPECTED_VALUE_OF_LASTUPDATE = Clock.FIXED_TIME_FOR_TESTING;
		final boolean EXPECTED_VALUE_OF_ISPAUSED = false;
		
		clock.reset();        // Method being tested		
		
		assertEquals(clock.getElapsedCycles(), EXPECTED_VALUE_OF_ELAPSEDCYCLES, assert1Label);
		assertEquals(clock.getExcessCycles(), EXPECTED_VALUE_OF_EXCESSCYCLES, assert2Label);
		assertEquals(clock.getLastUpdate(), EXPECTED_VALUE_OF_LASTUPDATE, assert3Label);
		assertEquals(clock.isPaused(), EXPECTED_VALUE_OF_ISPAUSED, assert4Label);
	}
	
	@Test
	void testSetPausedAndIsPaused() {
		final String assert1Label = "Tests setPaused(true)";
		final String assert2Label = "Tests the setPaused(false)";
		clock.setPaused(true);
		assertEquals(clock.isPaused(), true, assert1Label);
		clock.setPaused(false);
		assertEquals(clock.isPaused(),false, assert2Label);
	}
	
	@Test
	void testElapsedCycles() {
		final int ELAPSED_CYCLES_INPUT = 1;
		clock.setElapsedCycles(ELAPSED_CYCLES_INPUT);
		
		assertEquals(clock.hasElapsedCycle(), true);		// When elapsedCycles > 0 return true and decrement elapsedCycles
		assertEquals(clock.getElapsedCycles(), 0);			// Checks if elapsedCycles was actually decremented
		assertEquals(clock.hasElapsedCycle(), false);		// elapsedCycles should be 0 by now and clock.hasElapsedCycle() should return false now	
	}
	
	@Test
	void testPeekElapsedCycle() {
		final int ELAPSED_CYCLES_INPUT_THAT_IS_GREATER_THAN_ZERO = 1;
		final int ELAPSED_CYCLES_INPUT_THAT_IS_NOT_GREATER_THAN_ZERO = 0;
		
		clock.setElapsedCycles(ELAPSED_CYCLES_INPUT_THAT_IS_GREATER_THAN_ZERO);
		assertEquals(clock.peekElapsedCycle(), true);		// When elapsedCycles > 0 return true
		
		clock.setElapsedCycles(ELAPSED_CYCLES_INPUT_THAT_IS_NOT_GREATER_THAN_ZERO);
		assertEquals(clock.peekElapsedCycle(), false);		// When elapsedCycles is not greater than 0 return false
	}
	
	@Test
	void testUpdateWhenGameIsPaused() {
		final int ELAPSEDCYCLES_INPUT = 1;
		final float EXCESS_CYCLE_INPUT = 2.0F;
		final float MILLS_PER_CYCLE_INPUT = 25.5F;
		clock.setElapsedCycles(ELAPSEDCYCLES_INPUT);
		clock.setExcessCycles(EXCESS_CYCLE_INPUT);
		clock.setMillsPerCycle(MILLS_PER_CYCLE_INPUT);
		clock.setLastUpdate(Clock.FIXED_TIME_FOR_TESTING - 2);
		
		clock.setPaused(true);    											// Tests for the case where the game is paused
		clock.update();    		  											// Method being tested
		assertEquals(clock.getElapsedCycles(), ELAPSEDCYCLES_INPUT);		// Shouldn't change
		assertEquals(clock.getExcessCycles(), EXCESS_CYCLE_INPUT);			// Shouldn't change
		assertEquals(clock.getLastUpdate(), Clock.FIXED_TIME_FOR_TESTING);	// Should change the current time
	}
	
	@Test
	void testUpdateWhenGameIsNotPaused() {
		final int ELAPSEDCYCLES_INPUT = 1;
		final float EXCESS_CYCLE_INPUT = 2.0F;
		final float MILLS_PER_CYCLE_INPUT = 25.5F;
		clock.setElapsedCycles(ELAPSEDCYCLES_INPUT);
		clock.setExcessCycles(EXCESS_CYCLE_INPUT);
		clock.setMillsPerCycle(MILLS_PER_CYCLE_INPUT);
		clock.setLastUpdate(Clock.FIXED_TIME_FOR_TESTING - 2);
		
		clock.setPaused(false);													// Tests update for the case where the game is not paused
		clock.update();    		  												// Method being tested
		assertEquals(clock.getElapsedCycles(), ELAPSEDCYCLES_INPUT);			// Should not change this time
		assertEquals(clock.getExcessCycles(), 4);								// Should change to 4
		assertEquals(clock.getLastUpdate(), Clock.FIXED_TIME_FOR_TESTING);		// Should change the current time
	}
	
	@Test
	void testGetCurrentTime() {
		Clock.USE_FIXED_TIME_FOR_TESTING = false;
		assertEquals(true,Clock.getCurrentTime() - System.nanoTime() < 2L);
	}
}
