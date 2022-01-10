package com.company.ServerPackage;

import java.io.IOException;
import java.net.ServerSocket;

public class CheckersServer {

	private static final int SERVER_PORT = 8901;
	private static int matchNumber = 1;

	public static void main(String[] args) throws IOException {

		ServerSocket serversocket = new ServerSocket(SERVER_PORT);
		System.out.println("Checkers server is running");

		try {
			while (true) {

				Match match = new Match(matchNumber);
				System.out.println("Waiting for players...");

				try {

					Match.Player playerWhite = match.new Player(serversocket.accept(), GameData.WHITE);
					System.out.println("Match #" + matchNumber + ": player #1 connected.");

					Match.Player playerBlack = match.new Player(serversocket.accept(), GameData.BLACK);
					System.out.println("Match #" + matchNumber + ": player #2 connected.");

					playerWhite.start();
					playerBlack.start();

					matchNumber++;
				} catch (IOException e) {
					System.out.println("IOError");
				}

			}
		} finally {
			serversocket.close();
		}

	}

}
