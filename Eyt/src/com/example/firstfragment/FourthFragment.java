package com.example.firstfragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import com.anfroidui.util.NetNutil;
import com.eyt.android.ip.Ipclass;
import com.eyt.search.KeywordsFlow;
import com.eyt.unlimited.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.listview.layout.And_UserSearch_Attr;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class FourthFragment extends Fragment implements OnClickListener {

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated methsetUserVisibleHintod stub
		if (isVisibleToUser) {
			if (SecondFragment.mShakeListener != null) {
				SecondFragment.mShakeListener.stop();
			}
			super.setUserVisibleHint(isVisibleToUser);
			Bundle bundle = this.getArguments();
			int number = bundle.getInt("number");
			SecondFragment.numrecord = number;
			System.out.println("number:" + number);
			System.out.println("执行第4个tUserVisibleHint");
		}

	}

	@Override
	public void setMenuVisibility(boolean menuVisible) {
		// TODO Auto-generated method stub

		super.setMenuVisibility(menuVisible);
		if (menuVisible) {
			Bundle bundle = this.getArguments();
			int number = bundle.getInt("number");
			System.out.println("number:" + number);
			System.out.println("执行 第4个setMenuVisibility");
		}
	}

	private KeywordsFlow keywordsFlow;
	private Button bt_search;
	private AutoCompleteTextView actv_search;
	@SuppressWarnings("unused")
	private static Object obj = new Object();
	public static ArrayList<String> keywords = new ArrayList<String>();
	private Handler hanlder = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(android.os.Message msg) {
			// if (msg.what == 1) {
			keywords = (ArrayList<String>) msg.obj;
			keywordsFlow.rubKeywords();
			feedKeywordsFlow(keywordsFlow, keywords);
			keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
		};
	};
//	private Button serch;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fourthfragment, null);
		bt_search = (Button) view.findViewById(R.id.bt_search);
		actv_search = (AutoCompleteTextView) view.findViewById(R.id.actv_search);
		keywordsFlow = (KeywordsFlow) view.findViewById(R.id.frameLayout1);
		keywordsFlow.setDuration(800l);
		keywordsFlow.setOnItemClickListener(this);
		bt_search.setOnClickListener(this);
		ArrayAdapter<String> aa = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_dropdown_item_1line, keywords);
		actv_search.setAdapter(aa);

		initView();
		return view;

	}

	private void initView() {
		final String PATH = "http://" + Ipclass.SERVER
				+ ":8080/EYT/servlet/Android_SearchAttra?";
		// TODO Auto-generated method stub
		keywords.add(".");
		// 添加
		feedKeywordsFlow(keywordsFlow, keywords);
		keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				URL url;
				try {
					url = new URL(PATH);
					HttpURLConnection htpcon = (HttpURLConnection) url
							.openConnection();
					htpcon.setRequestMethod("GET");
					htpcon.setConnectTimeout(5000);
					if (htpcon.getResponseCode() == 200) {
						InputStream is = htpcon.getInputStream();
						byte[] back = NetNutil.converInputStreamByArray(is);
						String json = new String(back);
						Gson g = new Gson();
						keywords = g.fromJson(json,new TypeToken<ArrayList<String>>() {
								}.getType());
						while (true) {
							Thread.sleep(5000);
							Message mag = hanlder.obtainMessage();
							mag.obj = keywords;
							hanlder.sendMessage(mag);
							// hanlder.sendEmptyMessage(1);

						}
					}
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
		bt_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (actv_search.getText().toString().equals("")) {
					Toast.makeText(getActivity(), "请输入您要搜索的信息",Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent(getActivity(),And_UserSearch_Attr.class);
					intent.putExtra("attr", actv_search.getText().toString());
					startActivity(intent);
				}
			}
		});

	}

	@Override
	public void onClick(View v1) {
		// TODO Auto-generated method stub
		if (v1 instanceof TextView) {
			String keyword = ((TextView) v1).getText().toString();
			actv_search.setText(keyword);

		}
	}

	private static void feedKeywordsFlow(KeywordsFlow keywordsFlow,
			ArrayList<String> keywords2) {
		Random random = new Random();
		for (int i = 0; i < KeywordsFlow.MAX; i++) {
			int ran = random.nextInt(keywords2.size());
			String tmp = keywords2.get(ran);
			keywordsFlow.feedKeyword(tmp);
		}
	}

}
