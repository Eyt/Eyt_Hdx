package com.example.menu.actionbar;

import java.util.ArrayList;

import com.anfroidclass.UserAndStrategy_SC;
import com.anfroidui.util.NetNutil;
import com.bmob.im.demo.ui.BaseActivity;
import com.eyt.android.ip.Ipclass;
import com.eyt.unlimited.R;
import com.listview.layout.And_MyStrategyAll;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyStrategy extends BaseActivity {
	
	private ListView lv_my_stra=null;
	FinalBitmap fb;
	LayoutInflater context;
	NewAdaterForMyStra adater=new NewAdaterForMyStra();
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		pd = ProgressDialog.show(MyStrategy.this, "", "加载中，请稍后……",true);
		fb = FinalBitmap.create(MyStrategy.this);
		setContentView(R.layout.shuo_shuo);
		initTopBarForLeft("我的攻略");
//		TextView tv=(TextView) findViewById(R.id.textView1);
		lv_my_stra=(ListView) findViewById(R.id.lv_my_strate);
		NetNutil.selectMyStratFromDb(lv_my_stra, adater, MyStrategy.this,pd);
		
	}
	
	public class NewAdaterForMyStra extends BaseAdapter {// 自定义adater

		ArrayList<UserAndStrategy_SC> date;

		public ArrayList<UserAndStrategy_SC> getDate() {
			return date;
		}

		public void setDate(ArrayList<UserAndStrategy_SC> date) {
			this.date = date;
		}

		public NewAdaterForMyStra() {
			super();
			// TODO Auto-generated constructor stub
		}

		public NewAdaterForMyStra(ArrayList<UserAndStrategy_SC> date) {
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

		// converView缓存的条目 position表示条目的位置 parent表示listview 返回值表示listview的一个条目
		@Override
		public View getView(final int position, View converView,
				ViewGroup parent) {

			final UserAndStrategy_SC news = date.get(position);
			if (converView == null) {
//           converView = context.inflate(R.layout.my_stra_layout,null);// 把自己定义的xml布局文件转成view
			// 后面的参数表示要不要关联到其他的xml上，null表示
				converView = LayoutInflater.from(MyStrategy.this).inflate(R.layout.my_stra_layout, null);
           
           }
			
			ImageView iv=(ImageView) converView.findViewById(R.id.stra_image);
			ImageView my_stra_delete=(ImageView) converView.findViewById(R.id.my_stra_delete);
			final TextView stra_title=(TextView) converView.findViewById(R.id.mystrat_title);
			TextView stra_id=(TextView) converView.findViewById(R.id.stra_id);
			TextView stra_udatetime=(TextView) converView.findViewById(R.id.stra_udatetime);
			stra_title.setText(news.getStra().getStrategy_title());
			stra_id.setText(String.valueOf(news.getStra().getStrategy_id()));
			stra_udatetime.setText(news.getConverDateToString());
			String url = news.getStra().getImage_url();
			if(url.equals(" ")||url.equals(null)){
		        iv.setVisibility(View.GONE);
			}
			fb.display(iv, "http://" + Ipclass.SERVER + ":8080/EYT/image/"+ url);
			converView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent in=new Intent(MyStrategy.this,And_MyStrategyAll.class);// TODO Auto-generated method stub
					in.putExtra("strat_title", news.getStra().getStrategy_title());
					Log.i("strat_title", news.getStra().getStrategy_title());
					startActivity(in);
				}
			});
			my_stra_delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					FinalHttp fh = new FinalHttp();
					AjaxParams param = new AjaxParams();
					param.put("strategy_id", String.valueOf(news.getStra().getStrategy_id()));
					fh.post("http://" + Ipclass.SERVER+ ":8080/EYT/servlet/Android_deletStrategy", param,new AjaxCallBack<Object>() {
						public void onSuccess(Object t) {
							super.onSuccess(t);
							Toast.makeText(MyStrategy.this, t.toString(), Toast.LENGTH_SHORT).show();
							Intent in=new Intent(MyStrategy.this,MyStrategy.class);
						    startActivity(in);
						    finish();
						};
						
					});
				}
			});
			return converView;
		}
	}

}
