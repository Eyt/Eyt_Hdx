package com.listview.layout;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;

import com.anfroidclass.Strategy;
import com.anfroidui.util.NetNutil;
import com.bmob.im.demo.ui.BaseActivity;
import com.eyt.android.ip.Ipclass;
import com.eyt.unlimited.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class And_MyStrategyAll extends BaseActivity {
	ListView lv = null;
	FinalBitmap fb;

	ArrayList<Strategy> data = new ArrayList<Strategy>();
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		pd = ProgressDialog.show(And_MyStrategyAll.this, "", "加载中，请稍后……",true);
		setContentView(R.layout.lv_mystartbytitle);
		Intent in = getIntent();
		Log.i("strat_title", in.getStringExtra("strat_title"));
		lv = (ListView) super.findViewById(R.id.lv_stra_all);
		NewAdaterForMyStrat adater = new NewAdaterForMyStrat();
		NetNutil.getAllMyStrat(lv, adater, in.getStringExtra("strat_title"),pd);
		initTopBarForLeft(in.getStringExtra("strat_title"));
		fb = FinalBitmap.create(this);

	}

	public class NewAdaterForMyStrat extends BaseAdapter {// 自定义adater

		ArrayList<Strategy> date;

		public ArrayList<Strategy> getDate() {
			return date;
		}

		public void setDate(ArrayList<Strategy> date) {
			this.date = date;
		}

		public NewAdaterForMyStrat() {
			super();
			// TODO Auto-generated constructor stub
		}

		public NewAdaterForMyStrat(ArrayList<Strategy> date) {
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
		// converView缓存的条目 position表示条目的位置 parent表示listview 返回值表示listview的一个条目
		public View getView(final int position, View converView,
				ViewGroup parent) {
			final Strategy news = date.get(position);
			if (converView == null) {
				LayoutInflater inflater = LayoutInflater
						.from(And_MyStrategyAll.this);
				converView = inflater.inflate(R.layout.and_querymystrat_all,
						null);// 把自己定义的xml布局文件转成view
								// 后面的参数表示要不要关联到其他的xml上，null表示不要
			}

			TextView stra_all_stratitle = (TextView) converView.findViewById(R.id.mstra_all_stratitle);
			ImageView iv = (ImageView) converView.findViewById(R.id.mstrat_image);
			//TextView stra_time = (TextView) converView.findViewById(R.id.mstra_time);
			TextView stra_all_content = (TextView) converView.findViewById(R.id.mstra_all_content);
			final TextView stra_good = (TextView) converView.findViewById(R.id.mstra_good);
//			ImageView m_goodimage = (ImageView) converView.findViewById(R.id.m_goodimage);
			stra_all_stratitle.setText(news.getStrategy_title());
		//	stra_time.setText(news.getConverDateToString());
			stra_all_content.setText("\t\t" + news.getStrategy_context());
			stra_good.setText("已有" + news.getGood_priase_times() + "人点赞");

			String url = news.getImage_url();
			if (url.equals(" ") || url.equals(null)) {
				iv.setVisibility(iv.GONE);
			}

			fb.display(iv, "http://" + Ipclass.SERVER + ":8080/EYT/image/"+ url);

			return converView;
		}

	}
}
