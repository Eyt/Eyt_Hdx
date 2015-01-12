package com.eyt.myweather;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.bmob.im.demo.util.CommonUtils;
import com.eyt.unlimited.R;

public class SelectCity extends Activity {

	private String[] citys;
	private ImageView back;
	private GridView cityList;
	private Intent intent;
	private EditText inputCity;
	private Button search;
	private ProgressDialog dialog;
	private Builder builder;
	private String city;

	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.select_city);

		// ����LocationClient��
		mLocationClient = new LocationClient(getApplicationContext());
		// ע���������
		mLocationClient.registerLocationListener(myListener);
		// ���ö�λ����
		setLocationOption();
		dialog = new ProgressDialog(SelectCity.this);
		dialog.setMessage("���ڶ�λ...");
		dialog.setCanceledOnTouchOutside(false);
		citys = getResources().getStringArray(R.array.citys);
		cityList = (GridView) findViewById(R.id.city_list);
		back = (ImageView) findViewById(R.id.back);
		inputCity = (EditText) findViewById(R.id.input_city);
		search = (Button) findViewById(R.id.search);
		back.setOnClickListener(new ButtonListener());
		search.setOnClickListener(new ButtonListener());
		inputCity.addTextChangedListener(new Watcher());
		cityList.setAdapter(new MyAdapter(SelectCity.this));
		cityList.setOnItemClickListener(new ClickListener());
	}

	class MyAdapter extends BaseAdapter {
		private Context mContext;
		private MyAdapter(Context mContext) {
			this.mContext = mContext;
		}

		@Override
		public int getCount() {
			return citys.length;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.city_item, null);
				holder = new ViewHolder();
				holder.city = (TextView) convertView.findViewById(R.id.city);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.city.setText(citys[position]);
			return convertView;
		}

	}

	class ViewHolder {
		TextView city;
	}

	class ClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			city = citys[arg2];
			if ("�Զ���λ".equals(city)) {
				if (CommonUtils.isNetworkAvailable(SelectCity.this) == false) {
					Toast.makeText(SelectCity.this, "�����쳣,������������",
							Toast.LENGTH_SHORT).show();
					return;
				}
				dialog.show();
				requestLocation();
			} else {
				intent = new Intent();
				intent.putExtra("city", city);
				SelectCity.this.setResult(1, intent);
				SelectCity.this.finish();
			}
		}

	}

	/**
	 * �����༭�����ݣ��������ݣ���ʾ��������
	 */
	class Watcher implements TextWatcher {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			if (inputCity.getText().toString().length() == 0) {
				search.setVisibility(View.GONE);
			} else {
				search.setVisibility(View.VISIBLE);
			}
		}
	}

	class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.back) {
				back();
			} else if (id == R.id.search) {
				city = inputCity.getText().toString();
				intent = new Intent();
				intent.putExtra("city", city);
				SelectCity.this.setResult(1, intent);
				SelectCity.this.finish();
			} else {
			}
		}

	}

	/**
	 * ʵ��BDLocationListener�ӿ�
	 * 
	 * BDLocationListener�ӿ���2��������Ҫʵ�֣� 1.�����첽���صĶ�λ�����������BDLocation���Ͳ�����
	 * 2.�����첽���ص�POI��ѯ�����������BDLocation���Ͳ�����
	 * 
	 *
	 * 
	 */
	class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			dialog.cancel();
			int code = location.getLocType();
			String addr = location.getAddrStr();
			if (code == 161 && addr != null) {
				// ��λ�ɹ�
				System.out.println(addr);
				city = formatCity(addr);
				intent = new Intent();
				intent.putExtra("city", city);
				SelectCity.this.setResult(1, intent);
				SelectCity.this.finish();
			} else {
				// ��λʧ��
				builder = new Builder(SelectCity.this);
				builder.setTitle("��ʾ");
				builder.setMessage("�Զ���λʧ�ܡ�");
				builder.setPositiveButton("����",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (CommonUtils.isNetworkAvailable(SelectCity.this) == false) {
									Toast.makeText(SelectCity.this,
											"�����쳣,������������", Toast.LENGTH_SHORT)
											.show();
									return;
								}
								SelectCity.this.dialog.show();
								requestLocation();
							}
						});
				builder.setNegativeButton("ȡ��", null);
				builder.setCancelable(false);
				builder.show();
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}

	}

	/**
	 * ���ö�λ������ ��λģʽ�����ζ�λ����ʱ��λ���������������ͣ��Ƿ��GPS�ȵȡ�
	 */
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// ���ö�λģʽ:�߾���ģʽ
		option.setCoorType("bd09ll"); // ������������:�ٶȾ�γ��
		option.setScanSpan(1000);// ���÷���λ����ļ��ʱ��Ϊ1000ms:����1000Ϊ�ֶ���λһ�Σ����ڻ����1000��Ϊ��ʱ��λ
		option.setIsNeedAddress(true);// ����Ҫ������ַ��Ϣ
		option.setProdName("eyt");// ���ò�Ʒ��
		option.setOpenGps(true);// ��gps
		mLocationClient.setLocOption(option);
	}

	/**
	 * ����λ����Ϣ
	 */
	private void requestLocation() {
		if (mLocationClient.isStarted() == false) {
			mLocationClient.start();
		} else {
			mLocationClient.requestLocation();
		}
	}

	/**
	 * ��λ����Ϣת��Ϊ����
	 * 
	 * @param addr
	 *            λ��
	 * @return ��������
	 */
	private String formatCity(String addr) {
		String city = null;
		if (addr.contains("������") && addr.contains("��")) {
			city = addr.substring(addr.indexOf("��") + 1, addr.indexOf("��"));
		} else if (addr.contains("��")) {
			city = addr.substring(addr.indexOf("��") + 1, addr.indexOf("��"));
		} else {
			int start = addr.indexOf("��");
			int end = addr.lastIndexOf("��");
			if (start == end) {
				if (addr.contains("ʡ")) {
					city = addr.substring(addr.indexOf("ʡ") + 1,
							addr.indexOf("��"));
				} else if (addr.contains("��")) {
					city = addr.substring(0, addr.indexOf("��"));
				}
			} else {
				city = addr.substring(addr.indexOf("��") + 1,
						addr.lastIndexOf("��"));
			}
		}
		return city;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			back();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * finish Activityǰ�ж��Ƿ������Activity
	 */
	private void back() {
		intent = getIntent();
		if ("".equals(intent.getStringExtra("city"))) {
			Weather.context.finish();
		}
		SelectCity.this.finish();
		System.exit(0);
	}
}
