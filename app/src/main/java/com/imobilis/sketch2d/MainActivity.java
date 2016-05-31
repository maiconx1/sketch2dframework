package com.imobilis.sketch2d;


	import android.os.Bundle;
	import android.support.v7.app.AppCompatActivity;

	import android.graphics.Color;
	import android.graphics.Point;
	import android.util.Log;
	import android.view.Menu;
	import android.view.MenuInflater;
	import android.view.MotionEvent;
	import android.widget.FrameLayout;
	import android.widget.SeekBar;

	import com.imobilis.sketch2dframework.Configuracoes;
	import com.imobilis.sketch2dframework.Figura;
	import com.imobilis.sketch2dframework.Singleton;
	import com.imobilis.sketch2dframework.Sketch2D;

	import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
	ArrayList<Figura> figuras;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return true;
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

		/*Sketch2D.desenhaLinha(this, (FrameLayout) findViewById(R.id.lnDesenho), new ArrayList<Point>()
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

		Sketch2D.desenhaLinha(this, (FrameLayout)findViewById(R.id.lnDesenho), false);*/
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		figuras = new ArrayList<>();
		Sketch2D.setClasseConfiguracao(ConfActivity.class);

		Sketch2D.desenhaLinha(this, (FrameLayout) findViewById(R.id.lnDesenho), new ArrayList<Point>()
		{{
				add(new Point(50, 50));
				add(new Point(50, 200));
			}}, true, new Configuracoes(false, Configuracoes.LINHA, 5, true, Color.BLACK, 255));

		Sketch2D.desenhaLinha(this, (FrameLayout) findViewById(R.id.lnDesenho), new ArrayList<Point>()
		{{
				add(new Point(250, 250));
				add(new Point(50, 250));
			}}, true, new Configuracoes(false, Configuracoes.LINHA, 5, true, Color.BLACK, 255));

		/*Sketch2D.desenhaLinha(this, (FrameLayout) findViewById(R.id.lnDesenho), new ArrayList<Point>()
		{{
				add(new Point(100, 700));
				add(new Point(250, 450));
			}}, true, new Configuracoes(false, Configuracoes.LINHA, 5, true, Color.CYAN, 255));
		Sketch2D.desenhaLinha(this, (FrameLayout) findViewById(R.id.lnDesenho), new ArrayList<Point>()
		{{
				add(new Point(700, 150));
				add(new Point(700, 700));
			}}, true, new Configuracoes(false, Configuracoes.LINHA, 5, true, Color.CYAN, 255));*/

		desenhaTudo();
		((SeekBar) findViewById(R.id.skbEscala)).setProgress(10);
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
		});
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
}