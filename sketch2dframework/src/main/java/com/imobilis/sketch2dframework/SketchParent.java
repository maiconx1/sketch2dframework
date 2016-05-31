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

/**
 * Criado por Maicon em 31/05/2016.
 */
public class SketchParent extends FrameLayout
{
	private Configuracoes conf;

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
		conf = new Configuracoes(Sketch2D.pontilhado, Configuracoes.LINHA, 3, true, Sketch2D.corLinhas, Sketch2D.alphaLinhas);
		if(!Sketch2D.atras)
		{
			for(int i = 0; i <= getWidth(); i += (getWidth() /  Sketch2D.qtdLinhas))
			{
				canvas.drawLine(i, 0, i, getHeight(), conf.getPaint());
			}
			for(int i = 0; i <= getHeight(); i += (getHeight() /  Sketch2D.qtdColunas))
			{
				canvas.drawLine(0, i, getWidth(), i, conf.getPaint());
			}
		}

		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		textPaint.setColor(Sketch2D.corLinhas);
		textPaint.setAlpha(2*Sketch2D.alphaLinhas);
		textPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics()));
		textPaint.setTextAlign(Paint.Align.LEFT);

		double tamL = getWidth()/Sketch2D.qtdLinhas;
		DisplayMetrics dm = getResources().getDisplayMetrics();
		String text = (tamL/dm.densityDpi)*2.5 + " cm";
		Rect bounds = new Rect();
		textPaint.getTextBounds(text, 0, text.length(), bounds);
		Point tamText = new Point(Math.abs(bounds.left-bounds.right),Math.abs(bounds.top-bounds.bottom));
		canvas.drawText("" + text, getWidth() - bounds.width() - 10, getHeight() - bounds.height(), textPaint);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		if(Sketch2D.atras)
		{
			for(int i = 0; i <= getWidth(); i += (getWidth() / Sketch2D.qtdLinhas))
			{
				canvas.drawLine(i, 0, i, getHeight(), conf.getPaint());
			}
			for(int i = 0; i <= getHeight(); i += (getHeight() /  Sketch2D.qtdColunas))
			{
				canvas.drawLine(0, i, getWidth(), i, conf.getPaint());
			}
		}
	}
}
