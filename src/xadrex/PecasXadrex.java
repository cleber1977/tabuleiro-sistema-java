package xadrex;

import boardgame.Board;
import boardgame.Pecas;

public class PecasXadrex extends Pecas{
	private Color color;

	public PecasXadrex(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}


}
