package com.edu4java.android.killthemall;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class GameView extends SurfaceView {

	private Bitmap bmp;
	private SurfaceHolder holder;
	private GameLoopThread gameLoopThread;
	private Sprite sprite;
	public GameView(Context context) {
		super(context);
		holder = getHolder();
		gameLoopThread = new GameLoopThread(this);
		holder.addCallback(new Callback() {

			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
			}

			public void surfaceCreated(SurfaceHolder holder) {
				gameLoopThread.setRunning(true);
				gameLoopThread.start();
			}

			public void surfaceDestroyed(SurfaceHolder holder) {
			}
		});
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.good4);
		sprite = new Sprite(this, bmp);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		sprite.onDraw(canvas);
	}
}
