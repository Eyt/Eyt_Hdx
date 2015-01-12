package com.eyt.mapprint;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapLongClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfigeration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOvelray;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.bmob.im.demo.util.PixelUtil;
import com.eyt.unlimited.R;

@SuppressLint({ "NewApi", "DefaultLocale" })
public class MapprintMainActivity extends BaseActivity implements
		OnClickListener, OnTouchListener, OnMarkerClickListener,
		OnGetPoiSearchResultListener, OnGetSuggestionResultListener,
		OnGetGeoCoderResultListener, OnGetRoutePlanResultListener {

	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private ImageView doCenter;
	private LinearLayout bottom;
	private ImageView search;
	private boolean isDetail = false;// 是否为详情Marker
	private LinearLayout popLayout;
	private LinearLayout mapView_layout;
	private LinearLayout plus_layout;// +
	private LinearLayout sub_layout;// -
	private LinearLayout searchRoad_layout;
	private ImageView searchRoad;
	private PopupWindow mPopupWindow;
	private boolean searchRoad_bool; // mPopupWindow是否显示中
	View popView = null;
	// -----定位相关
	private LocationMode mCurrentMode;// 地图定位方式：普通、跟随、罗盘
	boolean isFirstLoc = true;// 是否首次定位
	LocationClient mLocClient;
	private double mLatitude = 0;
	private double mLongitude = 0;
	private String address;// 地址
	private String city = null;// 城市
	public MyLocationListenner myListener = new MyLocationListenner();
	private TextView map_clear;
	private float zoomLevel = 14f;// 当前地图缩放级别
	private LatLng mLatLng;
	// -----覆盖物相关
	private Marker mMarker;
	List<Marker> markers = new ArrayList<Marker>();
	// 初始化全局 bitmap 信息，不用时及时 recycle
	private TextView tv_address;
	private ImageView type1, type2, type3;
	boolean type2_bool;
	private RelativeLayout remove_print;
//	private RelativeLayout menu_search;
	private SwitchButton switchButton;
	private TextView switchText;
	// -----Poi相关
	private PoiSearch mPoiSearch = null;
	private EditText editCity;
	private EditText editSearchKey;
	private TextView search_confirm;
	private TextView search_location;
	GeoCoder mSearch = null; // 经纬度地址转换搜索模块，也可去掉地图模块独立使用
	RoutePlanSearch routePlanSearch = null; // 线路规划搜索模块
	RouteLine route = null;
	EditText editSt;
	EditText editEn;
	ImageView img_more;
	PopupWindow popwindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_mapprint);
		inteView();
		// 初始化Poi搜索模块，注册搜索事件监听
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);
		editCity = (EditText) findViewById(R.id.city);
		editSearchKey = (EditText) findViewById(R.id.searchkey);
		search_location = (TextView) findViewById(R.id.search_location);
		search_confirm = (TextView) findViewById(R.id.search_confirm);
		search_confirm.setOnClickListener(this);
		search_location.setOnClickListener(this);
		// 初始化经纬度地址转换搜索模块
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);
		// 初始化线路规划搜索模块，注册事件监听
		routePlanSearch = RoutePlanSearch.newInstance();
		routePlanSearch.setOnGetRoutePlanResultListener(this);
	}

	/**
	 * 加载popwindow
	 */
	protected void PopuptWindow() {
		View popupWindow_view = getLayoutInflater().inflate(
				R.layout.popwindow_activity, null, false);
		popwindow = new PopupWindow(popupWindow_view, 350,
				android.app.ActionBar.LayoutParams.WRAP_CONTENT, true);
		popwindow.setAnimationStyle(R.style.AnimationFade);
		popupWindow_view.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (popwindow != null && popwindow.isShowing()) {
					popwindow.dismiss();
					popwindow = null;
				}
				return false;
			}
		});
		tv_address=(TextView) popupWindow_view.findViewById(R.id.tv_address);
		type1 = (ImageView) popupWindow_view.findViewById(R.id.type1);
		type2 = (ImageView) popupWindow_view.findViewById(R.id.type2);
		type3 = (ImageView) popupWindow_view.findViewById(R.id.type3);
		remove_print = (RelativeLayout) popupWindow_view
				.findViewById(R.id.re_remove_print);
//		menu_search = (RelativeLayout) popupWindow_view.findViewById(R.id.menu_search);
		switchButton = (SwitchButton) popupWindow_view.findViewById(R.id.switchButton);
		switchText = (TextView) popupWindow_view.findViewById(R.id.switchText);
		type1.setOnClickListener(this);
		type2.setOnClickListener(this);
		type3.setOnClickListener(this);
		remove_print.setOnClickListener(this);
//		menu_search.setOnClickListener(this);
		switchButton.setChecked(false);
		tv_address.setText(address);
		switchButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					Toast.makeText(MapprintMainActivity.this, "长按地图可显示地址信息",
							Toast.LENGTH_SHORT).show();
				} else {
				}
			}
		});
		// 长按事件
				mBaiduMap.setOnMapLongClickListener(new OnMapLongClickListener() {
					@Override
					public void onMapLongClick(LatLng lalon) {

						if (switchButton.isChecked()) {
							// 反Geo搜索(经纬度转地址)
							mSearch.reverseGeoCode(new ReverseGeoCodeOption()
									.location(lalon));
						} else {
							mLatLng = lalon;
						}

						Toast.makeText(MapprintMainActivity.this,
								"纬度：" + lalon.latitude + "经度" + lalon.longitude, 1)
								.show();

					}
				});
	}

	/**
	 * 初始化地图相关
	 */
	public void inteView() {
		searchRoad = (ImageView) findViewById(R.id.searchRoad);// 搜索路线
		bottom = (LinearLayout) findViewById(R.id.bottom);// 中心
		search = (ImageView) findViewById(R.id.search);// 搜索
		plus_layout = (LinearLayout) findViewById(R.id.plus_layout);// 加
		sub_layout = (LinearLayout) findViewById(R.id.sub_layout);// 减
		mapView_layout = (LinearLayout) findViewById(R.id.mapView_layout);// 地图显示
		searchRoad_layout = (LinearLayout) findViewById(R.id.searchRoad_layout);// 搜索路线布局
		img_more = (ImageView) findViewById(R.id.img_more);
		img_more.setOnClickListener(this);
		// 设置默认位置为北京 缩放级别为13.5f
		MapStatus mapStatus = new MapStatus.Builder()
				.target(new LatLng(39.904965, 116.327764)).zoom(13.5f).build();
		BaiduMapOptions mapOptions = new BaiduMapOptions();
		// 隐藏地图缩放控件
		mapOptions.zoomControlsEnabled(false).mapStatus(mapStatus);
		// 因需要设置mapOptions，所以无法在XML生成mMapView。
		mMapView = new MapView(this, mapOptions);
		mapView_layout.addView(mMapView);
		doCenter = (ImageView) findViewById(R.id.docenter);
		mBaiduMap = mMapView.getMap();
		search.setOnClickListener(this);
		doCenter.setOnClickListener(this);
		plus_layout.setOnClickListener(this);
		sub_layout.setOnClickListener(this);
		searchRoad_layout.setOnClickListener(this);
		// 为地图上的Marker设置监听事件
		mBaiduMap.setOnMarkerClickListener(this);
		

		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);// 设置地图的缩放比例
		mBaiduMap.setMapStatus(msu);// 将前面的参数交给BaiduMap类
		mBaiduMap.setOnMapStatusChangeListener(new OnMapStatusChangeListener() {

			@Override
			public void onMapStatusChangeStart(MapStatus arg0) {
				// 手势操作地图，设置地图状态等操作导致地图状态开始改变。

			}

			@Override
			public void onMapStatusChangeFinish(MapStatus mapStatus) {
				// 地图状态改变结束
				zoomLevel = mBaiduMap.getMapStatus().zoom;
			}

			@Override
			public void onMapStatusChange(MapStatus arg0) {
				// 地图状态变化中
			}
		});

		mCurrentMode = LocationMode.NORMAL;
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setProdName("eyt");// 设置产品线
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		option.setOpenGps(true);
		option.setIsNeedAddress(true);
		option.setIgnoreKillProcess(true);
		mLocClient.setLocOption(option);
		mLocClient.start();
		mBaiduMap.isBuildingsEnabled();// 获取是否允许楼块效果

	}


	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
			address = location.getAddrStr();
			mLatitude = location.getLatitude();
			mLongitude = location.getLongitude();
			city = location.getCity();
			Log.v(address, mLatitude + "==" + mLongitude);
			System.out.println("**********************" + address);
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	/**
	 * 添加点、线、多边形、圆、文字
	 */
	public void addCustomElements(LatLng latLng) {

		// // 添加文字
		//
		// int color=Color.parseColor("#F8F8FF");//十六进制颜色代码,转为int类型
		// OverlayOptions ooText = new TextOptions().bgColor(color)
		// .fontSize(23).fontColor(0xFFFF00FF).text("胜意大厦").rotate(-30)
		// .position(latLng);
		// mBaiduMap.addOverlay(ooText);
	}

	/**
	 * 响应搜索按钮点击事件
	 * 
	 * @param index
	 *            =0默认查询第一页 10条
	 */
	public void searchButton(int index) {
		mPoiSearch.searchInCity((new PoiCitySearchOption())
				.city(editCity.getText().toString())
				.keyword(editSearchKey.getText().toString()).pageNum(index));
	}

	/**
	 * searchNearby
	 * 
	 * @param index
	 * @param la
	 * @param lon
	 */
	public void searchButtonByLatLng(int index, double la, double lon) {
		LatLng latLng = new LatLng(la, lon);
		mPoiSearch.searchNearby(new PoiNearbySearchOption().location(latLng)
				.keyword(editCity.getText().toString()).pageNum(index));
	}

	@Override
	protected void onPause() {
		super.onPause();
		// activity 暂停时同时暂停地图控件
		mMapView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// activity 恢复时同时恢复地图控件
		mMapView.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		mPoiSearch.destroy();
		mSearch.destroy();
	}

	boolean search_bool;

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.docenter:
			if (mLatitude != 0 && mLongitude != 0) {
				LatLng ll = new LatLng(mLatitude, mLongitude);
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
			break;

		case R.id.img_more:
			getPopupWindow();
			popwindow.showAtLocation(v, Gravity.LEFT, 0, -10);

			break;
		case R.id.type1:
			mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);// 普通地图
			popwindow.dismiss();
			break;

		case R.id.type2:
			if (type2_bool) {
				mBaiduMap.setTrafficEnabled(true);// 交通图
				type2_bool = false;
			} else {
				mBaiduMap.setTrafficEnabled(false);
				type2_bool = true;
			}
			popwindow.dismiss();
			break;

		case R.id.type3:
			mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);// 卫星图
			popwindow.dismiss();
			break;
//		case R.id.menu_search:
//			if (bottom.getVisibility() == View.GONE) {
//				Animation animation = AnimationUtils.loadAnimation(
//						MapprintMainActivity.this, R.anim.slide_in_right_scale);
//				bottom.startAnimation(animation);
//				bottom.setVisibility(View.VISIBLE);
//			}
//			popwindow.dismiss();
//			break;
		case R.id.search:
			setSearchStasus(search_bool);
			break;
		case R.id.search_confirm:
			// 确认搜索
			setSearchStasus(search_bool);
			searchButton(0);
			break;
		case R.id.search_location:
			setSearchStasus(search_bool);
			if (mLatitude != 0 && mLongitude != 0) {
				searchButtonByLatLng(0, mLatitude, mLongitude);
			}
			break;
		case R.id.re_remove_print:
			// 清除所有图层
			mMapView.getMap().clear();
			popwindow.dismiss();
			delMarker();
			break;
		case R.id.plus_layout:
			zoomLevel += 0.9f;
			// 设置地图的缩放比例
			MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(zoomLevel);
			mBaiduMap.setMapStatus(msu);
			break;
		case R.id.sub_layout:
			zoomLevel -= 0.9f;
			MapStatusUpdate msu1 = MapStatusUpdateFactory.zoomTo(zoomLevel);
			mBaiduMap.setMapStatus(msu1);
			break;
		case R.id.searchRoad_layout:
			initPopupWindow();
			break;
		case R.id.by_car:
			serachRoadPlan(searchRoad_bool, 1);
			break;
		case R.id.by_bus:
			serachRoadPlan(searchRoad_bool, 2);
			break;
		case R.id.by_foot:
			serachRoadPlan(searchRoad_bool, 3);
			break;
		default:
			break;
		}
	}

	/**
	 * 设置搜索按钮图片 动画。
	 * 
	 * @param bool
	 */
	public void setSearchStasus(boolean bool) {
		if (bool) {
			Animation animation0 = AnimationUtils.loadAnimation(MapprintMainActivity.this, R.anim.slide_out_right);
			bottom.startAnimation(animation0);
			bottom.setVisibility(View.GONE);
			search.setImageDrawable(getResources().getDrawable(R.drawable.map_search2));
			search_bool = false;
		} else {
			Animation animation0 = AnimationUtils.loadAnimation(
					MapprintMainActivity.this, R.anim.slide_in_right);
			bottom.startAnimation(animation0);
			bottom.setVisibility(View.VISIBLE);
			search.setImageDrawable(getResources().getDrawable(
					R.drawable.map_search1));
			search_bool = true;
		}
	}

	private void getPopupWindow() {
		// TODO Auto-generated method stub
		if (null != popwindow) {
			popwindow.dismiss();
			return;
		} else {
			PopuptWindow();
		}
	}

	/**
	 * 加载PopupWindow
	 */
	private void initPopupWindow() {
		if (searchRoad_bool) {
			return;
		}
		popView = View.inflate(this, R.layout.popwindow_layout, null);
		mPopupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		if (mPopupWindow != null && mPopupWindow.isShowing() == false) {
			int screenWidth = PixelUtil.getScreenWidth(MapprintMainActivity.this);
			mPopupWindow.setWidth(screenWidth - 2 * 10);
			// 设置background后在外点击才会消失
			mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.map_layer_background));
			mPopupWindow.setOutsideTouchable(true);// 设置可允许在外点击消失
			// 自定义动画
			mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
			mPopupWindow.update();
			mPopupWindow.setTouchable(true);
			mPopupWindow.setFocusable(true);
			int[] location = new int[2];
			// 获取在整个屏幕内的绝对坐标,location [0],x坐标;location [1],y坐标
			searchRoad_layout.getLocationOnScreen(location);
			int popX = screenWidth- (searchRoad_layout.getWidth() + popView.getWidth());
			mPopupWindow.showAtLocation(popView, Gravity.NO_GRAVITY,PixelUtil.px2dp(10, MapprintMainActivity.this), location[1]
							+ searchRoad_layout.getHeight());
			mPopupWindow.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss() {
					// popPopupWindow 点击了外部消失状态需设置回来
					searchRoad_bool = false;
					searchRoad.setImageDrawable(getResources().getDrawable(R.drawable.road_plan));
				}
			});

			TextView by_car = (TextView) popView.findViewById(R.id.by_car);
			TextView by_bus = (TextView) popView.findViewById(R.id.by_bus);
			TextView by_foot = (TextView) popView.findViewById(R.id.by_foot);
			editSt = (EditText) popView.findViewById(R.id.start_ed);
			editEn = (EditText) popView.findViewById(R.id.end_ed);
			by_car.setOnClickListener(this);
			by_bus.setOnClickListener(this);
			by_foot.setOnClickListener(this);
			searchRoad.setImageDrawable(getResources().getDrawable(R.drawable.map_search1));
			searchRoad_bool = true;

		}

	}

	/**
	 * 
	 * @param bool
	 *            true,mPopupWindow显示中；false mPopupWindow未显示
	 * @param flag
	 *            1驾车 2公交 3步行
	 */
	public void serachRoadPlan(boolean bool, int flag) {
		if (!bool) {
			initPopupWindow();
			searchRoad.setImageDrawable(getResources().getDrawable(R.drawable.map_search1));
			return;
		} else {

			if (city.isEmpty()) {
				Toast.makeText(MapprintMainActivity.this, "Sorry，没有定位到您当前的城市",Toast.LENGTH_SHORT).show();
				return;
			}
			// 设置起终点信息，对于tranist search 来说，城市名无意义
			PlanNode startNode = PlanNode.withCityNameAndPlaceName(city, editSt.getText().toString());
			PlanNode endNode = PlanNode.withCityNameAndPlaceName(city, editEn.getText().toString());
//			Toast.makeText(MapprintMainActivity.this,
//					"city--" + city + "editSt.getText().toString()--"
//							+ editSt.getText().toString()
//							+ "editEn.getText().toString()--"
//							+ editEn.getText().toString(), Toast.LENGTH_SHORT)
//					.show();

			if (flag == 1) {
				routePlanSearch.drivingSearch((new DrivingRoutePlanOption()).from(startNode).to(endNode));
			}
			if (flag == 2) {
				routePlanSearch.transitSearch((new TransitRoutePlanOption()).from(startNode).city(city).to(endNode));
			}
			if (flag == 3) {
				routePlanSearch.walkingSearch((new WalkingRoutePlanOption()).from(startNode).to(endNode));
			}
			mPopupWindow.dismiss();

		}
	}

	/**
	 * //清除覆盖物Marker
	 */
	public void delMarker() {
		if (mMarker != null && markers.size() > 0) {
			for (int i = 0; i < markers.size(); i++) {
				markers.get(i).remove();
			}
		}
	}

	// ----POI

	@Override
	public void onGetSuggestionResult(SuggestionResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult result) {

		if (result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(MapprintMainActivity.this, "抱歉，未找到结果",Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(MapprintMainActivity.this, "成功，查看详情页面",Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onGetPoiResult(PoiResult result) {

		if (result == null
				|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			Toast.makeText(MapprintMainActivity.this, result.error + "",Toast.LENGTH_SHORT).show();
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			mBaiduMap.clear();
			PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(result);
			overlay.addToMap();
			overlay.zoomToSpan();
			return;
		}
	}

	private class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public boolean onPoiClick(int index) {
			super.onPoiClick(index);
			PoiInfo poi = getPoiResult().getAllPoi().get(index);
			if (poi.hasCaterDetails) {
				mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
						.poiUid(poi.uid));
			}
			return true;
		}
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {

		// 地址-->经纬度
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation()).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result.getLocation()));
		String strInfo = String.format("纬度：%f 经度：%f",result.getLocation().latitude, result.getLocation().longitude);
		Toast.makeText(MapprintMainActivity.this, strInfo, Toast.LENGTH_LONG).show();
	}

	int zIndex = 0x123;

	@SuppressLint("DefaultLocale")
	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		// 经纬度-->地址
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(MapprintMainActivity.this, "抱歉，未能找到结果",Toast.LENGTH_LONG).show();
		}
		mBaiduMap.clear();
		View view = getLayoutInflater().inflate(R.layout.location_view, null);
		TextView textView = (TextView) view.findViewById(R.id.address_text);
		textView.setText(result.getAddress());
		// 将View转化成用于显示的bitmap
		BitmapDescriptor bitmap = BitmapDescriptorFactory.fromView(view);
		OverlayOptions overlayOptions = new MarkerOptions().position(result.getLocation()).icon(bitmap).zIndex(zIndex);
		mBaiduMap.addOverlay(overlayOptions);
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result.getLocation()));

	}

	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(MapprintMainActivity.this, "抱歉，未找到结果",Toast.LENGTH_SHORT).show();
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {

			route = result.getRouteLines().get(0);
			DrivingRouteOvelray overlay = new DrivingRouteOvelray(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}

	}

	@Override
	public void onGetTransitRouteResult(TransitRouteResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(MapprintMainActivity.this, "抱歉，未找到结果",Toast.LENGTH_SHORT).show();
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {

			route = result.getRouteLines().get(0);
			TransitRouteOverlay overlay = new TransitRouteOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}

	}

	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(MapprintMainActivity.this, "抱歉，未找到结果",Toast.LENGTH_SHORT).show();
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			route = result.getRouteLines().get(0);
			WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}

	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}

}
