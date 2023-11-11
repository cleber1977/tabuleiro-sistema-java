package xadrex.pecas;

import boardgame.Board;
import xadrex.Color;
import xadrex.PecasXadrex;

public class Rook extends PecasXadrex {

	public Rook(Board board, Color color) {
		super(board, color);

	}

	@Override
	public String toString() {
		return "R";
	}

}
