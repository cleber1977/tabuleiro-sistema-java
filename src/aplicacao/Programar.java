package aplicacao;

import boardgame.Board;
import boardgame.Posicao;
import xadrex.PartidaXadrex;

public class Programar {

	public static void main(String[] args) {
		PartidaXadrex partidaxadex = new PartidaXadrex();
		UI.printBoard(partidaxadex.getPecasXadrexs());
	}

}
