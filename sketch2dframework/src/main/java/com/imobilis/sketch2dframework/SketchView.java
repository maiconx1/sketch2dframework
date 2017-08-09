package com.imobilis.sketch2dframework;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.text.DecimalFormat;
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
		else
		{
			for(Point p : f.getPontos())
			{
				float dx = figura.getMaiorX().x + figura.getMenorX().x;
				dx/=2;
				float dy = figura.getMaiorY().y + figura.getMenorY().y;
				dy/=2;

				dx = figura.getDifs().x;
				dy = figura.getDifs().y;

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

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		boolean old = true;
		float a=figura.getConfiguracoes().getTamLinha()/2;

		Log.d("STATUSE: ", "DESENHANDO");
		Point menorX, menorY, maiorX, maiorY;

		boolean linha_distancia = false;

		if((figura.getClass() == Linha.class && ((Linha)figura).isDistancia()))
			linha_distancia=true;
		float oldRadio=-1f;
		ArrayList<Point> oldPoints = copyPoints(figura.getPontos());
		if(figura instanceof Circulo)
		{
			oldRadio = ((Circulo) figura).getRaio();
		}
		aply_scale(figura,true);
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
		if (figura instanceof Poligono || (figura instanceof Linha && !linha_distancia))
		{
			setX(menorX.x-a);
			setY(menorY.y-a);

			for(Point p : figura.getPontos())
			{
				p.x -=(figura.getConfiguracoes().getTamLinha());
				p.y -=(figura.getConfiguracoes().getTamLinha());
			}

			if(figura instanceof Poligono)
			{
				canvas.drawPath(((Poligono) figura).getCaminho(getX(), getY()), figura.getPaint());
				if(figura.getConfiguracoes().getTamBorda()>0)
				{
                    Configuracoes c = new Configuracoes();
                    c.setCor(figura.getConfiguracoes().getCorBorda());
                    c.setTamLinha(figura.getConfiguracoes().getTamBorda());
                    Paint teste = c.getPaint();
					canvas.drawPath(((Poligono) figura).getCaminho(getX(), getY()), teste);
				}
			}
			else
			{
				canvas.drawPath(((Linha) figura).getCaminho(getX(),getY()), figura.getPaint());
			}

			figura.setPontosEscalados(copyPoints(figura.getPontos()));

			figura.setMaior(new Point(maiorX.x, maiorY.y));
			figura.setMenor(new Point(menorX.x, menorY.y));

			if(old)
			{
				figura.setPontos(oldPoints);
			}
			else
			{
				figura.setMaior(new Point(maiorX.x, maiorY.y));
				figura.setMenor(new Point(menorX.x, menorY.y));
			}

		}
		else if(figura instanceof  Arco)
		{
			setX(menorX.x - ((Arco) figura).getRaio());
			setY(menorY.y - ((Arco) figura).getRaio());
			float left,top,right,bottom,angleInit,angleEnd;
			float x = figura.getPonto(0).x- getX();
			float y = figura.getPonto(0).y-getY();
			float raio = ((Arco)figura).getRaio();
			left = x-raio;
			right = x+raio;
			top = y-raio;
			bottom = y+raio;
			angleInit = ((Arco)figura).getAngleInit();
			angleEnd = ((Arco)figura).getDegrees();
			RectF rect = new RectF(left,top,right,bottom);
			canvas.drawArc(rect, angleInit, angleEnd, true, figura.getPaint());
		}
		else if(figura instanceof Circulo)
		{
			int dif = (int)(a);
			setX(menorX.x - ((Circulo) figura).getRaio()-dif-1.5f);
			setY(menorY.y - ((Circulo) figura).getRaio()-dif-1.5f);

			dif = (int)(a*2);
			canvas.drawCircle(figura.getPonto(0).x - getX()-dif,figura.getPonto(0).y - getY()-dif, ((Circulo) figura).getRaio(), figura.getPaint());
			if(((Circulo)figura).isPontoNoCentro())
			{
				Paint paint = new Paint(figura.getPaint());
				paint.setPathEffect(null);
				paint.setStyle(Paint.Style.FILL);
				Configuracoes conf = new Configuracoes(figura.getConfiguracoes());
				conf.setAlpha(255);
				paint.setColor(conf.getCor());
				canvas.drawCircle(figura.getPonto(0).x - getX()-dif,figura.getPonto(0).y - getY()-dif, ((Circulo) figura).getRaio()*0.15f, paint);
			}
            if(((Circulo)figura).isMostraTexto())
            {
                Circulo circulo = (Circulo)figura;
                if(((Circulo)figura).getTexto() == null) ((Circulo)figura).setTexto("");
                //Paint paint = (circulo).getConfTexto().getPaint();
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
                paint.setColor(circulo.getConfTexto().getCor());
                paint.setAlpha(circulo.getConfTexto().getAlpha());
                paint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
                String text = circulo.getTexto();
                Rect bounds = new Rect();
                paint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, ((Circulo)figura).getTamTexto(), getActivity().getResources().getDisplayMetrics()));
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setTypeface(Typeface.DEFAULT);
                paint.getTextBounds(text, 0, text.length(), bounds);

                Point tamText = new Point(Math.abs(bounds.left-bounds.right),Math.abs(bounds.top-bounds.bottom));
                int dimensoes[] = (new int[]{tamText.x,tamText.y});

                canvas.drawText(text, circulo.getRaio() - dimensoes[0]/2, circulo.getRaio() + dimensoes[1]/2,paint);
            }
			((Circulo) figura).setRaio(oldRadio);
			figura.setPontos(oldPoints);
		}
        else if(figura instanceof Texto)
        {
            setX(menorX.x);
            setY(menorY.y);
            Texto texto = ((Texto)figura);
			canvas.drawText(texto.getString(),0,texto.getDimensoes()[1],texto.getPaint());
        }


		if(figura instanceof Arco)
		{
			Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
			textPaint.setColor(Sketch2D.corTextoDistancia);
			textPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,Sketch2D.tamanhoTextoAngulo, getResources().getDisplayMetrics()));
			textPaint.setTextAlign(Paint.Align.LEFT);

			String text = new DecimalFormat("##."+Sketch2D.casasPosVirgula).format(Math.abs(((Arco)figura).getDegrees()))+"°";
			Rect bounds = new Rect();
			textPaint.getTextBounds(""+text, 0, text.length(), bounds);
			Point tamText = new Point(Math.abs(bounds.left-bounds.right),Math.abs(bounds.top-bounds.bottom));

			float dy = ((Arco)figura).getRaio()-((Arco)figura).getRaio()/4;
			float dx = ((Arco)figura).getRaio();

			canvas.save();
			//canvas.translate(90, 90); //Consertando deslocamento da linha para centro do canvas
			canvas.drawText(" "+text, dx, dy, textPaint);
			if(!((Arco)figura).getInfoAdd().trim().equals(""))
			{
				canvas.drawText(" "+((Arco)figura).getInfoAdd(), dx, dy+tamText.y, textPaint);
			}


			canvas.restore();
		}
		else if(linha_distancia)
		{
			setX(menorX.x);
			setY(menorY.y);
			for(Point p : figura.getPontos())
			{
				p.x *= figura.getConfiguracoes().getEscala() * figura.getConfiguracoes().getZoom();
				p.y *= figura.getConfiguracoes().getEscala() * figura.getConfiguracoes().getZoom();
			}

			Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
			textPaint.setColor(Sketch2D.corTextoDistancia);
			textPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, Sketch2D.tamanhoTextoDistancia, getResources().getDisplayMetrics()));
			textPaint.setTextAlign(Paint.Align.LEFT);

			double tamL = ((Linha) figura).getModulo();
			//String text = ""+(int)tamL+" dp";
			//String text = ""+(int)(tamL/Sketch2D.proporcao)+" m";
			String text = new DecimalFormat("##."+Sketch2D.casasPosVirgula).format(tamL/Sketch2D.proporcao)+" m";
            float bearing = Sketch2D.bearingValue;
            boolean showBearing = Sketch2D.showBearingText;
            String text2 = new DecimalFormat("##."+Sketch2D.casasPosVirgula).format(bearing)+" °";

            Rect bounds = new Rect();
			textPaint.getTextBounds(""+text, 0, text.length(), bounds);
			Point tamText = new Point(Math.abs(bounds.left-bounds.right),Math.abs(bounds.top-bounds.bottom));


			float angle  = ((Linha) figura).getInclinacao();
			int dy = -10;
			int dx = 10;

			Point parado = figura.getPontos().get(1);
			Point move = figura.getPontos().get(0);

			canvas.save();
			canvas.translate(90, 90); //Consertando deslocamento da linha para centro do canvas
			canvas.drawPath(((Linha) figura).getCaminho(getX(), getY()), figura.getPaint());


			float rot  = 90 - Math.abs(angle);
			float radangle = (float)(rot*Math.PI)/180;
			int gap = (int)(Sketch2D.ajustaTextoProporcao*tamL);
			if(gap<10 || !Sketch2D.ajustaTextoLinha) gap = 10;

            int ys=tamText.y+20;

			if(move.y <= parado.y)
			{
				dx+= tamL * Math.cos(Math.abs(radangle));
				if(move.x>parado.x)
				{
					dx = 0;
				}
				dy+= tamL * Math.sin(Math.abs(radangle));
				canvas.translate(dx, dy);
				if(angle<0)
				{
					rot *= -1;
				}
				canvas.rotate(rot);
				if(angle>=0)
				{
					canvas.drawText("" + text, -tamText.x - gap, 0, textPaint);
                    if(showBearing)
                        canvas.drawText("" + text2, -tamText.x - gap, ys, textPaint);
				}
				else
				{
					canvas.drawText("" + text, gap, 0, textPaint);
                    if(showBearing)
                        canvas.drawText("" + text2, gap, ys, textPaint);
				}
			}
			else if(angle<0)
			{
				rot *= -1;
				dx+= tamL * Math.cos(Math.abs(radangle));
				canvas.translate(dx,0);
				canvas.rotate(rot);
				canvas.drawText("" + text, -tamText.x - gap, dy, textPaint);
                if(showBearing)
                    canvas.drawText("" + text2, -tamText.x - gap,2*dy+ys, textPaint);
			}
			else
			{
				canvas.rotate(rot);
				canvas.drawText("" + text, dx + gap, dy, textPaint);
                if(showBearing)
                    canvas.drawText("" + text2, dx + gap, dy+ys, textPaint);
			}

			canvas.restore();
		}
		if(figura instanceof Texto)
		{
			//this.setBackgroundColor(Color.argb(120,100,100,100));
		}
		figura.setView(this);
	}
}
