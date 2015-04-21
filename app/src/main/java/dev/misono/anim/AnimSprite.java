package dev.misono.anim;

import android.graphics.Canvas;
import android.view.animation.Interpolator;

import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

public abstract class AnimSprite implements AnimatorUpdateListener {

	ValueAnimator valueAnimator;
	public int w, h;
	public int delay, duration;

	public AnimSprite(float w, int delay, int duration) {
		this.w = (int) w;
		this.delay = delay;
		this.duration = duration;
		valueAnimator = ValueAnimator.ofFloat(0, 1);
		valueAnimator.setDuration(duration);
		valueAnimator.addUpdateListener(this);

	}

	public void setInterpolator(Interpolator interpolator) {
		valueAnimator.setInterpolator(interpolator);
	}

	public abstract void recycle();

	public abstract void draw(Canvas canvas);

	public abstract void init();

	@Override
	public void onAnimationUpdate(ValueAnimator arg0) {
		float fl = (float) arg0.getAnimatedValue();
		onUpdate(fl, arg0.getCurrentPlayTime());
	}

	public abstract void onUpdate(float factor, long l);
}
