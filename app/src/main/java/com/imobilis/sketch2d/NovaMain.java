package com.imobilis.sketch2d;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.imobilis.sketch2dframework.Circulo;
import com.imobilis.sketch2dframework.Configuracoes;
import com.imobilis.sketch2dframework.Figura;
import com.imobilis.sketch2dframework.Linha;
import com.imobilis.sketch2dframework.Poligono;
import com.imobilis.sketch2dframework.Singleton;
import com.imobilis.sketch2dframework.Sketch2D;
import com.imobilis.sketch2dframework.SketchParent;

import java.util.ArrayList;

/**
 * Criado por Maicon em 08/06/2016.
 */
public class NovaMain extends AppCompatActivity
{
	SketchParent parent;
	ArrayList<Figura> furos;
	Point pInicial, pFinal;
	Figura fInicial, fFinal;
	float altura;
	int tipo;
	Malha malha;
	Figura linha;
	int estado = ESTADO_INICIAL;
	public static final int ESTADO_INICIAL = 0, ESTADO_PRIMEIRO = 1, ESTADO_SEGUNDO = 2;
	Figura figura;
	int offset = 10;
	Poligono pOffset = null;
	ArrayList<Figura> lDeslocadas = null;

	@Override
	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.activity_main);
		parent = (SketchParent)findViewById(R.id.lnDesenho);
		furos = new ArrayList<>();
		altura = 0;
		tipo = 0;
		linha = null;
		fInicial = null;
		fFinal = null;

		SketchParent.setMostraEscala(true);
		parent.invalidate();

				((SeekBar) findViewById(R.id.seekBar)).setProgress(10);
		((SeekBar)findViewById(R.id.seekBar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
				altura = 10 + ((float) progress) * 3;
				offset = progress;
				atualizaMalha();
				//criaOffset((Poligono)poligono, offset);
				testeOffset();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar)
			{
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar)
			{
			}
		});
		/*parent.setOnTouchListener(new View.OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				switch(event.getAction())
				{
					case MotionEvent.ACTION_DOWN:
						if(estado == ESTADO_INICIAL)
						{
							pInicial = new Point((int) event.getX(), (int) event.getY());
							estado = ESTADO_PRIMEIRO;
							fInicial = Sketch2D.desenhaCirculo(NovaMain.this, parent, pInicial, 10, false, new Configuracoes(false, Configuracoes.PREENCHIDO, 1, true, Color.BLACK, 255));
							return true;
						}
						break;
					case MotionEvent.ACTION_MOVE:
						if(estado == ESTADO_PRIMEIRO)
						{
							if(linha != null)
							{
								Sketch2D.removeDesenho(linha);
							}
							ArrayList<Point> l = new ArrayList<>();
							l.add(fInicial.getPonto(0));
							l.add(new Point((int) event.getX(), (int) event.getY()));
							Sketch2D.setTamLinhaPadrao(1);
							Sketch2D.setCorPadrao(Color.BLACK);
							linha = Sketch2D.desenhaLinha(NovaMain.this, parent, l, false, new Configuracoes(false, Configuracoes.LINHA, 1, true, Color.BLACK, 255));
							return true;
						}
						break;
					case MotionEvent.ACTION_UP:
						pFinal = new Point((int)event.getX(), (int)event.getY());
						estado = ESTADO_SEGUNDO;
						fFinal = Sketch2D.desenhaCirculo(NovaMain.this, parent, pFinal, 10, false, new Configuracoes(false, Configuracoes.PREENCHIDO, 1, true, Color.BLACK, 255));
						tria(poligono, malha);
						return true;
				}
				return false;
			}
		});*/
		SketchParent.setQtdColunas(50);
		Circulo c = Sketch2D.desenhaCirculo(this, parent, new Point(200, 200), 20, true, new Configuracoes(false, Configuracoes.LINHA, 1, true, Color.BLUE, 255));
		c.setCruz(true);
		ArrayList<Point> points = new ArrayList<>();
		points.add(new Point(100, 300));
		points.add(new Point(300, 100));
		Linha l = Sketch2D.desenhaLinha(this, parent, points, false, new Configuracoes(false, Configuracoes.LINHA, 1, true, Color.BLACK, 255));
		points = new ArrayList<>();
		points.add(new Point(200, 100));
		points.add(new Point(200, 300));
		Sketch2D.desenhaLinha(this, parent, points, false, new Configuracoes(false, Configuracoes.LINHA, 1, true, Color.BLACK, 255));
		//c.getView().invalidate();
		parent.invalidate();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		//inflater.inflate(R.menu.menu_main, menu);
		inflater.inflate(R.menu.menu_unidades, menu);
		return true;
	}

	void atualizaMalha()
	{
		if(estado == ESTADO_SEGUNDO)
		{
			parent.invalidate();
		}
		else
		{
			//Toast.makeText(getBaseContext(), "Selecione o primeiro e segundo ponto primeiro!!", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.dp:
				tipo = Malha.QUADRATICA;
				Sketch2D.setUnidade(Sketch2D.UNIDADE_DP);
				Configuracoes.setCorPadrao(Color.RED);
				Configuracoes.refresh(parent);
				atualizaMalha();
				break;
			case R.id.cm:
				tipo = Malha.ESTAGIADA;
				Sketch2D.setUnidade(Sketch2D.UNIDADE_CM);
				Configuracoes.setCorPadrao(Color.GREEN);
				Configuracoes.refresh(parent);
				atualizaMalha();
				//tria(poligono, Malha.QUADRATICA, malha);

				break;
			case R.id.m:
				Sketch2D.setUnidade(Sketch2D.UNIDADE_M);
				Configuracoes.setCorPadrao(Color.BLACK);
				Configuracoes.refresh(parent);
				break;
			case R.id.km:
				Sketch2D.setUnidade(Sketch2D.UNIDADE_KM);
				Configuracoes.setCorPadrao(Color.CYAN);
				Configuracoes.refresh(parent);
				break;
			case R.id.inch:
				Sketch2D.setUnidade(Sketch2D.UNIDADE_INCH);
				Configuracoes.setCorPadrao(Color.YELLOW);
				Configuracoes.refresh(parent);
				//criaOffset((Poligono) figura, 10);
				testeOffset();
				break;
		}
		parent.invalidate();
		return super.onOptionsItemSelected(item);
	}

	public Linha deslocaPonto(Point p0, Point p1, int offset)
	{
		ArrayList<Point> pontos = new ArrayList<>();
		pontos.add(new Point(p0));
		pontos.add(new Point(p1));
		double dify = p0.y - p1.y, difx = p0.x - p1.x;
		double angulo = Math.atan(dify/difx);
		if(p0.x < p1.x)
		{
			pontos.get(0).x += offset * Math.sin(angulo);
			pontos.get(1).x += offset * Math.sin(angulo);
			pontos.get(0).y -= offset * Math.cos(angulo);
			pontos.get(1).y -= offset * Math.cos(angulo);
		}
		else
		{
			pontos.get(0).x -= offset * Math.sin(angulo);
			pontos.get(1).x -= offset * Math.sin(angulo);
			pontos.get(0).y += offset * Math.cos(angulo);
			pontos.get(1).y += offset * Math.cos(angulo);
		}
		Linha linha = new Linha(this, pontos, false, new Configuracoes(false, Configuracoes.LINHA, 1, true, Color.RED, 255));
		Log.d("DESLOCAMENTO", p0 + "//" + p1 + "//" + pontos);
		return linha;
	}

	public void testeOffset()
	{
		if(lDeslocadas != null)
		{
			for(Figura f : lDeslocadas)
			{
				Sketch2D.removeDesenho(f);
			}
		}
		lDeslocadas = new ArrayList<>();
		if(pOffset != null)
		{
			Sketch2D.removeDesenho(pOffset);
		}
		ArrayList<Linha> linhas = ((Poligono)figura).explode();
		ArrayList<Point> pOffset = new ArrayList<>();
		for(int i = 0;i<linhas.size();i++)
		{
			int index = (i == linhas.size()-1?0:i+1);
			Point p0 = linhas.get((i==0?linhas.size()-1:i-1)).getPonto(0), p1 = linhas.get(i).getPonto(0), p2 = linhas.get(index).getPonto(0);
			//Log.d("LINHAS", linhas.get((i==0?linhas.size()-1:i-1)).getPontos() + "//" + linhas.get(i).getPontos() + "//" + linhas.get(index).getPontos());
			//Log.d("LINHAS", linhas.get(i).getPontos() + "");
			Linha linha1 = deslocaPonto(p0, p1, offset), linha2 = deslocaPonto(p1, p2, offset);
			lDeslocadas.add(Sketch2D.desenhaLinha(this, parent, linha1.getPontos(), false, new Configuracoes(false, Configuracoes.LINHA, 1, true, Color.RED, 255)));
			Point intersecao = linha1.pontoIntersecao(linha2);
			//Log.d("INTERSECAO GERADA: ", intersecao + "");
			pOffset.add(intersecao);
		}
		Sketch2D.setEstiloPadrao(Configuracoes.LINHA);
		Sketch2D.setCorPadrao(Color.BLACK);
		this.pOffset = (Poligono)Sketch2D.desenhaPoligono(this, parent, pOffset, false);
		for(Point p : pOffset)
		{
			Log.d("PONTOSOFFSET", p + "");
		}
	}

	int height, width;

	public void tria(Figura f, Malha malha)
	{
		if(linha != null)
		{
			Sketch2D.removeDesenho(linha);
		}
		for(Figura fig : furos)
		{
			Sketch2D.removeDesenho(fig);
		}
		if(estado == ESTADO_SEGUNDO)
		{
			int width=f.getMaior().x-f.getMenor().x;
			double proporcao = width/tamReal[0];
			int divW = (int)(proporcao*9);
			int divH = (int)(proporcao*4.5);
			if(fInicial != null)
			{
				Sketch2D.removeDesenho(fInicial);
			}
			if(fFinal != null)
			{
				Sketch2D.removeDesenho(fFinal);
			}
			malha = new Malha(Malha.ESTAGIADA, divW, divH, pInicial, pFinal, true, 15, 5);
			furos = new ArrayList<>();

			ArrayList<Point> ps = new ArrayList<>();
			ps.add(malha.getInicio());
			ps.add(malha.getFim());

			Sketch2D.setTamLinhaPadrao(1);
			linha = Sketch2D.desenhaLinha(this, parent, ps, false, new Configuracoes(false, Configuracoes.LINHA, 1, true, Color.BLACK, 255));

			Configuracoes.setEstiloPadrao(Configuracoes.LINHA);
			int xini, yini;
			Point inicio = new Point(0, 0);
			for(int i =0;i<100;i++)
			{
				for(int j = 0;j<40;j++)
				{
					Point p = new Point(i*10, j*8);
					if(f.isDentro(p))
					{
						Sketch2D.desenhaCirculo(this, parent, new Point(p.x + f.getMenor().x, p.y + f.getMenor().y), 1, false);
					}
				}
			}
			/*for(int i = 0; i <= f.getView().getHeight() / divH; i++)
			{
				if(i > 0)
				{
					xini = (int) (inicio.x + i * malha.getDistColuna() * Math.sin(malha.getAngulo()));
					yini = (int) (inicio.y - i * malha.getDistColuna() * Math.cos(malha.getAngulo()));
					for(int j = 0; j < f.getView().getWidth() / divW; j++)
					{
						Log.d("ANGULO", "--" + malha.getAngulo());
						Point p, pv;
						switch(malha.getTipo())
						{
							case Malha.ESTAGIADA:
								if(i % 2 != 0)
								{
									p = new Point((int) ((xini + j * (malha.getDistLinha() * Math.cos(malha.getAngulo()))) + (malha.getDistLinha() / 2) * Math.cos((malha.getAngulo()))), (int) ((yini + j * (malha.getDistLinha() * Math.sin(malha.getAngulo()))) + (malha.getDistLinha() / 2) * Math.sin((malha.getAngulo()))));
								}
								else
								{
									p = new Point((int) (xini + j * (malha.getDistLinha() * Math.cos(malha.getAngulo()))), (int) (yini + j * (malha.getDistLinha() * Math.sin(malha.getAngulo()))));
								}
								break;
							default:
							case Malha.QUADRATICA:
								p = new Point((int) (xini + j * (malha.getDistLinha() * Math.cos(malha.getAngulo()))), (int) (yini + j * (malha.getDistLinha() * Math.sin(malha.getAngulo()))));
								break;
						}
						pv = new Point(p);
						pv.x -= f.getView().getX();
						pv.y -= f.getView().getY();
						if(f.isDentro(pv))
							furos.add(Sketch2D.desenhaCirculo(this, parent, p, 10, false));
					}
				}
				else
				{
					xini = (int) (malha.getInicio().x + i * malha.getDistColuna() * Math.sin(malha.getAngulo()));
					yini = (int) (malha.getInicio().y - i * malha.getDistColuna() * Math.cos(malha.getAngulo()));
					for(int j = 0; j < f.getView().getWidth() / divW; j++)
					{
						Log.d("ANGULO", "--" + malha.getAngulo());
						Point p, pv, p1;
						p = new Point((int) (xini + j * (malha.getDistLinha() * Math.cos(malha.getAngulo()))), (int) (yini + j * (malha.getDistLinha() * Math.sin(malha.getAngulo()))));
						inicio.x = xini - (int)(proporcao*malha.getAltura()*Math.sin(malha.getAnguloPerfuracao())*Math.sin(malha.getAngulo()));
						inicio.y = yini + (int)(proporcao*malha.getAltura()*Math.sin(malha.getAnguloPerfuracao())*Math.cos(malha.getAngulo()));
						pv = new Point(p);
						pv.x -= f.getView().getX();
						pv.y -= f.getView().getY();
						p1 = new Point((int)(inicio.x + j * (malha.getDistLinha() * Math.cos(malha.getAngulo()))), (int)(inicio.y + j * (malha.getDistLinha() * Math.sin(malha.getAngulo()))));
						if(f.isDentro(pv))
						{
							furos.add(Sketch2D.desenhaCirculo(this, parent, p, 10, false));
							furos.add(Sketch2D.desenhaCirculo(this, parent, p1, 10, false, new Configuracoes(true, Configuracoes.LINHA, 1, true, Color.BLACK, 180)));
							ArrayList<Point> arr = new ArrayList<>();
							arr.add(p);
							arr.add(p1);
							Sketch2D.desenhaLinha(this, parent, arr, false, new Configuracoes(true, Configuracoes.LINHA, 1, true, Color.BLACK, 180));
						}
					}
				}
			}*/
			Configuracoes.setCorPadrao(Color.BLACK);
		}
	}

	int divW, divH;

	public void criaOffset(Poligono f, float dist)
	{
		ArrayList<Point> pontosOffSet = new ArrayList<>();
		/*for(int i = 1;i<=f.getPontos().size();i++)
		{
			int index = i, indexAnterior = i-1;
			if(i == f.getPontos().size())
			{
				index = 0;
			}
			Point ponto = f.getPontos().get(index);
			Point pAnterior = f.getPontos().get(indexAnterior);
			Point novo = new Point(pAnterior);
			double dify = ponto.y - pAnterior.y, difx = ponto.x - pAnterior.x;
			double angulo = Math.atan(dify/difx);
			if(ponto.x < pAnterior.x)
			{
				novo.y += dist*Math.cos(angulo);
				if(ponto.y > pAnterior.y)
				{
					novo.x -= dist*Math.sin(angulo);
				}
				else if(ponto.y < pAnterior.y)
				{
					novo.x += dist*Math.sin(angulo);
				}
			}
			else
			{
				novo.y -= dist*Math.cos(angulo);
				if(ponto.y > pAnterior.y)
				{
					novo.x += dist*Math.sin(angulo);
				}
				else if(ponto.y < pAnterior.y)
				{
					novo.x -= dist*Math.sin(angulo);
				}
			}
			//Log.d("PONTOSOFFSET", novo + "");
			Log.d("ANGULO", angulo + "");
			pontosOffSet.add(novo);
		}*/
		for(Point p : f.getPontos())
		{
			pontosOffSet.add(new Point(p));
		}
		for(int i = 0;i<f.getPontos().size();i++)
		{
			int index = i+1, iAnterior = i;
			if(index == f.getPontos().size())
			{
				index = 0;
			}
			Point ponto = f.getPontos().get(index);
			Point pAnterior = f.getPontos().get(iAnterior);
			Point novo = new Point(pAnterior);
			if(ponto.x < pAnterior.x)
			{
				pontosOffSet.get(index).y += dist;
				pontosOffSet.get(iAnterior).y += dist;
				if(ponto.y > pAnterior.y)
				{
					novo.x -= dist;
				}
				else if(ponto.y < pAnterior.y)
				{
					novo.x += dist;
				}
			}
			else
			{
				novo.y -= dist;
				if(ponto.y > pAnterior.y)
				{
					novo.x += dist;
				}
				else if(ponto.y < pAnterior.y)
				{
					novo.x -= dist;
				}
			}
		}
		Sketch2D.setEstiloPadrao(Configuracoes.LINHA);
		Sketch2D.setTamLinhaPadrao(3);
		//Sketch2D.desenhaPoligono(NovaMain.this, parent, pontosOffSet, false);
		ArrayList<Integer> colors = new ArrayList<>();
		colors.add(Color.RED);
		colors.add(Color.GRAY);
		colors.add(Color.GREEN);
		colors.add(Color.YELLOW);
		colors.add(Color.BLACK);
		colors.add(Color.BLUE);
		colors.add(Color.CYAN);
		for(int i = 0;i<pontosOffSet.size()-1;i++)
		{
			ArrayList<Point> p = new ArrayList<>();
			p.add(new Point(pontosOffSet.get(i)));
			Log.d("PONTOS", "Pontox: " + pontosOffSet.get(i));
			if(i == pontosOffSet.size()-2)
			{
				p.add(new Point(pontosOffSet.get(0)));
			}
			else
			{
				p.add(new Point(pontosOffSet.get(i + 1)));
			}
			Sketch2D.desenhaLinha(this, parent, p, false, new Configuracoes(false, Configuracoes.LINHA, 1, true, colors.get(i%7), 255));
		}
	}

	@Override
	public void onResume()
	{
		super.onResume();
		Log.d("ON RESUME: ", "QUANTIDADE DE FIGURAS: " + Singleton.getInstance().getFiguras().size());
		FrameLayout ln = (FrameLayout) findViewById(R.id.lnDesenho);
		figura = geraFiguras(getTamanho(), ln);
		//geraMalha(poligono);
	}
	public Point getTamanho()
	{
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;

		return new Point(width, height-120);
	}

	public Figura geraFiguras(Point tamanho,FrameLayout ln)
	{
		Point center = new Point(tamanho.x/2,tamanho.y/2);
		int tela_menor;
		boolean eixoy = false;
		tela_menor = tamanho.x;
		if(tamanho.y < tamanho.x)
		{
			tela_menor = tamanho.y;
			eixoy = true;
		}
		ArrayList<ArrayList<float[]>> todosPontos = drawB_GN0930();

		ArrayList<ArrayList<Point>> allPoints = new ArrayList<>();

		/*ArrayList<Point> pontos = new ArrayList<>();
		pontos.add(new Point(100, 700));
		pontos.add(new Point(350, 700));
		pontos.add(new Point(600, 700));
		pontos.add(new Point(600, 450));
		pontos.add(new Point(600, 200));
		pontos.add(new Point(350, 50));
		pontos.add(new Point(100, 200));*/



		double maiory, maiorx, menorx, menory;
		maiorx = menorx=todosPontos.get(0).get(0)[0];
		maiory = menory=todosPontos.get(0).get(0)[1];
		//maiorx = menorx = pontos.get(0).x;
		//maiory = menory = pontos.get(0).y;
		ArrayList<Point> pontos = new ArrayList<>();
		for(ArrayList<float[]> ps : todosPontos)
		{
			for(float[] p : ps)
			{
				if(p[0]<menorx)
					menorx=p[0];
				else if(p[0]>maiorx)
					maiorx=p[0];
				if(p[1]<menory)
					menory=p[1];
				else if(p[1]>maiory)
					maiory=p[1];
			}
		}
		for(Point p : pontos)
		{
			if(p.x < menorx) menorx = p.x;
			else if(p.x > maiorx) maiorx = p.x;
			if(p.y < menory) menory = p.y;
			else if(p.y > maiory) maiory = p.y;
		}
		widthReal = maiorx - menorx;
		heightReal = maiory - menory;
		Log.d("REAL", "WIDTH: " + widthReal + "//HEIGHT: " + heightReal);
		double mxdif=0,mydif=0;
		double tamx = maiorx-menorx;
		double tamy = maiory-menory;
		Log.d("Main","tamanho x = "+tamx+" tamy = "+tamy+" scale = "+tela_menor/tamy);

		double tam = tamx;
		tamReal[0] = tamx;
		if(eixoy)
			tam = tamy;
		int d = (int)(tela_menor/tam);
		int scale = d-1;
		//TODO: REMOVER SE DER ERRADO
		double dx = center.x-(tamx*scale/2.0);
		double dy = center.y -(tamy*scale/2.0);
		Log.d("MENORX", menorx + "");

		for(ArrayList<float[]> ps : todosPontos)
		{
			pontos = new ArrayList<>();
			for(int i = 0;i<ps.size();i++)
			{
				float[] p = ps.get(i);
				float difx =(float)(p[0]-menorx);
				float dify = (float)((p[1]-maiory)*(-1.0));
				float[] difs = new float[2];
				Log.d("Valores","x= " + p[0] + " difx = "+difx+" dify = "+dify + " menorx = " + menorx);
				difs[0]=difx;
				difs[1]=dify;
				p=difs;

				difx*=scale;
				dify*=scale;

				difx+=dx;
				dify+=dy;

				Log.d("Valores","x= " + p[0] + " difx = "+difx+" dify = "+dify);

				if(difx>mxdif)
					mxdif=difx;
				if(dify>mydif)
					mydif = dify;



				int mult=1;
				int multx=1;
				float multy=1f;
				Point aux = new Point((int)(difx*mult*multx),(int)(dify*mult*multy));
				if(pontos.size() > 0)
				{
					if(!(pontos.get(pontos.size()-1).x == aux.x) && !(pontos.get(pontos.size()-1).y == aux.y))
					{
						Log.d("AUX", aux + "");
						pontos.add(aux);
					}
				}
				else
				{
					pontos.add(aux);
				}
			}

			allPoints.add(pontos);
		}
		ArrayList<Integer> colors = new ArrayList<>();
		colors.add(Color.RED);
		colors.add(Color.GRAY);
		colors.add(Color.GREEN);
		colors.add(Color.YELLOW);
		colors.add(Color.BLACK);
		colors.add(Color.BLUE);
		colors.add(Color.CYAN);


		for(int i=0;i<1;i++)
		{
			//Log.i("PontosH", "" + allPoints.get(i).size());
			Configuracoes c = new Configuracoes();
			c.setEstilo(1);
			c.setCor(colors.get(i));
			poligono = Sketch2D.desenhaPoligono(this, ln, allPoints.get(0), false, c);
		}
		//Configuracoes c = new Configuracoes();
		//c.setEstilo(Configuracoes.LINHA);
		//poligono = Sketch2D.desenhaPoligono(this, ln, pontos, false, c);

		for(int i = 0;i<allPoints.get(0).size();i++)
		{
			ArrayList<Point> p = new ArrayList<>();
			p.add(new Point(allPoints.get(0).get(i)));
			Log.d("PONTOS", "Pontox: " + allPoints.get(0).get(i));
			if(i == allPoints.get(0).size()-1)
			{
				p.add(new Point(allPoints.get(0).get(0)));
			}
			else
			{
				p.add(new Point(allPoints.get(0).get(i + 1)));
			}
			Sketch2D.desenhaLinha(this, parent, p, false, new Configuracoes(false, Configuracoes.LINHA, 1, true, colors.get(i%7), 255));
		}
		//return pontos;
		return poligono;
	}

	double tamReal[] = new double[2];
	ArrayList<Integer> indexs;
	char separator;
	ArrayList<Integer> colors;
	Figura poligono;
	double widthReal = 0, heightReal = 0, widthPequeno = 0, heightPequeno = 0;

	public ArrayList<ArrayList<float[]>> drawB_GN0930()
	{
		int sub=0;
		colors = new ArrayList<>();
		indexs = new ArrayList<>();
		/*separator=';';
		ArrayList<String> strings = new ArrayList<>();
		strings.add("a;669871.854121;7803644.542408 ");
		strings.add("a;669867.348095;7803642.443725 ");
		strings.add("a;669848.343115;7803637.172918 ");
		strings.add("a;669849.94308;7803631.536003 ");
		strings.add("a;669869.581005;7803635.883796 ");
		strings.add("a;669879.77311;7803635.039376 ");
		strings.add("a;669884.701155;7803635.38447 ");
		strings.add("a;669888.646106;7803636.547694 ");
		strings.add("a;669893.358909;7803638.450772 ");
		strings.add("a;669895.283493;7803639.31569 ");
		strings.add("a;669900.214522;7803641.846586 ");
		strings.add("a;669914.637291;7803648.296346 ");
		strings.add("a;669930.552878;7803655.491521 ");
		strings.add("a;669918.567666;7803688.258446 ");
		strings.add("a;669899.413408;7803670.888306 ");
		strings.add("a;669886.898728;7803660.675813 ");
		strings.add("a;669871.854121;7803644.542408 ");*/

		separator=',';
		ArrayList<String> strings = new ArrayList<>();
		//strings.add("75,670317.318,7803189.819,908.896,boundary");
		/*strings.add("76,670316.533,7803191.076,908.863,boundary");
		strings.add("77,670311.299,7803183.229,908.669,boundary");
		strings.add("78,670316.941,7803176.041,908.710,boundary");
		//strings.add("79,670323.185,7803170.247,908.741,boundary");
		strings.add("80,670323.169,7803170.205,908.735,boundary");
		strings.add("81,670330.062,7803172.314,908.706,boundary");
		strings.add("82,670335.862,7803169.936,908.854,boundary");
		strings.add("83,670341.369,7803166.857,909.223,boundary");
		strings.add("84,670347.693,7803158.624,909.272,boundary");
		strings.add("85,670352.682,7803149.369,909.136,boundary");
		strings.add("86,670357.418,7803140.145,908.959,boundary");
		strings.add("87,670363.066,7803131.579,908.925,boundary");
		strings.add("88,670369.919,7803126.295,908.896,boundary");
		strings.add("89,670374.398,7803117.303,908.920,boundary");
		strings.add("90,670378.372,7803103.659,908.296,boundary");
		strings.add("91,670384.097,7803109.072,908.562,boundary");
		strings.add("92,670389.074,7803111.095,908.612,boundary");
		strings.add("93,670396.625,7803109.465,908.299,boundary");
		strings.add("94,670412.386,7803113.140,908.372,boundary");
		strings.add("95,670409.885,7803122.427,908.408,boundary");
		strings.add("96,670419.723,7803129.956,908.403,boundary");
		strings.add("97,670435.952,7803143.318,908.485,boundary");
		strings.add("98,670432.872,7803153.688,908.379,boundary");
		strings.add("99,670420.630,7803164.804,908.377,boundary");
		strings.add("100,670410.513,7803163.256,908.134,boundary");
		strings.add("101,670399.392,7803153.604,908.228,boundary");
		//TODO: CORRIGIR ESCALA (tirar o comentário - as duas linhas seguintes são praticamente o mesmo ponto)
		//strings.add("102,670384.881,7803162.663,908.642,boundary");
		strings.add("103,670384.871,7803162.699,908.646,boundary");
		strings.add("104,670368.242,7803205.794,909.139,boundary");
		strings.add("105,670326.056,7803192.921,909.195,boundary");
		strings.add("106,670324.598,7803200.939,909.135,boundary");
		strings.add("107,670314.745,7803199.850,908.851,boundary");*/
		//strings.add("108,670316.570,7803190.829,908.902,boundary");
		/*strings.add("100, 100, 100, 100, 100");
		strings.add("101, 200, 100, 100, 100");
		strings.add("102, 200, 200, 100, 100");
		strings.add("103, 100, 200, 100, 100");*/

		//OFFSET BUGADO
		/*strings.add("100, 100, 100, 100, 100");
		strings.add("100, 150, 50, 100, 100");
		strings.add("101, 200, 100, 100, 100");
		strings.add("102, 208, 207, 100, 100");
		strings.add("103, 109, 200, 100, 100");*/

		/*strings.add("100, 100, 100, 100, 100");
		strings.add("101, 150, 50, 100, 100");
		strings.add("102, 200, 100, 100, 100");
		strings.add("103, 200, 200, 100, 100");
		strings.add("104, 100, 200, 100, 100");*/


		strings.add("62,670360.236,7803785.472,919.392,11.00");
		strings.add("63,670354.942,7803785.350,919.399,11.00");
		strings.add("64,670348.331,7803785.332,919.579,11.00");
		//strings.add("65,670348.670,7803779.874,919.531,11.00");
		strings.add("66,670350.204,7803770.948,919.437,11.00");
		strings.add("67,670353.010,7803769.202,919.459,11.00");
		strings.add("68,670357.011,7803768.121,919.435,11.00");
		strings.add("69,670367.237,7803765.403,918.818,11.00");
		strings.add("70,670374.130,7803764.474,918.306,11.00");
		strings.add("71,670383.770,7803761.554,917.802,11.00");
		strings.add("72,670390.621,7803758.963,917.411,11.00");
		strings.add("73,670396.816,7803756.596,917.405,11.00");
		strings.add("74,670403.836,7803753.905,917.168,11.00");
		strings.add("75,670410.393,7803751.112,916.950,11.00");
		strings.add("76,670416.032,7803748.273,916.607,11.00");
		strings.add("77,670419.123,7803746.539,916.450,11.00");
		strings.add("78,670424.286,7803743.078,916.063,11.00");
		strings.add("79,670426.673,7803741.312,915.626,11.00");
		strings.add("80,670432.191,7803737.706,914.542,11.00");
		strings.add("81,670443.878,7803730.686,914.088,11.00");
		strings.add("82,670446.295,7803738.215,914.404,11.00");
		strings.add("83,670448.737,7803744.719,915.002,11.00");
		strings.add("84,670450.549,7803751.424,915.176,11.00");
		strings.add("85,670451.874,7803757.625,915.716,11.00");
		strings.add("86,670452.030,7803766.698,916.447,11.00");
		strings.add("87,670452.832,7803770.149,916.647,11.00");
		strings.add("88,670458.074,7803780.324,916.656,11.00");
		strings.add("89,670458.084,7803780.325,916.653,11.00");
		strings.add("90,670448.571,7803785.730,917.104,11.00");
		strings.add("91,670440.857,7803768.863,917.098,11.00");
		strings.add("92,670436.643,7803768.892,917.246,9.55");
		strings.add("93,670426.939,7803772.593,917.881,9.55");
		strings.add("94,670417.509,7803774.407,918.137,10.50");
		strings.add("95,670398.171,7803779.686,917.932,10.20");
		strings.add("96,670389.632,7803782.190,917.932,10.20");
		strings.add("97,670379.403,7803785.153,918.386,10.25");
		strings.add("98,670371.129,7803786.285,918.586,10.25");
		strings.add("99,670367.525,7803786.823,918.765,10.25");
		strings.add("100,670360.176,7803785.485,919.308,10.25");

		indexs.add(strings.size()-sub);
		sub=strings.size();
		colors.add(Color.RED);


		ArrayList<ArrayList<float[]>> todosPontos = new ArrayList<>();
		ArrayList<float[]> a = new ArrayList<>();
		for(int i=0;i<strings.size();i++)
		{
			a.add(getPontoUTM(strings.get(i)));
		}

		todosPontos.add(a);

		return todosPontos;
	}

	public float[] getPontoUTM(String ponto)
	{
		String posicoes[] = ponto.split(""+separator);

		ArrayList<float[]> p = new ArrayList<>();
		float a=Float.parseFloat(posicoes[1]);
		float b=Float.parseFloat(posicoes[2]);
		float[] point =new float[2];
		point[0]=a;
		point[1]=b;
		p.add(point);

		return point;
	}
}
