package org.psnbtech;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardPanelTest {
	private BoardPanel board;

	@BeforeEach
	void setUp() throws Exception {
		board =  new BoardPanel(new Tetris());
	}

	@AfterEach
	void tearDown() throws Exception {
		board = null;
	}

	@Test
	void testCheckLine_TR1() {
		
		// Preparing Input Data
		final int CURRENT_COL = 1;
		final int CURRENT_ROW = 0;
		final int CURRENT_ROTATION = 0;
		
		// Inserting Input Data
		board.addPiece(TileType.TypeI, CURRENT_COL, CURRENT_ROW, CURRENT_ROTATION);
		
		// Asserting outputs
		assertFalse(board.checkLine(1));    // No lines should be destroyed
	}

	@Test
	void testCheckLine_TR2() {
		
		// Preparing Input Data
		final int TILE1_CURRENT_COL = 0;
		final int TILE2_CURRENT_COL = 4;
		final int TILE3_CURRENT_COL = 8;
		final int CURRENT_ROW = 0;
		final int CURRENT_ROTATION = 0;
		
		// Inserting Input Data
		board.addPiece(TileType.TypeI, TILE1_CURRENT_COL, CURRENT_ROW, CURRENT_ROTATION);
		board.addPiece(TileType.TypeI, TILE2_CURRENT_COL, CURRENT_ROW, CURRENT_ROTATION);
		board.addPiece(TileType.TypeO, TILE3_CURRENT_COL, CURRENT_ROW, CURRENT_ROTATION);
		
		// Asserting outputs
		assertTrue(board.checkLine(1));    // Should destroy the far bottom row
		
	}
	
	
	@Test
	void testCheckLines_TR() {
		
		final int ROW_ZERO = 0;
		final int ROW_ONE = 1;
		final int CURRENT_ROTATION = 0;
		final int TILE1_CURRENT_COL = 0;
		final int TILE2_CURRENT_COL = 4;
		final int TILE3_CURRENT_COL = 0;
		final int TILE4_CURRENT_COL = 4;
		final int TILE5_CURRENT_COL = 8;
		final int TILE6_CURRENT_COL = 8;
		
		board.addPiece(TileType.TypeI, TILE1_CURRENT_COL, ROW_ZERO , CURRENT_ROTATION);
		board.addPiece(TileType.TypeI, TILE2_CURRENT_COL, ROW_ZERO , CURRENT_ROTATION);
		board.addPiece(TileType.TypeI, TILE3_CURRENT_COL, ROW_ONE, CURRENT_ROTATION);
		board.addPiece(TileType.TypeI, TILE4_CURRENT_COL, ROW_ONE, CURRENT_ROTATION);
		board.addPiece(TileType.TypeO, TILE5_CURRENT_COL, ROW_ZERO, CURRENT_ROTATION);
		board.addPiece(TileType.TypeO, TILE6_CURRENT_COL, ROW_ONE, CURRENT_ROTATION);
		
		assertEquals(board.checkLines(), 2);   // Two lines should be cleared
	}
	
}
