package com.imobilis.sketch2dframework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.EditText;
import android.widget.ImageView;

import com.imobilis.sketch2dframework.Zoom.MoveGestureDetector;
import com.imobilis.sketch2dframework.Zoom.RotateGestureDetector;

import java.text.DecimalFormat;

/**
 * Created by Maicon on 22/11/16.
 */

public class SketchParentZoom extends SketchParent// implements View.OnTouchListener
{
	private Matrix matrix = new Matrix();
	private ScaleGestureDetector SGD;
	private RotateGestureDetector RGD;
	private MoveGestureDetector MGD;
	private SketchParentZoom mZoomableRelativeLayout;

	float mScaleFactor = 1;
	float mRotationDegrees = 0.f;
	float mPivotX;
	float mPivotY;

	public SketchParentZoom(Context context) {
		super(context);
		setStaticTransformationsEnabled(true);
		setup();
	}

	public SketchParentZoom(Context context, AttributeSet attrs) {
		super(context, attrs);
		setStaticTransformationsEnabled(true);
		setup();
	}

	public SketchParentZoom(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setStaticTransformationsEnabled(true);

		setup();
	}

	private void setup()
	{
		mZoomableRelativeLayout = this;
		SGD = new ScaleGestureDetector(Sketch2D.context,new ScaleListener());
		RGD = new RotateGestureDetector(Sketch2D.context,new RotateListener());
		MGD = new MoveGestureDetector(Sketch2D.context,new MoveListener());
	}

	public boolean onTouchEvent(MotionEvent ev) {
		SGD.onTouchEvent(ev);
		RGD.onTouchEvent(ev);
		MGD.onTouchEvent(ev);
		return true;
	}

	@Override
	protected boolean getChildStaticTransformation(View child, Transformation t)
	{
		t.getMatrix().set(matrix);
		return true;
	}

	protected void dispatchDraw(Canvas canvas)
	{
		canvas.save(Canvas.MATRIX_SAVE_FLAG);
		canvas.scale(mScaleFactor, mScaleFactor, mPivotX, mPivotY);
		canvas.rotate(mRotationDegrees);
		super.dispatchDraw(canvas);
		canvas.restore();
	}

	public void scale(float scaleFactor, float pivotX, float pivotY)
	{
		mScaleFactor = Math.min(scaleFactor, 5.0f);
		mPivotX = pivotX;
		mPivotY = pivotY;
		this.invalidate();
		for(Figura f : Sketch2D.getFiguras())
		{
			if(f instanceof Circulo)
				Log.d("PONTO MODIFICADO: ", "(" + f.getView().getX() + ", " + f.getView().getY() + ")");
		}
	}

	public void rotate(float rotationFactor, float pivotX, float pivotY)
	{
		mRotationDegrees = rotationFactor;
		mPivotX = pivotX;
		mPivotY = pivotY;
		this.invalidate();
	}

	public void move(float pivotX, float pivotY)
	{
		mPivotX = pivotX;
		mPivotY = pivotY;
		this.invalidate();
	}

	public void restore()
	{
		mScaleFactor = 1;
		this.invalidate();
	}

	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener
	{
		float startingSpan = 1;
		float endSpan;
		float startFocusX;
		float startFocusY;

		public boolean onScaleBegin(ScaleGestureDetector detector) {
			startingSpan = detector.getCurrentSpan();
			startFocusX = detector.getFocusX();
			startFocusY = detector.getFocusY();
			//mZoomableRelativeLayout.scale(anterior, startFocusX, startFocusY);
			return true;
		}

		public boolean onScale(ScaleGestureDetector detector) {
			Log.d("SPAN", "" + detector.getCurrentSpan());

			//mZoomableRelativeLayout.scale(anterior + (detector.getCurrentSpan() / startingSpan), startFocusX, startFocusY);
			mScaleFactor *= detector.getScaleFactor();
			mZoomableRelativeLayout.scale(mScaleFactor, detector.getFocusX(), detector.getFocusY());
			return true;
		}

		public void onScaleEnd(ScaleGestureDetector detector) {

		}
	}

	private class RotateListener extends RotateGestureDetector.SimpleOnRotateGestureListener
	{
		public boolean onRotate(RotateGestureDetector detector)
		{
			mRotationDegrees -= detector.getRotationDegreesDelta();
			mZoomableRelativeLayout.rotate(mRotationDegrees, detector.getFocusX(), detector.getFocusY());
			return true;
		}
	}

	private class MoveListener extends MoveGestureDetector.SimpleOnMoveGestureListener
	{
		@Override
		public boolean onMove(MoveGestureDetector detector) {
			PointF d = detector.getFocusDelta();
			mPivotX += d.x;
			mPivotY += d.y;
			mZoomableRelativeLayout.move(mPivotX, mPivotY);
			return true;
		}
	}

	private float clamp(final float min, final float value, final float max) {
		return Math.max(min, Math.min(value, max));
	}

	/*private float mScaleFactor = 1.0f;
	private float mRotationDegrees = 0.f;
	private float mFocusX = 0.f;
	private float mFocusY = 0.f;
	private SketchParentZoom mZoomableRelativeLayout;
	private Matrix matrix = new Matrix();

	private ScaleGestureDetector mScaleDetector;
	private RotateGestureDetector mRotateDetector;
	private MoveGestureDetector mMoveDetector;

	public SketchParentZoom(Context context) {
		super(context);
		setup();
	}

	public SketchParentZoom(Context context, AttributeSet attrs) {
		super(context, attrs);
		setup();
	}

	public SketchParentZoom(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setup();
	}

	private void setup()
	{
		setStaticTransformationsEnabled(true);
		mScaleDetector = new ScaleGestureDetector(Sketch2D.context, new ScaleListener());
		mRotateDetector = new RotateGestureDetector(Sketch2D.context, new RotateListener());
		mMoveDetector = new MoveGestureDetector(Sketch2D.context, new MoveListener());
		mZoomableRelativeLayout = this;
	}

	public boolean onTouch(View v, MotionEvent event) {
		mScaleDetector.onTouchEvent(event);
		mRotateDetector.onTouchEvent(event);
		mMoveDetector.onTouchEvent(event);
		mZoomableRelativeLayout.scale(mScaleFactor, mFocusX, mFocusY);
		Log.d("SCALE", mScaleFactor + "");
		// Mmmmmhhhagic!!!
		//  with: mScaleFactor, mRotationDegrees, mFocusX and mFocusY
		//...
		return true; // indicate event was handled
	}

	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			mScaleFactor *= detector.getScaleFactor(); // scale change since previous event
			return true;
		}
	}

	private class RotateListener extends RotateGestureDetector.SimpleOnRotateGestureListener {
		@Override
		public boolean onRotate(RotateGestureDetector detector) {
			mRotationDegrees -= detector.getRotationDegreesDelta();
			Log.d("ROTATE", mScaleFactor + "");
			return true;
		}
	}

	private class MoveListener extends MoveGestureDetector.SimpleOnMoveGestureListener {
		@Override
		public boolean onMove(MoveGestureDetector detector) {
			PointF d = detector.getFocusDelta();
			mFocusX += d.x;
			mFocusY += d.y;
			Log.d("MOVE", mScaleFactor + "");
			return true;
		}
	}

	@Override
	protected boolean getChildStaticTransformation(View child, Transformation t) {

		// apply transform to child view - child triggers this call by call to `invalidate()`
		Log.d("SCALE", "APLICANDO");
		t.getMatrix().set(matrix);
		return true;
	}

	protected void dispatchDraw(Canvas canvas)
	{
		//super.dispatchDraw(canvas);
		canvas.save(Canvas.MATRIX_SAVE_FLAG);
		canvas.scale(mScaleFactor, mScaleFactor, mFocusX, mFocusY);
		super.dispatchDraw(canvas);
		canvas.restore();
	}

	public void scale(float scaleFactor, float pivotX, float pivotY) {
		mScaleFactor = Math.min(scaleFactor, 5.0f);
		mFocusX = pivotX;
		mFocusY = pivotY;
		this.invalidate();
	}

	public void restore() {
		mScaleFactor = 1;
		this.invalidate();
	}*/
}
