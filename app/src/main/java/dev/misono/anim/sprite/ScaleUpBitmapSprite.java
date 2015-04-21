package dev.misono.anim.sprite;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Rect;

public class ScaleUpBitmapSprite extends BitmapSprite {
	Rect srcR = null;
	Rect dstR = null;
	float fromScale;
	float toScale;
	float currentScale;
	int cx;
	int cy;

	public ScaleUpBitmapSprite(String path, float w, int delay, int duration, AssetManager ass) {
		super(path, w, delay, duration, ass);
		srcR = new Rect(0, 0, (int) w, h);
	}

	public void init(float cx, float cy, float fromScale, float toScale) {
		this.cx = (int) cx;
		this.cy = (int) cy;
		this.fromScale = fromScale;
		this.toScale = toScale;
		this.currentScale = fromScale;
		dstR = new Rect();
		setDstRect();
	}

	private void setDstRect() {
		int tarw = (int) (w * currentScale);
		int tarh = (int) (h * currentScale);

		int l = cx - tarw / 2;
		int t = cy - tarh / 2;
		dstR.set(l, t, l + tarw, t + tarh);
	}

	@Override
	public void draw(Canvas canvas) {
		if (factor == 0) {
			return;
		}
		if (src != null && !src.isRecycled()) {
			canvas.drawBitmap(src, srcR, dstR, null);
		}
	}

	float factor = 0;

	@Override
	public void onUpdate(float factor, long l) {
		this.factor = factor;
		currentScale = fromScale + factor * (toScale - fromScale);
		setDstRect();
	}

}
