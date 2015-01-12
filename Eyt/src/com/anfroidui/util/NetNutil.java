package com.anfroidui.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.anfroidclass.Attraction;
import com.anfroidclass.AttractionAndStrategy_AS;
import com.anfroidclass.City;
import com.anfroidclass.MyTellAbout;
import com.anfroidclass.Strategy;
import com.anfroidclass.UserAndStrategy_SC;
import com.bmob.im.demo.CustomApplcation;

import com.example.firstfragment.FirstFragment.Filliper;
import com.example.firstfragment.FirstFragment.NewAAdater;
import com.example.firstfragment.ThirdFragment.NewAdaterForTell;

import com.eyt.android.ip.Ipclass;
import com.eyt.android.ip.UserName;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.listview.layout.And_MyStrategyAll.NewAdaterForMyStrat;
import com.listview.layout.And_UserQueryStrategy.NewAdaterForStra;
import com.listview.layout.And_UserQueryStrategyAll.NewAdaterForStrat;
import com.listview.layout.MyCollectStrategyAll.NewAdaterForCollctStrat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.AdapterViewFlipper;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NetNutil {

	public static final String PATH = "http://" + Ipclass.SERVER
			+ ":8080/EYT/servlet/Android_AttractionForUser";

	public static void getDataFromServer(final ListView lv,final NewAAdater adater) {
		AsyncTask< Void,Void, ArrayList<Attraction>> at = new AsyncTask< Void,Void, ArrayList<Attraction>>() {
      
			@Override
			protected ArrayList<Attraction> doInBackground(Void... arg0) {
				HttpClient client = new DefaultHttpClient();

				HttpGet get = new HttpGet(PATH);
				try {

					HttpResponse response = client.execute(get);
					if (response.getStatusLine().getStatusCode() == 200) {
						InputStream is = response.getEntity().getContent();
						byte[] back = converInputStreamByArray(is);

						String json = new String(back);

						Gson g = new Gson();
						ArrayList<Attraction> list = g.fromJson(json,
								new TypeToken<ArrayList<Attraction>>() {
								}.getType());
						return list;
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(ArrayList<Attraction> result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (result != null) {

					adater.setDate(result);
					lv.setAdapter(adater);

				}
			}

			// TODO Auto-generated method stub
		};
		at.execute();

	}

	public static byte[] converInputStreamByArray(InputStream is) {
		byte[] buff = new byte[1024];
		int len;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			while ((len = is.read(buff)) != -1) {
				baos.write(buff, 0, len);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return baos.toByteArray();

	}

	public static void resetWantTo(String attr_name, final TextView tv) {

		final String PATHToWanto = "http://" + Ipclass.SERVER
				+ ":8080/EYT/servlet/Android_WantTo?attr_name=" + attr_name;
		AsyncTask<Void, Void, Integer> at = new AsyncTask<Void, Void, Integer>() {

			@Override
			protected Integer doInBackground(Void... arg0) {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(PATHToWanto);
				try {

					HttpResponse response = client.execute(get);
					if (response.getStatusLine().getStatusCode() == 200) {
						InputStream is = response.getEntity().getContent();
						byte[] back = converInputStreamByArray(is);

						String json = new String(back);

						Gson g = new Gson();
						int i = g.fromJson(json, Integer.class);
						return i;
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Integer result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (result != null) {
					tv.setText("有" + String.valueOf(result) + "人想去");
					tv.setEnabled(false);

				}
			}

			// TODO Auto-generated method stub
		};
		at.execute();

	}

	public static void getDataFromServer(final ListView lv,
			final NewAdaterForStra adater, String attr_name,
			final Context context, final ImageView imageView,final ProgressDialog pd) {
		final String PATHToStrat = "http://" + Ipclass.SERVER
				+ ":8080/EYT/servlet/Android_QuerySttra?attr_name=" + attr_name;
		AsyncTask<Void, Void, ArrayList<AttractionAndStrategy_AS>> at = new AsyncTask<Void, Void, ArrayList<AttractionAndStrategy_AS>>() {

			@Override
			protected ArrayList<AttractionAndStrategy_AS> doInBackground(
					Void... arg0) {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(PATHToStrat);
				try {

					HttpResponse response = client.execute(get);
					if (response.getStatusLine().getStatusCode() == 200) {
						InputStream is = response.getEntity().getContent();
						byte[] back = converInputStreamByArray(is);

						String json = new String(back);

						Gson g = new Gson();
						ArrayList<AttractionAndStrategy_AS> list = g
								.fromJson(
										json,
										new TypeToken<ArrayList<AttractionAndStrategy_AS>>() {
										}.getType());
						return list;
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(
					ArrayList<AttractionAndStrategy_AS> result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (result != null) {
					if (result.size() == 0) {
						Toast.makeText(context, "亲~暂时还没有攻略，快去添加吧", Toast.LENGTH_LONG).show();
						pd.dismiss();
					} else {
						adater.setDate(result);
						lv.setAdapter(adater);
						pd.dismiss();
					}

				}
			}

			// TODO Auto-generated method stub
		};
		at.execute();

	}

	public static void resetGoodPraise(String strategy_title, final TextView tv) {
		final String PATHToWanto = "http://" + Ipclass.SERVER
				+ ":8080/EYT/servlet/Android_ClickGoodPraise?strategy_title="
				+ strategy_title;
		AsyncTask<Void, Void, Integer> at = new AsyncTask<Void, Void, Integer>() {

			@Override
			protected Integer doInBackground(Void... arg0) {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(PATHToWanto);
				try {

					HttpResponse response = client.execute(get);
					if (response.getStatusLine().getStatusCode() == 200) {
						InputStream is = response.getEntity().getContent();
						byte[] back = converInputStreamByArray(is);

						String json = new String(back);

						Gson g = new Gson();
						int i = g.fromJson(json, Integer.class);
						return i;
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Integer result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (result != null) {
					tv.setText("有" + String.valueOf(result) + "人点赞");
					tv.setEnabled(false);

				}
			}

			// TODO Auto-generated method stub
		};
		at.execute();

	}

	public static void addStrategy(final Strategy stra, final Activity context,
			final String attr, final String imgurl) {
		final String PATHToStrat = "http://" + Ipclass.SERVER
				+ ":8080/EYT/servlet/Android_UpStrategy";
		AsyncTask<Void, Void, String> at = new AsyncTask<Void, Void, String>() {

			private InputStream is = null;

			@Override
			protected String doInBackground(Void... arg0) {
				// 1 创建浏览器
				HttpClient client = new DefaultHttpClient();

				HttpPost post = new HttpPost(PATHToStrat);
				List<NameValuePair> reqPara = new ArrayList<NameValuePair>();
				try {
					CustomApplcation all = (CustomApplcation) context
							.getApplication();
					reqPara.add(new BasicNameValuePair("stra_content",
							URLEncoder.encode(stra.getStrategy_context(),
									"utf-8")));
					reqPara.add(new BasicNameValuePair("stra_title", URLEncoder
							.encode(stra.getStrategy_title(), "utf-8")));
					reqPara.add(new BasicNameValuePair("attr_name", URLEncoder
							.encode(attr, "utf-8")));
					reqPara.add(new BasicNameValuePair("name", URLEncoder
							.encode(all.getUserName(), "utf-8")));
					reqPara.add(new BasicNameValuePair("image_url", URLEncoder
							.encode(imgurl, "utf-8")));
				} catch (UnsupportedEncodingException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				UrlEncodedFormEntity entity;
				try {
					entity = new UrlEncodedFormEntity(reqPara);
					post.setEntity(entity);// 设置发送的数据
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				HttpResponse response = null;
				try {
					response = client.execute(post);// 2 发送请求
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (response.getStatusLine().getStatusCode() == 200) {
					try {
						is = response.getEntity().getContent();
						byte[] back = converInputStreamByArray(is);
						String json = new String(back);

						return json;
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (result != null) {

					Toast.makeText(context, "上传成功", Toast.LENGTH_LONG).show();
				}
			}
		};

		at.execute();

	}

	public static void addStrategyCollect(final String straTitle,
			final Activity context) {
		final String PATHToStrat = "http://" + Ipclass.SERVER
				+ ":8080/EYT/servlet/Android_AddStrategy";
		AsyncTask<Void, Void, String> at = new AsyncTask<Void, Void, String>() {

			private InputStream is = null;

			@Override
			protected String doInBackground(Void... arg0) {
				// 1 创建浏览器
				HttpClient client = new DefaultHttpClient();

				HttpPost post = new HttpPost(PATHToStrat);
				List<NameValuePair> reqPara = new ArrayList<NameValuePair>();
				try {
					CustomApplcation all = (CustomApplcation) context
							.getApplication();
					reqPara.add(new BasicNameValuePair("name", URLEncoder
							.encode(all.getUserName(), "utf-8")));
					reqPara.add(new BasicNameValuePair("stra_title", URLEncoder
							.encode(straTitle, "utf-8")));
				} catch (UnsupportedEncodingException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				UrlEncodedFormEntity entity;
				try {
					entity = new UrlEncodedFormEntity(reqPara);
					post.setEntity(entity);// 设置发送的数据
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				HttpResponse response = null;
				try {
					response = client.execute(post);// 2 发送请求
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (response.getStatusLine().getStatusCode() == 200) {
					try {
						is = response.getEntity().getContent();
						byte[] back = converInputStreamByArray(is);
						String json = new String(back);

						return json;
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (result != null) {
					Toast.makeText(context, "收藏成功", Toast.LENGTH_LONG).show();
				}
			}
		};

		at.execute();

	}

	public static void addMyTellAbout(final Activity context,
			final String mytell, final String imgurl) {
		final String PATHToMyTell = "http://" + Ipclass.SERVER
				+ ":8080/EYT/servlet/Android_addMyTell";
		AsyncTask<Void, Void, String> at = new AsyncTask<Void, Void, String>() {

			private InputStream is = null;

			@Override
			protected String doInBackground(Void... arg0) {
				// 1 创建浏览器
				HttpClient client = new DefaultHttpClient();

				HttpPost post = new HttpPost(PATHToMyTell);
				List<NameValuePair> reqPara = new ArrayList<NameValuePair>();
				try {
					CustomApplcation all = (CustomApplcation) context
							.getApplication();
					reqPara.add(new BasicNameValuePair("name", URLEncoder
							.encode(all.getUserName(), "utf-8")));
					reqPara.add(new BasicNameValuePair("img_url", URLEncoder
							.encode(imgurl, "utf-8")));
					reqPara.add(new BasicNameValuePair("mytell", URLEncoder
							.encode(mytell, "utf-8")));

				} catch (UnsupportedEncodingException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				UrlEncodedFormEntity entity;
				try {
					entity = new UrlEncodedFormEntity(reqPara);
					post.setEntity(entity);// 设置发送的数据
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				HttpResponse response = null;
				try {
					response = client.execute(post);// 2 发送请求
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (response.getStatusLine().getStatusCode() == 200) {
					try {
						is = response.getEntity().getContent();
						byte[] back = converInputStreamByArray(is);
						String json = new String(back);

						return json;
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (result != null) {
					Toast.makeText(context, result, Toast.LENGTH_LONG).show();
				}
			}
		};
		at.execute();

	}

	public static void getMyTellAboutFromDb(final ListView lv,
			final NewAdaterForTell adater) {
		final String PATHToStrat = "http://" + Ipclass.SERVER
				+ ":8080/EYT/servlet/Android_ShowMyTell";
		AsyncTask<Void, Void, ArrayList<MyTellAbout>> at = new AsyncTask<Void, Void, ArrayList<MyTellAbout>>() {

			@Override
			protected ArrayList<MyTellAbout> doInBackground(Void... arg0) {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(PATHToStrat);
				try {

					HttpResponse response = client.execute(get);
					if (response.getStatusLine().getStatusCode() == 200) {
						InputStream is = response.getEntity().getContent();
						byte[] back = converInputStreamByArray(is);

						String json = new String(back);

						Gson g = new Gson();
						ArrayList<MyTellAbout> list = g.fromJson(json,
								new TypeToken<ArrayList<MyTellAbout>>() {
								}.getType());
						return list;
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(ArrayList<MyTellAbout> result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (result != null) {
					adater.setDate(result);
					lv.setAdapter(adater);

				}
			}

			// TODO Auto-generated method stub
		};
		at.execute();

	}

	public static void selectMyTellAboutFromDb(final ListView lv_select_mytell,
			final com.example.menu.actionbar.MySession.NewAdaterForSelectTell adater, final Activity context,final ProgressDialog pd) {
		CustomApplcation all = (CustomApplcation) context.getApplication();
		final String PATHToStrat = "http://" + Ipclass.SERVER
				+ ":8080/EYT/servlet/Android_SelectMyTell?name="
				+ all.getUserName();
		AsyncTask<Void, Integer, ArrayList<MyTellAbout>> at = new AsyncTask<Void, Integer, ArrayList<MyTellAbout>>() {

			@Override
			protected ArrayList<MyTellAbout> doInBackground(Void... arg0) {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(PATHToStrat);
				try {

					HttpResponse response = client.execute(get);
					if (response.getStatusLine().getStatusCode() == 200) {
						InputStream is = response.getEntity().getContent();
						byte[] back = converInputStreamByArray(is);

						String json = new String(back);

						Gson g = new Gson();
						ArrayList<MyTellAbout> list = g.fromJson(json,
								new TypeToken<ArrayList<MyTellAbout>>() {
								}.getType());
						return list;
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(ArrayList<MyTellAbout> result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (result != null) {
					if (result.size() == 0) {
						Toast.makeText(context, "亲~你还没有发表约游哦，快去发表吧。",Toast.LENGTH_LONG).show();
						pd.dismiss();
					}else {
					adater.setDate(result);
					lv_select_mytell.setAdapter(adater);
					pd.dismiss();
					}

				}
			}

			// TODO Auto-generated method stub
		};
		at.execute();

	}

	public static void selectMyStratFromDb(final ListView lv_my_stra,
			final com.example.menu.actionbar.MyStrategy.NewAdaterForMyStra adater, final Activity context,final ProgressDialog pd) {
		CustomApplcation all = (CustomApplcation) context.getApplication();
		final String PATHToStrat = "http://" + Ipclass.SERVER
				+ ":8080/EYT/servlet/Android_SelectMyStrat?name="
				+ all.getUserName();
		AsyncTask<Void, Void, ArrayList<UserAndStrategy_SC>> at = new AsyncTask<Void, Void, ArrayList<UserAndStrategy_SC>>() {

			@Override
			protected ArrayList<UserAndStrategy_SC> doInBackground(Void... arg0) {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(PATHToStrat);
				try {

					HttpResponse response = client.execute(get);
					if (response.getStatusLine().getStatusCode() == 200) {
						InputStream is = response.getEntity().getContent();
						byte[] back = converInputStreamByArray(is);

						String json = new String(back);

						Gson g = new Gson();
						ArrayList<UserAndStrategy_SC> list = g.fromJson(json,
								new TypeToken<ArrayList<UserAndStrategy_SC>>() {
								}.getType());
						return list;
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(ArrayList<UserAndStrategy_SC> result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (result != null) {
					if (result.size() == 0) {
						Toast.makeText(context, "亲~你还没有属于自己的攻略，快去上传吧。",Toast.LENGTH_LONG).show();
						pd.dismiss();
					}else{
					adater.setDate(result);
					lv_my_stra.setAdapter(adater);
					pd.dismiss();
					}
				}
			}

			// TODO Auto-generated method stub
		};
		at.execute();

	}

	public static void addFeedBack(final Activity context,
			final String feedbackTitle, final String feedbackContent) {
		final String PATHToMyTell = "http://" + Ipclass.SERVER
				+ ":8080/EYT/servlet/Android_addFeedBack";
		AsyncTask<Void, Void, String> at = new AsyncTask<Void, Void, String>() {

			private InputStream is = null;

			@Override
			protected String doInBackground(Void... arg0) {
				// 1 创建浏览器
				HttpClient client = new DefaultHttpClient();

				HttpPost post = new HttpPost(PATHToMyTell);
				List<NameValuePair> reqPara = new ArrayList<NameValuePair>();
				try {
					CustomApplcation all = (CustomApplcation) context
							.getApplication();
					reqPara.add(new BasicNameValuePair("title", URLEncoder
							.encode(feedbackTitle, "utf-8")));
					reqPara.add(new BasicNameValuePair("content", URLEncoder
							.encode(feedbackContent, "utf-8")));
					reqPara.add(new BasicNameValuePair("name", URLEncoder
							.encode(all.getUserName(), "utf-8")));
				} catch (UnsupportedEncodingException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				UrlEncodedFormEntity entity;
				try {
					entity = new UrlEncodedFormEntity(reqPara);
					post.setEntity(entity);// 设置发送的数据
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				HttpResponse response = null;
				try {
					response = client.execute(post);// 2 发送请求
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (response.getStatusLine().getStatusCode() == 200) {
					try {
						is = response.getEntity().getContent();
						byte[] back = converInputStreamByArray(is);
						String json = new String(back);

						return json;
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (result != null) {

					Toast.makeText(context, "反馈成功", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(context, "反馈失败", Toast.LENGTH_LONG).show();
				}
			}
		};
		at.execute();

	}

	public static void getMyCollectStrate(final ListView lv,
			final com.example.menu.actionbar.Strategycollection.NewAAdaterforCollect adater, final Activity context,final ProgressDialog pd) {
		CustomApplcation all = (CustomApplcation) context.getApplication();
		final String PATHToStrat = "http://" + Ipclass.SERVER
				+ ":8080/EYT/servlet/Android_QueryCollectStrategy?name="
				+ all.getUserName();
		AsyncTask<Void, Void, ArrayList<UserAndStrategy_SC>> at = new AsyncTask<Void, Void, ArrayList<UserAndStrategy_SC>>() {

			@Override
			protected ArrayList<UserAndStrategy_SC> doInBackground(Void... arg0) {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(PATHToStrat);
				try {

					HttpResponse response = client.execute(get);
					if (response.getStatusLine().getStatusCode() == 200) {
						InputStream is = response.getEntity().getContent();
						byte[] back = converInputStreamByArray(is);

						String json = new String(back);

						Gson g = new Gson();
						ArrayList<UserAndStrategy_SC> list = g.fromJson(json,
								new TypeToken<ArrayList<UserAndStrategy_SC>>() {
								}.getType());
						return list;
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(ArrayList<UserAndStrategy_SC> result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (result != null) {
					if (result.size() == 0) {
						Toast.makeText(context, "亲~你还没有收藏过攻略，快去收藏吧。",Toast.LENGTH_LONG).show();
						pd.dismiss();
					}else{
						adater.setDate(result);
						lv.setAdapter(adater);
						pd.dismiss();
					}
				}
			}

			// TODO Auto-generated method stub
		};
		at.execute();

	}

	public static void getMyCollectDate(final TextView collect_name_time) {
		final String PATHToStrat = "http://" + Ipclass.SERVER
				+ ":8080/EYT/servlet/Android_GetCollectDate?name="
				+ UserName.USERNAME;
		AsyncTask<Void, Void, ArrayList<UserAndStrategy_SC>> at = new AsyncTask<Void, Void, ArrayList<UserAndStrategy_SC>>() {

			@Override
			protected ArrayList<UserAndStrategy_SC> doInBackground(Void... arg0) {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(PATHToStrat);
				try {

					HttpResponse response = client.execute(get);
					if (response.getStatusLine().getStatusCode() == 200) {
						InputStream is = response.getEntity().getContent();
						byte[] back = converInputStreamByArray(is);

						String json = new String(back);

						Gson g = new Gson();
						ArrayList<UserAndStrategy_SC> list = g.fromJson(json,
								new TypeToken<ArrayList<UserAndStrategy_SC>>() {
								}.getType());
						return list;
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(ArrayList<UserAndStrategy_SC> result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (result != null) {

					// result=new ArrayList<UserAndStrategy_SC>();
					for (UserAndStrategy_SC usc : result) {
						Log.i("collect_name_time=", usc.getCollectDate());
						collect_name_time.setText("于" + usc.getCollectDate()
								+ "收藏");
					}

				}
			}

			// TODO Auto-generated method stub
		};
		at.execute();

	}

	public static void getImageUrl(final AdapterViewFlipper flipper,
			final Filliper fillperAdater) {
		final String PATH = "http://" + Ipclass.SERVER
				+ ":8080/EYT/servlet/Android_SelectAttractionImageUrl?name=zzg";// +all.getUserName();
		AsyncTask<Void, Void, ArrayList<Attraction>> at = new AsyncTask<Void, Void, ArrayList<Attraction>>() {

			@Override
			protected ArrayList<Attraction> doInBackground(Void... arg0) {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(PATH);
				try {

					HttpResponse response = client.execute(get);
					if (response.getStatusLine().getStatusCode() == 200) {
						InputStream is = response.getEntity().getContent();
						byte[] back = converInputStreamByArray(is);

						String json = new String(back);

						Gson g = new Gson();
						ArrayList<Attraction> list = g.fromJson(json,
								new TypeToken<ArrayList<Attraction>>() {
								}.getType());
						return list;
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(ArrayList<Attraction> result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (result != null) {
					fillperAdater.setImgUrl(result);
					flipper.setAdapter(fillperAdater);

				}
			}

			// TODO Auto-generated method stub
		};
		at.execute();

	}

	public static void insetMyFootFrint(final Activity context,
			final TextView tv, final String city) {
		final String PATHToMyTell = "http://" + Ipclass.SERVER
				+ ":8080/EYT/servlet/Android_InsertCity";
		AsyncTask<Void, Void, ArrayList<City>> at = new AsyncTask<Void, Void, ArrayList<City>>() {

			private InputStream is = null;

			@Override
			protected ArrayList<City> doInBackground(Void... arg0) {
				// 1 创建浏览器
				HttpClient client = new DefaultHttpClient();

				HttpPost post = new HttpPost(PATHToMyTell);
				List<NameValuePair> reqPara = new ArrayList<NameValuePair>();
				try {
					CustomApplcation all = (CustomApplcation) context
							.getApplication();
					reqPara.add(new BasicNameValuePair("name", URLEncoder
							.encode(all.getUserName(), "utf-8")));

					reqPara.add(new BasicNameValuePair("city", URLEncoder
							.encode(city, "utf-8")));

				} catch (UnsupportedEncodingException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				UrlEncodedFormEntity entity;
				try {
					entity = new UrlEncodedFormEntity(reqPara);
					post.setEntity(entity);// 设置发送的数据
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				HttpResponse response = null;
				try {
					response = client.execute(post);// 2 发送请求
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (response.getStatusLine().getStatusCode() == 200) {
					try {
						is = response.getEntity().getContent();
						byte[] back = converInputStreamByArray(is);
						String json = new String(back);
						Gson g = new Gson();
						ArrayList<City> list = g.fromJson(json,
								new TypeToken<ArrayList<City>>() {
								}.getType());

						return list;
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return null;
			}

			@Override
			protected void onPostExecute(ArrayList<City> result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (result != null) {
				}
			}
		};
		at.execute();

	}

	public static void getAllStrat(final ListView lv,
			final NewAdaterForStrat adater, final String stra_title,final ProgressDialog pd) {
		final String PATHToMyTell = "http://" + Ipclass.SERVER
				+ ":8080/EYT/servlet/Android_QueryStraAll";
		AsyncTask<Void, Void, ArrayList<Strategy>> at = new AsyncTask<Void, Void, ArrayList<Strategy>>() {

			private InputStream is = null;

			@Override
			protected ArrayList<Strategy> doInBackground(Void... arg0) {
				// 1 创建浏览器
				HttpClient client = new DefaultHttpClient();

				HttpPost post = new HttpPost(PATHToMyTell);
				List<NameValuePair> reqPara = new ArrayList<NameValuePair>();
				try {

					reqPara.add(new BasicNameValuePair("stra_title", URLEncoder
							.encode(stra_title, "utf-8")));

				} catch (UnsupportedEncodingException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				UrlEncodedFormEntity entity;
				try {
					entity = new UrlEncodedFormEntity(reqPara);
					post.setEntity(entity);// 设置发送的数据
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				HttpResponse response = null;
				try {
					response = client.execute(post);// 2 发送请求
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (response.getStatusLine().getStatusCode() == 200) {
					try {
						is = response.getEntity().getContent();
						byte[] back = converInputStreamByArray(is);
						String json = new String(back);
						Gson g = new Gson();
						ArrayList<Strategy> list = g.fromJson(json,
								new TypeToken<ArrayList<Strategy>>() {
								}.getType());

						return list;
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return null;
			}

			@Override
			protected void onPostExecute(ArrayList<Strategy> result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (result != null) {
					adater.setDate(result);
					lv.setAdapter(adater);
					pd.dismiss();
				}
			}
		};
		at.execute();

	}

	public static void getAllmycollectStrat(final ListView lv,
			final NewAdaterForCollctStrat adater, final String stra_title,final ProgressDialog pd) {
		final String PATHToMyTell = "http://" + Ipclass.SERVER
				+ ":8080/EYT/servlet/Android_MycollectStraAll";
		AsyncTask<Void, Void, ArrayList<Strategy>> at = new AsyncTask<Void, Void, ArrayList<Strategy>>() {

			private InputStream is = null;

			@Override
			protected ArrayList<Strategy> doInBackground(Void... arg0) {
				// 1 创建浏览器
				HttpClient client = new DefaultHttpClient();

				HttpPost post = new HttpPost(PATHToMyTell);
				List<NameValuePair> reqPara = new ArrayList<NameValuePair>();
				try {

					reqPara.add(new BasicNameValuePair("stra_title", URLEncoder
							.encode(stra_title, "utf-8")));

				} catch (UnsupportedEncodingException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				UrlEncodedFormEntity entity;
				try {
					entity = new UrlEncodedFormEntity(reqPara);
					post.setEntity(entity);// 设置发送的数据
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				HttpResponse response = null;
				try {
					response = client.execute(post);// 2 发送请求
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (response.getStatusLine().getStatusCode() == 200) {
					try {
						is = response.getEntity().getContent();
						byte[] back = converInputStreamByArray(is);
						String json = new String(back);
						Gson g = new Gson();
						ArrayList<Strategy> list = g.fromJson(json,
								new TypeToken<ArrayList<Strategy>>() {
								}.getType());

						return list;
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return null;
			}

			@Override
			protected void onPostExecute(ArrayList<Strategy> result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (result != null) {
					adater.setDate(result);
					lv.setAdapter(adater);
					pd.dismiss();
				}
			}
		};
		at.execute();

	}

	public static void getAllMyStrat(final ListView lv,
			final NewAdaterForMyStrat adater, final String stra_title,final ProgressDialog pd) {
		final String PATHToMyTell = "http://" + Ipclass.SERVER
				+ ":8080/EYT/servlet/Android_MyStraAll";
		AsyncTask<Void, Void, ArrayList<Strategy>> at = new AsyncTask<Void, Void, ArrayList<Strategy>>() {

			private InputStream is = null;

			@Override
			protected ArrayList<Strategy> doInBackground(Void... arg0) {
				// 1 创建浏览器
				HttpClient client = new DefaultHttpClient();

				HttpPost post = new HttpPost(PATHToMyTell);
				List<NameValuePair> reqPara = new ArrayList<NameValuePair>();
				try {

					reqPara.add(new BasicNameValuePair("stra_title", URLEncoder
							.encode(stra_title, "utf-8")));

				} catch (UnsupportedEncodingException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				UrlEncodedFormEntity entity;
				try {
					entity = new UrlEncodedFormEntity(reqPara);
					post.setEntity(entity);// 设置发送的数据
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				HttpResponse response = null;
				try {
					response = client.execute(post);// 2 发送请求
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (response.getStatusLine().getStatusCode() == 200) {
					try {
						is = response.getEntity().getContent();
						byte[] back = converInputStreamByArray(is);
						String json = new String(back);
						Gson g = new Gson();
						ArrayList<Strategy> list = g.fromJson(json,
								new TypeToken<ArrayList<Strategy>>() {
								}.getType());

						return list;
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return null;
			}

			@Override
			protected void onPostExecute(ArrayList<Strategy> result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (result != null) {
					adater.setDate(result);
					lv.setAdapter(adater);
					pd.dismiss();
				}
			}
		};
		at.execute();

	}

}
