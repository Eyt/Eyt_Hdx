package com.example.firstfragment;

import com.eyt.randomattractions.RandomAttractionActivity;
import com.eyt.randomattractions.ShakeListener;
import com.eyt.randomattractions.ShakeListener.OnShakeListener;
import com.eyt.unlimited.R;
import android.app.FragmentManager;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SecondFragment extends Fragment {

	private RelativeLayout mImgUp;// 动画向上
	private RelativeLayout mImgDn;// 动画向下
	private Vibrator mVibrator;// 传感器
	public static com.eyt.randomattractions.ShakeListener mShakeListener = null;// 摇晃监听器
	private LinearLayout shake_report_waiting;
	private ImageView shake_line_up;
	private ImageView shake_line_down;
	private ImageView shakeBg;
	private SoundPool soundPool;// 声明一个SoundPool
	private int music;// 定
	private int musicMatch;// 定
	private ImageView shake_search_music_switch_btn;
	private boolean shakeMusicFlg = false;
	private RelativeLayout mTitle;
	private boolean flg = true;
	private ImageView sound_ic;
	private View view;
	public static int numrecord;// 判断哪个fragment
	FragmentManager fragmentmanager;

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated methsetUserVisibleHintod stub
		if (isVisibleToUser) {
			super.setUserVisibleHint(isVisibleToUser);
			Bundle bundle = this.getArguments();
			int number = bundle.getInt("number");
			System.out.println("number:" + number);
			System.out.println("执行第se 3个tUserVisibleHint");
		}
	}

	@Override
	public void setMenuVisibility(boolean menuVisible) {
		// TODO Auto-generated method stub

		super.setMenuVisibility(menuVisible);
		if (menuVisible) {
			System.out.println("执行第se 3个setMenuVisibility");
			Bundle bundle = this.getArguments();
			int number = bundle.getInt("number");
			System.out.println("number:" + number);
			numrecord = number;
			System.out.println("获取的activity的名称"+ getActivity().getClass().getName());
			initSound();
			initView();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		System.out.println("执行onCreateView");
		view = inflater.inflate(R.layout.thirdfragment, null);
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("onresume");
		// System.out.println("执行resume");
		if (numrecord == 2 && mShakeListener != null) {
			mShakeListener.start();
		}
	}

	private void initView() {
		System.out.println("进入是直行");
		mVibrator = (Vibrator) getActivity().getApplication().getSystemService(getActivity().VIBRATOR_SERVICE);
		mImgUp = (RelativeLayout) view.findViewById(R.id.shake_up_ll);
		mImgDn = (RelativeLayout) view.findViewById(R.id.shake_down_ll);
		shake_report_waiting = (LinearLayout) view.findViewById(R.id.shake_report_waiting);
		shake_line_up = (ImageView) view.findViewById(R.id.shake_line_up);
		shake_line_down = (ImageView) view.findViewById(R.id.shake_line_down);
		shakeBg = (ImageView) view.findViewById(R.id.shakeBg);
		shake_search_music_switch_btn = (ImageView) view.findViewById(R.id.shake_search_music_switch_btn);
		mShakeListener = new ShakeListener(getActivity());
		mShakeListener.start();
		mShakeListener.setOnShakeListener(new OnShakeListener() {
			@Override
			public void onShake() {
				startAnim(); // 开始 摇一摇手掌动画
				mShakeListener.stop();
				startVibrato(); // 开始 震动
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						soundPool.play(musicMatch, 1, 1, 0, 0, 1);
						shake_report_waiting.setVisibility(View.VISIBLE);
						shake_report_waiting.setVisibility(View.GONE);
						Intent in = new Intent();
						in.setClass(getActivity(),RandomAttractionActivity.class);
						getActivity().startActivity(in);
						mVibrator.cancel();
						mShakeListener.start();
					}
				}, 2000);
			}
		});
		mTitle = (RelativeLayout) view.findViewById(R.id.shake_title_bar);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("直行destroy");
	}

	private void initSound() {
		soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);// 创建SoundPool,可以同时设置最大容纳的音频流数量，音质．
		System.out.println("getActivity" + getActivity());
		music = soundPool.load(getActivity(), R.raw.shake_sound_male, 1); // 加载多个不同的声音文件
		musicMatch = soundPool.load(getActivity(), R.raw.shake_match, 1); // 加载多个不同的声音文件
		sound_ic = (ImageView) view.findViewById(R.id.sound_ic);
		Intent intent = getActivity().getIntent();
		flg = intent.getBooleanExtra("sound_flag", true);

	}

	public void startAnim() { // 定义摇一摇动画动画
		AnimationSet animup = new AnimationSet(true);
		TranslateAnimation mytranslateanimup0 = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,-0.5f);
		mytranslateanimup0.setDuration(1000);
		TranslateAnimation mytranslateanimup1 = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,+0.5f);
		mytranslateanimup1.setDuration(1000);// 动画运行时间，定义在多次时间（ms）内完成动画
		mytranslateanimup1.setStartOffset(1000);// 延迟一定时间后运行动画
		animup.addAnimation(mytranslateanimup0);
		animup.addAnimation(mytranslateanimup1);// 添加动画
		animup.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				shake_line_up.setVisibility(View.VISIBLE);
				if (flg) {
					soundPool.play(music, 1, 1, 0, 0, 1);// 根据声音的ID号来播放不同的声音
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				shake_line_up.setVisibility(View.GONE);
				// soundPool.stop(music);
			}
		});
		mImgUp.startAnimation(animup);
		AnimationSet animdn = new AnimationSet(true);
		TranslateAnimation mytranslateanimdn0 = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,+0.5f);
		mytranslateanimdn0.setDuration(1000);
		TranslateAnimation mytranslateanimdn1 = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,-0.5f);
		mytranslateanimdn1.setDuration(1000);
		mytranslateanimdn1.setStartOffset(1000);
		animdn.addAnimation(mytranslateanimdn0);
		animdn.addAnimation(mytranslateanimdn1);
		animdn.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				shake_line_down.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				shake_line_down.setVisibility(View.GONE);
			}
		});
		mImgDn.startAnimation(animdn);
	}

	public void startVibrato() { // 定义震动
		mVibrator.vibrate(new long[] { 500, 200, 500, 200 }, -1); // 第一个｛｝里面是节奏数组，
		// 第二个参数是重复次数，-1为不重复，非-1从pattern的指定下标开始重复
	}

	private void test() {
		// TODO Auto-generated method stub
		Intent in = new Intent();
		in.setClass(getActivity(), RandomAttractionActivity.class);
		getActivity().startActivity(in);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		System.out.println("直行到暂停");
		if (mShakeListener != null) {
			mShakeListener.stop();
		}
	}

}
