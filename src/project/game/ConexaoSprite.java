package project.game;

import java.awt.Color;

public class ConexaoSprite extends Sprite {
	public static final int CONEXAO_HOR = 1;
	public static final int CONEXAO_VER = 2;
		
	boolean conexaoRealizada; 
	//Método Construtor
	public ConexaoSprite(){
		super();
		conexaoRealizada = false;
		cor = Color.WHITE;			
	}
	/*----------------------------------------*/
	//Criar conexão entre pontos
	public static ConexaoSprite criarConexao(int tipo, int x, int y){
		ConexaoSprite conex = new ConexaoSprite();
		if(tipo == ConexaoSprite.CONEXAO_HOR){
			conex.largura = GameApplication.ESPACO_PONTOS;
			conex.altura = GameApplication.TAMANHO_PONTO;
		}
		else if (tipo == ConexaoSprite.CONEXAO_VER){
			conex.largura = GameApplication.TAMANHO_PONTO;
			conex.altura = GameApplication.ESPACO_PONTOS;		
		}
		else {
			return null;
		}
		conex.x = x;
		conex.y = y;
	
		conex.forma.addPoint(-conex.largura/2, -conex.altura/2);
		conex.forma.addPoint(-conex.largura/2, conex.altura/2);
		conex.forma.addPoint(conex.largura/2, conex.altura/2);
		conex.forma.addPoint(conex.largura/2, -conex.altura/2);
		
		return conex;
	}
}

