package com.listview.layout;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;

import com.anfroidclass.Strategy;
import com.anfroidui.util.NetNutil;
import com.bmob.im.demo.ui.AddFriendActivity;
import com.bmob.im.demo.ui.BaseActivity;
import com.eyt.android.ip.Ipclass;
import com.eyt.unlimited.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class And_UserQueryStrategyAll extends BaseActivity {
	ListView lv = null;
	FinalBitmap fb;

	ArrayList<Strategy> data = new ArrayList<Strategy>();
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		pd = ProgressDialog.show(And_UserQueryStrategyAll.this, "", "�����У����Ժ󡭡�",true);
		setContentView(R.layout.lv_startbytitle);
		Intent in = getIntent();
		lv = (ListView) super.findViewById(R.id.lv_my_stra_all);
		NewAdaterForStrat adater = new NewAdaterForStrat();
		NetNutil.getAllStrat(lv, adater, in.getStringExtra("strat_title"),pd);
		initTopBarForLeft(in.getStringExtra("strat_title"));
		fb = FinalBitmap.create(this);

	}

	public class NewAdaterForStrat extends BaseAdapter {// �Զ���adater

		ArrayList<Strategy> date;

		public ArrayList<Strategy> getDate() {
			return date;
		}

		public void setDate(ArrayList<Strategy> date) {
			this.date = date;
		}

		public NewAdaterForStrat() {
			super();
			// TODO Auto-generated constructor stub
		}

		public NewAdaterForStrat(ArrayList<Strategy> date) {
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

		@Override
		// converView�������Ŀ position��ʾ��Ŀ��λ�� parent��ʾlistview ����ֵ��ʾlistview��һ����Ŀ
		public View getView(final int position, View converView,
				ViewGroup parent) {
			final Strategy news = date.get(position);
			if (converView == null) {
				LayoutInflater inflater = LayoutInflater
						.from(And_UserQueryStrategyAll.this);
				converView = inflater
						.inflate(R.layout.and_querystrat_all, null);// ���Լ������xml�����ļ�ת��view
						// ����Ĳ�����ʾҪ��Ҫ������������xml�ϣ�null��ʾ��Ҫ
			}

			TextView stra_all_stratitle = (TextView) converView.findViewById(R.id.stra_all_stratitle);
			final TextView note = (TextView) converView.findViewById(R.id.note);
			ImageView iv = (ImageView) converView.findViewById(R.id.strat_image);
			ImageView talk = (ImageView) converView.findViewById(R.id.talk);
			final ImageView collecty = (ImageView) converView.findViewById(R.id.collecty);
		    final ImageView imagegoodb = (ImageView) converView.findViewById(R.id.imagegoodb);
			final ImageView stra_good_image = (ImageView) converView.findViewById(R.id.stra_good_image);
			final TextView stra_username = (TextView) converView.findViewById(R.id.stra_username);
			TextView stra_time = (TextView) converView.findViewById(R.id.stra_time);
			TextView stra_all_content = (TextView) converView.findViewById(R.id.stra_all_content);
			final TextView stra_good = (TextView) converView.findViewById(R.id.stra_good);
			final ImageView collect = (ImageView) converView.findViewById(R.id.collect);
			stra_all_stratitle.setText(news.getStrategy_title());
			stra_username.setText(news.getUsername());
			stra_time.setText(news.getConverDateToString());
			stra_all_content.setText("\t\t" + news.getStrategy_context());
			stra_good.setText("��" + news.getGood_priase_times() + "�˵���");

			String url = news.getImage_url();
			if (url.equals(" ") || url.equals(null)) {
				iv.setVisibility(iv.GONE);
			}

			fb.display(iv, "http://" + Ipclass.SERVER + ":8080/EYT/image/"+ url);
			collect.setOnClickListener(new OnClickListener() {

				@SuppressWarnings("static-access")
				@Override
				public void onClick(View arg0) {
					//collect.setEnabled(false);
					collecty.setVisibility(collecty.VISIBLE);
					collect.setVisibility(collect.GONE);
					note.setVisibility(note.INVISIBLE);
					//Toast.makeText(And_UserQueryStrategyAll.this, "�ղسɹ�", Toast.LENGTH_SHORT).show();
					NetNutil.addStrategyCollect(news.getStrategy_title()
							.replaceAll(" ", ""), And_UserQueryStrategyAll.this);

				}
			});
			stra_good_image.setOnClickListener(new OnClickListener() {

				@SuppressWarnings("static-access")
				@Override
				public void onClick(View arg0) {
					stra_good_image.setEnabled(false);
					imagegoodb.setVisibility(imagegoodb.VISIBLE);
					stra_good_image.setVisibility(stra_good_image.GONE);
					NetNutil.resetGoodPraise(news.getStrategy_title()
							.replaceAll(" ", ""), stra_good);

				}
			});
			talk.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent in = new Intent();
					in.setClass(And_UserQueryStrategyAll.this, AddFriendActivity.class);
					in.putExtra("username", news.getUsername());
					startActivity(in);
					
				}
			});
			return converView;
		}
	}
}
