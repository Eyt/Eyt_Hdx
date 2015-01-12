package com.anfroidui.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ListView;
import com.anfroidclass.Attraction;
import com.eyt.android.ip.Ipclass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.listview.layout.And_UserSearch_Attr.NewAdaterForAttr;

public class NetNutil_AttrUserSearch {

	public static void getDataFromServer(final ListView lv,final NewAdaterForAttr adater, String attr_name,final ProgressDialog pd) {
		final String PATH = "http://" + Ipclass.SERVER
				+ ":8080/EYT/servlet/Android_UserSearchByAttrName?attr_name="
				+ attr_name;
		AsyncTask<Void, Void, ArrayList<Attraction>> at = new AsyncTask<Void, Void, ArrayList<Attraction>>() {

			@Override
			protected void onProgressUpdate(Void... values) {
				// TODO Auto-generated method stub
				super.onProgressUpdate(values);
			}
			
			
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
					pd.dismiss();

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

}
