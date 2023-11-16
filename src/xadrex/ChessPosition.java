package xadrex;

import boardgame.Posicao;

public class ChessPosition {
	private char colunm;
	private int row;
	public ChessPosition(char colunm, int row) {
		if(colunm < 'a' || colunm > 'h' || row < 1 || row > 8) {
			throw new ChessExcepetion("Erro Instanciando Posicao no Xadrex: Valido de a1 ate h8");
		}
		this.colunm = colunm;
		this.row = row;
	}
	public char getColunm() {
		return colunm;
	}
	public int getRow() {
		return row;
	}
	
	protected Posicao toPosition() {
		return new Posicao(8 - row,  colunm - 'a' );
	}
	
	protected static ChessPosition fromPosition(Posicao position) {
		return new ChessPosition((char)('a' + position.getColumn()), 8 - position.getRow());
	}
	
	@Override
	public String toString() {
		return "" + colunm + row;
	}
}
