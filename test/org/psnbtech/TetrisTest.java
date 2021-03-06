package org.psnbtech;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TetrisTest {
	
	private Tetris tetris;
	private Clock clock;

	@BeforeEach
	void setUp() throws Exception {
		Tetris.TEST_MODE = true;				// Activates Test Mode avoid using random numbers, and running the game loop infinitely 
		final float gameSpeed = 1.5F; 
		tetris = new Tetris();
		tetris.setCurrentType(TileType.TypeI);	
		tetris.setNextType(TileType.TypeJ);
		tetris.setRandom(new Random()); 
		tetris.setLogicTimer(new Clock(gameSpeed));
		clock = new Clock(gameSpeed);
	}

	@AfterEach
	void tearDown() throws Exception {
		tetris = null;
	}
	
	@Test
	void testResetGame_NC(){
		tetris.resetGame();
		
		assertEquals(tetris.getLevel(), 1);
		assertEquals(tetris.getScore(), 0);
		assertEquals(tetris.getGameSpeed(), 1.0F);
		assertNotNull(tetris.getPieceType());
		assertNotNull(tetris.getLogicTimer());
	}

	@Test
	void testSpawnPiecePPC_TR1() {   
	
		tetris.spawnPiece();
		assertEquals(tetris.getPieceType(), TileType.TypeJ);
		assertEquals(tetris.getNextPieceType(), TileType.TypeZ);
		assertEquals(tetris.getPieceCol(), TileType.TypeJ.getSpawnColumn());
		assertEquals(tetris.getPieceRow(), TileType.TypeJ.getSpawnRow());
	
	}
	
	@Test
	void testSpawnPiecePPC_TR2() {
		Tetris.TEST_MODE = false;	
		final int COL_NUMBER_THAT_IS_OUT_OF_BOUND = BoardPanel.COL_COUNT + 1;
		TileType OUT_OF_BOUND_TILE = TileType.TypeJ;
		OUT_OF_BOUND_TILE.spawnCol = COL_NUMBER_THAT_IS_OUT_OF_BOUND;
		tetris.setNextType(OUT_OF_BOUND_TILE);
		
		tetris.spawnPiece();
		assertTrue(tetris.isGameOver());
		assertTrue(tetris.getLogicTimer().isPaused());
	}
	
	@Test
	void testRotatePiece_TR1() {
		final int NEW_ROTATION = 0;
		final int PREVIOUS_ROW = tetris.getPieceRow();
		final int PREVIOUS_COL = tetris.getPieceCol();
		tetris.rotatePiece(NEW_ROTATION);
		
		assertEquals(tetris.getPieceRotation(), NEW_ROTATION);
		assertEquals(tetris.getPieceRow(), PREVIOUS_ROW);
		assertEquals(tetris.getPieceCol(), PREVIOUS_COL);
	}
	
	@Test
	void testRotatePiece_TR2() {
		final int NEW_ROTATION = 0;
		final int CURRENT_COL = TileType.TypeI.dimension - TileType.TypeI.dimension - 1;
		final int CURRENT_ROW = -TileType.TypeI.dimension;
		tetris.setCurrentCol(CURRENT_COL);
		tetris.setCurrentRow(CURRENT_ROW);
		tetris.rotatePiece(NEW_ROTATION);
		
		assertEquals(tetris.getPieceRotation(), NEW_ROTATION);
	}
	
	@Test
	void testRotatePiece_TR3(){
		final int NEW_ROTATION = 0;
		final int CURRENT_COL = TileType.TypeI.dimension + BoardPanel.COL_COUNT;
		final int CURRENT_ROW =  TileType.TypeI.dimension + BoardPanel.ROW_COUNT;
		tetris.setCurrentCol(CURRENT_COL);
		tetris.setCurrentRow(CURRENT_ROW);
		tetris.rotatePiece(NEW_ROTATION);
		
		assertEquals(tetris.getPieceRotation(), NEW_ROTATION);
	}
	
	@Test
	void renderGame_TR1(){
		tetris.renderGame();
		assertTrue(tetris.getBoardPanel().isDisplayable());
		assertTrue(tetris.getSidePanel().isDisplayable());
	}

	
	@Test
	void testUpdateGame_TR1() {
		// Inputs
		final int CURRENT_SCORE = 0;
		final int CURRENT_COL = 0;
		final int CURRENT_ROW = 3;
		final float CURRENT_SPEED = 2.0F;
		final int CURRENT_LEVEL = 0;
		
		// Expected Outputs
		final int EXPECTED_ROW = CURRENT_ROW + 1;
		final float EXPECTED_SPEED = 2.0F;
		final int EXPECTED_LEVEL = 0;
		final int EXPECTED_DOPCOLDOWN = 0;
		final int EXPECTED_SCORE = 0;
		
		// Inserting inputs
		tetris.setScore(CURRENT_SCORE);
		tetris.setCurrentCol(CURRENT_COL);
		tetris.setCurrentRow(CURRENT_ROW);
		tetris.setGameSpeed(CURRENT_SPEED);
		tetris.setLevel(CURRENT_LEVEL);
		
		// Testing the method
		tetris.updateGame();
		
		// Asserting outputs
		assertEquals(tetris.getScore(), EXPECTED_SCORE);
		assertEquals(tetris.getDropCoolDown(), EXPECTED_DOPCOLDOWN);
		assertEquals(tetris.getLevel(), EXPECTED_LEVEL);
		assertEquals(tetris.getPieceRow(), EXPECTED_ROW);
		assertEquals(tetris.getGameSpeed(), EXPECTED_SPEED);
		
	}
	
	@Test
	void testUpdateGame_TR2() {
		
		// Preparing Inputs
		final int CURRENT_SCORE = 0;
		final int CURRENT_ROW = 20;
		final int CURRENT_COL = 4;
		final float CURRENT_SPEED = 2F;
		final int CURRENT_LEVEL = 0;
		
		// Inserting Inputs
		tetris.setScore(CURRENT_SCORE);
		tetris.setCurrentRow(CURRENT_ROW);
		tetris.setCurrentCol(CURRENT_COL);
		tetris.setGameSpeed(CURRENT_SPEED);
		tetris.setLevel(CURRENT_LEVEL);
		
		// Expected Outputs
		final int EXPECTED_ROW = 0;
		final float EXPECTED_SPEED = CURRENT_SPEED + 0.035F;
		final int EXPECTED_LEVEL = (int)(EXPECTED_SPEED * 1.70F);
		final int EXPECTED_DROPCOLDOWN = 25;
		final int EXPECTED_SCORE = 0;
		
		// Testing the method
		tetris.updateGame();
		
		// Asserting outputs
		assertEquals(tetris.getGameSpeed(), EXPECTED_SPEED);   				// Speed should increase by 0.0035
		assertEquals(tetris.getDropCoolDown(), EXPECTED_DROPCOLDOWN);
		assertEquals(tetris.getLevel(), EXPECTED_LEVEL);
		assertEquals(tetris.getPieceRow(), EXPECTED_ROW);
		System.out.println(tetris.getScore());
		assertEquals(tetris.getScore(), EXPECTED_SCORE);   						// Score should not change
	}
	
	@Test
	void testUpdateGame_TR3() {
		
		// Preparing Inputs
		final int CURRENT_SCORE = 0;
		final int CURRENT_ROW = 20;
		final int CURRENT_COL = 7;
		final float CURRENT_SPEED = 2F;
		final int CURRENT_LEVEL = 0;
		
		// Filling up bottom row to have a cleared line case
		tetris.getBoardPanel().addPiece(TileType.TypeJ, 0, CURRENT_ROW, 0);
		tetris.getBoardPanel().addPiece(TileType.TypeI, 3, CURRENT_ROW, 0);
		
		// Inserting Inputs
		tetris.setCurrentType(TileType.TypeJ);
		tetris.setScore(CURRENT_SCORE);
		tetris.setCurrentRow(CURRENT_ROW);
		tetris.setCurrentCol(CURRENT_COL);
		tetris.setGameSpeed(CURRENT_SPEED);
		tetris.setLevel(CURRENT_LEVEL);
		
		// Inserting Inputs
		final int EXPECTED_ROW = 0;
		final float EXPECTED_SPEED = CURRENT_SPEED + 0.035F;
		final int EXPECTED_LEVEL = (int)(CURRENT_SPEED * 1.70F);
		final int EXPECTED_SCORE = CURRENT_SCORE + 100;
		final int EXPECTED_DROPCOLDOWN = 25;
		
		// Testing the method
		tetris.updateGame();
		
		// Asserting outputs
		assertEquals(tetris.getGameSpeed(), EXPECTED_SPEED);   				
		assertEquals(tetris.getDropCoolDown(), EXPECTED_DROPCOLDOWN);
		assertEquals(tetris.getLevel(), EXPECTED_LEVEL);
		assertEquals(tetris.getPieceRow(), EXPECTED_ROW);
		System.out.println(tetris.getScore());
		assertEquals(tetris.getScore(), EXPECTED_SCORE);   				    // Score should increase by 100
	}
	
	@Test
	void testStartGame_TR1() {
		tetris = new Tetris();
		final float INITIAL_SPEED = 1.0F;
		
		tetris.startGame();
		assertTrue(tetris.getLogicTimer().isPaused());
		assertNotNull(tetris.getRandomNumberGenerator());
		assertNotNull(tetris.getLogicTimer());
		assertTrue(tetris.isNewGame());
		assertEquals(tetris.getGameSpeed(),INITIAL_SPEED);
	}
	
	@Test
	void testStartGame_TR2() {
		// TBD
	}
	
	@Test
	void testStartGame_TR3() {
		tetris.setDropCoolDown(1);
		tetris.startGame();
		assertEquals(tetris.getDropCoolDown(), 0);
	}

	@Test
	void testTetris_TR1() {
		assertNotNull(tetris.getBoardPanel());
		assertNotNull(tetris.getSidePanel());
	}
	
	@Test 
	void testTetris_TR2() {   
		KeyEvent keyEvent = new KeyEvent(tetris, KeyEvent.KEY_PRESSED, System.nanoTime(), 0, KeyEvent.VK_S, 's');
		clock.setCyclesPerSecond(25.0f);
		final float EXPECTED_MILLS_PER_CYLE = clock.getMillsPerCycle();
		tetris.dispatchEvent(keyEvent);
		assertEquals(tetris.getLogicTimer().getMillsPerCycle(), EXPECTED_MILLS_PER_CYLE);
	}
	
	@Test 
	void testTetris_TR3() {
		final int PREVIOUS_COL = 5;
		tetris.setCurrentCol(PREVIOUS_COL);   // Makes sure the tile is not at the far left side of the board
		KeyEvent keyEvent = new KeyEvent(tetris, KeyEvent.KEY_PRESSED, System.nanoTime(), 0, KeyEvent.VK_A, 'a');
		tetris.dispatchEvent(keyEvent);
		assertEquals(tetris.getPieceCol(), PREVIOUS_COL - 1);
	}
	
	@Test 
	void testTetris_TR4() {
		final int PREVIOUS_COL = 3;
		tetris.setCurrentCol(PREVIOUS_COL);
		KeyEvent keyEvent = new KeyEvent(tetris, KeyEvent.KEY_PRESSED, System.nanoTime(), 0, KeyEvent.VK_D, 'd');
		tetris.dispatchEvent(keyEvent);
		assertEquals(tetris.getPieceCol(), PREVIOUS_COL + 1);
	}
	
	@Test 
	void testTetris_TR5() {
		final int PREVIOUS_ROTATION = 0;
		tetris.setCurrentRotation(PREVIOUS_ROTATION);
		KeyEvent keyEvent = new KeyEvent(tetris, KeyEvent.KEY_PRESSED, System.nanoTime(), 0, KeyEvent.VK_Q, 'q');
		tetris.dispatchEvent(keyEvent);
		
		int EXPECTED_NEW_CURRENT_ROTATION = 3;
		assertEquals(tetris.getPieceRotation(), EXPECTED_NEW_CURRENT_ROTATION);
		
		EXPECTED_NEW_CURRENT_ROTATION = 2;
		tetris.dispatchEvent(keyEvent);
		assertEquals(tetris.getPieceRotation(), EXPECTED_NEW_CURRENT_ROTATION);
	}
	
	@Test 
	void testTetris_TR6() {
		final int PREVIOUS_ROTATION = 2;
		tetris.setCurrentRotation(PREVIOUS_ROTATION);
		KeyEvent keyEvent = new KeyEvent(tetris, KeyEvent.KEY_PRESSED, System.nanoTime(), 0, KeyEvent.VK_E, 'e');
		tetris.dispatchEvent(keyEvent);
		int EXPECTED_NEW_CURRENT_ROTATION = 3;
		assertEquals(tetris.getPieceRotation(), EXPECTED_NEW_CURRENT_ROTATION);
		
		EXPECTED_NEW_CURRENT_ROTATION = 0;
		tetris.dispatchEvent(keyEvent);
		assertEquals(tetris.getPieceRotation(), EXPECTED_NEW_CURRENT_ROTATION);
		
	}
	
	@Test 
	void testTetris_TR7() {
		KeyEvent keyEvent = new KeyEvent(tetris, KeyEvent.KEY_PRESSED, System.nanoTime(), 0, KeyEvent.VK_P, 'p');
		tetris.dispatchEvent(keyEvent);
		assertTrue(tetris.isPaused());
	}
	
	@Test 
	void testTetris_TR8() {
		tetris.setIsNewGame(true);
		KeyEvent keyEvent = new KeyEvent(tetris, KeyEvent.KEY_PRESSED, System.nanoTime(), 0, KeyEvent.VK_ENTER, '\n');
		tetris.dispatchEvent(keyEvent);
		assertFalse(tetris.isPaused());
	}
	
	@Test 
	void testTetris_TR9() {
		KeyEvent keyEvent = new KeyEvent(tetris, KeyEvent.KEY_RELEASED, System.nanoTime(), 0, KeyEvent.VK_S, 's');
		tetris.dispatchEvent(keyEvent);
		assertFalse(tetris.getLogicTimer().isPaused());
	}
}
