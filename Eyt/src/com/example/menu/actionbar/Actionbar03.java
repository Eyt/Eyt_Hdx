package com.example.menu.actionbar;

import com.bmob.im.demo.ui.BaseActivity;
import com.eyt.unlimited.R;

import android.os.Bundle;
import android.view.Window;

public class Actionbar03 extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.actionbar03);
		
		initTopBarForLeft("关于我们");
	}

}
