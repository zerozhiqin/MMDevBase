package dev.misono.anim.sprite;

import android.graphics.Canvas;
import android.graphics.Color;

import dev.misono.anim.AnimSprite;

public class FadeColorSprite extends AnimSprite {
	int red;
	int green;
	int blue;
	int color;

	public FadeColorSprite(int delay, int duration, int color) {
		super(0, delay, duration);
		red = Color.red(color);
		green = Color.green(color);
		blue = Color.blue(color);
	}

	@Override
	public void recycle() {
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawColor(color);
	}

	@Override
	public void init() {
	}

	@Override
	public void onUpdate(float factor, long l) {
		int alpha = (int) (factor * 255);
		color = Color.argb(alpha, red, green, blue);
	}

}
