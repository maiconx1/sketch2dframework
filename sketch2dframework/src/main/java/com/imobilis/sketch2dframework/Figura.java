package com.imobilis.sketch2dframework;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Criado por Maicon em 18/04/2016.
 */
public abstract class Figura extends AppCompatActivity
{
	private ArrayList<Point> pontos, pontosEscalados = new ArrayList<>();
	private static Class classeConfiguracao = null;
	private Paint p;
	private Point menorX, menorY, maiorX, maiorY, menor, maior;
	private static Activity activity;
	private boolean editavel;
	boolean confPadrao = false;
	private Configuracoes configuracoes;
	private SketchView view;
	private int index;
	public static float x = 0, y = 0, movX = 0, movY = 0;
	private static Timer timerAtual;
	private static TimerTask task;
	private static final Handler hand = new Handler();
	private static boolean timerAtivo = false;
	private static Activity actTimer = null;
	private static ArrayList<Figura> indexLinhas = new ArrayList<>();
	private static boolean editando = false;
	public static float xViewAnterior = 0, yViewAnterior = 0;
	public static final int CIRCULO = 0, LINHA = 1, POLIGONO = 2;

	private Point difs;

	public Figura(Activity activity, ArrayList<Point> pontos)
	{
		setActivity(activity);
		setPontos(pontos);
		setConfiguracoes(new Configuracoes());
		setPaint(getConfiguracoes().getPaint());
		calculaMenorMaior();
		setConfPadrao(true);
	}

	public Figura(Activity activity, ArrayList<Point> pontos, Configuracoes configuracoes)
	{
		setActivity(activity);
		setPontos(pontos);
		setConfiguracoes(configuracoes);
		setPaint(getConfiguracoes().getPaint());
		calculaMenorMaior();
		setConfPadrao(false);
	}

	public Figura(Activity activity, ArrayList<Point> pontos, boolean editavel)
	{
		setActivity(activity);
		setPontos(pontos);
		setConfiguracoes(new Configuracoes());
		setPaint(getConfiguracoes().getPaint());
		setEditavel(editavel);
		calculaMenorMaior();
		setConfPadrao(true);
	}

	public Figura(Activity activity, ArrayList<Point> pontos, boolean editavel, Configuracoes configuracoes)
	{
		setActivity(activity);
		setPontos(pontos);
		setConfiguracoes(configuracoes);
		setPaint(getConfiguracoes().getPaint());
		setEditavel(editavel);
		calculaMenorMaior();
		setConfPadrao(false);
	}

	private void calculaMenorMaior()
	{
		menorX = menorY = maiorX = maiorY = getPontos().get(0);
		for(Point p : getPontos())
		{
			if(p.x < menorX.x)
			{
				setMenorX(p);
			}
			if(p.y < menorY.y)
			{
				setMenorY(p);
			}
			if(p.x > maiorX.x)
			{
				setMaiorX(p);
			}
			if(p.y > maiorY.y)
			{
				setMaiorY(p);
			}
		}
		setMaior(new Point(getMaiorX().x, getMaiorY().y));
		setMenor(new Point(getMenorX().x, getMenorY().y));
		setDifs(new Point((getMaior().x + getMenor().x)/2, (getMaior().y+getMenor().y)/2));
	}

	public void setMenorX(Point menorX)
	{
		this.menorX = menorX;
	}

	public Point getMenorX()
	{
		return menorX;
	}

	public void setMenorY(Point menorY)
	{
		this.menorY = menorY;
	}

	public Point getMenorY()
	{
		return menorY;
	}

	public void setMaiorX(Point maiorX)
	{
		this.maiorX = maiorX;
	}

	public Point getMaiorX()
	{
		return maiorX;
	}

	public void setMaiorY(Point maiorY)
	{
		this.maiorY = maiorY;
	}

	public Point getMaiorY()
	{
		return maiorY;
	}

	public abstract boolean isDentro(Point ponto);

	public void setPaint(Paint p)
	{
		this.p = p;
	}

	public Paint getPaint()
	{
		return getConfiguracoes().getPaint();
		//return p;
	}

	public ArrayList<Point> getPontos()
	{
		return pontos;
	}

	public Point getPonto(int index)
	{
		Point p;
		float mul = getConfiguracoes().getEscala()*getConfiguracoes().getZoom();
		p = new Point((int)(getPontos().get(index).x + getConfiguracoes().getTamLinha()*mul), (int)(getPontos().get(index).y + getConfiguracoes().getTamLinha()*mul));
		return p;
	}

	public void setPontos(ArrayList<Point> pontos)
	{
		this.pontos = pontos;
	}

	public int getX(int index)
	{
		return pontos.get(index).x + getConfiguracoes().getTamLinha();
	}

	public int getY(int index)
	{
		return pontos.get(index).y + getConfiguracoes().getTamLinha();
	}

	public Context getContext()
	{
		return activity.getBaseContext();
	}

	public Activity getActivity()
	{
		return activity;
	}

	public void setActivity(Activity activity)
	{
		this.activity = activity;
	}

	public boolean isEditavel()
	{
		return editavel;
	}

	public void setEditavel(boolean editavel)
	{
		this.editavel = editavel;
	}



	private static void ativaTimer(Activity activity, final View v)
	{
		timerAtivo = true;
		Figura.actTimer = activity;
		Log.d("STATUS: ", "TIMER - ATIVADO");
		timerAtual = new Timer();
		task = new TimerTask()
		{
			public void run()
			{
				hand.post(new Runnable()
				{
					public void run()
					{
						Log.d("STATUS: ", "TENTA CONSTRUIR BUILDER - ACTIVITY = " + Figura.actTimer);
						AlertDialog.Builder builder = new AlertDialog.Builder(Figura.actTimer);
						//builder.setTitle(Figura.actTimer.getString(R.string.title_context_menu));
						builder.setTitle("Selecione uma opção");
						LinearLayout ln = new LinearLayout(Figura.actTimer);
						ListView lst = new ListView(Figura.actTimer);
						//String[] lista = new String[] {Figura.actTimer.getString(R.string.edita), Figura.actTimer.getString(R.string.edita_excluir)};
						String[] lista = new String[] {"Editar figura", "Excluir"};
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(Figura.actTimer, android.R.layout.simple_list_item_1, android.R.id.text1, lista);
						lst.setAdapter(adapter);
						lst.setOnItemClickListener(new AdapterView.OnItemClickListener()
						{
							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id)
							{
								AlertDialog alert = (AlertDialog) ((View) view.getParent().getParent()).getTag();
								Figura f;
								switch(position)
								{
									case 0:
										f = (Figura) v.getTag();
										if(classeConfiguracao != null)
										{
											Intent i = new Intent(f.getActivity(), classeConfiguracao);
											i.putExtra("CONFIGURACAO", f.getConfiguracoes());
											int tFigura = 0;
											if(f instanceof Circulo)
											{
												tFigura = Figura.CIRCULO;
												i.putExtra("RAIO", ((Circulo) f).getRaio());
											}
											else if(f instanceof Linha)
											{
												tFigura = Figura.LINHA;
											}
											else if(f instanceof Poligono)
											{
												tFigura = Figura.POLIGONO;
											}
											i.putExtra("FIGURA", tFigura);
											String extra = "";
											for(Point p : f.getPontos())
											{
												extra += (extra.isEmpty() ? "" : "//") + p.x + "/" + p.y;
											}
											i.putExtra("PONTOS", extra);
											i.putExtra("INDEX", Sketch2D.getFiguras().indexOf(f));
											f.getActivity().startActivity(i);
										}
										else
										{
											Toast.makeText(f.getActivity().getBaseContext(), "Classe de configuração não setada. Use Sketch2D.setClasseConfiguracao() para setá-la.", Toast.LENGTH_LONG).show();
										}
										break;
									case 1:
										Sketch2D.removeDesenho(((Figura)v.getTag(R.string.id_numero)).getIndex());
										//((ViewGroup) v.getParent()).removeView(v);
										//f = (Figura)v.getTag();
										//((AppCompatActivity)f.getActivity()).setActionBar(actionBar);
										break;
								}
								alert.dismiss();
							}
						});
						ln.setOrientation(LinearLayout.HORIZONTAL);
						ln.addView(lst);
						builder.setView(ln);
						ln.setTag(builder.show());
					}
				});
				cancelaTimer();
			}
		};
		timerAtual.schedule(task, 1000, 1000);
	}
	private static void cancelaTimer()
	{
		timerAtual.cancel();
		timerAtivo = false;
		Log.d("STATUS: ", "TIMER - DESATIVADO");
	}

	private static View.OnTouchListener onTouch()
	{
		return new View.OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				switch(event.getAction())
				{
					case MotionEvent.ACTION_DOWN:
						Log.d("TESTESTATUS", "DOWNN");
						if(editando)
						{
							return false;
						}
						Log.d("TESTESTATUS", "PÓS EDITANDO");
						setEditando(true);
						x = event.getX();
						y = event.getY();
						if(!((Figura) v.getTag()).isDentro(new Point((int) x, (int) y)) || !((Figura)v.getTag()).isEditavel())
						{
							Log.d("STATUS: ", "FORA DA FIGURA");
							setEditando(false);
							return false;
						}
						Log.d("TESTESTATUS", "PÓS FORA FIGURA");
						xViewAnterior = v.getX();
						yViewAnterior = v.getY();
						v.getParent().bringChildToFront(v);
						ativaTimer(((SketchView) v).getActivity(), v);
						Log.d("STATUS: ", "DENTRO DA FIGURA");
						Log.d("STATUS: ", "X inicial: " + x + "// Y inicial: " + y);
						break;
					case MotionEvent.ACTION_MOVE:
						//Log.d("TETESTES", "" + event.getPointerCount());
						Log.d("TESTESTATUS", "MOVEEE");
						if(((Figura) v.getTag()).isEditavel() && event.getPointerId(0) == 0)
						{
							v.setX(v.getX() + event.getX() - x);
							v.setY(v.getY() + event.getY() - y);
							movX = event.getX();
							movY = event.getY();
							if(Math.sqrt(Math.pow(event.getX() - x, 2) + Math.pow(event.getY() - y, 2)) >= 5 && timerAtivo)
							{
								cancelaTimer();
							}
							Log.d("QUANTIDADE", "" + Sketch2D.getFiguras().size());
							while(indexLinhas.size() > 0)
							{
								Sketch2D.removeDesenho(indexLinhas.get(indexLinhas.size() - 1));
								indexLinhas.remove(indexLinhas.size() - 1);
							}
							for(int i = 0; i < Sketch2D.getFiguras().size(); i++)
							{
								if(indexLinhas.indexOf(Sketch2D.getFiguras().get(i)) < 0)
								{
									Figura f = Sketch2D.getFiguras().get(i);
									if(Sketch2D.getFiguras().indexOf(f) != Sketch2D.getFiguras().indexOf(v.getTag()))
									{
										ArrayList<Point[]> linhas;
										linhas = ((Figura) v.getTag()).pontoMaisProximo(f, v.getX() - xViewAnterior, v.getY() - yViewAnterior);
										for(Point p[] : linhas)
										{
											double dist = Figura.distancia2Pontos(p[0], p[1]);
											float mul = ((Figura) v.getTag()).getConfiguracoes().getEscala() * ((Figura) v.getTag()).getConfiguracoes().getZoom();
											if(dist < Sketch2D.distanciaParaLinha * mul/*200*/)
											{
												ArrayList<Point> pp = new ArrayList<>();
												pp.add(new Point(p[0].x - 90, p[0].y - 90));
												pp.add(new Point(p[1].x - 90, p[1].y - 90));
												Sketch2D.desenhaLinha(((Figura) v.getTag()).getActivity(), (FrameLayout) v.getParent(), pp, false, new Configuracoes(true, Configuracoes.LINHA, 3, true, Sketch2D.corDistancia, 180), true);
												indexLinhas.add(Sketch2D.getFiguras().get(Sketch2D.getFiguras().size() - 1));
											}
										}
									}
								}
							}
							Log.d("STATUS: ", "MOVENDO - vX: " + v.getX() + "/vY: " + v.getY() + " - X: " + x + "/Y: " + y + " - MOVX: " + event.getX() + "/MOVY: " + event.getY());
						}
						break;
					case MotionEvent.ACTION_CANCEL:
						Log.d("TESTESTATUS: ", "CANCEL");
					case MotionEvent.ACTION_UP:
						if(event.getPointerId(0) == 0)
						{
							setEditando(false);
							Log.d("TESTESTATUS: ", "UPPPP");
							if(timerAtivo)
							{
								cancelaTimer();
							}
							movX = 0;
							movY = 0;
							for(Point p : ((Figura) v.getTag()).getPontos())
							{
								p.x += v.getX() - xViewAnterior;
								p.y += v.getY() - yViewAnterior;
							}
							for(Point p : ((Figura) v.getTag()).getPontosEscalados())
							{
								p.x += v.getX() - xViewAnterior;
								p.y += v.getY() - yViewAnterior;
							}

							((Figura)v.getTag()).deslocaFigura((int)(v.getX() - xViewAnterior), (int)(v.getY() - yViewAnterior));
							xViewAnterior = 0;
							yViewAnterior = 0;
							while(indexLinhas.size() > 0)
							{
								Sketch2D.removeDesenho(indexLinhas.get(indexLinhas.size() - 1));
								indexLinhas.remove(indexLinhas.size() - 1);
							}
						}
						break;
				}
				return true;
			}
		};
	}

	private static void setEditando(boolean editando)
	{
		Figura.editando = editando;
		Log.d("EDITANDO", "Valor = " + (editando?"true":"false"));
	}

	public void desenha(FrameLayout layout, Figura figura)
	{
		Log.d("DESENHA", "" + figura.getClass());
		SketchView sketchView = new SketchView(activity, figura);
		//Singleton.getInstance().addFigura(figura);
		figura.setView(sketchView);
		sketchView.setOnTouchListener(onTouch());
		int med[] = tamLayout(figura);
		sketchView.setLayoutParams(new FrameLayout.LayoutParams(med[0], med[1]));
		Log.d("DESENHA", "TAMANHO = " + med[0] + "-" + med[1]);
		figura.setIndex(Sketch2D.getFiguras().size() - 1);
		sketchView.setTag(figura);
		sketchView.setTag(R.string.id_activity, activity);
		sketchView.setTag(R.string.id_numero, figura);
		layout.addView(sketchView);
	}

	public static void desenha(Activity activity, ArrayList<Figura> figuras, FrameLayout layout)
	{
		for(Figura f : figuras)
		{
			//Singleton.getInstance().addFigura(f);
			SketchView sketchView = new SketchView(activity, f);
			f.setView(sketchView);
			sketchView.setOnTouchListener(onTouch());
			int med[] = tamLayout(f);
			sketchView.setLayoutParams(new FrameLayout.LayoutParams(med[0],med[1]));
			f.setIndex(Sketch2D.getFiguras().size() - 1);
			sketchView.setTag(f);
			sketchView.setTag(R.string.id_activity, activity);
			layout.addView(sketchView);
		}
	}

	public static void desenha(Activity activity, FrameLayout layout, Figura figura)
	{
		//Singleton.getInstance().addFigura(figura);
		SketchView sketchView = new SketchView(activity, figura);
		figura.setView(sketchView);
		sketchView.setOnTouchListener(onTouch());
		int med[] = tamLayout(figura);
		sketchView.setLayoutParams(new FrameLayout.LayoutParams(med[0], med[1]));
		figura.setIndex(Sketch2D.getFiguras().size() - 1);
		sketchView.setTag(figura);
		sketchView.setTag(R.string.id_activity, activity);
		activity.registerForContextMenu(sketchView);
		layout.addView(sketchView);
	}

	/*public static int[] tamLayout(Figura figura)
	{
		int tam[] = {0,0};
		if(figura instanceof  Circulo)
		{
			tam[0] = 2*(int)((Circulo)figura).getRaio();
			tam[1] = 2*(int)((Circulo)figura).getRaio();
		}
		else
		{
			Point menorX, menorY, maiorX, maiorY;
			maiorX = maiorY = menorX = menorY = figura.getPontos().get(0);
			for(Point p : figura.getPontos())
			{
				if(p.x < menorX.x)
				{
					menorX = p;
				}
				if(p.y < menorY.y)
				{
					menorY = p;
				}
				if(p.x > maiorX.x)
				{
					maiorX = p;
				}
				if(p.y > maiorY.y)
				{
					maiorY = p;
				}
			}
			tam[0] = maiorX.x-menorX.x;
			tam[1] = maiorY.y-menorY.y;
		}
		tam[0] += 2*figura.getConfiguracoes().getTamLinha();
		tam[1] += 2*figura.getConfiguracoes().getTamLinha();
		return tam;
	}*/

	public static int[] tamLayout(Figura figura)
	{
		boolean old=!false;
		Log.i("Scalando", "tamLayout");
		int tam[] = {0,0};
		float mul = figura.getConfiguracoes().getEscala();
		if(figura instanceof  Circulo)
		{

			tam[0] = 2*(int)(((Circulo)figura).getRaio()*mul);
			tam[1] = 2*(int)(((Circulo)figura).getRaio()*mul);


			Log.i("Scalando","Nova mul = "+mul+" r = "+((Circulo)figura).getRaio()+" tam[0]="+tam[0]+" tam[1]="+tam[1]);
		}
		else
		{
			Point menorX, menorY, maiorX, maiorY;
			maiorX = maiorY = menorX = menorY = figura.getPontos().get(0);
			for(Point p : figura.getPontos())
			{
				if(p.x < menorX.x)
				{
					menorX = p;
				}
				if(p.y < menorY.y)
				{
					menorY = p;
				}
				if(p.x > maiorX.x)
				{
					maiorX = p;
				}
				if(p.y > maiorY.y)
				{
					maiorY = p;
				}
			}
			tam[0] = maiorX.x-menorX.x;
			tam[1] = maiorY.y-menorY.y;


			Log.i("Scalano","tam[0]="+tam[0]+" tam[1]="+tam[1]);


			if(old)
			{
				tam[0] = (int)(tam[0]*mul);

				tam[1] = (int)(tam[1]*mul);
			}

			Log.i("Scalano","2tam[0]="+tam[0]+" tam[1]="+tam[1]+" mul = "+mul);

		}

		tam[0] += 2*(figura.getConfiguracoes().getTamLinha()+2);
		tam[1] += 2*(figura.getConfiguracoes().getTamLinha()+2);

		if(figura instanceof Linha)
		{
			boolean linha_distancia = false;
			if(((Linha)figura).isDistancia())
			{
				linha_distancia = true;
			}
			if(linha_distancia)
			{
				tam[1] += 180;
				tam[0] += 180;
			}

		}

		return tam;
	}

	public void setConfiguracoes(Configuracoes configuracoes)
	{
		this.configuracoes = configuracoes;
	}

	public Configuracoes getConfiguracoes()
	{
		return configuracoes;
	}

	public void setView(SketchView view)
	{
		this.view = view;
	}

	public SketchView getView()
	{
		return view;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public int getIndex()
	{
		return index;
	}

	private void reescala()
	{
		//f.getConfiguracoes().setEscala(f.getConfiguracoes().getEscala() * 2);

		int tam[]= tamLayout(this);

		getView().setLayoutParams(new FrameLayout.LayoutParams(tam[0], tam[1]));

		getView().invalidate();
	}

	public Point getMenor() {
		return menor;
	}

	public void setMenor(Point menor) {
		this.menor = menor;
	}

	public Point getMaior() {
		return maior;
	}

	public void setMaior(Point maior) {
		this.maior = maior;
	}

	public void setPontosEscalados(ArrayList<Point> pontosEscalados)
	{
		this.pontosEscalados = pontosEscalados;
	}

	public ArrayList<Point> getPontosEscalados()
	{
		return pontosEscalados;
	}

	public void setEscala(float escala)
	{
		getConfiguracoes().setEscala(escala);
		reescala();
	}

	public static void setClasseConfiguracao(Class classeConfiguracao)
	{
		Figura.classeConfiguracao = classeConfiguracao;
	}

	public abstract ArrayList<Point[]> pontoMaisProximo(Figura f, float offsetX, float offsetY);

	private static int getMaior(ArrayList<Integer> indexes)
	{
		int maior = indexes.get(0);
		for(Integer i : indexes)
		{
			if(maior < i) maior = i;
		}
		return maior;
	}

	public static double distancia2Pontos(Point p1, Point p2)
	{
		Log.d("DOIS PONTOS", "p2: " + p2 + "//p1: " + p1);
		return Math.sqrt(Math.pow(p2.x-p1.x, 2) + Math.pow(p2.y-p1.y, 2));
	}

	public boolean isConfPadrao()
	{
		return confPadrao;
	}

	public void setConfPadrao(boolean confPadrao)
	{
		this.confPadrao = confPadrao;
	}

	public Point getDifs()
	{
		return difs;
	}

	public void setDifs(Point difs)
	{
		this.difs = difs;
	}

	public void deslocaFigura(int dx, int dy)
	{
		setDifs(new Point(getDifs().x+dx, getDifs().y+dy));
		setMaior(new Point(getMaior().x + dx, getMaior().y + dy));
		setMenor(new Point(getMenor().x + dx, getMenor().y + dy));
	}
}
