package com.imobilis.sketch2dframework;

import android.app.Activity;
import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;

/**
 * Criado por Maicon em 18/04/2016.
 */
public class Circulo extends Figura
{
	private float raio;

	public Circulo(Activity activity, ArrayList<Point> pontos, float raio, boolean editavel)
	{
		super(activity, pontos, editavel);
		setRaio(raio);
	}

	public Circulo(Activity activity, ArrayList<Point> pontos, float raio, boolean editavel, Configuracoes configuracoes)
	{
		super(activity, pontos, editavel, configuracoes);
		setRaio(raio);
	}

	public void setRaio(float raio)
	{
		this.raio = raio;
	}

	public float getRaio()
	{
		return raio;
	}

	@Override
	public boolean isDentro(Point ponto)
	{
		float mul = (int)getConfiguracoes().getEscala()*getConfiguracoes().getZoom();
		Point c = new Point((int)(getRaio()*mul), (int)(getRaio()*mul));
		double distancia = Math.sqrt((c.x - ponto.x)*(c.x - ponto.x) + (c.y - ponto.y)*(c.y - ponto.y));
		return distancia <= raio*mul;
	}

	@Override
	public Point[] pontoMaisProximo(Figura f, float offsetX, float offsetY)
	{
		Point pontos[] = new Point[2];
		pontos[0] = getPonto(0);
		pontos[0].x += offsetX;
		pontos[0].y += offsetY;
		if(f instanceof Circulo)
		{
			pontos[1] = f.getPonto(0);
			return pontos;
		}
		/*else if(f instanceof Poligono)
		{
			Point p0 = f.getPonto(0), p1 = f.getPonto(1), p2 = new Point(getPonto(0));
			p2.x += offsetX;
			p2.y += offsetY;
			double dist = Figura.distancia2Pontos(p0, p2);
			for(int i = 0;i<f.getPontos().size();i++)
			{
				Point p = f.getPontos().get(i);
				double d = Figura.distancia2Pontos(p, p2);
				if(d < dist)
				{
					p0 = p;
					if(i == f.getPontos().size() - 1)
					{
						p1 = f.getPontos().get(0);
					}
					else
					{
						p1 = f.getPontos().get(i+1);
					}
					dist = d;
				}
			}
			double x = ((p1.x*p0.y-p0.x*p1.y)*(p1.y-p0.y) - p2.x*Math.pow(p1.x-p0.x, 2) - p2.y*(p1.y-p0.y)*(p1.x-p0.x))/(-(Math.pow(p1.x - p0.x, 2) + Math.pow(p1.y-p0.y, 2)));
			double y = (x*(p0.x-p1.x) + p2.x*(p1.x - p0.x) + p2.y*(p1.y - p0.y))/(p1.y-p0.y);
			if(x > p0.x && x < p1.x && y > p0.y && y < p1.y)
			{
				pontos[1] = new Point((int)x, (int)y);
				return pontos;
			}
		}*/
		else if(f instanceof Linha)
		{
			Point p0 = f.getPonto(0), p1 = f.getPonto(1), p2 = new Point(getPonto(0));
			p2.x += offsetX;
			p2.y += offsetY;
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
}
