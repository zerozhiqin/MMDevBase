package dev.misono.anim;

import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

import java.util.ArrayList;

public class AnimScene implements AnimatorUpdateListener {
	public interface OnAnimEndListener {
		public void onAnimEnd(AnimScene currentScene);
	}

	public int scenePlayTime;
	public long delay;
	public ValueAnimator valueAnimator;
	public boolean isInit = false;
	boolean playEnd = false;
	
	public String tag = "";

	public AnimScene(String tag) {
		this.tag = tag;
	}

	public boolean isPlaying() {
		return valueAnimator.isRunning();
	}

	public void setDuration(int time) {
		scenePlayTime = time;
		valueAnimator = ValueAnimator.ofInt(0, scenePlayTime);
		valueAnimator.setDuration(scenePlayTime);
		valueAnimator.addUpdateListener(this);
	}

	ArrayList<AnimSprite> anims = new ArrayList<>();

	public void addSprite(AnimSprite sprite) {
		anims.add(sprite);
	}

	public void recycle() {
		isInit = false;
		for (int i = 0; i < anims.size(); i++) {
			anims.get(i).recycle();
		}
	}

	public void init() {
		isInit = true;
		for (int i = 0; i < anims.size(); i++) {
			anims.get(i).init();
		}
		if (playEnd) {
			valueAnimator.setCurrentPlayTime(scenePlayTime);
		} else {
			valueAnimator.setCurrentPlayTime(0);
		}
	}

	@Override
	public void onAnimationUpdate(ValueAnimator arg0) {
		int realTime = (int) arg0.getAnimatedValue();
		// Log.v("MM", " realTime  : " + realTime);
		for (int i = 0; i < anims.size(); i++) {
			long time = realTime - anims.get(i).delay;
			time = Math.max(time, 0);
			time = Math.min(time, anims.get(i).duration);
			anims.get(i).valueAnimator.setCurrentPlayTime(time);
		}
	}

	@Override
	public String toString() {
		return "AnimScene [tag=" + tag + "]";
	}
	
}
