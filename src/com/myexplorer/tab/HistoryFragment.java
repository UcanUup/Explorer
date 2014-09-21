package com.myexplorer.tab;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.myexplorer.R;
import com.myexplorer.adapter.HistoryAdapter;
import com.myexplorer.init.MainActivity.PlaceholderFragment;
import com.myexplorer.lib.Variable;
import com.myexplorer.sqlite.HistoryDatabase;

public class HistoryFragment extends PlaceholderFragment {

	private ListView lv;
	private boolean allSelect = false;    // ���ڸı�actionbar�ϵ�ȫѡ��ȫ��ѡ
	private boolean isDelete = false;    // �����Ƿ���ʾactionbar�ϵ�ɾ��
	
	private final int SELECT_ALL = 0;
	private final int SELECT_NONE = 1;
	private final int DELETE_SELECT = 2;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.history, container,
				false);
		
		// ��ȡ��ʷ��¼
		HistoryDatabase historyDatabase = new HistoryDatabase(getActivity());
		historyDatabase.read();
		
		lv = (ListView)rootView.findViewById(R.id.history_list_view);
		lv.setAdapter(new HistoryAdapter(getActivity(), false, false));
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				getActivity().getActionBar().setTitle(getString(R.string.home_page));
				
				// ����ת����ҳ��ַ���͸���ҳ
				Variable.gotoUrl = Variable.historySite.get(position);
				
				// ������ҳ
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager
						.beginTransaction()
						.replace(R.id.container,
								PlaceholderFragment.newInstance(1)).commit();
			}
		});
		
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ����ʱ���¸���actionbar��item
				isDelete = true;
				getActivity().getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
				// ��������listview����ʾcheckbox
				lv.setAdapter(new HistoryAdapter(getActivity(), true, false));
	
				return true;
			}
		});
		
		// ����������ܹ��ڲ˵��Ļص�����������
		setHasOptionsMenu(true);
		
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {	
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == SELECT_ALL) {
			allSelect = true;
			getActivity().getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
			// ��������listview
			lv.setAdapter(new HistoryAdapter(getActivity(), true, true));
			return true;
		}
		else if (id == DELETE_SELECT) {
			// ɾ��ѡ��Ԫ��
			int j = 0;
			HistoryDatabase historyDatabase = new HistoryDatabase(getActivity());
			for (int i = 0; i < Variable.historyChecks.length; i++) {
				if (Variable.historyChecks[i]) {
					historyDatabase.delete(Variable.historyId.get(i-j));
					Variable.historyId.remove(i-j);
					Variable.historyName.remove(i-j);
					Variable.historySite.remove(i-j);
					j++;
				}
			}
			isDelete = false;
			getActivity().getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
			// ��������listview
			lv.setAdapter(new HistoryAdapter(getActivity(), false, false));
			
			return true;
		}
		else if (id == SELECT_NONE) {
			allSelect = false;
			getActivity().getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
			// ��������listview
			lv.setAdapter(new HistoryAdapter(getActivity(), true, false));
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		
		// actionbar��ʾȫѡ��ɾ��
		if (!allSelect && isDelete) {
			menu.add(0, SELECT_ALL, SELECT_ALL, R.string.select_all)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			menu.add(0, DELETE_SELECT, DELETE_SELECT, R.string.delete)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			
			allSelect = true;
		}
		// actionbar��ʾȫ��ѡ��ɾ��
		else if (allSelect && isDelete) {
			menu.add(0, SELECT_NONE, SELECT_NONE, R.string.select_none)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			menu.add(0, DELETE_SELECT, DELETE_SELECT, R.string.delete)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			
			allSelect = false;
		}
		
		super.onPrepareOptionsMenu(menu);
	}
	
	// �������ؼ��˳�ɾ��״̬
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK
        		&& event.getAction() == KeyEvent.ACTION_DOWN 
        		&& isDelete) {
			// ɾ�������а��·��ؼ�����
			allSelect = false;
			isDelete = false;
			getActivity().getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
			// ��������listview
			lv.setAdapter(new HistoryAdapter(getActivity(), false, false));
			return true;
		}			
		return false;
    }
}
