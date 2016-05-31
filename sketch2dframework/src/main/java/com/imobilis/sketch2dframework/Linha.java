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
	private boolean distancia;

	public Linha(Activity activity, ArrayList<Point> pontos, boolean editavel)
	{
		super(activity, pontos, editavel);
		Configuracoes conf = new Configuracoes();
		conf.setEstilo(Configuracoes.LINHA);
		conf.setTamLinha(5);
		setConfiguracoes(conf);
		setDistancia(false);
	}

	public Linha(Activity activity, ArrayList<Point> pontos, boolean editavel, Configuracoes configuracoes)
	{
		super(activity, pontos, editavel, configuracoes);
		setDistancia(false);
	}

	public Linha(Activity activity, ArrayList<Point> pontos, boolean editavel, Configuracoes configuracoes, boolean distancia)
	{
		super(activity, pontos, editavel, configuracoes);
		setDistancia(distancia);
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
	public ArrayList<Point[]> pontoMaisProximo(Figura f, float offsetX, float offsetY)
	{
		ArrayList<Point[]> retorno = new ArrayList<>();
		Point pontos[] = new Point[2];
		pontos[0] = getPonto(0);
		pontos[1] = new Point(1000000, 1000000);
		if(f instanceof Linha)
		{
			Point p2 = new Point(getPonto(0)), p1 = new Point(f.getPonto(1)), p0 = new Point(f.getPonto(0));
			p2.x += offsetX;
			p2.y += offsetY;
			if(p1.y == p0.y)
			{
				double y = p1.y;
				double x = p2.x;
				pontos = new Point[2];
				pontos[0] = new Point(p2);
				pontos[1] = new Point(1000000, 1000000);
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
					retorno.add(pontos);
				}
			}
			else if(p1.x == p0.x)
			{
				double y = p2.y;
				double x = p1.x;
				pontos = new Point[2];
				pontos[0] = new Point(p2);
				pontos[1] = new Point(1000000, 1000000);
				double y0, y1;
				Log.d("XXXXX", "P0: " + p0 + "// P1: " + p1 + "// P2: " + p2 + "// X: " + x + "// Y: " + y);
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
					retorno.add(pontos);
				}
			}
			else
			{
				//doublex = p2.x;
				//double y = (x*(p1.y-p0.y) + p1.x*p0.y - p0.x*p1.y)/(p1.x - p0.x);
				double y = p2.y;
				double x = (y * (p1.x - p0.x) - p1.x * p0.y + p0.x * p1.y) / (p1.y - p0.y);
				//pontos[1] = new Point((int)x, (int)y);
				pontos = new Point[2];
				pontos[0] = new Point(p2);
				pontos[1] = new Point(1000000, 1000000);
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
				}
				retorno.add(pontos);
			}
			p2 = new Point(getPonto(1));
			p1 = new Point(f.getPonto(0));
			p0 = new Point(f.getPonto(1));
			p2.x += offsetX;
			p2.y += offsetY;
			if(p1.y == p0.y)
			{
				double y = p1.y;
				double x = p2.x;
				pontos = new Point[2];
				pontos[0] = new Point(p2);
				pontos[1] = new Point(1000000, 1000000);
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
					retorno.add(pontos);
				}
			}
			else if(p1.x == p0.x)
			{
				double y = p2.y;
				double x = p1.x;
				pontos = new Point[2];
				pontos[0] = new Point(p2);
				pontos[1] = new Point(1000000, 1000000);
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
					retorno.add(pontos);
				}
			}
			else
			{
				//x = p0.x;
				//y = (x*(p2.y-p1.y) + p2.x*p1.y - p1.x*p2.y)/(p2.x-p1.x);
				double y = p2.y;
				double x = (y * (p1.x - p0.x) - p1.x * p0.y + p0.x * p1.y) / (p1.y - p0.y);
				pontos = new Point[2];
				pontos[0] = new Point(p2);
				pontos[1] = new Point(1000000, 1000000);
				int x0, x1, y0, y1;
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
				}
				retorno.add(pontos);
			}
			return retorno;
		}
		else if(f instanceof Circulo)
		{
			pontos = new Point[2];
			Point p0 = new Point(getPonto(0)), p1 = new Point(getPonto(1)), p2 = f.getPonto(0);
			pontos[0] = new Point(p2);
			pontos[1] = new Point(1000000, 1000000);
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
					/*pontos[0] = new Point((int) x, (int) y);
					pontos[0].x += offsetX;
					pontos[0].y += offsetY;
					pontos[1] = new Point(p2);*/
					pontos[0] = new Point((int) x, (int) y);
					pontos[1] = new Point(p2);
					retorno.add(pontos);
					return retorno;
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
					pontos[0] = new Point((int) x, (int) y);
					pontos[1] = new Point(p2);
					retorno.add(pontos);
					return retorno;
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
					pontos[0] = new Point((int) x, (int) y);
					pontos[1] = new Point(p2);
					retorno.add(pontos);
					return retorno;
				}
			}
		}
		else if(f instanceof Poligono)
		{
			for(int i = 0;i<f.getPontos().size();i++)
			{
				/*Point p2 = new Point(getPonto(0)), p1, p0 = f.getPonto(i);;
				if(i != f.getPontos().size()-1)
				{
					p1 = f.getPonto(i+1);
				}
				else
				{
					p1 = f.getPonto(0);
				}
				p2.x += offsetX;
				p2.y += offsetY;
				//doublex = p2.x;
				//double y = (x*(p1.y-p0.y) + p1.x*p0.y - p0.x*p1.y)/(p1.x - p0.x);
				double y = p2.y;
				double x = (y*(p1.x-p0.x) - p1.x*p0.y + p0.x*p1.y)/(p1.y - p0.y);
				pontos[0] = new Point(p2);
				//pontos[1] = new Point((int)x, (int)y);
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
				}
				retorno.add(pontos);
				p2 = new Point(getPonto(1));
				//p1 = new Point(f.getPonto(0));
				p0 = new Point(f.getPonto(1));
				if(i != f.getPontos().size()-1)
				{
					p1 = f.getPonto(i+1);
				}
				else
				{
					p1 = f.getPonto(0);
				}
				p2.x += offsetX;
				p2.y += offsetY;
				//x = p0.x;
				//y = (x*(p2.y-p1.y) + p2.x*p1.y - p1.x*p2.y)/(p2.x-p1.x);
				y = p2.y;
				x = (y*(p1.x-p0.x) - p1.x*p0.y + p0.x*p1.y)/(p1.y - p0.y);
				pontos = new Point[2];
				pontos[0] = new Point(p2);
				pontos[1] = new Point(1000000, 1000000);
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
				}
				retorno.add(pontos);*/
				Point p2 = new Point(getPonto(1)), p1, p0 = f.getPonto(i);
				if(i != f.getPontos().size()-1)
				{
					p1 = f.getPonto(i+1);
				}
				else
				{
					p1 = f.getPonto(0);
				}
				p2.x += offsetX;
				p2.y += offsetY;
				if(p1.y == p0.y)
				{
					double y = p1.y;
					double x = p2.x;
					pontos = new Point[2];
					pontos[0] = new Point(p2);
					pontos[1] = new Point(1000000, 1000000);
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
						retorno.add(pontos);
					}
				}
				else if(p1.x == p0.x)
				{
					double y = p2.y;
					double x = p1.x;
					pontos = new Point[2];
					pontos[0] = new Point(p2);
					pontos[1] = new Point(1000000, 1000000);
					double y0, y1;
					Log.d("XXXXX", "P0: " + p0 + "// P1: " + p1 + "// P2: " + p2 + "// X: " + x + "// Y: " + y);
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
						retorno.add(pontos);
					}
				}
				else
				{
					//doublex = p2.x;
					//double y = (x*(p1.y-p0.y) + p1.x*p0.y - p0.x*p1.y)/(p1.x - p0.x);
					double y = p2.y;
					double x = (y * (p1.x - p0.x) - p1.x * p0.y + p0.x * p1.y) / (p1.y - p0.y);
					//pontos[1] = new Point((int)x, (int)y);
					pontos = new Point[2];
					pontos[0] = new Point(p2);
					pontos[1] = new Point(1000000, 1000000);
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
					}
					retorno.add(pontos);
				}
				p2 = new Point(getPonto(0));
				p0 = f.getPonto(i);
				if(i != f.getPontos().size()-1)
				{
					p1 = f.getPonto(i+1);
				}
				else
				{
					p1 = f.getPonto(0);
				}
				p2.x += offsetX;
				p2.y += offsetY;
				if(p1.y == p0.y)
				{
					double y = p1.y;
					double x = p2.x;
					pontos = new Point[2];
					pontos[0] = new Point(p2);
					pontos[1] = new Point(1000000, 1000000);
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
						retorno.add(pontos);
					}
				}
				else if(p1.x == p0.x)
				{
					double y = p2.y;
					double x = p1.x;
					pontos = new Point[2];
					pontos[0] = new Point(p2);
					pontos[1] = new Point(1000000, 1000000);
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
						retorno.add(pontos);
					}
				}
				else
				{
					//x = p0.x;
					//y = (x*(p2.y-p1.y) + p2.x*p1.y - p1.x*p2.y)/(p2.x-p1.x);
					double y = p2.y;
					double x = (y * (p1.x - p0.x) - p1.x * p0.y + p0.x * p1.y) / (p1.y - p0.y);
					pontos = new Point[2];
					pontos[0] = new Point(p2);
					pontos[1] = new Point(1000000, 1000000);
					int x0, x1, y0, y1;
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
					}
					retorno.add(pontos);
				}
			}
			return retorno;
		}
		pontos[1] = new Point(1000000, 1000000);
		retorno.add(pontos);
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
		return caminho;
	}

	public boolean isDistancia()
	{
		return distancia;
	}

	public void setDistancia(boolean distancia)
	{
		this.distancia = distancia;
	}
}
