package com.csii.keyboarddemo.view;


import com.csii.keyboarddemo.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class KeyboardViewWithHeader extends LinearLayout {

	private Context mContext;
	private FrameLayout mHeader;
	public View getmHeader() {
			
		return mHeader.getChildAt(0);
	}


	public void setmHeader(View header) {
		mHeader.removeAllViews();
		mHeader.addView(header);
	}


	public MyKeyboardView getMyKeyboardView() {
		return mKeyboardView;
	}


	private MyKeyboardView mKeyboardView;
	private Drawable keyBackground;
	private int keyTextColor;

	public KeyboardViewWithHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		parseAttributes(context.obtainStyledAttributes(attrs, R.styleable.KeyboardViewWithHeader));
		this.mContext = context;
		init();
	}


	private void parseAttributes(TypedArray a) {
		keyBackground = a.getDrawable(R.styleable.KeyboardViewWithHeader_keyBackground);
		keyTextColor = a.getColor(R.styleable.KeyboardViewWithHeader_keyTextColor, Color.WHITE);
		a.recycle();
		
	}


	private void init() {
		mKeyboardView = new MyKeyboardView(mContext, null);
		mKeyboardView.setKeyBackground(keyBackground);
		mKeyboardView.setKeyTextColor(keyTextColor);
		mHeader = new FrameLayout(mContext);
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		
		mHeader.setLayoutParams(lp);
		
		
		this.setOrientation(LinearLayout.VERTICAL);
		this.setGravity(Gravity.BOTTOM);
		
		this.addView(mKeyboardView);
		this.addView(mKeyboardView);
		mKeyboardView.addOnLayoutChangeListener(new OnLayoutChangeListener() {
			
			@Override
			public void onLayoutChange(View v, int left, int top, int right,
					int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
				
				KeyboardViewWithHeader.this.requestLayout();
			}
		});
	}
	

}
