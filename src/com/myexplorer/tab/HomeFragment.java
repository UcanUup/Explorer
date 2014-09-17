package com.myexplorer.tab;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.myexplorer.R;
import com.myexplorer.init.MainActivity;
import com.myexplorer.init.MainActivity.PlaceholderFragment;
import com.myexplorer.lib.HttpUrl;
import com.myexplorer.lib.Variable;
import com.myexplorer.sqlite.HistoryDatabase;
import com.myexplorer.utils.Validation;

public class HomeFragment extends PlaceholderFragment {

	private EditText mWebSite = null;
	private ImageButton mLoad = null;
	private ProgressBar mProgress = null;
	private WebView mWebView = null;

	private final int RIGHT = 1;
	private final int LEFT = 2;
	
	private GestureDetector gestureDetector;  
	private MainActivity.MyTouchListener myTouchListener;  
	
	private final int FIRST_TIME = 0;
	private final int SECOND_TIME = 1;
	private final int SERVERAL_TIME = 2;
	
	// WebView�����Ź�ģ�����Ŵ���
	private float orginalScale;
	private float currentScale = 0;
	private int times = FIRST_TIME;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.home_page, container,
				false);
		
		mWebSite = (EditText)rootView.findViewById(R.id.site);
		mLoad = (ImageButton)rootView.findViewById(R.id.open);
		mProgress = (ProgressBar)rootView.findViewById(R.id.progress);
		mWebView = (WebView)rootView.findViewById(R.id.web_page);
		
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.getSettings().setUseWideViewPort(true); 
		mWebView.getSettings().setLoadWithOverviewMode(true);

		// ����ҳ�����ʱ�Ľ�����
		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress < 100) {
					mProgress.setVisibility(View.VISIBLE);
					mProgress.setProgress(newProgress * 10);
				}
				else {
					mProgress.setVisibility(View.GONE);
					
					// ���뵽��ʷ��¼��
					HistoryDatabase historyDatabase = new HistoryDatabase(getActivity());
					historyDatabase.write(mWebView.getTitle(), mWebView.getUrl());
				}
				
				super.onProgressChanged(view, newProgress);
			}
		});
		// ��������ϴ�����ʱ�Ĺ���
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				Toast.makeText(getActivity(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
				
				super.onReceivedError(view, errorCode, description, failingUrl);
			}

			@Override
			public void onScaleChanged(WebView view, float oldScale,
					float newScale) {			
				// ȷ����ҳ��ԭʼ���Ź�ģ
				if (times == FIRST_TIME) {
					orginalScale = oldScale;
					currentScale = newScale;
					times = SECOND_TIME;
				}
				else if (times == SECOND_TIME && newScale == orginalScale) {
					currentScale = newScale;
					times = SERVERAL_TIME;
				}
				else {
					times = SERVERAL_TIME;
				}
				
				// �����ǰ���Ź�ģ������δ����ʱ�Ĺ�ģ�����������л���ҳ
				if (currentScale != newScale) {
					((MainActivity)getActivity()).unRegisterMyTouchListener(myTouchListener);
				}
				// �����ǰ���Ź�ģ����δ����ʱ�Ĺ�ģ�����������л���ҳ
				else {
					((MainActivity)getActivity()).registerMyTouchListener(myTouchListener);
				}
				
				super.onScaleChanged(view, oldScale, newScale);
			}
			
		});
		// ���ȷ��ʱ��ת����ҳ
		mLoad.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				// �ж��Ƿ�����������
				if (!Validation.isNetAvailable(getActivity())) {
					Toast.makeText(getActivity(), R.string.internet_error, Toast.LENGTH_SHORT).show();
					return;
				}
				
				String url = mWebSite.getText().toString();
				
				// �����������ַ
				if (url.indexOf("http://") < 0) {
					url = "http://" + url;
				}
				
				times = FIRST_TIME;
				mWebView.loadUrl(url);
				mWebSite.setText("");
			}
		});
		// ���ƻ���ʱ����Ϊ
		gestureDetector = new GestureDetector(getActivity(),
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						float x = e2.getX() - e1.getX();
						float y = e2.getY() - e1.getY();

						// �ж������Ƿ�Ϊ���һ�����������
						if (Math.abs(y) >= Math.abs(x)) {
						} else if (x > 0) {  
			                onFlingDo(RIGHT);  
			            } else if (x < 0) {  
			                onFlingDo(LEFT);  
			            }  
						
						return super.onFling(e1, e2, velocityX, velocityY);
					}
		}); 
		// ���·��ؼ�ʱ������Ի�������ˣ������˳�����
		mWebView.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK
						&& event.getAction() == KeyEvent.ACTION_DOWN
						&& mWebView.canGoBack()) {
					// ���ع����а��·��ؼ���ֹͣ����
					if (mWebView.getProgress() < 100) {
						mWebView.stopLoading();
					}
					mWebView.goBack();
					return true;
				}			
				return false;
			}
		});

		/**
		* Fragment�У�ע��
		* ����MainActivity��Touch�ص��Ķ���
		* ��д���е�onTouchEvent�����������и�Fragment���߼�����
		*/
		myTouchListener = new MainActivity.MyTouchListener() {
		    @Override
		    public void onTouchEvent(MotionEvent event) {
		    	gestureDetector.onTouchEvent(event);  
		    }
		};
		// ��myTouchListenerע�ᵽ�ַ��б�
		((MainActivity)this.getActivity()).registerMyTouchListener(myTouchListener);
		
		// ��ʼ��ʱ������ҳ
		onInit();
		
		rootView.setFocusableInTouchMode(true);
		return rootView;
	}
	
	// ��������ʱ�Ķ���
	void onFlingDo(int direction) {
		switch(direction) {
		case LEFT:
			times = FIRST_TIME;
			mWebView.goForward();
			break;
		case RIGHT:
			times = FIRST_TIME;
			mWebView.goBack();
			break;
		}
	}
	
	void onInit() {
		// ����ʷ��¼����ת�����		
		String link = Variable.gotoUrl;
		Variable.gotoUrl = "";
		if (link != null && !link.equals("")) {
			mWebView.loadUrl(link);
			return;
		}
		
		// �ж��Ƿ�����������
		if (!Validation.isNetAvailable(getActivity())) {
			Toast.makeText(getActivity(), R.string.internet_error, Toast.LENGTH_SHORT).show();
			return;
		}
		
		mWebView.loadUrl(HttpUrl.HOME);
	}

}
