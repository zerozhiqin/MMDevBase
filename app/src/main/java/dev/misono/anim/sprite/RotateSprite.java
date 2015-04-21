package dev.misono.anim.sprite;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class RotateSprite extends BitmapSprite {
	Matrix matrix;
	int cx, cy;
	float allDegrees;
	boolean clockwise;

	public RotateSprite(String path, float w, int delay, int duration, AssetManager ass) {
		super(path, w, delay, duration, ass);
		matrix = new Matrix();
	}

	public void init(float cx, float cy, float degreesInSecond, boolean clockwise) {
		this.cx = (int) cx;
		this.cy = (int) cy;
		this.allDegrees = degreesInSecond;
		this.clockwise = clockwise;
		matrix.setTranslate(cx - w / 2, cy - h / 2);
	}

	@Override
	public void draw(Canvas canvas) {
		if (factor > 0) {
			canvas.drawBitmap(src, matrix, null);
		}
	}

	float factor = 0;
	long lastTime = 0;

	@Override
	public void onUpdate(float factor, long l) {
		this.factor = factor;
		float degree = allDegrees * (lastTime - l) / 1000f;
		lastTime = l;
		if (!clockwise) {
			degree *= -1;
		}
		matrix.postRotate(degree, cx, cy);
	}

}
