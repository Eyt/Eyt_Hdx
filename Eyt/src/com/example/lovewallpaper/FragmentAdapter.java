package com.example.lovewallpaper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

//import com.example.firstfragment.FifthFragment;
import com.example.firstfragment.FirstFragment;
import com.example.firstfragment.FourthFragment;
import com.example.firstfragment.SecondFragment;
import com.example.firstfragment.ThirdFragment;

public class FragmentAdapter extends FragmentPagerAdapter {

	public FragmentAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	public final static int TAB_COUNT = 4;

	@Override
	public Fragment getItem(int id) {
		System.out.println("移动Id"+id);
		switch (id) {
		
		case 0:
			System.out.println("切换第一个");
			Fragment firstfragment = new FirstFragment();
			 Bundle args = new Bundle();
	         args.putInt("number", 1);
	            firstfragment.setArguments(args);
			return firstfragment;
		case 1:
			System.out.println("切换第2个");
			SecondFragment secondfragment = new SecondFragment();
			 Bundle args2 = new Bundle();
	         args2.putInt("number", 2);
	         secondfragment.setArguments(args2);
			return secondfragment;
		case 2:
			System.out.println("切换第3个");
			ThirdFragment thirdfragment = new ThirdFragment();
			 Bundle args3 = new Bundle();
	         args3.putInt("number", 3);
	         thirdfragment.setArguments(args3);
			return thirdfragment;
		case 3:
			System.out.println("切换第4个");
			FourthFragment fourthfragment = new FourthFragment();
			 Bundle args4 = new Bundle();
	         args4.putInt("number", 4);
	         fourthfragment.setArguments(args4);
			return fourthfragment;
		}
		return null;
	}
	@Override
	public int getCount() {
		return TAB_COUNT;
	}
}
