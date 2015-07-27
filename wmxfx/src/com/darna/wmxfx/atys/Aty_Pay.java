package com.darna.wmxfx.atys;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.R;
import com.google.gson.Gson;
import com.pingplusplus.android.PaymentActivity;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class Aty_Pay extends Activity{
	private static final String URL = "http://192.168.1.106/darna/mobile/pingpp/pay.php";
	private static final int REQUEST_CODE_PAYMENT = 1;
	
	int amount;
	private static final String CHANNEL_ALIPAY = "alipay";
	//private static final String CHANNEL_UPMP = "upmp";
    private static final String CHANNEL_WECHAT = "wx";
    
    String payway = CHANNEL_ALIPAY, sn;
    
    RelativeLayout rl_epay, rl_wechatpay;
    ImageView iv_epay, iv_wechatpay;
    Button btn_confirmpay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_pay);
		
		amount = getIntent().getIntExtra(Config.KEY_AMOUNT, 0);
		sn = getIntent().getStringExtra(Config.KEY_ORDERSN);
		
		rl_epay = (RelativeLayout) findViewById(R.id.rl_epay);
		rl_wechatpay = (RelativeLayout) findViewById(R.id.rl_wechatpay);
		iv_epay = (ImageView) findViewById(R.id.iv_epay);
		iv_wechatpay = (ImageView) findViewById(R.id.iv_wechatpay);
		btn_confirmpay = (Button) findViewById(R.id.btn_confirmpay);
		
		rl_epay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				iv_epay.setVisibility(View.VISIBLE);
				iv_wechatpay.setVisibility(View.GONE);
				payway = CHANNEL_ALIPAY;
			}
		});
		
		rl_wechatpay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				iv_epay.setVisibility(View.GONE);
				iv_wechatpay.setVisibility(View.VISIBLE);
				payway = CHANNEL_WECHAT;
			}
		});
		
		btn_confirmpay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onConfirm();
			}
		});
		
		//支付宝支付
		/*findViewById(R.id.btn_apay).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Toast.makeText(Aty_Pay.this, "支付宝支付", Toast.LENGTH_SHORT).show();
				new PaymentTask().execute(new PaymentRequest(CHANNEL_ALIPAY, amount));
			}
		});
		//微信支付
		findViewById(R.id.btn_weixinpay).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Toast.makeText(Aty_Pay.this, "微信支付", Toast.LENGTH_SHORT).show();
				new PaymentTask().execute(new PaymentRequest(CHANNEL_WECHAT, amount));
			}
		});
		//银联卡支付
		findViewById(R.id.btn_yinlianpay).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Toast.makeText(Aty_Pay.this, "银联卡支付", Toast.LENGTH_SHORT).show();
				new PaymentTask().execute(new PaymentRequest(CHANNEL_UPMP, amount));
			}
		});*/
	}
	
	
	protected void onConfirm() {
		if (payway.equals(CHANNEL_ALIPAY)) {
			Toast.makeText(Aty_Pay.this, "支付宝支付", Toast.LENGTH_SHORT).show();
			new PaymentTask().execute(new PaymentRequest(CHANNEL_ALIPAY, sn, amount));
		}else {
			Toast.makeText(Aty_Pay.this, "微信支付", Toast.LENGTH_SHORT).show();
			new PaymentTask().execute(new PaymentRequest(CHANNEL_WECHAT, sn, amount));
		}
	}


	class PaymentTask extends AsyncTask<PaymentRequest, Void, String> {

        @Override
        protected void onPreExecute() {

            //按键点击之后的禁用，防止重复点击
        	btn_confirmpay.setEnabled(false);
        }

        @Override
        protected String doInBackground(PaymentRequest... pr) {

            PaymentRequest paymentRequest = pr[0];
            String data = null;
            String json = new Gson().toJson(paymentRequest);
            try {
                //向Your Ping++ Server SDK请求数据
                data = postJson(URL, json);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String data) {
        	Log.d("charge", data);
            Intent intent = new Intent();
            String packageName = getPackageName();
            ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
            intent.setComponent(componentName);
            intent.putExtra(PaymentActivity.EXTRA_CHARGE, data);
            startActivityForResult(intent, REQUEST_CODE_PAYMENT);
        }

    }
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //支付页面返回处理
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                /* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */
               // System.out.println("支付成功");
                //String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                //String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                //showMsg("支付成功", "", "");
                System.out.println("支付成功");
                Intent intent = new Intent(Aty_Pay.this, Aty_UnfinishedOrder.class);
                intent.putExtra(Config.KEY_ORDERSN, sn);
                startActivity(intent);
                finish();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //showMsg("User canceled", "", "");
            	System.out.println("支付失败");
            	Intent intent = new Intent(Aty_Pay.this, Aty_UnfinishedOrder.class);
                intent.putExtra(Config.KEY_ORDERSN, sn);
                startActivity(intent);
                finish();
            }
        }
    }
    
    public void showMsg(String title, String msg1, String msg2) {
    	String str = title;
    	if (msg1.length() != 0) {
    		str += "\n" + msg1;
    	}
    	if (msg2.length() != 0) {
    		str += "\n" + msg2;
    	}
    	AlertDialog.Builder builder = new Builder(Aty_Pay.this);
    	builder.setMessage(str);
    	builder.setTitle("提示");
    	builder.setPositiveButton("OK", null);
    	builder.create().show();
    }
	
	private static String postJson(String url, String json) throws IOException {
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(type, json);
        Request request = new Request.Builder().url(url).post(body).build();

        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }
	
	class PaymentRequest {
        String channel;
        String sn;
        int amount;

        public PaymentRequest(String channel, String sn, int amount) {
            this.channel = channel;
            this.sn = sn;
            this.amount = amount;
        }
    }
}



















