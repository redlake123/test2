package com.csii.keyboarddemo.util;

import java.util.List;

import com.csii.keyboarddemo.R;
import com.csii.keyboarddemo.view.KeyboardViewWithHeader;
import com.csii.keyboarddemo.view.MyKeyboardView;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

public class KeyboardUtil {
	private Context ctx;
	private Activity act;
	private KeyboardViewWithHeader keyboardViewWithHeader;
	private Keyboard k1;// 字母键盘
	private Keyboard k2;// 数字键盘
	public boolean isnun = false;// 是否数据键盘
	public boolean isupper = false;// 是否大写
	public boolean openItselft = true;// 完成键接口回调还是自关闭

	private EditText ed;

	public KeyboardUtil(Activity act, Context ctx, EditText edit) {
		openItselft = true;
		this.act = act;
		this.ctx = ctx;
		this.ed = edit;
		k1 = new Keyboard(ctx, R.xml.qwerty);
		k2 = new Keyboard(ctx, R.xml.symbols);
		keyboardViewWithHeader = (KeyboardViewWithHeader) act.findViewById(R.id.keyboard_view);
		initKeyboardView();
	}
	public KeyboardUtil(View view, Context ctx, EditText edit) {
		openItselft = false;
		this.ctx = ctx;
		this.ed = edit;
		k1 = new Keyboard(ctx, R.xml.qwerty);
		k2 = new Keyboard(ctx, R.xml.symbols);
		keyboardViewWithHeader = (KeyboardViewWithHeader) view.findViewById(R.id.keyboard_view);
		initKeyboardView();
	}
	private void initKeyboardView() {
		keyboardView = keyboardViewWithHeader.getMyKeyboardView();
		Keyboard curKb = keyboardView.getKeyboard();
		if(curKb==null){
			
			keyboardView.setKeyboard(k1);
		}else{
			keyboardView.setKeyboard(curKb);
			
		}
		keyboardView.setEnabled(true);
		keyboardView.setPreviewEnabled(false);
		keyboardView.setOnKeyboardActionListener(listener);
	}
	public void addHeadView(View v){
		keyboardViewWithHeader.setmHeader(v);
	}

	private OnKeyboardActionListener listener = new OnKeyboardActionListener() {
		@Override
		public void swipeUp() {
		}

		@Override
		public void swipeRight() {
		}

		@Override
		public void swipeLeft() {
		}

		@Override
		public void swipeDown() {
		}

		@Override
		public void onText(CharSequence text) {
		}

		@Override
		public void onRelease(int primaryCode) {
		}

		@Override
		public void onPress(int primaryCode) {
		}

		@Override
		public void onKey(int primaryCode, int[] keyCodes) {
			Editable editable = ed.getText();
			int start = ed.getSelectionStart();
			if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 完成
				if(openItselft){
					
					hideKeyboard();
				}else{
					l.onKeyboardShow(false);
				}
			} else if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
				if (editable != null && editable.length() > 0) {
					if (start > 0) {
						editable.delete(start - 1, start);
					}
				}
			} else if (primaryCode == Keyboard.KEYCODE_SHIFT) {// 大小写切换
				changeKey();
				keyboardView.setKeyboard(k1);

			} else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE) {// 数字键盘切换
				if (isnun) {
					isnun = false;
					keyboardView.setKeyboard(k1);
				} else {
					isnun = true;
					keyboardView.setKeyboard(k2);
				}
			} else if (primaryCode == 57419) { // go left
				if (start > 0) {
					ed.setSelection(start - 1);
				}
			} else if (primaryCode == 57421) { // go right
				if (start < ed.length()) {
					ed.setSelection(start + 1);
				}
			} else {
				editable.insert(start, Character.toString((char) primaryCode));
			}
		}
	};
	private MyKeyboardView keyboardView;

	/**
	 * 键盘大小写切换
	 */
	private void changeKey() {
		List<Key> keylist = k1.getKeys();
		if (isupper) {// 大写切换小写
			isupper = false;
			for (Key key : keylist) {
				if (key.label != null && isword(key.label.toString())) {
					key.label = key.label.toString().toLowerCase();
					key.codes[0] = key.codes[0] + 32;
				}
			}
		} else {// 小写切换大写
			isupper = true;
			for (Key key : keylist) {
				if (key.label != null && isword(key.label.toString())) {
					key.label = key.label.toString().toUpperCase();
					key.codes[0] = key.codes[0] - 32;
				}
			}
		}
	}

	public void showKeyboard() {
		int visibility = keyboardViewWithHeader.getVisibility();
		if (visibility == View.GONE || visibility == View.INVISIBLE) {
			keyboardViewWithHeader.setVisibility(View.VISIBLE);
		}
	}

	public void hideKeyboard() {
		int visibility = keyboardViewWithHeader.getVisibility();
		if (visibility == View.VISIBLE) {
			keyboardViewWithHeader.setVisibility(View.INVISIBLE);
		}
	}

	private boolean isword(String str) {
		String wordstr = "abcdefghijklmnopqrstuvwxyz";
		if (wordstr.indexOf(str.toLowerCase()) > -1) {
			return true;
		}
		return false;
	}
	private KeyboardShowListener l;
	public interface KeyboardShowListener{
		void onKeyboardShow(boolean isShow);
	}
	public void setOnKeyboardShowListener(KeyboardShowListener l){
		this.l = l;
	}

}
