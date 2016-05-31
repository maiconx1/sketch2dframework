package com.imobilis.sketch2dframework;

import android.app.Activity;
import android.graphics.Path;
import android.graphics.Point;

import java.util.ArrayList;

/**
 * Criado por Maicon em 18/04/2016.
 */
public class Poligono extends Figura
{

	public Poligono(Activity activity, ArrayList<Point> pontos, boolean editavel)
	{
		super(activity, pontos, editavel);
	}

	public Poligono(Activity activity, ArrayList<Point> pontos, boolean editavel, Configuracoes configuracoes)
	{
		super(activity, pontos, editavel, configuracoes);
	}

	/*@Override
	public boolean isDentro(Point ponto)
	{
		ponto.x = ponto.x + getMenorX().x;
		ponto.y = ponto.y + getMenorY().y;
		int cruzamentos = 0;
		Point a, b;
		int r;
		for(int i = 0;i<getPontos().size() - 1;i++)
		{
			a = getPonto(i);
			b = getPonto(i + 1);
			r = (b.x - a.x)*(ponto.y - a.y) - (ponto.x - a.x)*(b.y - a.y);
			if(a.y > b.y)
			{
				Point aux = a;
				a = b;
				b = aux;
			}
			if(r == 0)
			{
				double escalar = (ponto.x - a.x)*(ponto.x - b.x) + (ponto.y - a.y)*(ponto.y-b.y);
				if(escalar <= 0)
				{
					return true;
				}
			}
			if(r > 0 && a.y < ponto.y && ponto.y <= b.y)
			{
				cruzamentos++;
			}
		}
		a = getPonto(getPontos().size() - 1);
		b = getPonto(0);
		r = (b.x - a.x)*(ponto.y - a.y) - (ponto.x - a.x)*(b.y - a.y);
		if(a.y > b.y)
		{
			Point aux = a;
			a = b;
			b = aux;
		}
		if(r > 0 && a.y < ponto.y && ponto.y <= b.y)
		{
			cruzamentos++;
		}
		return cruzamentos % 2 == 0;
	}*/

	@Override
	public boolean isDentro(Point ponto)
	{
		int cruzamentos = 0;
		ponto.x = ponto.x + getMenor().x;
		ponto.y = ponto.y + getMenor().y;
		Point a, b;
		int r;
		for(int i = 0;i<getPontosEscalados().size() - 1;i++)
		{
			a = getPontosEscalados().get(i);
			b = getPontosEscalados().get(i + 1);
			r = (b.x - a.x)*(ponto.y - a.y) - (ponto.x - a.x)*(b.y - a.y);
			if(a.y > b.y)
			{
				Point aux = a;
				a = b;
				b = aux;
			}
			if(r > 0 && a.y < ponto.y && ponto.y <= b.y)
			{
				cruzamentos++;
			}
		}
		a = getPontosEscalados().get(getPontosEscalados().size() - 1);
		b = getPontosEscalados().get(0);
		r = (b.x - a.x)*(ponto.y - a.y) - (ponto.x - a.x)*(b.y - a.y);
		if(a.y > b.y)
		{
			Point aux = a;
			a = b;
			b = aux;
		}
		if(r > 0 && a.y < ponto.y && ponto.y <= b.y)
		{
			cruzamentos++;
		}
		return cruzamentos % 2 == 0;
	}

	@Override
	public ArrayList<Point[]> pontoMaisProximo(Figura f, float offsetX, float offsetY)
	{
		ArrayList<Point[]> retorno = new ArrayList<>();
		//Point pontos[] = new Point[2];
		//pontos[0] = new Point(10000000, 10000000);
		//pontos[1] = new Point(10000000, 10000000);
		for(int i = 0;i<getPontos().size();i++)
		{
			ArrayList<Point> pLinha = new ArrayList<>();
			pLinha.add(new Point(getPonto(i)));
			if(i == getPontos().size()-1)
			{
				pLinha.add(new Point(getPonto(0)));
			}
			else
			{
				pLinha.add(new Point(getPonto(i+1)));
			}
			Linha l = new Linha(getActivity(), pLinha, false);
			retorno.addAll(l.pontoMaisProximo(f, offsetX, offsetY));
		}
		return retorno;
	}

	public Path getCaminho(float initViewX, float initViewY)
	{
		Path caminho;
		caminho = new Path();
		caminho.reset();
		caminho.moveTo(getX(0) - initViewX, getY(0) - initViewY);
		for(int i = 1;i<getPontos().size();i++)
		{
			caminho.lineTo(getX(i) - initViewX, getY(i) - initViewY);
		}
		caminho.lineTo(getX(0) - initViewX, getY(0) - initViewY);
		return caminho;
	}

	public String toString()
	{
		return "";
	}
}
