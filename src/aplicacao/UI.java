package aplicacao;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import xadrex.ChessPosition;
import xadrex.Color;
import xadrex.PartidaXadrex;
import xadrex.PecasXadrex;

public class UI {

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	// Https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static ChessPosition readChessPosition(Scanner sc) {
		try {
			String s = sc.nextLine();
			char column = s.charAt(0);
			int row = Integer.parseInt(s.substring(1));
			return new ChessPosition(column, row);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Erro Lendo a posi��o Xadrex: Valores Validos a1 ate h8");
		}
	}

	public static void printMatch(PartidaXadrex partidaXadrex, List<PecasXadrex> captured) {
		printBoard(partidaXadrex.getPecasXadrexs());
		System.out.println();
		printCapturePieces(captured);
		System.out.println();
		System.out.println("Turno: " + partidaXadrex.getTurn());
		System.out.println("Agurdando Jogador: " + partidaXadrex.getCurrentPlayer());
	}

	public static void printBoard(PecasXadrex[][] pecas) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				printPeca(pecas[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	public static void printBoard(PecasXadrex[][] pecas, boolean[][] possibleMoves) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				printPeca(pecas[i][j], possibleMoves[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	private static void printPeca(PecasXadrex pecas, boolean background) {
		if (background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}

		if (pecas == null) {
			System.out.print("-" + ANSI_RESET);
		} else {
			if (pecas.getColor() == Color.WHITE) {
				System.out.print(ANSI_WHITE + pecas + ANSI_RESET);
			} else {
				System.out.print(ANSI_YELLOW + pecas + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}

	private static void printCapturePieces(List<PecasXadrex> captured) {
		List<PecasXadrex> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
		List<PecasXadrex> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());

		System.out.println("Pecas Capturadas: ");
		System.out.print("Brancas: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(white.toArray()));
		System.out.println(ANSI_RESET);

		System.out.print("Pretas: ");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(black.toArray()));
		System.out.println(ANSI_RESET);
	}
}
