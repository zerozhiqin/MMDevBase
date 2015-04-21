package dev.misono.anim.sprite;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Rect;

public class TranslateAnimSprite extends BitmapSprite {

	Rect srcMat = null;
	Rect desMat = null;

	public TranslateAnimSprite(String path, float w, int delay, int duration, AssetManager ass) {
		super(path, w, delay, duration, ass);
		srcMat = new Rect(0, 0, (int) w, h);
		desMat = new Rect();
	}

	@Override
	public void init() {
		super.init();
		srcMat = new Rect(0, 0, src.getWidth(), src.getHeight());
	}

	public int x, y;
	public int startX, startY, targetX, targetY;
	private int offX, offY;

	public void init(float startX, float startY, float targetX, float targetY) {
		this.startX = (int) startX;
		this.startY = (int) startY;
		this.targetX = (int) targetX;
		this.targetY = (int) targetY;
		offX = (int) (targetX - startX);
		offY = (int) (targetY - startY);
	}

	public void draw(Canvas canvas) {
		if (src != null && !src.isRecycled()) {
			canvas.drawBitmap(src, srcMat, desMat, null);
		}
	}

	@Override
	public void onUpdate(float factor, long l) {
		x = (int) (startX + factor * offX);
		y = (int) (startY + factor * offY);
		desMat.set(x, y, x + w, y + h);
	}
}
