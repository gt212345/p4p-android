package p4p.android.Activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import info.androidhive.slidingmenu.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends Activity {
	Bundle bundle;
	HttpClient httpclient;
	HttpGet getmethod;
	String Depturl = "http://api.prof4prof.info/depts?school=國立臺灣大學";
	HttpResponse response;
	String[] prodept;
	Thread getDepts;
	ArrayList<String> id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		id = new ArrayList<String>();
		getDepts = new Thread(getDeptsR);
		getDepts.start();
		Timer timer = new Timer(true);
		timer.schedule(new timerTask(), 3000, 2000);
	}

	void putstringarray(Bundle bundle, String[] str) {
		for (int i = 0; i < str.length; i++) {
			bundle.putString("pro" + String.valueOf(i), str[i]);
			Log.w("put", str[i]);
		}
	}

	void putarraystringarray(Bundle bundle, ArrayList<String> str) {
		for (int i = 0; i < str.size(); i++) {
			bundle.putString("id" + String.valueOf(i), str.get(i));
			Log.w("put", str.get(i));
		}
	}

	Runnable getDeptsR = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			httpclient = new DefaultHttpClient();
			getmethod = new HttpGet(Depturl);
			try {
				response = httpclient.execute(getmethod);
				Log.w("Welcome", "Get Request");
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								response.getEntity().getContent(), "UTF-8"));
				String json = reader.readLine();
				JSONArray jarray = new JSONArray(json);
				prodept = new String[jarray.length()];
				for (int i = 0; i < jarray.length(); i++) {
					prodept[i] = jarray.getJSONObject(i).getString("name");
					id.add(jarray.getJSONObject(i).getString("id"));
					Log.w("string", prodept[i]);
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(getApplication(),
								"ClientProtocol error", Toast.LENGTH_SHORT)
								.show();
					}
				});
				e.printStackTrace();
			} catch (IOException e) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(getApplication(), "IO error",
								Toast.LENGTH_SHORT).show();
					}
				});
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(getApplication(), "JSON error",
								Toast.LENGTH_SHORT).show();
					}
				});
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	public class timerTask extends TimerTask {

		public void run() {
			if (prodept != null) {
				Intent it = new Intent();
				bundle = new Bundle();
				int length = prodept.length;
				bundle.putInt("length", length);
				putstringarray(bundle, prodept);
				putarraystringarray(bundle, id);
				it.setClass(WelcomeActivity.this, MainActivity.class);
				it.putExtras(bundle);
				startActivity(it);
				this.cancel();// Fuck u timertask
				WelcomeActivity.this.finish();
			}
		}
	}

}
