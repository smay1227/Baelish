package com.darna.wmxfx.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;

import com.darna.wmxfx.Config;


public class NetConnection {
	public NetConnection(final String url, final HttpMethod method, final SuccessCallback successCallback, final FailCallback failCallback, final String ... kvs) {
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				StringBuffer paramBuffer = new StringBuffer();
				for (int i = 0; i < kvs.length; i+=2) {
					paramBuffer.append(kvs[i]).append("=").append(kvs[i+1]).append("&");
				}
				try {
					URLConnection uc;
					switch (method) {
					case POST:
						uc = new URL(url).openConnection();
						uc.setDoOutput(true);
						BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(uc.getOutputStream(), Config.CHARSET));
						bw.write(paramBuffer.toString());
						bw.flush();
						break;

					default:
						uc = new URL(url + "?" + paramBuffer.toString()).openConnection();
						break;
					}
					
					System.out.println("Request URL: " + uc.getURL());
					System.out.println("Request Data: " + paramBuffer);
					BufferedReader bReader = new BufferedReader(new InputStreamReader(uc.getInputStream(), Config.CHARSET));
					String line = null;
					StringBuffer result = new StringBuffer();
				    while ((line = bReader.readLine()) != null) {
						result.append(line);
					}
				    System.out.println("Result: " + result);
				    return result.toString();
					
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(String result) {
				
				if (result != null) {
					if(successCallback != null){
						successCallback.onSuccess(result);
					}
				}else{
					if (failCallback != null) {
						failCallback.onFail();
					}
				}
				
				super.onPostExecute(result);
			}
			
			
		}.execute();
	}
	
	public static interface SuccessCallback{
		void onSuccess(String result);
	}
	
	public static interface FailCallback{
		void onFail();
	}
	
}
