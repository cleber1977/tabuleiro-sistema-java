package boardgame;

public class Pecas {
	protected Posicao position;
	private Board board;
	
	public Pecas(Board board) {
		this.board = board;
		position = null;
	}

	protected Board getBoard() {
		return board;
	}

}
