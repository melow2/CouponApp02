package com.khs.couponapp02.view.fragment.web;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;


import com.story.notebook.viewmodel.MainVM;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.logging.Handler;


public class Bridge {

    private WebView mWebView;

    private static final String TAG = Bridge.class.getSimpleName();
    public Bridge(WebView view) {
        this.mWebView = view;
    }

    @JavascriptInterface
    public void getHtml(String html){
        Document doc = Jsoup.parse(html);
        Elements cou_jungbo = doc.select("ul[class=cpf_inul]").select("span[class=name]");
        Log.d(TAG,cou_jungbo.text());
    }

}
