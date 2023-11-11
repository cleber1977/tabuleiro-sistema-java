package xadrex.pecas;

import boardgame.Board;
import xadrex.Color;
import xadrex.PecasXadrex;

public class King extends PecasXadrex {

	public King(Board board, Color color) {
		super(board, color);
	}
	
	public String toString() {
		return "K";
	}

}