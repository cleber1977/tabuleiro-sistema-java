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

	@Override
	public boolean[][] possibleMoves() {
		boolean [][] mat = new boolean [getBoard().getRows()][getBoard().getColumns()];
		return mat;
	}

}
