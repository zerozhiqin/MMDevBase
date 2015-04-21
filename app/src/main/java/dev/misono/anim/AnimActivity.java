package dev.misono.anim;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.SeekBar;

import java.util.ArrayList;

import dev.misono.anim.AnimScene.OnAnimEndListener;
import dev.misono.anim.AnimView.PlayState;
import dev.misono.anim.sprite.FadeBitmapSprite;
import dev.misono.anim.sprite.RotateSprite;
import dev.misono.anim.sprite.ScaleUpBitmapSprite;
import dev.misono.anim.sprite.TranslateAnimSprite;
import dev.misono.util.DensityUtil;
import dev.misono.util.ZZ;

public class AnimActivity extends Activity implements OnClickListener, OnGestureListener, OnAnimEndListener {
	AnimView animView;
	SeekBar seekBar = null;

	GestureDetector gestureDetector;

	@SuppressLint("ClickableViewAccessibility")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		animView = new AnimView(this);
		setContentView(animView);

		AnimScene scene1 = buildScaneTest(animView, getAssets());
		AnimScene scene2 = buildScaneTest(animView, getAssets());
		AnimScene scene3 = buildScaneTest(animView, getAssets());
		AnimScene scene4 = buildScaneTest(animView, getAssets());
		AnimScene scene5 = buildScaneTest(animView, getAssets());
		AnimScene scene6 = buildScaneTest(animView, getAssets());

		animView.init();
		
		animView.setOnAnimEndListener(this);
		gestureDetector = new GestureDetector(this, this);

		animView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		});
	}

	boolean playNext = true;

	@Override
	public void onClick(View v) {
		if (playNext) {
			PlayState playState = animView.playNextScene();
			if (playState == PlayState.NoNext) {
				animView.playPreScene();
				playNext = false;
			}
		} else {
			PlayState playState = animView.playPreScene();
			if (playState == PlayState.NoPre) {
				animView.playNextScene();
				playNext = true;
			}
		}
	}

	ArrayList<Float> points = new ArrayList<Float>();

	public enum SlideState {
		None, Left, Right, Cancel;
	}

	SlideState slideState = SlideState.None;

	@Override
	public boolean onDown(MotionEvent e) {
		slideState = SlideState.None;
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		if (Math.abs(distanceX) > Math.abs(distanceY) / 2) {
			if (slideState != SlideState.Cancel) {
				if (distanceX < 0) {
					slideState = SlideState.Right;
				} else if (distanceX > 0) {
					slideState = SlideState.Left;
				}
			}
		} else {
			slideState = SlideState.Cancel;
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		ZZ.z("slideState : " + slideState);
		if (slideState == SlideState.Left && velocityX < 0) {
			animView.playNextScene();
		}
		if (slideState == SlideState.Right && velocityX > 0) {
			animView.playNextScene();
		}
		return false;
	}

	@Override
	public void onAnimEnd(AnimScene currentScene) {
		// AnimEnd
		ZZ.z("anim End");
	}

	static String guideRes = "guideRes_3/";
	public static AnimScene buildScaneTest(AnimView animView, AssetManager ass) {
		AnimScene animScene = new AnimScene("Test");
		animScene.setDuration(5000);

		FadeBitmapSprite sprite1 = new FadeBitmapSprite(guideRes + "4_1.png", DensityUtil.screenW, 0, 800, ass);
		sprite1.setInterpolator(new OvershootInterpolator(1.4f));
		sprite1.init(0, DensityUtil.screenH - sprite1.h);

		RotateSprite sprite2 = new RotateSprite(guideRes + "4_2.png", DensityUtil.screenW * 0.56f, 1000, 4000, ass);
		sprite2.setInterpolator(new DecelerateInterpolator(1));
		sprite2.init(sprite2.w / 2, DensityUtil.screenH - sprite2.h / 2, 180, true);

		TranslateAnimSprite sprite4 = new TranslateAnimSprite(guideRes + "4_4.png", DensityUtil.screenW, 2000, 800, ass);
		sprite4.setInterpolator(new BounceInterpolator());
		sprite4.init(0, -sprite4.h, 0, 0);

		ScaleUpBitmapSprite sprite3 = new ScaleUpBitmapSprite(guideRes + "4_3.png", DensityUtil.screenW * 0.35f, 1000, 400, ass);
		sprite3.setInterpolator(new DecelerateInterpolator(1));
		sprite3.init(DensityUtil.screenW - sprite3.w - DensityUtil.screenW * 0.07f, sprite4.h * 0.56f, 0.1f, 1.0f);

		TranslateAnimSprite sprite5 = new TranslateAnimSprite(guideRes + "4_5.png", DensityUtil.screenW * 0.7f, 3000, 2000, ass);
		sprite5.setInterpolator(new DecelerateInterpolator(2));
		sprite5.init(DensityUtil.screenW - sprite5.w, DensityUtil.screenH, DensityUtil.screenW - sprite5.w, DensityUtil.screenH
				- sprite5.h);

		animScene.addSprite(sprite1);
		animScene.addSprite(sprite2);
		animScene.addSprite(sprite3);
		animScene.addSprite(sprite5);
		animScene.addSprite(sprite4);

		animView.addScene(animScene);
		return animScene;
	}
}
