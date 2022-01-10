package com.company.ClientPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


import com.company.CommonPackage.*;


public class Connecting extends Thread {

	private static final int SERVER_PORT = 8901;
	private static final String HOST_NAME = "localhost";

	private Socket mySocket;
	private ObjectInputStream myInput;
	private static ObjectOutputStream myOutput;

	private Object object;

	private static MessageFromClient messageToServer;
	private MessageFromServer messageFromServer;

	static boolean connectedToServer = true;
	private volatile boolean threadRunning = true;

	public Connecting() {
		messageToServer = new MessageFromClient();

	}

	@Override
	public void run() {
		while (threadRunning) {
			try {

				connectedToServer = true;
				mySocket = new Socket(HOST_NAME, SERVER_PORT);
				myOutput = new ObjectOutputStream(mySocket.getOutputStream());
				myOutput.flush();
				myInput = new ObjectInputStream(mySocket.getInputStream());

			} catch (IOException e1) {
				GameFlowClient.setTryingToConnect(false);
				connectedToServer = false;
				CheckersGame.startButton.setEnabled(true);
				CheckersGame.stopButton.setEnabled(false);
			}

			while (connectedToServer) {
				try {

					object = myInput.readObject();
					messageFromServer = (MessageFromServer) object;

					GameFlowClient.setTryingToConnect(false);
					GameFlowClient.setResign(false);

					getDataFromServer(messageFromServer.getBoard(), messageFromServer.getChosenRow(),
							messageFromServer.getChosenCol(), messageFromServer.isGameRunning(),
							messageFromServer.getCurrentPlayer(), messageFromServer.getPossibleMoves(),
							messageFromServer.getMyColor(), messageFromServer.getWinner());
					// System.out.println(GameFlowClient.isResign());

					if (GameFlowClient.isResign() == true) {
						sendMessageToServer(-1, -1, GameFlowClient.isResign());
						CheckersGame.startButton.setEnabled(true);
						CheckersGame.stopButton.setEnabled(false);
						break;

					} else if (messageFromServer.getWinner() != GameFlowClient.EMPTY) {
						if (messageFromServer.getWinner() == GameFlowClient.getMyColor()) {
							CheckersGame.startButton.setEnabled(true);
							CheckersGame.stopButton.setEnabled(false);

							break;
						} else {
							CheckersGame.startButton.setEnabled(true);
							CheckersGame.stopButton.setEnabled(false);

							break;
						}
					}

				} catch (ClassNotFoundException e) {
				} catch (IOException e) {


				}

			}
			threadRunning = false;
			try {
				myOutput.close();
				myInput.close();
				mySocket.close();
			} catch (IOException e) {


			}
		}

	}

	private void getDataFromServer(int[][] board, int chosenRow, int chosenCol, boolean gameRunning, int currentPlayer,
			CheckersMove[] possibleMoves, int myColor, int winner) {
		GameFlowClient.setBoard(board);
		GameFlowClient.setChosenRow(chosenRow);
		GameFlowClient.setChosenCol(chosenCol);
		GameFlowClient.setGameRunning(gameRunning);
		GameFlowClient.setCurrentPlayer(currentPlayer);
		GameFlowClient.setPossibleMoves(possibleMoves);
		GameFlowClient.setMyColor(myColor);
		GameFlowClient.setWinner(winner);

	}

	private static void prepareMessageToServer(int row, int col, boolean resign) {
		messageToServer.setChosenCol(col);
		messageToServer.setChosenRow(row);
		messageToServer.setResign(resign);

	}

	public static void sendMessageToServer(int row, int col, boolean resign) {
		prepareMessageToServer(row, col, resign);
		try {
			myOutput.reset();
			myOutput.writeObject(messageToServer);
		} catch (IOException e) {

		}

	}

}
