package com.company.ClientPackage;

import com.company.CommonPackage.*;


public class GameFlowClient {

	static boolean gameRunning = false;

	static final int EMPTY = 0, WHITE = 1, WHITE_QUEEN = 2, BLACK = 3, BLACK_QUEEN = 4;
	static private int[][] board = new int[8][8];

	static CheckersMove[] possibleMoves;
	static int currentPlayer;
	static int chosenRow = -1;
	static int chosenCol = -1;
	static int myColor;
	static int winner = -1;
	static boolean resign = false;

	static boolean tryingToConnect = false;
	static Connecting connecting;

	public static boolean isTryingToConnect() {
		return tryingToConnect;
	}

	public static void setTryingToConnect(boolean tryingToConnect) {
		GameFlowClient.tryingToConnect = tryingToConnect;
	}

	public static boolean isResign() {
		return resign;
	}

	public static void setResign(boolean resign) {
		GameFlowClient.resign = resign;
	}

	public static int getWinner() {
		return winner;
	}

	public static void setWinner(int winner) {
		GameFlowClient.winner = winner;
	}

	public static int getMyColor() {
		return myColor;
	}

	public static void setMyColor(int myColor) {
		GameFlowClient.myColor = myColor;
	}

	public static boolean isGameRunning() {
		return gameRunning;
	}

	public static void setGameRunning(boolean gameRunning) {
		GameFlowClient.gameRunning = gameRunning;
	}

	public static int getChosenRow() {
		return chosenRow;
	}

	public static void setChosenRow(int chosenRow) {
		GameFlowClient.chosenRow = chosenRow;
	}

	public static int getChosenCol() {
		return chosenCol;
	}

	public static void setChosenCol(int chosenCol) {
		GameFlowClient.chosenCol = chosenCol;
	}

	public static int getCurrentPlayer() {
		return currentPlayer;
	}

	public static void setCurrentPlayer(int currentPlayer) {
		GameFlowClient.currentPlayer = currentPlayer;
	}

	public static int[][] getBoard() {
		return board;
	}

	public static void setBoard(int[][] board) {
		GameFlowClient.board = board;
	}

	public static CheckersMove[] getPossibleMoves() {
		return possibleMoves;
	}

	public static void setPossibleMoves(CheckersMove[] possibleMoves) {
		GameFlowClient.possibleMoves = possibleMoves;
	}

	public GameFlowClient() {

		initializeGame();

	}


	private void initializeGame() {
		setElementsOnStart();

	}


	public static void setElementsOnStart() {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (row % 2 != col % 2) {
					if (row < 3)
						board[row][col] = BLACK;
					else if (row > 4)
						board[row][col] = WHITE;
					else
						board[row][col] = EMPTY;
				} else {
					board[row][col] = EMPTY;
				}
			}
		}
	}

	public static int getFieldOnBoard(int row, int col) {
		return board[row][col];
	}

	static void startNewGame() {

		CheckersGame.startButton.setEnabled(false);
		CheckersGame.stopButton.setEnabled(true);

		connecting = new Connecting();
		connecting.start();

	}


	static void resignGame() {
		resign = true;

	}


	
}
