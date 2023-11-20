package xadrex.pecas;

import boardgame.Board;
import boardgame.Posicao;
import xadrex.Color;
import xadrex.PartidaXadrex;
import xadrex.PecasXadrex;

public class King extends PecasXadrex {
	
	private PartidaXadrex partidaXadrex;
	
	public King(Board board, Color color, PartidaXadrex partidaXadrex) {
		super(board, color);
		this.partidaXadrex = partidaXadrex;
	}

	public String toString() {
		return "K";
	}

	private boolean canMove(Posicao position) {
		PecasXadrex p = (PecasXadrex) getBoard().pecas(position);
		return p == null || p.getColor() != getColor();
	}
	
	private boolean testRookCastling(Posicao position) {
		PecasXadrex p = (PecasXadrex)getBoard().pecas(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Posicao p = new Posicao(0, 0);
		// Acima
		p.setValues(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// abaixo
		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// direita
		p.setValues(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// esquerda
		p.setValues(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// nw
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// ne
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// sw
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// sudeste
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Movimento Especial
		if(getMoveCount() == 0 && !partidaXadrex.getCheck()) {
			// Movimento especial Rei Rook
			Posicao posT1 = new Posicao(position.getRow(), position.getColumn() + 3);
			if(testRookCastling(posT1)) {
				Posicao p1 = new Posicao(position.getRow(), position.getColumn() + 1);
				Posicao p2 = new Posicao(position.getRow(), position.getColumn() + 2);
				if(getBoard().pecas(p1)== null && getBoard().pecas(p2) == null) {
					mat[position.getRow()][position.getColumn() + 2] = true;
				}
			}
			
			// Movimento especial Rei Rook grande
			Posicao posT2 = new Posicao(position.getRow(), position.getColumn() -4);
			if(testRookCastling(posT2)) {
				Posicao p1 = new Posicao(position.getRow(), position.getColumn() - 1);
				Posicao p2 = new Posicao(position.getRow(), position.getColumn() - 2);
				Posicao p3 = new Posicao(position.getRow(), position.getColumn() - 3);
				if(getBoard().pecas(p1)== null && getBoard().pecas(p2) == null && getBoard().pecas(p3) == null) {
					mat[position.getRow()][position.getColumn() - 2] = true;
				}
			}
		}
		return mat;
	}

}
