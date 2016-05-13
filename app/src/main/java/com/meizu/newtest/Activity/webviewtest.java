package com.meizu.newtest.Activity;

/**
 * Created by Administrator on 15-3-17.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.meizu.newtest.R;

//import android.view.Menu;


public class webviewtest extends AppCompatActivity
{

	private WebView webview;
    private Toolbar toolbar;

//	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        setContentView(R.layout.webviewtest);
        setTitle("WebView");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webview = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webview.getSettings();
		webSettings.setJavaScriptEnabled(true);  
        webSettings.setAllowFileAccess(true);
//        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webview.loadUrl("https://testerhome.com");
        webview.setWebViewClient(new webViewClient ());
	}


    /**
     * 实现后退按钮
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home://相应 actionbar的后退按钮
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.layout.webviewtest, menu);
		return true;
	}
*/
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();  
            return true;  
        }  
        finish();
        return false;  
    }  
        
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);  
            return true;  
        }  
    }

    @Override
    public void finish() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.destroy();
    }
}
