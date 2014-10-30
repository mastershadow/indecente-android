package com.roccatello.indecente.fragment;

import com.roccatello.indecente.MainActivity;

/**
 * Created by Eduard on 07/09/2014.
 */
public class CreditsFragment extends ERWebViewFragment {

    @Override
    public void onResume() {
        super.onResume();
        getWebView().loadUrl(MainActivity.WEB_URL + "credits/credits.html");
    }
}
