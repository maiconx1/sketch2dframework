package com.imobilis.sketch2dframework;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import java.util.ArrayList;

/**
 * Criado por Maicon em 25/04/2016.
 */
public class Sketch2D extends AppCompatActivity
{
	private static Singleton instance = Singleton.getInstance();

	public static float distanciaParaLinha = 200;
	public static int corDistancia = Color.BLACK, unidade = Sketch2D.UNIDADE_DP;



	public static final int TIPO_TRIANGULO = 0, TIPO_QUADRADO = 1, TIPO_PENTAGONO = 2, TIPO_HEXAGONO = 3;
	public static final int UNIDADE_DP = 0, UNIDADE_CM = 1, UNIDADE_M = 2, UNIDADE_KM = 3, UNIDADE_INCH = 4;
	public static final int EXCLUI = 0, NAO_EXCLUI = 1;


	/**
	 * Desenha uma linha padrão no framelayout escolhido. A linha vai do ponto (50, 50) ao ponto (300, 300).
	 * A linha é adicionada a um array de figuras do framework, acessível pelo método Sketch2D.getFiguras()
	 * A linha é desenhada com configurações padrão (cor preta, não pontilhado, anti alias ativado, tamanho = 1).
	 *
	 * @param activity Activity pai do FrameLayout onde a linha será desenhada.
	 * @param layout FrameLayout onde a linha será desenhada.
	 * @param editavel Define se a linha será editável/excluível ou não.
	 * @return retorna a linha criada.
	 */
	public static Linha desenhaLinha(Activity activity, FrameLayout layout, boolean editavel)
	{
		Linha linha = new Linha(activity, new ArrayList<Point>(){{add(new Point(50, 50)); add(new Point(300, 300));}}, editavel);
		Sketch2D.instance.addFigura(linha);
		linha.desenha(layout, linha);
		return linha;
	}

	/**
	 * Desenha uma linha no framelayout escolhido. A linha vai do primeiro ao segundo ponto do ArrayList de pontos.
	 * A linha é adicionada a um array de figuras do framework, acessível pelo método Sketch2D.getFiguras()
	 * A linha é desenhada com configurações padrão (cor preta, não pontilhado, anti alias ativado, tamanho = 1).
	 *
	 * @param activity Activity pai do FrameLayout onde a linha será desenhada.
	 * @param layout FrameLayout onde a linha será desenhada.
	 * @param pontos ArrayList com os pontos inicial e final da linha.
	 * @param editavel Define se a linha será editável/excluível ou não.
	 * @return retorna a linha criada.
	 */
	public static Linha desenhaLinha(Activity activity, FrameLayout layout, ArrayList<Point> pontos, boolean editavel)
	{
		Linha linha = new Linha(activity, pontos, editavel);
		Sketch2D.instance.addFigura(linha);
		linha.desenha(layout, linha);
		return linha;
	}

	/**
	 * Desenha uma linha no framelayout escolhido. A linha vai do primeiro ao segundo ponto do ArrayList de pontos.
	 * A linha é adicionada a um array de figuras do framework, acessível pelo método Sketch2D.getFiguras()
	 *
	 * @param activity Activity pai do FrameLayout onde a linha será desenhada.
	 * @param layout FrameLayout onde a linha será desenhada.
	 * @param pontos ArrayList com os pontos inicial e final da linha.
	 * @param editavel Define se a linha será editável/excluível ou não.
	 * @param configuracoes Define as configurações referentes a linha.
	 * @return retorna a linha criada.
	 */
	public static Linha desenhaLinha(Activity activity, FrameLayout layout, ArrayList<Point> pontos, boolean editavel, Configuracoes configuracoes)
	{
		Linha linha = new Linha(activity, pontos, editavel, configuracoes);
		Sketch2D.instance.addFigura(linha);
		linha.desenha(layout, linha);
		return linha;
	}

	/**
	 * Desenha uma linha no framelayout escolhido. A linha vai do primeiro ao segundo ponto do ArrayList de pontos.
	 * A linha é adicionada a um array de figuras do framework, acessível pelo método Sketch2D.getFiguras()
	 *
	 * @param activity Activity pai do FrameLayout onde a linha será desenhada.
	 * @param layout FrameLayout onde a linha será desenhada.
	 * @param pontos ArrayList com os pontos inicial e final da linha.
	 * @param editavel Define se a linha será editável/excluível ou não.
	 * @param configuracoes Define as configurações referentes a linha.
	 * @param distancia Define se a linha é uma linha de distância ou não
	 * @return retorna a linha criada.
	 */
	protected static Linha desenhaLinha(Activity activity, FrameLayout layout, ArrayList<Point> pontos, boolean editavel, Configuracoes configuracoes, boolean distancia)
	{
		Linha linha = new Linha(activity, pontos, editavel, configuracoes, distancia);
		Sketch2D.instance.addFigura(linha);
		linha.desenha(layout, linha);
		return linha;
	}

	/**
	 * Desenha um círculo padrão no framelayout escolhido. O círculo centra no ponto (50, 50) e possui raio 50.
	 * O círculo é adicionado a um array de figuras do framework, acessível pelo método Sketch2D.getFiguras()
	 * O círculo é desenhado com configurações padrão (cor preta, preenchido, anti alias ativado).
	 *
	 * @param activity Activity pai do FrameLayout onde o círculo será desenhado.
	 * @param layout FrameLayout onde o círculo será desenhado.
	 * @param editavel Define se o círculo será editável/excluível ou não.
	 * @return retorna o círculo criado.
	 */
	public static Circulo desenhaCirculo(Activity activity, FrameLayout layout, boolean editavel)
	{
		Circulo circulo = new Circulo(activity, new ArrayList<Point>(){{add(new Point(50, 50));}}, 50, editavel);
		Sketch2D.instance.addFigura(circulo);
		Figura.desenha(activity, layout, circulo);
		return circulo;
	}

	/**
	 * Desenha um círculo padrão no framelayout escolhido. O círculo centra no ponto (50, 50) e possui raio 50.
	 * O círculo é adicionado a um array de figuras do framework, acessível pelo método Sketch2D.getFiguras()
	 *
	 * @param activity Activity pai do FrameLayout onde o círculo será desenhado.
	 * @param layout FrameLayout onde o círculo será desenhado.
	 * @param editavel Define se o círculo será editável/excluível ou não.
	 * @param configuracoes Define as configurações referentes ao círculo.
	 * @return retorna o círculo criado.
	 */
	public static Circulo desenhaCirculo(Activity activity, FrameLayout layout, boolean editavel, Configuracoes configuracoes)
	{
		Circulo circulo = new Circulo(activity, new ArrayList<Point>(){{add(new Point(50, 50));}}, 50, editavel, configuracoes);
		Sketch2D.instance.addFigura(circulo);
		Figura.desenha(activity, layout, circulo);
		return circulo;
	}

	/**
	 * Desenha um círculo no framelayout escolhido. O círculo é centrado no primeiro ponto do Array de pontos.
	 * O círculo é adicionado a um array de figuras do framework, acessível pelo método Sketch2D.getFiguras()
	 * O círculo é desenhado com configurações padrão (cor preta, preenchido, anti alias ativado).
	 *
	 * @param activity Activity pai do FrameLayout onde o círculo será desenhado.
	 * @param layout FrameLayout onde o círculo será desenhado.
	 * @param ponto Point com o ponto de centro do círculo.
	 * @param raio Raio float do círculo
	 * @param editavel Define se o círculo será editável/excluível ou não.
	 * @return retorna o círculo criado.
	 */
	public static Circulo desenhaCirculo(Activity activity, FrameLayout layout, Point ponto, float raio, boolean editavel)
	{
		ArrayList<Point> p = new ArrayList<>();
		p.add(new Point(ponto.x, ponto.y));
		Circulo circulo = new Circulo(activity, p, raio, editavel);
		Sketch2D.instance.addFigura(circulo);
		circulo.desenha(layout, circulo);
		return circulo;
	}

	/**
	 * Desenha um círculo no framelayout escolhido. O círculo é centrado no primeiro ponto do Array de pontos.
	 * O círculo é adicionado a um array de figuras do framework, acessível pelo método Sketch2D.getFiguras()
	 *
	 * @param activity Activity pai do FrameLayout onde o círculo será desenhado.
	 * @param layout FrameLayout onde o círculo será desenhado.
	 * @param ponto Point com o ponto de centro do círculo.
	 * @param raio Raio float do círculo
	 * @param editavel Define se o círculo será editável/excluível ou não.
	 * @param configuracoes Define as configurações referentes ao círculo.
	 * @return retorna o círculo criado.
	 */
	public static Circulo desenhaCirculo(Activity activity, FrameLayout layout, Point ponto, float raio, boolean editavel, Configuracoes configuracoes)
	{
		ArrayList<Point> p = new ArrayList<>();
		p.add(new Point(ponto.x, ponto.y));
		Circulo circulo = new Circulo(activity, p, raio, editavel, configuracoes);
		Sketch2D.instance.addFigura(circulo);
		circulo.desenha(layout, circulo);
		return circulo;
	}

	/**
	 * Desenha um polígono padrão de lados iguais no framelayout escolhido, de acordo com o tipo escolhido.
	 * O polígono é adicionado a um array de figuras do framework, acessível pelo método Sketch2D.getFiguras()
	 * O polígono é desenhado com configurações padrão (cor preta, preenchido, anti alias ativado).
	 *
	 * @param activity Activity pai do FrameLayout onde o polígono será desenhado.
	 * @param layout FrameLayout onde o polígono será desenhado.
	 * @param tipo tipo de polígono padrão. Pode ser Triângulo(Sketch2D.TIPO_TRIANGULO) ou Quadrado (Sketch2D.TIPO_QUADRADO).
	 * @param editavel Define se o polígono será editável/excluível ou não.
	 * @return retorna o polígono criado.
	 */
	public static Poligono desenhaPoligono(Activity activity, FrameLayout layout, int tipo, boolean editavel)
	{
		ArrayList<Point> pontos = new ArrayList<>();
		switch(tipo)
		{
			case TIPO_TRIANGULO:
				pontos.add(new Point(0, 100));
				pontos.add(new Point(50, 0));
				pontos.add(new Point(100, 100));
				break;
			case TIPO_QUADRADO:
				pontos.add(new Point(0, 0));
				pontos.add(new Point(100, 0));
				pontos.add(new Point(100, 100));
				pontos.add(new Point(0, 100));
				break;
			case TIPO_PENTAGONO:
				/*pontos.add(new Point(0, 0));
				pontos.add(new Point(50, 0));
				pontos.add(new Point(50, 50));
				pontos.add(new Point(0, 50));
				pontos.add(new Point(0, 50));*/
				break;
			case TIPO_HEXAGONO:
				/*pontos.add(new Point(0, 0));
				pontos.add(new Point(50, 0));
				pontos.add(new Point(50, 50));
				pontos.add(new Point(0, 50));
				pontos.add(new Point(0, 50));
				pontos.add(new Point(0, 50));*/
				break;
			default:
				break;
		}
		Poligono poligono = new Poligono(activity, pontos, editavel);
		Sketch2D.instance.addFigura(poligono);
		poligono.desenha(layout, poligono);
		return poligono;
	}

	/**
	 * Desenha um polígono de lados iguais no framelayout escolhido, de acordo com o tipo escolhido.
	 * O polígono é adicionado a um array de figuras do framework, acessível pelo método Sketch2D.getFiguras()
	 *
	 * @param activity Activity pai do FrameLayout onde o polígono será desenhado.
	 * @param layout FrameLayout onde o polígono será desenhado.
	 * @param tipo tipo de polígono padrão. Pode ser Triângulo(Sketch2D.TIPO_TRIANGULO) ou Quadrado (Sketch2D.TIPO_QUADRADO).
	 * @param editavel Define se o polígono será editável/excluível ou não.
	 * @param configuracoes Define as configurações referentes ao polígono.
	 * @return retorna o polígono criado.
	 */
	public static Poligono desenhaPoligono(Activity activity, FrameLayout layout, int tipo, boolean editavel, Configuracoes configuracoes)
	{
		ArrayList<Point> pontos = new ArrayList<>();
		switch(tipo)
		{
			case TIPO_TRIANGULO:
				pontos.add(new Point(0, 100));
				pontos.add(new Point(50, 0));
				pontos.add(new Point(100, 100));
				break;
			case TIPO_QUADRADO:
				pontos.add(new Point(0, 0));
				pontos.add(new Point(100, 0));
				pontos.add(new Point(100, 100));
				pontos.add(new Point(0, 100));
				break;
			case TIPO_PENTAGONO:
				/*pontos.add(new Point(0, 0));
				pontos.add(new Point(50, 0));
				pontos.add(new Point(50, 50));
				pontos.add(new Point(0, 50));
				pontos.add(new Point(0, 50));*/
				break;
			case TIPO_HEXAGONO:
				/*pontos.add(new Point(0, 0));
				pontos.add(new Point(50, 0));
				pontos.add(new Point(50, 50));
				pontos.add(new Point(0, 50));
				pontos.add(new Point(0, 50));
				pontos.add(new Point(0, 50));*/
				break;
			default:
				break;
		}
		Poligono poligono = new Poligono(activity, pontos, editavel, configuracoes);
		Sketch2D.instance.addFigura(poligono);
		poligono.desenha(layout, poligono);
		return poligono;
	}

	/**
	 * Desenha um polígono no framelayout escolhido com os vértices nos pontos escolhidos.
	 * O polígono é adicionado a um array de figuras do framework, acessível pelo método Sketch2D.getFiguras()
	 *
	 * @param activity Activity pai do FrameLayout onde o polígono será desenhado.
	 * @param layout FrameLayout onde o polígono será desenhado.
	 * @param pontos Array com os pontos de vértice do polígono. O último ponto não precisa ser o primeiro.
	 * @param editavel Define se o polígono será editável/excluível ou não.
	 * @return retorna o polígono criado.
	 */
	public static Figura desenhaPoligono(Activity activity, FrameLayout layout, ArrayList<Point> pontos, boolean editavel)
	{
		Figura poligono;
		if(pontos.size() == 2)
		{
			poligono = new Linha(activity, pontos, editavel);
		}
		else
		{
			poligono = new Poligono(activity, pontos, editavel);
		}
		Sketch2D.instance.addFigura(poligono);
		poligono.desenha(layout, poligono);
		return poligono;
	}

	/**
	 * Desenha um polígono no framelayout escolhido com os vértices nos pontos escolhidos.
	 * O polígono é adicionado a um array de figuras do framework, acessível pelo método Sketch2D.getFiguras()
	 *
	 * @param activity Activity pai do FrameLayout onde o polígono será desenhado.
	 * @param layout FrameLayout onde o polígono será desenhado.
	 * @param pontos Array com os pontos de vértice do polígono. O último ponto não precisa ser o primeiro.
	 * @param editavel Define se o polígono será editável/excluível ou não.
	 * @param configuracoes Define as configurações referentes ao polígono.
	 * @return retorna o polígono criado.
	 */
	public static Figura desenhaPoligono(Activity activity, FrameLayout layout, ArrayList<Point> pontos, boolean editavel, Configuracoes configuracoes)
	{
		//Poligono poligono = new Poligono(activity, pontos, editavel, configuracoes);
		Figura poligono;
		if(pontos.size() == 2)
		{
			poligono = new Linha(activity, pontos, editavel, configuracoes);
		}
		else
		{
			poligono = new Poligono(activity, pontos, editavel, configuracoes);
		}
		Sketch2D.instance.addFigura(poligono);
		poligono.desenha(layout, poligono);
		return poligono;
	}

	/**
	 * Desenha as figuras presentes no Array de figuras. Elas não são adicionadas ao array de figuras do framework.
	 * @param activity Activity pai do FrameLayout onde as figuras serão desenhadas.
	 * @param figuras Array de figuras contendo as figuras a serem desenhadas.
	 * @param layout FrameLayout onde as figuras serão desenhadas.
	 */
	public static void desenhaFiguras(Activity activity, ArrayList<Figura> figuras, FrameLayout layout)
	{
		for(Figura f : figuras)
		{
			f.desenha(layout, f);
		}
	}

	/**
	 * Desenha a figura passada como parâmetro. Ela é adicionada ao array de figuras do framework.
	 * @param activity Activity pai do FrameLayout onde a figura será desenhada.
	 * @param figura Figura contendo as informações a serem desenhadas.
	 * @param layout FrameLayout onde a figura será desenhada.
	 */
	public static void desenhaFigura(Activity activity, Figura figura, FrameLayout layout)
	{
		figura.desenha(layout, figura);
		Sketch2D.instance.addFigura(figura);
	}

	/**
	 * Pega o array de figuras do framework.
	 * @return Array de figuras do framework.
	 */
	public static ArrayList<Figura> getFiguras()
	{
		return instance.getFiguras();
	}

	/**
	 * Exclui todas as figuras do array de figuras do framework.
	 * @param opcao decide se exclui as views criadas ou não. Pode ser Sketch2D.EXCLUI ou Sketch2D.NAO_EXCLUI.
	 */
	public static void limpaFiguras(int opcao)
	{
		switch(opcao)
		{
			case Sketch2D.EXCLUI:
				while(getFiguras().size() > 0)
				{
					Sketch2D.removeDesenho(getFiguras().size() - 1);
				}
				break;
			default:
			case Sketch2D.NAO_EXCLUI:
				Singleton.getInstance().getFiguras().clear();
				break;
		}
	}

	/**
	 * Exclui a figura na posição index do array de figuras do framework e deleta a view.
	 * @param index posição da figura no array de figuras do framework.
	 */
	public static void removeDesenho(int index)
	{
		((ViewGroup) instance.getFigura(index).getView().getParent()).removeView(instance.getFigura(index).getView());
		if(index > -1 && index < instance.getFiguras().size())
		{
			instance.getFiguras().remove(index);
		}
		for(int i = 0;i<instance.getFiguras().size();i++)
		{
			instance.getFiguras().get(i).setIndex(i);
		}
	}

	public static void removeDesenho(Figura f)
	{
		((ViewGroup) f.getView().getParent()).removeView(f.getView());
		instance.getFiguras().remove(f);
		for(int i = 0;i<instance.getFiguras().size();i++)
		{
			instance.getFiguras().get(i).setIndex(i);
		}
	}

	public static void setClasseConfiguracao(Class classeConfiguracao)
	{
		Figura.setClasseConfiguracao(classeConfiguracao);
	}

	public static void setAlphaPadrao(int alphaPadrao)
	{
		Configuracoes.setAlphaPadrao(alphaPadrao);
	}

	public static void setTamLinhaPadrao(int tamLinhaPadrao)
	{
		Configuracoes.setTamLinhaPadrao(tamLinhaPadrao);
	}

	public static void setEstiloPadrao(int estiloPadrao)
	{
		Configuracoes.setEstiloPadrao(estiloPadrao);
	}

	public static void setEscalaPadrao(float escalaPadrao)
	{
		Configuracoes.setEscalaPadrao(escalaPadrao);
	}

	public static void setPontilhadoPadrao(boolean pontilhadoPadrao)
	{
		Configuracoes.setPontilhadoPadrao(pontilhadoPadrao);
	}

	public static void setAntiAliasPadrao(boolean antiAliasPadrao)
	{
		Configuracoes.setAntiAliasPadrao(antiAliasPadrao);
	}

	public static void setCorPadrao(int corPadrao)
	{
		Configuracoes.setCorPadrao(corPadrao);
	}

	public static void setDistanciaParaLinha(float distanciaParaLinha)
	{
		Sketch2D.distanciaParaLinha = distanciaParaLinha;
	}

	public static void setCorDistancia(int corDistancia)
	{
		Sketch2D.corDistancia = corDistancia;
	}

	public static void setUnidade(int unidade)
	{
		Sketch2D.unidade = unidade;
	}
}
