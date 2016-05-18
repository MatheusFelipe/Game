package project.game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class GameApplication extends JFrame implements MouseMotionListener, MouseListener {
	//Aqui você pode alterar algumas configurações do jogo
	public static final int NUM_PONTOS = 4;	//	Numero de pontos no lado
	public static final int ESPACO_PONTOS = 50;		//	espaço entre os pontos				
	
	public static final int TAMANHO_PONTO = 8;		//	O comprimento dos lados de cada ponto
	   
	public static final int JOGADOR_1 = 1;
	public static final int JOGADOR_2 = 2;
	//Alterar as cores dos jogadores
	public static final Color COR_JOGADOR_1 = Color.PINK;	//	Cor do jogador 1
	public static final Color COR_JOGADOR_2 = Color.BLUE;	//  Cor do jogador 2
	
	//Vetores de conexões
	private ConexaoSprite[] ConexoesHorizontais;
	private ConexaoSprite[] ConexoesVerticais;
	//Vetor de caixas
	private CaixaSprite[] caixas;
	//Vetor de pontos
	private Sprite [] pontos;
	Graphics BGrap;
	
	private Dimension dim;
	private int clickx;		//	coordenada x do click do mouse
	private int clicky;		// 	coordenada y do click do mouse
	   
	private int mousex;		// 	x: localização do mouse
	private int mousey; 	// 	y: localização do mouse
	   	
	private int centerx;	//	x: centro do gameboard
	private int centery; 	// 	y: centro do gameboard
	   	
	private int lado_jogo;
	private int espaco;	    //  comprimento de 1 ponto mais uma conexão
	
	private int jogadorAtual;
	
	public GameApplication(){
		super("PONTOS E BORDAS");
		setSize(500,600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		carregarJogo();
		carregarPontos();
		comecarJogo();
		setVisible(true);
	}
	
	private void carregarJogo(){
		clickx = clicky = mousex = mousey = 0;      
	    dim = getSize();
	    centerx = dim.width/2;
	    centery = (dim.height - 100)/2;
	       
	    lado_jogo = NUM_PONTOS * TAMANHO_PONTO + (NUM_PONTOS - 1) * ESPACO_PONTOS;	
	   	espaco = TAMANHO_PONTO + ESPACO_PONTOS;
	}
	//Carrega as conexões realizadas
	private void carregarConexoes(){
		ConexoesHorizontais = new ConexaoSprite[(NUM_PONTOS - 1)*NUM_PONTOS];
		ConexoesVerticais = new ConexaoSprite[(NUM_PONTOS - 1)*NUM_PONTOS];
		
		for(int i = 0; i< ConexoesHorizontais.length;i++){
			int colunasx = i % (NUM_PONTOS-1);
			int linhasx = i / (NUM_PONTOS-1);
			int horx = centerx - lado_jogo/2 + TAMANHO_PONTO + colunasx*espaco;
			int hory = centery - lado_jogo/2 + linhasx*espaco;
			ConexoesHorizontais[i] = ConexaoSprite.criarConexao(ConexaoSprite.CONEXAO_HOR, horx, hory);
			
			int colunasy = i % NUM_PONTOS;
			int linhasy = i / NUM_PONTOS;
			int vertx = centerx - lado_jogo/2 + colunasy*espaco;
			int verty = centery - lado_jogo/2 + TAMANHO_PONTO + linhasy*espaco;
			ConexoesVerticais[i] = ConexaoSprite.criarConexao(ConexaoSprite.CONEXAO_VER, vertx, verty);			
		}
	}
	
	private void carregarCaixas(){
		caixas = new CaixaSprite[(NUM_PONTOS-1)*(NUM_PONTOS-1)];
		for(int i=0; i< caixas.length;i++){
			int colunas = i %(NUM_PONTOS-1);
			int linhas = i / (NUM_PONTOS-1);
			
			int caixax=centerx - lado_jogo/2 + TAMANHO_PONTO + colunas*espaco;
			int caixay=centery - lado_jogo/2 + TAMANHO_PONTO + linhas*espaco;
			
			ConexaoSprite[] horConex = new ConexaoSprite[2];
			horConex[0] = ConexoesHorizontais[i];
			horConex[1] = ConexoesHorizontais[i+ (NUM_PONTOS -1)];
			
			ConexaoSprite[] verConex = new ConexaoSprite[2];
			verConex[0] = ConexoesVerticais[i + linhas];
			verConex[1] = ConexoesVerticais[i+ linhas + 1];
			
			caixas[i] = CaixaSprite.criarCaixa(caixax, caixay, horConex, verConex);			
		}
	}
	private void carregarPontos(){
		pontos = new Sprite[NUM_PONTOS*NUM_PONTOS];
		for(int linhas =0; linhas<NUM_PONTOS; linhas++)
			for(int colunas=0; colunas<NUM_PONTOS; colunas++){
				Sprite ponto = new Sprite();
				ponto.largura = TAMANHO_PONTO;
				ponto.altura = TAMANHO_PONTO;
				ponto.x = centerx - lado_jogo/2 + colunas*espaco;
				ponto.y = centery - lado_jogo/2 + linhas*espaco;
				ponto.forma.addPoint(-TAMANHO_PONTO/2, -TAMANHO_PONTO/2);
				ponto.forma.addPoint(-TAMANHO_PONTO/2, TAMANHO_PONTO/2);
				ponto.forma.addPoint(TAMANHO_PONTO/2, TAMANHO_PONTO/2);
				ponto.forma.addPoint(TAMANHO_PONTO/2, -TAMANHO_PONTO/2);
				int indice = linhas* NUM_PONTOS + colunas;
				pontos[indice] = ponto;
			}
	}
	private void comecarJogo(){
		jogadorAtual = JOGADOR_1;
		carregarConexoes();
		carregarCaixas();
	}
	
	private ConexaoSprite getConexao(int a, int b){
		for(int i=0; i<ConexoesHorizontais.length;i++)
			if(ConexoesHorizontais[i].contemPonto(a,b))
				return ConexoesHorizontais[i];
		
		for(int i=0; i<ConexoesVerticais.length;i++)
			if(ConexoesVerticais[i].contemPonto(a,b))
				return ConexoesVerticais[i];
		return null;
	}
	private boolean[] getCaixaStatus(){
		boolean[] status = new boolean[caixas.length];
		for(int i =0; i< status.length;i++)
			status[i] = caixas[i].estaFechada();
		return status;
	}
	private int[] calcularPontos() {
	   	int[] pontos = {0, 0};
	   	
	   	for(int i=0; i<caixas.length; i++) 
	   		if(caixas[i].estaFechada() && caixas[i].jogador!=0)
	   			pontos[caixas[i].jogador - 1]++;	   	
	   	return pontos;
	}

	private boolean fazerConexao(ConexaoSprite conexao) {
		boolean novaCaixa = false;
		boolean[] caixaStatusAntesConexao = getCaixaStatus();	
		conexao.conexaoRealizada = true;
		boolean[] caixaStatusDepoisConexao = getCaixaStatus();
		
		for(int i=0; i<caixas.length; i++) {
			//Verifica se uma caixa foi fechada e atribui ponto ao jogador que fechou
			if(caixaStatusDepoisConexao[i]!=caixaStatusAntesConexao[i]) {
		   			novaCaixa = true;
		   			caixas[i].jogador = jogadorAtual;
		   		}
		   	}
		   	
		   	if(!novaCaixa) {	
		   		
		   		if(jogadorAtual==JOGADOR_1){
		   			jogadorAtual = JOGADOR_2;
		   			//seleciona a cor do fundo para jogador 2
		   			BGrap.setColor(COR_JOGADOR_2);
		   		}
		   		else{ 
		   			//seleciona a cor do fundo para jogador 1
		   			jogadorAtual = JOGADOR_1;
		   			BGrap.setColor(COR_JOGADOR_1);
		   		}
		   	} 	
		   	checarFimDeJogo();
		   	return novaCaixa;
	}
	//Verifica se o jogo acabou e põe os resultados na tela
	private void checarFimDeJogo() {
		int[] pontos = calcularPontos();
	   	if((pontos[0] + pontos[1])==((NUM_PONTOS - 1) * (NUM_PONTOS - 1))) {
	   		repaint();
	   		//Cria janela para apresentar animação de fim de jogo
	   		JFrame janelaFimdeJogo = new JFrame ();
	   		janelaFimdeJogo.add(new GameOver());
	   		janelaFimdeJogo.pack();
	   		janelaFimdeJogo.setTitle("Fim de Jogo");    
	   		janelaFimdeJogo.setLocationRelativeTo(null);
	   		janelaFimdeJogo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   		janelaFimdeJogo.setVisible(true);
	
	   		setVisible(false);
	   		//Mensagem de pontuação
	   		JOptionPane.showMessageDialog(this, "Jogador 1: " + pontos[0] + "\nJogador 2: " + pontos[1], "Fim de Jogo", JOptionPane.PLAIN_MESSAGE);		
	   		setVisible(true);
	   		janelaFimdeJogo.setVisible(false);
	   		comecarJogo();
	   		repaint();
	   	}
	}
	private void handleClick() {
	   	ConexaoSprite conexao = getConexao(clickx, clicky);
	   	if(conexao == null)
	   		return;
	   	if(!conexao.conexaoRealizada) 
	   		fazerConexao(conexao);
	   	repaint();
	}	
	@Override
	public void mouseClicked(MouseEvent e) {
		clickx = e.getX();
		clicky = e.getY();
		handleClick();
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		mousex = e.getX();
		mousey = e.getY();
		repaint();
	}
	//pinta o fundo do Gameboard
	private void pintarFundo(Graphics graf) {
		if(jogadorAtual == JOGADOR_1){
			graf.setColor(COR_JOGADOR_1);
		}
		else graf.setColor(COR_JOGADOR_2);
		graf.fillRect(0, 0, dim.width, dim.height);
	}
	//Pinta os pontos
	private void pintarPontos(Graphics graf) {
		for(int i=0; i<pontos.length; i++)
			pontos[i].render(graf);
	}
	private void pintarConexoes(Graphics graf) {
		for(int i=0; i<ConexoesHorizontais.length; i++) {
			if(!ConexoesHorizontais[i].conexaoRealizada) {
				if(ConexoesHorizontais[i].contemPonto(mousex, mousey))
					ConexoesHorizontais[i].cor = Color.RED;
				else ConexoesHorizontais[i].cor = Color.WHITE;
		   	} 
			else {
				ConexoesHorizontais[i].cor = Color.BLUE;
			}	
			ConexoesHorizontais[i].render(graf);
		}
		for(int i=0; i<ConexoesVerticais.length; i++) {
			if(!ConexoesVerticais[i].conexaoRealizada) {
				if(ConexoesVerticais[i].contemPonto(mousex, mousey))
					ConexoesVerticais[i].cor = Color.RED;
				else ConexoesVerticais[i].cor = Color.WHITE;
		   	} 
			else {
				ConexoesVerticais[i].cor = Color.BLUE;
			}
			ConexoesVerticais[i].render(graf);
		}
	}
	//pinta as caixas de acordo com a cor do jogador que fechou
	public void pintarCaixas(Graphics graf) {
		for(int i=0; i<caixas.length; i++) {
			if(caixas[i].estaFechada()) {
		   		if(caixas[i].jogador == JOGADOR_1) {
		   			caixas[i].cor = COR_JOGADOR_1;
		   		}else if(caixas[i].jogador == JOGADOR_2) {
		   			caixas[i].cor = COR_JOGADOR_2;
		   		}
		   	}else {
		   		caixas[i].cor = Color.WHITE;
		   	}
		   	caixas[i].render(graf);
		}
	}
	
	public void pintarStatus(Graphics graf) {
		int[] pontos = calcularPontos();
		String status= "Vez do jogador " + jogadorAtual;
		String status2= "Jogador 1: " + pontos[0];
		String status3= "Jogador 2: " + pontos[1];
		   	
		graf.setColor(Color.BLACK);
		graf.drawString(status, 20, dim.height-50);
		   	
		graf.setColor(Color.BLACK);
		graf.drawString(status2, 20, dim.height-35);
		   	
		graf.setColor(Color.BLACK);
		graf.drawString(status3, 20, dim.height-20);
	}
	public void update(Graphics graf) {
		paint(graf);
	}
	
	public void paint(Graphics graf) {
	   	
	   	Image bufferImage = createImage(dim.width, dim.height);
	   	BGrap = bufferImage.getGraphics();
	   	
	   	pintarFundo(BGrap);    	
	    pintarPontos(BGrap);   	
	   	pintarConexoes(BGrap);
	   	pintarCaixas(BGrap);
	   	pintarStatus(BGrap);
	   	
	   	graf.drawImage(bufferImage, 0, 0, null);
	}
	public static void main(String[] args) {
		new GameApplication();
	} 	
 }