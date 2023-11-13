package aplicacao;

import java.util.Scanner;

import xadrex.ChessPosition;
import xadrex.PartidaXadrex;
import xadrex.PecasXadrex;

public class Programa {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		PartidaXadrex partidaxadex = new PartidaXadrex();
		while(true) {
			UI.printBoard(partidaxadex.getPecasXadrexs());
			System.out.println();
			System.out.println("Source: ");
			ChessPosition source = UI.readChessPosition(sc);
			System.out.println();
			System.out.println("Target: ");
			ChessPosition target = UI.readChessPosition(sc);
			PecasXadrex capturedPiece = partidaxadex.performChessMove(source, target);
		}
		
	}

}
