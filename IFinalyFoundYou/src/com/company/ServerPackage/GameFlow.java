package com.company.ServerPackage;

import com.company.CommonPackage.*;

/*
 * игра
 */
public class GameFlow {

	private boolean gameRunning = false;// flag
	private int winner = GameData.EMPTY;

	GameData boardData;// данные доски
	private int currentPlayer;// кто ходит

	private int chosenRow = -1;// coordinates of selected checker
	private int chosenCol = -1;// -1 means no selected row or column

	CheckersMove[] possibleMoves;// массив с возможными ходами


	public synchronized int getChosenRow() {
		return chosenRow;
	}

	public synchronized int getCurrentPlayer() {
		return currentPlayer;
	}

	public synchronized void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public synchronized void setChosenRow(int chosenRow) {
		this.chosenRow = chosenRow;
	}

	public synchronized int getChosenCol() {
		return chosenCol;
	}

	public synchronized void setChosenCol(int chosenCol) {
		this.chosenCol = chosenCol;
	}

	public synchronized CheckersMove[] getPossibleMoves() {
		return possibleMoves;
	}

	public synchronized int getWinner() {
		return winner;
	}

	public synchronized boolean isGameRunning() {
		return gameRunning;
	}

	public synchronized void setGameRunning(boolean gameRunning) {
		this.gameRunning = gameRunning;
	}

	public synchronized void setWinner(int winner) {
		this.winner = winner;
	}

	public GameFlow() {

		initializeGame();

	}

	private void initializeGame() {
		if (gameRunning == true) {
			// этого не может быть
			return;
		}
		boardData = new GameData();
		currentPlayer = GameData.WHITE;// белые ходят первыми
		possibleMoves = boardData.getPossibleMovesForPlayer(currentPlayer);
		gameRunning = true;

	}

	private void gameIsOver(int winner) {

		gameRunning = false;
		this.winner = winner;
	}


	synchronized void makeClick(int row, int col, boolean resign) {

		if (resign == true) {

			if (currentPlayer == GameData.WHITE)
				gameIsOver(GameData.BLACK);
			else
				gameIsOver(GameData.WHITE);

		} else {
			/*
			выбрали шашку
			 */
			for (int i = 0; i < possibleMoves.length; i++)
				if (possibleMoves[i].getMoveFromRow() == row && possibleMoves[i].getMoveFromCol() == col) {
					chosenRow = row;
					chosenCol = col;
					return;
				}
			/*
			 * если ничего не выбрано
			 */
			if (chosenRow < 0) {
				return;
			}
			/*
			 * совершается ход
			 */
			for (int i = 0; i < possibleMoves.length; i++)
				if (possibleMoves[i].getMoveFromRow() == chosenRow && possibleMoves[i].getMoveFromCol() == chosenCol
						&& possibleMoves[i].getMoveToRow() == row && possibleMoves[i].getMoveToCol() == col) {
					performMove(possibleMoves[i]);
					return;
				}
		}
	}

	/*
	 * Make specific move
	 */
	synchronized private void performMove(CheckersMove checkerMove) {

		// make a move
		boardData.makeMove(checkerMove);

		/*
		 проверка на двойной удар и дамку
		 */
		if ((checkerMove.isMoveBeating() && !checkerMove.isMovePerformedByQueen())
				|| checkerMove.isBeatingPerformedByQueen()) {
			possibleMoves = boardData.getPossibleSecondBeating(currentPlayer, checkerMove.getMoveToRow(),
					checkerMove.getMoveToCol());
			if (possibleMoves != null) {
				chosenRow = checkerMove.getMoveToRow(); // вилка
				chosenCol = checkerMove.getMoveToCol();
				return;
			}
		}


		checkerMove.setMovePerformedByQueen(false);
		checkerMove.setBeatingPerformedByQueen(false);

		/*
		 смена хода и проверка на окончание
		 */
		if (currentPlayer == GameData.WHITE) {
			currentPlayer = GameData.BLACK;
			possibleMoves = boardData.getPossibleMovesForPlayer(currentPlayer);
			if (possibleMoves == null)
				gameIsOver(GameData.WHITE);

		} else {
			currentPlayer = GameData.WHITE;
			possibleMoves = boardData.getPossibleMovesForPlayer(currentPlayer);
			if (possibleMoves == null)
				gameIsOver(GameData.BLACK);
		}

		  //начало хода ничего не выбрано

		chosenRow = -1;
		chosenCol = -1;

		//возможно ударить
		if (possibleMoves != null) {
			boolean sameSquare = true;
			for (int i = 1; i < possibleMoves.length; i++)
				if (possibleMoves[i].getMoveFromRow() != possibleMoves[0].getMoveFromRow()
						|| possibleMoves[i].getMoveFromCol() != possibleMoves[0].getMoveFromCol()) {
					sameSquare = false;
					break;
				}
			if (sameSquare) {
				chosenRow = possibleMoves[0].getMoveFromRow();
				chosenCol = possibleMoves[0].getMoveFromCol();
			}
		}
	}

}
