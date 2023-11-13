package boardgame;

public class Board {
	private int rows;
	private int columns;
	private Pecas[][] pecas;

	public Board(int rows, int columns) {
		if(rows < 1 || columns < 1 ) {
			throw new BoardException("Erro ao cria tabuleiro: É necessario ao menos 1 linha e 1 coluna");
		}
		this.rows = rows;
		this.columns = columns;
		pecas = new Pecas[rows][columns];
	}

	public int getRows() {
		return rows;
	}


	public int getColumns() {
		return columns;
	}

	public Pecas pecas(int rows, int columns) {
		if(!positionExists(rows, columns)) {
			throw new BoardException("Posicao não existe no Tabuleiro");
		}
		return pecas[rows][columns];
	}

	public Pecas pecas(Posicao position) {
		if(!positionExists(position)) {
			throw new BoardException("Posicao não existe no Tabuleiro");
		}
		return pecas[position.getRow()][position.getColumn()];
	}

	public void PlacePecas(Pecas peca, Posicao posicao) {
		if(therIsaPiece(posicao)) {
			throw new BoardException("Ja existe uma peca na posicao" + posicao);
		}
		pecas[posicao.getRow()][posicao.getColumn()] = peca;
		peca.position = posicao;
	}
	
	public Pecas removePiece(Posicao position) {
		if(!positionExists(position)) {
			throw new BoardException("Posição não esta no Tabuleiro");
		}
		if(pecas(position) == null) {
			return null;
		}
		Pecas aux = pecas(position);
		aux.position = null;
		
		pecas[position.getRow()][position.getColumn()] = null;
		return aux;
	}

	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}

	public boolean positionExists(Posicao posicao) {
		return positionExists(posicao.getRow(), posicao.getColumn());
	}

	public boolean therIsaPiece(Posicao posicao) {
		if(!positionExists(posicao)) {
			throw new BoardException("Posicao não existe no Tabuleiro");
		}
		return pecas(posicao) != null;
	}
	

}
