package dev.misono.util;

import java.lang.reflect.Field;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * @h hdpi for 480P: density 1.5
 * @xh xhdpi for 720P: density 2
 * @xxh xxhdpi for 1080P: density 3
 * 
 */
public class DensityUtil {
	private static float density = 0f;
	public static int screenW, screenH;

	public static void init(Activity ctx) {
		density = ctx.getResources().getDisplayMetrics().density;
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		ctx.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		screenW = mDisplayMetrics.widthPixels;
		screenH = mDisplayMetrics.heightPixels;
		ZZ.z("Display Density : " + density);
	}

	public static float density() {
		return density;
	}

	public static int dip2px(float dpValue) {
		return (int) (dpValue * density + 0.5f);
	}

	public static int px2dip(float pxValue) {
		return (int) (pxValue / density + 0.5f);
	}

	public static int getStateBarHeight() {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = dip2px(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return sbar;
	}

}
