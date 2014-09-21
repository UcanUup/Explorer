package com.myexplorer.init;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.myexplorer.R;
import com.myexplorer.lib.HttpUrl;
import com.myexplorer.lib.Variable;
import com.myexplorer.sqlite.FavorDatabase;
import com.myexplorer.sqlite.SettingDatabase;
import com.myexplorer.tab.FavorFragment;
import com.myexplorer.tab.HistoryFragment;
import com.myexplorer.tab.HomeFragment;
import com.myexplorer.tab.SettingFragment;

public class MainActivity extends Activity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	// 侧边栏需要实现的功能
	private static final int HOME_PAGE = 1;
	private static final int STORE_WEB_PAGE = 2;
	private static final int MANAGE_FAVOR = 3;
	private static final int HISTORY_RECORD = 4;
	private static final int SETTING = 5;
	private static final int SHORTCUT = 6;
	private static final int EXIT = 7;
	
	// 储存当前fragment
	private static Fragment fg;

	// 用于代码代开drawer
	private DrawerLayout mDrawerLayout;
	private View mFragmentContainerView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);
		
		// 设置的条数
		Variable.settingNum = 3;
		// 读取设置的值
		SettingDatabase settingDatabase = new SettingDatabase(this);
		settingDatabase.read();

		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// 用于打开drawer
		mFragmentContainerView = findViewById(R.id.navigation_drawer);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		
		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	public interface MyTouchListener {
	    public void onTouchEvent(MotionEvent event);
	}
	
	// 保存MyTouchListener接口的列表
	private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<MainActivity.MyTouchListener>();
	
	/**
	* 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
	* @param listener
	*/
	public void registerMyTouchListener(MyTouchListener listener) {
	     myTouchListeners.add(listener);
	}
	
	/**
	* 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
	* @param listener
	*/
	public void unRegisterMyTouchListener(MyTouchListener listener) {
	    myTouchListeners.remove( listener );
	}
	
	/**
	* 分发触摸事件给所有注册了MyTouchListener的接口
	*/
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) { 
	    for (MyTouchListener listener : myTouchListeners) {
	        listener.onTouchEvent(ev);
	    }
	    return super.dispatchTouchEvent(ev);
	}

	private long exitTime = 0;
	// 监听按键并传给fragment
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// result为true使用自定义方法，否则叠加使用父方法
		boolean result = false;
		
		// 返回键
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (fg instanceof HistoryFragment) {
				result = ((HistoryFragment) fg).onKeyDown(keyCode, event);
			}
			else if (fg instanceof FavorFragment) {
				result = ((FavorFragment) fg).onKeyDown(keyCode, event);
			}
			else if (fg instanceof HomeFragment) {
				// 用户按下返回键时提示再按一次退出程序
				if ((System.currentTimeMillis() - exitTime) > 2000) {
					Toast.makeText(this, R.string.exit_application, Toast.LENGTH_SHORT).show();
					exitTime = System.currentTimeMillis();
					result = true;
				} else {
					finish();
				}
			}
		}
		else if (keyCode == KeyEvent.KEYCODE_MENU
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			// 菜单键控件drawer
			if (mDrawerLayout.isDrawerOpen(mFragmentContainerView))
				mDrawerLayout.closeDrawer(mFragmentContainerView);
			else
				mDrawerLayout.openDrawer(mFragmentContainerView);
			result = true;
		}
		
		if (result)
			return true;
		else
			return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// 选择退出时直接finish
		if (position + 1 == EXIT) {
			Variable.site = HttpUrl.HOME;
			finish();
			return;    // 因为后面的方法还会执行，所以需要return掉
		}
		else if (position + 1 == STORE_WEB_PAGE) {
			if (fg instanceof HomeFragment) {
				// 插入到收藏夹中
				FavorDatabase favorDatabase = new FavorDatabase(this);
				favorDatabase.write(Variable.title, Variable.site);
			
				Toast.makeText(this, R.string.already_add_favor, Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(this, R.string.goto_home_page, Toast.LENGTH_SHORT).show();
			}
			
			return;
		}
		else if (position + 1 == SHORTCUT) {
			createShortCut();
			Toast.makeText(this, R.string.create_shortcut, Toast.LENGTH_SHORT).show();
			return;
		}
		
		// 切换fragment
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position + 1)).commit();
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case HOME_PAGE:
			mTitle = getString(R.string.home_page);
			break;
		case STORE_WEB_PAGE:
			mTitle = getString(R.string.store_page);
			break;
		case MANAGE_FAVOR:
			mTitle = getString(R.string.manage_folder);
			break;
		case HISTORY_RECORD:
			mTitle = getString(R.string.history_record);
			break;
		case SETTING:
			mTitle = getString(R.string.setting);
			break;
		case SHORTCUT:
			mTitle = getString(R.string.shortcut);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return super.onOptionsItemSelected(item);
	}

	// 创建桌面快捷方式
	public void createShortCut(){
		//创建快捷方式的Intent
        Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        //不允许重复创建
        shortcutintent.putExtra("duplicate", false);
        //需要现实的名称
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
        //快捷图片
        Parcelable icon = Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.ic_launcher);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        //点击快捷图片，运行的程序主入口
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(getApplicationContext() , MainActivity.class));
        //发送广播
        sendBroadcast(shortcutintent);
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";
		
		private static int mSelectedNum = HOME_PAGE;

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			// 存储选中的位置
			mSelectedNum = sectionNumber;
			
			PlaceholderFragment fragment = null;
			
			// 选择不同item加载各自的fragment
			switch(mSelectedNum) {
			case HOME_PAGE: 
				fragment = new HomeFragment();
				break;
			case HISTORY_RECORD: 
				fragment = new HistoryFragment();
				break;
			case MANAGE_FAVOR:
				fragment = new FavorFragment();
				break;
			case SETTING:
				fragment = new SettingFragment();
				break;
			default:
				fragment = new PlaceholderFragment();
				break;
			}
			fg = fragment;
			
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
		
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
	}

}
