package com.csii.keyboarddemo.view;

import java.lang.reflect.Field;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

public class MyKeyboardView extends KeyboardView {

	public MyKeyboardView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public void setKeyBackground(Drawable d){
		try {
			Field field = KeyboardView.class.getDeclaredField("mKeyBackground");
			field.setAccessible(true);
			field.set(this, d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void setKeyTextColor(int color){
		try {
			Field field = KeyboardView.class.getDeclaredField("mKeyTextColor");
			field.setAccessible(true);
			field.set(this, color);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
