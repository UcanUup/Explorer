package com.myexplorer.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.myexplorer.R;
import com.myexplorer.adapter.HistoryAdapter;
import com.myexplorer.init.MainActivity.PlaceholderFragment;
import com.myexplorer.sqlite.HistoryDatabase;

public class HistoryFragment extends PlaceholderFragment {

	private ListView lv;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.history, container,
				false);

		// 读取历史记录
		HistoryDatabase historyDatabase = new HistoryDatabase(getActivity());
		historyDatabase.read();
		
		lv = (ListView)rootView.findViewById(R.id.history_list_view);
		lv.setAdapter(new HistoryAdapter(getActivity()));
		
		return rootView;
	}

}
