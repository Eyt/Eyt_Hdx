package com.bmob.im.demo.ui;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
//import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.listener.SaveListener;

import com.bmob.im.demo.CustomApplcation;
import com.bmob.im.demo.config.BmobConstants;
import com.bmob.im.demo.util.CommonUtils;
//import com.bmob.im.demo.view.dialog.DialogTips;
import com.example.lovewallpaper.MainActivity;
import com.eyt.android.ip.Ipclass;
import com.eyt.unlimited.R;

/**
 * @ClassName: LoginActivity
 * @Description: TODO
 * @author smile
 * @date 2014-12-17 下午4:41:42
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

	EditText et_username, et_password;
	Button btn_login;
	TextView btn_register;
	BmobChatUser currentUser;
	String logoName,logoPassword;
	public CustomApplcation allProject;

	private MyBroadcastReceiver receiver = new MyBroadcastReceiver();
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		allProject=(CustomApplcation) this.getApplication();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		init();
		//注册退出广播
		IntentFilter filter = new IntentFilter();
		filter.addAction(BmobConstants.ACTION_REGISTER_SUCCESS_FINISH);
		registerReceiver(receiver, filter);
		
//		showNotice();
	}

//	public void showNotice() {
//		DialogTips dialog = new DialogTips(this,"提示",getResources().getString(R.string.show_notice), "确定",true,true);
//		// 设置成功事件
//		dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialogInterface, int userId) {
//				
//			}
//		});
//		// 显示确认对话框
//		dialog.show();
//		dialog = null;
//	}
	
	private void init() {
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_register = (TextView) findViewById(R.id.btn_register);
		Intent in=getIntent();
		et_username.setText(in.getStringExtra("userName"));
		et_password.setText(in.getStringExtra("userPassword"));
		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);
	}

	public class MyBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null && BmobConstants.ACTION_REGISTER_SUCCESS_FINISH.equals(intent.getAction())) {
				finish();
			}
		}

	}
	
	@Override
	public void onClick(View v) {
		if (v == btn_register) {
			Intent intent = new Intent(LoginActivity.this,
					RegisterActivity.class);
			startActivity(intent);
		} else {
			boolean isNetConnected = CommonUtils.isNetworkAvailable(this);
			if(!isNetConnected){
				ShowToast(R.string.network_tips);
				return;
			}
			login();
		}
	}
	
	private void login(){
		String name = et_username.getText().toString();
		String password = et_password.getText().toString();

		if (TextUtils.isEmpty(name)) {
			ShowToast(R.string.toast_error_username_null);
			return;
		}

		if (TextUtils.isEmpty(password)) {
			ShowToast(R.string.toast_error_password_null);
			return;
		}

		final ProgressDialog progress = new ProgressDialog(LoginActivity.this);
		progress.setMessage("正在登陆...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		userManager.login(name, password, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				logineyt(logoName,logoPassword);
				
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						progress.setMessage("正在获取好友列表...");
					}
				});
				//更新用户的地理位置以及好友的资料
				updateUserInfos();
				progress.dismiss();
				Intent intent = new Intent(LoginActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}

			@Override
			public void onFailure(int errorcode, String arg0) {
				// TODO Auto-generated method stub
				progress.dismiss();
				BmobLog.i(arg0);
				ShowToast("用户名或密码错误");
			}
		});
		
	}
	
	private void logineyt(String name ,String password){
		logoName = et_username.getText().toString();
		logoPassword = et_password.getText().toString();
		FinalHttp fh=new FinalHttp();
		AjaxParams param=new AjaxParams();
		try {
			param.put("user", URLEncoder.encode(logoName, "utf-8"));	
			param.put("pwd", URLEncoder.encode(logoPassword, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fh.post("http://"+Ipclass.SERVER+":8080/EYT/servlet/LoginSuccess",param, new AjaxCallBack<Object>(){
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				System.out.println("开始请求FinalServlet");
			}
           //成功返回
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				//System.out.println("请求FinalServlet成功"+t);
				//tv.setText(t.toString());//接收反馈回来的数据
		       Log.i("Login", "public void onSuccess(Object t)"+t.toString());
		       
			       if("true".equals(t)){
			    	  // Log.i("Login", "----------"+"登录成功".equals(t.toString()));
//			    	   Intent in=new Intent(LoginActivity.this,MainActivity.class);
			    	   allProject.setUserName(et_username.getText().toString());
			    	   Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//						startActivity(in);
						
			       }
			       else{
//			    	   Intent in=new Intent(LoginActivity.this,LoginActivity.class);
			    	   Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
//						startActivity(in);
			       }
//			       Intent in=new Intent(Login.this,ListViewFromServer.class);
//					startActivity(in);
			    
			}
			@SuppressWarnings("unused")
			public void onFailure(Throwable t, int errorNo,
					String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, strMsg);
				Toast.makeText(LoginActivity.this, strMsg, Toast.LENGTH_SHORT).show();
//				System.out.println("请求失败"+strMsg);
				//tv.setText(strMsg );//接收反馈回来的数据
			}
			@Override
			public void onLoading(long count, long current) {
				// TODO Auto-generated method stub
				super.onLoading(count, current);
				System.out.println("1秒钟被回调一次");
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}
	
}
