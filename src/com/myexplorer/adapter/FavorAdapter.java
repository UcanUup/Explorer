package com.myexplorer.adapter;

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

public class FavorAdapter extends BaseAdapter {
	
	private Context mContext;
	private boolean isDelete;
	
	private List<String> item1;
	private List<String> item2;
		
	public FavorAdapter(Context mContext, boolean isDelete, boolean selectAll) {
		super();
		this.mContext = mContext;
		this.isDelete = isDelete;
		
		item1 = Variable.favorName;
		item2 = Variable.favorSite;
		
		Variable.favorChecks = new boolean[item1.size()];
		// 全部选中的时候
		for (int i = 0; i < Variable.favorChecks.length; i++) {
			if (selectAll)
				Variable.favorChecks[i] = true;
			else
				Variable.favorChecks[i] = false;
		}
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
		RelativeLayout layout = (RelativeLayout)LayoutInflater.from(mContext).inflate(R.layout.favor_item, null);
		
		// 删除状态的时候复选框才会显示
		if (isDelete) {
			CheckBox cb = (CheckBox)layout.findViewById(R.id.item_cb);
			cb.setFocusable(true);
			cb.setClickable(true);
			cb.setVisibility(View.VISIBLE);
			
			// listview滚动时能够保存check选中的状态
			final int pos = position;
			cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					Variable.favorChecks[pos] = isChecked;
				}
			});
			
			cb.setChecked(Variable.favorChecks[position]);
		}
		
		TextView tv1 = (TextView)layout.findViewById(R.id.name);		
		tv1.setText(item1.get(position));
		
		TextView tv2 = (TextView)layout.findViewById(R.id.site);		
		tv2.setText(item2.get(position));

		return layout;
	}
	
}
