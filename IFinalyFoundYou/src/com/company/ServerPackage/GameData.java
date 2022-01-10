package com.company.ServerPackage;

import java.util.ArrayList;

import com.company.CommonPackage.*;



 //где что стоит и как ходит
public class GameData {

	// фигуры на доске (через цифры возвращаемся к истокам)
	static final int EMPTY = 0, WHITE = 1, WHITE_QUEEN = 2, BLACK = 3, BLACK_QUEEN = 4;
	private int[][] board = new int[8][8];// массив доски (поле)

	public int[][] getBoard() {
		return board;
	}

	public GameData() {

		setElementsOnStart();

	}

	//заготовка поля
	public void setElementsOnStart() {
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





	  //делаем возможный ход

	public void makeMove(CheckersMove move) {


		 //если ход дамки

		if (board[move.getMoveFromRow()][move.getMoveFromCol()] == BLACK_QUEEN
				|| board[move.getMoveFromRow()][move.getMoveFromCol()] == WHITE_QUEEN) {
			removeOpponentCheckerIfBeating(move);
			moveChecker(move);
		}

		 //ход шашки

		else if (move.isMoveBeating()) {// удар
			removeOpponentChecker(move);
			moveChecker(move);
		} else
			moveChecker(move);// ход

		checkIfNewQueen(move);

	}

	private void removeOpponentCheckerIfBeating(CheckersMove move) {// драка
		int opponentCheckerRow = 0;// координаты шашки противника
		int opponentCheckerCol = 0;

		int checkRow = move.getMoveFromRow();
		int checkCol = move.getMoveFromCol();

		/*
		 * Start looking for opponent checker in 4 possible directions
		 */
		if (move.getMoveFromRow() < move.getMoveToRow() && move.getMoveFromCol() < move.getMoveToCol()) {

			while (checkCol < move.getMoveToCol() && checkRow < move.getMoveToRow()) {
				checkCol++;
				checkRow++;

				if (board[checkRow][checkCol] != EMPTY) {
					move.setBeatingPerformedByQueen(true);// только драка не  ходы
					move.setMovePerformedByQueen(false);

					break;
				}
			}

		} else if (move.getMoveFromRow() < move.getMoveToRow() && move.getMoveFromCol() > move.getMoveToCol()) {
			while (checkCol > move.getMoveToCol() && checkRow < move.getMoveToRow()) {
				checkCol--;
				checkRow++;

				if (board[checkRow][checkCol] != EMPTY) {
					move.setBeatingPerformedByQueen(true);//ходы дамки
					move.setMovePerformedByQueen(false);

					break;
				}
			}

		} else if (move.getMoveFromRow() > move.getMoveToRow() && move.getMoveFromCol() < move.getMoveToCol()) {
			while (checkCol < move.getMoveToCol() && checkRow > move.getMoveToRow()) {
				checkCol++;
				checkRow--;

				if (board[checkRow][checkCol] != EMPTY) {
					move.setBeatingPerformedByQueen(true);
					move.setMovePerformedByQueen(false);

					break;
				}
			}

		} else if (move.getMoveFromRow() > move.getMoveToRow() && move.getMoveFromCol() > move.getMoveToCol()) {
			while (checkCol > move.getMoveToCol() && checkRow > move.getMoveToRow()) {

				checkCol--;
				checkRow--;

				if (board[checkRow][checkCol] != EMPTY) {
					move.setBeatingPerformedByQueen(true);
					move.setMovePerformedByQueen(false);

					break;
				}
			}

		}

		opponentCheckerCol = checkCol;
		opponentCheckerRow = checkRow;

		board[opponentCheckerRow][opponentCheckerCol] = EMPTY;

		if (move.isBeatingPerformedByQueen() == false) {
			move.setMovePerformedByQueen(true);

		}

	}

	//выход в дамки
	private void checkIfNewQueen(CheckersMove move) {
		if (move.getMoveToRow() == 0 && board[move.getMoveToRow()][move.getMoveToCol()] == WHITE) {
			move.setMovePerformedByQueen(true);//белые
			board[move.getMoveToRow()][move.getMoveToCol()] = WHITE_QUEEN;

		}
		if (move.getMoveToRow() == 7 && board[move.getMoveToRow()][move.getMoveToCol()] == BLACK) {
			move.setMovePerformedByQueen(true);//черные
			board[move.getMoveToRow()][move.getMoveToCol()] = BLACK_QUEEN;

		}
	}

	/*
	 * ход
	 */
	private void moveChecker(CheckersMove move) {
		board[move.getMoveToRow()][move.getMoveToCol()] = board[move.getMoveFromRow()][move.getMoveFromCol()];
		board[move.getMoveFromRow()][move.getMoveFromCol()] = EMPTY;
	}

	/*
	  удаление шашки
	 */
	private void removeOpponentChecker(CheckersMove move) {

		int opponentCheckerRow = 0;
		int opponentCheckerCol = 0;

		if (move.getMoveFromRow() < move.getMoveToRow() && move.getMoveFromCol() < move.getMoveToCol()) {
			opponentCheckerRow = move.getMoveToRow() - 1;
			opponentCheckerCol = move.getMoveToCol() - 1;
		} else if (move.getMoveFromRow() < move.getMoveToRow() && move.getMoveFromCol() > move.getMoveToCol()) {
			opponentCheckerRow = move.getMoveToRow() - 1;
			opponentCheckerCol = move.getMoveToCol() + 1;
		} else if (move.getMoveFromRow() > move.getMoveToRow() && move.getMoveFromCol() < move.getMoveToCol()) {
			opponentCheckerRow = move.getMoveToRow() + 1;
			opponentCheckerCol = move.getMoveToCol() - 1;
		} else if (move.getMoveFromRow() > move.getMoveToRow() && move.getMoveFromCol() > move.getMoveToCol()) {
			opponentCheckerRow = move.getMoveToRow() + 1;
			opponentCheckerCol = move.getMoveToCol() + 1;
		}

		board[opponentCheckerRow][opponentCheckerCol] = EMPTY;

	}

	/*
	 собираем масив ходов
	 */
	public CheckersMove[] getPossibleMovesForPlayer(int player) {

		if (player != WHITE && player != BLACK)
			return null;

		int playerQueen;
		if (player == WHITE)
			playerQueen = WHITE_QUEEN;
		else
			playerQueen = BLACK_QUEEN;

		// массив ходов
		ArrayList<CheckersMove> moves = new ArrayList<CheckersMove>();

		// массив боя
		checkPossibleBeating(moves, player, playerQueen);

		// массив передвижения
		if (moves.size() == 0) {
			checkPossibleRegularMoves(moves, player, playerQueen);

		}

		// ходов нет
		if (moves.size() == 0) {
			return null;

		} else {
			CheckersMove[] arrayOfPossibleMoves = new CheckersMove[moves.size()];
			for (int i = 0; i < moves.size(); i++) {
				arrayOfPossibleMoves[i] = moves.get(i);
			}
			return arrayOfPossibleMoves;

		}

	}

	/*
	 поиск ходов
	 */
	private void checkPossibleRegularMoves(ArrayList<CheckersMove> moves, int player, int playerQueen) {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (board[row][col] == player) {
					// 4 направления
					if (canMove(player, row, col, row + 1, col + 1))
						moves.add(new CheckersMove(row, col, row + 1, col + 1));
					if (canMove(player, row, col, row - 1, col + 1))
						moves.add(new CheckersMove(row, col, row - 1, col + 1));
					if (canMove(player, row, col, row + 1, col - 1))
						moves.add(new CheckersMove(row, col, row + 1, col - 1));
					if (canMove(player, row, col, row - 1, col - 1))
						moves.add(new CheckersMove(row, col, row - 1, col - 1));
				} // ходы дамки
				else if (board[row][col] == playerQueen) {
					canQueenMove(moves, player, row, col);
				}
			}
		}
	}

	/*
	 ходы дамки
	 */
	private void canQueenMove(ArrayList<CheckersMove> moves, int player, int rowFrom, int colFrom) {

		int rowToCheck = rowFrom;
		int colToCheck = colFrom;

		if (player == WHITE) {


			while (--rowToCheck >= 0 && --colToCheck >= 0) {
				if (board[rowToCheck][colToCheck] != EMPTY) {
					break;
				}
				moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));
			}
			rowToCheck = rowFrom;
			colToCheck = colFrom;
			while (--rowToCheck >= 0 && ++colToCheck <= 7) {

				if (board[rowToCheck][colToCheck] != EMPTY) {

					break;
				}
				moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

			}
			rowToCheck = rowFrom;
			colToCheck = colFrom;
			while (++rowToCheck <= 7 && --colToCheck >= 0) {

				if (board[rowToCheck][colToCheck] != EMPTY) {

					break;
				}
				moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

			}
			rowToCheck = rowFrom;
			colToCheck = colFrom;
			while (++rowToCheck <= 7 && ++colToCheck <= 7) {

				if (board[rowToCheck][colToCheck] != EMPTY) {

					break;
				}
				moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

			}

		} else {
			while (--rowToCheck >= 0 && --colToCheck >= 0) {

				if (board[rowToCheck][colToCheck] != EMPTY) {

					break;
				}
				moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

			}
			rowToCheck = rowFrom;
			colToCheck = colFrom;
			while (--rowToCheck >= 0 && ++colToCheck <= 7) {

				if (board[rowToCheck][colToCheck] != EMPTY) {

					break;
				}
				moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

			}
			rowToCheck = rowFrom;
			colToCheck = colFrom;
			while (++rowToCheck <= 7 && --colToCheck >= 0) {

				if (board[rowToCheck][colToCheck] != EMPTY) {

					break;
				}
				moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

			}
			rowToCheck = rowFrom;
			colToCheck = colFrom;
			while (++rowToCheck <= 7 && ++colToCheck <= 7) {

				if (board[rowToCheck][colToCheck] != EMPTY) {

					break;
				}
				moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

			}

		}

	}


	private boolean canMove(int player, int rowFrom, int colFrom, int rowTo, int colTo) {
		// мимо доски
		if (colTo > 7 || colTo < 0 || rowTo > 7 || rowTo < 0)
			return false;

		// на пути противник
		if (board[rowTo][colTo] != EMPTY)
			return false;

		// кто ходит
		if (player == WHITE) {
			if (rowTo > rowFrom)
				return false;
			return true;
		} else {
			if (rowTo < rowFrom)
				return false;
			return true;
		}
	}

	/*
	 поиск драки
	 */
	private void checkPossibleBeating(ArrayList<CheckersMove> moves, int player, int playerQueen) {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (board[row][col] == player) {// check for possible beating
												// for checker
					// 4 directions
					if (canBeat(player, row, col, row + 1, col + 1, row + 2, col + 2))
						moves.add(new CheckersMove(row, col, row + 2, col + 2));
					if (canBeat(player, row, col, row - 1, col + 1, row - 2, col + 2))
						moves.add(new CheckersMove(row, col, row - 2, col + 2));
					if (canBeat(player, row, col, row + 1, col - 1, row + 2, col - 2))
						moves.add(new CheckersMove(row, col, row + 2, col - 2));
					if (canBeat(player, row, col, row - 1, col - 1, row - 2, col - 2))
						moves.add(new CheckersMove(row, col, row - 2, col - 2));
				} // для дпмки
				else if (board[row][col] == playerQueen) {
					canQueenBeat(moves, player, row, col);
				}

			}

		}
	}

	/*
	 бой дамки
	 */
	private void canQueenBeat(ArrayList<CheckersMove> moves, int player, int rowFrom, int colFrom) {

		int rowToCheck = rowFrom;
		int colToCheck = colFrom;

		boolean enemyCheckerFound = false;
		if (player == WHITE) {

			while (--rowToCheck >= 0 && --colToCheck >= 0) {

				if (board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN) {
					break;
				} else if ((board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN)
						&& enemyCheckerFound == true) {
					enemyCheckerFound = false;
					break;
				} else if (board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN) {
					enemyCheckerFound = true;
				} else if (enemyCheckerFound == true && board[rowToCheck][colToCheck] == EMPTY) {
					moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

				}

			}
			rowToCheck = rowFrom;
			colToCheck = colFrom;
			enemyCheckerFound = false;

			while (--rowToCheck >= 0 && ++colToCheck <= 7) {
				if (board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN) {
					break;
				} else if ((board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN)
						&& enemyCheckerFound == true) {
					enemyCheckerFound = false;
					break;
				} else if (board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN) {
					enemyCheckerFound = true;
				} else if (enemyCheckerFound == true && board[rowToCheck][colToCheck] == EMPTY) {
					moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

				}

			}
			rowToCheck = rowFrom;
			colToCheck = colFrom;
			enemyCheckerFound = false;

			while (++rowToCheck <= 7 && --colToCheck >= 0) {
				if (board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN) {
					break;
				} else if ((board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN)
						&& enemyCheckerFound == true) {
					enemyCheckerFound = false;
					break;
				} else if (board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN) {
					enemyCheckerFound = true;
				} else if (enemyCheckerFound == true && board[rowToCheck][colToCheck] == EMPTY) {
					moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

				}

			}
			rowToCheck = rowFrom;
			colToCheck = colFrom;
			enemyCheckerFound = false;
			while (++rowToCheck <= 7 && ++colToCheck <= 7) {
				if (board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN) {
					break;
				} else if ((board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN)
						&& enemyCheckerFound == true) {
					enemyCheckerFound = false;
					break;
				} else if (board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN) {
					enemyCheckerFound = true;
				} else if (enemyCheckerFound == true && board[rowToCheck][colToCheck] == EMPTY) {
					moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

				}

			}

		} else {
			while (--rowToCheck >= 0 && --colToCheck >= 0) {
				if (board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN) {
					break;
				} else if ((board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN)
						&& enemyCheckerFound == true) {
					enemyCheckerFound = false;
					break;
				} else if (board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN) {
					enemyCheckerFound = true;
				} else if (enemyCheckerFound == true && board[rowToCheck][colToCheck] == EMPTY) {
					moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

				}

			}
			rowToCheck = rowFrom;
			colToCheck = colFrom;
			enemyCheckerFound = false;

			while (--rowToCheck >= 0 && ++colToCheck <= 7) {
				if (board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN) {
					break;
				} else if ((board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN)
						&& enemyCheckerFound == true) {
					enemyCheckerFound = false;
					break;
				} else if (board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN) {
					enemyCheckerFound = true;
				} else if (enemyCheckerFound == true && board[rowToCheck][colToCheck] == EMPTY) {
					moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

				}

			}
			rowToCheck = rowFrom;
			colToCheck = colFrom;
			enemyCheckerFound = false;
			while (++rowToCheck <= 7 && --colToCheck >= 0) {
				if (board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN) {
					break;
				} else if ((board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN)
						&& enemyCheckerFound == true) {
					enemyCheckerFound = false;
					break;
				} else if (board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN) {
					enemyCheckerFound = true;
				} else if (enemyCheckerFound == true && board[rowToCheck][colToCheck] == EMPTY) {
					moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

				}

			}
			rowToCheck = rowFrom;
			colToCheck = colFrom;
			enemyCheckerFound = false;
			while (++rowToCheck <= 7 && ++colToCheck <= 7) {
				if (board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN) {
					break;
				} else if ((board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN)
						&& enemyCheckerFound == true) {
					enemyCheckerFound = false;
					break;
				} else if (board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN) {
					enemyCheckerFound = true;
				} else if (enemyCheckerFound == true && board[rowToCheck][colToCheck] == EMPTY) {
					moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

				}

			}

		}
	}

	private boolean canBeat(int player, int rowFrom, int colFrom, int rowJumped, int colJumped, int rowTo, int colTo) {


		if (colTo > 7 || colTo < 0 || rowTo > 7 || rowTo < 0) {
			return false;

		}


		if (board[rowTo][colTo] != EMPTY) {
			return false;

		}


		if (player == WHITE) {
			if (rowTo > rowFrom && board[rowFrom][colFrom] == WHITE) {

				return false;

			}
			if (board[rowJumped][colJumped] != BLACK && board[rowJumped][colJumped] != BLACK_QUEEN) {

				return false;

			}
			return true;
		} else {
			if (rowTo < rowFrom && board[rowFrom][colFrom] == BLACK) {

				return false;

			}
			if (board[rowJumped][colJumped] != WHITE && board[rowJumped][colJumped] != WHITE_QUEEN) {

				return false;

			}
			return true;
		}

	}


	public CheckersMove[] getPossibleSecondBeating(int player, int rowFrom, int colFrom) {

		if (player != WHITE && player != BLACK)
			return null;

		int playerQueen;
		if (player == WHITE)
			playerQueen = WHITE_QUEEN;
		else
			playerQueen = BLACK_QUEEN;


		ArrayList<CheckersMove> moves = new ArrayList<CheckersMove>();


		checkPossibleSecondBeating(moves, player, playerQueen, rowFrom, colFrom);


		if (moves.size() == 0) {
			return null;

		} else {
			CheckersMove[] arrayOfSecondBeat = new CheckersMove[moves.size()];
			for (int i = 0; i < moves.size(); i++)
				arrayOfSecondBeat[i] = moves.get(i);
			return arrayOfSecondBeat;

		}

	}


	private void checkPossibleSecondBeating(ArrayList<CheckersMove> moves, int player, int playerQueen, int row,
			int col) {

		if (board[row][col] == player) {

			if (canBeat(player, row, col, row + 1, col + 1, row + 2, col + 2))
				moves.add(new CheckersMove(row, col, row + 2, col + 2));
			if (canBeat(player, row, col, row - 1, col + 1, row - 2, col + 2))
				moves.add(new CheckersMove(row, col, row - 2, col + 2));
			if (canBeat(player, row, col, row + 1, col - 1, row + 2, col - 2))
				moves.add(new CheckersMove(row, col, row + 2, col - 2));
			if (canBeat(player, row, col, row - 1, col - 1, row - 2, col - 2))
				moves.add(new CheckersMove(row, col, row - 2, col - 2));
		}
		else if (board[row][col] == playerQueen) {
			canQueenBeat(moves, player, row, col);
		}

	}

}
