package com.bmob.im.demo.ui;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Pattern;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.listener.SaveListener;


import com.bmob.im.demo.bean.User;
import com.bmob.im.demo.util.CommonUtils;
import com.eyt.android.ip.Ipclass;
import com.eyt.unlimited.R;

public class RegisterActivity extends BaseActivity {

	Button btn_register;
	EditText et_username, et_password, pwd_again,et_email;
	BmobChatUser currentUser;
	
	String userName,userPassword,userEmail,userConfPwd;
	String name,password,email,pwd_agains;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);

		initTopBarForLeft("注册");

		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		et_email = (EditText) findViewById(R.id.et_email);
		pwd_again = (EditText) findViewById(R.id.pwd_again);

		btn_register = (Button) findViewById(R.id.btn_register);
		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				register();
			}
		});
	}
//	@SuppressWarnings("unused")
	private void register(){
		String name = et_username.getText().toString();
		String password = et_password.getText().toString();
		String pwd_agains = pwd_again.getText().toString();
		String email =  et_email.getText().toString();
		String regex = "([a-z]|[A-Z]|[0-9]|[\\u4e00-\\u9fa5])+";
        Pattern pattern = Pattern.compile(regex);
		if (TextUtils.isEmpty(name)) {
			ShowToast(R.string.toast_error_username_null);
			return;
		}

		if (TextUtils.isEmpty(password)) {
			ShowToast(R.string.toast_error_password_null);
			return;
		}
		if (TextUtils.isEmpty(pwd_agains)) {
			ShowToast(R.string.toast_error_password_null);
			return;
		}
		if (TextUtils.isEmpty(email)&&!Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}").matcher(email)
		        .matches()) {
			ShowToast(R.string.toast_error_email_null);
			return;
		}
		if (!Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}").matcher(email).matches()) {
			ShowToast(R.string.toast_error_email_null);
			return;
        }
		if (!pwd_agains.equals(password)) {
			ShowToast(R.string.toast_error_comfirm_password);
			return;
		}
		
		
		boolean isNetConnected = CommonUtils.isNetworkAvailable(this);
		if(!isNetConnected){
			ShowToast(R.string.network_tips);
			return;
		}
		
		final ProgressDialog progress = new ProgressDialog(RegisterActivity.this);
		progress.setMessage("正在注册...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		//由于每个应用的注册所需的资料都不一样，故IM sdk未提供注册方法，用户可按照bmod SDK的注册方式进行注册。
		//注册的时候需要注意两点：1、User表中绑定设备id和type，2、设备表中绑定username字段
		final User bu = new User();
		bu.setUsername(name);
		bu.setPassword(password);
		bu.setEmail(email);
		//将user和设备id进行绑定
		bu.setDeviceType("android");
		bu.setInstallId(BmobInstallation.getInstallationId(this));
		bu.signUp(RegisterActivity.this, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				progress.dismiss();
				ShowToast("注册成功");
				// 注册到自己的数据库
				userName = bu.getUsername();
				userPassword = bu.getPassword();
				userEmail = bu.getEmail();
				register(userName,userPassword,userEmail);
				
				// 将设备与username进行绑定
				userManager.bindInstallationForRegister(bu.getUsername());
				//更新地理位置信息
				updateUserLocation();
				//发广播通知登陆页面退出
//				sendBroadcast(new Intent(BmobConstants.ACTION_REGISTER_SUCCESS_FINISH));
				// 启动
				Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("userName", userName);
				intent.putExtra("userPassword", userPassword);
				startActivity(intent);
				finish();
				
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				BmobLog.i(arg1);
				ShowToast("注册失败");
				progress.dismiss();
			}
		});
	}
//	@SuppressWarnings("unused")
	private void register(String userName1,String userPassword2,String userEmail3){
		userName = et_username.getText().toString();
		userPassword = et_password.getText().toString();
		pwd_agains = pwd_again.getText().toString();
		userEmail =  et_email.getText().toString();
		FinalHttp fh=new FinalHttp();
		AjaxParams param=new AjaxParams();
		try {
			param.put("user", URLEncoder.encode(userName, "utf-8"));
			param.put("pwd", URLEncoder.encode(userPassword, "utf-8"));
			param.put("email", URLEncoder.encode(userEmail, "utf-8"));
			param.put("confpwd", URLEncoder.encode(pwd_agains, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fh.post("http://"+Ipclass.SERVER+":8080/EYT/servlet/RegisterSuccess",param, new AjaxCallBack<Object>(){
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
				Toast.makeText(RegisterActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
//				Intent in=new Intent(Register.this,Login.class);
//				startActivity(in);
			}

//			public void onFailure(Throwable t, int errorNo,
//					String strMsg) {
//				// TODO Auto-generated method stub
//				super.onFailure(t, strMsg);
//				Toast.makeText(RegisterActivity.this, strMsg, Toast.LENGTH_SHORT).show();
////				System.out.println("请求失败"+strMsg);
//				//tv.setText(strMsg );//接收反馈回来的数据
//			}
			@Override
			public void onLoading(long count, long current) {
				// TODO Auto-generated method stub
				super.onLoading(count, current);
				System.out.println("1秒钟被回调一次");
			}
		});
		//Toast.makeText(Register.this, "注册成功", Toast.LENGTH_LONG).show();
		
		
	}
	
}
