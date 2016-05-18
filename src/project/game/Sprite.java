package project.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Sprite {
	Polygon forma;
	Color cor;
	int largura; //do Sprite
	int altura; //do Sprite
	int x; //coordenada horizontal do centro do Sprite
	int y; //coordenada vertical do centro do Sprite
	
	/*--------------------------------------------*/
	//Método construtor
	public Sprite (){
		forma = new Polygon();
		largura = altura = x = y = 0;
		cor = Color.BLACK;
	}
	/*--------------------------------------------*/
	//Retorna true se o ponto (a,b) está contido no Sprite
	public boolean contemPonto (int x, int y){
		return forma.contains(x-this.x-largura/2,y-this.y-altura/2); 
	}
	/*--------------------------------------------*/
	//Posiciona o Sprite
	public void render(Graphics g){
		g.setColor(cor);
		Polygon formaRenderizada = new Polygon ();
		for(int i = 0; i < forma.npoints; i++)
		{
			int renderedx = forma.xpoints[i]+x+largura/2;
	        int renderedy=forma.ypoints[i]+y+altura/2;
	        formaRenderizada.addPoint(renderedx, renderedy);
		}
		g.fillPolygon(formaRenderizada);
	}	
}
