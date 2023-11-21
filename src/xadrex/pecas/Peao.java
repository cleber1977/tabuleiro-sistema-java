package xadrex.pecas;

import boardgame.Board;
import boardgame.Posicao;
import xadrex.Color;
import xadrex.PartidaXadrex;
import xadrex.PecasXadrex;

public class Peao extends PecasXadrex {
	private PartidaXadrex partidaXadrex;
	

	public Peao(Board board, Color color, PartidaXadrex partidaXadrex) {
		super(board, color);
		this.partidaXadrex = partidaXadrex;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Posicao p = new Posicao(0, 0);

		if (getColor() == Color.WHITE) {
			p.setValues(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().therIsaPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(position.getRow() - 2, position.getColumn());
			Posicao p2 = new Posicao(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().therIsaPiece(p) && getBoard().positionExists(p2)
					&& !getBoard().therIsaPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValues(position.getRow() - 1, position.getColumn() - 1);
			if (getBoard().positionExists(p) && isThereOpponetPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValues(position.getRow() - 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && isThereOpponetPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			// Movimento Espeical enPassan White
			if (position.getRow() == 3) {
				Posicao left = new Posicao(position.getRow(), position.getColumn() - 1);
				if (getBoard().positionExists(left) && isThereOpponetPiece(left)
						&& getBoard().pecas(left) == partidaXadrex.getEnPassantVulnerable()) {
					mat[left.getRow() - 1][left.getColumn()] = true;
				}

				Posicao right = new Posicao(position.getRow(), position.getColumn() + 1);
				if (getBoard().positionExists(right) && isThereOpponetPiece(right) && getBoard().pecas(right) == partidaXadrex.getEnPassantVulnerable()) {
					mat[right.getRow() - 1][right.getColumn()] = true;
				}

			}

		} else {
			p.setValues(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().therIsaPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(position.getRow() + 2, position.getColumn());
			Posicao p2 = new Posicao(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().therIsaPiece(p) && getBoard().positionExists(p2) && !getBoard().therIsaPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValues(position.getRow() + 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && isThereOpponetPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValues(position.getRow() + 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && isThereOpponetPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			// Movimento Espeical enPassan Black
			if (position.getRow() == 4) {
				Posicao left = new Posicao(position.getRow(), position.getColumn() - 1);
				if (getBoard().positionExists(left) && isThereOpponetPiece(left) && getBoard().pecas(left) == partidaXadrex.getEnPassantVulnerable()) {
					mat[left.getRow() + 1][left.getColumn()] = true;
				}

				Posicao right = new Posicao(position.getRow(), position.getColumn() + 1);
				if (getBoard().positionExists(right) && isThereOpponetPiece(right) && getBoard().pecas(right) == partidaXadrex.getEnPassantVulnerable()) {
					mat[right.getRow() + 1][right.getColumn()] = true;
				}

			}
		}
		return mat;
	}

	@Override
	public String toString() {
		return "P";
	}
}
