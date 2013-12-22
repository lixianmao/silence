package listener_adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class LyricView extends TextView {

	private float height;
	private float width;
	private Paint currentPaint; // 画当前高亮部分歌词
	private Paint notCurrentPaint; // 非当前高亮部分歌词的paint
	private float TextHigh = 25;
	private float TextSize = 15;
	private int Index = 0;

	private List<LyricContent> mSentenceEntities = new ArrayList<LyricContent>();

	public void setSentenceEntities(List<LyricContent> mSentenceEntities) {
		this.mSentenceEntities = mSentenceEntities;
	}

	public LyricView(Context context) {
		super(context);
		init();
		// TODO Auto-generated constructor stub
	}

	public LyricView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		// TODO Auto-generated constructor stub
	}

	public LyricView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		// TODO Auto-generated constructor stub
	}

	private void init() {
		setFocusable(true);

		currentPaint = new Paint();
		currentPaint.setAntiAlias(true);
		currentPaint.setTextAlign(Paint.Align.CENTER);

		notCurrentPaint = new Paint();
		notCurrentPaint.setAntiAlias(true);
		notCurrentPaint.setTextAlign(Paint.Align.CENTER);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		if (canvas == null) {
			return;
		}

		currentPaint.setColor(Color.WHITE);
		notCurrentPaint.setColor(Color.GREEN);

		currentPaint.setTextSize(TextSize);
		currentPaint.setTypeface(Typeface.SERIF);

		notCurrentPaint.setTextSize(TextSize);
		notCurrentPaint.setTypeface(Typeface.SERIF);

		try {
			canvas.drawText(mSentenceEntities.get(Index).getLyric(), width / 2,
					height / 2, currentPaint);
			float tempY = height / 2;
			
			//当前歌词以上部分的绘制
			for (int i = Index - 1; i >= 0; i--) {
				tempY = tempY - TextHigh;
				canvas.drawText(mSentenceEntities.get(i).getLyric(), width / 2,
						tempY, notCurrentPaint);
			}
			tempY = height / 2;
			
			//当前歌词以下部分的绘制
			for (int i = Index + 1; i < mSentenceEntities.size(); i++) {

				tempY = tempY + TextHigh;
				canvas.drawText(mSentenceEntities.get(i).getLyric(), width / 2,
						tempY, notCurrentPaint);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		this.height = h;
		this.width = w;
	}

	public void SetIndex(int index) {
		this.Index = index;
		// System.out.println(index);
	}
}
