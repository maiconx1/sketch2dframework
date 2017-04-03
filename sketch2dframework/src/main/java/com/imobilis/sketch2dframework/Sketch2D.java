package com.imobilis.sketch2dframework;

import android.app.Activity;
import android.content.Context;
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
	public static int tamanhoTextoAngulo=15;
	public static float distanciaParaLinha = 200;
	public static int corDistancia = Color.BLACK, unidade = Sketch2D.UNIDADE_DP;
	public static CommandManager commandManager;
	public static Context context;



	public static final int TIPO_TRIANGULO = 0, TIPO_QUADRADO = 1, TIPO_PENTAGONO = 2, TIPO_HEXAGONO = 3;
	public static final int UNIDADE_DP = 0, UNIDADE_CM = 1, UNIDADE_M = 2, UNIDADE_KM = 3, UNIDADE_INCH = 4;
	public static final int EXCLUI = 0, NAO_EXCLUI = 1;

	public static int corTextoDistancia = Color.RED, tamanhoTextoDistancia=40;
	public static boolean ajustaTextoLinha = false;
	public static float ajustaTextoProporcao = 0.2f;

	public static double proporcao=1;

	public static int textoDistancia_apos_virgula=2;
	public static String casasPosVirgula="##";

	/**
	 * Desenha um arco no framelayout escolhido. O arco é centrado no ponto passado.
	 * O arco é adicionado a um array de figuras do framework, acessível pelo método Sketch2D.getFiguras()
	 * O arco é desenhado com configurações padrão (cor preta, preenchido, anti alias ativado).
	 *
	 * @param activity Activity pai do FrameLayout onde o círculo será desenhado.
	 * @param layout FrameLayout onde o círculo será desenhado.
	 * @param ponto Point com o ponto de centro do círculo.
	 * @param raio Raio float do círculo.
	 * @param angleInit Angulo float de começo do arco.
	 * @param degrees Graus float que o arco possui.
	 * @param editavel Define se o círculo será editável/excluível ou não.
	 * @return retorna o arco criado.
	 */
	public static Arco desenhaArco(Activity activity, FrameLayout layout, Point ponto,float raio,float angleInit,float degrees, boolean editavel)
	{
		ArrayList<Point> p = new ArrayList<>();
		p.add(new Point(ponto.x, ponto.y));
		Arco arco = new Arco(activity,p,raio,angleInit,degrees, editavel);
		Sketch2D.instance.addFigura(arco);
		arco.desenha(layout,arco);
		return arco;
	}

    /**
     * Desenha um texto no framelayout escolhido. O texto é centrado no ponto escolhido.
     * O texto é adicionado a um array de figuras do framework, acessível pelo método Sketch2D.getFiguras()
     * @param textoNormal Objeto do tipo texto que ainda não foi desenhado, apenas instanciado.
     * @param layout FrameLayout onde o círculo será desenhado.
     * @return retorna o texto criado.
     */
    public static Texto desenhaTextoCentrado(Texto textoNormal,FrameLayout layout)
    {
        Point pontoCentrado = textoNormal.getPontos().get(0);
        pontoCentrado.x-=textoNormal.getDimensoes()[0]/2;
        pontoCentrado.y-=textoNormal.getDimensoes()[1]/2;
        ArrayList<Point> ps = new ArrayList<>();
        ps.add(new Point(pontoCentrado));
        Texto texto = new Texto(textoNormal.getActivity(),ps,textoNormal.getString(),textoNormal.getTamTexto(),textoNormal.getCor());
        Sketch2D.instance.addFigura(texto);
        texto.desenha(textoNormal.getActivity(),layout,texto);
        return texto;
    }
    /**
     * Desenha um texto no framelayout escolhido. O texto começa do ponto Superior esquerdo.
     * O texto é adicionado a um array de figuras do framework, acessível pelo método Sketch2D.getFiguras()
     *
     * @param activity Activity pai do FrameLayout onde o círculo será desenhado.
     * @param layout FrameLayout onde o círculo será desenhado.
     * @param superiorEsquerdo Point com o ponto do inicio do texto.
     * @param string Texto a ser apresentado.
     * @param tamTexto O tamanho da fonte do texto.
     * @param cor Cor do texto.
     * @return retorna o texto criado.
     */

    public static Texto desenhaTexto(Activity activity, FrameLayout layout, final Point superiorEsquerdo,String string,float tamTexto,int cor)
    {
        ArrayList<Point> p = new ArrayList<>();
        p.add(new Point(superiorEsquerdo.x, superiorEsquerdo.y));
        Texto texto = new Texto(activity,p,string,tamTexto,cor);
        Sketch2D.instance.addFigura(texto);
        texto.desenha(activity, layout, texto);
        return texto;
    }
    /**
     * Desenha um texto no framelayout escolhido. O texto começa do ponto Superior esquerdo.
     * O texto é adicionado a um array de figuras do framework, acessível pelo método Sketch2D.getFiguras()
     * O texto é desenhado com configurações padrão (cor preta).
     *
     * @param activity Activity pai do FrameLayout onde o círculo será desenhado.
     * @param layout FrameLayout onde o círculo será desenhado.
     * @param superiorEsquerdo Point com o ponto do inicio do texto.
     * @param string Texto a ser apresentado.
     * @param tamTexto O tamanho da fonte do texto.
     * @return retorna o texto criado.
     */
    public static Texto desenhaTexto(Activity activity, FrameLayout layout, final Point superiorEsquerdo,String string,float tamTexto)
    {
        ArrayList<Point> p = new ArrayList<>();
        p.add(new Point(superiorEsquerdo.x, superiorEsquerdo.y));
        Texto texto = new Texto(activity,p,string,tamTexto);
        Sketch2D.instance.addFigura(texto);
        texto.desenha(activity, layout, texto);
        return texto;
    }

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
	public static Linha desenhaLinha(Activity activity, FrameLayout layout, ArrayList<Point> pontos, boolean editavel, Configuracoes configuracoes, boolean distancia)
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
     * Desenha um polígono aberto no framelayout escolhido com os vértices nos pontos escolhidos.
     * O polígono é adicionado a um array de figuras do framework, acessível pelo método Sketch2D.getFiguras()
     *
     * @param activity Activity pai do FrameLayout onde o polígono será desenhado.
     * @param layout FrameLayout onde o polígono será desenhado.
     * @param pontos Array com os pontos de vértice do polígono. O último ponto não precisa ser o primeiro.
     * @param editavel Define se o polígono será editável/excluível ou não.
     * @return retorna o polígono criado.
     */
    public static Figura desenhaPoligonoAberto(Activity activity, FrameLayout layout, ArrayList<Point> pontos, boolean editavel)
    {
        //Poligono poligono = new Poligono(activity, pontos, editavel, configuracoes);
        Figura poligono;
        if(pontos.size() == 2)
        {
            poligono = new Linha(activity, pontos, editavel);
        }
        else
        {
            poligono = new Poligono(activity, pontos, editavel,true);
        }
        Sketch2D.instance.addFigura(poligono);
        poligono.desenha(layout, poligono);
        return poligono;
    }

	/**
	 * Desenha um polígono aberto no framelayout escolhido com os vértices nos pontos escolhidos.
	 * O polígono é adicionado a um array de figuras do framework, acessível pelo método Sketch2D.getFiguras()
	 *
	 * @param activity Activity pai do FrameLayout onde o polígono será desenhado.
	 * @param layout FrameLayout onde o polígono será desenhado.
	 * @param pontos Array com os pontos de vértice do polígono. O último ponto não precisa ser o primeiro.
	 * @param editavel Define se o polígono será editável/excluível ou não.
	 * @param configuracoes Define as configurações referentes ao polígono.
	 * @return retorna o polígono criado.
	 */
	public static Figura desenhaPoligonoAberto(Activity activity, FrameLayout layout, ArrayList<Point> pontos, boolean editavel, Configuracoes configuracoes)
	{
		//Poligono poligono = new Poligono(activity, pontos, editavel, configuracoes);
		Figura poligono;
		if(pontos.size() == 2)
		{
			poligono = new Linha(activity, pontos, editavel, configuracoes);
		}
		else
		{
			poligono = new Poligono(activity, pontos, editavel, configuracoes,true);
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
	public static Figura removeDesenho(int index)
	{
		Figura f = null;
		try{
            ((ViewGroup) instance.getFigura(index).getView().getParent()).removeView(instance.getFigura(index).getView());
        }catch (Exception ex){}
		if (index > -1 && index < instance.getFiguras().size()) {
			f = instance.getFiguras().get(index);
			instance.getFiguras().remove(index);
		}
		for (int i = 0; i < instance.getFiguras().size(); i++) {
			instance.getFiguras().get(i).setIndex(i);
		}
		return f;
	}

	public static void removeDesenho(Figura f)
	{
		try{
            ((ViewGroup) f.getView().getParent()).removeView(f.getView());
        }catch (Exception ex){}
		instance.getFiguras().remove(f);
		for(int i = 0;i<instance.getFiguras().size();i++)
		{
			instance.getFiguras().get(i).setIndex(i);
		}
	}

	/**
	 * Adapta o tamanho de todas as figuras com o aumento de linha.
	 * @param aumentoDaLinha valor do aumento do tamanho(espessura) da linha.
	 */
	public static void adaptaTamanho(int aumentoDaLinha)
	{
		for(Figura f:Sketch2D.getFiguras())
		{
			f.getConfiguracoes().setEstilo(Configuracoes.LINHA);
			f.getConfiguracoes().setTamLinha(f.getConfiguracoes().getTamLinha() + aumentoDaLinha);
			ViewGroup.LayoutParams params = (f.getView()).getLayoutParams();
			f.getView().setLayoutParams(new FrameLayout.LayoutParams(params.width + aumentoDaLinha, params.height + aumentoDaLinha));

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

	public static double getProporcao()
	{
		return proporcao;
	}

	public static void setProporcao(double proporcao)
	{
		Sketch2D.proporcao = proporcao;
	}

	public static void setTextoDistancia_apos_virgula(int qtd)
	{
		Sketch2D.textoDistancia_apos_virgula=qtd;
		Sketch2D.casasPosVirgula = new String(new char[Sketch2D.textoDistancia_apos_virgula]).replace("\0","#");
	}
}
