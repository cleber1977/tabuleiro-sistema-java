package xadrex;

import boardgame.Board;

public class PartidaXadrex {
	private Board board;
	
	public PartidaXadrex() {
		board = new Board(8, 8);
	}
	
	public PecasXadrex[][] getPecasXadrexs(){
		PecasXadrex[][] mat = new PecasXadrex[board.getRows()][board.getColumns()];
		for(int i=0; i<board.getRows(); i++) {
			for(int j=0; j<board.getColumns(); j++) {
				mat[1][j] = (PecasXadrex) board.pecas(i,j);
			}
		}
		return mat;
	}
}
