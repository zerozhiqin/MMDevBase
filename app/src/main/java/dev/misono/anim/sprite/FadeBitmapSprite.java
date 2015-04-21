package dev.misono.anim.sprite;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class FadeBitmapSprite extends BitmapSprite {

	Rect srcMat = null;
	Rect desMat = null;
	// public int x, y;
	Paint paint;

	public FadeBitmapSprite(String path, float w, int delay, int duration, AssetManager ass) {
		super(path, w, delay, duration, ass);
		paint = new Paint();
	}

	public void init(float left, float top) {
		super.init();
		// x = (int) left;
		// y = (int) top;
		srcMat = new Rect(0, 0, src.getWidth(), src.getHeight());
		desMat = new Rect();
		desMat.set((int) left, (int) top, (int) left + w, (int) top + h);
	}

	@Override
	public void draw(Canvas canvas) {
		// if (src != null && !src.isRecycled()) {
		// canvas.drawBitmap(src, x, y, paint);
		// }
		if (src != null && !src.isRecycled()) {
			canvas.drawBitmap(src, srcMat, desMat, paint);
		}
	}

	@Override
	public void onUpdate(float factor, long l) {
		paint.setAlpha((int) Math.min(255 * factor, 255));
	}

}
