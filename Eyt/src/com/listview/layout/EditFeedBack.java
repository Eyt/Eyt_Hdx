package com.listview.layout;

import com.anfroidui.util.NetNutil;
import com.bmob.im.demo.ui.BaseActivity;
import com.eyt.unlimited.R;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditFeedBack extends BaseActivity {
	private EditText editText_feedback_title;
	private EditText edit_feed_content;
	private Button clear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.edit_feedback);
		initTopBarForLeft("反馈意见");
		editText_feedback_title = (EditText) super.findViewById(R.id.editText_feedback_title);
		edit_feed_content = (EditText) super.findViewById(R.id.edit_feed_content);
		clear = (Button) super.findViewById(R.id.clear);
		Button submit_feedback = (Button) super.findViewById(R.id.submit_feedback);
		clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				editText_feedback_title.setText("");
				edit_feed_content.setText("");

			}
		});
		submit_feedback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (editText_feedback_title.getText().toString().equals("")
						|| editText_feedback_title.getText().toString().equals(null)) {
					Toast.makeText(EditFeedBack.this, "标题不能为空",Toast.LENGTH_SHORT).show();
				} else if ((edit_feed_content.getText().toString().equals("") || edit_feed_content
						.getText().toString().equals(null))) {
					Toast.makeText(EditFeedBack.this, "意见内容不能为空",Toast.LENGTH_SHORT).show();
				} else {
					NetNutil.addFeedBack(EditFeedBack.this,
							editText_feedback_title.getText().toString(),
							edit_feed_content.getText().toString());
					finish();
				}
				
			}
		});
	}
}
