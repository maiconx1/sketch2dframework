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
	ArrayList<Figura> furos;
	Point pInicial, pFinal;
	Figura fInicial, fFinal;
	float altura;
	int tipo;
	Malha malha;
	Figura linha;
	int estado = ESTADO_INICIAL;
	public static final int ESTADO_INICIAL = 0, ESTADO_PRIMEIRO = 1, ESTADO_SEGUNDO = 2;

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

		((SeekBar) findViewById(R.id.seekBar)).setProgress(10);
		((SeekBar)findViewById(R.id.seekBar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
				altura = 10 + ((float) progress) * 3;
				atualizaMalha();
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
		parent.setOnTouchListener(new View.OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				/*switch(estado)
				{
					case ESTADO_INICIAL:
						pInicial = new Point((int)event.getX(), (int)event.getY());
						//((TextView)findViewById(R.id.txtSelecione)).setText("Selecione o segundo ponto!");
						estado = ESTADO_PRIMEIRO;
						fInicial = Sketch2D.desenhaCirculo(NovaMain.this, parent, pInicial, 10, false, new Configuracoes(false, Configuracoes.PREENCHIDO, 1, true, Color.BLACK, 255));
						break;
					case ESTADO_PRIMEIRO:
						pFinal = new Point((int)event.getX(), (int)event.getY());
						findViewById(R.id.txtSelecione).setVisibility(View.GONE);
						estado = ESTADO_SEGUNDO;
						fFinal = Sketch2D.desenhaCirculo(NovaMain.this, parent, pFinal, 10, false, new Configuracoes(false, Configuracoes.PREENCHIDO, 1, true, Color.BLACK, 255));
						break;
					default:
					case ESTADO_SEGUNDO:
						break;
				}*/
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
						//break;
				}
				return false;
			}
		});
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
			Toast.makeText(getBaseContext(), "Selecione o primeiro e segundo ponto primeiro!!", Toast.LENGTH_LONG).show();
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
				break;
		}
		parent.invalidate();
		return super.onOptionsItemSelected(item);
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
			for(int i = 0; i <= f.getView().getHeight() / divH; i++)
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
			}
			Configuracoes.setCorPadrao(Color.BLACK);
		}
	}

	int divW, divH;

	@Override
	public void onResume()
	{
		super.onResume();
		Log.d("ON RESUME: ", "QUANTIDADE DE FIGURAS: " + Singleton.getInstance().getFiguras().size());
		FrameLayout ln = (FrameLayout) findViewById(R.id.lnDesenho);
		Figura f = geraFiguras(getTamanho(), ln);
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
