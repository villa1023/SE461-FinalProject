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
		final long EXPECTED_VALUE_OF_LASTUPDATE = System.nanoTime() / 1000000L;
		final boolean EXPECTED_VALUE_OF_ISPAUSED = false;
		
		assertEquals(clock.getMillsPerCycle(), EXPECTED_VALUE_OF_MILLSPERCYCLE, assert1Label);
		assertEquals(clock.getElapsedCycles(), EXPECTED_VALUE_OF_ELAPSEDCYCLES, assert2Label);
		assertEquals(clock.getExcessCycles(), EXPECTED_VALUE_OF_EXCESSCYCLES, assert3Label);
		assertEquals(true, (EXPECTED_VALUE_OF_LASTUPDATE - clock.getLastUpdate() < 5L), assert4Label);
		assertEquals(clock.getIsPaused(), EXPECTED_VALUE_OF_ISPAUSED, assert5Label);
	}
	
	@Test
	void testSetCyclesPersecond() {
		final String assert1Label = "Thes the setCyclePerSecond(float cyclesPerSecond)";
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
		final long EXPECTED_VALUE_OF_LASTUPDATE = System.nanoTime() / 1000000L;
		final boolean EXPECTED_VALUE_OF_ISPAUSED = false;
		
		clock.reset();        // Method being tested		
		
		assertEquals(clock.getElapsedCycles(), EXPECTED_VALUE_OF_ELAPSEDCYCLES, assert1Label);
		assertEquals(clock.getExcessCycles(), EXPECTED_VALUE_OF_EXCESSCYCLES, assert2Label);
		assertEquals(true, (EXPECTED_VALUE_OF_LASTUPDATE - clock.getLastUpdate() < 5L), assert3Label);
		assertEquals(clock.getIsPaused(), EXPECTED_VALUE_OF_ISPAUSED, assert4Label);
	}
}
