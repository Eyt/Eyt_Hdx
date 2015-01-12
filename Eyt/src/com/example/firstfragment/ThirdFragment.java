package com.example.firstfragment;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;

import com.anfroidclass.MyTellAbout;
import com.anfroidui.util.NetNutil;
import com.bmob.im.demo.ui.AddFriendActivity;
import com.eyt.android.ip.Ipclass;
import com.eyt.unlimited.R;
import com.listview.layout.EditeMyTell;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ThirdFragment extends Fragment {

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated methsetUserVisibleHintod stub
		if (isVisibleToUser) {
			super.setUserVisibleHint(isVisibleToUser);
			Bundle bundle = this.getArguments();
			int number = bundle.getInt("number");
			System.out.println("number:" + number);
			System.out.println("执行第se 2个tUserVisibleHint");
		}
	}

	@Override
	public void setMenuVisibility(boolean menuVisible) {
		// TODO Auto-generated method stub

		super.setMenuVisibility(menuVisible);
		if (menuVisible) {
			if (SecondFragment.mShakeListener != null) {
				SecondFragment.mShakeListener.stop();
			}
			Bundle bundle = this.getArguments();
			int number = bundle.getInt("number");
			SecondFragment.numrecord = number;
			System.out.println("number:" + number);
			System.out.println("执行 第2个setMenuVisibility");
		}
	}

	LayoutInflater context;
	ListView lv = null;
	FinalBitmap fb;
	NewAdaterForTell adater = new NewAdaterForTell();
	private Button tv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		fb = FinalBitmap.create(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		context = getActivity().getLayoutInflater();// 获取fragment的上下文
		View view = inflater.inflate(R.layout.secondfragment, null);
		lv = (ListView) view.findViewById(R.id.lv_tell_about);
		NetNutil.getMyTellAboutFromDb(lv, adater);
		tv = (Button) view.findViewById(R.id.commit_tell_about);
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// edit_tell_about.setVisibility(View.VISIBLE);
				Intent in = new Intent(getActivity(), EditeMyTell.class);
				startActivity(in);

			}
		});

		return view;
	}

	public class NewAdaterForTell extends BaseAdapter {// 自定义adater

		ArrayList<MyTellAbout> date;

		public ArrayList<MyTellAbout> getDate() {
			return date;
		}

		public void setDate(ArrayList<MyTellAbout> date) {
			this.date = date;
		}

		public NewAdaterForTell() {
			super();
			// TODO Auto-generated constructor stub
		}

		public NewAdaterForTell(ArrayList<MyTellAbout> date) {
			this.date = date;
		}

		@Override
		// listview 的条目有多少
		public int getCount() {
			// TODO Auto-generated method stub
			return date.size();
		}

		@Override
		// position指定的条目关联的数据对象
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return date.get(position);
		}

		@Override
		// 条目的id
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View converView,
				ViewGroup parent) {

			final MyTellAbout news = date.get(position);
			if (converView == null) {
				converView = context.inflate(R.layout.tell_about_layout, null);// 把自己定义的xml布局文件转成view
			}
			ImageView iv = (ImageView) converView.findViewById(R.id.display_image);
			ImageView hiv = (ImageView) converView.findViewById(R.id.himage);
			final TextView my_tell_content = (TextView) converView.findViewById(R.id.display_tell_about);
			final TextView my_tell_username = (TextView) converView.findViewById(R.id.tv_my_tell_username);
			final TextView tv_tell_time = (TextView) converView.findViewById(R.id.tv_tell_time);
			my_tell_content.setText("\t\t" + news.getTell_context());
			my_tell_username.setText(news.getUsername());
			tv_tell_time.setText(news.getDateToString());
			String url = news.getMy_tell_about_image_url();
			String headUrl = news.getImageUrl();
			if (url.equals(" ") || url.equals(null)) {
				iv.setVisibility(View.GONE);
			}else{
				iv.setVisibility(View.VISIBLE);
			}
			fb.display(iv, "http://" + Ipclass.SERVER + ":8080/EYT/image/"+ url);
			fb.display(hiv, "http://" + Ipclass.SERVER + ":8080/EYT/image/"+ headUrl);
			converView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent in = new Intent();
					in.setClass(getActivity(), AddFriendActivity.class);
					in.putExtra("username", news.getUsername());
					getActivity().startActivity(in);

				}
			});
			return converView;
		}
	}
}
