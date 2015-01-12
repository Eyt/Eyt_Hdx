package com.example.firstfragment;

import java.util.ArrayList;

//import org.crazyit.ui.AdapterViewFlipperTest;

import net.tsz.afinal.FinalBitmap;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.anfroidclass.Attraction;
import com.anfroidui.util.NetNutil;
import com.eyt.android.ip.Ipclass;
import com.eyt.unlimited.R;
import com.listview.layout.And_UserSearch_Attr;

public class FirstFragment extends Fragment {

//	private ViewPager firstviewpager;

	int currIndex = 0;
	ListView lv = null;
	FinalBitmap fb;
	Filliper fillperAdater = new Filliper();
	NewAAdater adater = new NewAAdater();
	LayoutInflater context;
	//,final ProgressDialog pd

	@Override
	public void setMenuVisibility(boolean menuVisible) {
		// TODO Auto-generated method stub

		super.setMenuVisibility(menuVisible);
		if (menuVisible) {

			if (SecondFragment.mShakeListener != null) {
				SecondFragment.mShakeListener.stop();
			}
			Bundle bundle = this.getArguments();
			int number = bundle.getInt("number");
			SecondFragment.numrecord = number;
			System.out.println("number:" + number);
			System.out.println("ִ�� ��2��setMenuVisibility");
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		fb = FinalBitmap.create(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		context = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.firstfragment, null);
		lv = (ListView) view.findViewById(R.id.lv_attration);
		AdapterViewFlipper flipper = (AdapterViewFlipper) view.findViewById(R.id.flippe);
		NetNutil.getDataFromServer(lv, adater);
		NetNutil.getImageUrl(flipper, fillperAdater);

		return view;
	}

	public class Filliper extends BaseAdapter {
		ArrayList<Attraction> ImgUrl = new ArrayList<Attraction>();
		private ImageView imageView;

		public ArrayList<Attraction> getImgUrl() {
			return ImgUrl;
		}

		public void setImgUrl(ArrayList<Attraction> imgUrl) {
			ImgUrl = imgUrl;
		}

		@Override
		public int getCount() {
			return ImgUrl.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		// �÷����ķ��ص�View���Ǵ�����ÿ���б���
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			String imgurl = ImgUrl.get(position).getImage_url();
			// ����һ��ImageView

			if (convertView == null) {

				imageView = new ImageView(getActivity());

				fb.display(imageView, "http://" + Ipclass.SERVER
						+ ":8080/EYT/image/" + imgurl);
				// ����ImageView����������
				imageView.setScaleType(ImageView.ScaleType.FIT_XY);
				imageView.setPadding(1, 1, 1, 1);
				// ΪimageView���ò��ֲ���
				imageView.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, 230));
			}
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent in = new Intent(getActivity(),
							And_UserSearch_Attr.class);
					in.putExtra("attr", ImgUrl.get(position)
							.getAttractions_name());
					startActivity(in);

				}
			});
			return imageView;
		}
	};

	public class NewAAdater extends BaseAdapter {// �Զ���adater

		ArrayList<Attraction> date;

		public ArrayList<Attraction> getDate() {
			return date;
		}

		public void setDate(ArrayList<Attraction> date) {
			this.date = date;
		}

		public NewAAdater() {
			super();
			// TODO Auto-generated constructor stub
		}

		public NewAAdater(ArrayList<Attraction> date) {
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

			Attraction news = date.get(position);
			if (converView == null) {

				converView = context.inflate(R.layout.listview_layout, null);// ���Լ������xml�����ļ�ת��view
																				// ����Ĳ�����ʾҪ��Ҫ������������xml�ϣ�null��ʾ��Ҫ
			}

			TextView title = (TextView) converView
					.findViewById(R.id.attractions_title);
			TextView desc = (TextView) converView
					.findViewById(R.id.attractions_description);
			ImageView iv = (ImageView) converView
					.findViewById(R.id.attractions_imageView1);
			title.setText(news.getAttractions_name());
			desc.setText(news.getAttractions_spots());

			String url = news.getImage_url();
			fb.display(iv, "http://" + Ipclass.SERVER + ":8080/EYT/image/"
					+ url);

			converView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent in = new Intent(getActivity(),
							And_UserSearch_Attr.class);
					in.putExtra("attr", date.get(position)
							.getAttractions_name());
					startActivity(in);

				}

			});
			return converView;
		}

	}

}
