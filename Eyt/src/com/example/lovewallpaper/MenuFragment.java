package com.example.lovewallpaper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bmob.im.demo.CustomApplcation;
import com.bmob.im.demo.ui.ChatMainActivity;
import com.bmob.im.demo.ui.LoginActivity;
import com.example.menu.actionbar.MySession;
import com.example.menu.actionbar.MyStrategy;
import com.example.menu.actionbar.Strategycollection;
import com.example.menufragment.HomeFragment;
import com.eyt.android.ip.Ipclass;
import com.eyt.mapprint.MapprintMainActivity;
import com.eyt.unlimited.R;

public class MenuFragment extends Fragment implements OnClickListener {

	RelativeLayout menu_search, menu_home, menu_mine, user_log, menu_local,
			menu_tuwen, menu_feeback, menu_about, menu_settings, menu_weather;
	Button btn_logout;
	ImageView iv_set_avator;
	private TextView tv;
	private File file;
	private Uri imageUri;
	private FinalBitmap fb;
	private  Activity context;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		fb = FinalBitmap.create(getActivity());
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 context=getActivity();
		 final CustomApplcation all=(CustomApplcation) context.getApplication();
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.menufragment, container, false);

		user_log = (RelativeLayout) view.findViewById(R.id.uesr_log);
		user_log.setOnClickListener(this);
		menu_search = (RelativeLayout) view.findViewById(R.id.menu_search);
		menu_search.setOnClickListener(this);
		menu_home = (RelativeLayout) view.findViewById(R.id.menu_home);
		menu_home.setOnClickListener(this);
		user_log = (RelativeLayout) view.findViewById(R.id.uesr_log);
		user_log.setOnClickListener(this);
		menu_local = (RelativeLayout) view.findViewById(R.id.menu_local);
		menu_local.setOnClickListener(this);
		menu_tuwen = (RelativeLayout) view.findViewById(R.id.menu_tuwen);
		menu_tuwen.setOnClickListener(this);
		menu_settings = (RelativeLayout) view.findViewById(R.id.menu_settings);
		menu_settings.setOnClickListener(this);
		menu_weather = (RelativeLayout) view.findViewById(R.id.menu_weather);
		menu_weather.setOnClickListener(this);
		btn_logout = (Button) view.findViewById(R.id.btn_logout);
		btn_logout.setOnClickListener(this);
		
		file=new File(Environment.getExternalStorageDirectory(),"temp_"+System.currentTimeMillis()+".jpg");
		
		imageUri=Uri.fromFile(file);
		
		iv_set_avator = (ImageView) view.findViewById(R.id.iv_set_avator);
		
	     tv=(TextView) view.findViewById(R.id.login_id);
	     tv.setText(all.getUserName());
	 	FinalHttp fh=new FinalHttp();
		
		AjaxParams params=new AjaxParams();
		params.put("name",all.getUserName());
		fh.get("http://"+Ipclass.SERVER+":8080/EYT/servlet/Android_UpHeadImageUrl",params,  new AjaxCallBack<Object>(){
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
					fb.display(iv_set_avator, "http://"+Ipclass.SERVER+":8080/EYT/image/"+t.toString());
			}
		});
	     iv_set_avator.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent();
				intent.setAction(Intent.ACTION_PICK);
				intent.setType("image/*");
			    intent.putExtra("crop", "true");
			    intent.putExtra("aspectX", 1);
			    intent.putExtra("aspectY", 1);
			    intent.putExtra("outputX", 300);
			    intent.putExtra("outputY", 300);
			    //图片是否缩放
			    intent.putExtra("scale", true);
			    //把图片存到imageUri
			    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			    intent.putExtra("return-data", false);
			    //输出格式
			    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
			    intent.putExtra("noFaceDetection", true); // no face detection
				startActivityForResult(intent,1);
				
			}
		});
		System.out.println();
		return view;
	}
	
	   @SuppressWarnings("static-access")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	// TODO Auto-generated method stub
	    	super.onActivityResult(requestCode, resultCode, data);
	    	 Activity activity=getActivity();
			 final CustomApplcation all=(CustomApplcation) activity.getApplication();
	    	if(resultCode!=context.RESULT_OK){
				Log.i("MainActivity", "select error" );
				return;
			}
	    	if(requestCode==1){
				if(imageUri!=null){
					final FinalHttp fh=new FinalHttp();
					AjaxParams params=new AjaxParams();
					try {
						params.put("file",file);	
						fh.post("http://"+Ipclass.SERVER+":8080/EYT/servlet/Android_UpHeadImage", params,  new AjaxCallBack<Object>(){
							public void onSuccess(Object t) {
								// TODO Auto-generated method stub
								super.onSuccess(t);
								fb.display(iv_set_avator, "http://"+Ipclass.SERVER+":8080/EYT/image/"+t.toString());
								Toast.makeText(getActivity(),t.toString()==null?"null":t.toString()+all.getUserName()+"上传成功", Toast.LENGTH_SHORT).show();
								AjaxParams param=new AjaxParams();
								try {
									param.put("username",URLEncoder.encode(all.getUserName(),"utf-8"));
									Log.i("username", all.getUserName());
									param.put("hurl",URLEncoder.encode(t.toString(),"utf-8"));
								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								fh.post("http://"+Ipclass.SERVER+":8080/EYT/servlet/Android_updateHeadImageAndUser", param,  new AjaxCallBack<Object>(){
									public void onSuccess(Object t) {
										// TODO Auto-generated method stub
										super.onSuccess(t);
										Toast.makeText(getActivity(),t.toString(), Toast.LENGTH_SHORT).show();
									}
								});
							}
						});
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
	    }
	@Override
	public void onClick(View argView) {
		// TODO Auto-generated method stub
		Fragment newContent = null;

		switch (argView.getId()) {
		case R.id.menu_home:
			newContent = new HomeFragment();
			user_log.setSelected(false);
			menu_search.setSelected(false);
			menu_home.setSelected(true);
			menu_local.setSelected(false);
			menu_tuwen.setSelected(false);
			menu_settings.setSelected(false);
			menu_weather.setSelected(false);
			break;
		case R.id.menu_search:
			Intent xc = new Intent();
			xc.setClass(getActivity(), Strategycollection.class);
			getActivity().startActivity(xc);
			break;
		case R.id.menu_local:
			Intent gl = new Intent();
			gl.setClass(getActivity(), MyStrategy.class);
			getActivity().startActivity(gl);
			break;
		case R.id.menu_tuwen:
			Intent in = new Intent();
			in.setClass(getActivity(), ChatMainActivity.class);
			getActivity().startActivity(in);
			break;
		case R.id.btn_logout:
			CustomApplcation.getInstance().logout();
			getActivity().finish();
			startActivity(new Intent(getActivity(), LoginActivity.class));
			break;
		case R.id.menu_settings:
			Intent hh = new Intent();
			hh.setClass(getActivity(), MySession.class);
			getActivity().startActivity(hh);
			break;
		case R.id.menu_weather:
			Intent dt = new Intent();
			dt.setClass(getActivity(), MapprintMainActivity.class);
			getActivity().startActivity(dt);
			break;
		default:
			break;
		}
		if (newContent != null)
			switchFragment(newContent);
	}

	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		MainActivity ra = (MainActivity) getActivity();
		ra.switchContent(fragment);
	}
}
