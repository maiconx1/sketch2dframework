package com.imobilis.sketch2d;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.ScaleGestureDetector;

import com.imobilis.sketch2dframework.Circulo;
import com.imobilis.sketch2dframework.Figura;
import com.imobilis.sketch2dframework.SketchParent;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Maicon on 22/11/16.
 */

public class SketchParentZoom extends SketchParent//  implements ScaleGestureDetector.OnScaleGestureListener
{
	public SketchParentZoom(Context context) {
		super(context);
	}

	public SketchParentZoom(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SketchParentZoom(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	/*private enum Mode {
		NONE,
		DRAG,
		ZOOM
	}

	private static final String TAG = "ZoomLayout";
	private static final float MIN_ZOOM = 1.0f;
	private static final float MAX_ZOOM = 4.0f;

	private Mode mode = Mode.NONE;
	private float scale = 1.0f;
	private float lastScaleFactor = 0f;

	// Where the finger first  touches the screen
	private float startX = 0f;
	private float startY = 0f;

	// How much to translate the canvas
	private float dx = 0f;
	private float dy = 0f;
	private float prevDx = 0f;
	private float prevDy = 0f;

	public SketchParentZoom(Context context) {
		super(context);
		init(context);
	}

	public SketchParentZoom(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public SketchParentZoom(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		final ScaleGestureDetector scaleDetector = new ScaleGestureDetector(context, this);
		this.setOnTouchListener(new View.OnTouchListener()
		{
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent)
			{
				switch (motionEvent.getAction() & MotionEvent.ACTION_MASK)
				{
					case MotionEvent.ACTION_DOWN:
						Log.i(TAG, "DOWN");
						if (scale > MIN_ZOOM) {
							mode = Mode.DRAG;
							startX = motionEvent.getX() - prevDx;
							startY = motionEvent.getY() - prevDy;
						}
						break;
					case MotionEvent.ACTION_MOVE:
						if (mode == Mode.DRAG) {
							dx = motionEvent.getX() - startX;
							dy = motionEvent.getY() - startY;
						}
						break;
					case MotionEvent.ACTION_POINTER_DOWN:
						mode = Mode.ZOOM;
						break;
					case MotionEvent.ACTION_POINTER_UP:
						mode = Mode.DRAG;
						break;
					case MotionEvent.ACTION_UP:
						Log.i(TAG, "UP");
						mode = Mode.NONE;
						prevDx = dx;
						prevDy = dy;
						break;
				}
				scaleDetector.onTouchEvent(motionEvent);

				if ((mode == Mode.DRAG && scale >= MIN_ZOOM) || mode == Mode.ZOOM)
				{
					for(int i = 0;i<getChildCount();i++)
					{
						getParent().requestDisallowInterceptTouchEvent(true);
						float maxDx = (child(i).getWidth() - (child(i).getWidth() / scale)) / 2 * scale;
						float maxDy = (child(i).getHeight() - (child(i).getHeight() / scale)) / 2 * scale;
						dx = Math.min(Math.max(dx, -maxDx), maxDx);
						dy = Math.min(Math.max(dy, -maxDy), maxDy);
						Log.i(TAG, "Width: " + child(i).getWidth() + ", scale " + scale + ", dx " + dx
								+ ", max " + maxDx);
						applyScaleAndTranslation(i);
					}
				}

				return true;
			}
		});
	}

	// ScaleGestureDetector

	@Override
	public boolean onScaleBegin(ScaleGestureDetector scaleDetector) {
		Log.i(TAG, "onScaleBegin");
		return true;
	}

	@Override
	public boolean onScale(ScaleGestureDetector scaleDetector) {
		float scaleFactor = scaleDetector.getScaleFactor();
		Log.i(TAG, "onScale" + scaleFactor);
		if (lastScaleFactor == 0 || (Math.signum(scaleFactor) == Math.signum(lastScaleFactor))) {
			scale *= scaleFactor;
			scale = Math.max(MIN_ZOOM, Math.min(scale, MAX_ZOOM));
			lastScaleFactor = scaleFactor;
		} else {
			lastScaleFactor = 0;
		}
		return true;
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector scaleDetector) {
		Log.i(TAG, "onScaleEnd");
	}

	private void applyScaleAndTranslation(int i)
	{
		child(i).setScaleX(scale);
		child(i).setScaleY(scale);
		child(i).setTranslationX(dx);
		child(i).setTranslationY(dy);
	}

	private View child(int i) {
		return getChildAt(i);
	}*/

	public interface ZoomViewListener {

		void onZoomStarted(float zoom, float zoomx, float zoomy);

		void onZooming(float zoom, float zoomx, float zoomy);

		void onZoomEnded(float zoom, float zoomx, float zoomy);
	}

	// zooming
	float zoom = 1.0f;
	float maxZoom = 2.0f;
	float smoothZoom = 1.0f;
	float zoomX, zoomY;
	float smoothZoomX, smoothZoomY;
	private boolean scrolling; // NOPMD by karooolek on 29.06.11 11:45


	// touching variables
	private long lastTapTime;
	private float touchStartX, touchStartY;
	private float touchLastX, touchLastY;
	private float startd;
	private boolean pinching;
	private float lastd;
	private float lastdx1, lastdy1;
	private float lastdx2, lastdy2;

	// drawing
	private final Matrix m = new Matrix();
	private final Paint p = new Paint();

	// listener
	ZoomViewListener listener;

	private Bitmap ch;

	public float getZoom() {
		return zoom;
	}

	public float getMaxZoom() {
		return maxZoom;
	}

	public void setMaxZoom(final float maxZoom) {
		if (maxZoom < 1.0f) {
			return;
		}

		this.maxZoom = maxZoom;
	}

	public void zoomTo(final float zoom, final float x, final float y) {
		this.zoom = Math.min(zoom, maxZoom);
		zoomX = x;
		zoomY = y;
		smoothZoomTo(this.zoom, x, y);
	}

	public void smoothZoomTo(final float zoom, final float x, final float y) {
		smoothZoom = clamp(1.0f, zoom, maxZoom);
		smoothZoomX = x;
		smoothZoomY = y;
		if (listener != null) {
			listener.onZoomStarted(smoothZoom, x, y);
		}
	}

	public ZoomViewListener getListener() {
		return listener;
	}

	public void setListner(final ZoomViewListener listener) {
		this.listener = listener;
	}

	public float getZoomFocusX() {
		return zoomX * zoom;
	}

	public float getZoomFocusY() {
		return zoomY * zoom;
	}

	@Override
	public boolean dispatchTouchEvent(final MotionEvent ev) {
		// single touch
		if (ev.getPointerCount() == 1) {
			processSingleTouchEvent(ev);
		}

		// // double touch
		if (ev.getPointerCount() == 2) {
			processDoubleTouchEvent(ev);
		}

		// redraw
		getRootView().invalidate();
		invalidate();

		return true;
	}

	private void processSingleTouchEvent(final MotionEvent ev) {

		final float x = ev.getX();
		final float y = ev.getY();


		final boolean touchingMiniMap = x >= 10.0f && x <= 10.0f && y >= 10.0f && y <= 10.0f;

		processSingleTouchOutsideMinimap(ev);
	}

	private void processSingleTouchOutsideMinimap(final MotionEvent ev) {
		final float x = ev.getX();
		final float y = ev.getY();
		float lx = x - touchStartX;
		float ly = y - touchStartY;
		final float l = (float) Math.hypot(lx, ly);
		float dx = x - touchLastX;
		float dy = y - touchLastY;
		touchLastX = x;
		touchLastY = y;

		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				touchStartX = x;
				touchStartY = y;
				touchLastX = x;
				touchLastY = y;
				dx = 0;
				dy = 0;
				lx = 0;
				ly = 0;
				scrolling = false;
				break;

			case MotionEvent.ACTION_MOVE:
				if (scrolling || (smoothZoom > 1.0f && l > 30.0f)) {
					if (!scrolling) {
						scrolling = true;
						ev.setAction(MotionEvent.ACTION_CANCEL);
						super.dispatchTouchEvent(ev);
					}
					smoothZoomX -= dx / zoom;
					smoothZoomY -= dy / zoom;
					return;
				}
				break;

			case MotionEvent.ACTION_OUTSIDE:
			case MotionEvent.ACTION_UP:

				// tap
				if (l < 30.0f) {
					// check double tap
					if (System.currentTimeMillis() - lastTapTime < 500) {
						if (smoothZoom == 1.0f) {
							smoothZoomTo(maxZoom, x, y);
						} else {
							smoothZoomTo(1.0f, getWidth() / 2.0f, getHeight() / 2.0f);
						}
						lastTapTime = 0;
						ev.setAction(MotionEvent.ACTION_CANCEL);
						super.dispatchTouchEvent(ev);
						return;
					}

					lastTapTime = System.currentTimeMillis();

					performClick();
				}
				break;

			default:
				break;
		}

		ev.setLocation(zoomX + (x - 0.5f * getWidth()) / zoom, zoomY + (y - 0.5f * getHeight()) / zoom);

		ev.getX();
		ev.getY();

		super.dispatchTouchEvent(ev);
	}

	private void processDoubleTouchEvent(final MotionEvent ev) {
		final float x1 = ev.getX(0);
		final float dx1 = x1 - lastdx1;
		lastdx1 = x1;
		final float y1 = ev.getY(0);
		final float dy1 = y1 - lastdy1;
		lastdy1 = y1;
		final float x2 = ev.getX(1);
		final float dx2 = x2 - lastdx2;
		lastdx2 = x2;
		final float y2 = ev.getY(1);
		final float dy2 = y2 - lastdy2;
		lastdy2 = y2;

		// pointers distance
		final float d = (float) Math.hypot(x2 - x1, y2 - y1);
		final float dd = d - lastd;
		lastd = d;
		final float ld = Math.abs(d - startd);

		Math.atan2(y2 - y1, x2 - x1);
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				startd = d;
				pinching = false;
				break;

			case MotionEvent.ACTION_MOVE:
				if (pinching || ld > 30.0f) {
					pinching = true;
					final float dxk = 0.5f * (dx1 + dx2);
					final float dyk = 0.5f * (dy1 + dy2);
					smoothZoomTo(Math.max(1.0f, zoom * d / (d - dd)), zoomX - dxk / zoom, zoomY - dyk / zoom);
				}

				break;

			case MotionEvent.ACTION_UP:
			default:
				pinching = false;
				break;
		}

		ev.setAction(MotionEvent.ACTION_CANCEL);
		super.dispatchTouchEvent(ev);
	}

	private float clamp(final float min, final float value, final float max) {
		return Math.max(min, Math.min(value, max));
	}

	private float lerp(final float a, final float b, final float k) {
		return a + (b - a) * k;
	}

	private float bias(final float a, final float b, final float k) {
		return Math.abs(b - a) >= k ? a + k * Math.signum(b - a) : b;
	}

	@Override
	protected void dispatchDraw(final Canvas canvas) {
		// do zoom
		//super.dispatchDraw(canvas);
		zoom = lerp(bias(zoom, smoothZoom, 0.05f), smoothZoom, 0.2f);
		smoothZoomX = clamp(0.5f * getWidth() / smoothZoom, smoothZoomX, getWidth() - 0.5f * getWidth() / smoothZoom);
		smoothZoomY = clamp(0.5f * getHeight() / smoothZoom, smoothZoomY, getHeight() - 0.5f * getHeight() / smoothZoom);

		zoomX = lerp(bias(zoomX, smoothZoomX, 0.1f), smoothZoomX, 0.35f);
		zoomY = lerp(bias(zoomY, smoothZoomY, 0.1f), smoothZoomY, 0.35f);
		if (zoom != smoothZoom && listener != null) {
			listener.onZooming(zoom, zoomX, zoomY);
		}

		final boolean animating = Math.abs(zoom - smoothZoom) > 0.0000001f
				|| Math.abs(zoomX - smoothZoomX) > 0.0000001f || Math.abs(zoomY - smoothZoomY) > 0.0000001f;

		// nothing to draw
		if (getChildCount() == 0) {
			return;
		}

		for(Figura c : NovaNovaMain.figuras)
		{
			Circulo circulo = (Circulo)c;
			canvas.drawCircle(circulo.getX(0), circulo.getY(0), circulo.getRaio(), circulo.getPaint());
			Log.d("FIGURAS", "DESENHA");
		}

		// prepare matrix
		m.setTranslate(0.5f * getWidth(), 0.5f * getHeight());
		m.preScale(zoom, zoom);
		m.preTranslate(-clamp(0.5f * getWidth() / zoom, zoomX, getWidth() - 0.5f * getWidth() / zoom),
				-clamp(0.5f * getHeight() / zoom, zoomY, getHeight() - 0.5f * getHeight() / zoom));

		// get view
		float x = 0, y = 0;
		for(int i = 0;i<getChildCount();i++)
		{
			final View v = getChildAt(i);
			//m.preTranslate(v.getLeft(), v.getTop());
			m.preTranslate(v.getX() - x, v.getY() - y);
			x = v.getX();
			y = v.getY();

			// get drawing cache if available
			/*if (animating && ch == null && isAnimationCacheEnabled()) {
				v.setDrawingCacheEnabled(true);
				ch = v.getDrawingCache();
			}*/

			// draw using cache while animating
			if (animating && isAnimationCacheEnabled() && ch != null) {
				//p.setColor(0xffffffff);
				canvas.drawBitmap(ch, m, p);
			} else { // zoomed or cache unavailable
				ch = null;
				canvas.save();
				canvas.concat(m);
				v.draw(canvas);
				canvas.restore();
			}

			// redraw
			// if (animating) {
			//getRootView().invalidate();
			//invalidate();
		}
	}

	private float firstFingerpreviousX;
	private float firstFingerpreviousY;
	private float secondFingerpreviousX;
	private float secondFingerpreviousY;
	private int touchState;
	private final int TOUCH = 0, PINCH = 1, ENDOFPINCH = 2;
	private float distance1, distance2;
	private float firstFingernewX, firstFingernewY, secondFingernewX, secondFingernewY;

	@Override public boolean onTouchEvent(MotionEvent event)
	{
		switch (event.getAction() & MotionEvent.ACTION_MASK) {

			// Gesture starts

			case MotionEvent.ACTION_DOWN:
				firstFingerpreviousX = event.getX();
				firstFingerpreviousY = event.getY();
				touchState = TOUCH;
				break;

			// Second finger is now on the show

			case MotionEvent.ACTION_POINTER_DOWN:
				touchState = PINCH;
				secondFingerpreviousX = event.getX(1);
				secondFingerpreviousY = event.getY(1);
				distance1 = makeDistance(firstFingerpreviousX,
						firstFingerpreviousY, secondFingerpreviousX,
						secondFingerpreviousY);
				break;

			// Fingers are moving

			case MotionEvent.ACTION_MOVE:
				System.out.println(touchState);

				if (touchState == PINCH) {
					firstFingernewX = event.getX(0);
					firstFingernewY = event.getX(0);
					secondFingernewX = event.getX(1);
					secondFingernewY = event.getY(1);
					distance2 = makeDistance(firstFingernewX, firstFingernewY,
							secondFingernewX, secondFingernewY);
					break;
				}
				break;

			// Second finger gets tired

			case MotionEvent.ACTION_POINTER_UP:
				touchState = ENDOFPINCH;
				// We calculate the distances between the fingers
				// to know if there is zoom in or out
				if (distance2 > distance1) {
					Log.d("STATUS", "Zoom in detected");
				} else {
					Log.d("STATUS", "Zoom out detected");
				}
				break;
		}
		return true;
	}

// Pythagorean Theorem distance maker method

	public static float makeDistance(float x1, float y1, float x2, float y2) {
		float delta1 = (x2 - x1) * (x2 - x1);
		float delta2 = (y2 - y1) * (y2 - y1);
		float distance = (float) Math.sqrt(delta1 + delta2);
		return distance;
	}
}