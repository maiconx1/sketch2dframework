package com.imobilis.sketch2dframework;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.FrameLayout;

import java.text.DecimalFormat;

/**
 * Criado por Maicon em 31/05/2016.
 */
public class SketchParent extends FrameLayout
{
	private Configuracoes conf;
	public static int  corLinhas = Color.rgb(200, 128, 50), alphaLinhas = 30, gradeMetros = 10;
	public static boolean atras = false, mostraEscala = true, pontilhado = false, mostraEscalaText = true;
    public static Point startFig=new Point(0,0);
    public static Point endFig=new Point(0,0);
	public SketchParent(Context context)
	{
		super(context);
		setWillNotDraw(false);
	}

	public SketchParent(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		setWillNotDraw(false);
	}

	public SketchParent(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		setWillNotDraw(false);
	}

	@Override
	protected void dispatchDraw(Canvas canvas)
	{
		super.dispatchDraw(canvas);
		if(!mostraEscala) return;

		conf = new Configuracoes(SketchParent.pontilhado, Configuracoes.LINHA, 3, true, SketchParent.corLinhas, SketchParent.alphaLinhas);
		if(!SketchParent.atras)
		{
			drawGrid(canvas);
		}
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		if(!mostraEscala) return;

		if(SketchParent.atras)
		{
			drawGrid(canvas);
		}
	}
	private void drawGrid(Canvas canvas)
	{
		int width = endFig.x>getWidth()?endFig.x:getWidth();
		int height =  endFig.y>getHeight()?endFig.y:getHeight();
        int startx = startFig.x<0?startFig.x:0;
        int starty=startFig.y<0?startFig.y:0;

		for(int i = startx; i <= width; i += (int)(gradeMetros*Sketch2D.getProporcao()))//(getWidth() /  SketchParent.qtdLinhas))
		{
			canvas.drawLine(i,starty, i, height, conf.getPaint());
		}
		for(int i = starty; i <= height; i += (int)(gradeMetros*Sketch2D.getProporcao()))//(getHeight() /  SketchParent.qtdColunas))
		{
			canvas.drawLine(startx, i, width, i, conf.getPaint());
		}
		canvas.drawLine(startx,height, width,height, conf.getPaint());
		canvas.drawLine(width,starty, width,height, conf.getPaint());
		setConfigGrid(canvas,startx,height);

	}
	private void setConfigGrid(Canvas canvas,int startx,int height)
	{
		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		textPaint.setColor(SketchParent.corLinhas);
		textPaint.setAlpha(2*SketchParent.alphaLinhas);
		textPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics()));
		textPaint.setTextAlign(Paint.Align.LEFT);
		double tamL = 1;
		DisplayMetrics dm = getResources().getDisplayMetrics();
		String text;
		switch(Sketch2D.unidade)
		{
			default:
			case Sketch2D.UNIDADE_DP:
				text = (tamL) + " dp";
				break;
			case Sketch2D.UNIDADE_CM:
				text = (tamL / dm.densityDpi) * 2.5 + " cm";
				break;
			case Sketch2D.UNIDADE_M:
				text = new DecimalFormat("##."+Sketch2D.casasPosVirgula).format(gradeMetros)+" m";
				break;
			case Sketch2D.UNIDADE_KM:
				text = (tamL / dm.densityDpi) * 2.5 / 100 / 1000 + " km";
				break;
			case Sketch2D.UNIDADE_INCH:
				text = (tamL / dm.densityDpi) + " inch";
				break;
		}
		Rect bounds = new Rect();
		textPaint.getTextBounds(text, 0, text.length(), bounds);
		if(mostraEscalaText)
			canvas.drawText("" + text, startx+10 /*getWidth() - bounds.width() - 10*/,height - bounds.height(), textPaint);
	}
	public static void setMostraEscala(boolean mostraEscala)
	{
		SketchParent.mostraEscala = mostraEscala;
	}

	public static void setAtras(boolean atras)
	{
		SketchParent.atras = atras;
	}

	public static void setCorLinhas(int corLinhas)
	{
		SketchParent.corLinhas = corLinhas;
	}
	public static void setPontilhado(boolean pontilhado)
	{
		SketchParent.pontilhado = pontilhado;
	}

	public static void setAlphaLinhas(int alphaLinhas)
	{
		SketchParent.alphaLinhas = alphaLinhas;
		String hexColor = String.format("#%06X", (0xFFFFFF & SketchParent.corLinhas));
		String hexAlpha = String.format("#%06X", (0xFF & SketchParent.alphaLinhas));
		String hex = "#" + hexAlpha.substring(5, 7) + hexColor.substring(1, 7);
		int cor = Color.parseColor(hex);
		setCorLinhas(cor);
	}

    public static Point getStartFig() {
        return startFig;
    }

    public static void setStartFig(Point startFig) {
        SketchParent.startFig = startFig;
    }

    public static Point getEndFig() {
        return endFig;
    }

    public static void setEndFig(Point endFig) {
        SketchParent.endFig = endFig;
    }
}
