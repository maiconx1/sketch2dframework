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
	public boolean sentidoHorario=true;


	public Poligono(Activity activity, ArrayList<Point> pontos, boolean editavel)
	{
		super(activity, pontos, editavel);
	}

	public Poligono(Activity activity, ArrayList<Point> pontos, boolean editavel, Configuracoes configuracoes)
	{
		super(activity, pontos, editavel, configuracoes);
	}

	//TODO 17-10
	public boolean isSentidoHorario() {
		return sentidoHorario;
	}

	public void setSentidoHorario(boolean sentidoHorario) {
		this.sentidoHorario = sentidoHorario;
	}

	//TODO 17-10 2


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
		if( p.y<0 || p.y>getView().getHeight() || p.x>getView().getWidth() || p.x<0)
			return false;
		int inclinados=0;
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
				Point aux = new Point(a);
				a = new Point(b);
				b = new Point(aux);
			}

			if(a.y < ponto.y && ponto.y <= b.y)
			{
				if(r> 0)
				{
					cruzamentos++;
				}
				inclinados++;
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
		if(a.y < ponto.y && ponto.y <= b.y)
		{
			if(r > 0)
			{
				cruzamentos++;
			}
			inclinados++;
		}

		if(inclinados == 0 && cruzamentos == 0)
		{
			return false;
		}

		if(inclinados >= 3)
		{
			return cruzamentos % 2 != 0;
		}
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
			Log.d("PONTOSOFFSETDESENHADO", getX(i) + "//" + getY(i) + "/////////" + (getX(i) - initViewX) + "//" + (getY(i) - initViewY));
		}
		caminho.lineTo(getX(0) - initViewX, getY(0) - initViewY);
		return caminho;
	}

	public ArrayList<Linha> explode()
	{
		ArrayList<Linha> linhas = new ArrayList<>();
		for(int i = 0;i<getPontos().size();i++)
		{
			Log.d("POLIGONO", "" + getPontos().get(i));
			int index = i+1;
			if(i == getPontos().size()-1)
			{
				index = 0;
			}
			ArrayList<Point> pontos = new ArrayList<>();
			pontos.add(new Point(getPontos().get(i)));
			pontos.add(new Point(getPontos().get(index)));
			linhas.add(new Linha(getActivity(), pontos, false, getConfiguracoes()));
		}
		return linhas;
	}

	public String toString()
	{
		return "";
	}
}
