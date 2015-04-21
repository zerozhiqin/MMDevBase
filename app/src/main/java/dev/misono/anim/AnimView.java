package dev.misono.anim;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

import java.util.ArrayList;

import dev.misono.anim.AnimScene.OnAnimEndListener;

public class AnimView extends View implements AnimatorUpdateListener {

	@SuppressLint("NewApi")
	public AnimView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
    }

	public AnimView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public AnimView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AnimView(Context context) {
		super(context);
	}

	OnAnimEndListener onAnimEndListener = null;

	public void setOnAnimEndListener(OnAnimEndListener onAnimEndListener) {
		this.onAnimEndListener = onAnimEndListener;
	}

	int delay;

	public void addScene(AnimScene scene) {
		scenes.add(scene);
		scene.delay = delay;
		delay += scene.scenePlayTime;
	}

	public enum PlayState {
		NoPre, NoNext, Playing, GoPlay;
	}

	public PlayState playPreScene() {
		if (currentScene.valueAnimator.isRunning()) {
			return PlayState.Playing;
		}
		if (playIndex < 0) {
			playIndex = 0;
			return PlayState.NoPre;
		}
		currentScene.valueAnimator.reverse();
		currentScene.valueAnimator.addListener(playPreListener);
		currentScene.valueAnimator.addUpdateListener(this);
		return PlayState.GoPlay;
	}

	public PlayState playNextScene() {
		if (currentScene.valueAnimator.isRunning()) {
			return PlayState.Playing;
		}
		if (playIndex >= scenes.size()) {
			playIndex = scenes.size() - 1;
			return PlayState.NoNext;
		}
		currentScene.valueAnimator.start();
		currentScene.valueAnimator.addListener(playNextListener);
		currentScene.valueAnimator.addUpdateListener(this);
		return PlayState.GoPlay;
	}

	boolean playNextWhenInited = false;

	public void playNextWhenInited() {
		playNextWhenInited = true;
	}

	AnimatorListener playPreListener = new AnimatorListener() {
		@Override
		public void onAnimationStart(Animator arg0) {
		}

		@Override
		public void onAnimationRepeat(Animator arg0) {
		}

		@Override
		public void onAnimationEnd(Animator arg0) {
			onAnimEndListener.onAnimEnd(currentScene);
			currentScene.valueAnimator.removeAllListeners();
			currentScene.playEnd = false;
			playIndex--;
			if (playIndex >= 0) {
				resetTarget();
			}
		}

		@Override
		public void onAnimationCancel(Animator arg0) {
		}
	};
	AnimatorListener playNextListener = new AnimatorListener() {
		@Override
		public void onAnimationStart(Animator arg0) {
		}

		@Override
		public void onAnimationRepeat(Animator arg0) {
		}

		@Override
		public void onAnimationEnd(Animator arg0) {
			onAnimEndListener.onAnimEnd(currentScene);
			currentScene.valueAnimator.removeAllListeners();
			currentScene.playEnd = true;
			playIndex++;
			if (playIndex < scenes.size()) {
				resetTarget();
			}
		}

		@Override
		public void onAnimationCancel(Animator arg0) {
		}
	};

	public void init() {
		playIndex = 0;
		resetTarget();
	}

	private void resetTarget() {
		try {
			preScene = scenes.get(playIndex - 1);
		} catch (Exception e) {
			preScene = null;
		}
		try {
			currentScene = scenes.get(playIndex);
		} catch (Exception e) {
			currentScene = null;
		}
		try {
			nextScene = scenes.get(playIndex + 1);
		} catch (Exception e) {
			nextScene = null;
		}
		gc();
		if (playNextWhenInited) {
			playNextWhenInited = false;
			playNextScene();
		}
	}

	private void gc() {
		for (int i = 0; i < scenes.size(); i++) {
			if (i >= playIndex - 1 && i < playIndex + 1) {
			} else {
				if (scenes.get(i).isInit) {
					scenes.get(i).recycle();
				}
			}
		}

		for (int i = 0; i < scenes.size(); i++) {
			if (i >= playIndex - 1 && i < playIndex + 1) {
				if (!scenes.get(i).isInit) {
					scenes.get(i).init();
				}
			} else {
			}
		}
	}

	AnimScene nextScene;
	AnimScene currentScene;
	AnimScene preScene;
	ArrayList<AnimScene> scenes = new ArrayList<>();
	int playIndex = -1;

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(0xffe7e4dd);
		if (preScene != null) {
			for (int i = 0; i < preScene.anims.size(); i++) {
				preScene.anims.get(i).draw(canvas);
			}
		}
		if (currentScene != null) {
			for (int i = 0; i < currentScene.anims.size(); i++) {
				currentScene.anims.get(i).draw(canvas);
			}
		}
	}

	@Override
	public void onAnimationUpdate(ValueAnimator arg0) {
		postInvalidate();
	}
}
