package com.listview.layout;

import java.io.File;
import java.io.FileNotFoundException;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.anfroidclass.Strategy;
import com.anfroidui.util.NetNutil;
import com.bmob.im.demo.ui.BaseActivity;
import com.eyt.android.ip.Ipclass;
import com.eyt.unlimited.R;

public class AddStrategy extends BaseActivity {
	FinalBitmap fb;
	private File file;
	private Uri imageUri;
	private String imgurl = "";
	private ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weitr_strategy);
		initTopBarForLeft("上传攻略");
		file = new File(Environment.getExternalStorageDirectory(), "stra_"+ System.currentTimeMillis() + ".jpg");
		imageUri = Uri.fromFile(file);
		fb = FinalBitmap.create(this);
		Intent in = getIntent();
		final String attr = in.getStringExtra("attr");
		final EditText edit_Stra_title = (EditText) super.findViewById(R.id.edit_Stra_title);
		final EditText edit_stra_content = (EditText) super.findViewById(R.id.edit_stra_content);
		Button uploadStrat = (Button) super.findViewById(R.id.up_strategy);
		Button remove = (Button) super.findViewById(R.id.remove);
		Button stra_addimg = (Button) super.findViewById(R.id.stra_addimg);
		iv = (ImageView) super.findViewById(R.id.stra_img);
		final Strategy stra = new Strategy();

		uploadStrat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (edit_Stra_title.getText().toString().equals("")
						|| edit_Stra_title.getText().toString().equals(null)) {
					Toast.makeText(AddStrategy.this, "攻略标题不能为空",Toast.LENGTH_LONG).show();
				} else if (edit_stra_content.getText().toString().equals("")
						|| edit_stra_content.getText().toString().equals(null)) {
					Toast.makeText(AddStrategy.this, "攻略内容不能为空",Toast.LENGTH_LONG).show();
				} else {
					stra.setStrategy_title(edit_Stra_title.getText().toString().replaceAll(" ", ""));
					stra.setStrategy_context(edit_stra_content.getText().toString());
					if (imgurl.equals("") || imgurl.equals(null)) {
						NetNutil.addStrategy(stra, AddStrategy.this, attr, " ");
					} else {
						NetNutil.addStrategy(stra, AddStrategy.this, attr,
								imgurl);
					}
					finish();
				}
			}
		});
		stra_addimg.setOnClickListener(new OnClickListener() {

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
		remove.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				edit_Stra_title.setText("");
				edit_stra_content.setText("");

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
							+ ":8080/EYT/servlet/Android_addStrateWithImg",
							params, new AjaxCallBack<Object>() {
								public void onSuccess(Object t) {
									// TODO Auto-generated method stub
									super.onSuccess(t);
									imgurl = t.toString();
									fb.display(iv, "http://" + Ipclass.SERVER+ ":8080/EYT/image/" + t.toString());

									Toast.makeText(
											AddStrategy.this,
											t.toString() == null ? "null" : t.toString() + "上传成功",
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
