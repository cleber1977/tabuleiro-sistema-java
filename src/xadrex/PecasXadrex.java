package xadrex;

import boardgame.Board;
import boardgame.Pecas;
import boardgame.Posicao;

public abstract class PecasXadrex extends Pecas{
	private Color color;

	public PecasXadrex(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	protected boolean isThereOpponetPiece(Posicao position) {
		PecasXadrex p = (PecasXadrex)getBoard().pecas(position);
		return p != null && p.getColor() != color;
	}


}
