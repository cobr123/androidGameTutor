package com.edu4java.android.killthemall;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {
	private int x = 0;
	private int y = 0;
	private int xSpeed = 5;
	private int ySpeed = 5;
	private GameView gameView;
	private Bitmap bmp;
	private int width;
	private int height;
	private int currentFrame;
	private static final int BMP_ROWS = 4;
	private static final int BMP_COLUMNS = 3;

	public Sprite(GameView gameView, Bitmap bmp) {
		this.gameView = gameView;
		this.bmp = bmp;
		this.width = bmp.getWidth() / BMP_COLUMNS;
		this.height = bmp.getHeight() / BMP_ROWS;
	}

	private void update() {
		if (x > gameView.getWidth() - width - xSpeed || x + xSpeed < 0) {
			xSpeed = -xSpeed;
		}
		x = x + xSpeed;
		if (y > gameView.getHeight() - height - ySpeed || y + ySpeed < 0) {
			ySpeed = -ySpeed;
		}
		y = y + ySpeed;
		currentFrame = ++currentFrame % BMP_COLUMNS;
	}

	public void onDraw(Canvas canvas) {
		update();
		int srcX = currentFrame * width;
		int srcY = getAnimationRow() * height;
		Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
		Rect dst = new Rect(x, y, x + width, y + height);
		canvas.drawBitmap(bmp, src, dst, null);
	}

	// return 1, 2, 3 or 4
	private int getAnimationRow() {
		if (xSpeed > 0 && ySpeed > 0) {
			return 2;
		}
		else if (xSpeed > 0 && ySpeed < 0) {
			return 3;
		}
		else if (xSpeed < 0 && ySpeed > 0) {
			return 0;
		}
		else if (xSpeed < 0 && ySpeed < 0) {
			return 1;
		}

		return 0;
	}
}