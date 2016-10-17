package com.imobilis.sketch2dframework;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * Criado por Maicon em 18/04/2016.
 */
public class Circulo extends Figura
{
	private float raio;
	private int index_poligono=-1;
	private boolean tipoExcluir=false;
	private boolean tipoClip=false;



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

    //TODO 17-10
    public boolean isTipoClip() {
        return tipoClip;
    }

    public void setTipoClip(boolean tipoClip) {
        this.tipoClip = tipoClip;
    }

    //TODO 17-10 2


    public int getIndex_poligono() {
		return index_poligono;
	}

	public void setIndex_poligono(int index_poligono) {
		this.index_poligono = index_poligono;
	}

	public boolean isTipoExcluir() {
		return tipoExcluir;
	}

	public void setTipoExcluir(boolean tipoExcluir) {
		this.tipoExcluir = tipoExcluir;
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
		float mul = getConfiguracoes().getEscala()*getConfiguracoes().getZoom();
		Point c = new Point((int)(getRaio()*mul), (int)(getRaio()*mul));
		double distancia = Math.sqrt((c.x - ponto.x)*(c.x - ponto.x) + (c.y - ponto.y)*(c.y - ponto.y));
		Log.d("lalala", "" + distancia + "//" + raio*mul);
		//TODO;
		if(isTipoExcluir())
		{
			if((distancia <= (raio+10)*mul)&&Figura.poligono_editando!=null){
				//Nao pode ser excluido todos os pontos para tal é utilizado a funcao excluir e nao excluir ponto a ponto.
				if(Figura.poligono_editando.getPontos().size()>1) {
					Sketch2D.commandManager.execute(new DeletePointCommand(this,new Point(this.getPontos().get(0).x,this.getPontos().get(0).y),this.getIndex_poligono()));
				}
			}

		}
		//TODO 2;


        //TODO 17-10;
        if(isTipoClip() && Figura.indexCirculosSelected[1]==-1)
        {
            if(Figura.indexCirculosSelected[0]==-1)
            {
                Figura.indexCirculosSelected[0]=this.index_poligono;
            }
            else if(Figura.indexCirculosSelected[1]==-1)
            {
                Figura.indexCirculosSelected[1]=this.index_poligono;
                int size = Figura.indexCirculosSize0;
                int cont= Figura.indexCirculosSelected[0];
                ArrayList<Integer> indexs = new ArrayList<>();
                int incremento = ((Poligono)Figura.poligono_editando).isSentidoHorario()?1:-1;
                for(int i=0;i<size;i++)
                {
                    cont+=incremento;

                    if(cont==-1)
                        cont = size-1;
                    else if(cont==size)
                        cont=0;

                    if(cont==Figura.indexCirculosSelected[1])
                        break;

                    indexs.add(cont);
                    indexCirculos.get(cont).getConfiguracoes().setEstilo(Configuracoes.PREENCHIDO);
                    indexCirculos.get(cont).getConfiguracoes().setCor(Color.RED);
                    indexCirculos.get(cont).getView().invalidate();
                }
                for(int i=0;i<indexs.size();i++)
                {
                    Log.d("MeuLog","P = "+indexs.get(i));
                }
                Figura.linhas_clip = new ArrayList<>();

                if(incremento==-1)
                {
                    indexs.add(0, indexCirculosSelected[0]);
                    if(indexCirculosSelected[0]!=indexCirculosSelected[1])
                        indexs.add(indexCirculosSelected[1]);
                }
                else{
                    indexs.add(0, indexCirculosSelected[1]);
                    if(indexCirculosSelected[0]!=indexCirculosSelected[1])
                        indexs.add(indexCirculosSelected[0]);
                }


                for(int i=0;i<indexs.size();i++)
                {
                    Log.d("MeuLog","PEE = "+indexs.get(i)+" ponto = "+Figura.poligono_editando.getPontos().get(i).toString());
                }

                for(int i=0;i<indexs.size()-1;i++)
                {
                    Point a = Figura.indexCirculos.get(indexs.get(i)).getPontos().get(0);
                    Point b = Figura.indexCirculos.get(indexs.get(i+1)).getPontos().get(0);
                    ArrayList<Point> pontos = new ArrayList<>();
                    pontos.add(a);
                    pontos.add(b);
                    Configuracoes cc = new Configuracoes();
                    cc.setCor(Color.BLUE);
                    Figura.linhas_clip.add(Sketch2D.desenhaLinha(Figura.indexCirculos.get(0).getActivity(),(FrameLayout)Figura.indexCirculos.get(0).getView().getParent(), pontos, true, cc));

                    ((Linha)Sketch2D.getFiguras().get(Sketch2D.getFiguras().size()-1)).setIndex_poligono(indexs.get(i+1));

                }

                Figura.indexsOffset = indexs;
            }
            this.getConfiguracoes().setEstilo(Configuracoes.PREENCHIDO);
            this.getView().invalidate();
        }
        //TODO 17-10 2;



		return distancia <= (raio+10)*mul;
	}

	@Override
	public ArrayList<Point[]> pontoMaisProximo(Figura f, float offsetX, float offsetY)
	{
		ArrayList<Point[]> retorno = new ArrayList<>();
		Point pontos[] = new Point[2];
		pontos[0] = getPonto(0);
		pontos[0].x += offsetX;
		pontos[0].y += offsetY;
		if(f instanceof Circulo)
		{
			pontos[1] = f.getPonto(0);
			retorno.add(pontos);
			return retorno;
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
					pontos[1] = new Point((int) x, (int) y);
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
					pontos[1] = new Point((int) x, (int) y);
					retorno.add(pontos);
					return retorno;
				}
			}
		}
		else if(f instanceof Poligono)
		{
			float mul = getConfiguracoes().getEscala()*getConfiguracoes().getZoom();
			for(int i = 0;i<f.getPontos().size();i++)
			{
				Point init = pontos[0];
				pontos = new Point[2];
				pontos[0] = init;
				Point p0 = f.getPonto(i), p1, p2 = new Point(getPonto(0));
				if(i == f.getPontos().size()-1)
				{
					p1 = f.getPonto(0);
				}
				else
				{
					p1 = f.getPonto(i+1);
				}
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
						retorno.add(pontos);
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
						retorno.add(pontos);
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
						retorno.add(pontos);
					}
				}
			}
			return retorno;
		}
		pontos[1] = new Point(1000000, 1000000);
		retorno.add(pontos);
		return retorno;
	}
}
