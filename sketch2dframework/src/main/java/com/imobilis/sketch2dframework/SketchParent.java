package com.imobilis.sketch2dframework;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
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
	public static int qtdLinhas = 5, qtdColunas = 5, corLinhas = Color.rgb(200, 128, 50), alphaLinhas = 30;
	public static boolean atras = false, mostraEscala = true, pontilhado = false;

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
			for(int i = 0; i <= getWidth(); i += (getWidth() /  SketchParent.qtdLinhas))
			{
				canvas.drawLine(i, 0, i, getHeight(), conf.getPaint());
			}
			for(int i = 0; i <= getHeight(); i += (getHeight() /  SketchParent.qtdColunas))
			{
				canvas.drawLine(0, i, getWidth(), i, conf.getPaint());
			}
		}
		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		textPaint.setColor(SketchParent.corLinhas);
		textPaint.setAlpha(2*SketchParent.alphaLinhas);
		textPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics()));
		textPaint.setTextAlign(Paint.Align.LEFT);
		double tamL = getWidth() / SketchParent.qtdLinhas;
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
				//text = (tamL / dm.densityDpi) * 2.5 / 100 + " m";
				text = new DecimalFormat("##."+Sketch2D.casasPosVirgula).format((tamL/Sketch2D.proporcao))+" m";
				break;
			case Sketch2D.UNIDADE_KM:
				text = (tamL / dm.densityDpi) * 2.5 / 100 / 1000 + " km";
				break;
			case Sketch2D.UNIDADE_INCH:
				text = (tamL / dm.densityDpi) + " inch";
				break;
		}
		//text = (tamL/dm.densityDpi)*2.5 + " cm";
		Rect bounds = new Rect();
		textPaint.getTextBounds(text, 0, text.length(), bounds);
		Point tamText = new Point(Math.abs(bounds.left - bounds.right), Math.abs(bounds.top - bounds.bottom));
		canvas.drawText("" + text, getWidth() - bounds.width() - 10, getHeight() - bounds.height(), textPaint);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		if(!mostraEscala) return;

		if(SketchParent.atras)
		{
			for(int i = 0; i <= getWidth(); i += (getWidth() / SketchParent.qtdLinhas))
			{
				canvas.drawLine(i, 0, i, getHeight(), conf.getPaint());
			}
			for(int i = 0; i <= getHeight(); i += (getHeight() /  SketchParent.qtdColunas))
			{
				canvas.drawLine(0, i, getWidth(), i, conf.getPaint());
			}
		}
		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		textPaint.setColor(SketchParent.corLinhas);
		textPaint.setAlpha(2*SketchParent.alphaLinhas);
		textPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics()));
		textPaint.setTextAlign(Paint.Align.LEFT);
		double tamL = getWidth() / SketchParent.qtdLinhas;
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
				//text = (tamL*Sketch2D.proporcao) + " m";
				text = new DecimalFormat("##."+Sketch2D.casasPosVirgula).format((tamL/Sketch2D.proporcao))+" m";
				break;
			case Sketch2D.UNIDADE_KM:
				text = (tamL / dm.densityDpi) * 2.5 / 100 / 1000 + " km";
				break;
			case Sketch2D.UNIDADE_INCH:
				text = (tamL / dm.densityDpi) + " inch";
				break;
		}
		//text = (tamL/dm.densityDpi)*2.5 + " cm";
		Rect bounds = new Rect();
		textPaint.getTextBounds(text, 0, text.length(), bounds);
		Point tamText = new Point(Math.abs(bounds.left - bounds.right), Math.abs(bounds.top - bounds.bottom));
		canvas.drawText("" + text, getWidth() - bounds.width() - 10, getHeight() - bounds.height(), textPaint);
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

	public static void setQtdColunas(int qtdColunas)
	{
		SketchParent.qtdColunas = qtdColunas;
	}

	public static void setQtdLinhas(int qtdLinhas)
	{
		SketchParent.qtdLinhas = qtdLinhas;
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
}
