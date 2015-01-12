package com.eyt.randomattractions;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;

import com.anfroidclass.Attraction;
import com.anfroidui.util.NetNutil;
import com.anfroidui.util.NetNutil_Attraction;
import com.bmob.im.demo.ui.BaseActivity;
import com.eyt.android.ip.Ipclass;
import com.eyt.unlimited.R;
import com.listview.layout.AddStrategy;
import com.listview.layout.And_UserQueryStrategy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class RandomAttractionActivity extends BaseActivity {
	Attraction attraction_name;
//	private ImageView img_attractions;
	ListView lv;
	private AttractionAdapter adapter;

//	private TextView tv_attractions_title, tv_attractions_want,tv_attractions_context;
	FinalBitmap fb;
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		pd = ProgressDialog.show(RandomAttractionActivity.this, "", "�����У����Ժ󡭡�",true);
		setContentView(R.layout.attractions_list_view);
		initTopBarForLeft("ҡ����");
		lv = (ListView) findViewById(R.id.lv_attraction_item);
		fb = FinalBitmap.create(this);
		adapter = new AttractionAdapter();
		NetNutil_Attraction.getDataFromServer(lv, adapter,pd);

	}

	public class AttractionAdapter extends BaseAdapter {// �Զ���adater

		ArrayList<Attraction> date;

		public ArrayList<Attraction> getDate() {
			return date;
		}

		public void setDate(ArrayList<Attraction> date) {
			this.date = date;
		}

		public AttractionAdapter() {
			super();
			// TODO Auto-generated constructor stub
		}

		public AttractionAdapter(ArrayList<Attraction> date) {
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
			Attraction news = date.get(position);
			if (converView == null) {
				LayoutInflater inflater = LayoutInflater.from(RandomAttractionActivity.this);
				converView = inflater.inflate(R.layout.and_usersearcher_yyy,null);// ���Լ������xml�����ļ�ת��view
								// ����Ĳ�����ʾҪ��Ҫ������������xml�ϣ�null��ʾ��Ҫ
			}

			TextView title = (TextView) converView.findViewById(R.id.title);
			TextView desc = (TextView) converView.findViewById(R.id.description);
			TextView addstrategy = (TextView) converView.findViewById(R.id.write_stategy);
			ImageView iv = (ImageView) converView.findViewById(R.id.imageView_attrforuser);
			final ImageView wantto_image = (ImageView) converView.findViewById(R.id.wantto_image);
			final ImageView wanttoRed=(ImageView) converView.findViewById(R.id.wantored);
			final TextView critical = (TextView) converView.findViewById(R.id.critical);
			Button query_strat = (Button) converView.findViewById(R.id.but_query_strat);
			title.setText(news.getAttractions_name());
			Log.i("And_UserSearch_Attr_Of_name", news.getAttractions_name());
			desc.setText("\t\t" + news.getAttractions_spots());
			Log.i("And_UserSearch_Attr_Of_name", news.getAttractions_spots());
			critical.setText("��" + news.getWant_to() + "����ȥ");
			String url = news.getImage_url(); 

			fb.display(iv, "http://" + Ipclass.SERVER + ":8080/EYT/image/"+ url);
			wantto_image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					wantto_image.setEnabled(false);
					wanttoRed.setVisibility(wanttoRed.VISIBLE);
					wantto_image.setVisibility(wanttoRed.GONE);
					NetNutil.resetWantTo(date.get(position).getAttractions_name(), critical);

				}
			});

			query_strat.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent in = new Intent(RandomAttractionActivity.this,
							And_UserQueryStrategy.class);
					in.putExtra("attr", date.get(position)
							.getAttractions_name());
					startActivity(in);
				}
			});
			addstrategy.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent in = new Intent(RandomAttractionActivity.this,
							AddStrategy.class);
					in.putExtra("attr", date.get(position)
							.getAttractions_name());
					startActivity(in);

				}
			});
			return converView;
		}

	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("RandomAttractionActivity.onDestroy()");

	}
}