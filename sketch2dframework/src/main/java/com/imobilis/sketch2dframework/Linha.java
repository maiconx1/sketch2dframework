package com.imobilis.sketch2dframework;

import android.app.Activity;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;

/**
 * Criado por Maicon em 02/05/2016.
 */
public class Linha extends Figura
{
	public Linha(Activity activity, ArrayList<Point> pontos, boolean editavel)
	{
		super(activity, pontos, editavel);
		Configuracoes conf = new Configuracoes();
		conf.setEstilo(Configuracoes.LINHA);
		conf.setTamLinha(5);
		setConfiguracoes(conf);
	}

	public Linha(Activity activity, ArrayList<Point> pontos, boolean editavel, Configuracoes configuracoes)
	{
		super(activity, pontos, editavel, configuracoes);
	}

	@Override
	public boolean isDentro(Point ponto)
	{
		float y;
		Point pInicial = getPonto(0), pFinal = getPonto(1);
		ponto.x += getView().getX();
		ponto.y += getView().getY();
		Log.d("STATUS: ", "IS DENTRO: clickX=" + ponto.x + "//clickY=" + ponto.y + "//initX=" + pInicial.x + "//initY=" + pInicial.y + "//fimX=" + pFinal.x + "//fimY=" + pFinal.y);
		if(pInicial.x == pFinal.x)
		{
			y = ponto.y;
		}
		else
		{
			y = (pFinal.y * (ponto.x - pInicial.x) + pInicial.y * (pFinal.x - ponto.x)) / (pFinal.x - pInicial.x);
		}
		if(y <= ponto.y + (15 + getConfiguracoes().getTamLinha()) && y >= ponto.y - (15 + getConfiguracoes().getTamLinha()))
		{
			return true;
		}
		return false;
	}

	@Override
	public Point[] pontoMaisProximo(Figura f, float offsetX, float offsetY)
	{
		Point pontos[] = new Point[2];
		pontos[0] = getPonto(0);
		pontos[0].x += offsetX;
		pontos[0].y += offsetY;
		if(f instanceof Poligono)
		{
			pontos[1] = f.getPonto(0);
			return pontos;
		}
		else if(f instanceof Circulo)
		{
			Point p0 = new Point(getPonto(0)), p1 = new Point(getPonto(1)), p2 = f.getPonto(0);
			p0.x += offsetX;
			p0.y += offsetY;
			p1.x += offsetX;
			p1.y += offsetY;
			if(p1.y == p0.y)
			{
				double y = p1.y;
				double x = p2.x;
				double x0, x1;
				if(p0.x > p1.x)
				{
					x0 = p0.x;
					x1 = p1.x;
				}
				else
				{
					x0 = p1.x;
					x1 = p0.x;
				}
				if(x < x0 && x > x1)
				{
					pontos[1] = new Point((int) x, (int) y);
					return pontos;
				}
			}
			else if(p1.x == p0.x)
			{
				double y = p2.y;
				double x = p1.x;
				double y0, y1;
				if(p0.y > p1.y)
				{
					y0 = p0.y;
					y1 = p1.y;
				}
				else
				{
					y0 = p1.y;
					y1 = p0.y;
				}
				if(y < y0 && y > y1)
				{
					pontos[1] = new Point((int) x, (int) y);
					return pontos;
				}
			}
			else
			{
				double x = ((p1.x * p0.y - p0.x * p1.y) * (p1.y - p0.y) - p2.x * Math.pow(p1.x - p0.x, 2) - p2.y * (p1.y - p0.y) * (p1.x - p0.x)) / (-(Math.pow(p1.x - p0.x, 2) + Math.pow(p1.y - p0.y, 2)));
				double y = (x * (p0.x - p1.x) + p2.x * (p1.x - p0.x) + p2.y * (p1.y - p0.y)) / (p1.y - p0.y);
				double x0, x1, y0, y1;
				if(p0.x > p1.x)
				{
					x0 = p0.x;
					x1 = p1.x;
				}
				else
				{
					x0 = p1.x;
					x1 = p0.x;
				}
				if(p0.y > p1.y)
				{
					y0 = p0.y;
					y1 = p1.y;
				}
				else
				{
					y0 = p1.y;
					y1 = p0.y;
				}
				if(x < x0 && x > x1 && y < y0 && y > y1)
				{
					pontos[1] = new Point((int) x, (int) y);
					return pontos;
				}
			}
		}
		pontos[1] = new Point(1000000, 1000000);
		return pontos;
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
		return caminho;
	}
}
