package com.imobilis.sketch2dframework;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Criado por Maicon em 31/05/2016.
 */
public class SketchParent extends FrameLayout
{

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
		//Let the framework do its thing
		super.dispatchDraw(canvas);
		//Draw the grid lines
		for (int i=0; i <= getWidth(); i += (getWidth() / /*mColumnCount*/ 5)) {
			canvas.drawLine(i, 0, i, getHeight(), new Configuracoes().getPaint()/*mGridPaint*/);
		}
		for (int i=0; i <= getHeight(); i += (getHeight() / /*mColumnCount*/ 5)) {
			canvas.drawLine(0, i, getWidth(), i, new Configuracoes().getPaint()/*mGridPaint*/);
		}
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
	}
}
