package com.eyt;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

import com.bmob.im.demo.ui.BaseActivity;
import com.eyt.android.ip.Ipclass;
import com.eyt.unlimited.R;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPwd extends BaseActivity {
	private Button reset = null;
	private EditText username = null, newpwd = null, forget_confpwd = null,
			email = null;
	String getuserName, getPwd, getConfPwd, getEmai;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.forget_pwd);
		initTopBarForLeft("修改密码");
		username = (EditText) super.findViewById(R.id.forget_username);
		newpwd = (EditText) super.findViewById(R.id.forget_new_pwd);
		forget_confpwd = (EditText) super.findViewById(R.id.forget_confpwd);
		email = (EditText) super.findViewById(R.id.forget_email);
		reset = (Button) super.findViewById(R.id.forget_btu_reset);

		reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				getuserName = username.getText().toString();
				getPwd = newpwd.getText().toString();
				getConfPwd = forget_confpwd.getText().toString();
				getEmai = email.getText().toString();
				if ("".equals(getuserName) || getuserName == null) {
					Toast.makeText(ForgetPwd.this, "用户名为空", Toast.LENGTH_LONG).show();

				} else if ("".equals(getEmai) || getEmai == null) {
					Toast.makeText(ForgetPwd.this, "邮箱为空", Toast.LENGTH_LONG).show();
				} else if ("".equals(getPwd) || getPwd == null) {
					Toast.makeText(ForgetPwd.this, "密码为空", Toast.LENGTH_LONG).show();
				} else if ("".equals(getConfPwd) || getConfPwd == null) {
					Toast.makeText(ForgetPwd.this, "确认密码为空", Toast.LENGTH_LONG).show();
				} else {
					FinalHttp fh = new FinalHttp();
					AjaxParams param = new AjaxParams();
					
					try {
						param.put("user",URLEncoder.encode(getuserName, "utf-8"));
						param.put("pwd", URLEncoder.encode(getPwd, "utf-8"));
						param.put("confpwd",URLEncoder.encode(getConfPwd, "utf-8"));
						param.put("email", URLEncoder.encode(getEmai, "utf-8"));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					fh.post("http://" + Ipclass.SERVER+ ":8080/EYT/servlet/UpdatePwd", param,new AjaxCallBack<Object>() {
								@Override
								public void onStart() {
									// TODO Auto-generated method stub
									super.onStart();
									System.out.println("开始请求FinalServlet");
								}

								// 成功返回
								@Override
								public void onSuccess(Object t) {
									// TODO Auto-generated method stub
									super.onSuccess(t);
									if(t.toString().equals("密码修改成功")){
										forgetpwd();
										finish();
									}
									Toast.makeText(ForgetPwd.this,t.toString(), Toast.LENGTH_SHORT).show();
								}

								@SuppressWarnings("unused")
								public void onFailure(Throwable t, int errorNo,
										String strMsg) {
									// TODO Auto-generated method stub
									super.onFailure(t, strMsg);
									Toast.makeText(ForgetPwd.this, strMsg,Toast.LENGTH_SHORT).show();
								}

								@Override
								public void onLoading(long count, long current) {
									// TODO Auto-generated method stub
									super.onLoading(count, current);
									System.out.println("1秒钟被回调一次");
								}
							});
				}

			}
		});

	}
	
	public void forgetpwd(){
		
		BmobUser bmobUser=BmobUser.getCurrentUser(this);
		bmobUser.setPassword(getConfPwd);
		bmobUser.update(this,new UpdateListener() {
		
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
			}
		
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(ForgetPwd.this, "修改密码失败", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
