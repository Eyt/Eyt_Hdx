package com.listview.layout;

import java.io.File;
import java.io.FileNotFoundException;

import com.anfroidui.util.NetNutil;
import com.bmob.im.demo.ui.BaseActivity;
import com.eyt.android.ip.Ipclass;
import com.eyt.unlimited.R;

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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EditeMyTell extends BaseActivity {
	FinalBitmap fb;

//	private TextView tv;
	private EditText content;
	private File file;
	private Uri imageUri;
	private String imgurl = "";

	private ImageView iv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lv_editmytell);
		initTopBarForLeft("发表约游");
		fb = FinalBitmap.create(this);
		Button submit = (Button) super.findViewById(R.id.button_submit);
		content = (EditText) super.findViewById(R.id.editText_write_tell);
		Button addimage = (Button) super.findViewById(R.id.addimage);
		iv = (ImageView) super.findViewById(R.id.image);
		file = new File(Environment.getExternalStorageDirectory(), "temp_"
				+ System.currentTimeMillis() + ".jpg");
		System.out.println(file);
		imageUri = Uri.fromFile(file);
		fb = FinalBitmap.create(this);
		addimage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_PICK);
				intent.setType("image/*");
				intent.putExtra("crop", "true");
				intent.putExtra("aspectX", 1);
				intent.putExtra("aspectY", 1);
				intent.putExtra("outputX", 300);
				intent.putExtra("outputY", 300);
				// 图片是否缩放
				intent.putExtra("scale", true);
				// 把图片存到imageUri
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				intent.putExtra("return-data", false);
				// 输出格式
				intent.putExtra("outputFormat",Bitmap.CompressFormat.JPEG.toString());
				intent.putExtra("noFaceDetection", true); // no face detection
				startActivityForResult(intent, 1);

			}
		});
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (content.getText().toString().equals("")
						|| content.getText().toString().equals(null)) {
					Toast.makeText(EditeMyTell.this, "约游内容不能为空",Toast.LENGTH_LONG).show();
				} else {
					if (imgurl.equals("") || imgurl.equals(null)) {
						NetNutil.addMyTellAbout(EditeMyTell.this, content.getText().toString(), " ");
					} else {
						NetNutil.addMyTellAbout(EditeMyTell.this, content.getText().toString(), imgurl);
					}
					finish();

				}

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			Log.i("MainActivity", "select error");
			return;
		}
		if (requestCode == 1) {
			if (imageUri != null) {

				FinalHttp fh = new FinalHttp();

				AjaxParams params = new AjaxParams();
				try {
					params.put("file", file);
					fh.post("http://" + Ipclass.SERVER
							+ ":8080/EYT/servlet/Android_addMyTellWithImg",
							params, new AjaxCallBack<Object>() {
								public void onSuccess(Object t) {
									// TODO Auto-generated method stub
									super.onSuccess(t);
									imgurl = t.toString();
									fb.display(iv, "http://" + Ipclass.SERVER+ ":8080/EYT/image/" + t.toString());
									Toast.makeText(
											EditeMyTell.this,
											t.toString() == null ? "null" : t
													.toString() + "上传成功",
											Toast.LENGTH_SHORT).show();
								}
							});
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
