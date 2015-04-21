package dev.misono.anim.sprite;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

import dev.misono.anim.AnimSprite;
import dev.misono.util.ZZ;

public abstract class BitmapSprite extends AnimSprite {

	public String path = null;
	protected Bitmap src;
	protected AssetManager ass;

	public BitmapSprite(String path, float w, int delay, int duration, AssetManager ass) {
		super(w, delay, duration);
		this.path = path;
		this.ass = ass;
		try {
			InputStream is = ass.open(path);
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(is, null, opts);
			float bmW = opts.outWidth;
			float bmH = opts.outHeight;
			this.h = (int) (bmH / bmW * w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void recycle() {
		if (src != null) {
			if (!src.isRecycled()) {
				ZZ.z("path : " + path);
				src.recycle();
			}
		}
		src = null;
	}

	public void init() {
		try {
			InputStream is = ass.open(path);
			ZZ.z("path : " + path);
			src = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
