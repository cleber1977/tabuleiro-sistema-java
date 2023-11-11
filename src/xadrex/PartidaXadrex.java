package xadrex;

import boardgame.Board;
import boardgame.Posicao;
import xadrex.pecas.King;
import xadrex.pecas.Rook;

public class PartidaXadrex {
	private Board board;
	
	public PartidaXadrex() {
		board = new Board(8, 8);
		initialSetup();
	}
	
	public PecasXadrex[][] getPecasXadrexs(){
		PecasXadrex[][] mat = new PecasXadrex[board.getRows()][board.getColumns()];
		for(int i=0; i<board.getRows(); i++) {
			for(int j=0; j<board.getColumns(); j++) {
				mat[i][j] = (PecasXadrex) board.pecas(i,j);
			}
		}
		return mat;
	}
	private void initialSetup() {
		
		board.PlacePecas(new Rook(board, Color.WHITE), new Posicao(2, 1));
		board.PlacePecas(new King(board, Color.BLACK), new Posicao(0, 4));
		board.PlacePecas(new King(board, Color.BLACK), new Posicao(7, 4));
	}
}
