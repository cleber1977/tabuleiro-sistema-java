package aplicacao;

import xadrex.PartidaXadrex;
import xadrex.PecasXadrex;

public class UI {
	public static void printBoard(PecasXadrex[][] pecas) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				printPeca(pecas[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c e f g h");
	}

	private static void printPeca(PecasXadrex pecas) {
		if (pecas == null) {
			System.out.print("-");
		} else {
			System.out.print(pecas);
		}
		System.out.print(" ");
	}
}
