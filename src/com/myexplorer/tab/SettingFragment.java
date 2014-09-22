package com.myexplorer.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.myexplorer.R;
import com.myexplorer.adapter.SettingAdapter;
import com.myexplorer.init.MainActivity.PlaceholderFragment;
import com.myexplorer.lib.Variable;
import com.myexplorer.sqlite.SettingDatabase;

public class SettingFragment extends PlaceholderFragment {

	private ListView lv;
	
	private final int SAVE = 1;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.setting, container,
				false);
		
		lv = (ListView)rootView.findViewById(R.id.setting_list_view);
		lv.setAdapter(new SettingAdapter(getActivity()));
		
		// 设置这个才能关于菜单的回调函数才有用
		setHasOptionsMenu(true);
		
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {	
		menu.clear();
		menu.add(0, SAVE, SAVE, R.string.save)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == SAVE) {
			Variable.originChecks = Variable.settingChecks;
			
			// 插入到历史记录中
			SettingDatabase settingDatabase = new SettingDatabase(getActivity());
			settingDatabase.delete();
			settingDatabase.write(Variable.originChecks);
			
			Toast.makeText(getActivity(), R.string.save_success, Toast.LENGTH_SHORT).show();
			
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
