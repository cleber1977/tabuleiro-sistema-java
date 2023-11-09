package boardgame;

public class Board {
	private int rows;
	private int columns;
	private Pecas[][] pecas;
	
	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		pecas = new Pecas[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public Pecas pecas (int rows, int columns) {
		return pecas[rows][columns];
	}
	public Pecas pecas(Posicao position) {
		return pecas[position.getRow()][position.getColumn()];
	}
	

}
