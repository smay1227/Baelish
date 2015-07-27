package com.darna.wmxfx.dialog;

import java.util.ArrayList;
import java.util.List;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.R;
import com.darna.wmxfx.adapter.DishPopAdapter;
import com.darna.wmxfx.atys.Aty_DishIndex;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class DishPop extends ListActivity {
	
	ListView mListView;
	DishPopAdapter dishPopAdapter;
	String code;
	String[] data;
	List<String> dataList;
	RelativeLayout rl_dishpop;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_dishpop);
		code = getIntent().getStringExtra(Config.KEY_CODE);
		data = getIntent().getStringArrayExtra(Config.KEY_DICT);
		
		init();
	}

	private void init() {
		mListView = (ListView) findViewById(android.R.id.list);
		rl_dishpop = (RelativeLayout) findViewById(R.id.rl_dishpop);
		
		dishPopAdapter = new DishPopAdapter(this);
		mListView.setAdapter(dishPopAdapter);
		
		rl_dishpop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		initData();
		
	}
	
	@Override
	public void finish() {
		Intent intent = new Intent(DishPop.this, Aty_DishIndex.class);
		intent.putExtra(Config.KEY_RETURNCODE, code);
		setResult(Config.RESULTCODE, intent);
		super.finish();
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id){
		super.onListItemClick(l, v, position, id);
		String attrName = data[position];
		code = attrName;
		dishPopAdapter.add(dataList, code);
	}

	private void initData() {
		dataList = new ArrayList<String>();
		for (int i = 0; i < data.length; i++) {
			dataList.add(data[i]);
		}
		dishPopAdapter.add(dataList, code);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
