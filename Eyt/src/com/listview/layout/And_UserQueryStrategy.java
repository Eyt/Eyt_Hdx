package com.listview.layout;

import java.util.ArrayList;
import net.tsz.afinal.FinalBitmap;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.anfroidclass.AttractionAndStrategy_AS;
import com.anfroidui.util.NetNutil;
import com.bmob.im.demo.ui.BaseActivity;
import com.eyt.android.ip.Ipclass;
import com.eyt.unlimited.R;

public class And_UserQueryStrategy  extends BaseActivity {
	 ListView lv=null;
	 FinalBitmap fb;
	 TextView desc;
	 private ProgressDialog pd;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		pd = ProgressDialog.show(And_UserQueryStrategy.this, "", "加载中，请稍后……",true);
		setContentView(R.layout.lv_query_stra_listview);
		initTopBarForLeft("攻略");
		
		ImageView imageView = new ImageView(And_UserQueryStrategy.this);
		// 设置ImageView的缩放类型
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		// 为imageView设置布局参数
		imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		Intent in=getIntent();
		lv=(ListView) super.findViewById(R.id.lv_querystra);
		NewAdaterForStra adater=new NewAdaterForStra();
		NetNutil.getDataFromServer(lv, adater,in.getStringExtra("attr"),And_UserQueryStrategy.this,imageView,pd);
		fb=FinalBitmap.create(this);
		
	}
	 public class NewAdaterForStra extends BaseAdapter{//自定义adater
		 
		    ArrayList<AttractionAndStrategy_AS> date;
			
		    
		    public ArrayList<AttractionAndStrategy_AS> getDate() {
				return date;
			}
			public void setDate(ArrayList<AttractionAndStrategy_AS> date) {
				this.date = date;
			}
			public NewAdaterForStra() {
				super();
				// TODO Auto-generated constructor stub
			}
			public NewAdaterForStra( ArrayList<AttractionAndStrategy_AS> date){
		    	this.date=date;
		    }
			@Override
			  // listview 的条目有多少
			public int getCount() {
				// TODO Auto-generated method stub
				Log.i("getCount", String.valueOf(date.size()));
				return date.size();
				
			}

			@Override
			//position指定的条目关联的数据对象
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return date.get(position);
			}

			@Override
			//条目的id
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			//converView缓存的条目    position表示条目的位置     parent表示listview 返回值表示listview的一个条目
			public View getView(final int position, View converView, ViewGroup parent) {
				final AttractionAndStrategy_AS news=date.get(position);

				if(converView==null){
					LayoutInflater inflater=LayoutInflater.from(And_UserQueryStrategy.this);
					converView=inflater.inflate(R.layout.and_query_stra, null);//把自己定义的xml布局文件转成view 后面的参数表示要不要关联到其他的xml上，null表示不要
				}
				
//				TextView title=(TextView) converView.findViewById(R.id.title);
				TextView stra_title=(TextView) converView.findViewById(R.id.stra_title);
				TextView strategy_time=(TextView) converView.findViewById(R.id.strategy_time);
				ImageView iv=(ImageView) converView.findViewById(R.id.imageView_querystra);
				stra_title.setText(news.getStra().getStrategy_title());//攻略标题
				strategy_time.setText(news.getConverDateToString());
				String url=news.getStra().getImage_url();
				if(url.equals(" ")||url.equals(null)){
			        iv.setVisibility(iv.GONE);
				}
				
				fb.display(iv, "http://"+Ipclass.SERVER+":8080/EYT/image/"+url);
				converView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						Intent in=new Intent(And_UserQueryStrategy.this,And_UserQueryStrategyAll.class);// TODO Auto-generated method stub
						in.putExtra("strat_title", news.getStra().getStrategy_title());
						startActivity(in);
					}
				});
				return converView;
				
			}
	    	
	    }
}
