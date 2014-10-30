package com.roccatello.indecente.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.roccatello.indecente.MainActivity;
import com.roccatello.indecente.R;

/**
 * Created by Eduard on 07/09/2014.
 */
public class ERWebViewFragment extends Fragment {

    private WebView webView;


    public ERWebViewFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_web, container, false);
        v.setBackgroundColor(Color.argb(255,0,0,0));
        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
        this.webView = (WebView)this.getView().findViewById(R.id.webView);
        getWebView().setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.startsWith(MainActivity.WEB_URL)) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        getWebView().setBackgroundColor(Color.BLACK);
        getWebView().getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCachePath("/data/data/"+ this.getActivity().getPackageName() +"/cache");
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

    }

    public WebView getWebView() {
        return webView;
    }
}
