package com.listview.layout;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;
import com.anfroidclass.Attraction;
import com.anfroidui.util.NetNutil;
import com.anfroidui.util.NetNutil_AttrUserSearch;
import com.bmob.im.demo.ui.BaseActivity;
import com.eyt.android.ip.Ipclass;
import com.eyt.unlimited.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class And_UserSearch_Attr extends BaseActivity  {
	ListView lv=null;
	FinalBitmap fb;
	ArrayList<Attraction> data=new   ArrayList<Attraction>();
	private ProgressDialog pd;
	
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		pd = ProgressDialog.show(And_UserSearch_Attr.this, "", "�����У����Ժ󡭡�",true);
		
		setContentView(R.layout.andr_search_attr_listview);
		Intent in=getIntent();
		Log.i("And_UserSearch_Attr", "attr_name="+in.getStringExtra("attr"));
		initTopBarForLeft(in.getStringExtra("attr"));
		lv=(ListView) super.findViewById(R.id.lv_search_attration);
		NewAdaterForAttr adater=new NewAdaterForAttr();
		NetNutil_AttrUserSearch.getDataFromServer(lv, adater,in.getStringExtra("attr"),pd);
		fb=FinalBitmap.create(this);
	

		
	}
	 public class NewAdaterForAttr extends BaseAdapter{//�Զ���adater
		 
		    ArrayList<Attraction> date;
		    
		    public ArrayList<Attraction> getDate() {
				return date;
			}
			public void setDate(ArrayList<Attraction> date) {
				this.date = date;
			}
			public NewAdaterForAttr() {
				super();
				// TODO Auto-generated constructor stub
			}
			public NewAdaterForAttr( ArrayList<Attraction> date){
		    	this.date=date;
		    }
			@Override
			  // listview ����Ŀ�ж���
			public int getCount() {
				// TODO Auto-generated method stub
				return date.size();
			}

			@Override
			//positionָ������Ŀ���������ݶ���
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return date.get(position);
			}

			@Override
			//��Ŀ��id
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			//converView�������Ŀ    position��ʾ��Ŀ��λ��     parent��ʾlistview ����ֵ��ʾlistview��һ����Ŀ
			public View getView(final int position, View converView, ViewGroup parent) {
				Attraction news=date.get(position);
				if(converView==null){
					LayoutInflater inflater=LayoutInflater.from(And_UserSearch_Attr.this);
					converView=inflater.inflate(R.layout.and_usersearcher_attra, null);//���Լ������xml�����ļ�ת��view ����Ĳ�����ʾҪ��Ҫ������������xml�ϣ�null��ʾ��Ҫ
				}

				TextView title=(TextView) converView.findViewById(R.id.title);
				TextView desc=(TextView) converView.findViewById(R.id.description);
				Button addstrategy=(Button) converView.findViewById(R.id.write_stategy);
				ImageView iv=(ImageView) converView.findViewById(R.id.imageView_attrforuser);
				final ImageView wantto_image=(ImageView) converView.findViewById(R.id.wantto_image);
				final ImageView wanttoRed=(ImageView) converView.findViewById(R.id.wantored);
				final TextView critical=(TextView) converView.findViewById(R.id.critical);
				Button query_strat=(Button) converView.findViewById(R.id.but_query_strat);
				title.setText(news.getAttractions_name());
				Log.i("And_UserSearch_Attr_Of_name", news.getAttractions_name());
				desc.setText("\t\t"+news.getAttractions_spots());
				Log.i("And_UserSearch_Attr_Of_name", news.getAttractions_spots());
				critical.setText("��"+news.getWant_to()+"����ȥ");
				String url=news.getImage_url();
				fb.display(iv, "http://"+Ipclass.SERVER+":8080/EYT/image/"+url);
				wantto_image.setOnClickListener(new OnClickListener() {
					
					@SuppressWarnings("static-access")
					@Override
					public void onClick(View arg0) {
						//wantto_image.setEnabled(false);
						wanttoRed.setVisibility(wanttoRed.VISIBLE);
						wantto_image.setVisibility(wanttoRed.GONE);
						NetNutil.resetWantTo( date.get(position).getAttractions_name(),critical);
						
					}
				});
				
				query_strat.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						Intent in = new Intent(And_UserSearch_Attr.this,And_UserQueryStrategy.class);
						in.putExtra("attr", date.get(position).getAttractions_name());
						startActivity(in);
					}
				});
				addstrategy.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
					Intent in=new Intent(And_UserSearch_Attr.this,AddStrategy.class);
					in.putExtra("attr", date.get(position).getAttractions_name());
					startActivity(in);
						
					}
				});
				return converView;
			}
	    	
	    }

}
