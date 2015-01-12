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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MyCollectStrategyAll extends BaseActivity {
	ListView lv = null;
	FinalBitmap fb;
	ArrayList<Strategy> data = new ArrayList<Strategy>();
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		pd = ProgressDialog.show(MyCollectStrategyAll.this, "", "加载中，请稍后……",true);
		setContentView(R.layout.lv_mycollectstartbytitle);
		Intent in = getIntent();
		lv = (ListView) super.findViewById(R.id.lv_cstra_all);
		NewAdaterForCollctStrat adater = new NewAdaterForCollctStrat();
		Log.i("strat_title", in.getStringExtra("strat_title"));
		NetNutil.getAllmycollectStrat(lv, adater,in.getStringExtra("strat_title"),pd);
		initTopBarForLeft(in.getStringExtra("strat_title"));
		fb = FinalBitmap.create(this);

	}

	public class NewAdaterForCollctStrat extends BaseAdapter {// 自定义adater

		ArrayList<Strategy> date;

		public ArrayList<Strategy> getDate() {
			return date;
		}

		public void setDate(ArrayList<Strategy> date) {
			this.date = date;
		}

		public NewAdaterForCollctStrat() {
			super();
			// TODO Auto-generated constructor stub
		}

		public NewAdaterForCollctStrat(ArrayList<Strategy> date) {
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
						.from(MyCollectStrategyAll.this);
				converView = inflater.inflate(
						R.layout.and_query_mycollectstrat_all, null);// 把自己定义的xml布局文件转成view
						// 后面的参数表示要不要关联到其他的xml上，null表示不要
			}

			TextView stra_all_stratitle = (TextView) converView.findViewById(R.id.cstra_all_stratitle);
			ImageView iv = (ImageView) converView.findViewById(R.id.cstrat_image);
			final TextView stra_username = (TextView) converView.findViewById(R.id.cstra_username);
			//TextView stra_time = (TextView) converView.findViewById(R.id.cstra_time);
			TextView stra_all_content = (TextView) converView.findViewById(R.id.cstra_all_content);
			final TextView stra_good = (TextView) converView.findViewById(R.id.cstra_good);
			ImageView c_goodimage = (ImageView) converView.findViewById(R.id.c_goodimage);
			ImageView c_tallk = (ImageView) converView.findViewById(R.id.c_tallk);
			stra_all_stratitle.setText(news.getStrategy_title());
			stra_username.setText(news.getUsername());
			//stra_time.setText(news.getConverDateToString());
			stra_all_content.setText("\t\t" + news.getStrategy_context());
			stra_good.setText("已有" + news.getGood_priase_times() + "人点赞");
			String url = news.getImage_url();
			if (url.equals(" ") || url.equals(null)) {
				iv.setVisibility(iv.GONE);
			}

			fb.display(iv, "http://" + Ipclass.SERVER + ":8080/EYT/image/"+ url);
			
			c_tallk.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent in = new Intent();
					in.setClass(MyCollectStrategyAll.this, AddFriendActivity.class);
					in.putExtra("username", news.getUsername());
					startActivity(in);
				}
			});

			return converView;
		}
	}
}
