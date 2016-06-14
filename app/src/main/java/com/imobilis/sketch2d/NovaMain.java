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
import android.widget.FrameLayout;

import com.imobilis.sketch2dframework.Configuracoes;
import com.imobilis.sketch2dframework.Figura;
import com.imobilis.sketch2dframework.Linha;
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

	@Override
	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.activity_main);
		parent = (SketchParent)findViewById(R.id.lnDesenho);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		//inflater.inflate(R.menu.menu_main, menu);
		inflater.inflate(R.menu.menu_unidades, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.dp:
				Sketch2D.setUnidade(Sketch2D.UNIDADE_DP);
				Configuracoes.setCorPadrao(Color.RED);
				Configuracoes.refresh(parent);
				Point p = getTamanho();
				DisplayMetrics dm = getResources().getDisplayMetrics();
				widthPequeno = poligono.getView().getWidth();
				heightPequeno = poligono.getView().getHeight();

				widthPequeno = (widthPequeno/dm.densityDpi)*2.5/100;
				heightPequeno = (heightPequeno/dm.densityDpi)*2.5/100;

				Log.d("ESCALA", "Width Real= " + widthReal + "m//Width pequeno = " + widthPequeno + "m//Escala width = " + widthPequeno/widthReal + " ////// Height real = " + heightReal + "m//Height pequeno = " + heightPequeno + "m//Escala height =  " + heightPequeno / heightReal);

				//escalaw = widthReal/widthPequeno;
				//escalah = heightReal/heightPequeno;
				Malha malha = new Malha(Malha.QUADRATICA, divW, divH, new Point(0, 0), new Point(100, 100), true);
				//tria(poligono, malha);
				tria(poligono, Malha.ESTAGIADA);
				break;
			case R.id.cm:
				Sketch2D.setUnidade(Sketch2D.UNIDADE_CM);
				Configuracoes.setCorPadrao(Color.GREEN);
				Configuracoes.refresh(parent);
				tria(poligono, Malha.QUADRATICA);
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
				break;
		}
		parent.invalidate();
		return super.onOptionsItemSelected(item);
	}

	int height, width;

	/*public void tria(Figura f, Malha malha)
	{
		//Point ini = new Point(f.getPontos().get(2).x,f.getPontos().get(0).y);
		Point ini = new Point(f.getMenor().x,f.getMenor().y);
		Configuracoes.setCorPadrao(Color.RED);
		Configuracoes.setEstiloPadrao(Configuracoes.LINHA);
		for(int i=2;i<height/malha.getDx();i++)
		{
			for(int j =2;j<width/malha.getDy();j++)
			{
				Point pt = new Point((int)(j*malha.getDx()),(int)(i*malha.getDy()));
				Point p = new Point(ini.x+pt.x,ini.y+pt.y);
				if(f.isDentro(pt))
					Sketch2D.desenhaCirculo(this, (FrameLayout) findViewById(R.id.lnDesenho), p, 4, true);
			}
		}
		Configuracoes.setCorPadrao(Color.BLACK);
	}*/

	public void tria(Figura f, int tipo)
	{
		//Point ini = new Point(f.getPontos().get(2).x,f.getPontos().get(0).y);
		Point ini = new Point(f.getMenor().x,f.getMenor().y);

		//Point fim = new Point(f.getMaior().x,f.getMaior().y-300);
		//Point inicio = new Point(0,f.getMaior().y);
		Point fim = new Point(647,615);
		Point inicio = new Point(350,744);

		ArrayList<Point> ps = new ArrayList<>();
		ps.add(inicio);
		ps.add(fim);
		double delx = (fim.x - inicio.x);
		double dely = (fim.y - inicio.y);
		double angulo = Math.atan(dely/delx);
		angulo = Math.abs(angulo);
		double hipotenusa = Math.sqrt(Math.pow(fim.y - inicio.y, 2) + Math.pow(fim.x - inicio.x, 2));
		double dx = divH*Math.cos(angulo);
		double dy = divW*Math.sin(angulo);

		Log.d("PONTOSD","delx = "+delx+" dely = "+dely+" div ="+ dely/delx+"divH = "+divH+" divW = "+divW);
		Log.d("PONTOSD", "dx = " + dx + " hip = " + hipotenusa + " angle = " + angulo + "fim = " + fim.toString() + " inicio = " + inicio.toString());


		Sketch2D.setTamLinhaPadrao(1);
		Sketch2D.desenhaLinha(this, parent, ps, false, new Configuracoes(false, Configuracoes.LINHA, 1, true, Color.BLACK, 255));
		Configuracoes.setCorPadrao(Color.RED);
		Configuracoes.setEstiloPadrao(Configuracoes.LINHA);
		//dy=0;
		int mult=0;
		int somax=0;
		int somay=0;
		int dif=(f.getMaior().y - f.getMenor().y);
		//int tipo = Malha.ESTAGIADA;
		for(int i=1;i<poligono.getView().getHeight()/divH;i++)
		{
			somay=0;

			for(int j =1;j<poligono.getView().getWidth()/divW;j++)
			{
				int ys;
				int xs;
				switch(tipo)
				{
					case Malha.ESTAGIADA:
						if(i%2 == 0)
						{
							xs = j*divW+somax;
							ys = (i*divH)+somay+mult*(int)dy;
						}
						else
						{
							xs = j*divW+somax + divW/2;
							ys = (i*divH)+somay+mult*(int)dy -divH/2;
						}
						break;
					case Malha.QUADRATICA:
					default:
						xs = j*divW+somax;
						ys = (i*divH)+somay+mult*(int)dy;
						break;
				}
				//int ys = (i*divH)+soma;
				Point pt = new Point(xs,ys);
				Point p = new Point(ini.x+xs,ini.y+ys);
				if(f.isDentro(pt))
				{
					if(pt.y>0)
					{
						Sketch2D.desenhaCirculo(this,parent,p,4,false);
					}
				}

				somay-=dy;
			}

			somax+=10;
		}
		Configuracoes.setCorPadrao(Color.BLACK);
	}

	int divW, divH;

	@Override
	public void onResume()
	{
		super.onResume();
		Log.d("ON RESUME: ", "QUANTIDADE DE FIGURAS: " + Singleton.getInstance().getFiguras().size());
		FrameLayout ln = (FrameLayout) findViewById(R.id.lnDesenho);
		Figura f = geraFiguras(getTamanho(), ln);
		width=f.getMaior().x-f.getMenor().x;
		height=Math.abs(f.getMaior().y - f.getMenor().y);
		double proporcao = width/tamReal[0];
		divW = (int)(proporcao*9);
		divH = (int)(proporcao*4.5);
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
		double maiory, maiorx, menorx, menory;
		maiorx = menorx=todosPontos.get(0).get(0)[0];
		maiory = menory=todosPontos.get(0).get(0)[1];
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

		for(ArrayList<float[]> ps : todosPontos)
		{
			pontos = new ArrayList<>();
			for(float[] p: ps)
			{
				float difx =(float)(p[0]-menorx);
				float dify = (float)((p[1]-maiory)*(-1.0));
				float[] difs = new float[2];
				difs[0]=difx;
				difs[1]=dify;
				p=difs;

				difx*=scale;
				dify*=scale;

				difx+=dx;
				dify+=dy;

				Log.d("Valores","difx = "+difx+" dify = "+dify);

				if(difx>mxdif)
					mxdif=difx;
				if(dify>mydif)
					mydif = dify;



				int mult=1;
				int multx=1;
				float multy=1f;
				Point aux = new Point((int)(difx*mult*multx),(int)(dify*mult*multy));
				pontos.add(aux);
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
			Log.i("PontosH", "" + allPoints.get(i).size());
			Configuracoes c = new Configuracoes();
			c.setEstilo(1);
			//c.setCor(colors.get(i));
			poligono = Sketch2D.desenhaPoligono(this, ln, allPoints.get(0), false, c);
		}

		for(int i = 0;i<allPoints.get(0).size()-1;i++)
		{
			ArrayList<Point> p = new ArrayList<>();
			p.add(new Point(allPoints.get(0).get(i)));
			Log.d("PONTOS", "Pontox: " + allPoints.get(0).get(i));
			if(i == allPoints.get(0).size()-2)
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
		separator=';';
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
		strings.add("a;669871.854121;7803644.542408 ");

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
