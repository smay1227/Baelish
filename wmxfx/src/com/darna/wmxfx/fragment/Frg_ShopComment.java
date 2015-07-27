package com.darna.wmxfx.fragment;

import java.util.Map;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.R;
import com.darna.wmxfx.adapter.ShopCommentAdapter;
import com.darna.wmxfx.bean.ShopPoint;
import com.darna.wmxfx.bean.ShopPointTotal;
import com.darna.wmxfx.net.NetLocate;
import com.darna.wmxfx.net.NetShopComment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Frg_ShopComment extends Fragment {
	View view;
	ListView mListView;
	ShopCommentAdapter shopCommentAdapter;
	ShopPointTotal pointTotal;
	TextView tv_comPoint, tv_comNum, headtext;
	LinearLayout ll_head;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.frg_shopcomment, container, false);
			init();
		}
		ViewGroup parent = (ViewGroup)view.getParent();  
        if(parent != null) {  
            parent.removeView(view);  
        }
		return view;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		shopCommentAdapter = new ShopCommentAdapter(getActivity());
	}
	
	private void getData() {
		new NetShopComment(getActivity(), Config.getCachedToken(getActivity()), Config.getCachedShopId(getActivity()), new NetShopComment.SuccessCallback() {
			@Override
			public void onSuccess(ShopPoint shopPoint) {
				shopCommentAdapter.addAll(shopPoint.getPointList());
				pointTotal = shopPoint.getPointTotal();
				setPage();
			}
		}, new NetShopComment.FailCallback() {
			
			@Override
			public void onFail(String errorCode) {
				if (errorCode.equals(Config.RESULT_STATUS_FAIL)){
					System.out.println("获取购物车信息失败");
				}else if(errorCode.equals(Config.RESULT_STATUS_INVALID_TOKEN)){
					setAddressInSession();
				}else if (errorCode.equals(Config.RESULT_STATUS_UNLOCATE)) {
					setAddressInSession();
				}
			}
		});
	}
	
	public void setPage(){
		if (pointTotal.getPointTotal().equals("null")) {
			headtext.setVisibility(View.VISIBLE);
			ll_head.setVisibility(View.GONE);
		}else {
			tv_comPoint.setText(pointTotal.getPointTotal());
			tv_comNum.setText(pointTotal.getTotal() + "次");
		}
	}
	
	private void setAddressInSession() {
		Map<String, String> poiMap = Config.getCachePOI(getActivity());
		new NetLocate(Config.getCachedToken(getActivity()), poiMap.get(Config.KEY_POSTITLE), 
				poiMap.get(Config.KEY_POSADDRESS), poiMap.get(Config.KEY_POSX), poiMap.get(Config.KEY_POSY), new NetLocate.SuccessCallback() {
					@Override
					public void onSuccess() {
						getData();
					}
				}, new NetLocate.FailCallback() {
					@Override
					public void onFail() {
						System.out.println("上传缓存地址到服务器失败");
					}
				});
	}

	private void init() {
		mListView = (ListView) view.findViewById(R.id.mlist);
		mListView.setAdapter(shopCommentAdapter);
		tv_comPoint = (TextView) view.findViewById(R.id.tv_comPoint);
		tv_comNum = (TextView) view.findViewById(R.id.tv_comNum);
		ll_head = (LinearLayout) view.findViewById(R.id.ll_head);
		headtext = (TextView) view.findViewById(R.id.headtext);
		getData();
	}
}
