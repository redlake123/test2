package com.csii.keyboarddemo;

import com.csii.keyboarddemo.util.KeyboardUtil;
import com.csii.keyboarddemo.util.KeyboardUtil.KeyboardShowListener;
import com.csii.keyboarddemo.view.KeyboardViewWithHeader;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;

public class MainActivity extends Activity {

	 private Context ctx;  
     private Activity act;  
     private EditText edit;  
     private EditText edit1;
	private View header;
	private EditText et_inner;
	private View dialog_content;
	private Dialog dialog;  

     @Override  
     public void onCreate(Bundle savedInstanceState) {  
             super.onCreate(savedInstanceState);  
             setContentView(R.layout.activity_main);  
             header = View.inflate(this, R.layout.keyboard_head, null);
             dialog_content = View.inflate(this, R.layout.keyboard_dialog, null);
             et_inner = (EditText) header.findViewById(R.id.et_inner);
             ctx = this;  
             act = this;  

             edit = (EditText) this.findViewById(R.id.edit);  
             edit.setInputType(InputType.TYPE_NULL);  
             //输入框获取焦点，不弹出系统键盘
             et_inner.setInputType(InputType.TYPE_NULL);  

             edit1 = (EditText) this.findViewById(R.id.edit1);  

             edit.setOnTouchListener(new OnTouchListener() {  
                     @Override  
                     public boolean onTouch(View v, MotionEvent event) {  
                             KeyboardUtil util = new KeyboardUtil(act, ctx, edit);
//                             util.addHeadView(header);
                             util.showKeyboard();  
                             return false;  
                     }  
             });  

             edit1.setOnTouchListener(new OnTouchListener() {  
                     @Override  
                     public boolean onTouch(View v, MotionEvent event) {  
                             int inputback = edit1.getInputType();  
                             edit1.setInputType(InputType.TYPE_NULL);  
                             KeyboardUtil util = new KeyboardUtil(act, ctx, edit1);
//                             util.addHeadView(header);
                             util.showKeyboard();  
                             edit1.setInputType(inputback);  
                             return false;  
                     }  
             });  
             et_inner.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					//输入框内容由自定义键盘托管
					KeyboardUtil util = new KeyboardUtil(act, ctx, et_inner);
					return false;
				}
			});
            initDialog();

     }

	private void initDialog() {
		KeyboardViewWithHeader keyboardView = (KeyboardViewWithHeader) dialog_content.findViewById(R.id.keyboard_view);
		
		KeyboardUtil util = new KeyboardUtil(dialog_content, ctx, et_inner);
		util.addHeadView(header);
		dialog = new Dialog(this,R.style.KeyboardDialog);
		dialog.setCanceledOnTouchOutside(false);
		dialog.getWindow().setGravity(Gravity.BOTTOM);
		Display display = getWindowManager().getDefaultDisplay();
		LayoutParams lp = dialog.getWindow().getAttributes();
		lp.width = display.getWidth();
		lp.height = LayoutParams.WRAP_CONTENT;
		dialog.getWindow().setAttributes(lp);
		dialog.getWindow().getDecorView().requestLayout();
		dialog.setContentView(dialog_content);
		util.setOnKeyboardShowListener(new KeyboardShowListener() {
			
			@Override
			public void onKeyboardShow(boolean isShow) {
				if(!isShow){
					dialog.dismiss();
				}
				
			}
		});
		
	}
	public void showDialog(View v){
		dialog.show();
	}

}
