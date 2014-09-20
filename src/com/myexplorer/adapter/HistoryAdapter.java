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

public class HistoryAdapter extends BaseAdapter {
	
	private Context mContext;
	private boolean isDelete;
	
	private List<String> item1;
	private List<String> item2;
		
	public HistoryAdapter(Context mContext, boolean isDelete, boolean selectAll) {
		super();
		this.mContext = mContext;
		this.isDelete = isDelete;
		
		item1 = Variable.historyName;
		item2 = Variable.historySite;
		
		Variable.historyChecks = new boolean[item1.size()];
		// ȫ��ѡ�е�ʱ��
		for (int i = 0; i < Variable.historyChecks.length; i++) {
			if (selectAll)
				Variable.historyChecks[i] = true;
			else
				Variable.historyChecks[i] = false;
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
		// ����ÿһ������ʾ������
		RelativeLayout layout = (RelativeLayout)LayoutInflater.from(mContext).inflate(R.layout.history_item, null);
		
		// ɾ��״̬��ʱ��ѡ��Ż���ʾ
		if (isDelete) {
			CheckBox cb = (CheckBox)layout.findViewById(R.id.item_cb);
			cb.setFocusable(true);
			cb.setClickable(true);
			cb.setVisibility(View.VISIBLE);
			
			// listview����ʱ�ܹ�����checkѡ�е�״̬
			final int pos = position;
			cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					Variable.historyChecks[pos] = isChecked;
				}
			});
			
			cb.setChecked(Variable.historyChecks[position]);
		}
		
		TextView tv1 = (TextView)layout.findViewById(R.id.name);		
		tv1.setText(item1.get(position));
		
		TextView tv2 = (TextView)layout.findViewById(R.id.site);		
		tv2.setText(item2.get(position));

		return layout;
	}
	
}
