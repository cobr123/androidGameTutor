package com.edu4java.android.killthemall;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class GameView extends SurfaceView {

	private Bitmap bmp;
	private Bitmap bmpBlood;
	private SurfaceHolder holder;
	private GameLoopThread gameLoopThread;
	private List<Sprite> sprites = new CopyOnWriteArrayList<Sprite>();
	private List<TempSprite> temps = new CopyOnWriteArrayList<TempSprite>();

	public GameView(Context context) {
		super(context);
		holder = getHolder();
		gameLoopThread = new GameLoopThread(this);
		holder.addCallback(new Callback() {

			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
			}

			public void surfaceCreated(SurfaceHolder holder) {
				createSprites(10);
				gameLoopThread.setRunning(true);
				gameLoopThread.start();
			}

			public void surfaceDestroyed(SurfaceHolder holder) {
			}
		});
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.good4);
		bmpBlood = BitmapFactory.decodeResource(getResources(), R.drawable.blood1);
	}

	private Sprite createSprite(int resouce) {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
		return new Sprite(this, bmp);
	}

	private void createSprites(int max) {
		for (int i = 0; i < max; ++i) {
			sprites.add(createSprite(R.drawable.good4));
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
		for (int i = sprites.size() - 1; i >= 0; i--) {
			Sprite sprite = sprites.get(i);
			if (sprite.isCollision(x, y)) {
				sprites.remove(sprite);
				temps.add(new TempSprite(temps, this, x, y, bmpBlood));
				createSprites(1);
				break;
			}
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		for (int i = temps.size() - 1; i >= 0; i--) {
			temps.get(i).onDraw(canvas);
		}
		for (Sprite sprite : sprites) {
			sprite.onDraw(canvas);
		}
	}
}
