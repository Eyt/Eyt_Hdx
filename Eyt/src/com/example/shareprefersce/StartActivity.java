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
		// ��ȡSharedPreferences����Ҫ������
		preferences = getSharedPreferences("count", MODE_WORLD_READABLE);
		int count = preferences.getInt("count", 0);
		// �жϳ�����ڼ������У�����ǵ�һ����������ת������ҳ��
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
		// ��������
		editor.putInt("count", ++count);
		// �ύ�޸�
		editor.commit();
	}
}
