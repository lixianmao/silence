package useless;

import java.util.TreeMap;

import org.hustunique.silence.R;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class LyricView2 extends View {

	public static TreeMap<Integer, LyricObject> lrc_map;
	public static boolean blLrc = false;
	private float mX;
	private float offsetY;
	private float touchY; // 当触摸歌词View时，保存为当前触点Y坐标
	private int lrcIndex = 0; // 保存歌词TreeMap的下标
	private int textSize; // 歌词文字大小
	private int gap; // 歌词行间距
	Paint lowPaint = null; // 画笔，画不是高亮部分的歌词
	Paint highPaint = null; // 画笔，画高亮部分的歌词

	public LyricView2(Context context) {
		super(context);
		init();
	}

	public LyricView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {
		lrc_map = new TreeMap<Integer, LyricObject>();
		offsetY = 320; // ?

		lowPaint = new Paint();
		lowPaint.setTextAlign(Paint.Align.CENTER);
		lowPaint.setColor(getResources().getColor(R.color.white));
		lowPaint.setAntiAlias(true); // ?
		lowPaint.setDither(true);
		lowPaint.setAlpha(180);

		highPaint = new Paint();
		highPaint.setTextAlign(Paint.Align.CENTER);
		highPaint.setColor(getResources().getColor(R.color.white));
		highPaint.setAntiAlias(true);
		highPaint.setAlpha(255);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if (blLrc) {
			highPaint.setTextSize(textSize);
			LyricObject temp = lrc_map.get(lrcIndex);
			canvas.drawText(temp.itemLrc, mX, offsetY + (textSize + gap)
					* lrcIndex, highPaint);

			// 画当前歌词之前的歌词
			for (int i = lrcIndex - 1; i >= 0; i--) {
				temp = lrc_map.get(i);
				if (offsetY + (textSize + gap) * i < 0) {
					break;
				}
				canvas.drawText(temp.itemLrc, mX, offsetY + (textSize + gap)
						* i, lowPaint);
			}
			// 画当前歌词之后的歌词
			for (int i = lrcIndex + 1; i < lrc_map.size(); i++) {
				temp = lrc_map.get(i);
				if (offsetY + (textSize + gap) * i > 600) {
					break;
				}
				canvas.drawText(temp.itemLrc, mX, offsetY + (textSize + gap)
						* i, lowPaint);
			}
		} else {
			lowPaint.setTextSize(25);
			canvas.drawText("找不到歌词", mX, 310, lowPaint);
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float tempTime = event.getY();
		if (blLrc) {
			return super.onTouchEvent(event);
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			touchY = tempTime - touchY;
			offsetY = offsetY + touchY;
			break;
		case MotionEvent.ACTION_UP:
			break;
		default:
			break;
		}
		touchY = tempTime;
		return true;
	}

	public void setTextSize() {
		if (blLrc) {
			int max = lrc_map.get(0).itemLrc.length();
			for (int i = 1; i < lrc_map.size(); i++) { // ?begin with 1
				LyricObject lrc = lrc_map.get(i);
				if (max < lrc.itemLrc.length()) {
					max = lrc.itemLrc.length();
				}
			}
			textSize = 320 / max;
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		mX = (float) (w * 0.5);
		super.onSizeChanged(w, h, oldw, oldh);
	}

	/**
	 * 歌词滚动速度
	 * 
	 * @return返回歌词滚动的速度
	 */
	public Float speedLrc() {
		float speed = 0;
		if (offsetY + (textSize + gap) * lrcIndex > 220) {
			speed = (offsetY + (textSize + gap) * lrcIndex - 220) / 20;
		} else if (offsetY + (textSize + gap) * lrcIndex < 120) {
			speed = 0;
		}
		return speed;
	}

	/**
	 * 按当前的歌曲的播放时间，从歌词里面获得那一句
	 * 
	 * @param time
	 *            当前歌曲的播放时间
	 * @return 返回当前歌词的索引值
	 */
	public int selectIndex(int time) {
		if (!blLrc) {
			return 0;
		}
		int index = 0;
		for (int i = 0; i < lrc_map.size(); i++) {
			LyricObject temp = lrc_map.get(i);
			if (temp.beginTime < time) {
				index++;
			}
		}
		lrcIndex = index - 1;
		if (lrcIndex < 0) {
			lrcIndex = 0;
		}
		return lrcIndex;
	}
	
	public static boolean isBlLrc() {
		return blLrc;
	}
	
	public float getOffsetY() {
		return offsetY;
	}
	public void setOffserY(float offsetY) {
		this.offsetY = offsetY;
	}
	public float getTextSize() {
		return textSize;
	}
	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}
		
}
