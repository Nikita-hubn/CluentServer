package com.company.CommonPackage;

import java.io.Serializable;

public class MessageFromServer implements Serializable {

	private static final long serialVersionUID = -5889903096874602732L;
	private int[][] board = new int[8][8];
	private boolean gameRunning;
	private int currentPlayer;
	private CheckersMove[] possibleMoves;
	private int chosenRow;
	private int chosenCol;
	private int winner;
	private int myColor;

	public int getMyColor() {
		return myColor;
	}

	public void setMyColor(int myColor) {
		this.myColor = myColor;
	}

	public int getChosenRow() {
		return chosenRow;
	}

	public void setChosenRow(int chosenRow) {
		this.chosenRow = chosenRow;
	}

	public int getChosenCol() {
		return chosenCol;
	}

	public void setChosenCol(int chosenCol) {
		this.chosenCol = chosenCol;
	}

	public int[][] getBoard() {
		return board;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	public boolean isGameRunning() {
		return gameRunning;
	}

	public void setGameRunning(boolean gameRunning) {
		this.gameRunning = gameRunning;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public CheckersMove[] getPossibleMoves() {
		return possibleMoves;
	}

	public void setPossibleMoves(CheckersMove[] possibleMoves) {
		this.possibleMoves = possibleMoves;
	}

}
