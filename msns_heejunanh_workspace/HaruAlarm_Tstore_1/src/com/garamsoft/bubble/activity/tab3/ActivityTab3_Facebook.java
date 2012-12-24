package com.garamsoft.bubble.activity.tab3;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.garamsoft.bubble.R;

public class ActivityTab3_Facebook extends Activity {
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tab3_facebook_webview);

		mWebView = (WebView) findViewById(R.id.tab3_facebook_webview);

		mWebView.getSettings().setJavaScriptEnabled(true); // 웹뷰에서 자바스크립트실행가능
		// mWebView.loadUrl("http://www.facebook.com/pages/SAME/320984084610053?ref=notif&notif_t=fbpage_fan_invite&sk=wall");

		// 이게 맞나 모르겠다.
		mWebView.loadUrl("http://m.facebook.com/harualarm");
		mWebView.setWebViewClient(new HelloWebViewClient()); // WebViewClient 지정

		mWebView.getSettings().setBuiltInZoomControls(true);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}

	private class HelloWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

}
