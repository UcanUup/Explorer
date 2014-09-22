package com.myexplorer.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myexplorer.R;
import com.myexplorer.lib.Variable;

public class SettingAdapter extends BaseAdapter {
	
	private Context mContext;
	
	private List<String> item1;
	private List<String> item2;
		
	public SettingAdapter(Context mContext) {
		super();
		this.mContext = mContext;
		
		item1 = new ArrayList<String>();
		item2 = new ArrayList<String>();
		
		item1.add("禁止加载图像");
		item1.add("启用网页缓存");
		item1.add("启用javascript");
		
		item2.add("设置浏览器不加载图像");
		item2.add("启动网页缓存加快加载速度");
		item2.add("启用javascript动态解析脚本");
		
		Variable.settingChecks = Variable.originChecks;
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
		RelativeLayout layout = (RelativeLayout)LayoutInflater.from(mContext).inflate(R.layout.setting_item, null);
		
		CheckBox cb = (CheckBox)layout.findViewById(R.id.item_cb);
		
		// listview滚动时能够保存check选中的状态
		final int pos = position;
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Variable.settingChecks[pos] = isChecked;
			}
		});
		cb.setChecked(Variable.settingChecks[position]);
		
		TextView tv1 = (TextView)layout.findViewById(R.id.name);		
		tv1.setText(item1.get(position));
		
		TextView tv2 = (TextView)layout.findViewById(R.id.hint);		
		tv2.setText(item2.get(position));

		return layout;
	}
	
}
