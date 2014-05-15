package com.meibing.test1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.meibing.eoetest1.R;
import com.meibing.util.LogHelper;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/*****
 * 
 * 普通方式获取网页信息
 * 
 * @author bing
 *
 */

public class Test1 extends Activity {

	//view控件
	private Button Test1_title_reset;
	private Button Test1_title_open;
	private TextView Test1_content;
	private Http_task http_task;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test1);
		viewInit();
	}
	
	private void viewInit(){
		Test1_title_reset = (Button) this.findViewById(R.id.Test1_title_reset);
		Test1_title_reset.setOnClickListener(onClickListener);
		Test1_title_open = (Button) this.findViewById(R.id.Test1_title_open);
		Test1_title_open.setOnClickListener(onClickListener);
		Test1_content = (TextView) this.findViewById(R.id.Test1_content);
		Test1_content.setMovementMethod(ScrollingMovementMethod.getInstance());
	}
	
	OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.Test1_title_reset:
				Test1_content.setText("网站内容");
				break;
			case R.id.Test1_title_open:
				if(http_task == null || http_task.getStatus() == Status.FINISHED){
					http_task = new Http_task();
					http_task.execute();
				}else{
					;
				}
				break;
			}
		}
	};
	
	private class Http_task extends AsyncTask<Object, Integer, Object>{

		String content = "";
		
		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			publishProgress(0);
			getWebContent();
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			switch(values[0]){
			case 0:
				Test1_content.setText("正在努力获取网页信息\n");
				break;
			case 1:
				Test1_content.setText(content);
				break;
			}
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
		
		private void getWebContent(){
			final String httpUrl = "http://thinkpage.cn/weather/WeatherService.svc/GetChildLocations?id=ZJ&lang=zh-CHS&provider=SMART";
			URL url = null;
			try {
				url = new URL(httpUrl);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
				InputStreamReader in = new InputStreamReader(httpURLConnection.getInputStream());
				BufferedReader buffer = new BufferedReader(in);
				String LineContent = "";
				while((LineContent = buffer.readLine()) != null){
					content += LineContent + "\n";
				}
				publishProgress(1);
				buffer.close();
				in.close();
				httpURLConnection.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				LogHelper.trace();
				e.printStackTrace();
			} 
		}
	}
}
