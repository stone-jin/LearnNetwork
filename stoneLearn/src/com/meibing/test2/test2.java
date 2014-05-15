package com.meibing.test2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.meibing.eoetest1.R;
import com.meibing.util.LogHelper;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.AsyncTask.Status;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/********
 * 
 * 以POST方式获取网站信息
 * 
 * @author bing
 *
 */
public class test2 extends Activity {
	private Button test2_title_reset;
	private Button test2_title_open;
	private TextView test2_content;
	private Http_task http_task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test2);
		viewInit();
	}
	
	private void viewInit(){
		test2_title_reset = (Button) this.findViewById(R.id.test2_title_reset);
		test2_title_reset.setOnClickListener(onClickListener);
		test2_title_open = (Button) this.findViewById(R.id.test2_title_open);
		test2_title_open.setOnClickListener(onClickListener);
		test2_content = (TextView) this.findViewById(R.id.test2_content);
	}
	
	OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.test2_title_reset:
				test2_content.setText("网站内容");
				break;
			case R.id.test2_title_open:
				if(http_task == null || http_task.getStatus() == Status.FINISHED){
					http_task = new Http_task();
					http_task.execute();
				}
				break;
			}
		}
	};
	
	private class Http_task extends AsyncTask<Object, Integer, Object>{
		private final String httpUrl = "http://pickingstone.sinaapp.com/action.php";
		private String response = "";

		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			publishProgress(0);
			try {
				URL url = new URL(httpUrl);
				HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
				httpURLConnection.setDoOutput(true);
				httpURLConnection.setDoInput(true);
				httpURLConnection.setRequestMethod("POST");
				httpURLConnection.setUseCaches(false);
				httpURLConnection.setInstanceFollowRedirects(true);
				httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				httpURLConnection.connect();
				DataOutputStream out = new DataOutputStream(httpURLConnection.getOutputStream());
				String content = "name=" + URLEncoder.encode("金炳", "utf-8");
				content += "&age=" + URLEncoder.encode("24", "utf-8");
				out.writeBytes(content);
				out.flush();
				out.close();
				BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
				String inputLine = null;
				while((inputLine = reader.readLine()) != null){
					response += inputLine + "\n";
				}
				reader.close();
				httpURLConnection.disconnect();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				LogHelper.trace();
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				LogHelper.trace();
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			test2_content.setText(response);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			switch(values[0]){
			case 0:
				test2_content.setText("正在努力获取网站内容");
				break;
			}
		}
	}
}
