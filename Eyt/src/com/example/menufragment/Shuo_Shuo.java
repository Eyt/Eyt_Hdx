//package com.example.menufragment;
//
//import java.util.ArrayList;
//
//import net.tsz.afinal.FinalBitmap;
//import net.tsz.afinal.FinalHttp;
//import net.tsz.afinal.http.AjaxCallBack;
//import net.tsz.afinal.http.AjaxParams;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.anfroidclass.UserAndStrategy_SC;
//import com.anfroidui.util.NetNutil;
//import com.eyt.android.ip.Ipclass;
//import com.eyt.unlimited.R;
//import com.listview.layout.And_MyStrategyAll;
//
//public class Shuo_Shuo extends Fragment {
//	private ListView lv_my_stra=null;
//	FinalBitmap fb;
//	LayoutInflater context;
//	NewAdaterForMyStra adater=new NewAdaterForMyStra();
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		fb = FinalBitmap.create(getActivity());
//	}
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		context = getActivity().getLayoutInflater();
//		View view=inflater.inflate(R.layout.shuo_shuo, null);
//		TextView tv=(TextView) view.findViewById(R.id.textView1);
//		lv_my_stra=(ListView) view.findViewById(R.id.lv_my_strate);
////		 NetNutil.selectMyStratFromDb(lv_my_stra, adater, getActivity());
//		return view;
//	}
//	public class NewAdaterForMyStra extends BaseAdapter {// 自定义adater
//
//		ArrayList<UserAndStrategy_SC> date;
//
//		public ArrayList<UserAndStrategy_SC> getDate() {
//			return date;
//		}
//
//		public void setDate(ArrayList<UserAndStrategy_SC> date) {
//			this.date = date;
//		}
//
//		public NewAdaterForMyStra() {
//			super();
//			// TODO Auto-generated constructor stub
//		}
//
//		public NewAdaterForMyStra(ArrayList<UserAndStrategy_SC> date) {
//			this.date = date;
//		}
//
//		@Override
//		// listview 的条目有多少
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return date.size();
//		}
//
//		@Override
//		// position指定的条目关联的数据对象
//		public Object getItem(int position) {
//			// TODO Auto-generated method stub
//			return date.get(position);
//		}
//
//		@Override
//		// 条目的id
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return position;
//		}
//
//		// converView缓存的条目 position表示条目的位置 parent表示listview 返回值表示listview的一个条目
//		@Override
//		public View getView(final int position, View converView,
//				ViewGroup parent) {
//
//			final UserAndStrategy_SC news = date.get(position);
//			if (converView == null) {
//           converView = context.inflate(R.layout.my_stra_layout,null);// 把自己定义的xml布局文件转成view
//			// 后面的参数表示要不要关联到其他的xml上，null表示
//           }
//			
//			ImageView iv=(ImageView) converView.findViewById(R.id.stra_image);
//			ImageView my_stra_delete=(ImageView) converView.findViewById(R.id.my_stra_delete);
//			final TextView stra_title=(TextView) converView.findViewById(R.id.mystrat_title);
//			TextView stra_id=(TextView) converView.findViewById(R.id.stra_id);
//			TextView stra_udatetime=(TextView) converView.findViewById(R.id.stra_udatetime);
//			stra_title.setText(news.getStra().getStrategy_title());
//			stra_id.setText(String.valueOf(news.getStra().getStrategy_id()));
//			stra_udatetime.setText(news.getConverDateToString());
//			String url = news.getStra().getImage_url();
//			if(url.equals(" ")||url.equals(null)){
//		        iv.setVisibility(iv.GONE);
//			}
//			fb.display(iv, "http://" + Ipclass.SERVER + ":8080/EYT/image/"+ url);
//			converView.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View arg0) {
//					Intent in=new Intent(getActivity(),And_MyStrategyAll.class);// TODO Auto-generated method stub
//					in.putExtra("strat_title", news.getStra().getStrategy_title());
//					Log.i("strat_title", news.getStra().getStrategy_title());
//					startActivity(in);
//				}
//			});
//			my_stra_delete.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View arg0) {
//					FinalHttp fh = new FinalHttp();
//					AjaxParams param = new AjaxParams();
//					param.put("strategy_id", String.valueOf(news.getStra().getStrategy_id()));
//					fh.post("http://" + Ipclass.SERVER+ ":8080/EYT/servlet/Android_deletStrategy", param,new AjaxCallBack<Object>() {
//						@SuppressWarnings("unused")
//						public void onSuccess(Object t) {
//							super.onSuccess(t);
//							Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
//						};
//						
//					});
//				}
//			});
//			return converView;
//		}
//	}
//}
