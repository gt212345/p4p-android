package info.androidhive.slidingmenu;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class ProfessorsModelFragment extends Fragment {
	Thread client;
	Socket socket;
	String url = "http://prof4prof.info";
	HttpClient httpclient;
	HttpGet getmethod;
	HttpResponse response;
	InputStream is;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		client = new Thread(clientSocket);
//		client.start();
//		HttpEntity httpentity = response.getEntity();
//		int ID = new JSONObject()
	}

	public ProfessorsModelFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_professors, container,
				false);

		return rootView;
	}

	Runnable clientSocket = new Runnable() {
		@Override
		public void run() {
			httpclient = new DefaultHttpClient();
			getmethod = new HttpGet(url);
			try {
				response = httpclient.execute(getmethod);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

}
