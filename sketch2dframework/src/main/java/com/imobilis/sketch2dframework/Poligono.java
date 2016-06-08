package com.imobilis.sketch2dframework;

import android.app.Activity;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;

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

	@Override
	public Point getPonto(int index)
	{
		//Point p;
		//float mul = getConfiguracoes().getEscala()*getConfiguracoes().getZoom();
		//p = new Point((int)(getPontos().get(index).x + getConfiguracoes().getTamLinha()*mul), (int)(getPontos().get(index).y + getConfiguracoes().getTamLinha()*mul));
		//p = new Point(getPontos().get(index).x + getConfiguracoes().getTamLinha(), getPontos().get(index).y + getConfiguracoes().getTamLinha());
		//return p;
		return getPontosEscalados().get(index);
	}

	@Override
	public boolean isDentro(Point p)
	{
		int cruzamentos = 0;
		Point ponto = new Point(p);
		ponto.x = ponto.x + getMenor().x;
		ponto.y = ponto.y + getMenor().y;
		Point a, b;
		int r;
		for(int i = 0;i<getPontosEscalados().size() - 1;i++)
		{
			a = getPonto(i);
			b = getPonto(i+1);
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
			Log.d("TESTEISDENTRO", "A: " + a + "//B: " + b + "//PONTO: " + ponto + "//R: " + r);
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
		Log.d("TESTEISDENTRO", "A: " + a + "//B: " + b + "//PONTO: " + ponto + "//R: " + r);
		return cruzamentos % 2 == 0;
	}

	@Override
	public ArrayList<Point[]> pontoMaisProximo(Figura f, float offsetX, float offsetY)
	{
		ArrayList<Point[]> retorno = new ArrayList<>();
		for(int i = 0;i<getPontosEscalados().size();i++)
		{
			ArrayList<Point> pLinha = new ArrayList<>();
			pLinha.add(new Point(getPontosEscalados().get(i)));
			if(i == getPontos().size()-1)
			{
				pLinha.add(new Point(getPontosEscalados().get(0)));
			}
			else
			{
				pLinha.add(new Point(getPontosEscalados().get(i+1)));
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
