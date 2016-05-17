package com.imobilis.sketch2dframework;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Criado por Maicon em 27/04/2016.
 */
public class SketchView extends View
{
	Figura figura;
	private Activity activity;

	public SketchView(Activity activity, Figura figura)
	{
		super(activity.getBaseContext());
		this.activity = activity;
		this.figura = figura;
		//setBackgroundColor(Color.CYAN);
	}

	public Activity getActivity()
	{
		return activity;
	}

	public void aply_scale(Figura f,boolean old)
	{
		float mul = f.getConfiguracoes().getEscala()*f.getConfiguracoes().getZoom();

		if(mul==1)
			return;



		if(f instanceof Circulo)
		{
			((Circulo) f).setRaio(((Circulo) f).getRaio()*mul);
		}
		else{

			if(!old)
				mul=2;
			for(Point p : f.getPontos())
			{
				float dx = figura.getMaiorX().x + figura.getMenorX().x;
				dx/=2;
				float dy = figura.getMaiorY().y + figura.getMenorY().y;
				dy/=2;

				//p.y-=(figura.getMaiorY().y/2);

				p.x*=mul;
				p.y*=mul;


				p.x-=(dx*(mul-1));
				p.y-=(dy*(mul-1));

				//p.x-=(figura.getMaiorX().x*mul/2);
				//p.y-=(figura.getMaiorY().y*mul/2);


			}
		}
	}

	public ArrayList<Point> copyPoints(ArrayList<Point> pontos)
	{
		ArrayList<Point> oldPoints = new ArrayList<>();

		for(Point p : pontos)
		{
			oldPoints.add(new Point(p.x,p.y));
		}

		return oldPoints;
	}

	/*@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		Log.d("STATUS: ", "DESENHANDO");
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
		if(figura.getClass() == Poligono.class)
		{
			setX(menorX.x);
			setY(menorY.y);
			for(Point p : figura.getPontos())
			{
				/*p.x += (figura.getConfiguracoes().getTamLinha());
				p.y += (figura.getConfiguracoes().getTamLinha());*//*
				p.x *= figura.getConfiguracoes().getEscala() * figura.getConfiguracoes().getZoom();
				p.y *= figura.getConfiguracoes().getEscala() * figura.getConfiguracoes().getZoom();
			}
			canvas.drawPath(((Poligono) figura).getCaminho(getX(), getY()), figura.getPaint());
		}
		else if(figura.getClass() == Circulo.class)
		{
			setX(menorX.x - ((Circulo) figura).getRaio());
			setY(menorY.y - ((Circulo) figura).getRaio());
			for(Point p : figura.getPontos())
			{
				/*p.x += (figura.getConfiguracoes().getTamLinha());
				p.y += (figura.getConfiguracoes().getTamLinha());*//*
				p.x *= figura.getConfiguracoes().getEscala() * figura.getConfiguracoes().getZoom();
				p.y *= figura.getConfiguracoes().getEscala() * figura.getConfiguracoes().getZoom();
			}
			canvas.drawCircle(figura.getX(0) - getX(), figura.getY(0) - getY(), ((Circulo)figura).getRaio(), figura.getPaint());
		}
		else if(figura.getClass() == Linha.class)
		{
			setX(menorX.x);
			setY(menorY.y);
			for(Point p : figura.getPontos())
			{
				p.x *= figura.getConfiguracoes().getEscala() * figura.getConfiguracoes().getZoom();
				p.y *= figura.getConfiguracoes().getEscala() * figura.getConfiguracoes().getZoom();
			}
			canvas.drawPath(((Linha) figura).getCaminho(getX(), getY()), figura.getPaint());
		}
		figura.setView(this);
	}*/

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		boolean old = !false;
		TextView txt = new TextView(activity.getBaseContext());
		txt.setText("lalalalala");
		txt.setX(50);
		txt.setY(50);

		Log.d("STATUSE: ", "DESENHANDO");
		Point menorX, menorY, maiorX, maiorY;

		float oldRadio=-1f;
		ArrayList<Point> oldPoints = copyPoints(figura.getPontos());
		if(figura instanceof Circulo)
		{
			oldRadio = ((Circulo) figura).getRaio();
		}
		aply_scale(figura,old);
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



		if (figura.getClass() == Poligono.class) {
			setX(menorX.x);
			setY(menorY.y);

			for(Point p : figura.getPontos())
			{
				p.x += (figura.getConfiguracoes().getTamLinha());
				p.y += (figura.getConfiguracoes().getTamLinha());
			}
			canvas.drawPath(((Poligono) figura).getCaminho(getX(), getY()), figura.getPaint());

			figura.setPontosEscalados(copyPoints(figura.getPontos()));

			figura.setMaior(new Point(maiorX.x, maiorY.y));
			figura.setMenor(new Point(menorX.x, menorY.y));

			if(old)
				figura.setPontos(oldPoints);
			else{
				figura.setMaior(new Point(maiorX.x, maiorY.y));
				figura.setMenor(new Point(menorX.x, menorY.y));
			}

		}
		else if(figura.getClass() == Circulo.class)
		{

			setX(menorX.x - ((Circulo) figura).getRaio());
			setY(menorY.y - ((Circulo) figura).getRaio());
			for(Point p : figura.getPontos())
			{
				p.x += (figura.getConfiguracoes().getTamLinha());
				p.y += (figura.getConfiguracoes().getTamLinha());
			}
			canvas.drawCircle(figura.getPontos().get(0).x - getX(), figura.getPontos().get(0).y - getY(), ((Circulo)figura).getRaio(), figura.getPaint());
			((Circulo) figura).setRaio(oldRadio);


			figura.setPontos(oldPoints);
		}
		else if(figura.getClass() == Linha.class)
		{
			setX(menorX.x);
			setY(menorY.y);
			for(Point p : figura.getPontos())
			{
				p.x *= figura.getConfiguracoes().getEscala() * figura.getConfiguracoes().getZoom();
				p.y *= figura.getConfiguracoes().getEscala() * figura.getConfiguracoes().getZoom();
			}
			canvas.drawPath(((Linha) figura).getCaminho(getX(), getY()), figura.getPaint());
		}
		figura.setView(this);
	}
}
