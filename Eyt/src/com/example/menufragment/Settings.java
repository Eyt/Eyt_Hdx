//package com.example.menufragment;
//
//import java.util.ArrayList;
//
//import net.tsz.afinal.FinalBitmap;
//import net.tsz.afinal.FinalHttp;
//import net.tsz.afinal.http.AjaxCallBack;
//import net.tsz.afinal.http.AjaxParams;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
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
//import com.anfroidclass.MyTellAbout;
//import com.anfroidui.util.NetNutil;
//import com.eyt.android.ip.Ipclass;
//import com.eyt.unlimited.R;
//
//public class Settings extends Fragment {
//	
//	private ListView lv_select_mytell=null;
//	FinalBitmap fb;
//	LayoutInflater context;
//	NewAdaterForSelectTell adater=new NewAdaterForSelectTell();
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
//		View view=inflater.inflate(R.layout.settings, null);
////		TextView tv=(TextView) view.findViewById(R.id.mytell);
//		lv_select_mytell=(ListView) view.findViewById(R.id.lv_selecte_mytell);
////		 NetNutil.selectMyTellAboutFromDb(lv_select_mytell, adater, getActivity());
//		return view;
//	}
//	public class NewAdaterForSelectTell extends BaseAdapter {// 自定义adater
//
//		ArrayList<MyTellAbout> date;
//
//		public ArrayList<MyTellAbout> getDate() {
//			return date;
//		}
//
//		public void setDate(ArrayList<MyTellAbout> date) {
//			this.date = date;
//		}
//
//		public NewAdaterForSelectTell() {
//			super();
//			// TODO Auto-generated constructor stub
//		}
//
//		public NewAdaterForSelectTell(ArrayList<MyTellAbout> date) {
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
//			final MyTellAbout news = date.get(position);
//			if (converView == null) {
//
//				converView = context.inflate(R.layout.select_mytell_layout,null);// 把自己定义的xml布局文件转成view
//																				// 后面的参数表示要不要关联到其他的xml上，null表示不要
//			}
//			
//			//final EditText edit_my_tell=(EditText) converView.findViewById(R.id.edit_tell_about);
//			ImageView iv=(ImageView) converView.findViewById(R.id.mytell_image);
//			ImageView hiv=(ImageView) converView.findViewById(R.id.my_himage);
//			ImageView image_delet=(ImageView) converView.findViewById(R.id.image_delet);
//			final TextView mytell_content=(TextView) converView.findViewById(R.id.mytell_content);
//			final TextView mytell_username=(TextView) converView.findViewById(R.id.tv_mytell_username);
//			final TextView tell_id=(TextView) converView.findViewById(R.id.tell_id);
//			mytell_content.setText(news.getTell_context());
//			mytell_username.setText(news.getDateToString());
//			tell_id.setText(String.valueOf(news.getTell_id()));
//			
//			String url = news.getMy_tell_about_image_url();
//			if(url.equals(" ")||url.equals(null)){
//				
//		        iv.setVisibility(iv.GONE);
//			}
//			String hurl=news.getImageUrl();
//			fb.display(iv, "http://" + Ipclass.SERVER + ":8080/EYT/image/"+ url);
//			fb.display(hiv, "http://" + Ipclass.SERVER + ":8080/EYT/image/"+ hurl);
//			image_delet.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View arg0) {
//					FinalHttp fh = new FinalHttp();
//					AjaxParams param = new AjaxParams();
//					param.put("tell_id", String.valueOf(news.getTell_id()));
//					fh.post("http://" + Ipclass.SERVER+ ":8080/EYT/servlet/Android_deletMyTell", param,new AjaxCallBack<Object>() {
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
//
//	}
//}
