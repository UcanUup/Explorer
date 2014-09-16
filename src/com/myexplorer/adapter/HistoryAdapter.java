package com.myexplorer.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myexplorer.R;
import com.myexplorer.lib.Variable;

public class HistoryAdapter extends BaseAdapter {
	
	private Context mContext;
	
	private List<String> item1;
	private List<String> item2;
		
	public HistoryAdapter(Context mContext) {
		super();
		this.mContext = mContext;
		
		item1 = Variable.historyName;
		item2 = Variable.historySite;
	}

	@Override
	public int getCount() {
		return item1.size();
	}

	@Override
	public Object getItem(int position) {
		return item1.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 设置每一个项显示的内容
		RelativeLayout layout = (RelativeLayout)LayoutInflater.from(mContext).inflate(R.layout.history_item, null);
		TextView tv1 = (TextView)layout.findViewById(R.id.name);		
		tv1.setText(item1.get(position));
		
		TextView tv2 = (TextView)layout.findViewById(R.id.site);		
		tv2.setText(item2.get(position));

		return layout;
	}
	
}
