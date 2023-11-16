package xadrex;

import boardgame.Board;
import boardgame.Pecas;
import boardgame.Posicao;

public abstract class PecasXadrex extends Pecas{
	private Color color;
	private int moveCount;

	public PecasXadrex(Board board, Color color) {
		super(board);
		this.color = color;
	}
	
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}

	public Color getColor() {
		return color;
	}
	public int getMoveCount() {
		return moveCount;
	}
	
	public void increaseMoveCount() {
		moveCount ++;
	}
	
	public void decreaseMoveCount() {
		moveCount --;
	}
	
	protected boolean isThereOpponetPiece(Posicao position) {
		PecasXadrex p = (PecasXadrex)getBoard().pecas(position);
		return p != null && p.getColor() != color;
	}


}
