package com.roccatello.indecente.fragment;

import android.app.Activity;
import android.webkit.WebViewFragment;

import com.roccatello.indecente.MainActivity;

/**
 * Created by Eduard on 07/09/2014.
 */
public class CopioneFragment extends ERWebViewFragment {

    @Override
    public void onResume() {
        super.onResume();
        getWebView().loadUrl(MainActivity.WEB_URL + "copione.html");
    }
}
