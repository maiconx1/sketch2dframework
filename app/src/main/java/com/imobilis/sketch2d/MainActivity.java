package com.imobilis.sketch2d;


	import android.os.Bundle;
	import android.support.v7.app.AppCompatActivity;

	import android.graphics.Color;
	import android.graphics.Point;
	import android.util.DisplayMetrics;
	import android.util.Log;
	import android.view.Display;
	import android.view.Menu;
	import android.view.MenuInflater;
	import android.view.MenuItem;
	import android.view.MotionEvent;
	import android.widget.FrameLayout;
	import android.widget.SeekBar;

	import com.imobilis.sketch2dframework.Configuracoes;
	import com.imobilis.sketch2dframework.Figura;
	import com.imobilis.sketch2dframework.Singleton;
	import com.imobilis.sketch2dframework.Sketch2D;
	import com.imobilis.sketch2dframework.SketchParent;

	import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
	ArrayList<Figura> figuras;
	SketchParent parent;

	double escalaw = 1, escalah = 1;

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

				escalaw = widthReal/widthPequeno;
				escalah = heightReal/heightPequeno;
				geraMalha(poligono);
				break;
			case R.id.cm:
				Sketch2D.setUnidade(Sketch2D.UNIDADE_CM);
				Configuracoes.setCorPadrao(Color.GREEN);
				Configuracoes.refresh(parent);
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

	private void desenhaTudo()
	{
		ArrayList<Point> pontos = new ArrayList<>();
		pontos.add(new Point(600, 600));

		Sketch2D.desenhaCirculo(this, (FrameLayout) findViewById(R.id.lnDesenho), true);
		Sketch2D.desenhaCirculo(this, (FrameLayout) findViewById(R.id.lnDesenho), new Point(600, 600), 90, true, new Configuracoes(false, Configuracoes.LINHA, 2, true, Color.BLACK, 255));
		Sketch2D.desenhaCirculo(this, (FrameLayout) findViewById(R.id.lnDesenho), true, new Configuracoes(true, Configuracoes.PREENCHIDO, 5, true, Color.YELLOW, 200));
		Sketch2D.desenhaPoligono(this, (FrameLayout) findViewById(R.id.lnDesenho), Sketch2D.TIPO_QUADRADO, false);
		Sketch2D.desenhaPoligono(this, (FrameLayout) findViewById(R.id.lnDesenho), Sketch2D.TIPO_TRIANGULO, true);

		pontos = new ArrayList<>();
		pontos.add(new Point(100, 100));
		Sketch2D.desenhaCirculo(this, (FrameLayout) findViewById(R.id.lnDesenho), new Point(100, 100), (float) 50.0, true, new Configuracoes(false, Configuracoes.PREENCHIDO, 1, true, Color.RED, 255));
		pontos = new ArrayList<>();
		pontos.add(new Point(200, 200));
		pontos.add(new Point(250, 200));
		pontos.add(new Point(275, 250));
		pontos.add(new Point(250, 250));
		pontos.add(new Point(250, 275));
		pontos.add(new Point(200, 300));
		Sketch2D.desenhaPoligono(this, (FrameLayout) findViewById(R.id.lnDesenho), pontos, false, new Configuracoes(true, Configuracoes.LINHA, 2, true, Color.BLUE, 255, 1, 1));
		pontos = new ArrayList<>();
		pontos.add(new Point(200, 400));
		Sketch2D.desenhaCirculo(this, (FrameLayout) findViewById(R.id.lnDesenho), new Point(200, 400), (float) 80.0, true, new Configuracoes(true, Configuracoes.PREENCHIDO, 10, true, Color.GREEN, 100, 1, 1));
		pontos = new ArrayList<>();
		pontos.add(new Point(300, 300));
		pontos.add(new Point(350, 300));
		pontos.add(new Point(575, 350));
		pontos.add(new Point(350, 350));
		pontos.add(new Point(350, 375));
		pontos.add(new Point(300, 600));
		Sketch2D.desenhaPoligono(this, (FrameLayout) findViewById(R.id.lnDesenho), pontos, true, new Configuracoes(false, Configuracoes.PREENCHIDO, 1, true, Color.RED, 255));
		pontos = new ArrayList<>();
		pontos.add(new Point(250, 0));
		pontos.add(new Point(300, 200));
		pontos.add(new Point(200, 200));
		Sketch2D.desenhaPoligono(this, (FrameLayout) findViewById(R.id.lnDesenho), pontos, true, new Configuracoes(false, Configuracoes.LINHA, 3, true, Color.BLACK, 255));

		Sketch2D.desenhaCirculo(this, (FrameLayout) findViewById(R.id.lnDesenho), true);

		Sketch2D.desenhaLinha(this, (FrameLayout) findViewById(R.id.lnDesenho), new ArrayList<Point>()
		{{
				add(new Point(50, 50));
				add(new Point(150, 200));
			}}, true, new Configuracoes(false, Configuracoes.LINHA, 5, true, Color.BLACK, 255));
		Sketch2D.desenhaLinha(this, (FrameLayout) findViewById(R.id.lnDesenho), new ArrayList<Point>()
		{{
				add(new Point(150, 200));
				add(new Point(300, 100));
			}}, true, new Configuracoes(false, Configuracoes.LINHA, 5, true, Color.RED, 255));
		Sketch2D.desenhaLinha(this, (FrameLayout) findViewById(R.id.lnDesenho), new ArrayList<Point>()
		{{
				add(new Point(300, 100));
				add(new Point(250, 50));
			}}, true, new Configuracoes(false, Configuracoes.LINHA, 5, true, Color.BLUE, 255));
		Sketch2D.desenhaLinha(this, (FrameLayout) findViewById(R.id.lnDesenho), new ArrayList<Point>()
		{{
				add(new Point(250, 50));
				add(new Point(250, 550));
			}}, true, new Configuracoes(false, Configuracoes.LINHA, 5, true, Color.YELLOW, 255));
		Sketch2D.desenhaLinha(this, (FrameLayout) findViewById(R.id.lnDesenho), new ArrayList<Point>()
		{{
				add(new Point(100, 700));
				add(new Point(250, 450));
			}}, true, new Configuracoes(false, Configuracoes.LINHA, 5, true, Color.CYAN, 255));
		Sketch2D.desenhaLinha(this, (FrameLayout) findViewById(R.id.lnDesenho), new ArrayList<Point>()
		{{
				add(new Point(350, 450));
				add(new Point(200, 700));
			}}, true, new Configuracoes(false, Configuracoes.LINHA, 5, true, Color.CYAN, 255));

		Sketch2D.desenhaLinha(this, (FrameLayout) findViewById(R.id.lnDesenho), new ArrayList<Point>()
		{{
				add(new Point(350, 450));
				add(new Point(200, 450));
			}}, true, new Configuracoes(false, Configuracoes.LINHA, 5, true, Color.GREEN, 255));

		Sketch2D.desenhaLinha(this, (FrameLayout)findViewById(R.id.lnDesenho), false);
	}

	public int converteM2P(float metros)
	{
		DisplayMetrics dm = getResources().getDisplayMetrics();
		return (int)(dm.densityDpi*0.025*metros);
	}

	public void geraMalha(Figura f)
	{
		Malha malha = new Malha(Malha.QUADRATICA, converteM2P(4.5f), converteM2P(9f), new Point((int)poligono.getView().getX(), (int)poligono.getView().getY()), new Point(100, 100), true);

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;

		int divW, divH;

		width=f.getMaior().x-f.getMenor().x;
		height=Math.abs(f.getMaior().y - f.getMenor().y);
		double proporcao = width/tamReal[0];
		divW = (int)(proporcao*4.5);
		divH = (int)(proporcao*9);

		Sketch2D.setEstiloPadrao(Configuracoes.LINHA);

		ArrayList<Point> furos = new ArrayList<>();
		furos.add(malha.getInicio());
		Point point;
		Log.d("PONTOS", "WidthPequeno: " + widthPequeno + "//Dist Linhas: " + malha.getDistLinha() + "//Dist Colunas: " + malha.getDistColuna());
		for(int i = 0;i<widthReal/malha.getDistLinha();i++)
		{
			for(int j = 0;j<heightReal/malha.getDistColuna();j++)
			{
				point = new Point((int)(furos.get(0).x + i*malha.getDistColuna()), (int)(furos.get(0).y + j*malha.getDistLinha()));
				Point np = new Point((int)(point.x - poligono.getView().getX()), (int)(point.y - poligono.getView().getY()));
				Log.d("PONTO", np + "");
				if(poligono.isDentro(np) && poligono.getView().getX() <= point.x && point.x <= poligono.getView().getWidth() + poligono.getView().getX() && poligono.getView().getY() <= point.y && point.y <= poligono.getView().getHeight() + poligono.getView().getY())
				{
					furos.add(point);
				}
			}
		}

		for(Point p : furos)
		{
			Sketch2D.desenhaCirculo(this, parent, p, 10, true);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		parent = (SketchParent) findViewById(R.id.lnDesenho);
		figuras = new ArrayList<>();
		Sketch2D.setClasseConfiguracao(ConfActivity.class);

		//geraFiguras(getTamanho(), parent);
		/*Sketch2D.desenhaLinha(this, (FrameLayout) findViewById(R.id.lnDesenho), new ArrayList<Point>()
		{{
				add(new Point(50, 50));
				add(new Point(50, 200));
			}}, true, new Configuracoes(false, Configuracoes.LINHA, 5, true, Color.BLACK, 255));

		Sketch2D.desenhaLinha(this, (FrameLayout) findViewById(R.id.lnDesenho), new ArrayList<Point>()
		{{
				add(new Point(250, 250));
				add(new Point(50, 250));
			}}, true);

		ArrayList<Point> p = new ArrayList<>();
		p.add(new Point(0, 200));
		p.add(new Point(100, 0));
		p.add(new Point(200, 200));
		Sketch2D.desenhaPoligono(this, parent, p, true);

		Sketch2D.desenhaLinha(this, (FrameLayout) findViewById(R.id.lnDesenho), new ArrayList<Point>()
		{{
				add(new Point(100, 700));
				add(new Point(250, 450));
			}}, true, new Configuracoes(false, Configuracoes.LINHA, 5, true, Color.CYAN, 255));
		Sketch2D.desenhaLinha(this, (FrameLayout) findViewById(R.id.lnDesenho), new ArrayList<Point>()
		{{
				add(new Point(700, 150));
				add(new Point(700, 700));
			}}, true, new Configuracoes(false, Configuracoes.LINHA, 5, true, Color.CYAN, 255));

		Sketch2D.setEstiloPadrao(Configuracoes.LINHA);
		Sketch2D.desenhaCirculo(this, (FrameLayout) findViewById(R.id.lnDesenho), true);
		Sketch2D.desenhaCirculo(this, (FrameLayout)findViewById(R.id.lnDesenho), true, new Configuracoes());
		Sketch2D.setEstiloPadrao(Configuracoes.PREENCHIDO);
		Sketch2D.desenhaCirculo(this, (FrameLayout)findViewById(R.id.lnDesenho), true);
		desenhaTudo();*/
		/*((SeekBar) findViewById(R.id.skbEscala)).setProgress(10);
		((SeekBar)findViewById(R.id.skbEscala)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
				float escala = 0.1f + (float) progress / 10;
				for(Figura f : Sketch2D.getFiguras())
				{
					f.setEscala(escala);
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar)
			{
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar)
			{
			}
		});*/
	}

	/*ArrayList<Figura> figuras;
	ArrayList<Integer> indexs;
	Point center;
	int tela_menor;
	boolean eixoy=false;
	float scale = 1f;
	char separator=',';
	float escala = 1;
	ArrayList<Integer> colors = new ArrayList<>();

	public ArrayList<ArrayList<double[]>> drawEL0930()
	{
		int sub=0;
		separator=',';
		ArrayList<String> strings = new ArrayList<>();
		strings.add("1,671127.486,7803855.256,923.665,b ");
		strings.add("2,671127.673,7803826.603,923.415,b ");
		strings.add("3,671124.560,7803811.311,923.913,b ");
		//strings.add("25,671127.354,7803855.285,923.643,b ");
		strings.add("4,671124.369,7803810.939,924.006,bf ");
		strings.add("5,671120.815,7803812.355,924.275,bf ");
		strings.add("6,671118.929,7803812.515,924.468,bf ");
		strings.add("7,671114.048,7803815.078,925.008,bf ");
		strings.add("8,671109.223,7803816.515,925.503,bf ");
		strings.add("9,671106.404,7803817.608,925.466,bf ");
		strings.add("10,671099.641,7803818.531,925.610,bf ");
		strings.add("11,671094.564,7803818.925,926.052,bf ");
		strings.add("12,671088.599,7803821.641,926.522,bf ");
		strings.add("13,671078.959,7803823.672,927.504,bf ");
		strings.add("14,671079.377,7803826.607,927.353,ES ");
		strings.add("15,671081.198,7803860.281,928.556,ES ");
		strings.add("16,671087.189,7803859.406,928.088,7.0 ");
		strings.add("17,671096.169,7803860.340,926.830,6 ");
		strings.add("18,671112.714,7803863.584,925.407,5 ");
		strings.add("19,671120.042,7803866.300,924.646,4 ");
		strings.add("20,671129.156,7803867.297,923.457,3 ");
		//strings.add("25,671127.354,7803855.285,923.643,b ");
		indexs.add(strings.size()-sub);
		sub=strings.size();
		colors.add(Color.RED);

		strings.add("21,671129.267,7803867.105,923.450,s ");
		strings.add("22,671135.016,7803867.803,922.811,s ");
		strings.add("23,671136.245,7803858.124,922.718,s ");
		strings.add("24,671127.836,7803857.231,923.537,s ");
		indexs.add(strings.size()-sub);
		sub=strings.size();
		colors.add(Color.GRAY);


		//Troquei pelo de baixo entao 25 e 26 sao repitidos pq esse B esta fora de lugar

		strings.add("26,671126.900,7803856.404,923.642,rp ");
		strings.add("27,671126.614,7803866.055,923.706,rp ");
		strings.add("28,671090.607,7803858.244,927.537,rp ");
		strings.add("29,671091.164,7803851.610,927.145,rp ");
		indexs.add(strings.size()-sub);
		sub=strings.size();
		colors.add(Color.GREEN);

		strings.add("30,671126.722,7803841.790,923.587,dsn ");
		strings.add("31,671126.236,7803831.645,923.082,dsn ");
		strings.add("32,671125.573,7803823.926,923.690,dsn ");
		strings.add("33,671123.374,7803814.739,924.007,dsn ");
		strings.add("34,671118.349,7803814.259,924.446,dsn ");
		strings.add("35,671095.655,7803821.389,925.856,dsn ");
		strings.add("36,671108.806,7803836.962,924.812,dsn ");
		strings.add("37,671113.865,7803842.053,924.605,dsn ");
		strings.add("38,671114.480,7803842.355,924.572,dsn ");
		indexs.add(strings.size()-sub);
		colors.add(Color.YELLOW);

		indexs.add(strings.size()-sub);
		sub=strings.size();
		colors.add(Color.RED);


		ArrayList<ArrayList<double[]>> todosPontos = new ArrayList<>();

		for(int i=0;i<strings.size();i++)
		{
			todosPontos.add(getPontoUTM(strings.get(i)));
		}

		return todosPontos;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("ON CREATE: ", "QUANTIDADE DE FIGURAS: " + Singleton.getInstance().getFiguras().size());
	}

	@Override
	public void onResume()
	{
		super.onResume();
		Log.d("ON RESUME: ", "QUANTIDADE DE FIGURAS: " + Singleton.getInstance().getFiguras().size());
		FrameLayout ln = (FrameLayout) findViewById(R.id.lnDesenho);
		if(Singleton.getInstance().getFiguras().size() < 1)
		{
			figuras = new ArrayList<>();
			indexs = new ArrayList();
			ArrayList<Point> pontos = new ArrayList<>();

			DisplayMetrics displaymetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
			int height = displaymetrics.heightPixels;
			int width = displaymetrics.widthPixels;
			tela_menor = width;
			if(height < width)
			{
				tela_menor = height;
				eixoy = true;
			}
			center = new Point(width / 2, height / 2);
			Log.d("MainActiviy", "tela = " + tela_menor);
			Log.d("MainActiviy", "centerx  = " + center.x + " centery = " + center.y);
			geraFiguras(pontos);
		}
		else
		{
			Log.d("STATUS: ", "ATRIBUIU FIGURAS DE SINGLETON");
			figuras = (ArrayList<Figura>)Singleton.getInstance().getFiguras().clone();
		}
		Sketch2D.limpaFiguras();
		Sketch2D.desenhaFiguras(this, figuras, ln);
	}

	public ArrayList<ArrayList<double[]>> pontosTodosCirculos()
	{

		ArrayList<ArrayList<double[]>> todosPontos = drawEL0930();//drawB_GN0930();

		return todosPontos;

	}

	public ArrayList<Point> geraFiguras(ArrayList<Point> pontos)
	{
		ArrayList<ArrayList<double[]>> todosPontos = pontosTodosCirculos();

		ArrayList<ArrayList<Point>> allPoints = new ArrayList<>();
		double maiory, maiorx, menorx, menory;
		maiorx = menorx=todosPontos.get(0).get(0)[0];
		maiory = menory=todosPontos.get(0).get(0)[1];
		pontos = new ArrayList<>();
		for(ArrayList<double[]> p : todosPontos)
		{
			if(p.get(0)[0]<menorx)
				menorx=p.get(0)[0];
			else if(p.get(0)[0]>maiorx)
				maiorx=p.get(0)[0];
			if(p.get(0)[1]<menory)
				menory=p.get(0)[1];
			else if(p.get(0)[1]>maiory)
				maiory=p.get(0)[1];

		}
		double mxdif=0,mydif=0;
		double tamx = maiorx-menorx;
		double tamy = maiory-menory;
		Log.d("Main","tamanho x = "+tamx+" tamy = "+tamy+" scale = "+tela_menor/tamy);

		double tam = tamx;
		if(eixoy)
			tam = tamy;
		int d = (int)(tela_menor/tam);
		scale = d-1;
		escala = scale;
		//TODO: REMOVER SE DER ERRADO
		scale = 1;
		double dx = center.x-(tamx*scale/2.0);
		double dy = center.y -(tamy*scale/2.0);


		ArrayList<Point> ps = new ArrayList<>();
		ps.add(new Point(center.x,center.y));
		drawCircle(ps,scale*3);



		int cont = 0;

		for(ArrayList<double[]> p : todosPontos)
		{
			double difx = p.get(0)[0]-menorx;
			double dify = (p.get(0)[1]-maiory)*(-1.0);
			double[] difs = new double[2];
			difs[0]=difx;
			difs[1]=dify;
			p.set(0,difs);

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

			//pontos = new ArrayList<>();
			pontos.add(aux);


			if(pontos.size()==indexs.get(cont))
			{
				cont++;
				allPoints.add(pontos);
				pontos = new ArrayList<>();
			}

			//figuras.add(new Circulo(Paint.Style.STROKE, Color.RED, pontos, (float) 5.0));

			Log.d("ValoresInt", "difx = " +difx  + " dify = " + dify);
		}

		colors.add(Color.RED);
		colors.add(Color.GRAY);
		colors.add(Color.GREEN);
		colors.add(Color.YELLOW);
		colors.add(Color.BLACK);
		colors.add(Color.BLUE);
		colors.add(Color.CYAN);


		for(int i=0;i<allPoints.size();i++)
		{
            /*if(colors.get(i)!=Color.GREEN && colors.get(i)!=Color.GRAY)*//*
			//figuras.add(new Poligono(Paint.Style.STROKE,colors.get(i),allPoints.get(i)));
			figuras.add(new Poligono(this, allPoints.get(i), false, new Configuracoes(false, Configuracoes.LINHA, 5, true, colors.get(i), 255, escala, 1)));
		}




		return pontos;
	}

	public void drawCircle(ArrayList<Point>  pontos, float rad)
	{
		//figuras.add(new Circulo(Paint.Style.STROKE, Color.RED, pontos, (float)rad));
		figuras.add(new Circulo(this, pontos, rad, true, new Configuracoes(false, Configuracoes.LINHA_E_PREENCHIDO, 5, true, Color.parseColor("#FF7700"), 180, escala, 1)));
	}

	public ArrayList<ArrayList<double[]>> drawB_GN0930()
	{
		int sub=0;
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


		ArrayList<ArrayList<double[]>> todosPontos = new ArrayList<>();

		for(int i=0;i<strings.size();i++)
		{
			todosPontos.add(getPontoUTM(strings.get(i)));
		}

		return todosPontos;
	}

	public ArrayList<double[]> getPontoUTM(String ponto)
	{
		String posicoes[] = ponto.split(""+separator);

		ArrayList<double[]> p = new ArrayList<>();
		double a=Double.parseDouble(posicoes[1]);
		double b=Double.parseDouble(posicoes[2]);
		double[] point =new double[2];
		point[0]=a;
		point[1]=b;
		p.add(point);

		return p;
	}*/
	/*private ArrayList<Point> ps = new ArrayList<>();;
	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		if(Singleton.getInstance().getFiguras().size()==0)
			ps = new ArrayList<>();
		Point p = new Point((int)ev.getX(),(int)ev.getY());
		Log.i("MDraw", "antes " + Singleton.getInstance().getFiguras().size());
		ArrayList<Point> aux = new ArrayList<>();
		//Configuracoes c = new Configuracoes(true, Configuracoes.LINHA, 2, true, Color.BLUE, 255, 1, 1);
		ps.add(p);
		if(Singleton.getInstance().getFiguras().size()>0)
			Sketch2D.removeDesenho(Singleton.getInstance().getFiguras().size() - 1);

		Log.i("MDraw", "meio " + Singleton.getInstance().getFiguras().size());
		Sketch2D.desenhaPoligono(MainActivity.this,(FrameLayout)findViewById(R.id.lnDesenho), ps, true);
		aux.add(p);
		Log.i("MDraw", "depois " + Singleton.getInstance().getFiguras().size());



		//pontosF.add(Sketch2D.desenhaCirculo(MainActivity.this,frame,aux,20f,true));
		return true;
	}*/

	void desenhaFigura()
	{
		ArrayList<Point> pontos = new ArrayList<>();
		ArrayList<double[]> dpontos = new ArrayList<>();
		double p[] = new double[2];
		p[0] = 669871.854121; p[1] = 7803644.542408;
		dpontos.add(p);
		p = new double[2];
		p[0] = 669867.348095; p[1] = 7803642.443725;
		dpontos.add(p);
		p = new double[2];
		p[0] = 669848.343115; p[1] = 7803637.172918;
		dpontos.add(p);
		p = new double[2];
		p[0] = 669849.94308; p[1] = 7803631.536003;
		dpontos.add(p);
		p = new double[2];
		p[0] = 669869.581005; p[1] = 7803635.883796;
		dpontos.add(p);
		p = new double[2];
		p[0] = 669879.77311; p[1] = 7803635.039376;
		dpontos.add(p);
		p = new double[2];
		p[0] = 669884.701155; p[1] = 7803635.38447;
		dpontos.add(p);
		p = new double[2];
		p[0] = 669888.646106; p[1] = 7803636.547694;
		dpontos.add(p);
		p = new double[2];
		p[0] = 669893.358909; p[1] = 7803638.450772;
		dpontos.add(p);
		p = new double[2];
		p[0] = 669895.283493; p[1] = 7803639.31569;
		dpontos.add(p);
		p = new double[2];
		p[0] = 669900.214522; p[1] = 7803641.846586;
		dpontos.add(p);
		p = new double[2];
		p[0] = 669914.637291; p[1] = 7803648.296346;
		dpontos.add(p);
		p = new double[2];
		p[0] = 669930.552878; p[1] = 7803655.491521;
		dpontos.add(p);
		p = new double[2];
		p[0] = 669918.567666; p[1] = 7803688.258446;
		dpontos.add(p);
		p = new double[2];
		p[0] = 669899.413408; p[1] = 7803670.888306;
		dpontos.add(p);
		p = new double[2];
		p[0] = 669886.898728; p[1] = 7803660.675813;
		dpontos.add(p);
		p = new double[2];
		p[0] = 669871.854121; p[1] = 7803644.542408;
		dpontos.add(p);

		double maiorx = 0, maiory = 0;
		maiorx = dpontos.get(0)[0]; maiory = dpontos.get(0)[1];
		for(double[] pt : dpontos)
		{
			if(maiorx < pt[0]) maiorx = pt[0];
			if(maiory < pt[1]) maiory = pt[1];
		}

		for(double[] pt : dpontos)
		{
			pt[0] -= maiorx;
			pt[1] -= maiory;
		}
	}

	@Override
	public void onResume()
	{
		super.onResume();
		Log.d("ON RESUME: ", "QUANTIDADE DE FIGURAS: " + Singleton.getInstance().getFiguras().size());
		FrameLayout ln = (FrameLayout) findViewById(R.id.lnDesenho);
		geraFiguras(getTamanho(), ln);
		//geraMalha(poligono);
	}
	public Point getTamanho()
	{
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;

		return new Point(width, height);
	}

	public ArrayList<Point> geraFiguras(Point tamanho,FrameLayout ln)
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
			poligono = Sketch2D.desenhaPoligono(this, ln, allPoints.get(0), true, c);
		}
		return pontos;
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