package com.darna.wmxfx.fragment;

import com.darna.wmxfx.R;
import com.darna.wmxfx.utils.BaiduUtil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class Frg_LocationHistory extends Fragment {
	View view;
	RelativeLayout rl_locnow;
	BaiduUtil baiduUtil;
	Boolean flag = false;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.frg_locationhistory, container, false);
			init();
		}
		ViewGroup parent = (ViewGroup)view.getParent();  
        if(parent != null) {  
            parent.removeView(view);  
        }
		return view;
	}
	
	@Override
	public void onDestroy() {
		if (flag) {
			baiduUtil.stop();
		}
		super.onDestroy();
	}
	
	private void init() {
		baiduUtil = new BaiduUtil(getActivity());
		rl_locnow = (RelativeLayout) view.findViewById(R.id.rl_locnow);
		rl_locnow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				flag = true;
				baiduUtil.start();
			}
		});
	}
	
	
}
