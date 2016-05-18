package project.game;

import java.awt.Color;

public class CaixaSprite extends Sprite{
	ConexaoSprite [] ConexoesHorizontais; //bordas de cima e de baixp
	ConexaoSprite [] ConexoesVerticais; // bordas laterais
	
	int jogador; //jogador que ganhou a caixa
	
	public CaixaSprite(){
		super();
		cor = Color.WHITE;
		ConexoesHorizontais = new ConexaoSprite[2];
		ConexoesVerticais = new ConexaoSprite[2];
		largura = GameApplication.ESPACO_PONTOS;
		altura = GameApplication.ESPACO_PONTOS;
		
		forma.addPoint(-largura/2, -altura/2);
		forma.addPoint(-largura/2, altura/2);
		forma.addPoint(largura/2, altura/2);
		forma.addPoint(largura/2, -altura/2);
	}
	//Verifica se a caixa tem as quatro bordas
	public boolean estaFechada(){
		boolean fechada = true;
		for(int i =0; i< 2; i++){
			if(!ConexoesHorizontais[i].conexaoRealizada||!ConexoesVerticais[i].conexaoRealizada)
				fechada = false;
		}
		return fechada;
	}
	public static CaixaSprite criarCaixa(int x, int y, ConexaoSprite[] ConexoesHorizontais, ConexaoSprite[] ConexoesVerticais){
		CaixaSprite caixa = new CaixaSprite();
		caixa.jogador = 0;
		caixa.x = x;
		caixa.y = y;
		caixa.ConexoesHorizontais = ConexoesHorizontais;
		caixa.ConexoesVerticais = ConexoesVerticais;
		return caixa;
	}
}
