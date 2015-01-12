package com.example.lovewallpaper;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.menu.actionbar.Actionbar03;
import com.example.menufragment.HomeFragment;
import com.eyt.ForgetPwd;
import com.eyt.myweather.Weather;
import com.eyt.unlimited.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.listview.layout.EditFeedBack;

public class MainActivity extends SlidingFragmentActivity {
	
	public static final int TAB_First = 0;
	public static final int TAB_Second = 1;
	public static final int TAB_Third = 2;
	public static final int TAB_Fourth = 3;
	private CanvasTransformer mTransformer;
	Fragment mContent;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,   
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);//全屏显示
		setContentView(R.layout.content_frame);
		
		System.out.println("新的oncreate");
		if(savedInstanceState!=null)
		{ 
			System.out.println("执行到保存数据");
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
			
		}
		if(mContent==null)
		{
		mContent = new HomeFragment();
		}
		
		showOverflowMenu();
		initSlidingMenu();
		initAnimation();
		
	}
	
	private void showOverflowMenu() {  
		// TODO Auto-generated method stub
	     try {  
	        ViewConfiguration config = ViewConfiguration.get(this);  
	        Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");  
	        if(menuKeyField != null) {  
	            menuKeyField.setAccessible(true);  
	            menuKeyField.setBoolean(config, false);  
	        }  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	}
	
	  @Override
	    protected void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        System.out.println("新的执行onSaveInstanceState");
	        getSupportFragmentManager().putFragment(outState,"mContent",mContent);
	    }
	
	/**
	 * 设置滑动菜单
	 */	
	@SuppressLint("NewApi")
	private void initSlidingMenu(){
		
		
		//设置滑动菜单打开后的视图界面
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mContent).commit();
		FragmentTransaction mFragementTransaction = getSupportFragmentManager()
				.beginTransaction();
		Fragment mFrag = new MenuFragment();//菜单的Fragment
		mFragementTransaction.replace(R.id.menu_frame, mFrag);
		mFragementTransaction.commit();
		//设置当打开滑动菜单时，ActionBar不能够跟随着一起滑动
		setSlidingActionBarEnabled(false);
		//设置滑动菜单的属性值，实例化滑动菜单对象
		SlidingMenu sm = getSlidingMenu();
		//设置左滑菜单
		sm.setMode(SlidingMenu.LEFT);
		//设置滑动阴影的宽度
		sm.setShadowWidthRes(R.dimen.shadow_width);
		//设置滑动阴影的图像资源
		sm.setShadowDrawable(R.drawable.shadow);
		//设置滑动菜单视图的宽度
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		//设置渐入渐出效果的值
		sm.setFadeDegree(0.35f);
		//设置触摸屏幕的模式
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		sm.setBehindCanvasTransformer(mTransformer);
		//主按键可以被点击
		getActionBar().setHomeButtonEnabled(true);
		//设置显示向左的图标
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	/** 
     * 初始化动画效果 
     */
	private void initAnimation() {
		// TODO Auto-generated method stub
		mTransformer = new CanvasTransformer(){  
            @Override  
            public void transformCanvas(Canvas canvas, float percentOpen) {  
                float scale = (float) (percentOpen*0.25 + 0.75);  
                canvas.scale(scale, scale, canvas.getWidth()/2, canvas.getHeight()/2);                
            }   
        };  	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();  
	    inflater.inflate(R.menu.mains, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			break;
		case R.id.Weather:
			Intent intent1 = new Intent();
            intent1.setClass(MainActivity.this,Weather.class);
            item.setIntent(intent1);
            break;
		case R.id.item2:
			Intent intent2 = new Intent();
            intent2.setClass(MainActivity.this, ForgetPwd.class);
            item.setIntent(intent2);
            break;
		case R.id.action_settings:
			Intent intent = new Intent();
            intent.setClass(MainActivity.this, EditFeedBack.class);
            item.setIntent(intent);
			break;
		case R.id.item3:
			Intent intent3 = new Intent();
            intent3.setClass(MainActivity.this, Actionbar03.class);
            item.setIntent(intent3);
            break;
			
		}	
		return super.onOptionsItemSelected(item);
}
	public void switchContent(Fragment fragment) {
		// TODO Auto-generated method stub
		mContent = fragment;
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
	}
	
private long exitTime = 0;  
    
	/** 
	 * 捕捉返回事件按钮 
	 *  
	 * 因为此 Activity 继承 TabActivity 用 onKeyDown 无响应，所以改用 dispatchKeyEvent 
	 * 一般的 Activity 用 onKeyDown 就可以了 
	 */  
	  
	@Override  
	public boolean dispatchKeyEvent(KeyEvent event) {  
	  if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {  
	    if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {  
	      this.exitApp();  
	    }  
	    return true;  
	  }  
	  return super.dispatchKeyEvent(event);  
	}  
	  
	/** 
	 * 退出程序 
	 */  
	private void exitApp() {  
	  // 判断2次点击事件时间  
	  if ((System.currentTimeMillis() - exitTime) > 2000) {  
	    Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();  
	    exitTime = System.currentTimeMillis();  
	  } else {  
	    finish();
	  }  
	}
}
