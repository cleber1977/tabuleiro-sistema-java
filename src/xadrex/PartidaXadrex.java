package xadrex;

import boardgame.Board;
import boardgame.Pecas;
import boardgame.Posicao;
import xadrex.pecas.King;
import xadrex.pecas.Rook;

public class PartidaXadrex {
	private Board board;

	public PartidaXadrex() {
		board = new Board(8, 8);
		initialSetup();
	}

	public PecasXadrex[][] getPecasXadrexs() {
		PecasXadrex[][] mat = new PecasXadrex[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (PecasXadrex) board.pecas(i, j);
			}
		}
		return mat;
	}

	public PecasXadrex performChessMove(ChessPosition sourcePosition, ChessPosition targetPoistion) {
		Posicao source = sourcePosition.toPosition();
		Posicao target = targetPoistion.toPosition();
		validateSourcePosition(source);
		Pecas capturedPiece = makeMove(source, target);
		return (PecasXadrex) capturedPiece;
	}
	
	private Pecas makeMove(Posicao source, Posicao target) {
		Pecas p = board.removePiece(source);
		Pecas capturedPiece = board.removePiece(target);
		board.PlacePecas(p, target);
		return capturedPiece;
	}

	private void validateSourcePosition(Posicao position) {
		if (!board.therIsaPiece(position)) {
			throw new ChessExcepetion("Peca não existe na Posicao");
		}
		if(!board.pecas(position).isThereAnyPossibleMove()) {
			throw new ChessExcepetion("Não existe movimento para peca Escolhida");
		}
	}

	private void placeNewPiece(char column, int row, PecasXadrex piece) {
		board.PlacePecas(piece, new ChessPosition(column, row).toPosition());
	}

	private void initialSetup() {

		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
		placeNewPiece('c', 2, new Rook(board, Color.WHITE));
		placeNewPiece('d', 2, new Rook(board, Color.WHITE));
		placeNewPiece('e', 2, new Rook(board, Color.WHITE));
		placeNewPiece('e', 1, new Rook(board, Color.WHITE));
		placeNewPiece('d', 1, new King(board, Color.WHITE));

		placeNewPiece('c', 7, new Rook(board, Color.BLACK));
		placeNewPiece('c', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
