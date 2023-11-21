package xadrex;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Pecas;
import boardgame.Posicao;
import xadrex.pecas.Bispo;
import xadrex.pecas.Cavalo;
import xadrex.pecas.King;
import xadrex.pecas.Peao;
import xadrex.pecas.Queen;
import xadrex.pecas.Rook;

public class PartidaXadrex {
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	private PecasXadrex enPassantVulnerable;
	private PecasXadrex promoted;
	private List<Pecas> piecesOnTheBoard = new ArrayList<>();
	private List<Pecas> capturedPieces = new ArrayList<>();

	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}
	
	public PecasXadrex getEnPassantVulnerable() {
		return enPassantVulnerable;
	}
	
	public PecasXadrex getPromoted() {
		return promoted;
	}
	
	public PartidaXadrex() {
		turn = 1;
		currentPlayer = Color.WHITE;
		board = new Board(8, 8);
		check = false;
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

	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Posicao position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.pecas(position).possibleMoves();
	}

	public PecasXadrex performChessMove(ChessPosition sourcePosition, ChessPosition targetPoistion) {
		Posicao source = sourcePosition.toPosition();
		Posicao target = targetPoistion.toPosition();
		validateSourcePosition(source);
		validadeTargetPosition(source, target);
		Pecas capturedPiece = makeMove(source, target);
		
		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessExcepetion("Vc nao pode se coloca em xeque");
		}
		
		PecasXadrex movePiece = ((PecasXadrex)board.pecas(target));
		
		// Movimento Especial promoted
		
		promoted = null;
		if(movePiece instanceof Peao) {
			if(movePiece.getColor() == Color.WHITE && target.getRow() == 0 || movePiece.getColor() == Color.BLACK && target.getRow() == 7 ) {
				promoted = (PecasXadrex)board.pecas(target);
				promoted = replacePromotedPiece("Q");
			}
			
		}
		
		
		check = (testCheck(oppnent(currentPlayer))) ? true : false;
		if (testCheck(oppnent(currentPlayer))) {
			checkMate = true;

		} else {
			nextTurn();
		}
		
		//Movimento Especial enPassan
		if(movePiece instanceof Peao && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
			enPassantVulnerable = movePiece;
			
		}else {
			enPassantVulnerable =  null;
		}

		return (PecasXadrex) capturedPiece;
	}
	
	public PecasXadrex replacePromotedPiece(String type) {
		if(promoted == null) {
			throw new IllegalStateException("There is no peca to be promoted");
		}
		if(!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
			return promoted;	
		}
		
		Posicao pos = promoted.getChessPosition().toPosition();
		Pecas p = board.removePiece(pos);
		piecesOnTheBoard.remove(p);
		
		PecasXadrex newPiece = newPiece(type, promoted.getColor());
		board.PlacePecas(newPiece, pos);
		piecesOnTheBoard.add(newPiece);
		return newPiece;
		
	}
	
	private PecasXadrex newPiece(String type, Color color) {
		if(type.equals("B")) return new Bispo(board, color);
		if(type.equals("B")) return new Cavalo(board, color);
		if(type.equals("B")) return new Queen(board, color);
		return new Rook(board, color);
	}

	private Pecas makeMove(Posicao source, Posicao target) {
		PecasXadrex p = (PecasXadrex) board.removePiece(source);
		p.increaseMoveCount();

		Pecas capturedPiece = board.removePiece(target);
		board.PlacePecas(p, target);

		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		// Movimento Especial King size rook
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Posicao sourceT = new Posicao(source.getRow(), source.getColumn() + 3);
			Posicao targetT = new Posicao(source.getRow(), source.getColumn() + 1);
			PecasXadrex rook = (PecasXadrex) board.removePiece(sourceT);
			board.PlacePecas(rook, targetT);
			rook.increaseMoveCount();
		}

		// Movimento Especial King QUEENSIDE rook
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Posicao sourceT = new Posicao(source.getRow(), source.getColumn() - 4);
			Posicao targetT = new Posicao(source.getRow(), source.getColumn() - 1);
			PecasXadrex rook = (PecasXadrex) board.removePiece(sourceT);
			board.PlacePecas(rook, targetT);
			rook.increaseMoveCount();
		}
		
		// Movimento Especial enPassan
		if(p instanceof Peao) {
			if(source.getColumn() != target.getColumn() && capturedPiece == null) {
				Posicao pawnPosition;
				if(p.getColor() == Color.WHITE) {
					pawnPosition = new Posicao(target.getRow() + 1, target.getColumn());
				}else {
					pawnPosition = new Posicao(target.getRow() - 1, target.getColumn());
				}
				capturedPiece = board.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
				
			}
		}
		return capturedPiece;
	}

	private void undoMove(Posicao source, Posicao target, Pecas capturedPiece) {
		PecasXadrex p = (PecasXadrex) board.removePiece(target);
		p.decreaseMoveCount();
		board.PlacePecas(p, source);
		if (capturedPiece != null) {
			board.PlacePecas(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}

		// Desfazendo Movimento Especial King size rook
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Posicao sourceT = new Posicao(source.getRow(), source.getColumn() + 3);
			Posicao targetT = new Posicao(source.getRow(), source.getColumn() + 1);
			PecasXadrex rook = (PecasXadrex) board.removePiece(targetT);
			board.PlacePecas(rook, sourceT);
			rook.decreaseMoveCount();
		}

		// Movimento Especial King QUEENSIDE rook
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Posicao sourceT = new Posicao(source.getRow(), source.getColumn() - 4);
			Posicao targetT = new Posicao(source.getRow(), source.getColumn() - 1);
			PecasXadrex rook = (PecasXadrex) board.removePiece(targetT);
			board.PlacePecas(rook, sourceT);
			rook.decreaseMoveCount();
		}
		
		// Desfazendo Movimento Especial enPassan
				if(p instanceof Peao) {
					if(source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
						PecasXadrex pawn = (PecasXadrex)board.removePiece(target);
						Posicao pawnPosition;
						if(p.getColor() == Color.WHITE) {
							pawnPosition = new Posicao(3, target.getColumn());
						}else {
							pawnPosition = new Posicao(4, target.getColumn());
						}
						board.PlacePecas(pawn, pawnPosition);
					}
				}

	}

	private void validateSourcePosition(Posicao position) {
		if (!board.therIsaPiece(position)) {
			throw new ChessExcepetion("Peca nao existe na Posicao");
		}
		if (currentPlayer != ((PecasXadrex) board.pecas(position)).getColor()) {
			throw new ChessExcepetion("A Peca Escolhida nao e Sua");
		}

		if (!board.pecas(position).isThereAnyPossibleMove()) {
			throw new ChessExcepetion("Nao existe movimento para peca Escolhida");
		}
	}

	private void validadeTargetPosition(Posicao source, Posicao target) {
		if (!board.pecas(source).possibleMove(target)) {
			throw new ChessExcepetion("A Peca de origem nao pode se mover para destino");
		}
	}

	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private Color oppnent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private PecasXadrex king(Color color) {
		List<Pecas> list = piecesOnTheBoard.stream().filter(x -> ((PecasXadrex) x).getColor() == color)
				.collect(Collectors.toList());
		for (Pecas p : list) {
			if (p instanceof King) {
				return (PecasXadrex) p;
			}
		}
		throw new IllegalStateException("There is no " + color + "King on the board");
	}

	private boolean testCheck(Color color) {
		Posicao kingPosition = king(color).getChessPosition().toPosition();
		List<Pecas> opponentPieces = piecesOnTheBoard.stream()
				.filter(x -> ((PecasXadrex) x).getColor() == oppnent(color)).collect(Collectors.toList());
		for (Pecas p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}
		List<Pecas> list = piecesOnTheBoard.stream().filter(x -> ((PecasXadrex) x).getColor() == color)
				.collect(Collectors.toList());
		for (Pecas p : list) {
			boolean[][] mat = p.possibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					if (mat[i][j]) {
						Posicao source = ((PecasXadrex) p).getChessPosition().toPosition();
						Posicao target = new Posicao(i, j);
						Pecas capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if (!testCheck) {
							return false;
						}
					}
				}
			}

		}
		return true;
	}

	private void placeNewPiece(char column, int row, PecasXadrex piece) {
		board.PlacePecas(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}

	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Cavalo(board, Color.WHITE));
		placeNewPiece('c', 1, new Bispo(board, Color.WHITE));
		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.WHITE, this));
		placeNewPiece('f', 1, new Bispo(board, Color.WHITE));
		placeNewPiece('h', 1, new Rook(board, Color.WHITE));
		placeNewPiece('g', 1, new Cavalo(board, Color.WHITE));
		placeNewPiece('a', 2, new Peao(board, Color.WHITE, this));
		placeNewPiece('b', 2, new Peao(board, Color.WHITE, this));
		placeNewPiece('c', 2, new Peao(board, Color.WHITE, this));
		placeNewPiece('d', 2, new Peao(board, Color.WHITE, this));
		placeNewPiece('e', 2, new Peao(board, Color.WHITE, this));
		placeNewPiece('f', 2, new Peao(board, Color.WHITE, this));
		placeNewPiece('g', 2, new Peao(board, Color.WHITE, this));
		placeNewPiece('h', 2, new Peao(board, Color.WHITE, this));

		placeNewPiece('a', 8, new Rook(board, Color.BLACK));
		placeNewPiece('c', 8, new Bispo(board, Color.BLACK));
		placeNewPiece('d', 8, new Queen(board, Color.BLACK));
		placeNewPiece('b', 8, new Cavalo(board, Color.BLACK));
		placeNewPiece('e', 8, new King(board, Color.BLACK, this));
		placeNewPiece('f', 8, new Bispo(board, Color.BLACK));
		placeNewPiece('g', 8, new Cavalo(board, Color.BLACK));
		placeNewPiece('h', 8, new Rook(board, Color.BLACK));
		placeNewPiece('a', 7, new Peao(board, Color.BLACK, this));
		placeNewPiece('b', 7, new Peao(board, Color.BLACK, this));
		placeNewPiece('c', 7, new Peao(board, Color.BLACK, this));
		placeNewPiece('d', 7, new Peao(board, Color.BLACK, this));
		placeNewPiece('e', 7, new Peao(board, Color.BLACK, this));
		placeNewPiece('f', 7, new Peao(board, Color.BLACK, this));
		placeNewPiece('g', 7, new Peao(board, Color.BLACK, this));
		placeNewPiece('h', 7, new Peao(board, Color.BLACK, this));

	}
}
