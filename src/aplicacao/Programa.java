package aplicacao;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrex.ChessExcepetion;
import xadrex.ChessPosition;
import xadrex.PartidaXadrex;
import xadrex.PecasXadrex;

public class Programa {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		PartidaXadrex partidaxadex = new PartidaXadrex();

		List<PecasXadrex> captured = new ArrayList<>();

		while (!partidaxadex.getCheckMate()) {
			try {
				UI.clearScreen();
				UI.printMatch(partidaxadex, captured);
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc);
				boolean[][] possibleMoves = partidaxadex.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(partidaxadex.getPecasXadrexs(), possibleMoves);

				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc);
				PecasXadrex capturedPiece = partidaxadex.performChessMove(source, target);

				if (capturedPiece != null) {
					captured.add(capturedPiece);
				}
				
				if(partidaxadex.getPromoted() != null) {
					System.out.println("Enter Piece for promotion (B/N/R/Q):" );
					String type = sc.nextLine().toUpperCase();
					while(!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
						System.out.println("Invalide digite novamente a Piece for promotion (B/N/R/Q):" );
						type = sc.nextLine().toUpperCase();	
					}
					partidaxadex.replacePromotedPiece(type);
				}
			} catch (ChessExcepetion e) {
				System.out.println((e.getMessage()));
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println((e.getMessage()));
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printMatch(partidaxadex, captured);

	}

}
