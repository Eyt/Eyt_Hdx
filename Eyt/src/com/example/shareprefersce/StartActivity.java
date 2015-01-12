package com.example.shareprefersce;

import com.bmob.im.demo.ui.SplashActivity;
import com.eyt.unlimited.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Window;

public class StartActivity extends Activity {
	SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_start);
		// 读取SharedPreferences中需要的数据
		preferences = getSharedPreferences("count", MODE_WORLD_READABLE);
		int count = preferences.getInt("count", 0);
		// 判断程序与第几次运行，如果是第一次运行则跳转到引导页面
		if (count == 0) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), GuideActivity.class);
			startActivity(intent);
			finish();
		}else{
			startActivity(new Intent(this, SplashActivity.class));
			finish();
		}

		Editor editor = preferences.edit();
		// 存入数据
		editor.putInt("count", ++count);
		// 提交修改
		editor.commit();
	}
}
