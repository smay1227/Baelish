package com.darna.wmxfx.atys;

import java.util.ArrayList;
import java.util.List;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.R;
import com.darna.wmxfx.atys.Aty_CommonAddress.CommonAddressAdapter.AddressCell;
import com.darna.wmxfx.bean.UserAddress;
import com.darna.wmxfx.net.NetUserAddress;
import com.darna.wmxfx.net.NetUserAddressDefaultSet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Aty_CommonAddress extends Activity {
	ListView mListView;
	AddressCell addressCell;
	CommonAddressAdapter commonAddressAdapter;
	RelativeLayout rl_addnewaddr;
	ImageView iv_rleft;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_commonaddress);
		
		mListView = (ListView) findViewById(R.id.mlist);
		rl_addnewaddr = (RelativeLayout) findViewById(R.id.rl_addnewaddr);
		iv_rleft = (ImageView) findViewById(R.id.iv_rleft);
		
		commonAddressAdapter = new CommonAddressAdapter();
		mListView.setAdapter(commonAddressAdapter);
		
		getData();
		
		setAddNewAddrOnClick();
		
		iv_rleft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		getData();
	}
	
	private void setAddNewAddrOnClick() {
		rl_addnewaddr.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Aty_CommonAddress.this, Aty_CommonAddressAdd.class));
			}
		});
	}

	private void getData() {
		new NetUserAddress(this, Config.getCachedToken(this), new NetUserAddress.SuccessCallback() {
			@Override
			public void onSuccess(List<UserAddress> addresses) {
				commonAddressAdapter.add(addresses);
			}
		}, new NetUserAddress.FailCallback() {
			@Override
			public void onFail(String errorCode) {
				if (errorCode.equals(Config.RESULT_STATUS_FAIL)) {
					System.out.println("获取数据失败");
				}else if (errorCode.equals(Config.RESULT_STATUS_INVALID_TOKEN)) {
					//进行登录和定位操作，然后再调用这个方法
					
				}
			}
		});
	}

	public void SetUserDefaultAddress(String d02011){
		new NetUserAddressDefaultSet(this, Config.getCachedToken(this), d02011, new NetUserAddressDefaultSet.SuccessCallback() {
			@Override
			public void onSuccess() {
				finish();
			}
		}, new NetUserAddressDefaultSet.FailCallback() {
			@Override
			public void onFail(String errorCode) {
				Toast.makeText(Aty_CommonAddress.this, "设置常用地址失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public class CommonAddressAdapter extends BaseAdapter{
		List<UserAddress> addresses = new ArrayList<UserAddress>();
		
		public void add(List<UserAddress> oaddresses){
			addresses.clear();
			this.addresses = oaddresses;
			notifyDataSetChanged();
		}
		
		public void clear(){
			addresses.clear();
		}
		
		@Override
		public int getCount() {
			return addresses.size();
		}

		@Override
		public UserAddress getItem(int position) {
			return addresses.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_addressitem, parent, false);
				addressCell = new AddressCell();
				addressCell.tv_addr = (TextView) convertView.findViewById(R.id.tv_addr);
				addressCell.tv_addruser = (TextView) convertView.findViewById(R.id.tv_addruser);
				addressCell.tv_addrphone = (TextView) convertView.findViewById(R.id.tv_addrphone);
				addressCell.iv_choose = (ImageView) convertView.findViewById(R.id.iv_choose);
				addressCell.rl_useraddr = (RelativeLayout) convertView.findViewById(R.id.rl_useraddr);
				convertView.setTag(addressCell);
			}else {
				addressCell = (AddressCell) convertView.getTag();
			}
			final UserAddress userAddress = getItem(position);
			if (userAddress.getDefaultFlg().equals("1")) {
				addressCell.iv_choose.setVisibility(View.VISIBLE);
			}else {
				addressCell.iv_choose.setVisibility(View.GONE);
			}
			addressCell.tv_addruser.setText(userAddress.getConsignee());
			addressCell.tv_addrphone.setText(userAddress.getTel());
			addressCell.tv_addr.setText(userAddress.getArea_address() + userAddress.getDetailed_address());
			addressCell.rl_useraddr.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					SetUserDefaultAddress(userAddress.getD02011());
				}
			});
			return convertView;
		}
		
		public class AddressCell{
			TextView tv_addruser, tv_addrphone, tv_addr;
			ImageView iv_choose;
			RelativeLayout rl_useraddr;
		}
		
	}
	
}
