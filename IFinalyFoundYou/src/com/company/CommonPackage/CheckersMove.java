package com.company.CommonPackage;

import java.io.Serializable;


public class CheckersMove implements Serializable {

	private static final long serialVersionUID = 7989998894212425464L;
	private int moveFromRow, moveFromCol; // координаты откуда фигуры пойдут

	private int moveToRow, moveToCol; // координаты куда пойдут фигуры


	private boolean movePerformedByQueen = false;// определитель когда ходит дамка

	private boolean beatingPerformedByQueen = false;// определитель когда бьет дамка


	public boolean isBeatingPerformedByQueen() {
		return beatingPerformedByQueen;
	}

	public void setBeatingPerformedByQueen(boolean beatingPerformedByQueen) {
		this.beatingPerformedByQueen = beatingPerformedByQueen;
	}

	public boolean isMovePerformedByQueen() {
		return movePerformedByQueen;
	}

	public void setMovePerformedByQueen(boolean movePerformedByQueen) {
		this.movePerformedByQueen = movePerformedByQueen;
	}

	public boolean isMoveBeating() {// когда шашка может побить

		return (moveFromCol - moveToCol == 2 || moveFromCol - moveToCol == -2);
	}

	public int getMoveFromRow() {
		return moveFromRow;
	}

	public void setMoveFromRow(int moveFromRow) {
		this.moveFromRow = moveFromRow;
	}

	public int getMoveFromCol() {
		return moveFromCol;
	}

	public void setMoveFromCol(int moveFromCol) {
		this.moveFromCol = moveFromCol;
	}

	public int getMoveToRow() {
		return moveToRow;
	}

	public void setMoveToRow(int moveToRow) {
		this.moveToRow = moveToRow;
	}

	public int getMoveToCol() {
		return moveToCol;
	}

	public void setMoveToCol(int moveToCol) {
		this.moveToCol = moveToCol;
	}

	public CheckersMove(int moveFromRow, int moveFromCol, int moveToRow, int moveToCol) {
		// простой конструктор
		this.moveFromRow = moveFromRow;
		this.moveFromCol = moveFromCol;
		this.moveToRow = moveToRow;
		this.moveToCol = moveToCol;
	}

}
