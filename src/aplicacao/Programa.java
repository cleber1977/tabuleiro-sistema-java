package aplicacao;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrex.ChessExcepetion;
import xadrex.ChessPosition;
import xadrex.PartidaXadrex;
import xadrex.PecasXadrex;

public class Programa {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		PartidaXadrex partidaxadex = new PartidaXadrex();
		while(true) {
			try {
			UI.clearScreen();
			UI.printMatch(partidaxadex);
			System.out.println();
			System.out.print("Source: ");
			ChessPosition source = UI.readChessPosition(sc);
			 boolean [][] possibleMoves = partidaxadex.possibleMoves(source);
			 UI.clearScreen();
			 UI.printBoard(partidaxadex.getPecasXadrexs(), possibleMoves);
			
			 
			System.out.println();
			System.out.print("Target: ");
			ChessPosition target = UI.readChessPosition(sc);
			PecasXadrex capturedPiece = partidaxadex.performChessMove(source, target);
			}
			catch(ChessExcepetion e) {
				System.out.println((e.getMessage()));
				sc.nextLine();
			}
			catch(InputMismatchException e) {
				System.out.println((e.getMessage()));
				sc.nextLine();
			}
		}
		
	}

}
