package com.example.menufragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.lovewallpaper.FragmentAdapter;
import com.eyt.unlimited.R;

public class HomeFragment extends Fragment implements OnClickListener{
	
	public static final int TAB_First = 0;
	public static final int TAB_Second = 1;
	public static final int TAB_Third = 2;
	public static final int TAB_Fourth = 3;
	private ViewPager Mainviewpager;
	private RadioButton tab_first,tab_second,tab_third,tab_fourth;//tab_fifth;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.homefragment, null);
		
		//初始化选卡
		Mainviewpager=(ViewPager)view.findViewById(R.id.viewpager);
		tab_first=(RadioButton)view.findViewById(R.id.first);
		tab_second=(RadioButton)view.findViewById(R.id.second);
		tab_third=(RadioButton)view.findViewById(R.id.third);
		tab_fourth=(RadioButton)view.findViewById(R.id.fourth);
		tab_first.setOnClickListener(this);
		tab_second.setOnClickListener(this);
		tab_third.setOnClickListener(this);
		tab_fourth.setOnClickListener(this);
		FragmentAdapter adapter = new FragmentAdapter(getChildFragmentManager());
		Mainviewpager.setAdapter(adapter);
		
		//设置选卡ID
		Mainviewpager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int id) {
				switch (id) {
				case TAB_First:
					tab_first.setChecked(true);
					break;
				case TAB_Second:
					tab_second.setChecked(true);
					break;
				case TAB_Third:
					tab_third.setChecked(true);
					break;
				case TAB_Fourth:
					tab_fourth.setChecked(true);
					break;
				default:
				break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.first:
			Mainviewpager.setCurrentItem(TAB_First);
			break;
		case R.id.second:
			Mainviewpager.setCurrentItem(TAB_Second);
			break;
		case R.id.third:
			Mainviewpager.setCurrentItem(TAB_Third);
			break;
		case R.id.fourth:
			Mainviewpager.setCurrentItem(TAB_Fourth);
			break;
		default:
			break;
		}		
	}
}
