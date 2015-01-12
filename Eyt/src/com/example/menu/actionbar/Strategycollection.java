package com.example.menu.actionbar;

import java.util.ArrayList;

import com.anfroidclass.UserAndStrategy_SC;
import com.anfroidui.util.NetNutil;
import com.bmob.im.demo.ui.BaseActivity;

import com.eyt.android.ip.Ipclass;
import com.eyt.unlimited.R;
import com.listview.layout.MyCollectStrategyAll;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Strategycollection extends BaseActivity {
	ListView lv = null;
	FinalBitmap fb;
	NewAAdaterforCollect adater = new NewAAdaterforCollect();
	Context context;
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		pd = ProgressDialog.show(Strategycollection.this, "", "�����У����Ժ󡭡�",true);
		setContentView(R.layout.searchfragment);
		initTopBarForLeft("�����ղ�");
		fb = FinalBitmap.create(Strategycollection.this);
		lv=(ListView) findViewById(R.id.lv_collect_strat);
		NetNutil.getMyCollectStrate(lv, adater, Strategycollection.this,pd);
		
		
	}
	public class NewAAdaterforCollect extends BaseAdapter {// �Զ���adater

		ArrayList<UserAndStrategy_SC> date;
		public ArrayList<UserAndStrategy_SC> getDate() {
			return date;
		}

		public void setDate(ArrayList<UserAndStrategy_SC> date) {
			this.date = date;
		}

		public NewAAdaterforCollect() {
			super();
			// TODO Auto-generated constructor stub
		}

		public NewAAdaterforCollect(ArrayList<UserAndStrategy_SC> date) {
			this.date = date;
		}

		@Override
		// listview ����Ŀ�ж���
		public int getCount() {
			// TODO Auto-generated method stub
			return date.size();
		}

		@Override
		// positionָ������Ŀ���������ݶ���
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return date.get(position);
		}

		@Override
		// ��Ŀ��id
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		// converView�������Ŀ position��ʾ��Ŀ��λ�� parent��ʾlistview ����ֵ��ʾlistview��һ����Ŀ
		@Override
		public View getView(final int position, View converView,
				ViewGroup parent) {

			final UserAndStrategy_SC news = date.get(position);
			if (converView == null) {

				//converView = Strategycollection.this.inflate(R.layout.collect_stra_listview_layout, null);// ���Լ������xml�����ļ�ת��view
				converView = LayoutInflater.from(Strategycollection.this).inflate(
						R.layout.collect_stra_listview_layout, null);	// ����Ĳ�����ʾҪ��Ҫ������������xml�ϣ�null��ʾ��Ҫ
			
				TextView mycollect_title = (TextView) converView.findViewById(R.id.mycollect_title);
				TextView mycollect_stra_title = (TextView) converView.findViewById(R.id.mycollect_stra_title);
				TextView collect_id= (TextView) converView.findViewById(R.id.collect_id);
				ImageView iv = (ImageView) converView.findViewById(R.id.collect_imageView_querystra);
				ImageView delete_my_collect_image= (ImageView) converView.findViewById(R.id.delete_my_collect_image);
				mycollect_stra_title.setText(news.getStra().getStrategy_context());
				collect_id.setText(String.valueOf(news.getSc().getStrategy_collect_id()));
			
				mycollect_title.setText(news.getConverDateToString());
				String url = news.getStra().getImage_url();
				if(url.equals(" ")||url.equals(null)){
					
			        iv.setVisibility(View.GONE);
				}
				fb.display(iv, "http://" + Ipclass.SERVER + ":8080/EYT/image/"
						+ url);
				
				converView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						Intent in=new Intent(Strategycollection.this,MyCollectStrategyAll.class);// TODO Auto-generated method stub
						in.putExtra("strat_title", news.getStra().getStrategy_context());
						Log.i("strat_title", news.getStra().getStrategy_context());
						startActivity(in);
						
					}
				});
	            delete_my_collect_image.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						FinalHttp fh = new FinalHttp();
						AjaxParams param = new AjaxParams();
						param.put("collect_id", String.valueOf(news.getSc().getStrategy_collect_id()));
						Log.i("collect_id", String.valueOf(news.getSc().getStrategy_collect_id()));
						fh.post("http://" + Ipclass.SERVER+ ":8080/EYT/servlet/Android_deletCollectStrategy", param,new AjaxCallBack<Object>() {
							public void onSuccess(Object t) {
								super.onSuccess(t);
								Toast.makeText(Strategycollection.this, t.toString(), Toast.LENGTH_SHORT).show();
								Intent in=new Intent(Strategycollection.this,Strategycollection.class);
							    startActivity(in);
							    finish();
								
								
							};
							
						});
						
					}
				});
			}

			
			return converView;
		}
	}

}
